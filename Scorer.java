package evolve;

import java.io.*;
import java.util.*;

import robocode.control.*;
import robocode.control.events.*;

public class Scorer {
	protected static final String ROBOCODE_DIRECTORY = System.getProperty("user.home") + "/robocode";
	protected static final String ROBOT = "evolve.EvolvedRobot";
	protected static final String[]OPPONENTS = {
		"sample.Corners",
		"sample.Crazy",
		"sample.Fire",
		"sample.MyFirstRobot",
		"sample.RamFire",
		"sample.SpinBot",
		"sample.Tracker",
		"sample.TrackFire",
		"sample.VelociRobot",
		"sample.Walls",
	};
	protected static final int ROUND_COUNT = 10;

	protected static RobocodeEngine engine;
	protected static int engineResult;

	protected static int score(List<Double>parameters, int trials) {
		try {
			PrintWriter writer = new PrintWriter(System.getProperty("user.home") + "/.robocode-genes", "UTF-8");
			for (double parameter : parameters) {
				writer.println(parameter);
			}
			writer.close();
		} catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		int result = 0;
		for (String opponent : OPPONENTS) {
			engine.setVisible(true);
			engine.runBattle(new BattleSpecification(trials, new BattlefieldSpecification(800, 600), engine.getLocalRepository(ROBOT + "*," + opponent)), true);
			result += engineResult;
		}
		return result;
	}

	public static void main(String[]arguments) {
		engine = new RobocodeEngine(new java.io.File(ROBOCODE_DIRECTORY));
		engine.addBattleListener(new BattleAdaptor() {
				public void onBattleCompleted(BattleCompletedEvent e) {
					String check = e.getIndexedResults()[0].getTeamLeaderName();
					engineResult = e.getIndexedResults()[(check.equals(ROBOT + "*")) ? 0 : 1].getFirsts();
				}
			});
		List<Double>parameters = new ArrayList<Double>();
		for (String argument : arguments) {
			System.out.println(argument);
			parameters.add(Double.parseDouble(argument));
		}
		System.out.println(score(parameters, ROUND_COUNT));
		engine.close();
		System.exit(0);
	}
}
