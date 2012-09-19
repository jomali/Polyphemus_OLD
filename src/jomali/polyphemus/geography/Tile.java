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

package jomali.polyphemus.geography;

import java.awt.Color;

import jomali.polyphemus.util.SColor;

/**
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 *
 */
public enum Tile {
	
	FLOOR		('.', SColor.LIGHT_GRAY),
	DIRT		('#', SColor.LIGHT_GRAY),
	STONE		('^', SColor.SLATE_GRAY),
	
	STAIRS_DOWN	('>', Color.WHITE),
	STAIRS_UP	('<', Color.WHITE),
	
	BOUNDS		('x', Color.BLACK),
	UNKNOWN		(' ', Color.WHITE);
	
	private char glyph;
	private Color color;
	
	Tile(char glyph, Color color) {
		this.glyph	= glyph;
		this.color = color;
	}
	
	public char glyph() { return glyph; }
	
	public Color color() { return color; }
	
	public boolean isGround() {
		return this != Tile.DIRT && this != Tile.STONE && this != Tile.BOUNDS;
	}
	
	public boolean isDiggable() {
		return this == Tile.DIRT;
	}

}
