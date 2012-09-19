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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Implementa operaciones para convertir datos de un mundo de juego en un 
 * archivo de texto, y viceversa.
 * 
 * TODO: Actualmente solo se lee y escribe la disposicion de los tiles. Pordia 
 * estudiarse annadir soporte para criaturas y tal vez objetos.
 * 
 * @author		J. Francisco Martin
 * @version	1.0
 * @serial		20120913
 *
 */
public class WorldFileManager {

	public static void writeWorldInFile(World world, String fileName) {
		try {
			FileWriter file = new FileWriter(fileName);
			PrintWriter writer = new PrintWriter(file);
			
			writer.print(world.width()+ "x"+ world.height()+ "x"+ world.depth());
			writer.println("@"+ world.name());			
			for (int k=0; k<world.depth(); k++) {
				for (int j=0; j<world.height(); j++) {
					for (int i=0; i<world.width(); i++) {
						writer.print(world.glyph(i, j, k));
					}
					writer.println();
				}
			}
			writer.close();
		}
		catch (IOException e) { System.out.println(e); }
	}
	
	public static World readWorldFromFile(String fileName) {
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			
			// Obtiene la información del mapa:
			String worldInfo = reader.readLine();
			int aux = worldInfo.indexOf('x');
			int x = Integer.parseInt(worldInfo.substring(0, aux).trim());
			worldInfo = worldInfo.substring(aux+1);
			aux = worldInfo.indexOf('x');
			int y = Integer.parseInt(worldInfo.substring(0, aux).trim());
			worldInfo = worldInfo.substring(aux+1);
			aux = worldInfo.indexOf('@');
			int z = Integer.parseInt(worldInfo.substring(0, aux).trim());
			String name = worldInfo.substring(aux+1).trim();
			
			Tile[][][] tiles = new Tile[x][y][z];
			for (int k=0; k<z; k++) {
				for (int j=0; j<y; j++) {
					String line = reader.readLine();
					for (int i=0; i<x; i++) {
						switch (line.charAt(i)) {
							case '.': tiles[i][j][k] = Tile.FLOOR; break;
							case '#': tiles[i][j][k] = Tile.DIRT; break;
							case '^': tiles[i][j][k] = Tile.STONE; break;
							case '>': tiles[i][j][k] = Tile.STAIRS_DOWN; break;
							case '<': tiles[i][j][k] = Tile.STAIRS_UP; break;
							default: tiles[i][j][k] = Tile.BOUNDS; break;
						}
					} // for
				} // for
			} // for
			reader.close();
			return new World(name, tiles);
		}
		catch (IOException e) { System.out.println(e); }
		return null;
	}
	
}
