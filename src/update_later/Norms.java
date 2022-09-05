package update_later;

import java.util.ArrayList;

public class Norms extends Unit {
	
	public Norms(boolean side) {
		super(5, 2, 0, "clubber", 1, side);
		this.setSprite(super.bi_spriteSheet.getSubimage(0, side?60:0, 60, 60));
	}
		
	public ArrayList<Square> getLegalMoves(int x, int y, boolean real)
	{	
			Square[][] squares = Mainwindow.Board.getSquares();
			ArrayList<Square> list = super.getLegalMoves(x,y,real);
			for(int i = 0; i <= 7; i++)
			{
				for(int j = 0; j <= 7; j++)
				{
					//Only allow moves to the correct squares
					if((x-i)*(x-i) + (y-j)*(y-j) == 1||(x-i)*(x-i) + (y-j)*(y-j) == 2)	
					{
						//Only allow moves not occupied by an allied piece.
						if((squares[i][j].getUnit() == null||squares[i][j].getUnit().getSide() !=this.getSide())&&(squares[i][j].getOb() != null|squares[i][j].getOb()==null))
						{
							list.add(squares[i][j]);
						}
						
					}
				}
			}
			return list;
	}
}