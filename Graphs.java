import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class StackX
   {
   private final int SIZE = 20;
   private int[] st;
   private int top;
// ------------------------------------------------------------
   public StackX()           // constructor
      {
      st = new int[SIZE];    // make array
      top = -1;
      }
// ------------------------------------------------------------
   public void push(int j)   // put item on stack
      { st[++top] = j; }
// ------------------------------------------------------------
   public int pop()          // take item off stack
      { 
	    return st[top--];  
	  }
// ------------------------------------------------------------
   public int peek()         // peek at top of stack
      { return st[top]; }
// ------------------------------------------------------------
   public boolean isEmpty()  // true if nothing on stack
      { return (top == -1); }
// ------------------------------------------------------------
   }  // end class StackX
////////////////////////////////////////////////////////////////
class Vertex
   {
   public String name;        
   public String state;
   public boolean wasVisited;
   public int friends_club_number;
   
// ------------------------------------------------------------
   public Vertex(String name, String state)   // constructor
      {
      this.name = name;
      this.state = state;
      wasVisited = false;
	  friends_club_number = -1; //they belong yet.
      }
// ------------------------------------------------------------
   }  // end class Vertex
////////////////////////////////////////////////////////////////
class Graph
   {
   private final int MAX_VERTS = 20;
   private Vertex vertexList[]; // list of vertices
   private int adjMat[][];      // adjacency matrix
   private int nVerts;          // current number of vertices
   private StackX theStack;
   private int count = 1;
// ------------------------------------------------------------
   public Graph()               // constructor
      {
      vertexList = new Vertex[MAX_VERTS];
                                          // adjacency matrix
      adjMat = new int[MAX_VERTS][MAX_VERTS];
      nVerts = 0;
      for(int y=0; y<MAX_VERTS; y++)      // set adjacency
         for(int x=0; x<MAX_VERTS; x++)   //    matrix to 0
            adjMat[x][y] = 0;
      theStack = new StackX();
      }  // end constructor
// ------------------------------------------------------------
   public void addVertex(Vertex vert)
      {
      vertexList[nVerts++] = new Vertex(vert.name, vert.state);
      }
   
   public int getVertexListIndex(String name) {
	   for (int i=0; i<nVerts; i++) {
		   if (vertexList[i].name.equalsIgnoreCase(name))
				   return i;
	   }
	   return -1;
   }
// ------------------------------------------------------------
   public void addEdge(int start, int end)
      {
      adjMat[start][end] = 1;
      adjMat[end][start] = 1;
      }
// ------------------------------------------------------------
   public void displayVertex(int v)
      {
      System.out.print(vertexList[v].name + " ");
      }
   
   public void displayAllVertex() {
	   for(int i=0; i<nVerts; i++) {
		   int count1 = 0;
		   for(int j=0; j<nVerts; j++) {
			   if(adjMat[i][j]==1)
		            count1++;
		   }
		   System.out.println(vertexList[i].name + ", " + vertexList[i].state + ", " + count1);
	   }
   }
// ------------------------------------------------------------

  public void dfs(int start)  // depth-first search
      {     
	  count = 1;
      vertexList[start].wasVisited = true;  // mark it
      vertexList[start].friends_club_number = start;
	  displayVertex(start);                 // display it
      theStack.push(start);                 // push it

      while( !theStack.isEmpty() )      // until stack empty,
         {
         // get an unvisited vertex adjacent to stack top
         int v = getAdjUnvisitedVertex( theStack.peek() );
         if(v == -1)                    // if no such vertex,
            theStack.pop();
         else                           // if it exists,
            {
            vertexList[v].wasVisited = true;
			vertexList[v].friends_club_number= start;
		     count++; 
			// mark it
            displayVertex(v);                 // display it
            theStack.push(v);                 // push it
            }
         }  // end while
		 
//      // stack is empty, so we're done
//      for(int j=0; j<nVerts; j++)          // reset flags
//         vertexList[j].wasVisited = false;
      }  // end dfs
  
  public void dfsNoPrint(int start)  // depth-first search
	  {     
	  count=1;
	  vertexList[start].wasVisited = true;  // mark it
	  vertexList[start].friends_club_number = start;
	//  displayVertex(start);                 // display it
	  theStack.push(start);                 // push it
	
	  while( !theStack.isEmpty() )      // until stack empty,
	     {
	     // get an unvisited vertex adjacent to stack top
	     int v = getAdjUnvisitedVertex( theStack.peek() );
	     if(v == -1)                    // if no such vertex,
	        theStack.pop();
	     else                           // if it exists,
	        {
	        vertexList[v].wasVisited = true;
			vertexList[v].friends_club_number= start;
		     count++; 
			// mark it
	//        displayVertex(v);                 // display it
	        theStack.push(v);                 // push it
	        }
	     }  // end while
	
	  // stack is empty, so we're done
	  for(int j=0; j<nVerts; j++)          // reset flags
	     vertexList[j].wasVisited = false;
	
	//  System.out.println("Count = " + count);
	  }  // end dfsNoPrint
	  
// ------------------------------------------------------------
   // returns an unvisited vertex adj to v
   
   public int getAdjUnvisitedVertex(int v)
      {
      for(int j=0; j<nVerts; j++)
         if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
            return j;
      return -1;
      }  // end getAdjUnvisitedVertex()
   
   //is the graph Connected?
   public Boolean isConnected() {
	   dfsNoPrint(0);
	   if(nVerts == count)
		   return true;
	   else 
		   return false;   
   }
   
   //Biconnected components?
   public void connectedComponent() {
	   for(int i=0; i<nVerts; i++) {
		   if(vertexList[i].wasVisited == false) {
			   dfs(i);
			   System.out.println();
		   }
	   }
   }
   
 //Articulation points?
   public void artPoint() {
	   for(int j=0; j<nVerts; j++)          // reset flags
		     vertexList[j].wasVisited = false;
	   for(int i=0; i<nVerts; i++) {
		   vertexList[i].wasVisited = true;
		   if(i == 0) 
			   dfsNoPrint(1);
		   else 
			   dfsNoPrint(0);
		   if(nVerts-1 != count)
			   System.out.println(vertexList[i].name);
	   }
   }
   
  //Search for path between 2 points?
   public void searchPath(String startVertex, String endVertex) {
	   boolean found = false;
	   theStack.push(getVertexListIndex(startVertex));
	   List<Integer> visited = new ArrayList<>();
	   do {
		   int x = theStack.pop();
		   if(x == getVertexListIndex(endVertex))
			   found = true;
		   else {
			   while(getAdjUnvisitedVertex(x) != -1) {
				   int y = getAdjUnvisitedVertex(x);
				   visited.add(y);
				   vertexList[y].wasVisited = true;
				   theStack.push(y);
			   }
		   }
	   }while(!theStack.isEmpty() && !found);
	   
	   //Restore "wasVisited"
	   for(int ii: visited)
		   vertexList[ii].wasVisited = false;
	   if(!found)
		   System.out.println("Path does not exist.");
	   else
		   System.out.println("Path exists.");
   }
// ------------------------------------------------------------
   } 
public class Graphs {
	public static void main(String[] args) {
		int choice = 0;
		Graph theGraph = new Graph();
		
		while(choice != 6)
	    {
	    System.out.println("ONLY enter a number: ");
	    System.out.println("1. Load Airport Data\n"
	    		+ "2. Print All Airports\n"
	    		+ "3. Check if there is a path between every given two airports in the data\n"
	    		+ "4. Display all the critical airports\n"
	    		+ "5. Check if there is a path (flight) in between two given airports\n"
	    		+ "6. Exit");
	    Scanner input = new Scanner(System.in);
	    choice = input.nextInt();
	    switch(choice)
	       {
	       case 1:
	    	   System.out.println("Loading airport data now.");
	    	   //while loop to read V.txt file
	    	   try {
	   				String fileName1 = "src/V.txt";
	   				FileReader reader1 = new FileReader(fileName1);
	   				Scanner input1 = new Scanner(reader1);
	   				String line1 = input1.nextLine();
	   				
	   				while(input1.hasNextLine()) {
	   					String line2 = input1.nextLine();
	   					if(line2.equals(""))
	   						break;
	   					String[] split= line2.split(",");
	   					Vertex a = new Vertex(split[0], split[1]);
	   					theGraph.addVertex(a);
	   				}
	   				input1.close();
	   			}catch(IOException ioe) {
	   				ioe.printStackTrace();
	   	            return;
	   			}
	    	   //while loop to read E.txt file
	    	   try {
	   				String fileName1 = "src/E.txt";
	   				FileReader reader1 = new FileReader(fileName1);
	   				Scanner input1 = new Scanner(reader1);
	   				while(input1.hasNextLine()) {
	   					String line1 = input1.nextLine();
	   					if(line1.equals(""))
	   						break;
	   					String[] split= line1.split(",");
	   					String a = split[0];
	   					String b = split[1];
	   					int index1 = theGraph.getVertexListIndex(a);
	   					int index2 = theGraph.getVertexListIndex(b);
	   					theGraph.addEdge(index1, index2);;
	   				}
	   				input1.close();
	   			}catch(IOException ioe) {
	   				ioe.printStackTrace();
	   	            return;
	   			}
	    	  break;
	    	   
	       case 2:
	    	   System.out.println("Printing all airports now.");
	    	   theGraph.displayAllVertex();
	    	  break;
	    	  
	       case 3:
	    	   System.out.println("Checking if there is a path now.");
	    	   if (theGraph.isConnected() == true)
	    	    	  System.out.println("The airport graph is connected, and there is a path between every given two airports in the data.");
	    	      else {
	    	    	  System.out.println("The airport graph is not connected.");
	    	    	  System.out.println("The connected components are: ");
	    	    	  theGraph.connectedComponent();
	    	      }
	    	   break;
	    	   
	       case 4:
	    	   System.out.println("Displaying all the critical airports now.");
	    	   theGraph.artPoint();
	    	   break;
	    	   
	       case 5:
	    	   System.out.println("Checking if there is a path now. Please enter one airport (in capital letters): ");
	    	   Scanner in = new Scanner(System.in);
	    	   String air1 = in.nextLine();
	    	   System.out.println("Please enter another airport (in capital letters): ");
	    	   String air2 = in.nextLine();
	    	   theGraph.searchPath(air1, air2);
	    	   break;
	    	   
	       
	       }
	    }
	}
}
