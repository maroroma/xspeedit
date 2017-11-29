package vsct.packaging;

import java.util.List;

/**
 * Classe principale.
 * @author rlevexie
 *
 */
public class Main {

	/**
	 * La méthode se contente de trace via du System.out.println les entrées et les sorties.
	 * Elle sort aussi un message d'erreur selon l'exception rencontrée.
	 * @param args -
	 */
	public static void main(final String[] args) {

		System.out.println("Arguments en entrée :");
		if (args != null) {
			for (String oneArg : args) {
				System.out.println(oneArg);
			}
		}

		try {
			// packaging
			Packager test = new Packager();
			List<Package> output = test.packageAll(args);

			// sortie
			System.out.println("Résultat du packaging :");
			System.out.println(Package.displayAll(output));
			System.out.println(output.size() + " paquets");
			
		} catch (IllegalArgumentException e) {
			System.out.println("Une erreur est survenue lors de la validation des paramètres, vérifiez svp (" + e.getMessage() + ")");
		}
	}

}
