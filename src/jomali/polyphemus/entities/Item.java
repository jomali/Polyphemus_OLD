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

/**
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 * 
 * TODO: Queda la posibilidad de annadir un color de fondo a las criaturas 
 * para incrementar su diversidad y/o diferenciacion.
 */
public class Item {
	
	private String name;
	private char glyph;
	private Color color;
	
	// TODO:
	private int attackValue;
	private int defenseValue;
	private int foodValue;
	
	public Item(String name, char glyph, Color color) {
		this.name		= name;
		this.glyph		= glyph;
		this.color		= color;
	}
	
	public String name() { return name; }
	
	public char glyph() { return glyph; }
	
	public Color color() { return color; }
		
	public int attackValue() { return attackValue; }
	
	public int defenseValue() { return defenseValue; }
	
	public int foodValue() { return foodValue; }
	
	public void modifyAttackValue(int amount) { attackValue += amount; }
	
	public void modifyDefenseValue(int amount) { defenseValue += amount; }
	
	public void modifyFoodValue(int amount) { foodValue += amount; }

}