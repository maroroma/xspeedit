package vsct.packaging;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * L'item est le résultat de l'extraction d'une taille d'item de la chaine brute.
 * <br /> Pour faciliter les opérations de regroupement, on lui colle la propriété {@link Item#added}.
 * @author rlevexie
 *
 */
@Data
@NoArgsConstructor
public class Item {

	/**
	 * Taille de l'item.
	 */
	private int itemSize;
	
	/**
	 * L'item a-t-il été ajouté à un paquet ?
	 */
	private boolean added;
	
	/**
	 * Constructeur.
	 * @param size taille de l'item.
	 */
	public Item(final int size) {
		this.itemSize = size;
	}
}
