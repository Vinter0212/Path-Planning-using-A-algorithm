package astarfinal;



import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class rgbToGrayscaleImage { //contains functions for processing image and converting to grayscale

	private BufferedImage img;
	
	rgbToGrayscaleImage(String dir_path) //Constructor that reads the image from the path specified
	{
  	    File f = null;
  	    try{
  	      f = new File(dir_path);
  	    this.img = ImageIO.read(f);
  	    }
  	    catch(IOException e){
  	      System.out.println(e);
  	    }
	}
	
	public int getHeight() // Get height of the image
	{
		return this.img.getHeight();
	}
	
	public int getWidth() // Get width of the image
	{
		return this.img.getWidth();
	}
	
	public int[][] grayscaleMap(int height, int width, int threshold)
	{
		int[][] binmap=new int[height][width];
		for(int y = 0; y < height; y++){
	  	      for(int x = 0; x < width; x++){
	  	        int rgb = this.img.getRGB(x,y); //get the RGB value

	  	        int r = (rgb>>16)&0xff; //get R value
	  	        int g = (rgb>>8)&0xff; //get G value
	  	        int b = rgb&0xff; //get B value

	  	        int avg = (int)(0.2989 * r + 0.5870 * g + 0.1140 * b) ; //MATLAB formula for RGB to grayscale

	  	        if(avg>threshold) //if Grayscale value is greater than threshold : Accessible
	  	            binmap[y][x]=-1;
	  	        else // Otherwise an Object
	  	            binmap[y][x]=0;   
	  	      }  
	  	    }
		return binmap;
	}
		
}


