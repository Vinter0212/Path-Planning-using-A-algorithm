package astarfinal;
import java.util.*;

public class astartry {

	    public static final int d_cost = 14; // Value of square root 2 =1.414
	    public static final int reg_cost = 10; 
	    
	    static class Cell{  
	        int hval = 0; //H value (Heuristic)
	        int fval = 0; //F value (F=G+H)
	        int y, x; // Cell Coordinates
	        Cell parent;  //Parent of cell
	        
	        Cell(int y, int x){  //Create a Cell structure
	            this.y = y;
	            this.x = x; 
	        }
	    }
	    
	    static Cell [][] grid = new Cell[258][203]; //Depends on the image size
	    
	    static PriorityQueue<Cell> open; //Open list (Collection of generated nodes) is implemented as Priority Queue so that we can dequeue the best node
	     
	    static boolean closed[][]; // Closed list (Collection of expanded nodes)
	    static int startY, startX, endY, endX; //Start and End coordinates
	    

	    static void update(Cell current, Cell adj, int cost){
	    	
	        if(adj == null || closed[adj.y][adj.x]) //If blocked or already visited, ignore
	        	return;
	        
	        int adj_fval = adj.hval +cost; // F value of the new Node
	        
	        if(!(open.contains(adj)) || adj_fval<adj.fval){ //if not in open list or if the cost is less than old cost
	        	adj.fval = adj_fval; 
	        	adj.parent = current; //set current as parent node of adjacent node
	            if(!(open.contains(adj)))
	            		open.add(adj); //if not in open list, add it to open list
	        }
	    }
	    
	    public static void AStar(){ 
	        
	        //add the start coordinates to open list.
	        open.add(grid[startY][startX]);       
	        Cell current;
	
	        while(true){ 
	            current = open.poll(); //retrieves first element of Queue
	            if(current==null)break; //if we reach a blocked cell
	            
	            closed[current.y][current.x]=true; //add the cell to Closed list

	            if(current.equals(grid[endY][endX])){ //if we reach the destination
	                return; 
	            } 

	            Cell adj;  
	            if(current.y-1>=0){ //Checks if Valid y coordinate on top
	               adj= grid[current.y-1][current.x]; //Top adjacent cell
	                update(current, adj, current.fval+reg_cost); 

	                if(current.x-1>=0){     
	                   adj= grid[current.y-1][current.x-1];  //Top Left adjacent cell 
	                    update(current,adj, current.fval+d_cost); 
	                }

	                if(current.x+1<grid[0].length){ 
	                   adj= grid[current.y-1][current.x+1]; //Top right adjacent cell
	                    update(current,adj, current.fval+d_cost); 
	                }
	            } 

	            if(current.x-1>=0){ //Check if Valid X coordinate on left
	               adj= grid[current.y][current.x-1]; //Left adjacent cell
	                update(current,adj, current.fval+reg_cost); 
	            }

	            if(current.x+1<grid[0].length){ //Check if Valid X coordinate on right
	               adj= grid[current.y][current.x+1]; //Right adjacent cell
	                update(current,adj, current.fval+reg_cost); 
	            }

	            if(current.y+1<grid.length){ //Check if Valid Y coordinate below
	               adj= grid[current.y+1][current.x]; //Down adjacent cell
	                update(current,adj, current.fval+reg_cost); 

	                if(current.x-1>=0){ 
	                   adj= grid[current.y+1][current.x-1]; //Left Down adjacent cell
	                    update(current,adj, current.fval+d_cost); 
	                }
	                
	                if(current.x+1<grid[0].length){
	                  adj= grid[current.y+1][current.x+1]; //Right Down adjacent cell
	                   update(current,adj, current.fval+d_cost); 
	                }  
	            }
	        } 
	    }
	    

	    public List<Cell> pathExt( int height, int width, int sourceY, int sourceX, int destY, int destX, int[][] maze){
	       
	           grid = new Cell[height][width];
	           closed = new boolean[height][width];
	           open = new PriorityQueue<>((Object ob1, Object ob2) -> {
	                Cell c1 = (Cell)ob1;
	                Cell c2 = (Cell)ob2;
	                return c1.fval>c2.fval?1:
	                        c1.fval<c2.fval?-1:0;
	            });
	           
	           //Source coordinates
	           startY = sourceY;
		       startX = sourceX;  
	           
	           //Destination coordinates
		       endY = destY;
		       endX = destX; 
	           
	           //Set grid coordinates and Hvalue
	           for(int y=0;y<height;++y){
	              for(int x=0;x<width;++x){
	                  grid[y][x] = new Cell(y, x); //Set coordinates for every cell
	                  grid[y][x].hval  = (int)(Math.hypot(y - destY, x - destX)); //Based on Euclidean distance
	              }
	           }
	             
	           // Set blocked cells to Null
	           for(int i=0;i<height;++i){
	        	   for(int j=0;j<width;++j)
	        	   {
	        		   if(maze[i][j]!=0) //if blocked
	        			   grid[i][j] = null; 
	        	   }         
	           }
	           
	           grid[sourceY][sourceX].fval = 0; //F value initally for source is 0
	           
	           
	           AStar(); //Call the Astar function
	      
	           List<Cell> path=new ArrayList<>(); //To store the optimal path
	           
	           if(closed[endY][endX]){ 	//If we are able to reach the destination from source   
	               //Trace back the path       
	                Cell current = grid[endY][endX]; 
	                path.add(0,current);   // Add the destination cell to path
	                while(current.parent!=null){ //Traverse to the source from destination using parents
	                	path.add(0,current.parent); 
	                    current = current.parent;
	                }   
	           }
	           return path;
	    }
}