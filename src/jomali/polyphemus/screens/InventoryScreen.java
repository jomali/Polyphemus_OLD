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

/**
 * 
 * @author J. Francisco Martin
 *
 */
public class InventoryScreen implements Screen {
	
	private final String[] categoryNames = new String[] {
			"All", "Weapons", "Attire", "Consumables", "Readings", "Miscellany"
	};
	
	private Creature creature;
	private Integer[] categories;
	private int category;
	private List<Item> items;
	private int index;
	
	public InventoryScreen(Creature creature) {
		this.creature	= creature;
		this.categories	= creature.inventory().getCategories();
		this.category	= 0;
		this.items		= creature.inventory().getList(0);
		this.index		= 0;
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos para manipular las categorias:
	
	/**
	 * Desplaza el puntero <code>category</code> en la cantidad indicada.
	 * @param amount valor en que se desplaza el puntero <code>category</code>
	 */
	private void moveCategory(int amount) {
		if (categories.length == 0) return;
		if (category == 0 && amount < 0) category = categories.length + amount;
		else category += amount;
		category = category % categories.length;
		// TODO: actualizar posicion de index al cambiar la categoria
	}
	
	private void displayCategories(RlTerminal terminal, int x0, int y0) {
		String str = "";
		if (categories.length == 0) str += " No categories to display ";
		else {
			str += " "+ category+ " / "+ (categories.length - 1)+ " ";
			str += "- "+ categoryNames[categories[category]]+ " ";
		}
		terminal.write(RlTerminal.TL, str, x0, y0, Color.BLACK, Color.WHITE);
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos para manipular los objetos:
	
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
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	private void displayFrame(RlTerminal terminal) {
		terminal.writeRow(RlTerminal.TL, 0);
		terminal.writeRow(RlTerminal.BL, 0);
		
		terminal.writeCol(RlTerminal.TL, 0);
		terminal.writeCol(RlTerminal.TR, 0);
		
		terminal.write(RlTerminal.TL, " INVENTORY ", 2, 0, Color.BLACK, Color.WHITE);
		terminal.write(RlTerminal.BR, " @h[esc]@r exit ", 2, 0, Color.BLACK, Color.WHITE);
	}
	
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
