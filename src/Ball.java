

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	
	private double myX;   // x and y coordinates of center
    private double myY;
    private double mydx;       // pixels to move each time step() is called.
    private double mydy;
    private double myDiameter;
    private Color myColor; 
    private double myRadius;
	
	
	public Ball(double x,double y, double dx,double dy,double Radius,Color color) {
		myX=x;
		myY=y;
		mydx=dx;
		mydy=dy;
		myRadius=Radius;
		myDiameter=Radius*2;
		myColor=color;
	}
	public double getX() 
    { 
       return myX;
    }
    public double getY()      
    { 
       return myY;
    }
    public double getDiameter() 
    { 
  	  return myDiameter;
    }
    public Color getColor() 
    { 
  	  return myColor;
    }
    public double getRadius() 
    { 
       return myRadius;
    }
    public double getdx()             
    {
       return mydx;
    }
     public double getdy()
    {
       return mydy;
    }
     
     public void setX(double x)
     {
        myX = x;
     } 
     public void setY(double y)
     {
        myY = y;
     } 
     public void setColor(Color c)
     {
        myColor = c;
     }
     public void setDiameter(double d)
     {
        myDiameter = d;
        myRadius = d/2;
     }
     public void setRadius(double r)
     {
        myRadius = r;
        myDiameter = 2*r;
     }
     public void setdx(double dx)        
     {
        mydx = dx;
     }
      public void setdy(double dy)
     {
        mydy = dy;
     }
      
      public void draw(Graphics myBuffer) 
      {
         myBuffer.setColor(myColor);
         myBuffer.fillOval((int)(getX() - getRadius()), (int)(getY()-getRadius()), (int)getDiameter(), (int)getDiameter());
      }
      

}
