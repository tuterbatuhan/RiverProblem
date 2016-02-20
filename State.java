
public class State {
	public enum Boat {
		LEFT, RIGHT
	};

	public int missionaires = 0;
	public int cannibals = 0;
	public Boat boat = Boat.LEFT;

	public State(int missionaires, int cannibals, Boat boat) {
		super();
		this.missionaires = missionaires;
		this.cannibals = cannibals;
		this.boat = boat;
	}

	public boolean equal(State st) {
		return (st.missionaires == this.missionaires && st.cannibals == this.cannibals && st.boat == this.boat);
	}

	@Override
	public boolean equals(Object O) {
		return O instanceof State && equal((State) O);
	}
	public String toString()
	{
		return missionaires+"\t\t"+cannibals+"\t\t"+boat.name();
	}
}
