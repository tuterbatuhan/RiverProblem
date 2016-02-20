import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Solver {
	public static final int MAX_MISSIONARY = 3;
	public static final int MAX_CANNIBAL = 3;
	public static final int BOAT_SIZE = 2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		State start = new State(MAX_MISSIONARY, MAX_CANNIBAL, State.Boat.LEFT);
		State goal = new State(0, 0, State.Boat.RIGHT);

		TreeMap<String, Integer> map = new TreeMap<>();
		for (int i = 0; i < 1000; i++) {
			Path p = solve(start, goal);
			String str = p.toString();
			if (map.containsKey(str))
				map.put(str, map.get(str) + 1);
			else
				map.put(str, 1);
		}
		int solutionCount = 1;
		for (Entry<String, Integer> s : map.entrySet()) {
			System.out.println("---------------------------------------------------------");
			System.out.println("Solution:" + solutionCount++);
			System.out.println("Frequency: " + s.getValue() + "\n");
			System.out.println(s.getKey());
		}
	}

	public static Path solve(State start, State goal) {
		List<Path> agenda = new ArrayList<>();
		List<State> extendedList = new ArrayList<>();
		agenda.add(new Path(start));
		while (!agenda.isEmpty()) {
			Path currPath = agenda.remove(0);// 1
			if (currPath.getLast().equal(goal))// 2
				return currPath;
			else {
				if (!extendedList.contains(currPath.getLast()))// 3
				{
					extendedList.add(currPath.getLast());// 3a
					List<State> neighbours = getNeighbours(currPath.getLast());
					for (State s : neighbours)
						agenda.add((int) (Math.random() * (agenda.size() + 1)), new Path(currPath, s));
				}
			}
		}
		return null;
	}

	private static List<State> getNeighbours(State last) {
		List<State> result = new ArrayList<>();

		for (int passengerCount = 1; passengerCount <= BOAT_SIZE; passengerCount++) {
			for (int missionaryCount = 0; missionaryCount <= passengerCount; missionaryCount++) {
				State s = canTransport(last, missionaryCount, passengerCount - missionaryCount);
				if (s != null)
					result.add(s);
			}
		}
		return result;
	}

	public static State canTransport(State s, int miss, int cann) {
		State.Boat position = s.boat;
		int cannibals = s.cannibals;
		int missionaries = s.missionaires;

		if (miss + cann > BOAT_SIZE || miss + cann <= 0)
			return null;

		if (position == State.Boat.LEFT) {
			cannibals -= cann;
			missionaries -= miss;
			position = State.Boat.RIGHT;
		} else {
			cannibals += cann;
			missionaries += miss;
			position = State.Boat.LEFT;
		}
		// Assumption: MAX_MISSIONAR = MAX_CANNIBAL
		// BOAT_SIZE <=2
		if (s.missionaires == 0 || s.missionaires == MAX_MISSIONARY || s.missionaires == s.cannibals) {
			return new State(missionaries, cannibals, position);
		} else
			return null;
	}
}
