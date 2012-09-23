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

package jomali.polyphemus.entities;

import java.awt.Color;
import java.util.List;

import jomali.polyphemus.entities.ais.BatAi;
import jomali.polyphemus.entities.ais.FungusAi;
import jomali.polyphemus.entities.ais.PlayerAi;
import jomali.polyphemus.geography.World;
import jomali.polyphemus.util.SColor;

public class CreatureFactory {
	
	private World world;
	
	public CreatureFactory(World world) {
		this.world = world;
	}
	
	public Creature newPlayer(String name, char glyph, Color color, 
			List<String> messages, FieldOfView fov) {
		Creature creature = new Creature(name, glyph, color, null, 
				world, 100, 20, 5);
		world.addAtEmptyLocation(creature, 0);
		new PlayerAi(creature, messages, fov);
		return creature;
	}
	
	public Creature newPlayer(String name, List<String> messages, FieldOfView fov) {
		Creature creature = new Creature(name, '@', SColor.WHITE, null, 
				world, 100, 20, 5);
		world.addAtEmptyLocation(creature, 0);
		new PlayerAi(creature, messages, fov);
		return creature;
	}
	
	public Creature newFungus(int depth) {
		Creature creature = new Creature("Fungus", 'f', SColor.GREEN_BAMBOO, null, 
				world, 10, 0, 0);
		world.addAtEmptyLocation(creature, depth);
		new FungusAi(creature, 5, this);
		return creature;
	}
	
	public Creature newBat(int depth) {
		Creature creature = new Creature("Bat", 'b', SColor.PALE_MAGENTA, null, 
				world, 15, 5, 0);
		world.addAtEmptyLocation(creature, depth);
		new BatAi(creature);
		return creature;
	}

}
