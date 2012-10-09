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

import jomali.polyphemus.entities.Item;
import jomali.polyphemus.entities.items.Attire;
import jomali.polyphemus.entities.items.Consumable;
import jomali.polyphemus.entities.items.Weapon;
import jomali.polyphemus.geography.World;
import jomali.polyphemus.util.SColor;

/**
 * Armas:
 * 	<ul>
 * 		<li>Dagger 				(+2) 	- 900 $
 * 		<li>Magic dagger		(+6)	- 3800 $
 * 		<li>Short sword			(+4)	- 2100 $
 * 		<li>Long sword			(+5)	- 2800 $
 * 		<li>Heavy sword			(+11)	- 9500 $
 * 		<li>Fire sword			(+12)	- 12 500 $
 * 		<li>Magic sword			(+15)	- 16 000 $
 * 		<li>Axe of chaos		(+25)	- 28 000 $
 * 	</ul>
 * 
 * Armaduras:
 * 	<ul>
 * 		<li>Leather armour		(+2)	- 1800 $
 * 		<li>Coat of chain mail	(+3)	- 3700 $
 * 		<li>Heavy armour		(+5)	- 8000 $
 * 		<li>Plate armour		(+7)	- 10 500 $
 * 		<li>Magic armour		(+9)	- 20 000 $
 * 		<li>Armour of chaos		(+12)	- 40 000 $
 * 		<li>Wooden shield		(+1)	- 1500 $
 * 		<li>Shield				(+2)	- 4000 $
 * 		<li>Heavy shield		(+3)	- 6500 $
 * 		<li>Shield of chaos		(+6)	- 15 000 $
 * 		<li>Helmet				(+1)	- 1200 $
 * 		<li>Heavy helmet		(+3)	- 1500 $
 * 		<li>Helmet of chaos		(+4)	- 7000 $
 * 	</ul>
 * 
 * [Datos basados en Ishar II: Messengers of Doom]
 */
public class ItemFactory {
	
	private World world;
	public ItemFactory(World world) { this.world = world; }
	
	////////////////////////////////////////////////////////////////////////////
	//
	//	WEAPONS
	//
	////////////////////////////////////////////////////////////////////////////
	
	public Item randomWeapon(int depth) {
		switch ((int)(Math.random() * 2)){
		case 0: return newDagger(depth);
		default: return newShortSword(depth);
		}
	}
	
	public Item newDagger(int depth) {
		Weapon item = new Weapon("Dagger", ')', SColor.LIGHT_BLUE, null);
		item.modifyAttackValue(2);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newShortSword(int depth) {
		Weapon item = new Weapon("Short sword", ')', SColor.LIGHT_GRAY, null);
		item.modifyAttackValue(4);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	////////////////////////////////////////////////////////////////////////////
	//
	//	ARMORS
	//
	////////////////////////////////////////////////////////////////////////////

	public Item randomArmor(int depth) {
		switch ((int)(Math.random() * 3)) {
		case 0: return newLeatherArmor(depth);
		case 1: return newHeavyArmor(depth);
		default: return newPlateArmor(depth);
		}
	}
	
	public Item newLeatherArmor(int depth) {
		Attire item = new Attire("Leather armor", '[', SColor.DARK_BROWN, null);
		item.modifyDefenseValue(2);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newHeavyArmor(int depth) {
		Attire item = new Attire("Heavy armor", '[', SColor.GRAY_ASPARAGUS, null);
		item.modifyDefenseValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newPlateArmor(int depth) {
		Attire item = new Attire("Plate armor", '[', SColor.LIGHT_BLUE_FLOWER, null);
		item.modifyDefenseValue(7);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	////////////////////////////////////////////////////////////////////////////
	//
	//	FOOD
	//
	////////////////////////////////////////////////////////////////////////////

	public Item newBlackMushroom(int depth) {
		Consumable item = new Consumable("Black mushroom", '%', SColor.GRAPE_MOUSE, null);
		item.modifyFoodValue(50);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newWhiteMushroom(int depth) {
		Consumable item = new Consumable("White mushroom", '%', SColor.WHEAT, null);
		item.modifyFoodValue(50);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	////////////////////////////////////////////////////////////////////////////
	//
	//	MISCELLANY
	//
	////////////////////////////////////////////////////////////////////////////
	
	public Item newRock(int depth) {
		/*
		Color[] colors = new Color[] {
				SColor.CAMO_GREEN, SColor.COPPER_ROSE, SColor.DARK_BROWN, 
				SColor.DARK_CHESTNUT, SColor.DARK_GRAY, SColor.DARK_SLATE_GRAY, 
				SColor.GRAY, SColor.GRAY_ASPARAGUS, SColor.KHAKI, SColor.LAVENDER_BLUE, 
				SColor.PALE_BROWN, SColor.SEN_NO_RIKYUS_TEA, SColor.SEPIA
		};
		Item item = new Item("Rock", ',', colors[(int)(Math.random()*colors.length)], null);
		*/
		Item item = new Item("Rock", ',', SColor.LIGHT_GRAY, null);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newVictoryItem(int depth) {
		Item item = new Item("teddy bear", '*', SColor.BLUE, null);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

}
