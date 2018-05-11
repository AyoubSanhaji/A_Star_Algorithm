import java.util.List;

/**
 * 
 * @author Ayoub Sanhaji
 *
 */

public class Board 
{
	// Grille de jeu
	private int block[][];
	// Sa dimension
	private int size;
	// Position de la case vide
	private int zeroRow;
	private int zeroCol;
	
	// Constructeur qui instancie l'objet avec une grille donnee
	public Board(int b[][])
	{
		size = b.length;
		block = new int[size][size];
		for(int i=0 ; i<size ; i++)
			for(int j=0 ; j<size ; j++)
			{
				block[i][j] = b[i][j];
				if(b[i][j] == 0)
				{
					zeroRow = i;
					zeroCol = j;
				}
			}
	}

	public int[][] getBlock() 
	{
		return block;
	}

	public int get(int i, int j)
	{
		return block[i][j];
	}

	public int getZeroRow() 
	{
		return zeroRow;
	}

	public int getZeroCol() 
	{
		return zeroCol;
	}

	public int getSize() 
	{
		return size;
	}
	
	// Retourne une copie de la grille de l'instance
	public int[][] copy()
	{
		Board cp = new Board(this.block);
		return cp.block;
	}
	
	// Verifie l'egalite de deux instances
	public boolean equals(Board y)
	{
		if(y.size == size)
		{
			for(int i=0 ; i<size ; i++)
				for(int j=0 ; j<size ; j++)
				{
					if(block[i][j] != y.block[i][j])
						return false;
				}
			return true;
		}
		return false;
	}
	
    // Verifie l'existence du Board d'un noeud dans une liste des noeuds
    // S'il existe elle nous retourne son indice
	public int isExist(List<Node> l)
	{
		for(int i=0 ; i<l.size() ; i++)
		{
			Board b = l.get(i).getBoard();
			if(this.equals(b))
				return i;
		}
		return -1;
	}
	
    // Affichage du Board
	public String toString()
	{
		String res = "";
		for(int i=0 ; i<size ; i++)
		{
			for(int j=0 ; j<size ; j++)
				res += block[i][j]+"\t";
			res += "\n";
		}
		return res;
	}
}