package graphing;

/**
 * Implements a processor that appends the name of a vertex to a result string.
 * It is used to generate the string associated with a BFS or DFS traversal. We
 * append the data of the vertex to the vertex's name.
 */

public class PrintingCallBack<E> implements CallingBack<E> {
	private String result = "";

	public void processVertex(String vertex, E vertexData) {
		result += "Vertex: " + vertex + ", Vertex Data: " + vertexData + "\n";
	}

	public String getResultFunc() {
		return result;
	}

	public void clearFunc() {
		result = "";
	}
}