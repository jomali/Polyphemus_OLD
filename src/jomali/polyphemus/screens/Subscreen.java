package jomali.polyphemus.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;

import jomali.polyphemus.util.RlTerminal;

/**
 * 
 * @author J. Francisco Martin
 *
 */
public abstract class Subscreen implements Screen {
	
	private String screenName;
	
	public Subscreen(String screenName) { this.screenName = screenName; }
		
	protected void displayFrame(RlTerminal terminal) {
		terminal.writeRow(RlTerminal.TL, 0);
		terminal.writeRow(RlTerminal.BL, 0);
		terminal.writeCol(RlTerminal.TL, 0);
		terminal.writeCol(RlTerminal.TR, 0);
		
		terminal.write(RlTerminal.TL, " "+ screenName+ " ", 2, 0, Color.BLACK, Color.WHITE);
		// TODO: Locations
		terminal.write(RlTerminal.BR, " [ESC] exit ", 2, 0, Color.BLACK, Color.WHITE);
	}
	
	@Override
	public void displayOutput(RlTerminal terminal) {
		terminal.cls();
		displayFrame(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return (key.getKeyCode() == KeyEvent.VK_ESCAPE) ? null : this;		
	}

}
