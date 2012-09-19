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

import jomali.polyphemus.geography.Line;
import jomali.polyphemus.geography.Point;
import jomali.polyphemus.geography.Tile;

/**
 * 
 * @author Trystan Spangler
 *
 */
public abstract class CreatureAi {
	
	protected Creature creature;
	
	public CreatureAi(Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}
	
	public void onEnter(int x, int y, int z, Tile tile) {
		if (tile.isGround()) {
			creature.x = x;
			creature.y = y;
			creature.z = z;
		}
		else {
			creature.doAction("bump into a wall");
		}
	}
	
	public void onUpdate() { }
	
	public void onNotify(String message) { }
	
	// TODO: Incompleto
	public boolean canSee(int wx, int wy, int wz) {
		if (creature.z != wz)
			return false;

		if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) 
				> creature.visionRadius()*creature.visionRadius())
			return false;
		
		for (Point p : new Line(creature.x, creature.y, wx, wy)) {
			if (creature.tile(p.x, p.y, p.z).isGround() || (p.x == wx && p.y == wy))
				continue;
			return false;
		}
		
		return true;
	}
	
	// TODO: Estudiar posibilidad de impedir movimientos diagonales
	public void wander() {
		int mx = (int)(Math.random() * 3) - 1;
		int my = (int)(Math.random() * 3) - 1;
		
		Creature other = creature.creature(creature.x, creature.y, creature.z);

		if (other != null && other.name().equals(creature.name()) 
				|| !creature.tile(creature.x+mx, creature.y+my, creature.z).isGround())
			return;
		else
			creature.moveBy(mx, my, 0);
	}

}
