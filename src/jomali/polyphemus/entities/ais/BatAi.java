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

/**
 * 
 * @author Trystan Spangler
 *
 */
public class BatAi extends CreatureAi {

	public BatAi(Creature creature) {
		super(creature);
	}
	
	// TODO: We could set up a system for dealing with different monster 
	// speeds(*) but this is simple enough: bats move twice for every one of 
	// your moves. Easy to implement, easy to understand.
	// (*).- http://roguebasin.roguelikedevelopment.org/index.php/Articles#Time_management	
	@Override
	public void onUpdate() {
		wander();
		wander();
	}
}
