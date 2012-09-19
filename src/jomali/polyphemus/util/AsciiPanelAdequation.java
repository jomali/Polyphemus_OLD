/* POLYPHEMUS - un pequenno juego roguelike en Java
 * Copyright © 2012 J. Francisco Martin 
 * 
 * ADVERTENCIA: Se han suprimido los acentos para evitar problemas de 
 * compatibilidad entre diferentes computadoras.
 */

package jomali.polyphemus.util;

import asciiPanel.AsciiPanel;
import java.awt.Color;

/**
 * Actua como capa de abstraccion intermedia entre una <code>RlTerminal</code> 
 * y su posible implementacion como <code>AsciiPanel</code>.
 * 
 * <p>Hay un conjunto de operaciones en <code>RlTerminal</code> que crean 
 * realmente la terminal y se encargan de escribir en ella; sus operaciones 
 * constructoras. Los demas metodos operan de alguna forma unos parametros de 
 * entrada y con los resultados obtenidos invocan a estas constructoras.
 * 
 * <p><code>AsciiPanelAdequation</code> determina cuales son los metodos de 
 * <code>AsciiPanel</code> que se corresponden en su implementacion particular 
 * con las operaciones constructoras de <code>RlTerminal</code> y los invoca.
 * 
 * @author J. Francisco Martin
 *
 */
public abstract class AsciiPanelAdequation extends AsciiPanel {
	private static final long serialVersionUID = -910981704968312878L;
	
	/**
	 * Constructor. Crea un nuevo <code>AsciiPanel</code> con las dimensiones 
	 * dadas y establece <code>foregroundColor</code> y 
	 * <code>backgroundColor</code> como colores de frente y fondo por defecto 
	 * respectivamente.
	 * 
	 * @param width ancho del nuevo <code>AsciiPanel</code> (num. de celdas)
	 * @param height alto del nuevo <code>AsciiPanel</code> (num. de celdas)
	 * @param foregroundColor color de frente por defecto del panel
	 * @param backgroundColor color de fondo por defecto del panel
	 */
	public AsciiPanelAdequation(int width, int height, Color foregroundColor, 
			Color backgroundColor) {
		super(width, height);
		super.setDefaultForegroundColor(foregroundColor);
		super.setDefaultBackgroundColor(backgroundColor);
	}
	
	/**
	 * Retorna el numero de celdas que tiene de ancho el <code>AsciiPanel</code>.
	 * @return ancho del <code>AsciiPanel</code> (num. de celdas)
	 */
	public int gridWidth() {
		return super.getWidthInCharacters();
	}
	
	/**
	 * Retorna el numero de celdas que tiene de alto el <code>AsciiPanel</code>.
	 * @return alto del <code>AsciiPanel</code> (num. de celdas)
	 */
	public int gridHeight() {
		return super.getHeightInCharacters();
	}
	
	/**
	 * Retorna el color de frente que usa el <code>AsciiPanel</code> por defecto.
	 * @return color de frente por defecto del <code>AsciiPanel</code>
	 */
	public Color defaultForegroundColor() {
		return super.getDefaultForegroundColor();
	}
	
	/**
	 * Retorna el color de fondo que usa el <code>AsciiPanel</code> por defecto.
	 * @return color de fondo por defecto del <code>AsciiPanel</code>
	 */
	public Color defaultBackgroundColor() {
		return super.getDefaultBackgroundColor();
	}
	
	@Override
	public AsciiPanel write(char c, int x, int y, Color foregroundColor, 
			Color backgroundColor) {
		if (c < 0 || c >= 256) { // 256 = glyphs.length
			System.out.println("Error. Caracter incorrecto.");
			return this;
		}
		if (x < 0 || x >= gridWidth()) {
			System.out.println("Error. Valor x:"+ x+ " incorrecto.");
			return this;
		}
		if (y < 0 || y >= gridHeight()) {
			System.out.println("Error. Valor y:"+ y+ " incorrecto.");
			return this;
		}
		if (foregroundColor == null) foregroundColor = defaultForegroundColor();
		if (backgroundColor == null) backgroundColor = defaultBackgroundColor();
		c = transpose(c);
		return super.write(c, x, y, foregroundColor, backgroundColor);
	}
	
	private char transpose(char c) {

//		if (c == 'á' || c == 'Á') return (char)160;
//		if (c == 'é' || c == 'É') return (char)130;
//		if (c == 'í' || c == 'Í') return (char)161;
//		if (c == 'ó' || c == 'Ó') return (char)162;
//		if (c == 'ú' || c == 'Ú') return (char)163;
//		if (c == 'ñ')	return (char)164;
//		if (c == 'Ñ')	return (char)165;
		if (c == '·')	return (char)250;
		if (c == '#')	return (char)177;
		if (c == '^')	return (char)178;
//		if (c == '^')	return (char)219;
		if (c == '>')	return (char)25;
		if (c == '<')	return (char)24;
//		if (c == '¿')	return (char) 168;
		
		return c;
	}
	
}
