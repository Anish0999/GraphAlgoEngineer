/**
 * @author Anish Gupta
 * 
 */

package graphing;

import java.util.*;

public class GraphingFunctions<E> {
	private HashMap<String, HashMap<String, Integer>> adjacencyMap;
	private HashMap<String, E> dataMap;

	/**
	 * Constructor for the Graph. It initializes the adjacency map and data map.
	 */
	public GraphingFunctions() {
		adjacencyMap = new HashMap<>();
		dataMap = new HashMap<>();
	}

	/**
	 * Adds a vertex to the graph along with its associated data.
	 */
	public void addVertexFunc(String vertex, E data) {
		if (!adjacencyMap.containsKey(vertex)) {
			adjacencyMap.put(vertex, new HashMap<>());
			dataMap.put(vertex, data);
		}
	}

	/**
	 * Adds a directed edge between the start and end vertex with the specified
	 * cost.
	 */
	public void addDirectedEdgeFunc(String startVertex, String endVertex,
			int cost) {
		if (adjacencyMap.containsKey(startVertex)
				&& adjacencyMap.containsKey(endVertex)) {
			adjacencyMap.get(startVertex).put(endVertex, cost);
		}
	}

	/**
	 * Returns the cost of the edge between the start and end vertex.
	 */
	public int getCostFunc(String startVertex, String endVertex) {
		return adjacencyMap.get(startVertex).get(endVertex);
	}

	/**
	 * Returns a set of all the vertices in the graph.
	 */
	public Set<String> getVerticesFunc() {
		return adjacencyMap.keySet();
	}

	/**
	 * Performs a breadth-first search on the graph starting from the start
	 * vertex and applies the specified callback to each vertex.
	 */
	public void doBreadthFirstSearchFunc(String startVertex, CallBack<E> callback) {
		Set<String> visited = new HashSet<>();
		Queue<String> queue = new LinkedList<>();
		queue.add(startVertex);

		while (!queue.isEmpty()) {
			String vertex = queue.poll();
			if (!visited.contains(vertex)) {
				visited.add(vertex);
				callback.processVertex(vertex, dataMap.get(vertex));
				TreeMap<String, Integer> sortedMap = new TreeMap<>(
						adjacencyMap.get(vertex));
				for (String adjacentVertex : sortedMap.keySet()) {
					queue.add(adjacentVertex);
				}
			}
		}
	}

	/**
	 * Performs a depth-first search on the graph starting from the start vertex
	 * and applies the specified callback to each vertex.
	 */
	public void doDepthFirstSearchFunc(String startVertex, CallingBack<E> callback) {
		Set<String> visited = new HashSet<>();
		Stack<String> stack = new Stack<>();
		stack.push(startVertex);

		while (!stack.isEmpty()) {
			String vertex = stack.pop();
			if (!visited.contains(vertex)) {
				visited.add(vertex);
				callback.processVertex(vertex, dataMap.get(vertex));
				
				for (String adjacentVertex : adjacencyMap.get(vertex)
						.keySet()) {
					stack.push(adjacentVertex);
				}
			}
		}
	}

	/**
	 * Performs Dijkstra's algorithm on the graph to find the shortest path
	 * between the start and end vertex. It stores the path in the specified
	 * list and returns the total cost.
	 */
	public int doDijkstrasFunc(String startVertex, String endVertex,
			ArrayList<String> path) {
		Map<String, Vertex> vertexMap = new HashMap<>();
		Map<String, String> previous = new HashMap<>();
		PriorityQueue<Vertex> queue = new PriorityQueue<>();

		// Initialize all distances as infinite (Integer.MAX_VALUE) and distance
		// of start node as 0.
		for (String vertex : adjacencyMap.keySet()) {
			Vertex vertexObject;
			if (vertex.equals(startVertex)) {
				vertexObject = new Vertex(vertex, 0);
			} else {
				vertexObject = new Vertex(vertex, Integer.MAX_VALUE);
			}
			queue.add(vertexObject);
			vertexMap.put(vertex, vertexObject);
		}

		while (!queue.isEmpty()) {
			Vertex smallest = queue.poll();

			if (smallest.distance == Integer.MAX_VALUE) {
				// If no path found
				path.add("None");
				return -1;
			}

			for (String neighbor : adjacencyMap.get(smallest.name).keySet()) {
				int alt = smallest.distance + getCostFunc(smallest.name, neighbor);
				Vertex neighborVertex = vertexMap.get(neighbor);
				if (alt < neighborVertex.distance) {
					queue.remove(neighborVertex);
					neighborVertex.distance = alt;
					previous.put(neighbor, smallest.name);
					queue.add(neighborVertex);
				}
			}

			if (smallest.name.equals(endVertex)) {
				String vertex = endVertex;
				while (vertex != null) {
					path.add(0, vertex);
					vertex = previous.get(vertex);
				}
				return smallest.distance;
			}
		}

		path.add("None");
		return -1;
	}

	/**
	 * Returns a string representation of the graph showing all vertices and
	 * their edges.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// Sort keys in alphabetical order
		TreeMap<String, HashMap<String, Integer>> sortedMap = new TreeMap<>(
				adjacencyMap);

		// Print vertices
		sb.append("Vertices: ").append(sortedMap.keySet()).append("\nEdges:\n");

		// Print edges
		for (String vertex : sortedMap.keySet()) {
			sb.append("Vertex(").append(vertex).append(")--->")
					.append(sortedMap.get(vertex)).append("\n");
		}

		return sb.toString();
	}

}