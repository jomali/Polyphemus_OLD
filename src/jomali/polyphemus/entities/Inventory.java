package jomali.polyphemus.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import jomali.polyphemus.entities.items.Attire;
import jomali.polyphemus.entities.items.Consumable;
import jomali.polyphemus.entities.items.Readable;
import jomali.polyphemus.entities.items.Weapon;

/**
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 *
 */
public class Inventory {
	
	private static final int ALL			= 0;
	private static final int WEAPONS		= 1;
	private static final int APPAREL		= 2;
	private static final int CONSUMABLES	= 3;
	private static final int READINGS		= 4;
	private static final int MISCELLANY	= 5;
	
	private int capacity;
	private int counter;
	private List<List<Item>> items;
	
	public Inventory(int capacity) {
		this.capacity	= capacity;
		this.counter	= 0;
		this.items		= new ArrayList<List<Item>>();
		// Se annaden las listas de objetos
		items.add(ALL,			new LinkedList<Item>());
		items.add(WEAPONS,		new LinkedList<Item>());
		items.add(APPAREL,		new LinkedList<Item>());
		items.add(CONSUMABLES,	new LinkedList<Item>());
		items.add(READINGS,		new LinkedList<Item>());
		items.add(MISCELLANY,	new LinkedList<Item>());
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	private int category(Item item) {
		if (item instanceof Weapon)			return WEAPONS;
		else if (item instanceof Attire)		return APPAREL;
		else if (item instanceof Consumable)	return CONSUMABLES;
		else if (item instanceof Readable)	return READINGS;
		else									return MISCELLANY;
	}
	
	/**
	 * Introduce el elemento <code>item</code> en la posicion que le 
	 * corresponda por orden alfabetico en la lista <code>list</code>.
	 * 
	 * <p>En el peor de los casos --la posicion del nuevo elemento se encuentra 
	 * al final de la lista--, se tienen que recorrer todos los elementos antes 
	 * de introducir el nuevo. Hay algoritmos de busqueda en listas ordenadas, 
	 * como el de busqueda binaria, que en otras circunstancias podrian dar con 
	 * la posicion del nuevo elemento sin tener que recorrer la lista por 
	 * completo. Sin embargo, el inventario utiliza listas de acceso secuencial 
	 * para almacenar los objetos en las que para acceder a un elemento 
	 * <i>n</i> antes hay que recorrer todos los elementos <i>(0, n-1)</i> 
	 * anteriores a el, de forma que utilizar uno de estos algoritmos de 
	 * busqueda no supondria una mejora de eficiencia (posiblemente al 
	 * contrario).
	 * 
	 * @param list, lista en la que se desea annadir el nuevo elemento
	 * @param item, elemento que se desea annadir a la lista
	 * @return true al annadir el elemento a la lista correctamente
	 */
	private static boolean add(List<Item> list, Item item) {
		ListIterator<Item> it = list.listIterator();
		while (it.hasNext()) {
			Item i = it.next();
			if (i.compareTo(item) >= 0) { it.previous(); break; }
		}
		it.add(item);
		return true;
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	public Integer[] getCategories() {
		List<Integer> categories = new LinkedList<Integer>();
		for (int i=0; i<items.size(); i++) {
			if (!items.get(i).isEmpty()) categories.add(i);
		}
		return categories.toArray(new Integer[0]);
	}
	
	public List<Item> getList(int category) {
		return items.get(category);
	}
	
	public Item getItem(int category, int index) {
		return items.get(category).get(index);
	}
	
	public boolean isEmpty() {
		return counter == 0;
	}
	
	public boolean isFull() {
		if (capacity < 0) return false;
		return counter >= capacity;
	}
	
	/**
	 * Introduce el elemento <code>item</code> en el inventario.
	 * @param item, elemento a annadir al inventario
	 */
	public void add(Item item) {
		if (isFull()) return;
		counter ++;
		// Se introduce el objeto en la lista general:
		add(items.get(ALL), item);
		// Se introduce el objeto en su lista particular:
		add(items.get(category(item)), item);
	}
	
	public void remove(Item item) {
		// TODO ...
	}

}
