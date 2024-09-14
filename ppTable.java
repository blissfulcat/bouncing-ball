package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.*;
/**
 * 
 * @author Howard Yu
 * this class creates the floor and reduces clutter in other classes
 *
 */
public class ppTable {


ppSimPaddleAgent dispRef;
GLine Floor;
	public ppTable(ppSimPaddleAgent dispRef) {
		
		Floor = new GLine(XLWALL*SCALE, scrHEIGHT, scrWIDTH, scrHEIGHT); // instance for right wall
		Floor.setColor(Color.BLACK);
		dispRef.add(Floor);  
		this.dispRef = dispRef;
	}
	/**
	 * removes all objects in the applet and only keeps the floor
	 */
	public void newScreen() { //removes all objects on screen and keeps the floor only
		dispRef.removeAll();
		dispRef.add(Floor);
	}
	/**
	 * 
	 * @param scrX: world X coordinate 
	 * @return returns applet X coordinate
	 */
	public double toScrX(double scrX) { // changes world X to screen X 
		return scrX*SCALE;	
	}
	/**
	 * 
	 * @param scrY: world Y coordinate
	 * @return returns applet Y coordinate
	 */
	public double toScrY(double scrY) { // changes world Y to screen Y
		return  scrHEIGHT - scrY*SCALE;
	}
	/**
	 * 
	 * @param scrX: applet X coordinate
	 * @return returns world X coordinate
	 */
	public double ScrtoX(double scrX) { // changes screen X to world Y
		return scrX/SCALE;	
	}
	/**
	 * 
	 * @param scrY: applet Y coordinate
	 * @return returns world Y coordinate
	 */
	public double ScrtoY(double scrY) { // changes screen Y to world Y
		return  -(scrY - scrHEIGHT)/SCALE;
	}
	ppSimPaddleAgent getDisplay() { // allows other classes to use GraphicsPrograms as well 
		return dispRef;
	}
	
}


