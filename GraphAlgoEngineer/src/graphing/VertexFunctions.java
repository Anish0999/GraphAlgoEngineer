package graphing;

import graphing.Vertex;

class Vertex implements Comparable<Vertex> {
	String name;
	int distance;

	Vertex(String name, int distance) {
		this.name = name;
		this.distance = distance;
	}

	@Override
	public int compareTo(Vertex other) {
		return Integer.compare(this.distance, other.distance);
	}
}
