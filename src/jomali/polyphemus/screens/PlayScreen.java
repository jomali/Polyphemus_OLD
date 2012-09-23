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
import jomali.polyphemus.entities.CreatureFactory;
import jomali.polyphemus.entities.FieldOfView;
import jomali.polyphemus.entities.Item;
import jomali.polyphemus.entities.ItemFactory;
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
	
	private Panel gamePanel;
	private Panel statusPanel;
	private Screen subscreen;
	
	private World world;
	private Creature player;
	private List<String> messages;
	private FieldOfView fov;
	
	////////////////////////////////////////////////////////////////////////////
	
	private class GamePanel extends Panel {
		
		GamePanel(int xOffset, int yOffset, int width, int height) {
			super(xOffset, yOffset, width, height);
		}
		
		private int getScrollX() {
			return Math.max(0, Math.min(player.x - width() / 2, 
					world.width() - width()));
		}
		
		private int getScrollY() {
			return Math.max(0, Math.min(player.y - height() / 2,
					world.height() - height()));
		}
		
		private void displayMessages(RlTerminal terminal, List<String> messages) {
			int top = (height()+yOffset()) - messages.size();
			for (int i=0; i<messages.size(); i++) {
				terminal.write(RlTerminal.TL, messages.get(i), xOffset()+1, top+i);
			}
			// TODO: Antes de limipiar la lista, los mensajes podrían copiarse a 
			// otra lista independiente (o una lista de listas) de forma que se 
			// preserve un historial de mensajes.
			messages.clear();
		}
		
		private void displayTiles(RlTerminal terminal, int left, int top) {
			fov.update(player.x, player.y, player.z, player.visionRadius());
			
			for (int x = xOffset(); x < (width()+xOffset()); x++) {
				for (int y = yOffset(); y < (height()+yOffset()); y++) {
// TODO:
// left = getScrollX(); [...]
// int wx = x + left - xOffset
// o
// for (int x = 0; ...)
					int wx = x + left;
					int wy = y + top;
					if (player.canSee(wx, wy, player.z))
						terminal.write(RlTerminal.TL, world.glyph(wx, wy, player.z), 
								x, y, world.foregroundColor(wx, wy, player.z));
					else
						terminal.write(RlTerminal.TL, fov.tile(wx, wy, player.z).glyph(), 
								x, y, SColor.DARK_GRAY);
				}
			}
		}
		
		@Override
		public void display(RlTerminal terminal) {			
			int left	= getScrollX() - xOffset();
			int top		= getScrollY() - yOffset();
			displayTiles(terminal, left, top);
			displayMessages(terminal, messages);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	private class StatusPanel extends Panel {
		
		StatusPanel(int xOffset, int yOffset, int width, int height) {
			super(xOffset, yOffset, width, height);
		}
		
		private String[] status() {
			String[] result = new String[4];
			result[0] = player.name();
			result[1] = "HP: "+ player.hp()+ "/"+ player.maxHp();
			result[2] = "AT: "+ player.attackValue();
			result[3] = "DF: "+ player.defenseValue();
			return result;
		}
		
		private Color[] colors() {
			Color[] result = new Color[4];
			result[0] = player.foregroundColor();
			result[1] = SColor.WHITE;
			result[2] = SColor.WHITE;
			result[3] = SColor.WHITE;
			return result;
		}
		
		@Override
		public void display(RlTerminal terminal) {
			String[] status = status();
			
			for (int i=0; i<status.length; i++) 
				terminal.cls(xOffset()+1, yOffset()+1+i, width()-1, 1);
			terminal.write(RlTerminal.TL, status(), xOffset()+1, yOffset()+1, colors());
		}
	}

	////////////////////////////////////////////////////////////////////////////
	
	public PlayScreen() {
		int spw = (ApplicationMain.WIDTH / 3);

		gamePanel = new GamePanel(1, 1,
				ApplicationMain.WIDTH - (spw) - 1, ApplicationMain.HEIGHT - 2);
		statusPanel = new StatusPanel(ApplicationMain.WIDTH - (spw) + 1, 1,
				(spw) - 2, ApplicationMain.HEIGHT - 2);
		
		world	 = new WorldBuilder(90, 32, 5).makeCaves().build("The Cave");
		messages = new ArrayList<String>();
		fov		 = new FieldOfView(world);
		
		createCreatures(new CreatureFactory(world));
		createItems(new ItemFactory(world));
		
		for (int k=0; k<world.depth(); k++) {
			for (int j=0; j<world.height(); j++) {
				for (int i=0; i<world.width(); i++) {
					Creature cr = world.creature(i, j, k);
					Item it = world.item(i, j, k);
					if (cr != null) System.out.println("Creature: "+ cr.id());
					if (it != null) System.out.println("Item: "+ it.id());
				}
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos privados:
	
	private void createCreatures(CreatureFactory factory) {
		// Jugador
		player = factory.newPlayer("Odysseus", '@', SColor.BLOOD, messages, fov);
		for (int z=0; z<world.depth(); z++) {
		// Fungus
			for (int i=0; i<8; i++) factory.newFungus(z);
		// Bat
			for (int i=0; i<20; i++) factory.newBat(z);
		}
	}
	
	private void createItems(ItemFactory factory) {
		for (int z=0; z<world.depth(); z++) {
			for (int i=0; i<world.width()*world.height() / 20; i++) {
				factory.newRock(z);
			}
			factory.newBlackMushroom(z);
			factory.newWhiteMushroom(z);
			factory.randomArmor(z);
			factory.randomWeapon(z);
			factory.randomWeapon(z);
		}
		factory.newVictoryItem(world.depth() - 1);
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
		char horizontalFrame	= (char)177; // 178
		char verticalFrame		= (char)177; // 178
		
		for (int i=0; i<ApplicationMain.WIDTH; i++) {
			terminal.write(RlTerminal.TL, horizontalFrame, i, 0);
			terminal.write(RlTerminal.BL, horizontalFrame, i, 0);
		}
		for (int j=0; j<ApplicationMain.HEIGHT; j++) {
			terminal.write(RlTerminal.TL, verticalFrame, 0, j);
			terminal.write(RlTerminal.TR, verticalFrame, 0, j);
			terminal.write(RlTerminal.TR, verticalFrame, statusPanel.width()+1, j);
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
//		terminal.write(RlTerminal.BL, " Espacio para mensajes ", 2, 0, Color.BLACK, Color.WHITE);
		
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
		
		if (subscreen == null) world.update();
		
		if (player.hp() < 1) return new LoseScreen();
		
		return this;
	}
	
}
