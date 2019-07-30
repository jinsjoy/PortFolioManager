package com.portfolio.fundWeightCalculator;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.portfolio.interfaces.PortfolioWeightCalculatorInterface;
import com.portfolio.nodes.Node;

public class PortfolioFundWeightCalculator implements PortfolioWeightCalculatorInterface {
	

	// add vertex name as key, and neighbours as values in set
	private final Map<String, Set<Node>> adjacencyLists = new HashMap<>();
	private static ArrayList<String> shortestPath = new ArrayList<String>();

	private void addVertex(final String name) {
		if (!this.adjacencyLists.containsKey(name)) {
			this.adjacencyLists.put(name, new HashSet<>());
		}
	}

	@Override
	public boolean addEdge(final String source, final String destination, final int data) {
		addVertex(source);
		Node node = new Node(data, destination);
		return this.adjacencyLists.get(source).add(node);
	}

	private Set<Node> getNeighbours(final String name) {
		HashSet<Node> neighBours = new HashSet<Node>();
		neighBours = (HashSet<Node>) this.adjacencyLists.get(name);
		return neighBours;
	}


	@Override
	public List<String> fundWeightCalculator() {
		List<String> fundWeights = new ArrayList<String>();

		adjacencyLists.forEach((key, value) -> {

			adjacencyLists.forEach((childKey, childValue) -> {
				childValue.forEach((e) -> {
					if ((!adjacencyLists.containsKey(e.getName())) && isChild(key, e.getName())) {
						double nodeWeight = e.getData();
						double totalWeight = findTotalWeight(key);
						if (totalWeight != 0) {
							fundWeights.add(key + "," + e.getName() + ","
									+ new DecimalFormat("#.###").format(nodeWeight / totalWeight));
						}
					}
				});
			});
		});
		return fundWeights;
	}

	private int findTotalWeight(String key) {
		Map<String, Integer> weightCache = new HashMap<String, Integer>();
		if (weightCache.containsKey(key)) {
			return weightCache.get(key);
		} else {
			int sum = 0;
			Set<Node> childElements = adjacencyLists.get(key);
			Iterator<Node> it = childElements.iterator();
			while (it.hasNext()) {
				Node currentNode = it.next();
				sum = sum + currentNode.getData();
			}
			weightCache.put(key, sum);
			return sum;
		}
	}

	private boolean isChild(String parentKey, String e) {
		ArrayList<String> edges = breadthFirstSearch(parentKey, e);
		if (edges == null) {
			return false;
		}
		return !edges.isEmpty();
	}

	private ArrayList<String> breadthFirstSearch(String source, String destination) {
		shortestPath.clear();

		// A list that stores the path.
		ArrayList<String> path = new ArrayList<String>();

	
		if (source.equals(destination)) {
			path.add(source);
			return path;
		}
	
		ArrayDeque<String> queue = new ArrayDeque<String>();

		// A queue to store the visited nodes.
		ArrayDeque<String> visited = new ArrayDeque<String>();

		queue.offer(source);
		while (!queue.isEmpty()) {
			String vertex = queue.poll();
			visited.offer(vertex);

			Set<Node> neighboursList = getNeighbours(vertex);
			if (neighboursList != null) {
				Iterator<Node> it = neighboursList.iterator();
				while (it.hasNext()) {
					Node currentNode = it.next();
					String nodeName = currentNode.getName();
					path.add(nodeName);
					path.add(vertex);

					if (nodeName.equals(destination)) {
						return processPath(source, destination, path);
					} else {
						if (!visited.contains(nodeName)) {
							queue.offer(nodeName);
						}
					}

				}
			}
		}
		return null;
	}

	private static ArrayList<String> processPath(String src, String destination, ArrayList<String> path) {

		int index = path.indexOf(destination);
		String source = path.get(index + 1);

		shortestPath.add(0, destination);

		if (source.equals(src)) {
			shortestPath.add(0, src);
			return shortestPath;
		} else {
			return processPath(src, source, path);
		}
	}

}
