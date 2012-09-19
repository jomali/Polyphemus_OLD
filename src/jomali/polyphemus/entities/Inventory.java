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
	
	private Item[] items;
	
	public Inventory(int max) {
		items = new Item[max];
	}
	
	public Item[] getItems() { return items; }
	
	public Item get(int i) { return items[i]; }
	
	public void add(Item item) {
		for (int i=0; i<items.length; i++) {
			if (items[i] == null) {
				items[i] = item;
				break;
			}
		}
	}
	
	public void remove(Item item) {
		for (int i=0; i<items.length; i++) {
			if (items[i] == item) {
				items[i] = null;
				break;
			}
		}
	}
	
	public boolean isFull() {
		int size = 0;
		for (int i=0; i<items.length; i++) {
			if (items[i] != null) size++;
		}
		return size == items.length;
	}

}
