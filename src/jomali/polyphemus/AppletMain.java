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

import java.applet.Applet;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jomali.polyphemus.screens.Screen;
import jomali.polyphemus.screens.TestScreen1;
import jomali.polyphemus.util.RlTerminal;

public class AppletMain extends Applet implements KeyListener {
	private static final long serialVersionUID = -5115683368053688964L;
	
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
	public AppletMain(){
		super();
		terminal = new RlTerminal(ApplicationMain.WIDTH, ApplicationMain.HEIGHT, 
				ApplicationMain.FG_COLOR, ApplicationMain.BG_COLOR);
		add(terminal);
		screen = new TestScreen1();
		addKeyListener(this);
		repaint();
	}
	
	@Override
	public void init(){
		super.init();
		this.setSize(terminal.getWidth() + 20, terminal.getHeight() + 20);
	}
	
	/**
	 * Limpia la terminal e imprime el estado de la pantalla de juego activa.
	 */
	@Override
	public void repaint() {
		terminal.clear(); // TODO: implementar metodo adecuado en RlTerminal
		screen.displayOutput(terminal);
		super.repaint();
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
	
}
