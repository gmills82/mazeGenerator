import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Grant Mills
 * @since 3/2/18
 */
public class Cell {
	private Integer row;
	private Integer column;
	private Set<Cell> linkedCells;
	private Cell north;
	private Cell south;
	private Cell east;
	private Cell west;
	private EnumMap<Direction, Cell> neighbors;

	public Cell(Integer row, Integer column) {
		this.row = row;
		this.column = column;
		this.linkedCells = new HashSet<>();

		this.neighbors = new EnumMap<Direction, Cell>(Direction.class);
	}

	//Linked cells are ones that share a passage
	//Links two cells, bidirectionally is the flag is true
	private void link(Cell cellToBeLinked, Boolean isBiDirectional) {
		//Default value
		if(isBiDirectional == null) {
			isBiDirectional = true;
		}
		this.linkedCells.add(cellToBeLinked);
		if(isBiDirectional) {
			Set<Cell> currentlyLinked = cellToBeLinked.getLinkedCells();
			if(!currentlyLinked.contains(this)) {
				currentlyLinked.add(this);
				cellToBeLinked.setLinkedCells(currentlyLinked);
			}
		}
	}

	//Unlinks two cells, bidirectionally if the flag is true
	private void unlink(Cell cellToBeUnlinked, Boolean isBiDirectional) {
		//Default value
		if(isBiDirectional == null) {
			isBiDirectional = true;
		}
		if(this.linkedCells.contains(cellToBeUnlinked)) {
			this.linkedCells.remove(cellToBeUnlinked);
		}
		if(isBiDirectional) {
			Set<Cell> currentlyLinked = cellToBeUnlinked.getLinkedCells();
			if(currentlyLinked.contains(this)) {
				currentlyLinked.remove(this);
				cellToBeUnlinked.setLinkedCells(currentlyLinked);
			}
		}
	}

	public Boolean isLinked(Cell testCell) {
		return this.getLinkedCells().contains(testCell);
	}

	public Set<Cell> getLinkedCells() {
		return linkedCells;
	}

	public void setLinkedCells(Set<Cell> linkedCells) {
		this.linkedCells = linkedCells;
	}

	public Integer getRow() {
		return row;
	}

	public Integer getColumn() {
		return column;
	}

	public Cell getNorth() {
		return north;
	}

	public void setNorth(Cell north) {
		this.north = north;
		this.neighbors.put(Direction.NORTH, north);
	}

	public Cell getSouth() {
		return south;
	}

	public void setSouth(Cell south) {
		this.south = south;
		this.neighbors.put(Direction.SOUTH, south);
	}

	public Cell getEast() {
		return east;
	}

	public void setEast(Cell east) {
		this.east = east;
		this.neighbors.put(Direction.EAST, east);
	}

	public Cell getWest() {
		return west;
	}

	public void setWest(Cell west) {
		this.west = west;
		this.neighbors.put(Direction.WEST, west);
	}

	public EnumMap<Direction, Cell> getNeighbors() {
		return neighbors;
	}
}
