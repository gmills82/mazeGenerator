package millscraft.mazeGenerator;

import millscraft.mazeGenerator.exception.CellLinkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Grant Mills
 * @since 3/2/18
 */
public class Cell {
	private static final Logger logger = LoggerFactory.getLogger(Cell.class);

	private Integer row;
	private Integer column;
	private Set<Cell> linkedCells;
	private Cell north;
	private Cell south;
	private Cell east;
	private Cell west;
	private EnumMap<Direction, Cell> neighbors;
	private Boolean visited = false;

	public Cell(Integer row, Integer column) {
		this.row = row;
		this.column = column;
		this.linkedCells = new HashSet<>();

		this.neighbors = new EnumMap<>(Direction.class);
	}

	/**
	 * Overridden method where default for bidirectional is set to true
	 *
	 * @param cell - a maze cell
	 */
	public void link(Cell cell) {
		this.link(cell, true);
	}

	/**
	 * Linked cells are ones that share a passage
	 * Links two cells, bidirectionally is the flag is true
	 *
	 * @param cellToBeLinked  - a maze cell
	 * @param isBiDirectional - boolean
	 */
	public void link(Cell cellToBeLinked, Boolean isBiDirectional) {

		if (null != cellToBeLinked) {
			//Only neighboring cells can be linked
			if (this.getNeighbors().containsValue(cellToBeLinked)) {
				this.linkedCells.add(cellToBeLinked);

				if (isBiDirectional) {
					Set<Cell> currentlyLinked = cellToBeLinked.getLinkedCells();
					if (!currentlyLinked.contains(this)) {
						currentlyLinked.add(this);
						cellToBeLinked.setLinkedCells(currentlyLinked);
					}
				}
			} else {
				throw new CellLinkException("Cells cannot be linked. Cell 1 is at row:" + this.getRow() + " col:" + this.getColumn() + ". Cell 2 is at row:" + cellToBeLinked.getRow() + " col:" + cellToBeLinked.getColumn() + ".");
			}
		} else {
			logger.info("Null cell was skipped and not linked");
		}
	}

	/**
	 * Overriden method where default for biderctional is set to true
	 *
	 * @param cell - a maze cell
	 */
	public void unlink(Cell cell) {
		this.link(cell, true);
	}

	/**
	 * Unlinks two cells, bidirectionally if the flag is true
	 *
	 * @param cellToBeUnlinked - a maze cell
	 * @param isBiDirectional  - boolean
	 */
	public void unlink(Cell cellToBeUnlinked, Boolean isBiDirectional) {

		this.linkedCells.remove(cellToBeUnlinked);
		if (isBiDirectional) {
			Set<Cell> currentlyLinked = cellToBeUnlinked.getLinkedCells();
			if (currentlyLinked.contains(this)) {
				currentlyLinked.remove(this);
				cellToBeUnlinked.setLinkedCells(currentlyLinked);
			}
		}
	}

	public Boolean isLinked(Cell testCell) {
		return this.getLinkedCells().contains(testCell);
	}

	public Cell getRandomNeighbor() {
		int randomDirection = ThreadLocalRandom.current().nextInt(0, this.neighbors.size());
		ArrayList<Cell> neighborCells = new ArrayList<>(this.neighbors.values());
		return neighborCells.get(randomDirection);
	}

	/**
	 * Returns an Optional<Cell> with a value of an unvisited Cell
	 * If all neighbor cells are visited the optional will be empty
	 *
	 * @return {@link Optional<Cell>}
	 */
	public Optional<Cell> getRandomUnvisitedNeighbor() {
		Optional<Cell> optionalCell;

		List<Cell> unvisitedNeighbors = new ArrayList<>();
		for (Cell cell : this.getNeighbors().values()) {
			if (cell.visited.equals(false)) {
				unvisitedNeighbors.add(cell);
			}
		}
		if (unvisitedNeighbors.isEmpty()) {
			optionalCell = Optional.empty();
		} else {
			int randomIndex = ThreadLocalRandom.current().nextInt(0, unvisitedNeighbors.size());
			optionalCell = Optional.of(unvisitedNeighbors.get(randomIndex));
		}

		return optionalCell;
	}

	public Optional<Cell> getRandomVisitedNeighbor() {
		Optional<Cell> optionalCell;

		List<Cell> unvisitedNeighbors = new ArrayList<>();
		for (Cell cell : this.getNeighbors().values()) {
			if (cell.visited.equals(true)) {
				unvisitedNeighbors.add(cell);
			}
		}
		if (unvisitedNeighbors.isEmpty()) {
			optionalCell = Optional.empty();
		} else {
			int randomIndex = ThreadLocalRandom.current().nextInt(0, unvisitedNeighbors.size());
			optionalCell = Optional.of(unvisitedNeighbors.get(randomIndex));
		}

		return optionalCell;
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

	public Boolean hasBeenVisited() {
		return visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}
}
