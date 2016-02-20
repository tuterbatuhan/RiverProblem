import java.util.ArrayList;
import java.util.List;

public class Path {
	public List<State> states;

	public Path() {
		states = new ArrayList<>();
	}

	public Path(State state) {
		states = new ArrayList<>();
		states.add(state);
	}

	public Path(Path copy, State nextState) {
		states = new ArrayList<>(copy.states);
		states.add(nextState);
	}
	public State getLast()
	{
		return states.get(states.size()-1);
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Missionary\tCannibal\tBoat Position\n");
		for (State s : states)
		{
			builder.append(s);
			builder.append('\n');
		}
		return builder.toString();
	}
}
