package astarfinal;


public class morphOp {

	public int[][] morph(int [][] binmap,int iterations, int height, int width, int morphpara)
	{
		for( int iter=1;iter<=iterations;iter++)
  	    {
			for(int y = 0; y < height; y++){
  		      	for(int x = 0; x < width; x++){
  		      		if((y==0 ||y== height-1)&& x>0 && x<width-1) //for first and last row
  		      		{
  		      			if(binmap[y][x-1]!=0 && binmap[y][x+1]!=0) //if adjacent cells are accessible
  		      				binmap[y][x]=-1;
  		      			if(binmap[y][x-1]==0 && binmap[y][x+1]==0) //if adjacent cells are blocked
  		      				binmap[y][x]=0;
  		      		}
  		      		if((x==0|| x==width-1) && y>0 && y<height-1) //for first and last column
  		      		{
  		      			if(binmap[y-1][x]!=0 && binmap[y+1][x]!=0) //if adjacent cells are accessible
  		      				binmap[y][x]=-1;
  		      			if(binmap[y-1][x]==0 && binmap[y+1][x]==0) //if adjacent cells are blocked
  		      				binmap[y][x]=0;
  		      		}
  		      		if(x>0 && x<width-1&& y>0 && y<height-1) // for all other rows and cells
  		      		{
  		      			int count1=0; // counts number of accessible adjacent cells
  		      			int count0=0; //// counts number of blocked adjacent cells
  		      			for(int a=-1;a<=1;a++) //iterates through the adjacent cells 
  		      			{
  		      				for(int b=-1;b<=1;b++)
  		      				{
  		      					if(a==0 && b==0) // This is the case when we are looking at the cell itself
  		      						continue;
  		      					if(binmap[a+y][b+x]==0) // If the adjacent cell is blocked
  		      						count0++;
  		      					else // Otherwise accessible
  		      						count1++;
  		      				}
  		      			}
  		      			if(count0>=morphpara) //if total adjacent blocked cells are >= threshold(6) MATLAB fill
  		      				binmap[y][x]=0;
  		      			if(count1>=morphpara) //if total adjacent accessible cells are >= threshold(6) MATLAB clean
  		      				binmap[y][x]=-1;
  		      		}
  		      	}
			}
  	    }
		return binmap;
	}
	
	
	
	public int[][] complement(int [][] binmap, int height, int width)
	{
		int[][] maze=new int[height][width];
	  	  for(int y = 0; y < height; y++){
		    	for(int x = 0; x < width; x++){
		    	if(binmap[y][x]==0)
		    		maze[y][x]=-1;
		    	else
		    		maze[y][x]=0;
		    	}
		    }
	  	  return maze;
	}
	
}
