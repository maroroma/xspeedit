package vsct.packaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Classe de traitement principal, appliquant l'algo du "firstFitDecreasing" pour la résolution de ce problème.
 * <br /> Cette classe prend en entrée une liste de chaine brute, en provance de la méthode main.
 * <br /> La chaine est splittée et convertie en entier puis en {@link Item}, qui seront triés et regroupés par la suite.
 * <br /> Le firstFitDecreasing consiste à trier les {@link Item} par taille décroissante, puis de remplir les {@link Package} 
 * en recherchant dans cette liste triée tout item qui completera le paquet.
 * <br /> On commence donc le premier paquet avec le plus gros Item, puis on parcourt le reste de la liste pour 
 * trouver le premier item rentrant de nouveau dans le paquet, et ce jusqu'à remplissage.
 * @author rlevexie
 *
 */
public class Packager {


	/**
	 * Prend en entrée l'entrée du main java initial, puis retourne une liste de {@link Package}, contenant les {@link Item} regroupés au mieu.
	 * @param args - liste de {@link String}. Seul le premier item est parsé. Il ne doit y avoir qu'un seul item.
	 * @return -
	 */
	public List<Package> packageAll(final String[] args) {

		// validation de base des arguments en entrée
		this.validateRaw(args);

		
		// construction de la liste d'item, qui va être utilisé pour créer nos paquets.
		List<Item> itemList = this.convertInputToOrderedDescItemList(args);
		
		// list de package à produire
		List<Package> packageList = new ArrayList<>();

		// package de travail
		Package currentPackage = new Package();
		
		// on boucle sur la liste d'item tant qu'ils n'ont pas tous été ajoutés dans un paquet
		while (itemList.stream().anyMatch(oneItem -> !oneItem.isAdded())) {
			
			// pour réuse dans les lambdas
			final Package testPackage = currentPackage;
			
			// on ne récupère que les items non ajoutés dont la taille match avec la taille restante sur le package en cours
			// et on prend le premier item de cette liste
			Optional<Item> itemToAdd = itemList.stream()
					.filter(oneItem -> !oneItem.isAdded() && testPackage.accept(oneItem))
					.findFirst();
			
			
			
			// si un item est trouvé on l'ajoute dans le paquet courant
			// (il est disponible, et sa taille passe dans le paquet)
			if (itemToAdd.isPresent()) {
				currentPackage.addItem(itemToAdd.get());
			}
			
			// si le paquet courant est plein 
			// ou si aucun item n'a été trouvé, 
			// ou si tous les items ont été ajoutés
			// on rajoute le paquet courant à la liste, et on recrée un nouveau paquet de travail 
			// (potentiellement non utilisé si tous les items sont triés)
			if (currentPackage.isFull() 
					|| !itemToAdd.isPresent() || itemList.stream().allMatch(oneItem -> oneItem.isAdded())) {
				packageList.add(currentPackage);
				currentPackage = new Package();
			}
		}
		
		
		
		
		return packageList;
	}

	/**
	 * Validation des arguments en entrées.
	 * <br /> On s'assure que :
	 * <br /> - la liste d'argument n'est pas vide
	 * <br /> - ne contient qu'un paramètre.
	 * <br /> 
	 * @param args -
	 */
	private void validateRaw(final String[] args) {
		
		// liste de paramètres non vide
		if (args == null || args.length == 0) {
			throw new IllegalArgumentException("Les paramètres ne peuvent être vides");
		}

		
		// pas plus de 1 paramètres
		if (args.length > 1) {
			throw new IllegalArgumentException("Il ne peut y avoir qu'un seul paramètre");
		}
		
		// le premier paramètre est non vide
		if (args[0] == null || args[0].length() == 0) {
			throw new IllegalArgumentException("La chaine en entrée est vide");
		}
		
	}

	/**
	 * Convertit le string initial en entrée en {@link Item}.
	 * <br /> La liste en sortie est triée de manière décroissante, afin d'implémenter la méthode  first-fit
	 * @param args 
	 * 			arguments de base du main java.
	 * @return 
	 * 			Liste d'{@link Item}, triée dans l'ordre décroissant.
	 * @throws NumberFormatException 
	 * 			si un des caractères n'est pas numérique dans la liste fournie
	 */
	private List<Item> convertInputToOrderedDescItemList(final String[] args) throws NumberFormatException {
		return args[0]
				// conversion en tableau de caractère
				.chars()
				// controle sur la conversion possible du caractère en entier
				.peek(oneInt -> {
					// si le char n'est pas un entier, au fait sauter la conversion et le reste de la routine
					if (!Character.isDigit(oneInt)) {
						throw new NumberFormatException("Un des items n'est pas un digit");
					}
				})
				// conversion des chars en entier puis en Item
				.mapToObj(oneInt -> new Item(Character.getNumericValue(oneInt)))
				// tri décroissant
				.sorted((item1, item2) -> Integer.compare(item2.getItemSize(), item1.getItemSize()))
				// transformation en liste
				.collect(Collectors.toList());
	}

}
