package update_later;

import java.awt.Color;
import java.util.ArrayList;

public class Square
{
	private int x, y;
	private Color c;
	private Unit p;
	private Objective ob;
	boolean b_isHighlighted;
	
	public Square(int x, int y, Color c){
		this.setX(x);
		this.setY(y);
		this.setColor(c);
		this.setChesspiece(null);
		this.setHighlighted(false);
		this.setOb(null);
		this.setUnit(null);
	}
	
	public Objective getOb() {
		return ob;
	}

	public void setOb(Objective ob) {
		this.ob = ob;
	}

	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public Color getColor(){
		return this.c;
	}
	
	public Unit getUnit(){
		return this.p;
	}
	
	public boolean getHighlighted(){
		return this.b_isHighlighted;
	}
	

	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setColor(Color c){
		this.c = c;
	}
	
	public void setChesspiece(Unit p){
		this.p = p;
	}
	
	public void setHighlighted(boolean b){
		this.b_isHighlighted = b;
	}
	public void setUnit(Unit u) {
		this.p = u;
	}
	
	//Gets this square's location on the board in chess coordinates.
	public static String getChessCoordinates(int x, int y){
		String s = "";
		s += (char)(x + 97);
		s += (8 - y);
		return s;
	}
	
	//Gets the union of two sets of Squares, a and b.
	public static ArrayList<Square> union(ArrayList<Square> a, ArrayList<Square> b){
		ArrayList<Square> u = new ArrayList<Square>();
		
		for(Square s : b){
			if(a.contains(s)) a.remove(s);
		}
		
		u.addAll(a);
		u.addAll(b);
		
		return u;
	}
	
	//Gets the intersection of two sets of Squares, a and b.
	public static ArrayList<Square> intersection(ArrayList<Square> a, ArrayList<Square> b){
		ArrayList<Square> n = new ArrayList<Square>();
		
		for(Square s : b){
			if(a.contains(s)) n.add(s);
		}
		
		return n;
	}
	
}