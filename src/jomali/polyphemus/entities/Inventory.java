/* POLYPHEMUS - un sencillo juego roguelike en Java
 * Copyright © 2012 J. Francisco Martin 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * NOTA: Se ha procurado suprimir todos los acentos a fin de evitar 
 * posibles problemas de compatibilidad entre diferentes computadoras.
 */

package jomali.polyphemus.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import jomali.polyphemus.entities.items.Armor;
import jomali.polyphemus.entities.items.Food;
import jomali.polyphemus.entities.items.Weapon;

/**
 * TODO: Modificar la estructura de datos utilizada para la gestion del 
 * inventario en orden de facilitar su manejo y mejorar la eficiencia de sus 
 * operaciones.
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 *
 */
public class Inventory {
	
	private int weightCapacity;
	private int weight;
	
	private List<Item> items;
	private List<Item> weapons;
	private List<Item> armors;
	private List<Item> food;
	private List<Item> miscellany;
		
	public Inventory(int weightCapacity) {
		this.weightCapacity	= weightCapacity;
		this.weight			= 0;
		// Se inicializan las listas de objetos
		this.items			= new LinkedList<Item>();
		this.weapons		= new LinkedList<Item>();
		this.armors			= new LinkedList<Item>();
		this.food			= new LinkedList<Item>();
		this.miscellany		= new LinkedList<Item>();		
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos de acceso a objetos del inventario:
	
	public List<Item> getItems() { return items; }
	
	public List<Item> getWeapons() { return weapons; }
	
	public List<Item> getArmors() { return armors; }
	
	public List<Item> getFood() { return food; }
	
	public List<Item> getMiscellany() { return miscellany; }
	
	public Item getItem(int index) { return items.get(index); }
	
	public Item getWeapon(int index) { return weapons.get(index); }
	
	public Item getArmor(int index) { return armors.get(index); }
	
	public Item getMiscellany(int index) { return miscellany.get(index); }
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos para introducir objetos:
	
	/**
	 * Introduce el elemento <code>item</code> en la posicion que le 
	 * corresponda segun orden alfabetico en la lista <code>list</code>.
	 * 
	 * TODO: Eficiencia actual del algoritmo: O(n). Dado que la lista esta 
	 * ordenada, se puede mejorar la eficiencia utilizando el algoritmo de 
	 * busqueda binaria para dar con la posicion que le corresponde a 
	 * <code>item</code> dentro de la lista.
	 * 
	 * @param list, lista a la que se desea annadir el elemento
	 * @param item, elemento que se desea annadir a la lista
	 * @return true si el elemento se annade a la lista correctamente, false 
	 * 		en caso contrario
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
	
	/**
	 * TODO: Introducir los objetos en orden alfabetico.
	 * @param item
	 */
	public void add(Item item) {
		// Se introduce el objeto en la lista general
										add(items,		item);
		// Se introduce el objeto en su lista particular
		if (item instanceof Weapon)		add(weapons,	item);
		else if (item instanceof Armor)	add(armors,		item);
		else if (item instanceof Food)	add(food,		item);
		else							add(miscellany,	item);
		
		// TODO: Eliminar. Esta de prueba
		display();
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos para eliminar objetos:
	
	public void remove(Item item) {
		// Se elimina el objeto de la lista general
		items.remove(item);
		// Se elimina el objeto de su lista particular
		if (item instanceof Weapon)		weapons.remove(item);
		else if (item instanceof Armor)	armors.remove(item);
		else								miscellany.remove(item);
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Otros metodos de utilidad:
	
	public boolean isFull() {
		return weight >= weightCapacity;
	}
	
	private void display() {
		System.out.println("ITEMS:");
		for (Item i : items)		System.out.println("\t- "+ i);
		System.out.println("WEAPONS:");
		for (Item i : weapons)		System.out.println("\t- "+ i);
		System.out.println("ARMORS:");
		for (Item i : armors)		System.out.println("\t- "+ i);
		System.out.println("FOOD:");
		for (Item i : food)			System.out.println("\t- "+ i);
		System.out.println("MISCELLANY:");
		for (Item i : miscellany)	System.out.println("\t- "+ i);
	}

}