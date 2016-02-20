import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Solver
{
	// Missionaries initially in the left bank of the river
	public static final int MAX_MISSIONARY = 3;
	// Cannibals initially in the left bank of the river
	public static final int MAX_CANNIBAL = 3;
	// Size of the boat to cross the river
	public static final int BOAT_SIZE = 2;

	public static void main(String[] args)
	{
		// Define start and goal states
		State start = new State(MAX_MISSIONARY, MAX_CANNIBAL, State.Boat.LEFT);
		State goal = new State(0, 0, State.Boat.RIGHT);

		// Creating a tree to calculate the number and the frequencies of the
		// different solutions.
		TreeMap<String, Integer> map = new TreeMap<>();

		// Solve the program N times.
		final int N = 1000;
		for (int i = 0; i < N; i++)
		{
			Path p = solve(start, goal);// Creates the solution path
			String str = p.toString();// Converts the solution path into string
			if (map.containsKey(str))// If the path already found before, update
										// the tree
				map.put(str, map.get(str) + 1);
			else// Add the new solution to the solution tree.
				map.put(str, 1);
		}
		// Print solutions.
		int solutionCount = 1;
		for (Entry<String, Integer> s : map.entrySet())
		{
			System.out.println("---------------------------------------------------------");
			System.out.println("Solution:" + solutionCount++);
			System.out.println("Frequency: " + s.getValue() + "\n");
			System.out.println(s.getKey());
		}
	}

	public static Path solve(State start, State goal)
	{

		// An arraylist consisting of the paths
		List<Path> agenda = new ArrayList<>();
		agenda.add(new Path(start));

		// An arraylist consisting of the travelled states
		List<State> extendedList = new ArrayList<>();

		// Pseudo code provided by Prof. Bob. Berwick.
		while (!agenda.isEmpty())
		{
			Path currPath = agenda.remove(0);// Pop the first element from agenda
			if (currPath.getLast().equal(goal))// Solution is found
				return currPath;
			// Solution not found
			else
			{
				if (!extendedList.contains(currPath.getLast()))// New state is not visited before
				{
					// Add state to visited list
					extendedList.add(currPath.getLast());
					
					// All valid moves from last state
					List<State> neighbours = getNeighbours(currPath.getLast());
					// Add new paths to the agenda in random places 		
					for (State s : neighbours)
						agenda.add((int) (Math.random() * (agenda.size() + 1)), new Path(currPath, s));
				}
			}
		}
		return null;
	}

	// Finds the reachable states from the last state of a path.
	private static List<State> getNeighbours(State last)
	{
		List<State> result = new ArrayList<>();// Result list consisting of the next states
		// Checks all different possibilities of crossing the river with the
		// given size of a boat.
		for (int passengerCount = 1; passengerCount <= BOAT_SIZE; passengerCount++)
		{
			for (int missionaryCount = 0; missionaryCount <= passengerCount; missionaryCount++)
			{
				// Checks if the next state is applicable
				State s = transport(last, missionaryCount, passengerCount - missionaryCount);
				if (s != null)
					result.add(s);// Add next states to the result list.
			}
		}
		return result;
	}

	// Checks if the given state is desired after it is ended
	public static State transport(State s, int miss, int cann)
	{
		State.Boat position = s.boat;
		int cannibals = s.cannibals;
		int missionaries = s.missionaires;

		// Checks if the given state is valid
		if (miss + cann > BOAT_SIZE || miss + cann <= 0)
			return null;

		// Concludes the state according to the boat position.
		if (position == State.Boat.LEFT)
		{
			cannibals -= cann;
			missionaries -= miss;
			position = State.Boat.RIGHT;
		} else
		{
			cannibals += cann;
			missionaries += miss;
			position = State.Boat.LEFT;
		}

		// Checks the states last status if it is desired.
		// Assumption: MAX_MISSIONAR = MAX_CANNIBAL && BOAT_SIZE <=2
		if (s.missionaires == 0 || s.missionaires == MAX_MISSIONARY || s.missionaires == s.cannibals)
		{
			return new State(missionaries, cannibals, position);
		} else
			return null;
	}
}
