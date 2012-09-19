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

/**
 * 
 * @author J. Francisco Martin
 *
 */
public class TestScreen1 implements Screen {

	private int prueba	= 0;
	
	private char test1	= 'x';
	private String test2 = "Hola mundo";
	private String[] test3 = new String[] {
			"\"Then we began to turn our glances",
			"to the land of the Kyklopes tribe nearby;",
			"we could see smoke and hear voices",
			"and the bleating of sheep and goats...",
			"When we reached the stretch of land I spoke of",
			"--it was not far away--",
			"there on the shore beside the sea we saw a high cave",
			"overarched with bay-trees.\""
	};
	
	private void testChars(RlTerminal terminal) {
		terminal.write(RlTerminal.TL, test1, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.TR, test1, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.TC, test1, 0, 0, Color.YELLOW);
		
		terminal.write(RlTerminal.BL, test1, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.BR, test1, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.BC, test1, 0, 0, Color.YELLOW);
		
		terminal.write(RlTerminal.ML, test1, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.MR, test1, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.MC, test1, 0, 0, Color.YELLOW);
	}
	
	private void testStrings(RlTerminal terminal) {
		terminal.write(RlTerminal.TL, test2, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.TR, test2, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.TC, test2, 0, 0, Color.YELLOW);
		
		terminal.write(RlTerminal.BL, test2, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.BR, test2, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.BC, test2, 0, 0, Color.YELLOW);
		
		terminal.write(RlTerminal.ML, test2, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.MR, test2, 0, 0, Color.YELLOW);
		terminal.write(RlTerminal.MC, test2, 0, 0, Color.YELLOW);
	}
	
	private void testMatrices01(RlTerminal terminal) { 
		terminal.write(RlTerminal.TL, test3, 0, 0, Color.YELLOW); }
	private void testMatrices02(RlTerminal terminal) { 
		terminal.write(RlTerminal.TR, test3, 0, 0, Color.YELLOW); }
	private void testMatrices03(RlTerminal terminal) { 
		terminal.write(RlTerminal.TC, test3, 0, 0, Color.YELLOW); }
	
	private void testMatrices04(RlTerminal terminal) { 
		terminal.write(RlTerminal.BL, test3, 0, 0, Color.YELLOW); }
	private void testMatrices05(RlTerminal terminal) { 
		terminal.write(RlTerminal.BR, test3, 0, 0, Color.YELLOW); }
	private void testMatrices06(RlTerminal terminal) { 
		terminal.write(RlTerminal.BC, test3, 0, 0, Color.YELLOW); }
	
	private void testMatrices07(RlTerminal terminal) { 
		terminal.write(RlTerminal.ML, test3, 0, 0, Color.YELLOW); }
	private void testMatrices08(RlTerminal terminal) { 
		terminal.write(RlTerminal.MR, test3, 0, 0, Color.YELLOW); }
	private void testMatrices09(RlTerminal terminal) { 
		terminal.write(RlTerminal.MC, test3, 0, 0, Color.YELLOW); }
	
	@Override
	public void displayOutput(RlTerminal terminal) {
		switch (prueba) {
		case 0: testChars(terminal); break;
		case 1: testStrings(terminal); break;
		case 2: testMatrices01(terminal); break;
		case 3: testMatrices02(terminal); break;
		case 4: testMatrices03(terminal); break;
		case 5: testMatrices04(terminal); break;
		case 6: testMatrices05(terminal); break;
		case 7: testMatrices06(terminal); break;
		case 8: testMatrices07(terminal); break;
		case 9: testMatrices08(terminal); break;
		case 10: testMatrices09(terminal); break;
		}
		String[] pruebas = new String[] {
				"PRUEBA DE CARACTERES", 
				"PRUEBA DE CADENAS", 
				"PRUEBA DE MATRICES 1",
				"PRUEBA DE MATRICES 2",
				"PRUEBA DE MATRICES 3",
				"PRUEBA DE MATRICES 4",
				"PRUEBA DE MATRICES 5",
				"PRUEBA DE MATRICES 6",
				"PRUEBA DE MATRICES 7",
				"PRUEBA DE MATRICES 8",
				"PRUEBA DE MATRICES 9"
		};
		terminal.write(RlTerminal.TL, pruebas[prueba], 1, 1);
		terminal.write(RlTerminal.TR, "Pulsa ENTER para cambiar de prueba", 1, 1);
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_ENTER) prueba = (prueba+1) % 11;
		test1 = key.getKeyChar();
		return this;
	}

}
