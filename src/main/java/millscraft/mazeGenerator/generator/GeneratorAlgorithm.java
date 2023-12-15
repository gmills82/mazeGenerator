package millscraft.mazeGenerator.generator;

import millscraft.mazeGenerator.Grid;

/**
 * The interface for algorithms that generate a maze.
 *
 * @author Grant Mills
 * @since 3/9/18
 */
public interface GeneratorAlgorithm {

    Grid prepareMaze(Grid maze);
}
