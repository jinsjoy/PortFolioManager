package com.portfolio.fundWeightCalculator;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
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
		List<String> fundWeight = new ArrayList<String>();
		adjacencyLists.forEach((key, value) -> {
			 breadthFirstSearch(key, fundWeight, "FUND_WEIGHT");
		});
		return fundWeight;
	}

	@Override
	public List<String> fundWeightedReturn() {
		List<String> fundWeightedReturn = new ArrayList<String>();
		
		adjacencyLists.forEach((key, value) -> {
			breadthFirstSearch(key, fundWeightedReturn, "WEIGHTED_RETURN");
		});
			
		return fundWeightedReturn;
	}

	// Since input can be random, parsing the adjacencyList for find the emv weight
	// of the root element

	private int findEmvOfRoot() {
		int biggest = 0, sum = 0;
		Collection<Set<Node>> valueSets = adjacencyLists.values();
		Iterator<Set<Node>> it = valueSets.iterator();
		while (it.hasNext()) {
			Set<Node> nodeSet = it.next();
			Iterator<Node> itNode = nodeSet.iterator();
			while (itNode.hasNext()) {
				Node node = itNode.next();
				sum = sum + node.getData();
			}
			if (biggest < sum)
				biggest = sum;
			sum = 0;
		}
		return biggest;
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

	private List<String> breadthFirstSearch(String source, List<String> results , String operation) {

		Node sourceNode = new Node(0, source);

		ArrayDeque<Node> queue = new ArrayDeque<Node>();

		// A queue to store the visited nodes.
		ArrayDeque<Node> visited = new ArrayDeque<Node>();

		queue.offer(sourceNode);
		while (!queue.isEmpty()) {
			Node vertexNode = queue.poll();
			if (!sourceNode.equals(vertexNode) && !adjacencyLists.containsKey(vertexNode.getName())) {
				applyOperation(results, operation, sourceNode, vertexNode);
			}
			visited.offer(vertexNode);

			Set<Node> neighboursList = getNeighbours(vertexNode.getName());
			if (neighboursList != null) {
				Iterator<Node> it = neighboursList.iterator();
				while (it.hasNext()) {
					Node currentNode = it.next();
					if (!visited.contains(currentNode)) {
						queue.offer(currentNode);
					}
				}
			}
		}
		
		return results;
	}

	private void applyOperation(List<String> results ,String operation, Node sourceNode, Node vertex) {
		if(operation.equals("FUND_WEIGHT")) {
			double totalWeight = findTotalWeight(sourceNode.getName());
			results.add(
					sourceNode.getName() + "," + vertex.getName() + "," + new DecimalFormat("#.###").format(vertex.getData() / totalWeight));
		} else if(operation.equals("WEIGHTED_RETURN")) {
			double emvWeightOfRoot = findEmvOfRoot();
			double emv = vertex.getData();
			double emvWeight = emv/emvWeightOfRoot;
			results.add(
					sourceNode.getName() + "," + vertex.getName() + "," + new DecimalFormat("#.###").format(emvWeight * emv));
		}
		
	}
}
