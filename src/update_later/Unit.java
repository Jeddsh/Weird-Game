package update_later;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Unit {
	protected BufferedImage bi_spriteSheet;
	private BufferedImage bi_sprite;
	private String name;
	private int hp;
	private int atk;
	private int defense;
	private int cost;
	private State state;
	private boolean side;
	
	enum State {
		UNMOVED,
		MOVED
	}
	public Unit(int hp, int atk, int def, String name, int cost, boolean side) {
		this.hp = hp;
		this.atk = atk;
		this.defense = def;
		this.name = name;
		this.cost = cost;
		this.setState(State.MOVED);
		this.side = side;
		try
		{
			this.bi_spriteSheet = ImageIO.read(new File("pixel-art.png"));
		} 
		catch (IOException ioe)
		{
			System.out.println("Couldn't load sprites.");
			System.exit(0);
		}
	}
	public int getCost(){
		return this.cost;
	}
	public int getHp() {
		return hp;
	}
	public int getAtk() {
		return atk;
	}
	public int getDefense() {
		return defense;
	}
	public State getState() {
		return this.state;
	}
	public String getName() {
		return name;
	}
	public BufferedImage getSprite()
	{
		return this.bi_sprite;
	}
	public boolean getSide() {
		return side;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public void setState(State s) {
		this.state = s;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSprite(BufferedImage sprite)
	{
		this.bi_sprite = sprite;
	}
	public ArrayList<Square> getLegalMoves(int x, int y, boolean real)
	{
		return new ArrayList<Square>();
	}
}	
	