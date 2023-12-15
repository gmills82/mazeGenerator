package millscraft.mazeGenerator.render;

import millscraft.mazeGenerator.Grid;

/**
 * @author Grant Mills
 * @since 3/9/18
 */
public interface Renderer<T> {

	T render(Grid maze);
}
