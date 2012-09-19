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
import java.util.ArrayList;
import java.util.List;

import jomali.polyphemus.entities.Creature;
import jomali.polyphemus.entities.Item;

/**
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 * 
 * TODO: Si las entidades del mundo se definen con colores de fondo 
 * especificos para ellas, la clase tiene que proveer de metodos para indicar 
 * cuales son los colores de fondo de una casillas dada.
 */
public class World {
	
	private String name;
	private Tile[][][] tiles;
	private int width;
	private int height;
	private int depth;
	private Item[][][] items;
	private List<Creature> creatures; // TODO: ¿Por que no Creature[][][]?
	
	
	public World(String name, Tile[][][] tiles) {
		this.name		= name;
		this.tiles		= tiles;
		this.width		= tiles.length;
		this.height		= tiles[0].length;
		this.depth		= tiles[0][0].length;
		this.items		= new Item[width][height][depth];
		this.creatures	= new ArrayList<Creature>();
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Metodos para obtener informacion del mundo:
	
	public String name() { return name; }
	
	public int width() { return width; }
	
	public int height() { return height; }
	
	public int depth() { return depth; }
	
	public Tile tile(int x, int y, int z) {
		// By checking for bounds here we don't need to worry about out of 
		// bounds errors and check every time we ask the world about a location
		if ((x < 0 || x >= width) || (y < 0 || y >= height) || (z < 0 || z >= depth))
			return Tile.BOUNDS;
		else
			return tiles[x][y][z];
	}
	
	public Item item(int x, int y, int z) {
		return items[x][y][z];
	}

	// TODO: Cada vez que se solicita un criatura hay que recorrer la lista 
	// de criaturas del mundo. Cambiando la estructura de datos se puede 
	// mejorar mucho la eficiencia de esta operación:
	public Creature creature(int x, int y, int z) {
		for (Creature c : creatures) {
			if (c.x == x && c.y == y && c.z == z)
				return c;
		}
		return null;
	}
	
	public char glyph(int x, int y, int z) {
		Creature creature = creature(x, y, z);
		if (creature != null) return creature.glyph();
		if (item(x, y, z) != null) return item(x, y, z).glyph();
		return tile(x, y, z).glyph();
	}
	
	public Color color(int x, int y, int z) {
		Creature creature = creature(x, y, z);
		if (creature != null) return creature.color();
		if (item(x, y, z) != null) return item(x, y, z).color();
		return tile(x, y, z).color();
	}
	
	/////////////////////////////////////////////////:///////////////////////////
	// Metodos para interactuar con el mundo
	
	// XXX: Si hay diferentes tipos de paredes y suelos, al cavar una pared 
	// deberia quedar un suelo del mismo material que la pared destruida
	public void dig(int x, int y, int z) {
		if (tile(x, y, z).isDiggable())
			tiles[x][y][z] = Tile.FLOOR;
	}
	
	// TODO: ¿Por que se itera sobre una copia de la lista de criaturas?
	public void update() {
		List<Creature> toUpdate = new ArrayList<Creature>(creatures);
		for (Creature creature : toUpdate) creature.update();
	}
	
	public void remove(int x, int y, int z) {
		items[x][y][z] = null;
	}
	
	public void remove(Creature other) {
		creatures.remove(other);
	}
	
	public void addAtEmptyLocation(Item item, int z) {
		int x, y;
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		} 
		while (!tile(x,y,z).isGround() || item(x,y,z) != null);
		
		items[x][y][z] = item;
	}
	
	public void addAtEmptyLocation(Creature creature, int z) {
		int x, y;
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		}
		while (!tile(x,y,z).isGround() || creature(x,y,z) != null);
		
		creature.x = x;
		creature.y = y;
		creature.z = z;
		creatures.add(creature);
	}
	
	/**
	 * TODO: Comentarios.
	 * 
	 * <p>Adding an item to a specific place is more complicated since we only 
	 * allow one item per tile. Because of that, we need to check adjacent 
	 * tiles for an open space and repeat until we find one or run out of open 
	 * spaces.
	 * 
	 * <p>A funky side effect of this is that if there are no open spaces then 
	 * the item won't be added but will no longer be in the creature's 
	 * inventory - it will vanish from the game. You can either let that happen 
	 * or somehow let the caller know that it hasn't been added and shouldn't 
	 * be removed from the inventory. Or you could notify everyone in viewing 
	 * distance that it has vanished. I'll leave that up to you. If you leave 
	 * it as it is then there's no indication that the item vanished and that 
	 * may be interpreted as a bug. If you tell users it happens they probably 
	 * won't consider it a bug - just part of the game. This could also make a 
	 * funny scenario: imagine being trapped in a room where the floor is 
	 * covered in treasure but you can't pick any up since your inventory is 
	 * full and there's no room to drop your useless rusty sword.
	 * 
	 * @param item
	 * @param x
	 * @param y
	 * @param z
	 * @return true si la accion termina con exito, false en caso contrario
	 */
	public boolean addAtEmptySpace(Item item, int x, int y, int z) {
		if (item == null) return true;
		
		List<Point> points	= new ArrayList<Point>();
		List<Point> checked	= new ArrayList<Point>();
		points.add(new Point(x, y, z));
		
		while (!points.isEmpty()) {
			Point p = points.remove(0);
			checked.add(p);
			
			if (!tile(p.x, p.y, p.z).isGround()) continue;
			
			if (items[p.x][p.y][p.z] == null) {
				items[p.x][p.y][p.z] = item;
				Creature c = this.creature(p.x, p.y, p.z);
				// TODO: A apple lands between your feet
				if (c != null) c.notify("A %s lands between your feet.", item.name());
				return true;
			}
			else {
				List<Point> neighbors = p.neighbors8();
				neighbors.removeAll(checked);
				points.addAll(neighbors);
			}
		} // while
		return false;
	}

}
