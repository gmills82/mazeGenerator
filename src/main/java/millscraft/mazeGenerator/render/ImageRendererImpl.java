package millscraft.mazeGenerator.render;

import millscraft.mazeGenerator.Cell;
import millscraft.mazeGenerator.Grid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * @author Grant Mills
 * @since 3/9/18
 */
public class ImageRendererImpl implements Renderer<File> {

	private static final Logger logger = LoggerFactory.getLogger(ImageRendererImpl.class);
	private static final Integer CELL_SIZE = 10;
	private static final Integer MAZE_BORDER = 10;
	private static final Integer WALL_THICKNESS = 1;

	private File doRender(Grid maze) {
		Integer imageWidth = (MAZE_BORDER * 2) + ((CELL_SIZE + WALL_THICKNESS) * maze.getColumnSize()) + WALL_THICKNESS;
		Integer imageHeigth = (MAZE_BORDER * 2) + ((CELL_SIZE + WALL_THICKNESS) * maze.getRowSize()) + WALL_THICKNESS;

		// Picked image type based on response here,
		// https://stackoverflow.com/questions/32414617/how-to-decide-which-bufferedimage-image-type-to-use
		BufferedImage mazeImageBuffer = new BufferedImage(imageWidth, imageHeigth, TYPE_INT_ARGB);
		Graphics2D graphics2D = mazeImageBuffer.createGraphics();
		graphics2D.setBackground(Color.WHITE);

		//White rectangle is actual background
		graphics2D.setColor(Color.WHITE);
		graphics2D.fillRect(0,0,imageWidth, imageHeigth);

		//Stroke for the walls of the maze cells
		graphics2D.setColor(Color.black);
		BasicStroke basicStroke = new BasicStroke(WALL_THICKNESS);
		graphics2D.setStroke(basicStroke);

		//Iterate over maze and draw borders to the cells
		//graphics2D.drawLine(0,0, 5, 0);
		for(int x = 0; x < maze.getGrid().size(); x++) {
			java.util.List<Cell> row = maze.getGrid().get(x);
			for(int y = 0; y < row.size(); y++) {
				Cell currentCell = row.get(y);
				Integer gridX = (currentCell.getColumn() * (CELL_SIZE + WALL_THICKNESS)) + MAZE_BORDER;
				Integer gridY = (currentCell.getRow() * (CELL_SIZE + WALL_THICKNESS)) + MAZE_BORDER;
				Integer gridX2 = gridX + CELL_SIZE + WALL_THICKNESS;
				Integer gridY2 = gridY + CELL_SIZE + WALL_THICKNESS;
				// Check linked cells
				// We only have to check two directions for links as they will handle drawing all the walls
				if(currentCell.getNorth() == null) {
					graphics2D.drawLine(gridX, gridY, gridX2, gridY);
				}
				if(currentCell.getWest() == null) {
					graphics2D.drawLine(gridX, gridY, gridX, gridY2);
				}
				if(!currentCell.isLinked(currentCell.getEast())) {
					graphics2D.drawLine(gridX2, gridY, gridX2, gridY2);
				}
				if(!currentCell.isLinked(currentCell.getSouth())) {
					graphics2D.drawLine(gridX, gridY2, gridX2, gridY2);
				}
			}
		}

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

	@Override
	public File render(Grid maze) {
		if(maze == null) {
			throw new IllegalArgumentException();
		}else {
			return doRender(maze);
		}
	}
}
