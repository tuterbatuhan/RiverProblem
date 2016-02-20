import java.util.ArrayList;
import java.util.List;

//Path is a list consisting of the states inside the state-space.
public class Path
{
	public List<State> states;

	// Default Constructors
	public Path()
	{
		states = new ArrayList<>();
	}

	// Creates a path with initial starting state
	public Path(State state)
	{
		states = new ArrayList<>();
		states.add(state);
	}

	// Creates a new path, extending the given path with the next state.
	public Path(Path copy, State nextState)
	{
		states = new ArrayList<>(copy.states);
		states.add(nextState);
	}

	// Returns the last state of the path
	public State getLast()
	{
		return states.get(states.size() - 1);
	}

	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("\t    Left\t\t\t\t\t    Right\n");
		builder.append("Missionaries\tCannibals\tBoat Position\tMissionaries\tCannibals\n");
		for (State s : states)
		{
			builder.append(s);
			builder.append('\n');
		}
		return builder.toString();
	}
}
