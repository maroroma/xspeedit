package vsct.packaging;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * Permet de réaliser quelques tests de base sur le {@link Package}.
 * @author rlevexie
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class PackageTest {
	// constantes pour les asserts.
	private static final String ERROR_PACKAGE_REJECTED = "Package devrait rejeté l'item";
	private static final String ERROR_WRONG_SIZE = "La taille est ko";
	private static final String ERROR_PACKAGE_NOT_FULL = "package ne devrait pas être plein";
	private static final String ERROR_PACKAGE_ACCEPTED = "Package devrait accepté l'item";

	/**
	 * Test qu'un item ajouté dans le packaqge est bien présent et flagué au passage.
	 */
	@Test
	public void testThatItemAreAdded() {
		Package packageToTest = new Package();
		Item itemToAdd = new Item(3);
		Assert.assertFalse("L'item ne peut être added à sa création", itemToAdd.isAdded());
		Assert.assertTrue("Aucun item ne peut être présent au démarrage", packageToTest.getItems().isEmpty());

		packageToTest.addItem(itemToAdd);

		Assert.assertEquals("Un item doit être présent", 1, packageToTest.getItems().size());
		Assert.assertTrue("L'item doit avoir été flaggué à added", itemToAdd.isAdded());

	}


	/**
	 * Valide que les {@link Item} sont bien acceptés tant que la taille max n'est pas atteinte
	 */
	@Test
	public void testThatPackageAcceptItemWithGoodSize() {
		Package packageToTest = new Package();
		
		// premier tests sur taille brute
		Assert.assertTrue(ERROR_PACKAGE_ACCEPTED, packageToTest.accept(new Item(3)));
		Assert.assertTrue(ERROR_PACKAGE_ACCEPTED, packageToTest.accept(new Item(10)));
		Assert.assertFalse(ERROR_PACKAGE_REJECTED, packageToTest.accept(new Item(13)));
		
		// deuxième test en prenant en compte l'ajout
		Assert.assertTrue(ERROR_PACKAGE_ACCEPTED, packageToTest.accept(new Item(3)));
		packageToTest.addItem(new Item(3));
		Assert.assertTrue(ERROR_PACKAGE_ACCEPTED, packageToTest.accept(new Item(6)));
		packageToTest.addItem(new Item(6));
		Assert.assertFalse(ERROR_PACKAGE_REJECTED, packageToTest.accept(new Item(2)));
		
	}

	/**
	 * Valide que le paquet return true sur {@link Package#isFull()} lorsque la taille max est dépassée.
	 */
	@Test
	public void testThatPackageDetectsWhenItsFull() {
		Package packageToTest = new Package();

		packageToTest.addItem(new Item(2));
		Assert.assertFalse(ERROR_PACKAGE_NOT_FULL, packageToTest.isFull());
		packageToTest.addItem(new Item(3));
		Assert.assertFalse(ERROR_PACKAGE_NOT_FULL, packageToTest.isFull());
		packageToTest.addItem(new Item(4));
		Assert.assertFalse(ERROR_PACKAGE_NOT_FULL, packageToTest.isFull());
		packageToTest.addItem(new Item(4));
		Assert.assertTrue("package devrait pas être plein", packageToTest.isFull());

		Assert.assertEquals("Le paquet devrait contenir 4 items", 4, packageToTest.getItems().size());
	}

	/**
	 * Valide que le {@link Package} retourne la taille cumulée des items qu'il contient correctement.
	 */
	@Test
	public void testThatPackageCalculateItsSize() {
		Package packageToTest = new Package();

		packageToTest.addItem(new Item(2));
		Assert.assertEquals(ERROR_WRONG_SIZE, 2, packageToTest.getItemsTotalSize());
		packageToTest.addItem(new Item(4));
		Assert.assertEquals(ERROR_WRONG_SIZE, 6, packageToTest.getItemsTotalSize());
		packageToTest.addItem(new Item(12));
		Assert.assertEquals(ERROR_WRONG_SIZE, 18, packageToTest.getItemsTotalSize());
	}
	
	/**
	 * Valide que la chaine d'affichage est ok.
	 */
	@Test
	public void testThatDisplayStringIsOk() {
		Package packageToTest = new Package();

		packageToTest.addItem(new Item(2));
		packageToTest.addItem(new Item(4));
		packageToTest.addItem(new Item(12));
		
		Assert.assertEquals("La chaine d'affichage est ko", "2412", packageToTest.toDisplayString());
	}
	
	/**
	 * La valide que la méthode de concaténation finale des paquest est ok.
	 */
	@Test
	public void testThatAllConcatIsOk() {
		List<Package> packages = new ArrayList<>();
		
		Package packageToTest = new Package();
		packageToTest.addItem(new Item(2));
		packageToTest.addItem(new Item(4));
		
		Package packageToTest2 = new Package();
		packageToTest2.addItem(new Item(3));
		packageToTest2.addItem(new Item(8));
		
		
		packages.add(packageToTest);
		packages.add(packageToTest2);
		
		Assert.assertEquals("24/38", Package.displayAll(packages));
		
		
	}

}
