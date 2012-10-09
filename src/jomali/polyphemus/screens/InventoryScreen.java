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

package jomali.polyphemus.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ListIterator;

import jomali.polyphemus.entities.Creature;
import jomali.polyphemus.entities.Item;
import jomali.polyphemus.util.RlTerminal;
import jomali.polyphemus.util.SColor;

/**
 * 
 * @author J. Francisco Martin
 *
 */
public class InventoryScreen extends Subscreen {
	
	private final String[] categoryNames = new String[] {
			"All", "Weapons", "Apparel", 
			"Consumables", "Readings", "Miscellany", 
	};
	
	private Creature creature;
	
	private Integer[] categories;
	private int category;
	private List<Item> items;
	private int index;
	
	public InventoryScreen(Creature creature) {
		super("INVENTORY");
		this.creature	= creature;
		this.categories	= creature.inventory().getCategories();
		this.category	= 0;
		this.items		= creature.inventory().getList(0); // List: ALL
		this.index		= 0;
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos privados para manipular los objetos del inventario:

	// TODO: Estudiar la opcion de invocar al metodo de uso concreto del 
	// objeto dado, en lugar del generico use(); es decir, invocar a equip() 
	// para armas o armaduras, eat() para comestibles, read() para textos, etc.
	private void useItem() {
		if (items.size() == 0) return;
		creature.use(items.get(index));
		System.out.println("Use "+ items.get(index));
	}
	
	private void dropItem() {
		// TODO: ...
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos privados para desplazarse por el inventario:
	
	/**
	 * Desplaza el puntero <code>category</code> en la cantidad indicada.
	 * @param amount valor en que se desplaza el puntero <code>category</code>
	 */
	private void moveCategory(int amount) {
		if (categories.length == 0) return;
		if (category == 0 && amount < 0) category = categories.length + amount;
		else category += amount;
		category = category % categories.length;
		
		/*
		 * Si <code>index</code> queda fuera de los limites de la lista de 
		 * objetos de la nueva categoria se modifica su valor para que apunte 
		 * a la ultima posicion de la lista.
		 * 
		 * En esta ocasion, la lista de objetos de la nueva categoria se saca 
		 * con <code>creature.inventory().getList(categories[category])</code> 
		 * en lugar de utilizar <code>items</code> porque el contenido de 
		 * <code>items</code> solo se actualiza una vez termina de ejecutarse 
		 * la accion lanzada por el usuario.
		 */
		
		if (index >= creature.inventory().getList(categories[category]).size())
			index = creature.inventory().getList(categories[category]).size()-1;
	}
	
	/**
	 * Desplaza el puntero <code>index</code> en la cantidad indicada.
	 * @param amount valor en que se desplaza el puntero <code>index</code>
	 */
	private void moveIndex(int amount) {
		if (items.size() == 0) return;
		if (index == 0 && amount < 0) index = items.size() + amount;
		else index += amount;
		index = index % items.size();
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos privados para mostrar los elementos de la pantalla:

	private void displayCategories(RlTerminal terminal, int x0, int y0) {
		String str = "";
		if (categories.length == 0) str += " No categories to display ";
		else {
			str += " "+ category+ " / "+ (categories.length - 1)+ " ";
			str += "- "+ categoryNames[categories[category]]+ " ";
		}
		terminal.write(RlTerminal.TL, str, x0, y0, Color.BLACK, Color.WHITE);
	}
	
	private void displayItems(RlTerminal terminal, int x0, int y0, int y1) {
		String str = "";
		if (items.size() == 0) str += " No items to display ";
		else str += index+ " / "+ (items.size() - 1);
		terminal.write(RlTerminal.TL, str, x0, y0);
		
		ListIterator<Item> it = items.listIterator();
		int i = 0;
		while (it.hasNext()) {
			terminal.write(RlTerminal.TL, it.next().name(), x0, y0+2+i);
			i++;
		}
		
		if (items.size() != 0) {
			Item item = items.get(index);
			String itemLine = item.actionName() == null ? "" : " [E] "+ item.actionName();
			itemLine += " [R] drop ";
			terminal.write(RlTerminal.BL, itemLine, 2, 0, SColor.BLACK, SColor.WHITE);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void displayOutput(RlTerminal terminal) {
		terminal.cls();
		displayFrame(terminal);
		displayCategories(terminal, 2, 2);
		displayItems(terminal, 4, 4, 20);
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
		// Movimiento entre categorias:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:			moveCategory(-1); break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:			moveCategory( 1); break;
		// Movimiento entre objetos:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:			moveIndex(-1); break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:			moveIndex( 1); break;
		// Usar objeto:
		case KeyEvent.VK_E:			useItem(); break;
		// Soltar objeto:
		case KeyEvent.VK_R:			dropItem(); break;
		// Salir de la pantalla:
		case KeyEvent.VK_I:
		case KeyEvent.VK_ESCAPE:	return null;
		}
		
		if (!creature.inventory().isEmpty()) {
			categories	= creature.inventory().getCategories();
			items		= creature.inventory().getList(categories[category]);
			
		}
		
		return this;
	}
	
}
