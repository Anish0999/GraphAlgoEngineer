package graphing;

/**
 * Represents the processing we apply to a vertex of a graph.
 */
public interface CallingBack<E> {
	public void processVertex(String vertex, E vertexData);
}
