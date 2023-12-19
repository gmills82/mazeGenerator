package millscraft.mazeGenerator;

/**
 * @author Grant Mills
 * @since 3/4/18
 */
public enum Direction {
	NORTH(1), SOUTH(3), EAST(2), WEST(4);

	int directionNumber;

	Direction(int directionNumber) {
		this.directionNumber = directionNumber;
	}

	public int getDirectionNumber() {
		return directionNumber;
	}
}
