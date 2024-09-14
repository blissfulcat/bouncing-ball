package ppPackage;

import java.awt.Color;
/**
 * 
 * @author Howard Yu
 * This is all parameter that is used the class
 * all parameters were taken from the teacher's assignment 4 document.
 *
 */
public class ppSimParams {


	static final int scrWIDTH = 1080; // n.b. screen coordinates
	static final int scrHEIGHT = 600;
	static final int OFFSET = 200;

	// 2. Parameters defined in simulation coordinates (MKS, x-->range, y-->height)

	static final double g = 9.8; // MKS
	static final double k = 0.1316; // Vt constant
	static final double Pi = 3.1416;
	static final double XMAX = 2.74; // Maximum value of X (pp table)
	static final double YMAX = 1.52; // Maximum value of Y (height above table)
	static final double XLWALL = 0.1; // position of left wall
	static final double XRWALL = XMAX; // position of right wall
	static final double bSize = 0.02; // pp ball radius
	static final double bMass = 0.0027; // pp ball mass
	static final double XINIT = XLWALL+bSize; // Initial ball location (X)
	static final double TICK = 0.01; // Clock tick duration (sec)
	static final double ETHR = 0.001; // Minimum ball energy needed to move
	static final double Yinit = YMAX/2; // Initial ball location (Y)
	static final double PD = 1; // Trace point diameter
	static final double SCALE = scrHEIGHT/YMAX; // Pixels/meter
	static final double ppPaddleH = 8*2.54/100; // Paddle height
	static final double ppPaddleW = 0.5*2.54/100; // Paddle width
	static final double ppPaddleXinit = XMAX-ppPaddleW/2; // Initial Paddle X Position
	static final double ppPaddleYinit = Yinit; // Initial Paddle Y Position
	static final double ppPaddleXgain = 2.0; // Vx gain on paddle hit
	static final double ppPaddleYgain = 2.0; // Vy gain on paddle hit
	static final double ppAgentXinit = XINIT; // Initial Paddle X Position
	static final double ppAgentYinit = Yinit; // Initial Paddle Y Position
	static final double ppAgentXgain = 2.0; // Vx gain on paddle hit
	static final double ppAgentYgain = 2.0; // Vy gain on paddle hit
	static final double TIMESCALE = 5000; // TICK to mSec scale factor (1000 -> real time)
	static final Color ColorPaddle = Color.GREEN; // User paddle color
	static final Color ColorAgent = Color.BLUE; // Agent paddle color
	static final Color ColorBall = Color.RED; // Ball color

	// 3. Parameters used by the ppSim (main) class

	static final int NUMBALLS = 1; // # pp balls to simulate
	static final double YinitMAX = 0.75*YMAX; // Max inital height at 75% of range
	static final double YinitMIN = 0.25*YMAX; // Min inital height at 25% of range
	static final double EMIN = 0.2; // Minimum loss coefficient
	static final double EMAX = 0.2; // Maximum loss coefficient
	static final double VoMIN = 5.0; // Minimum velocity
	static final double VoMAX = 5.0; // Maximum velocity
	static final double ThetaMIN = 0.0; // Minimum launch angle
	static final double ThetaMAX = 20.0; // Maximum launch angle

	// 4. Miscellaneous

	static final boolean DEBUG = false; // Enable debug messages and single step if true
	static final boolean TEST = false; // Print motion parameters
	static final long RSEED = 8976232; // Random number generator seed value

}
