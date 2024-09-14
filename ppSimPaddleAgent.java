package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import acm.gui.IntField;
import acm.gui.TableLayout;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener; 

/**
 * 
 * @author Howard Yu
 * this is the main class, it initialises every objects.
 */
public class ppSimPaddleAgent extends GraphicsProgram {
	ppTable myTable;
	ppPaddle myPaddle;
	ppPaddleAgent theAgent;
	ppBall myBall;
	IntField agentField;
	IntField humanField;
	JSlider t;
	JToggleButton Trace;
	double iYinit; // Current Yinit
	double iLoss; //random loss parameter
	double iVel; // random initial velocity
	double iTheta; // random angle
	double Xinit; // constant initial X
	int tickModif;
	
	/**
	 * initialises scoreboard, buttons, slider
	 * initialises paddle, agent, and ball
	 */
	public void init() {
		this.resize(scrWIDTH+OFFSET,scrHEIGHT+OFFSET);
		//scoreboard
		agentField = new IntField(0); //score of the agent
		humanField = new IntField(0); //score of the paddle
		
		//this is to prevent from user to edit the scores
		agentField.setEditable(false);
		humanField.setEditable(false);
		
		//adding labels and score for agent and paddle on top of the applet
		add(new JLabel("Agent"), NORTH);
		add(agentField, NORTH);
		add(new JLabel("Human"), NORTH);
		add(humanField, NORTH);
		
		
		//button that user can interact with (added at the bottom of the applet)
		Trace = new JToggleButton("Trace", true); // Toggle start with tracer on
		add(new JButton("Clear"), SOUTH);
		add(new JButton("New Serve"), SOUTH);
		add(Trace, SOUTH);
		add(new JButton("Quit"), SOUTH);
		
		
		//slider that user can interact with (added at the bottom of the applet)
		t = new JSlider(1000,3000,1000);
		
		add(new JLabel("-t"), SOUTH); 
		add(t, SOUTH);
		add(new JLabel("+t"), SOUTH);
		tickModif = t.getValue(); //updates the value of the tick modifier based 
								  //on the slider
		
		
		//detects change to the slider
		t.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent event) {
				tickModif = t.getValue(); //tick modifier's value becomes the value of the slider
			}
		});
		
		addActionListeners();// initializes interaction with buttons 
		addMouseListeners(); // initializes interaction with mouse
		
		
		myTable = new ppTable(this); // instance of walls
		myBall = newBall();
		myPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,ColorPaddle,myTable); // instance of paddle
		theAgent = new ppPaddleAgent(ppAgentXinit,ppAgentYinit,ColorAgent,myTable); //instance of agents
		myBall.setPaddle(myPaddle); //reference of paddle instance to ppBall class
		myBall.setAgent(theAgent); //reference of agent instance to ppBall class
		
		
		add(myBall.getBall()); //adds an instance of ball
		// Each thread must be explicitly started
		theAgent.start();
		myPaddle.start();
		myBall.start();
	}

	
	
	
	/**
	 * 
	 * generates ball with random parameters
	 * @return returns ball
	 */
	
	//method that return an instance of the ball using random generation
	ppBall newBall() {
		
		//random number generation with preset seed
		RandomGenerator rgen = RandomGenerator.getInstance();
		rgen.setSeed(RSEED);
		
		iYinit = rgen.nextDouble(YinitMIN,YinitMAX); // Current Yinit
		iLoss = rgen.nextDouble(EMIN,EMAX); //random loss parameter
		iVel = rgen.nextDouble(VoMIN,VoMAX); // random initial velocity
		iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX); // random angle
		Xinit = XINIT; // constant initial X
		
		return new ppBall(Xinit, iYinit, iVel, iTheta, ColorBall, iLoss,myTable, Trace.isSelected());
	}
	
	/**
	 * 
	 * convey's mouses Y coordinate to paddle's Y coordinate
	 */
	//mouse Y coordinate sets world Y coordinate of paddle
	public void mouseMoved(MouseEvent e) {
		this.myPaddle.setY(this.myTable.ScrtoY((double)e.getY()));
	}
	
	/**
	 * 
	 * parameters for what the buttons do
	 */
	//method for buttons when pressed
	public void actionPerformed(ActionEvent e) {
		
		/**
		 * if user presses clear, resets scoreboard value 
		 * and resets the screen
		 */
		//resets everything when button "Clear" has been pressed
		if (e.getActionCommand().equals("Clear")) {
			//resets scoreboard back to initial vlaue
			agentField.setValue(0);  
			humanField.setValue(0);
			//removes all objects except for floor
			myTable.newScreen();
		}
		
		/**
		 * if user presses New Serve, reinitialises paddle, agent, and ball
		 */
			//removes all pre-existing objects and starts a new serve
		else if (e.getActionCommand().equals("New Serve")) {
				//prevents user to initialize new serve if ball is already in play
			if(myBall.ballInPlay() == true) {
				println("i refuse");
			}
				
			else {
				myBall = newBall(); //creates new instance of ball
				myTable.newScreen(); //initializes the floor
				myPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,ColorPaddle,myTable); // instance of paddle
				theAgent = new ppPaddleAgent(ppAgentXinit,ppAgentYinit,ColorAgent,myTable); //instance of agent
				myBall.setPaddle(myPaddle); //reference of paddle instance to ppBall class
				myBall.setAgent(theAgent); //reference of agent instance to ppBall class
				add(myBall.getBall()); //adds instance of the ball
					
				// Each thread must be explicitly started
				theAgent.start();
				myPaddle.start();
				myBall.start();
			}
		}
		/**
		 * if user presses quit, system closes.
		 */
			//quits the program when button "Quit" has been pressed
		else if (e.getActionCommand().equals("Quit")) {
			System.exit(0);
		}
	}			
}