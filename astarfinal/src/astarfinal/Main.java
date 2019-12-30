package astarfinal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;


import astarfinal.astartry;
import astarfinal.astartry.Cell;

public class Main {
	
	public static void main(String[] args) {
		
		//read Image and convert it to Grayscale
		rgbToGrayscaleImage ob1= new rgbToGrayscaleImage("C:\\Users\\vinay\\Desktop\\alwar.tif");
		int width= ob1.getWidth();
		int height=ob1.getHeight();
		int[][] binmap=ob1.grayscaleMap(height,width,135); //135 is threshold for object detection
		//binmap contains a 2d matrix with 0's as objects and -1's as accessible cells
		
		morphOp ob2 = new morphOp(); //Creates an object of morphOp class for morphological operations
		binmap=ob2.morph(binmap,500,height,width,6); // Performs morphological operation on the binmap
		// 500 corresponds to number of iterations of morphological operation
		// 6 represents the number of neighbors for a cell based on which the operation should be performed
		
		int[][] maze = ob2.complement(binmap, height, width);// reverse the cell values: 0 accessible -1 objects
			
		
		int sourceY=249; //Y coordinate of source
		int sourceX=6; //X coordinate of source
		int destY=25; //Y coordinate of destination
		int destX=160; //X coordinate of destination
		
		if(maze[sourceY][sourceX]==-1 || maze[destY][destX]==-1) //Check if the source/destination are blocked
		{
			System.out.println("The source/destination are themselves obstacles");
			return;
		}
		
		astartry as = new astartry(); //Create object for Astar algorithm
        List<Cell> path = as.pathExt(height,width,sourceY,sourceX,destY, destX,maze); //Get the optimal path
        
        if (path != null) { //If the path exists
        	
        	BufferedImage img2 = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY); //Create an image with the path
    		for(int y = 0; y < height; y++){ 
      	    	for(int x = 0; x < width; x++){	
      	    	img2.setRGB(x, y, binmap[y][x]); //set pixel values in the created image
      	    	}   	
      	    } 
    		
            path.forEach((n) -> {
                System.out.print("[" + n.y + ", " + n.x + "] "); //Outputs the optimal path cells  
                img2.setRGB(n.x, n.y, (0<<24) | (0<<16) | (0<<8) | 0 ); //Adds the pixel for optimal path in the image 
            });

     	    try{
     	  	      File f = new File("C:\\Users\\vinay\\Desktop\\finalPath.jpg"); //Store the output image
     	  	      ImageIO.write(img2, "jpg", f);
     	  	    }catch(IOException e){
     	  	      System.out.println(e);
     	  	    }
        }
        else //If no path exists
        {
        	System.out.println("No Path exists");
        }
	}// main ends
}// class ends