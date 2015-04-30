package evolve;

import java.io.*;
import java.util.*;
import java.awt.Color;

import static robocode.util.Utils.*;
import robocode.*;

public class EvolvedRobot extends AdvancedRobot {
	// Some sample parameters:
	public double firePower, radius, aim, speed;
	double mapX;
	double mapY;
	double robotHeight;
	double robotWidth;
	double robotY;
	double robotX;
	double heading;
	boolean scan;
	public void run() {

		setAdjustRadarForGunTurn(true);




		mapX = getBattleFieldWidth();
		mapY = getBattleFieldHeight();
		robotHeight = getHeight();
		robotWidth = getWidth();
		robotY = getY();
		robotX = getX();

		heading = getHeading();
		// Initialization of the robot should be put here
		setColors(Color.black, Color.yellow, Color.black); // body,gun,radar




		turnRadarRight(360);



		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:






		// Robot main loop


		while (true) {
			// Replace the next 4 lines with any behavior you would like

			

			if (scan == false) {
				setTurnRadarRight(360);
			}
			scan = false;
			execute();


		}

	}
	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		double angle = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
		double radarAngle = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getRadarHeading());
		//double Bearing = e.getBearing();

		scan = true;


		setTurnGunRight(angle * 2 * aim / 100.0);


		setTurnRadarRight(radarAngle * 1.1);


		setTurnRight(e.getBearing() + 90 * radius / 100.0);
		setAhead(speed * 20);
	
				setFire(3 * firePower / 100.0);

		
			
		


	}



	public void onWin(WinEvent e) {
		setColors(Color.black, Color.black, Color.black); // body,gun,radar

		setTurnGunRight(360);
		doNothing();

	}

}
