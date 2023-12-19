package millscraft.mazeGenerator.render;

import millscraft.mazeGenerator.Cell;
import millscraft.mazeGenerator.Direction;
import millscraft.mazeGenerator.Grid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * @author Grant Mills
 * @since 3/9/18
 */
public class ImageRendererImpl implements Renderer<File> {

	private static final Logger logger = LoggerFactory.getLogger(ImageRendererImpl.class);
	private Integer cellSize = 15;
	private Integer mazeBorder = cellSize * 2;
	private Integer wallThickness = 5;
	private Color backgroundColor = Color.WHITE;
	private Color wallColor = Color.BLACK;

	public ImageRendererImpl(
			Integer cellSize,
			Integer mazeBorder,
			Integer wallThickness,
			Color backgroundColor,
			Color wallColor
	) {
		if (cellSize != null) {
			this.cellSize = cellSize;
		}
		if (mazeBorder != null) {
			this.mazeBorder = mazeBorder;
		}
		if (wallThickness != null) {
			this.wallThickness = wallThickness;
		}
		if (backgroundColor != null) {
			this.backgroundColor = backgroundColor;
		}
		if (wallColor != null) {
			this.wallColor = wallColor;
		}
	}

	private File doRender(Grid maze, Direction startSide, Direction endSide) {
		if (startSide.equals(endSide)) {
			throw new IllegalArgumentException("Starting side and ending side cannot be the same.");
		}

		int imageWidth = (mazeBorder * 2) + ((cellSize + wallThickness) * maze.getColumnSize()) + wallThickness;
		int imageHeigth = (mazeBorder * 2) + ((cellSize + wallThickness) * maze.getRowSize()) + wallThickness;

		// Picked image type based on response here,
		// https://stackoverflow.com/questions/32414617/how-to-decide-which-bufferedimage-image-type-to-use
		BufferedImage mazeImageBuffer = new BufferedImage(imageWidth, imageHeigth, TYPE_INT_ARGB);
		Graphics2D graphics2D = mazeImageBuffer.createGraphics();
		graphics2D.setBackground(backgroundColor);

		//White rectangle is actual background
		graphics2D.setColor(backgroundColor);
		graphics2D.fillRect(0, 0, imageWidth, imageHeigth);

		//Stroke for the walls of the maze cells
		graphics2D.setColor(wallColor);
		BasicStroke basicStroke = new BasicStroke(wallThickness);
		graphics2D.setStroke(basicStroke);

		//Iterate over maze and draw borders to the cells
		//graphics2D.drawLine(0,0, 5, 0);
		for (int x = 0; x < maze.getGrid().size(); x++) {
			java.util.List<Cell> row = maze.getGrid().get(x);
			for (Cell currentCell : row) {
				// Check linked cells
				// We only have to check two directions for links as they will handle drawing all the walls
				if (currentCell.getNorth() == null) {
					//Draw top wall
					drawWall(graphics2D, currentCell, Direction.NORTH);
				}
				if (currentCell.getWest() == null) {
					//Draw left side vertical wall
					drawWall(graphics2D, currentCell, Direction.WEST);
				}
				if (!currentCell.isLinked(currentCell.getEast())) {
					//Draw right side vertical wall
					drawWall(graphics2D, currentCell, Direction.EAST);
				}
				if (!currentCell.isLinked(currentCell.getSouth())) {
					//Draw bottom wall
					drawWall(graphics2D, currentCell, Direction.SOUTH);
				}
			}
		}

		//Draw start and ending line
		drawEntranceAndExit(maze, graphics2D, startSide, endSide);

		//Output file
		File outputFile = new File("testMaze.png");

		try {
			ImageIO.write(mazeImageBuffer, "png", outputFile);
			logger.info("Maze image written to file. Location " + outputFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return outputFile;
	}

	private void drawEntranceAndExit(Grid maze, Graphics2D graphics2D, Direction startSide, Direction endSide) {
		//Stroke for the openings and exits of the maze cells
		graphics2D.setColor(backgroundColor);
		BasicStroke basicBackgroundStroke = new BasicStroke(wallThickness);
		graphics2D.setStroke(basicBackgroundStroke);

		if (startSide.equals(Direction.NORTH) || endSide.equals(Direction.NORTH)) {
			//Northern line
			//Draw random start line over wall border with background color
			List<Cell> topRow = maze.getGrid().get(0);
			int randomCellValue = ThreadLocalRandom.current().nextInt(0, topRow.size() - 1);
			Cell cell = topRow.get(randomCellValue);

			//Draw opening line
			drawInteriorWall(graphics2D, cell, Direction.NORTH);
		}
		if (startSide.equals(Direction.EAST) || endSide.equals(Direction.EAST)) {
			List<Cell> leftMostCells = new ArrayList<>();
			for (List<Cell> cellList : maze.getGrid()) {
				leftMostCells.add(cellList.get(0));
			}
			int randomCellValue = ThreadLocalRandom.current().nextInt(0, leftMostCells.size() - 1);
			Cell cell = leftMostCells.get(randomCellValue);

			drawInteriorWall(graphics2D, cell, Direction.EAST);
		}
		if (startSide.equals(Direction.SOUTH) || endSide.equals(Direction.SOUTH)) {
			//Ending Line
			//Draw random end line over wall border with background color
			List<Cell> bottomRow = maze.getGrid().get(maze.getRowSize() - 1);
			int randomCellValue = ThreadLocalRandom.current().nextInt(0, bottomRow.size() - 1);
			Cell cell = bottomRow.get(randomCellValue);

			//Draw ending line
			drawInteriorWall(graphics2D, cell, Direction.SOUTH);
		}
		if (startSide.equals(Direction.WEST) || endSide.equals(Direction.WEST)) {
			List<Cell> rightMostCells = new ArrayList<>();
			for (List<Cell> cellList : maze.getGrid()) {
				rightMostCells.add(cellList.get(cellList.size() - 1));
			}
			int randomCellValue = ThreadLocalRandom.current().nextInt(0, rightMostCells.size() - 1);
			Cell cell = rightMostCells.get(randomCellValue);

			drawInteriorWall(graphics2D, cell, Direction.WEST);
		}
	}

	private void drawWall(Graphics2D graphics2D, Cell cell, Direction direction) {
		int gridX = (cell.getColumn() * (cellSize + wallThickness)) + mazeBorder;
		int gridY = (cell.getRow() * (cellSize + wallThickness)) + mazeBorder;
		int gridX2 = gridX + cellSize + wallThickness;
		int gridY2 = gridY + cellSize + wallThickness;

		switch (direction) {
			case NORTH:
				//Draw top wall
				graphics2D.drawLine(gridX, gridY, gridX2, gridY);
				break;
			case EAST:
				//Draw right side vertical wall
				graphics2D.drawLine(gridX2, gridY, gridX2, gridY2);
				break;
			case SOUTH:
				//Draw bottom wall
				graphics2D.drawLine(gridX, gridY2, gridX2, gridY2);
				break;
			case WEST:
				//Draw left side vertical wall
				graphics2D.drawLine(gridX, gridY, gridX, gridY2);
				break;
		}
	}

	private void drawInteriorWall(Graphics2D graphics2D, Cell cell, Direction direction) {
		int gridX;
		int gridY;
		int gridX2;
		int gridY2;

		switch (direction) {
			case NORTH:
				//Draw top wall
				gridX = (cell.getColumn() * (cellSize + wallThickness)) + mazeBorder + wallThickness;
				gridY = (cell.getRow() * (cellSize + wallThickness)) + mazeBorder;
				gridX2 = gridX + cellSize - wallThickness;
				graphics2D.drawLine(gridX, gridY, gridX2, gridY);
				break;
			case SOUTH:
				//Draw bottom wall
				gridX = (cell.getColumn() * (cellSize + wallThickness)) + mazeBorder + wallThickness;
				gridY = (cell.getRow() * (cellSize + wallThickness)) + mazeBorder + wallThickness;
				gridX2 = gridX + cellSize - wallThickness;
				gridY2 = gridY + cellSize;
				graphics2D.drawLine(gridX, gridY2, gridX2, gridY2);
				break;
			case EAST:
				//Draw left wall
				gridX = (cell.getColumn() * (cellSize + wallThickness)) + mazeBorder;
				gridY = (cell.getRow() * (cellSize + wallThickness)) + mazeBorder + wallThickness;
				gridY2 = gridY + cellSize - wallThickness;
				graphics2D.drawLine(gridX, gridY, gridX, gridY2);
				break;
			case WEST:
				//Draw right wall
				gridX = (cell.getColumn() * (cellSize + wallThickness)) + mazeBorder + cellSize + wallThickness;
				gridY = (cell.getRow() * (cellSize + wallThickness)) + mazeBorder + wallThickness;
				gridY2 = gridY + cellSize - wallThickness;
				graphics2D.drawLine(gridX, gridY, gridX, gridY2);
				break;
		}
	}

	@Override
	public File render(Grid maze, Direction startSide, Direction endSide) {
		if (maze == null) {
			throw new IllegalArgumentException();
		} else {
			return doRender(maze, startSide, endSide);
		}
	}

}
