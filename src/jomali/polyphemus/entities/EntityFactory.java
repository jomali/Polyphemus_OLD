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

import java.awt.Color;
import java.util.List;

import jomali.polyphemus.entities.ais.BatAi;
import jomali.polyphemus.entities.ais.FungusAi;
import jomali.polyphemus.entities.ais.PlayerAi;
import jomali.polyphemus.geography.World;
import jomali.polyphemus.util.SColor;

public class EntityFactory {
	
	private World world;
	
	public EntityFactory(World world) {
		this.world = world;
	}
	
	////////////////////////////////////////////////////////////////////////////
	//
	//	CREATURES
	//
	//	Constructor: (World world, String name, char glyph, Color color, 
	//	int maxHp, int attack, int defense)
	//
	////////////////////////////////////////////////////////////////////////////
	
	public Creature newPlayer(String name, char glyph, Color color, 
			List<String> messages, FieldOfView fov) {
		Creature creature = new Creature(name, glyph, color, null, 
				world, 100, 20, 5);
		world.addAtEmptyLocation(creature, 0);
		new PlayerAi(creature, messages, fov);
		return creature;
	}
	
	public Creature newPlayer(String name, List<String> messages, FieldOfView fov) {
		Creature creature = new Creature(name, '@', SColor.WHITE, null, 
				world, 100, 20, 5);
		world.addAtEmptyLocation(creature, 0);
		new PlayerAi(creature, messages, fov);
		return creature;
	}
	
	public Creature newFungus(int depth) {
		Creature creature = new Creature("Fungus", 'f', SColor.GREEN_BAMBOO, null, 
				world, 10, 0, 0);
		world.addAtEmptyLocation(creature, depth);
		new FungusAi(creature, 5, this);
		return creature;
	}
	
	public Creature newBat(int depth) {
		Creature creature = new Creature("Bat", 'b', SColor.PALE_MAGENTA, null, 
				world, 15, 5, 0);
		world.addAtEmptyLocation(creature, depth);
		new BatAi(creature);
		return creature;
	}
	
	////////////////////////////////////////////////////////////////////////////
	//
	//	ITEMS
	//
	//	Constructor: (String name, char glyph, Color color)
	//
	////////////////////////////////////////////////////////////////////////////
	
	public Item newRock(int depth) {
		Color[] colors = new Color[] {
				SColor.CAMO_GREEN, SColor.COPPER_ROSE, SColor.DARK_BROWN, 
				SColor.DARK_CHESTNUT, SColor.DARK_GRAY, SColor.DARK_SLATE_GRAY, 
				SColor.GRAY, SColor.GRAY_ASPARAGUS, SColor.KHAKI, SColor.LAVENDER_BLUE, 
				SColor.PALE_BROWN, SColor.SEN_NO_RIKYUS_TEA, SColor.SEPIA
		};
		Item rock = new Item("roca", ',', colors[(int)(Math.random()*colors.length)], null);
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}
	
	public Item newVictoryItem(int depth) {
		Item item = new Item("teddy bear", '*', SColor.BLUE, null);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newBread(int depth) {
		Item item = new Item("bread", '%', SColor.YELLOW, null);
		item.modifyFoodValue(200);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newFruit(int depth) {
		Item item = new Item("apple", '%', SColor.RED_PIGMENT, null);
		item.modifyFoodValue(100);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newDagger(int depth) {
		Item item = new Item("dagger", ')', SColor.LIGHT_BLUE, null);
		item.modifyAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newSword(int depth) {
		Item item = new Item("sword", ')', SColor.LIGHT_GRAY, null);
		item.modifyAttackValue(10);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newStaff(int depth) {
		Item item = new Item("staff", ')', SColor.YELLOW, null);
		item.modifyAttackValue(5);
		item.modifyDefenseValue(3);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newEdibleWeapon(int depth) {
		Item item = new Item("baguette", ')', SColor.YELLOW, null);
		item.modifyAttackValue(3);
		item.modifyFoodValue(50);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newLightArmor(int depth) {
		Item item = new Item("tunic", '[', SColor.GREEN, null);
		item.modifyDefenseValue(2);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newMediumArmor(int depth) {
		Item item = new Item("chainmail", '[', SColor.LIGHT_GRAY, null);
		item.modifyDefenseValue(4);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	public Item newHeavyArmor(int depth) {
		Item item = new Item("platemail", '[', SColor.GRAY_ASPARAGUS, null);
		item.modifyDefenseValue(6);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item randomWeapon(int depth) {
		switch ((int)(Math.random() * 3)){
		case 0: return newDagger(depth);
		case 1: return newSword(depth);
		default: return newStaff(depth);
		}
	}

	public Item randomArmor(int depth) {
		switch ((int)(Math.random() * 3)) {
		case 0: return newLightArmor(depth);
		case 1: return newMediumArmor(depth);
		default: return newHeavyArmor(depth);
		}
	}

}
