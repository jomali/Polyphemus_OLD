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

import jomali.polyphemus.util.RlTerminal;

/**
 * 
 * @author J. Francisco Martin
 *
 */
public abstract class Panel {
	
	private int xOffset;
	private int yOffset;
	private int width;
	private int height;
	
	public Panel(int xOffset, int yOffset, int width, int height) {
		this.xOffset	= xOffset;
		this.yOffset	= yOffset;
		this.width		= width;
		this.height		= height;
	}
	
	public int xOffset() { return xOffset; }
	
	public int yOffset() { return yOffset; }
	
	public int width() { return width; }
	
	public int height() { return height; }
	
	public void display(RlTerminal terminal) { }

}
