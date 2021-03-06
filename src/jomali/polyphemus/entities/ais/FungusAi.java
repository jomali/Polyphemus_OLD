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

import jomali.polyphemus.entities.Creature;
import jomali.polyphemus.entities.CreatureAi;
import jomali.polyphemus.entities.CreatureFactory;
import jomali.polyphemus.util.SColor;

/**
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 *
 */
public class FungusAi extends CreatureAi {
	
	private CreatureFactory factory;
	private int spreadcountLimit;
	private int spreadcount;
	
	public FungusAi(Creature creature, int spreadcountLimit, CreatureFactory factory) {
		super(creature);
		this.spreadcountLimit	= spreadcountLimit;
		this.spreadcount		= 0;
		this.factory			= factory;
	}
	
	@Override
	public void onUpdate() {
		if ((spreadcount < spreadcountLimit) && (Math.random() < 0.01)) {
			spread();
		}
	}
	
	private void spread() {
		int x = creature.x + (int)(Math.random() * 11) - 5;
		int y = creature.y + (int)(Math.random() * 11) - 5;
		if (!creature.canEnter(x, y, creature.z)) return;
		
		creature.doAction("spawn a child");
		Creature child = factory.newFungus(creature.z);
		child.x = x;
		child.y = y;
		child.z = creature.z;
		spreadcount++;
		if (spreadcount >= spreadcountLimit) {
			creature.doAction("dry up");
			creature.setForegroundColor(SColor.CAMO_GREEN);
		}
	}

}
