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
import java.util.ArrayList;
import java.util.List;

import jomali.polyphemus.ApplicationMain;
import jomali.polyphemus.entities.Creature;
import jomali.polyphemus.entities.EntityFactory;
import jomali.polyphemus.entities.FieldOfView;
import jomali.polyphemus.geography.World;
import jomali.polyphemus.geography.WorldBuilder;
import jomali.polyphemus.util.SColor;
import jomali.polyphemus.util.RlTerminal;

/**
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 *
 */
public class PlayScreen implements Screen {
	
	private GamePanel gamePanel;
	private StatusPanel statusPanel;
	private Screen subscreen;
	
	private World world;
	private Creature player;
	private List<String> messages;
	private FieldOfView fov;
	
	////////////////////////////////////////////////////////////////////////////
	
	private class GamePanel {
		
		private int width;
		private int height;
		private int xOffset;
		private int yOffset;
		
		GamePanel(int width, int height, int xOffset, int yOffset) {
			this.width		= width;
			this.height		= height;
			this.xOffset	= xOffset;
			this.yOffset	= yOffset;
		}
		
		private int getScrollX() {
			return Math.max(0, Math.min(player.x - width / 2, 
					world.width() - width));
		}
		
		private int getScrollY() {
			return Math.max(0, Math.min(player.y - height / 2,
					world.height() - height));
		}
		
		private void displayMessages(RlTerminal terminal, List<String> messages) {
			int top = (height+yOffset) - messages.size();
			for (int i=0; i<messages.size(); i++) {
				terminal.write(RlTerminal.TL, messages.get(i), xOffset+1, top+i);
			}
			// TODO: Antes de limipiar la lista, los mensajes podrían copiarse a 
			// otra lista independiente (o una lista de listas) de forma que se 
			// preserve un historial de mensajes.
			messages.clear();
		}
		
		private void displayTiles(RlTerminal terminal, int left, int top) {
			fov.update(player.x, player.y, player.z, player.visionRadius());
			
			for (int x = xOffset; x < (width+xOffset); x++) {
				for (int y = yOffset; y < (height+yOffset); y++) {
// TODO:
// left = getScrollX(); [...]
// int wx = x + left - xOffset
// o
// for (int x = 0; ...)
					int wx = x + left;
					int wy = y + top;
					if (player.canSee(wx, wy, player.z))
						terminal.write(RlTerminal.TL, world.glyph(wx, wy, player.z), 
								x, y, world.color(wx, wy, player.z));
					else
						terminal.write(RlTerminal.TL, fov.tile(wx, wy, player.z).glyph(), 
								x, y, SColor.DARK_GRAY);
				}
			}
		}
		
		public void display(RlTerminal terminal) {			
			int left	= getScrollX() - xOffset;
			int top		= getScrollY() - yOffset;
			displayTiles(terminal, left, top);
			displayMessages(terminal, messages);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	private class StatusPanel {
		
		private int width;
		private int height;
		private int xOffset;
		private int yOffset;
		
		StatusPanel(int width, int height, int xOffset, int yOffset) {
			this.width		= width;
			this.height		= height;
			this.xOffset	= xOffset;
			this.yOffset	= yOffset;
		}
		
		private String[] status() {
			String[] result = new String[2];
			result[0] = player.name();
			result[1] = "HP: "+ player.hp()+ "/"+ player.maxHp();
			return result;
		}
		
		private Color[] colors() {
			Color[] result = new Color[2];
			result[0] = player.color();
			result[1] = SColor.WHITE;
			return result;
		}
		
		public void display(RlTerminal terminal) {
			String[] status = status();
			
			for (int i=0; i<status.length; i++) 
				terminal.cls(xOffset+1, yOffset+1+i, width-1, 1, SColor.RED);
			terminal.write(RlTerminal.TL, status(), xOffset+1, yOffset+1, colors());
		}
	}

	////////////////////////////////////////////////////////////////////////////
	
	public PlayScreen() {
		int spw = (ApplicationMain.WIDTH / 4);

		gamePanel = new GamePanel(
				ApplicationMain.WIDTH - (spw) - 1, ApplicationMain.HEIGHT - 2,
				1, 1);
		statusPanel = new StatusPanel(
				(spw) - 2, ApplicationMain.HEIGHT - 2,
				ApplicationMain.WIDTH - (spw) + 1, 1);
		
		world = new WorldBuilder(90, 32, 5).makeCaves().build("Cave");
		messages = new ArrayList<String>();
		fov = new FieldOfView(world);
		
		EntityFactory factory = new EntityFactory(world);
		createCreatures(factory);
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos privados:
	
	private void createCreatures(EntityFactory factory) {
		// Se crea al jugador:
		player = factory.newPlayer("Odysseus", '@', SColor.BLOOD, messages, fov);
	}
	
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
	
	private String worldName() {
		return " "+ world.name().toUpperCase()+ " - Level "+ (player.z+1+ " ");
	}
	
	////////////////////////////////////////////////////////////////////////////

	@Override
	public void displayOutput(RlTerminal terminal) {
		// Dibuja el marco de la pantalla:
		displayFrame(terminal);
		// Dibuja el panel de juego:
		gamePanel.display(terminal);
		// Dibuja el panel de estado:
		statusPanel.display(terminal);
		
		terminal.write(RlTerminal.TL, worldName(), 2, 0, Color.BLACK, Color.WHITE);
		terminal.write(RlTerminal.BL, " Espacio para mensajes ", 2, 0, Color.BLACK, Color.WHITE);
		
		if (subscreen != null) subscreen.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if (subscreen != null) {
			subscreen = subscreen.respondToUserInput(key);
		}
		else {
			switch (key.getKeyCode()) {
			// Movimiento:
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:			player.moveBy(-1, 0, 0); break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:			player.moveBy( 1, 0, 0); break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:			player.moveBy( 0,-1, 0); break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:			player.moveBy( 0, 1, 0); break;
			// Accion principal:
			case KeyEvent.VK_ENTER:
			case KeyEvent.VK_E:			player.acts(); break;
			// Otras pantallas:
			case KeyEvent.VK_ESCAPE:
				subscreen = new MainMenuScreen(); break;
			case KeyEvent.VK_I:
				subscreen = new InventoryScreen(); break;
			case KeyEvent.VK_L:
				subscreen = new LogScreen(); break;
			}
			
			switch (key.getKeyChar()) {
			case '<':					player.moveBy( 0, 0,-1); break;
			case '>':					player.moveBy( 0, 0, 1); break;
			}
		}
		
		return this;
	}
	
}
