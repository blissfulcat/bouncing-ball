package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.*;
/**
 * 
 * @author Howard Yu
 * This is the paddle of the agent
 * It moves accordingly to the position
 * of the ball
 */

public class ppPaddleAgent extends ppPaddle{
	
	private ppTable myTable;
	private double X;
	private double Y;
	
	public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable) {
		super(X, Y, myColor, myTable);
		this.myTable = myTable;
		this.X = X;
		
	}
	
	/**
	 * 
	 * @param Y
	 * agent's paddle Y coordinates 
	 * is the same as the ball's Y coordinates
	 */
	public void AgentsetY(double Y) { //Sets the Y position of the paddle.
		this.Y = Y;
		
		//agent's maximum world height
		if (Y >= scrHEIGHT/SCALE - ppPaddleH/2) myPaddle.setLocation(myTable.toScrX(X + ppPaddleW/2),myTable.toScrY(scrHEIGHT/SCALE)); 
		//agent's minimum world height
		else if (Y <= ppPaddleH/2) myPaddle.setLocation(myTable.toScrX(X + ppPaddleW/2),myTable.toScrY(ppPaddleH));
		//agent's height based on cursor's position
		else {
			myPaddle.setLocation(myTable.toScrX(X + ppPaddleW/2),myTable.toScrY( Y + ppPaddleH/2));
		}
	}

	/**
	 * 
	 * @param Sx: X coordinate of the ball
	 * @param Sy: Y coordinate of the ball
	 * @return returns whether the ball has made contact with ball or not
	 */
	public boolean Agentcontact (double Sx, double Sy) { //Returns true if a surface at position (Sx,Sy) is deemed to be in contact 
													//with the agent.
		return ((Sx <= getX()-ppPaddleW/2) && (Sy <=  Y + ppPaddleH/2) && (Sy >= Y - ppPaddleH/2));
	}
}

