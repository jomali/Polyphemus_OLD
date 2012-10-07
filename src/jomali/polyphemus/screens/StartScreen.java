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

import java.awt.event.KeyEvent;

import jomali.polyphemus.ApplicationMain;
import jomali.polyphemus.util.RlTerminal;
import jomali.polyphemus.util.SColor;

/**
 * Primera pantalla con la que se encontraran los usuarios. Muestra algo de 
 * informacion y lanza la pantalla de juego cuando el jugador pulsa ENTER.
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 *
 */

public class StartScreen implements Screen {
	
	@Override
	public void displayOutput(RlTerminal terminal) {
				
		String title = "=== "+ ApplicationMain.NAME.toUpperCase()+ " ===";
		String[] introduction = new String[] {
				"\"Then we began to turn our glances",
				"to the land of the Kyklopes tribe nearby;",
				"we could see smoke and hear voices",
				"and the bleating of sheep and goats...",
				"When we reached the stretch of land I spoke of",
				"--it was not far away--",
				"there on the shore beside the sea",
				"we saw a high CAVE overarched with bay-trees.\"",
				"", 
				"Homer, Odyssey 9.187 - 542",
		};
		
		terminal.write(RlTerminal.TC, title, 0, 1);
		terminal.write(RlTerminal.MC, introduction, 0, 0, SColor.BLOOD);
		terminal.write(RlTerminal.BC, "[ Press @hENTER@r to continue ]", 0, 1);
		
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}

}
