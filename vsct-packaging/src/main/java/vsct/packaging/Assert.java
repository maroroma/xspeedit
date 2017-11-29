package vsct.packaging;

/**
 * Classe utilitaire pour les assertions (sans tirer spring).
 * @author rlevexie
 *
 */
public abstract class Assert {

	/**
	 * Contrôle que l'objet en entrée n'est pas null.
	 * @param toTest
	 * 		objet à tester.
	 * @throws IllegalArgumentException si l'objet est null.
	 * 
	 */
	public static void notNull(final Object toTest) throws IllegalArgumentException {
		if (toTest == null) {
			throw new IllegalArgumentException("cet objet ne peut être null");
		}
	}
	
}
