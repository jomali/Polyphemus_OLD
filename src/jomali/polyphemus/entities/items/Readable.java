package jomali.polyphemus.entities.items;

import java.awt.Color;

import jomali.polyphemus.entities.Item;

public class Readable extends Item {
	
	public Readable(String name, char glyph, Color foregroundColor, Color backgroundColor) {
		super(name, glyph, foregroundColor, backgroundColor);
	}
	
	@Override
	public String actionName() { return "read"; }

}
