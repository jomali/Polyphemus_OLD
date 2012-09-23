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

package jomali.polyphemus.util;

import java.awt.Color;

/**
 * 
 * @author J. Francisco Martin
 *
 */
public class RlTerminal extends AsciiPanelAdequation {
	private static final long serialVersionUID = -3145507684118806424L;
	
	private static final int DEFAULT_WIDTH		= 80;
	private static final int DEFAULT_HEIGHT		= 24;
	private static final Color DEFAULT_FGCOLOR	= Color.WHITE;
	private static final Color DEFAULT_BGCOLOR	= Color.BLACK;
	
	public static final int TOP_LEFT				= 0;
	public static final int TOP_RIGHT				= 1;
	public static final int TOP_CENTER			= 2;
	public static final int BOTTOM_LEFT			= 3;
	public static final int BOTTOM_RIGHT			= 4;
	public static final int BOTTOM_CENTER			= 5;
	public static final int MIDDLE_LEFT			= 6;
	public static final int MIDDLE_RIGHT			= 7;
	public static final int MIDDLE_CENTER			= 8;
	
	public static final int TL	= 0;
	public static final int TR	= 1;
	public static final int TC	= 2;
	public static final int BL	= 3;
	public static final int BR	= 4;
	public static final int BC	= 5;
	public static final int ML	= 6;
	public static final int MR	= 7;
	public static final int MC	= 8;
	
	/**
	 * Constructor. Crea una nueva terminal <code>RlTerminal</code> con las 
	 * dimensiones y los colores de frente y fondo por defecto indicados.
	 * @param width ancho del nuevo <code>RlTerminal</code> (num. de celdas)
	 * @param height alto del nuevo <code>RlTerminal</code> (num. de celdas)
	 * @param foregroundColor color de frente por defecto del nuevo terminal
	 * @param backgroundColor color de fondo por defecto del nuevo terminal
	 */
	public RlTerminal(int width, int height, Color foregroundColor, 
			Color backgroundColor) {
		super(width, height, foregroundColor, backgroundColor);
	}
	
	/**
	 * Constructor. Crea una nueva terminal <code>RlTerminal</code> con las 
	 * dimensiones indicadas y los colores de frente y fondo por defecto.
	 * @param width ancho del nuevo <code>RlTerminal</code> (num. de celdas)
	 * @param height alto del nuevo <code>RlTerminal</code> (num. de celdas)
	 */
	public RlTerminal(int width, int height) {
		super(width, height, DEFAULT_FGCOLOR, DEFAULT_BGCOLOR);
	}
	
	/**
	 * Constructor. Crea una nueva terminal <code>RlTerminal</code> con las 
	 * dimensiones y los colores de frente y fondo por defecto.
	 */
	public RlTerminal() {
		super(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FGCOLOR, DEFAULT_BGCOLOR);
	}
	
	/**
	 * Retorna el numero de celdas que tiene de ancho <code>RlTerminal</code>.
	 * @return ancho de <code>RlTerminal</code> (num. de celdas)
	 */
	@Override
	public int gridWidth() {
		return super.gridWidth();
	}
	
	/**
	 * Retorna el numero de celdas que tiene de alto <code>RlTerminal</code>.
	 * @return alto de <code>RlTerminal</code> (num. de celdas)
	 */
	@Override
	public int gridHeight() {
		return super.gridHeight();
	}
	
	/**
	 * Retorna el color de frente que usa <code>RlTerminal</code> por defecto.
	 * @return color de frente por defecto de <code>RlTerminal</code>
	 */
	@Override
	public Color defaultForegroundColor() {
		return super.defaultForegroundColor();
	}
	
	/**
	 * Retorna el color de fondo que usa <code>RlTerminal</code> por defecto.
	 * @return color de fondo por defecto de <code>RlTerminal</code>
	 */
	@Override
	public Color defaultBackgroundColor() {
		return super.defaultBackgroundColor();
	}
	
	/**
	 * 
	 * @param orientation
	 * @param c
	 * @param x
	 * @param y
	 * @param foregroundColor
	 * @param backgroundColor
	 */
	public void write(int orientation, char c, int x, int y, 
			Color foregroundColor, Color backgroundColor) {
		// Establece el punto de inicio de escritura en el eje horizontal:
		switch (orientation % 3) { // 0.Left - 1.Right - 2.Center
		case 0: break;
		case 1: x = (gridWidth() - 1) - x; break;
		case 2: x = (gridWidth() / 2) + x; break;
		}
		// Establece el punto de inicio de escritura en el eje vertical:
		switch (orientation / 3) { // 0.Top - 1.Bottom - 2.Middle
		case 0: break;
		case 1: y = (gridHeight() - 1) - y; break;
		case 2: y = (gridHeight() / 2) + y; break;
		}
		// Invoca al metodo de escritura de la superclase:
		super.write(c, x, y, foregroundColor, backgroundColor);		
	}
	
	public void write(int orientation, char c, int x, int y, Color foregroundColor) {
		write(orientation, c, x, y, foregroundColor, defaultBackgroundColor()); }
	public void write(int orientation, char c, int x, int y) {
		write(orientation, c, x, y, defaultForegroundColor(), defaultBackgroundColor()); }
	
	/**
	 * 
	 * @param orientation
	 * @param text
	 * @param x
	 * @param y
	 * @param foregroundColor
	 * @param backgroundColor
	 */
	public void write(int orientation, String text, int x, int y, 
			Color foregroundColor, Color backgroundColor) {
		// Establece el punto de inicio de escritura en el eje horizontal:
		switch (orientation % 3) { // 0.Left - 1.Right - 2.Center
		case 0: break;
		case 1: x = (gridWidth() - 1) - (text.length() - 1) - x; break;
		case 2: x = (gridWidth() / 2) - (text.length() / 2) + x; break;
		}
		// Establece el punto de inicio de escritura en el eje vertical:
		switch (orientation / 3) { // 0.Top - 1.Bottom - 2.Middle
		case 0: break;
		case 1: y = (gridHeight() - 1) - y; break;
		case 2: y = (gridHeight() / 2) + y; break;
		}
		// Invoca al metodo de escritura de la superclase:
		for (int i=0; i<text.length(); i++) {
			super.write(text.charAt(i), x+i, y, foregroundColor, backgroundColor);
		}
	}
	
	public void write(int orientation, String text, int x, int y, Color foregroundColor) {
		write(orientation, text, x, y, foregroundColor, defaultBackgroundColor()); }
	public void write(int orientation, String text, int x, int y) {
		write(orientation, text, x, y, defaultForegroundColor(), defaultBackgroundColor()); }
	
	/**
	 * 
	 * @param orientation
	 * @param matrix
	 * @param x
	 * @param y
	 * @param foregroundColor
	 * @param backgroundColor
	 */
	public void write(int orientation, String[] matrix, int x, int y, 
			Color foregroundColor, Color backgroundColor) {
		// Establece el punto de inicio de escritura en el eje vertical:
		switch (orientation / 3) { // 0.Top - 1.Bottom - 2.Middle
		case 0: break;
		case 1: y = (gridHeight() - 1) - (matrix.length - 1) - y; break;
		case 2: y = (gridHeight() / 2) - (matrix.length / 2) + y; break;
		}
		for (int j=0; j<matrix.length; j++) {
			write(orientation%3, matrix[j], x, y+j, foregroundColor, backgroundColor);
		}
	}
	
	public void write(int orientation, String[] matrix, int x, int y, Color foregroundColor) {
		write(orientation, matrix, x, y, foregroundColor, defaultBackgroundColor()); }
	public void write(int orientation, String[] matrix, int x, int y) {
		write(orientation, matrix, x, y, defaultForegroundColor(), defaultBackgroundColor()); }
	

	// XXX: Metodos en prueba
	
	public void cls(char c, int x, int y, int width, int height, 
			Color foregroundColor, Color backgroundColor) {
		for (int i=x; i<(x+width); i++) {
			for (int j=y; j<(y+height); j++) {
				write(c, i, j, foregroundColor, backgroundColor);
			}
		}
	}
	public void cls(char c, int x, int y, int width, int height, Color foregroundColor) {
		cls(c, x, y, width, height, foregroundColor, defaultBackgroundColor()); }
	public void cls(char c, int x, int y, int width, int height) {
		cls(c, x, y, width, height, defaultForegroundColor(), defaultBackgroundColor()); }
	public void cls(char c) {
		cls(c, 0, 0, gridWidth(), gridHeight(), defaultForegroundColor(), defaultBackgroundColor()); }
	public void cls(int x, int y, int width, int height, Color backgroundColor) {
		cls(' ', x, y, width, height, defaultForegroundColor(), backgroundColor); }
	public void cls(int x, int y, int width, int height) {
		cls(' ', x, y, width, height, defaultForegroundColor(), defaultBackgroundColor()); }
	public void cls() {
		cls(' ', 0, 0, gridWidth(), gridHeight(), defaultForegroundColor(), defaultBackgroundColor()); }
	
	
	public void write(int orientation, String[] matrix, int x, int y, 
			Color[] foregroundColors, Color[] backgroundColors) {
		// Establece el punto de inicio de escritura en el eje vertical:
		switch (orientation / 3) { // 0.Top - 1.Bottom - 2.Middle
		case 0: break;
		case 1: y = (gridHeight() - 1) - (matrix.length - 1) - y; break;
		case 2: y = (gridHeight() / 2) - (matrix.length / 2) + y; break;
		}
		for (int j=0; j<matrix.length; j++) {
			write(orientation%3, matrix[j], x, y+j, foregroundColors[j], backgroundColors[j]);
		}
	}
	
	public void write(int orientation, String[] matrix, int x, int y, 
			Color[] foregroundColors) {
		// Establece el punto de inicio de escritura en el eje vertical:
		switch (orientation / 3) { // 0.Top - 1.Bottom - 2.Middle
		case 0: break;
		case 1: y = (gridHeight() - 1) - (matrix.length - 1) - y; break;
		case 2: y = (gridHeight() / 2) - (matrix.length / 2) + y; break;
		}
		for (int j=0; j<matrix.length; j++) {
			write(orientation%3, matrix[j], x, y+j, foregroundColors[j]);
		}
	}

}
