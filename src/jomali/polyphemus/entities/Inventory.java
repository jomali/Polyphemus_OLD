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

import jomali.polyphemus.entities.items.Attire;
import jomali.polyphemus.entities.items.Consumable;
import jomali.polyphemus.entities.items.Readable;
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
	
	private int capacity;	// Capacidad del inv. (en peso, # uds., u otros)
	private int counter;	
	
	private List<Item> items;
	private List<Item> weapons;
	private List<Item> apparel;
	private List<Item> consumables;
	private List<Item> readings;
	private List<Item> miscellany;
		
	public Inventory(int capacity) {
		this.capacity		= capacity;
		this.counter		= 0;
		// Se inicializan las listas de objetos
		this.items			= new LinkedList<Item>();
		this.weapons		= new LinkedList<Item>();
		this.apparel		= new LinkedList<Item>();
		this.consumables	= new LinkedList<Item>();
		this.readings		= new LinkedList<Item>();
		this.miscellany		= new LinkedList<Item>();	
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos de acceso a las listas del inventario:
	
	public List<Item> getItems() { return items; }
	
	public List<Item> getWeapons() { return weapons; }
	
	public List<Item> getApparel() { return apparel; }
	
	public List<Item> getConsumables() { return consumables; }
	
	public List<Item> getReadings() { return readings; }
	
	public List<Item> getMiscellany() { return miscellany; }
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos para introducir objetos:
	
	/**
	 * Introduce el elemento <code>item</code> en la posicion que le 
	 * corresponda por orden alfabetico en la lista <code>list</code>.
	 * 
	 * <p>En el peor de los casos --la posicion del nuevo elemento se encuentra 
	 * al final de la lista--, se tienen que recorrer todos los elementos antes 
	 * de introducir el nuevo. Hay algoritmos de busqueda en listas ordenadas, 
	 * como el de busqueda binaria, que en otras circunstancias podrian 
	 * permitir dar con la posicion del nuevo elemento sin tener que recorrer 
	 * la lista por completo. Sin embargo, el inventario utiliza estructuras 
	 * <code>LinkedList</code> para las listas de objetos, en las que para 
	 * acceder a un elemento n antes hay que recorrer todos los elementos 
	 * anteriores a el, de forma que utilizar uno de estos algoritmos no 
	 * supondria una mejora de eficiencia (posiblemente al contrario).
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
	 * Introduce el elemento <code>item</code> en el inventario.
	 * @param item, elemento a annadir al inventario
	 */
	public void add(Item item) {
		if (isFull()) return;
		counter ++;
		// Se introduce el objeto en la lista general
											 add(items,			item);
		// Se introduce el objeto en su lista particular
		if (item instanceof Weapon)			 add(weapons,		item);
		else if (item instanceof Attire)	 add(apparel,		item);
		else if (item instanceof Consumable) add(consumables,	item);
		else if (item instanceof Readable)	 add(readings,		item);
		else								 add(miscellany,	item);
		
		// TODO: Eliminar. Esta de prueba
		display();
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos para eliminar objetos:
	
	public void remove(Item item) {
		// TODO: Implementar
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Otros metodos de utilidad:
	
	public boolean isFull() {
		if (capacity < 0) return false;
		return counter >= capacity;
	}
	
	
	
	
	
	// TODO: eliminar
	private void display() {
		System.out.println("ITEMS:");
		for (Item i : items)		System.out.println("\t- "+ i);
		System.out.println("WEAPONS:");
		for (Item i : weapons)		System.out.println("\t- "+ i);
		System.out.println("APPAREL:");
		for (Item i : apparel)		System.out.println("\t- "+ i);
		System.out.println("CONSUMABLES:");
		for (Item i : consumables)	System.out.println("\t- "+ i);
		System.out.println("READINGS:");
		for (Item i : readings)		System.out.println("\t- "+ i);
		System.out.println("MISCELLANY:");
		for (Item i : miscellany)	System.out.println("\t- "+ i);
	}

}