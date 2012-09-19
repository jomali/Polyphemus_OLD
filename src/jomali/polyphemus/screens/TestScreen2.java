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

import jomali.polyphemus.ApplicationMain;
import jomali.polyphemus.util.RlTerminal;

/**
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 *
 */
public class TestScreen2 implements Screen {
	
	private Panel gamePanel;
	private Panel statusPanel;
	private char input;
	
	////////////////////////////////////////////////////////////////////////////
	
	private class Panel {
		
		private int width;
		private int height;
		private int xOffset;
		private int yOffset;
		private Color color;
		
		Panel(int width, int height, int xOffset, int yOffset, Color color) {
			this.width		= width;
			this.height		= height;
			this.xOffset	= xOffset;
			this.yOffset	= yOffset;
			this.color		= color;
		}
		
		public void display(RlTerminal terminal) {
			terminal.cls(xOffset, yOffset, width, height, color);
			terminal.write(RlTerminal.TL, 'x', xOffset, yOffset, Color.GREEN, Color.BLACK);
			terminal.write(RlTerminal.TL, 'x', xOffset+width-1, yOffset, Color.RED, Color.BLACK);
			terminal.write(RlTerminal.TL, 'x', xOffset, yOffset+height-1, Color.GREEN, Color.BLACK);
			terminal.write(RlTerminal.TL, 'x', xOffset+width-1, yOffset+height-1, Color.RED, Color.BLACK);
		}
	}

	////////////////////////////////////////////////////////////////////////////
	
	public TestScreen2() {

		gamePanel = new Panel(
				ApplicationMain.WIDTH - (ApplicationMain.WIDTH / 3) - 1,
				ApplicationMain.HEIGHT - 2,
				1, 1,
				Color.YELLOW);
		statusPanel = new Panel(
				(ApplicationMain.WIDTH / 3) - 2,
				ApplicationMain.HEIGHT - 2,
				ApplicationMain.WIDTH - (ApplicationMain.WIDTH / 3) + 1, 1, 
				Color.BLUE);
		
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos privados:
	
	private void displayFrame(RlTerminal terminal) {
/*
		char horizontalFrame	= (char)205;
		char verticalFrame		= (char)186;		
		char topLeftCorner		= (char)201;
		char topRightCorner		= (char)187;
		char topCenterCorner	= (char)203;
		char bottomLeftCorner	= (char)200;
		char bottomRightCorner	= (char)188;
		char bottomCenterCorner	= (char)202;
*/
		char horizontalFrame	= (char)178;
		char verticalFrame		= (char)178;
		
		for (int i=0; i<ApplicationMain.WIDTH; i++) {
			terminal.write(RlTerminal.TL, horizontalFrame, i, 0);
			terminal.write(RlTerminal.BL, horizontalFrame, i, 0);
		}
		for (int j=0; j<ApplicationMain.HEIGHT; j++) {
			terminal.write(RlTerminal.TL, verticalFrame, 0, j);
			terminal.write(RlTerminal.TR, verticalFrame, 0, j);
			terminal.write(RlTerminal.TR, verticalFrame, statusPanel.width+1, j);
		}		
	}
	
	private String inputToString() {
		String result = ""+input;
		switch (input) {
		case 'a':	result = " Left ";			break;
		case 'd':	result = " Right ";			break;
		case 'w':	result = " Up ";			break;
		case 's':	result = " Down ";			break;
		case 'e':	result = " Main action ";	break;
		case '_':	result = " Main menu ";		break;
		case 'i':	result = " Inventory ";		break;
		case 'l':	result = " Log ";			break;
		default:	result = "";				break;
		}
		return result;
	}
	
	////////////////////////////////////////////////////////////////////////////

	@Override
	public void displayOutput(RlTerminal terminal) {
		displayFrame(terminal);
		gamePanel.display(terminal);
		statusPanel.display(terminal);
		
		String[] controls = new String[] {
				"- Movement: W/A/S/D /directional keys", 
				"- Main action: E/ENTER", 
				"- Main menu: ESC", 
				"- Inventory: I", 
				"- Log: L", 
		};
		
		terminal.write(RlTerminal.TL, " CONTROLS ", 2, 0, Color.BLACK, Color.WHITE);
		terminal.write(RlTerminal.TL, controls, 2, 2);
		terminal.write(RlTerminal.BL, inputToString(), 2, 0, Color.BLACK, Color.WHITE);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
		// Movimiento:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:			input = 'a'; break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:			input = 'd'; break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:			input = 'w'; break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:			input = 's'; break;
		// Accion principal:
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_E:			input = 'e'; break;
		// Otras pantallas:
		case KeyEvent.VK_ESCAPE:	input = '_'; break;
		case KeyEvent.VK_I:			input = 'i'; break;
		case KeyEvent.VK_L:			input = 'l'; break;
		//
		default:					input = key.getKeyChar(); break;
		}
		return this;
	}
	
}
