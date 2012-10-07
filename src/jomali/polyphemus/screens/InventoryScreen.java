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
import jomali.polyphemus.entities.Inventory;
import jomali.polyphemus.entities.Item;
import jomali.polyphemus.util.RlTerminal;

/**
 * 
 * @author J. Francisco Martin
 *
 */
public class InventoryScreen implements Screen {
	
	private final String[] categoryNames = new String[] {
			"All", "Weapons", "Apparel", "Consumables", "Readings", "Miscellany"
	};
	
	private Inventory inventory;
	private int category;
	private int index;
	private int cursor;
	
	public InventoryScreen(Creature creature) {
		this.inventory	= creature.inventory();
		this.category	= 0;
		this.index		= 0;
		this.cursor		= 0;
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos para manipular la pantalla y el inventario:
	
	/**
	 * Desplaza el puntero de la categoria de objetos en la cantidad 
	 * <code>amount</code> indicada.
	 * @param amount valor en que se desplaza al puntero de categoria
	 */
	private void moveCategory(int amount) {
		if (inventory.numberOfCategories() == 0) return;
		if (category==0 && amount<0) category = inventory.numberOfCategories()+amount;
		else category += amount;
		category = category % inventory.numberOfCategories();
		if (inventory.get(category).size() > 0 && index >= inventory.get(category).size()) 
			index = inventory.get(category).size()-1;
	}
	
	/**
	 * Desplaza el puntero de la lista de objetos en la cantidad 
	 * <code>amount</code> indicada.
	 * @param amount valor en que se desplaza al puntero de lista
	 */
	private void moveIndex(int amount) {
		if (inventory.get(category).size() == 0) return;
		if (index==0 && amount<0) index = inventory.get(category).size()+amount;
		else index += amount;
		index = index % inventory.get(category).size();
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos para dibujar la pantalla:
	
	private void displayFrame(RlTerminal terminal) {
		terminal.writeRow(RlTerminal.TL, 0);
		terminal.writeRow(RlTerminal.BL, 0);
		
		terminal.writeCol(RlTerminal.TL, 0);
		terminal.writeCol(RlTerminal.TR, 0);
		
		terminal.write(RlTerminal.TL, " INVENTORY ", 2, 0, Color.BLACK, Color.WHITE);
		terminal.write(RlTerminal.BR, " @h[esc]@r exit ", 2, 0, Color.BLACK, Color.WHITE);
	}
	
	private void displayCategories(RlTerminal terminal, int xOffset, int yOffset) {
		String str;
		if (inventory.numberOfCategories() == 0)  str = " No categories to display ";
		else str = " "+ category+ " / "+ (inventory.numberOfCategories()-1)+ " ";
		str += "- "+ categoryNames[category]+ " ";
		terminal.write(RlTerminal.TL, str, xOffset, yOffset, Color.BLACK, Color.WHITE);
	}
	
	private void displayItems(RlTerminal terminal, int xOffset, int yOffset) {
		String str;
		if (inventory.get(category).size() == 0) str = "No items to display.";
		else str = index+ " / "+ (inventory.get(category).size()-1);
		terminal.write(RlTerminal.TL, str, xOffset, yOffset);
		
		ListIterator<Item> it = inventory.get(category).listIterator();
		int i = 0;
		while (it.hasNext()) {
			terminal.write(RlTerminal.TL, it.next().name(), 4, 6+i);
			i++;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////

	@Override
	public void displayOutput(RlTerminal terminal) {
		terminal.cls();
		displayFrame(terminal);
		displayCategories(terminal, 2, 2);
		displayItems(terminal, 4, 4);
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
		
		return this;		
	}
	
}
