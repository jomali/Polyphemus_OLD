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

package jomali.polyphemus.entities.items;

import java.awt.Color;

import jomali.polyphemus.entities.Item;

/**
 * 
 * @author J. Francisco Martin
 *
 */
public class Consumable extends Item {
	
	private int foodValue;
	
	public Consumable(String name, char glyph, Color foregroundColor, Color backgroundColor, 
			int foodValue) {
		super(name, glyph, foregroundColor, backgroundColor);
		this.foodValue = foodValue;
	}
	
	public Consumable(String name, char glyph, Color foregroundColor, Color backgroundColor) {
		super(name, glyph, foregroundColor, backgroundColor);
		this.foodValue = 0;
	}
	
	public int foodValue() { return foodValue; }
	
	public void setFoodValue(int foodValue) { this.foodValue = foodValue; }
	
	public void modifyFoodValue(int amount) { foodValue += amount; }

}
