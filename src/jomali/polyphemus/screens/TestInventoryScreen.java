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

import jomali.polyphemus.util.RlTerminal;
import jomali.polyphemus.util.SColor;

/**
 * 
 * @author J. Francisco Martin
 *
 */
public class TestInventoryScreen extends Subscreen {
	
	public TestInventoryScreen() { super("INVENTORY"); }

	@Override
	public void displayOutput(RlTerminal terminal) {
		terminal.cls();
		displayFrame(terminal);
		
		String cat = ""; int len = 0;
		len += 2; cat = " ALL ";
		terminal.write(RlTerminal.TL, cat, len, 2, Color.BLACK, Color.WHITE);
		len += cat.length(); cat = " WEAPONS ";
		terminal.write(RlTerminal.TL, cat, len, 2, Color.WHITE, Color.BLACK);
		len += cat.length(); cat = " APPAREL ";
		terminal.write(RlTerminal.TL, cat, len, 2, Color.BLACK, Color.WHITE);
		len += cat.length(); cat = " CONSUMABLES ";
		terminal.write(RlTerminal.TL, cat, len, 2, Color.BLACK, Color.WHITE);
		len += cat.length(); cat = " READINGS ";
		terminal.write(RlTerminal.TL, cat, len, 2, Color.BLACK, Color.WHITE);
		len += cat.length(); cat = " MISCELLANY ";
		terminal.write(RlTerminal.TL, cat, len, 2, Color.BLACK, Color.WHITE);

		String[] objects = new String[] {
				"Equiped: Weapon 5", "Weapon 1", "Weapon 2", 
				"Weapon 3", "Weapon 4", "Weapon 6", 
		};
		Color[] foregroundColors = new Color[] {
				SColor.LIME_GREEN, Color.GRAY, Color.WHITE, 
				Color.GRAY, Color.GRAY, Color.GRAY, 
		};
		for (int i=0; i<objects.length; i++) {
			terminal.write(RlTerminal.TL, objects[i], 4, i+4, foregroundColors[i]);
		}
		terminal.write(RlTerminal.BR, "Gold: 1.000 Capacity: 6/20", 2, 2);
		terminal.write(RlTerminal.BL, " [E] equip [R] drop ", 2, 0, Color.BLACK, Color.WHITE);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) { return this; }
	
}
