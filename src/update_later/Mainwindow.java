package update_later;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.*;

import update_later.Objective.capState;
import update_later.Unit.State;



public class Mainwindow {
	@SuppressWarnings("serial")
	public static class Board extends JPanel 
	{	
		private final int squareSize = 500/10;
		private static Square[][] squares = new Square[8][8];
		//move list for pieces
		ArrayList<String> moveList = new ArrayList<String>();
		//mouse position
		int mpx, mpy;
		int redEconomy = 1, blueEconomy = 1;
		Unit movingUnit, promtingUnit,statsUnit;
		boolean isProducing, showStats = false;
		boolean redMove = true, redMoving = false, redWin = false, blueWin = false;
		public Board() {
			this.setBackground(Color.BLACK);
			this.addMouseListener(mouseActions);
			this.addMouseMotionListener(mouseActions);
			Color clr= new Color(251,201,38);
			
			for(int j = 0; j <= 7; j++) 
			{
				for(int i = 0; i <= 7; i++)
				{	
					Board.squares[i][j] = new Square(i,j,clr);
				}
			}
			this.initialize();
		}
		private void initialize() {
			
			squares[4][7].setOb(new Objective(true, 1));
			squares[3][0].setOb(new Objective(false, 1));
			squares[4][7].setUnit(new Norms(redMove));
			squares[3][0].setUnit(new Norms(!redMove));
			squares[4][7].getUnit().setState(State.UNMOVED);
			squares[3][0].getUnit().setState(State.UNMOVED);
			
		}
		
		public void produce() {
			if(!redMove) {
				if(squares[3][0].getUnit() == null) {
					if(blueEconomy >= 1) {
						squares[3][0].setUnit(new Norms(redMove));
						blueEconomy--;
					}
				}
			}else {
				if(squares[4][7].getUnit() == null) {
					if(redEconomy >= 1) {
						squares[4][7].setUnit(new Norms(redMove));
						redEconomy--;
					}
				}
			}
		}
		public static Square[][] getSquares(){
			return squares;
		}
		public void endTurn() {
			for(Square[] ss : Board.squares) {
				for(Square s : ss) {
					if(s.getUnit() != null) {
						if(s.getUnit().getState() == State.MOVED) {	
							s.getUnit().setState(State.UNMOVED);
						}
					}
				}
			}
			endGame();
			if(redMove) {redEconomy++;
			}
			else {blueEconomy++;
			}
			redMove = !redMove;
		}
		public void endGame() {
			if(squares[4][7].getOb().getState()==capState.SEIGED) {
				if(redMove) {
					blueWin = true;
				}
			}
			if(squares[3][0].getOb().getState()==capState.SEIGED) {
				if(!redMove) {
					redWin = true;
				}
			}
		}
		public void moveUnit(Square cs, Square ds){
			movingUnit = cs.getUnit();
			
			cs.setUnit(null);
			if(ds.getUnit() != null && ds.getUnit().getSide() != redMove &&( Math.abs(cs.getX() - ds.getX()) == 1 || Math.abs(cs.getY() - ds.getY()) == 1))
			{
				this.attack(movingUnit,ds.getUnit());
				if (ds.getUnit().getHp() <= 0) {
					ds.setUnit(null);
				}
				else {
					this.attack(ds.getUnit(), movingUnit);
					if(movingUnit.getHp() <= 0) {
						return;
					}
					cs.setUnit(movingUnit);
					movingUnit.setState(State.MOVED);
					return;
				}
			}
			ds.setUnit(movingUnit);
			if(ds.getOb() != null) {
				ds.getOb().setState(capState.SEIGED);
			}
			movingUnit.setState(State.MOVED);
			
		}
		public void attack(Unit att, Unit def) {
			int attack = att.getAtk()- def.getDefense();
			def.setHp(def.getHp()-attack);
			if(def.getDefense() != 0) {
				def.setDefense(def.getDefense() - 1);
			}
		}
		@Override
		public void paintComponent(Graphics g) {super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;	
		if(blueWin||redWin) {
			g2d.setFont(new Font(g2d.getFont().getFontName(),Font.BOLD, 100 * this.squareSize/100));
			g2d.setColor(Color.WHITE);
			g2d.drawString(blueWin?"Blue Wins!":"Red Wins!", 125,250);
		}
		else {
			g2d.setFont(new Font(g2d.getFont().getFontName(),Font.BOLD, 30 * this.squareSize/100));
			g2d.setColor(Color.WHITE);
			if(redMove) {
				g2d.drawString("Red's Turn", 20, 20);
				g2d.drawString("Your points: " + redEconomy, 180, 20);
			} else {
				g2d.drawString("Blue's Turn", 20, 20);
				g2d.drawString("Your points: " + blueEconomy, 180, 20);
			}
			g2d.drawString("End Turn", 200, 480);
			//Draw the board onto the screen.
			AffineTransform at = new AffineTransform();
			at.scale(this.squareSize/60.0, this.squareSize/60.0);
			at.translate(60, 60);
	    
			for(int x = 0; x <= 7; x++)
			{
				for(int y = 0; y <= 7; y++)
				{
					g2d.setColor(Board.squares[x][y].getColor());
					g2d.fillRect((x+1)*this.squareSize, (y+1)*this.squareSize, this.squareSize - 5, this.squareSize - 5);
					if(Board.squares[x][y].getOb() != null)
					{
						at.translate(60*x, 60*y);
			    		g2d.drawImage(Board.squares[x][y].getOb().getSprite(), at, this);
			    		at.translate(-60*x, -60*y);
					}
					if(Board.squares[x][y].getUnit() != null)
					{
						at.translate(60*x, 60*y);
						g2d.drawImage(Board.squares[x][y].getUnit().getSprite(), at, this);
			    		at.translate(-60*x, -60*y);
					}
					
					if(Board.squares[x][y].getHighlighted())
					{
						g2d.setColor(new Color(150,150,150,192));
						g2d.fillRect((x+1)*this.squareSize, (y+1)*this.squareSize, this.squareSize, this.squareSize);
					}
				}
			}
			
			if(showStats) {
				g2d.drawImage(statsUnit.getSprite(),null, 20, 500);
				g2d.drawString("Hp: "+ statsUnit.getHp()+ "  Atk: "+ statsUnit.getAtk() + "  Movement: 1  Def: "+ statsUnit.getDefense(), 50, 500);
			}
		}
			
		g2d.dispose();
		}
		private MouseAdapter mouseActions = new MouseAdapter()
		{
			//When any mouse button is pressed, do this.
			@Override
			public void mousePressed(MouseEvent e)
			{	
				if((e.getX()>= 195 && e.getX() <= 270)&&(e.getY() <= 485 && e.getY() >= 460)) {
					endTurn();
				}
				int x = (e.getX()/squareSize) - 1;
				int y = (e.getY()/squareSize) - 1;
				
					if(x >= 0 && x <= 7 && y >= 0 && y <= 7)
					{
						if(!redMoving)
						{
							if(squares[x][y].getUnit() != null)
							{
								
								showStats = true;
								statsUnit = squares[x][y].getUnit();
								if(squares[x][y].getUnit().getSide() == redMove && squares[x][y].getUnit().getState() == State.UNMOVED)
								{						
									for(Square s : squares[x][y].getUnit().getLegalMoves(x,y,true))
									{
										s.setHighlighted(true);
										
										movingUnit = squares[x][y].getUnit();
										mpx = x;
										mpy = y;
										System.out.println(x+' '+y);
										redMoving = true;
									}								
								}
							}
							else if(squares[x][y].getOb() != null && squares[x][y].getOb().isRed()==redMove&&squares[x][y].getUnit()==null) {
								produce();
							}
						}
						else
						{
							//Check if a highlighted square was clicked on.
							if(squares[x][y].getHighlighted())
							{
								
								moveUnit(squares[mpx][mpy],squares[x][y]);
							
							}		
							
							//Reset the board and unhighlight all Squares.
							redMoving = false;
							movingUnit = null;
							for(Square[] s : squares)
							{
								for(Square t : s)
								{
									t.setHighlighted(false);
								}
							}
							showStats = false;
						}
					} 
				repaint();
			}
			
		};
		 
	}

	private JFrame MainFrame;
	private JMenuBar MainMenubar;
	public void frame() {
		MainMenubar = new JMenuBar();
		GraphicsConfiguration gc = null;
		MainFrame = new JFrame("The Basic Game", gc);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainFrame.setSize(500,600);
		MainFrame.setMinimumSize(new Dimension(500,600));
		MainFrame.setResizable(true);
		MainFrame.setBackground(Color.BLACK);
		MainFrame.setVisible(true);
		MainFrame.setJMenuBar(MainMenubar);
		MainFrame.getContentPane().add(new Board());
		MainFrame.pack();
	}
	public Mainwindow() {
		frame();
	}
	public static void main(String[] Args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { new Mainwindow();}
		});
	}
	
	
	
}