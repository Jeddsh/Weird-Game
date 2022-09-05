package update_later;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import update_later.Unit.State;

public class Objective {
	protected BufferedImage bi_spriteSheet;
	private BufferedImage bi_sprite;
	private boolean isRed;
	private int defense;
	private capState state;
	
	enum capState{
		SEIGED,
		NOTSEIGED
	}
	public Objective(boolean red, int def){
		this.isRed = red;
		this.defense = def;	
		setState(capState.NOTSEIGED);
		try
		{
			this.bi_spriteSheet = ImageIO.read(new File("pixel-art.png"));
		} 
		catch (IOException ioe)
		{
			System.out.println("Couldn't load sprites.");
			System.exit(0);
		}
		this.setSprite(bi_spriteSheet.getSubimage(60, this.isRed()?60:0, 60, 60));
	}
	public int getDefense() {
		return defense;
	}
	public capState getState() {
		return this.state;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public void setState(capState s) {
		this.state = s;
	}
	public boolean isCapturable(Unit u) {
		if (state == capState.SEIGED && u.getState() == State.UNMOVED){
			return true;
		}
		return false;
		
	}
	public boolean isRed() {
		return isRed;
	}
	public void setRed(boolean isRed) {
		this.isRed = isRed;
	}
	public BufferedImage getSprite() {
		return bi_sprite;
	}
	public void setSprite(BufferedImage bi_sprite) {
		this.bi_sprite = bi_sprite;
	}
}