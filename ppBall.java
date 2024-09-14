package ppPackage;
import java.awt.Color;
import acm.graphics.*;
import static ppPackage.ppSimParams.*;

/**
 * 
 * @author Howard Yu
 * this is the ball and the tracer
 */
public class ppBall extends Thread{
	private double Xinit; // Initial position of ball - X
	private double Yinit; // Initial position of ball - Y
	private double Vo; // Initial velocity (Magnitude)
	private double theta; // Initial direction
	private double loss; // Energy loss on collision
	private Color color; // Color of ball
	private ppTable myTable; // Instance of ping-pong table
	private ppPaddle myPaddle; // Instance of Paddle
	private ppPaddleAgent myAgent; //instance of Agent
	private boolean running; //determines if ball moves
	private boolean traceOn; //determines if tracer is turned on or not
	private GOval myBall; 
	
	
	public ppBall(double Xinit, double Yinit, double Vo, double theta, Color color, double
			loss, ppTable myTable, boolean traceOn) {
			
		this.Xinit = Xinit;
		this.Yinit = Yinit;
		this.color = color;
		this.Vo = Vo;
		this.theta = theta;
		this.loss = loss;
		this.myTable = myTable;
		this.traceOn = traceOn;
		this.myBall = new GOval(Xinit*SCALE, Yinit*SCALE, bSize*2*SCALE, bSize*2*SCALE);
		this.myBall.setColor(this.color);
		this.myBall.setFilled(true);
		
		}
	/**
	 * 
	 * allows class ppBall to use instance of the paddle
	 * 
	 */
	public void setPaddle (ppPaddle myPaddle) { //set instance of paddle in ppBall class
		this.myPaddle = myPaddle;
	}
	/**
	 * 
	 * allows class ppBall to use instance of the agent
	 */
	public void setAgent (ppPaddleAgent theAgent) { //set instance of agent in ppBall class
		this.myAgent = theAgent;
	}
	/**
	 * 
	 * @return returns whether ball is in play or not
	 */
	public boolean ballInPlay() { //determines whether ball is in play or not
		return running;
	}
	/**
	 * 
	 * @return returns instance of the ball
	 */
	
	public GOval getBall() { // returns instance of the ball
		return this.myBall;
	}
	

	/**
	 * 
	 * run method computes the ball's trajectory,
	 * code is based off of the teacher's code, and has been modified
	 * for assignment 4.
	 */
	
	public void run() {

		// Initialize simulation parameters
		 
		 double time = 0; // time (reset at each interval)
		 double Vt = bMass*g / (4*Pi*bSize*bSize*k); // Terminal velocity
		 double KEx=ETHR,KEy=ETHR; // Kinetic energy in X and Y directions
		 double PE=ETHR; // Potential energy
		 double Xo, X, Vx; // X position and velocity variables
		 double Yo, Y, Vy; // Y position and velocity variables

		 double Vox=Vo*Math.cos(theta*Pi/180); // Initial velocity components in X and Y
		 double Voy=Vo*Math.sin(theta*Pi/180);

		 Xo=Xinit+bSize; // Initial X offset (ball touching wall)
		 Yo=Yinit; // Initial Y offset


		 running = true; //running must be true initially for ball to move
		 while (running) {

			 X = Vox*Vt/g*(1-Math.exp(-g*time/Vt)); // Update relative position
			 Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time;
			 Vx = Vox*Math.exp(-g*time/Vt);
			 Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;
			 
			 if (Xo + X >= ppPaddleXinit + 0.02) { //terminates if ball passes through paddle, gives point to agent
				 running = false; 	 
				 myTable.getDisplay().agentField.setValue(myTable.getDisplay().agentField.getValue() + 1);
			 }
			 
			 if((Xo + X) <= Xinit - 1) { //terminates if ball passes through agent, gives point to human
				 running = false;
				 myTable.getDisplay().humanField.setValue(myTable.getDisplay().humanField.getValue() + 1);
			 }
			 
			 //collision detection to floor
			 else if (Vy<0 && (Yo+Y)<=bSize) {
				 KEx = 0.5*bMass*Vx*Vx*(1-loss); // Kinetic energy in X direction after collision
				 KEy = 0.5*bMass*Vy*Vy*(1-loss); // Kinetic energy in Y direction after collision
				 PE = 0; // Potential energy (at ground)
				 Vox = Math.min(5, Math.sqrt(2*KEx/bMass));
				 Voy = Math.min(5, Math.sqrt(2*KEy/bMass));
				 if (Vx<0) Vox=-Vox; // Preserve sign of Vox
		 
				 time=0; // Reset current interval time
				 Xo+=X; // Update X and Y offsets
				 Yo=bSize;
				 X=0; // Reset X and Y for next iteration
				 Y=0;
				 if ((KEx+KEy+PE)<ETHR) running=false; // Terminate if insufficient energy
				 
				 
				 //collision detection for paddle
			 }	 else if (myPaddle.contact(Xo + X, Yo + Y) == true && Vx>0) {
				 KEx = 0.5*bMass*Vx*Vx*(1-loss); // Kinetic energy in X direction after collision
				 KEy = 0.5*bMass*Vy*Vy*(1-loss); // Kinetic energy in Y direction after collision
				 PE = bMass*g*Y; // Potential energy	
				 
				//velocity calculation for ball based on total energy and velocity of paddle
				 Vox = Math.min(5, Math.sqrt(2*KEx/bMass) * ppPaddleXgain); 
				 Voy = Math.min(5, Math.sqrt(2*KEy/bMass) * ppPaddleYgain*myPaddle.getSgnVy()); 
				 Vox = -Vox;	
				 if (Vy<0) Voy=-Voy; // Preserve sign of Voy
	
				 time=0; // Reset current interval time
				 Xo=XMAX-bSize; // Update X and Y offsets
				 Yo+=Y;
				 X=0; // Reset X to zero for start of next interval
				 Y=0;
				 
				 //collision detection for agent
			 }	 else if (Vx<0 && myAgent.Agentcontact(Xo + X, Yo + Y) == true) {
			
				 KEx = 0.5*bMass*Vx*Vx*(1-loss); // Kinetic energy in X direction after collision
				 KEy = 0.5*bMass*Vy*Vy*(1-loss); // Kinetic energy in Y direction after collision
				 PE = bMass*g*Y; // Potential energy
				 
				//velocity calculation for ball based on total energy and velocity of agent
				 Vox = Math.min(5, Math.sqrt(2*KEx/bMass) * ppAgentXgain);
				 Voy = Math.min(5, Math.sqrt(2*KEy/bMass) * ppAgentYgain*myPaddle.getSgnVy());
				 if (Vy<0) Voy=-Voy; // Preserve sign of Voy
		
				 time=0; // Reset current interval time
				 Xo=Xinit+bSize; // Update X and Y offsets
				 Yo+=Y;
				 X=0; // Reset X to zero for start of next interval
				 Y=0;
			 
			
			 } else if (Y+Yo > YMAX && Vx > 0) { //detects if the ball goes beyond the ceiling and gives
				 								 //human the point if its the agent that hit it last.
				 running = false;
				 myTable.getDisplay().humanField.setValue(myTable.getDisplay().humanField.getValue() + 1);
				 
			 
			 } else if (Y+Yo > YMAX && Vx < 0) {//detects if the ball goes beyond the ceiling and gives
					 							//human the point if its the agent that hit it last.
				 running = false; 	 
				 myTable.getDisplay().agentField.setValue(myTable.getDisplay().agentField.getValue() + 1);
				 
			 }
			 
			 myBall.setLocation(myTable.toScrX(Xo + X - bSize),myTable.toScrY(Yo + Y + bSize)); // Change position of ball in display
			 if (traceOn == true) trace(Xo+X,Yo+Y); // Place a marker on the current position
	
			 
			 time+=TICK; // Update time
			 myTable.getDisplay().pause(TICK*1000*myTable.getDisplay().tickModif/1000); //delays ball movement so that it is playable
			 myAgent.AgentsetY(Yo + Y);
		 	}
		 
		 }
	/**
	 * 
	 * @param x: x coordinate of the ball
	 * @param y: y coordinate of the ball
	 * creates the tracer
	 */
		 // A simple method to plot trace points on the screen
		 private void trace(double x, double y) {
		 GOval pt = new GOval(x*SCALE,scrHEIGHT - y*SCALE,PD,PD);
		 pt.setColor(Color.BLACK);
		 pt.setFilled(true);
		 myTable.getDisplay().add(pt); 
		}

	}

