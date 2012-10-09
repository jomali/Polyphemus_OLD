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

package jomali.polyphemus;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

// TODO: importar exclusivamente las clases necesarias
import jomali.polyphemus.screens.*;
import jomali.polyphemus.util.RlTerminal;

/**
 * Implementa el metodo principal que lanza la aplicacion.
 * 
 * <p>Se encarga de crear el marco grafico en que se mostraran las diferentes 
 * pantallas que conforman cada uno de los modos de juego y se encarga de 
 * recibir la entrada del usuario y transmitirla a la pantalla activa para que 
 * sea ella quien se encargue de procesarla.
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 *
 */
public class ApplicationMain extends JFrame implements KeyListener {
	private static final long serialVersionUID = -4232751380750227765L;
	
	public static final String NAME		= "Polyphemus";
	public static final int RELEASE		= 0;
	public static final int WIDTH			= 80;
	public static final int HEIGHT		= 24;
	public static final Color FG_COLOR	= Color.WHITE;
	public static final Color BG_COLOR	= Color.BLACK;
	
	private RlTerminal terminal;
	private Screen screen;
	
	/**
	 * Constructor. Crea una ventana grafica que simula una terminal de texto 
	 * y la annade al marco grafico de la aplicacion. Es esta terminal quien 
	 * se encarga de imprimir la informacion de la pantalla de juego activa. 
	 * Crea tambien una pantalla <code>StartScreen</code> que se selecciona 
	 * como pantalla activa, siendo por tanto esta pantalla la primera con la 
	 * que se encontrara el usuario al iniciar la aplicacion.
	 */
	public ApplicationMain() {
		super();
		terminal = new RlTerminal(WIDTH, HEIGHT, FG_COLOR, BG_COLOR);
		add(terminal);
		pack();
		screen = new StartScreen();
		addKeyListener(this);
		repaint();
		
		// Se centra el marco en pantalla:
		Toolkit kit = Toolkit.getDefaultToolkit();
		int screenWidth = kit.getScreenSize().width;
		int screenHeight = kit.getScreenSize().height;
		setLocation(screenWidth/2 - getSize().width/2, screenHeight/2 - getSize().height/2);

		// TODO: comprobar path del icono para que sea leído correctamente al 
		// ejecutar la aplicación empaquetada como un .jar
		setResizable(false);
		setIconImage(kit.getImage("res/arroba_icon02.png"));
		setTitle(NAME+ " v."+ RELEASE);
	}
	
	/**
	 * Limpia la terminal e imprime el estado de la pantalla de juego activa.
	 */
	@Override
	public void repaint() {
		// TODO: Se calcula el tiempo que tarda la operacion en realizarse a 
		// fin de poder evaluar el rendimiento de la terminal.
		long tStart = System.currentTimeMillis();
		
		terminal.clear(); // TODO: implementar metodo adecuado en RlTerminal
		screen.displayOutput(terminal);
		super.repaint();
		
		// TODO: Eliminar al terminar pruebas.
		long tEnd = System.currentTimeMillis();
		System.out.println("Time to repaint: "+ (tEnd - tStart)+ "ms.");
	}
	
	/**
	 * Recibe un evento de pulsacion de tecla y se lo transmite a la pantalla 
	 * de juego activa. Despues pinta de nuevo el marco.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		screen = screen.respondToUserInput(e);
		repaint();
	}
	
	/**
	 * Recibe un evento de soltado de tecla pero no hace nada con el.
	 */
	@Override
	public void keyReleased(KeyEvent e) { }
	
	/**
	 * Recibe un evento de tecla introducida pero no hace nada con el.
	 */
	@Override
	public void keyTyped(KeyEvent e) { }
	
	////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		ApplicationMain app = new ApplicationMain();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
