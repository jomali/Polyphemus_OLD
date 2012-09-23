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

import jomali.polyphemus.entities.items.Attire;
import jomali.polyphemus.entities.items.Consumable;
import jomali.polyphemus.entities.items.Weapon;
import jomali.polyphemus.geography.Tile;
import jomali.polyphemus.geography.World;

/**
 * Clase base a partir de la que se crean todas las criaturas que pueblan el 
 * mundo del juego, incluyendo al jugador.
 * 
 * TODO: Comentarios:
 * 
 * <p>To implement all the different behaviors of all the different creatures, 
 * we could use a bunch of flags representing creature traits, or we could use 
 * subclassing, but let's use something that's usually more flexible: 
 * delegation. Each creature will have a reference to a CreatureAi and the 
 * creatures can let their ai to decide what to do. Instead of using what's 
 * called constructor injection and passing the CreatureAi in the constructor 
 * like we do with the world, glyph, and color, we'll use setter injection.
 * (<misko.hevery.com/2009/02/19/constructor-injection-vs-setter-injection/>).
 * 
 * <p>I made the x and y coordinate publicly accessible since they'll be used a 
 * lot, we don't need to constrain them or do anything when they change, and 
 * I'd rather not have to deal with getters and setters. Getters and setters 
 * are almost always a better idea than public fields but part of software 
 * engineering is knowing the rules and part is knowing when to break them. If 
 * this turns out to be a bad idea and we wish we used getters and setters 
 * instead, then it's not a big deal since most IDE's can automatically create 
 * getters and setters and rewrite your code to use them. (Encapsulate Field or 
 * Generate Getters and Setters in Eclipse.)
 * 
 * @author Trystan Spangler
 * @author J. Francisco Martin
 * 
 * TODO: Estudiar la opcion de crear una superclase <code>Entity</code> a 
 * partir de la que representar todas las entidades que pueden interactuar en 
 * el mundo del juego: Criaturas, Objetos, Ptos. de informacion,...
 * 
 * TODO: Queda la posibilidad de annadir un color de fondo a las criaturas 
 * para incrementar su diversidad y/o diferenciacion.
 */
public class Creature extends Entity {
	
	private World world;
	private CreatureAi ai;
	private Inventory inventory;
	
	// TODO: Modificar atributos de la criatura
	private int maxHp;
	private int hp;
	private int maxFood;
	private int food;
	private int visionRadius;
	private int attackValue;
	private int defenseValue;
	
	// TODO: Ahora mismo, los objetos equipables por la criatura estan 
	// definidos como atributos independientes suyos. Como estos objetos siguen 
	// siendo parte del inventario, parece buena idea que esten definidos en 
	// la clase Inventory y que esta provea de metodos adecuados para conocer 
	// los valores de ataque o defensa que annaden los armas y las armaduras 
	// equipadas a la criatura. 
	// Ej: inventory.attackValue(), inventory.defenseValue()
	private Weapon weapon;
	private Attire armor;
	
	// Atributos publicos:
	public int x;
	public int y;
	public int z;
	
	public Creature(String name, char glyph, Color foregroundColor, Color backgroundColor, 
			World world, int maxHp, int attack, int defense) {
		super(name, glyph, foregroundColor, backgroundColor);
		this.world			= world;
		this.maxHp			= maxHp;
		this.hp				= maxHp;
		// TODO: Estos son los atributos que son proclives a ser modificados. 
		// En cualquier caso, se deberian inicializar de forma diferente.
		this.visionRadius	= 9;
		this.attackValue	= attack;
		this.defenseValue	= defense;
		this.inventory		= new Inventory(20);
		this.maxFood		= 1000;
		this.food			= (maxFood / 3) * 2;
	}
	
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
		
	////////////////////////////////////////////////////////////////////////////
	// Metodos para acceder a los atributos:
		
	public Inventory inventory() { return inventory; }
	
	public int maxHp() { return maxHp; }
	
	public int hp() { return hp; }
	
	public int visionRadius() { return visionRadius; }
	
	// TODO: Tras modificar el inventario, el metodo podria quedar asi:
	// return attackValue + inventory.attackValue();
	public int attackValue() {
		return attackValue
				+ (weapon == null ? 0 : weapon.attackValue());
	}
	
	// TODO: Tras modificar el inventario, el metodo podria quedar asi:
	// return defenseValue + inventory.defenseValue();
	public int defenseValue() {
		return defenseValue
				+ (armor == null ? 0 : armor.defenseValue());
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Metodos modificadores de atributos:
	
	public void modifyHp(int amount) {
		hp += amount;
		
		if (hp > maxHp) {
			hp = maxHp;
		} else if (hp < 1) {
			doAction("die");
			leaveCorpse();
			world.remove(this);
		}
	}
	
	public void modifyFood(int amount) {
		food += amount;
		
		if (food > maxFood) {
			maxFood = (maxFood + food) / 2;
			food = maxFood;
			notify("You can't belive your stomach can hold that much!");
			modifyHp(-1);
		} else if (food < 1 && isPlayer()) {
			modifyHp(-1000);
		}
	}
	
	private void leaveCorpse() {
		Consumable corpse = new Consumable(name() + " corpse", '%', foregroundColor().darker(), null);
		corpse.modifyFoodValue(maxHp);
		world.addAtEmptySpace(corpse, x, y, z);
	}
	
	////////////////////////////////////////////////////////////////////////////
	// TODO: Estudiar conveniencia de los metodos

	public Tile tile(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz);
	}

	public Creature creature(int wx, int wy, int wz) {
		return world.creature(wx, wy, wz);
	}
	
	public boolean canSee(int wx, int wy, int wz){
		return ai.canSee(wx, wy, wz);
	}

	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
	}
	
	public void moveBy(int mx, int my, int mz){
		if (mx==0 && my==0 && mz==0) return;
		
		Tile tile = world.tile(x+mx, y+my, z+mz);
		
		// La criatura intenta descender un nivel
		if (mz == -1){
			if (tile == Tile.STAIRS_DOWN) {
				doAction("walk up the stairs to level %d", z+mz+1);
			} else {
				doAction("try to go up but are stopped by the cave ceiling");
				return;
			}
		}
		// La criatura intenta ascender un nivel
		else if (mz == 1){
			if (tile == Tile.STAIRS_UP) {
				doAction("walk down the stairs to level %d", z+mz+1);
			} else {
				doAction("try to go down but are stopped by the cave floor");
				return;
			}
		}
		
		Creature other = world.creature(x+mx, y+my, z+mz);
		
// XXX:
//		modifyFood(-1);
		
		if (other == null)
			ai.onEnter(x+mx, y+my, z+mz, tile);
		else
			attack(other);
	}
	
	public void dig(int wx, int wy, int wz) {
		modifyFood(-10);
		world.dig(wx, wy, wz);
		doAction("dig");
	}
	
	/**
	 * Hay múltiples formas de calcular la cantidad de daño provocado en un 
	 * ataque. En este caso, la solución empleada es muy simple: la cantidad de 
	 * daño es un número aleatorio comprendido entre 1 y el valor de ataque del 
	 * atacante menos el valor defensivo de la criatura a la que se ataca. 
	 * Sencillo de entender, sencillo de implementar, y haciendo uso únicamente 
	 * de dos variables ya ha funcionado bien en otras ocasiones (por ejemplo 
	 * en Castlevania: Symphony of the Night).
	 * @param other Creature sobre la que se realiza el ataque
	 */
	public void attack(Creature other){
		modifyFood(-2);
		
		int amount = Math.max(0, attackValue() - other.defenseValue());
		
		amount = (int)(Math.random() * amount) + 1;
		
		doAction("attack the %s for %d damage", other.name(), amount);
		
		other.modifyHp(-amount);
	}
	
	public void update(){
		modifyFood(-1);
		ai.onUpdate();
	}

	public void notify(String message, Object ... params){
		ai.onNotify(String.format(message, params));
	}
	
	public void doAction(String message, Object ... params){
		int r = 9;
		for (int ox = -r; ox < r+1; ox++){
			for (int oy = -r; oy < r+1; oy++){
				if (ox*ox + oy*oy > r*r)
					continue;
				
				Creature other = world.creature(x+ox, y+oy, z);
				
				if (other == null)
					continue;
				
				if (other == this)
					other.notify("You " + message + ".", params);
				else if (other.canSee(x, y, z))
					other.notify(String.format("The %s %s.", name(), makeSecondPerson(message)), params);
			}
		}
	}
	
	private String makeSecondPerson(String text){
		String[] words = text.split(" ");
		words[0] = words[0] + "s";
		
		StringBuilder builder = new StringBuilder();
		for (String word : words){
			builder.append(" ");
			builder.append(word);
		}
		
		return builder.toString().trim();
	}
	
	public void pickup(){
		Item item = world.item(x, y, z);
		
		if (inventory.isFull() || item == null){
			doAction("grab at the ground");
		} else {
			doAction("pickup a %s", item.name());
			world.remove(x, y, z);
			inventory.add(item);
		}
	}
	
	public void drop(Item item){
		if (world.addAtEmptySpace(item, x, y, z)){
			doAction("drop a " + item.name());
			inventory.remove(item);
			unequip(item);
		} else {
			notify("There's nowhere to drop the %s.", item.name());
		}
	}
	
	public boolean isPlayer(){
		return glyph() == '@';
	}
	
	public void eat(Consumable consumable){
		if (consumable.foodValue() < 0)
			notify("Gross!");
		
		modifyFood(consumable.foodValue());
		inventory.remove(consumable);
		unequip(consumable);
	}
	
	public void unequip(Item item){
		if (item == null)
			return;
		
		if (item == armor){
			doAction("remove a " + item.name());
			armor = null;
		} else if (item == weapon) {
			doAction("put away a " + item.name());
			weapon = null;
		}
	}
	
	public void equip(Item item){
/*
		if (item.attackValue() == 0 && item.defenseValue() == 0)
			return;
		
		if (item.attackValue() >= item.defenseValue()){
			unequip(weapon);
			doAction("wield out a " + item.name());
			weapon = item;
		} else {
			unequip(armor);
			doAction("put on a " + item.name());
			armor = item;
		}
*/
	}
	
	public Creature closestCreature() {
		int distance = 6;
		for (int a=1; a<=distance; a++) {
			for (int i=-a; i<=a; i++) {
				for (int j=-a; j<=a; j++) {
					Creature c = world.creature(this.x+i, this.y+j, this.z);
					if (c != null && !c.isPlayer()) { return c; }
				}
			}
		}
		return null;
	}
	
	public void acts() {
		// La criatura se encuentra sobre un objeto:
		Item item = world.item(x, y, z);
		if (item != null) { pickup(); return; }
		
		// La criatura se encuentra sobre una casilla vacía:
		Tile tile = world.tile(x, y, z);
		if (tile == Tile.STAIRS_DOWN) { moveBy(0, 0, 1); return; }
		else if (tile == Tile.STAIRS_UP) { moveBy(0, 0, -1); return; }
		
		// ...no se hace nada
	}

}
