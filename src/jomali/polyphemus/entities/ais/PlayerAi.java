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

package jomali.polyphemus.entities.ais;

import java.util.List;

import jomali.polyphemus.entities.Creature;
import jomali.polyphemus.entities.CreatureAi;
import jomali.polyphemus.entities.FieldOfView;
import jomali.polyphemus.geography.Tile;

/**
 * 
 * @author Trystan Spangler
 *
 */
public class PlayerAi extends CreatureAi {
	
	private List<String> messages;
	private FieldOfView fov;
	
	public PlayerAi(Creature creature, List<String> messages, FieldOfView fov) {
		super(creature);
		this.messages = messages;
		this.fov = fov;
	}

	/**
	 * TODO: 
	 * 
	 * We need to override the onEnter method to dig through walls and walk on 
	 * ground tiles. If your world has doors then you can make the player 
	 * automatically open them by walking into them with code very similar to 
	 * this. Instead of checking the tile type directly we just ask if it can 
	 * be walked on or dug through like with isDiggable.
	 */
	@Override
	public void onEnter(int x, int y, int z, Tile tile) {
		if (tile.isGround()) {
			creature.x = x;
			creature.y = y;
			creature.z = z;
		}
		else if (tile.isDiggable()) {
			creature.dig(x, y, z);
		}
	}
	
	@Override
	public void onNotify(String message) {
		messages.add(message);
	}
	
	@Override
	public boolean canSee(int wx, int wy, int wz) {
		return fov.isVisible(wx, wy, wz);
	}

}
