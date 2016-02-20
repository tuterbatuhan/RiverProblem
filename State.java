//States represent the nodes of the state-space tree
public class State
{
	public enum Boat
	{
		LEFT, RIGHT
	};

	public int missionaires = 0;// Number of missionaries on the left bank of
								// the river
	public int cannibals = 0;// Number of cannibals on the left bank of the
								// river
	public Boat boat = Boat.LEFT;// Bank side of the boat

	public State(int missionaires, int cannibals, Boat boat)
	{
		super();
		this.missionaires = missionaires;
		this.cannibals = cannibals;
		this.boat = boat;
	}

	public boolean equal(State st)
	{
		return (st.missionaires == this.missionaires && st.cannibals == this.cannibals && st.boat == this.boat);
	}

	@Override
	public boolean equals(Object O)
	{
		return O instanceof State && equal((State) O);
	}

	public String toString()
	{
		int remMiss = Solver.MAX_MISSIONARY-missionaires;
		int remCann = Solver.MAX_CANNIBAL-cannibals;
		return +missionaires + "\t\t" + cannibals + "\t\t" + boat.name()+"\t\t" + remMiss + "\t\t" + remCann;
	}
}
