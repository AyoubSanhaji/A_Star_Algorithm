import java.util.*;
import org.omg.CORBA.portable.IndirectionException;

/**
 * 
 * @author Ayoub Sanhaji
 *
 */

public class Node 
{
    // La grille du noeud
	private Board board;
	// Le noeud pere
	private Node previous;
    // Le coût exacte
	private int g;
    // Le coût estime
	private int h;
	
	//Constructeur
	public Node(Board b, Node prev, int m)
	{
		board = new Board(b.getBlock());
		previous = prev;
		g = m;
		h = h();
	}

	public Board getBoard() 
	{
		return board;
	}

	public Node getPrevious() 
	{
		return previous;
	}

	public int f()
	{
		return g+h;
	}
	
	// Nombre de mouvements ayant mene a l'etat courant
	public int g()
	{
		return g;
	}
	
	// L'estimation du cout restant au but
	public int h()
	{
		return h1();
	}
	
	// H1: Nombre de cases mal placees
	private int h1()
	{
		int malPlacees=0;
		for(int i=0 ; i<board.getSize() ; i++)
			for(int j=0 ; j<board.getSize() ; j++)
			{
				if((3*i+j+1!=9) && (board.getBlock()[i][j]!=3*i+j+1))
					malPlacees++;
			}
		return malPlacees;
	}
	
	// H2: la somme des distances des cases par rapport a leurs positions cibles
	private int h2()
	{
		int dist=0;
		
		for(int i=0 ; i<board.getSize() ; i++)
			for(int j=0 ; j<board.getSize() ; j++)
			{
				int X = (board.getBlock()[i][j]-1) / 3;
				int Y = board.getBlock()[i][j] - 3*X - 1;
				// Distance de Manhattan
				if((3*i+j+1!=9) && (board.getBlock()[i][j]!=3*i+j+1))
					dist += Math.abs(X-i) + Math.abs(Y-j);
			}
		return dist;
	}
	
    // Elle retourne le meilleur noeud (plus petit valeur de h())
	public static Node bestNode(List<Node> l)
	{
		int indice = 0;
		int hmin = l.get(indice).h;
		for(int i=1 ; i<l.size() ; i++)
		{
			if(hmin > l.get(i).h)
			{
				hmin = l.get(i).h;
				indice = i;
			}
		}
		return l.get(indice);
	}
	
    // Teste si le noeud contient une configuration finale
	public boolean isGoal()
	{
		for(int i=0 ; i<board.getSize() ; i++)
			for(int j=0 ; j<board.getSize() ; j++)
			{
				if((3*i+j+1!=9) && (board.getBlock()[i][j]!=3*i+j+1))
					return false;
			}
		return true;
	}
	
    // Elle retourne une pile des noeuds voisins obtenus en deplacant la case vide
	public Stack<Board> neighbors()
	{
		Stack<Board> n = new Stack<Board>();
		int[][] grille;
		int zeroRow = board.getZeroRow();
		int zeroCol = board.getZeroCol();
		// haut : (i-1) (j) <=> (i) (j)
		if(zeroRow-1 >= 0)
		{
			grille = board.copy();
			grille[zeroRow][zeroCol] = grille[zeroRow-1][zeroCol];
			grille[zeroRow-1][zeroCol] = 0;
			n.push(new Board(grille));
		}
		// bas : (i+1) (j) <=> (i) (j)
		if(zeroRow+1 < board.getSize())
		{
			grille = board.copy();
			grille[zeroRow][zeroCol] = grille[zeroRow+1][zeroCol];
			grille[zeroRow+1][zeroCol] = 0;
			n.push(new Board(grille));
		}
		// gauche : (i) (j-1) <=> (i) (j)
		if(zeroCol-1 >= 0)
		{
			grille = board.copy();
			grille[zeroRow][zeroCol] = grille[zeroRow][zeroCol-1];
			grille[zeroRow][zeroCol-1] = 0;
			n.push(new Board(grille));	
		}
		// droite : (i) (j+1) <=> (i) (j)
		if(zeroCol+1 < board.getSize())
		{
			grille = board.copy();
			grille[zeroRow][zeroCol] = grille[zeroRow][zeroCol+1];
			grille[zeroRow][zeroCol+1] = 0;
			n.push(new Board(grille));	
		}
		return n;
	}
}