package vsct.packaging;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;


/**
 * Représente un paquet, dont la taille max est {@link Constants#MAX_SIZE}.
 * <br /> Il permet de maintenir la liste des items qui y ont été associés, et offre des fonctionnalités permettant
 * de connaitre son occupation à n'importe quel moment.
 * @author rlevexie
 *
 */
@Data
public class Package {

	

	/**
	 * Liste des items portés par le paquet.
	 */
	private List<Item> items;
	
	/**
	 * Taille maximale du paquet.
	 */
	private int maxSize;
	
	/**
	 * Constructeur.
	 */
	public Package() {
		// taille par défaut.
		this.maxSize = Constants.MAX_SIZE;
		this.items = new ArrayList<>();
	}
	
	/**
	 * Détermine si le paquet est rempli (en fonction de la taille des items qui le constitue).
	 * @return
	 * 		true si le paquet est rempli, false sinon
	 */
	public boolean isFull() {
		return this.maxSize <= this.getItemsTotalSize();
	}
	
	/**
	 * Retourne la taille totale occupée par l'ensemble des items du paquet.
	 * @return 
	 * 		La taille totale cumulée de l'ensemble des items du paquet.
	 */
	protected int getItemsTotalSize() {
		// on récupère la taille de chaque item avant cumul.
		return this.items.stream().mapToInt(oneItem -> oneItem.getItemSize()).sum();
	}
	

	/**
	 * Permet de savoir si ce paquet peut contenir le nextItem.
	 * @param nextItem 
	 * 		{@link Item} dont on veut savoir si il peut être contenu dans le paquet.
	 * @return 
	 * 		true si le paquet peut accueillir l'item testé, false sinon.
	 */
	public boolean accept(final Item nextItem) {
		Assert.notNull(nextItem);
		return this.maxSize >= this.getItemsTotalSize() + nextItem.getItemSize();
	}

	/**
	 * Rajout d'un nouvel {@link Item} dans le paquet.
	 * <br /> <b>L'item ajouté est automatiquement flagué comme "ajouté" ({@link Item#isAdded()} renverra true)</b>
	 * @param nextItem
	 * 		nouvel item à ajouter.
	 */
	public void addItem(final Item nextItem) {
		Assert.notNull(nextItem);
		this.items.add(nextItem);
		nextItem.setAdded(true);
	}
	
	/**
	 * Convertir la liste d'items du paquet en une chaine concaténée simple donnant leur taille.
	 * <br /> un paquet portant une liste d'item suivante 2, 3, 4 retournera à travers cette méthode la chaine 234
	 * @return chaine formatée des items.
	 */
	public String toDisplayString() {
		return this.items.stream()
				// conversion des tailles en string avant collecte
				.map(oneItem -> Integer.toString(oneItem.getItemSize()))
				// aggregation des chaines.
				.collect(Collectors.joining());
	}
	
	/**
	 * Permet de concaténer l'ensemble des {@link Package} pour la sortie.
	 * @param allToDisplay -
	 * @return -
	 */
	public static String displayAll(final List<Package> allToDisplay) {
		Assert.notNull(allToDisplay);
		return allToDisplay.stream().map(pak -> pak.toDisplayString()).collect(Collectors.joining(Constants.DELIMITER));
	}
 	
}
