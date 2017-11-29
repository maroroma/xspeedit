package vsct.packaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;


/**
 * Classe de test pour {@link Packager}.
 * @author rlevexie
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class PackagerTest {

	/**
	 * Permet de générer un nombre d'item aléatoires de taille aléatoire.
	 * On reste dans les 50 items, avec des tailles allant de 1 à 9.
	 * @return liste d'item aléatoires
	 */
	private List<Integer> randomize() {
		List<Integer> returnValue = new ArrayList<>();
		Random randomizer = new Random();
		// nombre d'item aléatoire
		int nbInput = randomizer.nextInt(50) + 1;

		
		// pour le nombre d'item donné, on génère des taille aléatoire
		for (int i = 0; i < nbInput; i++) {
			returnValue.add(randomizer.nextInt(9) + 1);
		}

		return returnValue;

	}
	
	/**
	 * Retourne la liste d'item sous forme de tableau de String (on simule l'entrée du {@link Packager}.
	 * @param input -
	 * @return -
	 */
	private String[] convertAsArgs(final List<Integer> input) {
		return new String[]{
			input.stream().map(oneInteger -> oneInteger.toString()).collect(Collectors.joining())};
	}

	/**
	 * On valide que tous les items une fois packagés sont tous présents.
	 */
	@Test
	public void testThatItemAreAllPresentInPackages() {

		List<Integer> itemListInput = randomize();

		Packager packager = new Packager();

		// pour les comparaisons
		List<Integer> rawOutput = new ArrayList<>();

		// création des packages
		List<Package> packages = packager.packageAll(convertAsArgs(itemListInput));

		// récupération de l'ensemble de taille d'item
		packages.stream()
		.forEach(onePack -> 
		rawOutput.addAll(onePack.getItems()
				.stream().map(oneItem -> oneItem.getItemSize()).collect(Collectors.toList())));

		// tri à l'identique de la liste initiale et de la liste cumulée sortie des paquets
		itemListInput.sort(Integer::compare);
		rawOutput.sort(Integer::compare);

		// les tailles doivent être identiques
		Assert.assertEquals("Les liste ne sont pas de même taille", itemListInput.size(), rawOutput.size());

		// les suites doivent être identiques
		for (int i = 0; i < itemListInput.size(); i++) {
			Assert.assertEquals("les items ne sont pas identiques", itemListInput.get(i), rawOutput.get(i));
		}

	}

	
	/**
	 * On valide que par paquet, les items ne débordent pas de la taille max donnée {@link Constants#MAX_SIZE}.
	 */
	@Test
	public void testThatItemsFitInPackages() {
		List<Integer> itemListInput = randomize();

		Packager packager = new Packager();

		List<Package> packages = packager.packageAll(convertAsArgs(itemListInput));

		Assert.assertTrue("Certains paquets ont des items qui débordent", 
				// on s'assure qu'aucun total de taille d'item ne dépasse la taille max.
				packages.stream().allMatch(onePackage -> onePackage.getItemsTotalSize() <= Constants.MAX_SIZE));
		
	}
	
	/**
	 * On s'assure qu'aucun paquet n'est vide dans la liste retournée.
	 */
	@Test
	public void testThatNoPackageAreEmpty() {
		List<Integer> itemListInput = randomize();

		Packager packager = new Packager();

		List<Package> packages = packager.packageAll(convertAsArgs(itemListInput));
		
		Assert.assertTrue("Aucun paquet ne doit être vide", packages.stream().allMatch(onePack -> !onePack.getItems().isEmpty()));
	}
	
	/**
	 * On valide que l'on ne produit pas plus de package que d'items à regrouper.
	 */
	@Test
	public void testThatPackageNumberIsLessOrEqualThatItemNumber() {
		List<Integer> itemListInput = randomize();

		Packager packager = new Packager();

		List<Package> packages = packager.packageAll(convertAsArgs(itemListInput));
		
		Assert.assertTrue("Il ne peut y avoir plus de paquet que d'items", packages.size() <= itemListInput.size());
	}
	
	/**
	 * On valide que l'on controle bien les entrées.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testThatNullInputIsRejected() {
		Packager packager = new Packager();
		packager.packageAll(null);
	}
	
	/**
	 * On valide que l'on controle bien les entrées.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testThatEmptyInputIsRejected() {
		Packager packager = new Packager();
		packager.packageAll(new String[]{});
	}
	
	/**
	 * On valide que l'on controle bien les entrées.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testThatEmptyStringInputIsRejected() {
		Packager packager = new Packager();
		packager.packageAll(new String[]{""});
	}
	
	/**
	 * On valide que l'on controle bien les entrées.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testThatTooManyArgsIsRejected() {
		Packager packager = new Packager();
		packager.packageAll(new String[]{"test", "test"});
	}
	
	/**
	 * On valide que l'on controle bien les entrées.
	 */
	@Test(expected = NumberFormatException.class)
	public void testThatNonNumericValueIsRejected() {
		Packager packager = new Packager();
		packager.packageAll(new String[]{"z12234"});
	}
	
	/**
	 * On valide que l'on controle bien les entrées.
	 */
	@Test(expected = NumberFormatException.class)
	public void testThatNonNumericValueIsRejected2() {
		Packager packager = new Packager();
		packager.packageAll(new String[]{"122)à=)à34"});
	}
	
}
