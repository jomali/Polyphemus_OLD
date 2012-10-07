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

import jomali.polyphemus.entities.Creature;
import jomali.polyphemus.util.RlTerminal;

/**
 * 
 * @author J. Francisco Martin
 *
 */
public class InventoryScreen implements Screen {
	
	private Creature creature;
	private int category;
	private int index;
	private int cursor;
	
	public InventoryScreen(Creature creature) {
		this.creature	= creature;
		this.category	= 0;
		this.index		= 0;
		this.cursor		= 0;
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos para manipular la pantalla y el inventario:
	
	private void moveCategory(int amount) {
		if (category == 0 && amount < 0) {
			category = creature.inventory().numberOfCategories()-1;
			amount++;
		}
		category = (category + amount) % creature.inventory().numberOfCategories();
	}
	
	private void moveIndex(int amount) {
		if (index == 0 && amount < 0) {
			index = creature.inventory().size()-1;
			amount++;
		}
		index = (index + amount) % creature.inventory().size();
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos para dibujar la pantalla:
	
	private void displayFrame(RlTerminal terminal) {
		terminal.writeRow(RlTerminal.TL, 0);
		terminal.writeRow(RlTerminal.BL, 0);
		
		terminal.writeCol(RlTerminal.TL, 0);
		terminal.writeCol(RlTerminal.TR, 0);
		
		terminal.write(RlTerminal.TL, " INVENTORY ", 2, 0, Color.BLACK, Color.WHITE);
		terminal.write(RlTerminal.BR, " @s[esc]@n exit ", 2, 0, Color.BLACK, Color.WHITE);
	}
	
	private void displayCategories(RlTerminal terminal, int xOffset, int yOffset) {
		String cat = (category+1)+ " / "+ (creature.inventory().numberOfCategories());
		terminal.write(RlTerminal.TL, cat, xOffset, yOffset);
	}
	
	private void displayItems(RlTerminal terminal, int xOffset, int yOffset) {
		String it = (index+1)+ " / "+ (creature.inventory().size());
		terminal.write(RlTerminal.TL, it, xOffset, yOffset);
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
		case KeyEvent.VK_ESCAPE:	return null;
		}
		
		return this;		
	}
	
}
