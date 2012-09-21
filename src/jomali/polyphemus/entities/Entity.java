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
 * @author J. Francisco Martin
 *
 */
public abstract class Entity {
	
	private static int nextId = 0;
	
	private int id;
	private String name;
	private char glyph;
	private Color foregroundColor;
	private Color backgroundColor;
	
	public Entity(String name, char glyph, Color foregroundColor, Color backgroundColor) {
		this.id					= nextId;
		nextId++;
		this.name				= name;
		this.glyph				= glyph;
		this.foregroundColor	= foregroundColor;
		this.backgroundColor	= backgroundColor;
	}
	
	public int id() { return id; }
	
	public String name() { return name; }
	
	public char glyph() { return glyph; }
	
	public Color foregroundColor() { return foregroundColor; }
	
	public Color backgroundColor() { return backgroundColor; }
	
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
