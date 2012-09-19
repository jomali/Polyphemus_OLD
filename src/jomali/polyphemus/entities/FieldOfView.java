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

import jomali.polyphemus.geography.Line;
import jomali.polyphemus.geography.Point;
import jomali.polyphemus.geography.Tile;
import jomali.polyphemus.geography.World;

/**
 * 
 * @author Trystan Spangler
 *
 */
public class FieldOfView {
	
	private World world;
	private int depth;
	private boolean[][] visible;
	private Tile[][][] tiles;
	
	public FieldOfView(World world) {
		this.world = world;
		this.visible = new boolean[world.width()][world.height()];
		this.tiles = new Tile[world.width()][world.height()][world.depth()];
		
		for (int x = 0; x < world.width(); x++) {
			for (int y = 0; y < world.height(); y++) {
				for (int z = 0; z < world.depth(); z++) {
					tiles[x][y][z] = Tile.UNKNOWN;
				}
			}
		}
	}
	
	public boolean isVisible(int x, int y, int z) {
		return z == depth && x >= 0 && y >= 0 && x < visible.length && y < visible[0].length && visible[x][y];
	}
	
	public Tile tile(int x, int y, int z) {
		return tiles[x][y][z];
	}
	
	public void update(int wx, int wy, int wz, int r) {
		depth = wz;
		visible = new boolean[world.width()][world.height()];
		
		for (int x = -r; x < r; x++) {
			for (int y = -r; y < r; y++) {
				if (x*x + y*y > r*r) 
					continue;
				if (wx + x < 0 || wx + x >= world.width() || wy + y < 0 || wy + y >= world.height())
					continue;
				
				for (Point p : new Line(wx, wy, wx+x, wy+y)) {
					Tile tile = world.tile(p.x, p.y, wz);
					visible[p.x][p.y] = true;
					tiles[p.x][p.y][wz] = tile;
					if (!tile.isGround()) break;
				}
			} // for
		} // for
	}

}
