package evolve;

import java.io.*;
import java.util.*;
import java.awt.Color;

import static robocode.util.Utils.*;
import robocode.*;

public class EvolvedRobot extends AdvancedRobot {
    // Some sample parameters:
    public double firePowerNear, firePowerFar, fire_f_melee, fire_f, closeD, farD;
    double mapX;
    double mapY;
    double robotHeight;
    double robotWidth;
    double robotY;
    double robotX;
    double heading;

    public void run() {
    
      System.out.println(System.getProperty("user.home"));
            List < Double > parameters = new ArrayList < Double > ();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/.robocode-genes"));
                for (String line;
                    (line = reader.readLine()) != null;) {
                    parameters.add(Double.parseDouble(line));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            firePowerNear = parameters.get(0);
            firePowerFar = parameters.get(1);
            fire_f_melee = parameters.get(2);
            fire_f = parameters.get(3);
            closeD = parameters.get(4);
            farD = parameters.get(5);

            setAdjustRadarForGunTurn(true);

            mapX = getBattleFieldWidth();
            mapY = getBattleFieldHeight();
            robotHeight = getHeight();
            robotWidth = getWidth();
            robotY = getY();
            robotX = getX();
            heading = getHeading();

            if (heading < 180) {
                turnLeft(heading);
                turnRadarLeft(heading);
            } else {
                turnRight(360 - heading);
                turnRadarRight(360 - heading);
            }
            turnGunRight(90);
            turnRadarRight(90);

            setColors(Color.blue, Color.red, Color.white);

           
           
            while (true) {
                setAhead(mapY - getY() - robotHeight); // go to top of map
                execute();
                while (getDistanceRemaining() > 0) {

                    setTurnRadarRight(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarLeft(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarLeft(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarRight(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();


                }


                setTurnRight(90); // turn right
                setTurnRadarRight(90);
                execute();
                while (getTurnRemaining() > 0) {
                    execute();
                }

                setAhead(mapX - getX() - robotHeight); // go to right wall
                while (getDistanceRemaining() > 0) {
                    setTurnRadarRight(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarLeft(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarLeft(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarRight(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();




                }

                setTurnRight(90); // turn right
                setTurnRadarRight(90);
                execute();
                while (getTurnRemaining() > 0) {
                    execute();
                }
                setAhead(getY() - robotHeight); // go to bottom of map

                while (getDistanceRemaining() > 0) {
                    setTurnRadarRight(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarLeft(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarLeft(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarRight(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();



                }

                setTurnRight(90); // turn right
                setTurnRadarRight(90);
                execute();
                while (getTurnRemaining() > 0) {
                    execute();
                }
                setAhead(getX() - robotHeight); // go to the left wall

                while (getDistanceRemaining() > 0) {
                    setTurnRadarRight(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarLeft(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarLeft(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();
                    setTurnRadarRight(Rules.RADAR_TURN_RATE - Rules.GUN_TURN_RATE);
                    execute();

                }


                setTurnRight(90); // turn right
                setTurnRadarRight(90);
                execute();
                while (getTurnRemaining() > 0) {
                    execute();
                }



                execute();
            }
            }
            public void onScannedRobot(ScannedRobotEvent e) {
                // Replace the next line with any behavior you would like

                double angle = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
                double radarAngle = normalRelativeAngleDegrees(getRadarHeading() - getHeading());
                //double Bearing = e.getBearing();




                setTurnGunRight(angle);

                if (getOthers() > 3) {

                    if (Math.abs(angle) < fire_f_melee) {
                        setFire(3);

                    }
                } else if (getOthers() > 1) {

                    if (Math.abs(angle) < fire_f) {
                        if (e.getDistance() < closeD) {
                            setFire(firePowerNear);
                        } else if (e.getDistance() < farD) {
                            setFire(firePowerFar);
                        }
                    }
                } else if (getOthers() == 1) {


                    setTurnRadarRight(radarAngle);
                    execute();

                    //setTurnGunLeft(Math.abs(getGunHeading()-getHeading()));



                    if (e.getBearing() > 0) {

                        setTurnRight(e.getBearing());
                        setAhead(100);
                        setTurnRadarRight(Rules.RADAR_TURN_RATE);
                        execute();
                        setTurnRadarLeft(Rules.RADAR_TURN_RATE);
                        execute();
                        setTurnRadarLeft(Rules.RADAR_TURN_RATE);
                        execute();
                        setTurnRadarRight(Rules.RADAR_TURN_RATE);
                        execute();


                        if (Math.abs(angle) < fire_f) {
                            if (e.getDistance() < closeD) {
                                setFire(firePowerNear);
                            } else if (e.getDistance() < farD) {
                                setFire(firePowerFar);
                            }
                        }

                    }
                    if (e.getBearing() < 0) {

                        setTurnLeft(Math.abs(e.getBearing()));
                        setAhead(100);
                        setTurnRadarRight(Rules.RADAR_TURN_RATE);
                        execute();
                        setTurnRadarLeft(Rules.RADAR_TURN_RATE);
                        execute();
                        setTurnRadarLeft(Rules.RADAR_TURN_RATE);
                        execute();
                        setTurnRadarRight(Rules.RADAR_TURN_RATE);
                        execute();


                        if (Math.abs(angle) < fire_f) {
                            if (e.getDistance() < closeD) {
                                setFire(firePowerNear);
                            } else if (e.getDistance() < farD) {
                                setFire(firePowerFar);
                            }
                        }

                    }


                }

            }




            /**
             * onHitByBullet: What to do when you're hit by a bullet
             */
            /*	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
	/*	double angle = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
	setTurnGunLeft(e.getBearing());
	 if (Math.abs(angle) < 3)  {
		setFire(3);
		}

	}
	*/

            /**
             * onHitWall: What to do when you hit a wall
             */
            public void onHitWall(HitWallEvent e) {
                // Replace the next line with any behavior you would like


            }


            public void onHitRobot(HitRobotEvent e) {

                if (getOthers() == 0) {
                    double angle = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
                    setTurnGunRight(angle);
                    setTurnRight(e.getBearing());
                    if (Math.abs(angle) < 3) {

                        setFire(3);
                    }


                }
            }
        } 
