package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.*;

/**
 * 
 * @author howard Yu
 * This is the paddle of the player
 * It moves accordingly to the cursor
 * 
 */
public class ppPaddle extends Thread{
	private ppTable myTable;
	private double Vx;
	private double Vy;
	private double X;
	private double Y;
	private double lastX;
	private double lastY;
	GRect myPaddle;
	public ppPaddle(double X, double Y, Color myColor, ppTable myTable) {
		this.myTable = myTable;
		this.X = X;
		lastX = X; //first lastX is initial position
		lastY = Y; //first lastY is initial position
		myPaddle = new GRect(X*SCALE, Y*SCALE, ppPaddleW*SCALE, ppPaddleH*SCALE); // creates instance of a variable
		myPaddle.setColor(myColor);
		myPaddle.setFilled(true);
		myTable.getDisplay().add(myPaddle);
		
	}
	
	/**
	 * 
	 * calculates the Vx and the Vy of the paddle
	 */
	public void run() {
		while (true) { //calculates paddle's velocity
		
		Vx=(X-lastX)/TICK;
		Vy=(Y-lastY)/TICK;
		lastX=X;
		lastY=Y;
		myTable.getDisplay().pause(TICK*TIMESCALE); // Time to mS
		}
		
	}
	/**
	 * 
	 * @return returns Vx of the paddle
	 */
	public double getVx() { //Returns the velocity of the paddle in the X direction
		return Vx;
	}
	/**
	 * 
	 * @return returns Vy of the paddle
	 */
	public double getVy() { // Returns the velocity of the paddle in the Y direction
		return Vy;
	}
	/**
	 * 
	 * @param Y: Y coordinate of the cursor
	 * moves the paddle based on the location of the cursor
	 */
	public void setY(double Y) { //Sets the Y position of the paddle.
		this.Y = Y;
		//paddle's maximum world height
		if (Y >= scrHEIGHT/SCALE - ppPaddleH/2) myPaddle.setLocation(myTable.toScrX(X + ppPaddleW/2),myTable.toScrY(scrHEIGHT/SCALE)); 
		//paddle's minimum world height
		else if (Y <= ppPaddleH/2) myPaddle.setLocation(myTable.toScrX(X + ppPaddleW/2),myTable.toScrY(ppPaddleH));
		//paddle's height based on cursor's position
		else myPaddle.setLocation(myTable.toScrX(X + ppPaddleW/2),myTable.toScrY(Y + ppPaddleH/2));
	}
	/**
	 * 
	 * @return x position of the paddle
	 */
	public double getX() { //Returns the X position of the paddle.
		return X;
	}
	/**
	 * 
	 * @return returns Y coordinate of the paddle
	 */
	public double getY() { //Returns the Y position of the paddle.
		return Y;
	}
	/**
	 * 
	 * @return returns the sign 
	 */
	public double getSgnVy() { //Returns the sign of the Y velocity of the paddle.
		if (Vy < 0) return -1;
		else return 1;
		
	}
	/**
	 * 
	 * @param Sx: X coordinate of the ball
	 * @param Sy: Y coordinate of the ball
	 * @return returns whether the ball made contact with the paddle
	 */
	public boolean contact (double Sx, double Sy) { //Returns true if a surface at position (Sx,Sy) is deemed to be in contact 
													//with the paddle.

		
		return ((Sx >= getX()-bSize-ppPaddleW/2) && (Sy <= Y + ppPaddleH/2) && (Sy >= Y - ppPaddleH/2));
	}
	
	

}
