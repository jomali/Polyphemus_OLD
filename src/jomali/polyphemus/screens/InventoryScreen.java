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

import jomali.polyphemus.ApplicationMain;
import jomali.polyphemus.entities.Creature;
import jomali.polyphemus.entities.Item;
import jomali.polyphemus.util.RlTerminal;
import jomali.polyphemus.util.SColor;

/**
 * Pantalla de inventario.
 * 
 * <p>Muestra el inventario de objetos de la criatura pasada al constructor. 
 * El jugador puede navegar entre categorias con las teclas IZQUIERDA y 
 * DERECHA, y entre objetos con las teclas ARRIBA y ABAJO. En cada instante 
 * hay un solo objeto del inventario seleccionado (o ninguno, si el inventario 
 * esta vacio), sobre el que se pueden realizar las acciones UTILIZAR 
 * --contextual; la accion concreta lazada dependera del tipo de objeto-- y 
 * DEJAR.
 * 
 * @author J. Francisco Martin
 *
 */
public class InventoryScreen extends Subscreen {
	
	private final String[] categoryNames = new String[] {
			"All", "Weapons", "Apparel", 
			"Consumables", "Readings", "Miscellany", 
	};
	
	/** Referencia a la criatura de la que se toma inventario. */
	private Creature creature;
	
	/** Vector de categorias de objetos no nulas del inventario. */
	private Integer[] categories;
	
	/** Puntero a la categoria mostrada en pantalla */
	private int category;
	
	/** Lista de objetos de la categoria actual */
	private List<Item> items;
	
	/** Puntero al objeto del inventario seleccionado */
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
	
	private void useItem(Item item) {
		// TODO: ...
	}
	
	private void dropItem(Item item) {
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
		 * objetos de la nueva categoria se modifica su valor para que apunte a 
		 * la ultima posicion de la lista.
		 * 
		 * Aqui, la lista de objetos de la nueva categoria se obtiene con 
		 * <code>creature.inventory().getList(categories[category])</code> 
		 * en lugar de hacer uso de <code>items</code> porque el contenido de 
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
		/*
		 * Tres casos:
		 * 1. No se puede mover el puntero.
		 * 2. Se mueve el puntero pero no el cursor (para hacer efecto scroll)
		 * 3. Se mueven tanto el puntero como el cursor
		 */
		if (items.size() == 0) return;
		if (index == 0 && amount < 0) index = items.size() + amount;
		else index += amount;
		index = index % items.size();
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos privados para mostrar los elementos de la pantalla:

	private void displayCategories(RlTerminal terminal, String[] categoryNames, 
			Integer[] categories, int category, int x0, int y0) {
		/*
		 * No se comprueba si el vector "categories" esta o no vacio porque 
		 * "displayOutput()" ya se encarga de comprobar que no lo este antes de 
		 * invocar a "displayCategories()".
		 */
		String str = " "+ category+ " / "+ (categories.length - 1)+ " ";
		str += "- "+ categoryNames[categories[category]]+ " ";
		terminal.write(RlTerminal.TL, str, x0, y0, Color.BLACK, Color.WHITE);
	}
	
	private void displayItems(RlTerminal terminal, List<Item> items, 
			int index, int x0, int y0, int y1) {
		/*
		 * No se comprueba si la lista "items" esta o no vacia porque 
		 * "displayOutput()" ya se encarga de comprobar que no lo este antes de 
		 * invocar a "displayItems()".
		 */
		int offset = Math.max(0, Math.min(index - (y1-y0)/2, items.size() - (y1-y0)));

		ListIterator<Item> it = items.listIterator(offset);
		for (int j=0; j<(y1-y0); j++) {
			if (!it.hasNext()) break;
			Color color = j+offset == index ? SColor.WHITE : SColor.GRAY;
			terminal.write(RlTerminal.TL, it.next().name(), x0, y0+j, color);
		}
		
		Item item = items.get(index);
		String itemLine = item.actionName() == null ? "" : " [E] "+ item.actionName();
		itemLine += " [R] drop ";
		terminal.write(RlTerminal.BL, itemLine, 2, 0, SColor.BLACK, SColor.WHITE);
		
		terminal.write(RlTerminal.BR, index+ " / "+ (items.size()-1), 2, 2);
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void displayOutput(RlTerminal terminal) {
		terminal.cls();
		displayFrame(terminal);
		
		if (categories.length == 0) {
			terminal.write(RlTerminal.TL, "No items to display", 2, 2);
		} 
		else {
			displayCategories(terminal, categoryNames, categories, category, 2, 2);
			displayItems(terminal, items, index, 4, 4, ApplicationMain.HEIGHT-4);
		}
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
		case KeyEvent.VK_E:			useItem(items.get(index)); break;
		// Soltar objeto:
		case KeyEvent.VK_R:			dropItem(items.get(index)); break;
		// Salir de la pantalla:
		case KeyEvent.VK_I:
		case KeyEvent.VK_ESCAPE:	return null;
		}
		
		// Se actualizan "categories" e "items"
		if (!creature.inventory().isEmpty()) {
			categories	= creature.inventory().getCategories();
			items		= creature.inventory().getList(categories[category]);
			
		}
		
		return this;
	}
	
}
