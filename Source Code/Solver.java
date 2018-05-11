import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 * 
 * @author Ayoub Sanhaji
 *
 */

public class Solver 
{
	// Noeud but 
	private Node goalNode;  
    // Liste des noeuds ouverts (à explorer)
    private List<Node> openList;
    // Liste des noeuds fermés (explorés)
    private List<Node> closedList; 
 
    // Constructeur implémente l'algorithme A*
    public Solver(Board initial)
    {
        // Initialisation
        openList = new ArrayList<Node>();
        closedList = new ArrayList<Node>();
        Node currentNode = new Node(initial, null, 0);
        openList.add(currentNode);
        while(!currentNode.isGoal() && !openList.isEmpty())
        {
            currentNode = Node.bestNode(openList);
            openList.remove(currentNode);
            closedList.add(currentNode);
            for(Board b: currentNode.neighbors())
                if(b.isExist(closedList) == -1)
                {
                    if(b.isExist(openList) != -1)
                    {
                        openList.add(b.isExist(openList), new Node(b, currentNode, currentNode.g()+1));
                    }
                    else
                    {
                        openList.add(new Node(b, currentNode, currentNode.g()+1));
                    }
                }	
        }
    }

    // Recontruit le chemin de la configuration initiale à la configuration but
    public List<Board> getPathSolution()
    {
        List<Board> res = new ArrayList<Board>();
        for(int i=0 ; i<closedList.size() ; i++)
            res.add(closedList.get(i).getBoard());
        return res;
    }

    // Recharge un fichier puzzle
    public static int [][] loadFile(String fileName)
    {
        int [][] block = null;
        try 
        {
            File f = new File(fileName);
            Scanner scanner = new Scanner(f);
            // la première ligne : taille du puzzle
            Scanner sc = new Scanner(scanner.nextLine());
            int N= sc.nextInt();
            block = new int [N][N];
            int i=0;
            while(scanner.hasNext()) 
            {
                sc = new Scanner(scanner.nextLine());
                int j=0;
                while(sc.hasNext()) 
                {
	                block[i][j]= sc.nextInt();
	                j++; 
                }
                i++;
            }
            // les autres lignes la grille du puzzle
            sc.close();
        } 
        catch (FileNotFoundException exception) 
        {
            System.out.println("File not found");
        }
        return block; 
    }

    public static void main(String[] args) 
    {
        // Créer un nouveau board à partir d'un fichier
        //int [][] block = loadFile("./puzzles/puzzle04.txt");
        int [][] block = {{0,2,3},{1,4,5},{7,8,6}};
        Board initial = new Board(block);

        // Résoudre le puzzle
        Solver sol = new Solver(initial);

        // Imprimer la solution si elle existe
        List res = sol.getPathSolution();
        if(res != null)
            for(int i=0 ; i<res.size() ; i++)
                System.out.println("Board N°: "+(i+1)+"\n"+res.get(i).toString());
    }
}
