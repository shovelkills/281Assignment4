package nz.ac.auckland.se281.a4.ds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nz.ac.auckland.se281.a4.TwitterHandle;
//*******************************
//YOU SHOUD MODIFY THE SPECIFIED 
//METHODS  OF THIS CLASS
//HELPER METHODS CAN BE ADDED
//REQUIRED LIBRARIES ARE ALREADY 
//IMPORTED -- DON'T ADD MORE
//*******************************

public class Graph {

	/**
	 * Each node maps to a list of all the outgoing edges from that node
	 */
	protected Map<Node<String>, LinkedList<Edge<Node<String>>>> adjacencyMap;
	/**
	 * root of the graph, to know where to start the DFS or BFS
	 */
	protected Node<String> root;

	/**
	 * !!!!!! You cannot change this method !!!!!!!
	 */

	/**
	 * Creates a Graph
	 * 
	 * @param edges a list of edges to be added to the graph
	 */
	public Graph(List<String> edges) {
		adjacencyMap = new LinkedHashMap<>();
		int i = 0;
		if (edges.isEmpty()) {
			throw new IllegalArgumentException("edges are empty");
		}

		for (String edge : edges) {
			String[] split = edge.split(",");
			Node<String> source = new Node<String>(split[0]);
			Node<String> target = new Node<String>(split[1]);
			Edge<Node<String>> edgeObject = new Edge<Node<String>>(source, target);

			if (!adjacencyMap.containsKey(source)) {
				adjacencyMap.put(source, new LinkedList<Edge<Node<String>>>());
			}
			adjacencyMap.get(source).append(edgeObject);

			if (i == 0) {
				root = source;
			}
			i++;
		}
	}

	/**
	 * Gets the first value of a node from a string relation so we can compare the
	 * first value
	 * 
	 * @param edge the string of the relation
	 * @return a string value of the first node
	 */
	public String getFirstNode(String edge) {
		String firstNode = edge.substring(edge.indexOf(edge.charAt(0)), edge.indexOf(","));
		return firstNode;
	}

	/**
	 * Gets the second value of a node from a string relation so we can compare the
	 * second value
	 * 
	 * @param edge the string of the relation
	 * @return a string value of the second node
	 */
	public String getSecondNode(String edge) {
		String secondNode = edge.substring(edge.indexOf(",") + 1, edge.length());
		return secondNode;
	}

	/**
	 * This method returns a boolean based on whether the input sets are reflexive.
	 * 
	 * TODO: Complete this method (Note a set is not passed in as a parameter but a
	 * list)
	 * 
	 * @param set      A list of TwitterHandles
	 * @param relation A relation between the TwitterHandles
	 * @return true if the set and relation are reflexive
	 */
	public boolean isReflexive(List<String> set, List<String> relation) {
		boolean isReflective = true;
		boolean isReflectiveNode = false;
		for (String node : set) {
			isReflectiveNode = false;
			for (String edge : relation) {
				if (node.equals(getFirstNode(edge)) && node.equals(getSecondNode(edge))) {
					isReflectiveNode = true;
					break;
				}
			}
			if (isReflectiveNode == false) {
				isReflective = false;
				break;
			}
		}
		return isReflective;

	}

	/**
	 * This method returns a boolean based on whether the input set is symmetric.
	 * 
	 * If the method returns false, then the following must be printed to the
	 * console: "For the graph to be symmetric tuple: " + requiredElement + " MUST
	 * be present"
	 * 
	 * TODO: Complete this method (Note a set is not passed in as a parameter but a
	 * list)
	 * 
	 * @param relation A relation between the TwitterHandles
	 * @return true if the relation is symmetric
	 */
	public boolean isSymmetric(List<String> relation) {
		boolean isSymmetric = true;
		boolean isSymmetricEdge = false;
		for (String edge : relation) {
			isSymmetricEdge = false;
			for (String symmetricEdge : relation) {
				if ((getFirstNode(edge).equals(getSecondNode(symmetricEdge)))
						&& (getSecondNode(edge).equals(getFirstNode(symmetricEdge)))) {
					isSymmetricEdge = true;
					break;
				}
			}
			if (isSymmetricEdge == false) {
				isSymmetric = false;
				break;
			}
		}
		return isSymmetric;
	}

	/**
	 * This method returns a boolean based on whether the input set is transitive.
	 * 
	 * If the method returns false, then the following must be printed to the
	 * console: "For the graph to be transitive tuple: " + requiredElement + " MUST
	 * be present"
	 * 
	 * TODO: Complete this method (Note a set is not passed in as a parameter but a
	 * list)
	 * 
	 * @param relation A relation between the TwitterHandles
	 * @return true if the relation is transitive
	 */
	public boolean isTransitive(List<String> relation) {
		boolean isTransitive = true;
		boolean isTransitiveEdge = false;
		for (String firstEdge : relation) {
			for (String secondEdge : relation) {
				if (getSecondNode(firstEdge).equals(getFirstNode(secondEdge))) {
					for (String transitiveEdge : relation) {
						isTransitiveEdge = false;
						if (getFirstNode(firstEdge).equals(getFirstNode(transitiveEdge))
								&& getSecondNode(secondEdge).equals(getSecondNode(transitiveEdge))) {
							isTransitiveEdge = true;
							break;
						}
					}
					if (isTransitiveEdge == false) {
						return false;
					}
				}

			}
		}
		return isTransitive;
	}

	/**
	 * This method returns a boolean based on whether the input sets are
	 * anti-symmetric TODO: Complete this method (Note a set is not passed in as a
	 * parameter but a list)
	 * 
	 * @param set      A list of TwitterHandles
	 * @param relation A relation between the TwitterHandles
	 * @return true if the set and relation are anti-symmetric
	 */
	public boolean isEquivalence(List<String> set, List<String> relation) {
		return isReflexive(set, relation) && isSymmetric(relation) && isTransitive(relation);
	}

	/**
	 * This method returns a List of the equivalence class
	 * 
	 * If the method can not find the equivalence class, then The following must be
	 * printed to the console: "Can't compute equivalence class as this is not an
	 * equivalence relation"
	 * 
	 * TODO: Complete this method (Note a set is not passed in as a parameter but a
	 * list)
	 * 
	 * @param node     A "TwitterHandle" in the graph
	 * @param set      A list of TwitterHandles
	 * @param relation A relation between the TwitterHandles
	 * @return List that is the equivalence class
	 */
	public List<String> computeEquivalence(String node, List<String> set, List<String> relation) {
		if (!(set.contains(node)) || !isEquivalence(set, relation)) {
			System.out.println("Can't compute equivalence class as this is not an equivalence relation");
			return null;
		}
		HashSet<String> equivalenceRelationHash = new HashSet<String>();
		HashSet<String> equivalenceSetHash = new HashSet<String>();
		for (String edge : relation) {
			if (node.equals(getFirstNode(edge)) || node.equals(getSecondNode(edge))) {
				equivalenceSetHash.add(getFirstNode(edge));
				equivalenceSetHash.add(getSecondNode(edge));
			}
		}

		for (String nodeSet : equivalenceSetHash) {
			for (String edge : relation) {
				if (nodeSet.equals(getFirstNode(edge)) || nodeSet.equals(getSecondNode(edge))) {
					equivalenceRelationHash.add(edge);
				}
			}
		}
		List<String> equivalenceRelationList = new ArrayList<String>(equivalenceRelationHash);
		List<String> equivalenceSetList = new ArrayList<String>(equivalenceSetHash);
		if (isEquivalence(equivalenceSetList, equivalenceRelationList)) {
			return equivalenceSetList;
		} else {
			System.out.println("Can't compute equivalence class as this is not an equivalence relation");
			return null;
		}
	}

	/**
	 * This method returns a List nodes using the BFS (Breadth First Search)
	 * algorithm
	 * 
	 * @return List of nodes (as strings) using the BFS algorithm
	 */
	public List<Node<String>> breadthFirstSearch() {
		return breadthFirstSearch(root, false);
	}

	/**
	 * This method returns a List nodes using the BFS (Breadth First Search)
	 * algorithm
	 * 
	 * @param start A "TwitterHandle" in the graph
	 * @return List of nodes (as strings) using the BFS algorithm
	 */
	public List<Node<String>> breadthFirstSearch(Node<String> start, boolean rooted) {// name to breadthFirstSearch
		NodesStackAndQueue<Node<String>> queue = new NodesStackAndQueue<Node<String>>();
		List<Node<String>> visited = new ArrayList<Node<String>>();
		Node<String> value;
		List<Node<String>> allNodes = new ArrayList<Node<String>>();
		for (Node<String> key : adjacencyMap.keySet()) {// Gets all the nodes
			allNodes.add(key);
		}
		int getNext = 0;
		queue.append(adjacencyMap.get(start).get(0).getSource());
		if (!rooted) {
			while (!visited.containsAll(getAllNodes())) {// Goes through all the variables
				if (queue.isEmpty()) {
					queue.append(adjacencyMap.get(allNodes.get(getNext)).get(0).getSource());
					while (visited.contains(queue.peek())) {
						getNext++;
						if (getNext >= allNodes.size()) {// Breaks if the getNext exceeds the size of the array
							break;
						}
						queue.append(adjacencyMap.get(allNodes.get(getNext)).get(0).getSource());
					}
				}
				value = (Node<String>) queue.pop();
				if (adjacencyMap.get(value) != null) {
					for (int i = 0; i < adjacencyMap.get(value).size(); i++) {// Adds nodes to the queue
						if (!visited.contains(adjacencyMap.get(value).get(i).getTarget())) {
							queue.append(adjacencyMap.get(value).get(i).getTarget());
						}
					}
				}
				if (!visited.contains(value)) {// Checks if the nodes are in the already in the Visited value
					visited.add(value);
				}
			}
		} else {
			while (!queue.isEmpty()) {
				value = (Node<String>) queue.pop();
				for (int i = 0; i < adjacencyMap.get(value).size(); i++) {
					if (!visited.contains(adjacencyMap.get(value).get(i).getTarget())) {
						queue.append(adjacencyMap.get(value).get(i).getTarget());
					}
				}
				visited.add(value);
			}
		}
		return visited;
	}

	/**
	 * This method returns a List nodes using the DFS (Depth First Search) algorithm
	 * 
	 * @return List of nodes (as strings) using the DFS algorithm
	 */
	public List<Node<String>> depthFirstSearch() {
		return depthFirstSearch(root, false);
	}

	/**
	 * This method returns a List nodes using the DFS (Depth First Search) algorithm
	 * 
	 * @param start A "TwitterHandle" in the graph
	 * @return List of nodes (as strings) using the DFS algorithm
	 */
	public List<Node<String>> depthFirstSearch(Node<String> start, boolean rooted) {
		NodesStackAndQueue<Node<String>> stack = new NodesStackAndQueue<Node<String>>();
		List<Node<String>> visited = new ArrayList<Node<String>>();
		Node<String> value;
		List<Node<String>> allNodes = new ArrayList<Node<String>>();
		for (Node<String> key : adjacencyMap.keySet()) {// Gets all the nodes
			allNodes.add(key);
		}
		int getNext = 0;
		stack.append(adjacencyMap.get(start).get(0).getSource());
		if (!rooted) {
			while (!visited.containsAll(getAllNodes())) {// Goes through all the variables
				if (stack.isEmpty()) {
					stack.append(adjacencyMap.get(allNodes.get(getNext)).get(0).getSource());
					while (visited.contains(stack.peek())) {
						getNext++;
						if (getNext >= allNodes.size()) {// Breaks if the getNext exceeds the size of the array
							break;
						}
						stack.push(adjacencyMap.get(allNodes.get(getNext)).get(0).getSource());
					}
				}
				value = (Node<String>) stack.pop();
				if (adjacencyMap.get(value) != null) {
					for (int i = 0; i < adjacencyMap.get(value).size(); i++) {// Adds nodes to the stack
						if (!visited.contains(adjacencyMap.get(value).get(i).getTarget())) {
							stack.push(adjacencyMap.get(value).get(i).getTarget());
						}
					}
				}
				if (!visited.contains(value)) {// Checks if the nodes are in the already in the Visited value
					visited.add(value);
				}
			}
		} else {
			while (!stack.isEmpty()) {
				value = (Node<String>) stack.pop();
				if (adjacencyMap.get(value) != null) {
					for (int i = 0; i < adjacencyMap.get(value).size(); i++) {// Adds nodes to the stack
						if (!visited.contains(adjacencyMap.get(value).get(i).getTarget())) {
							stack.push(adjacencyMap.get(value).get(i).getTarget());
						}
					}
				}
				if (!visited.contains(value)) {// Checks if the nodes are in the already in the Visited value
					visited.add(value);
				}
			}
		}
		return visited;

	}

	/**
	 * @return returns the set of all nodes in the graph
	 */
	public Set<Node<String>> getAllNodes() {

		Set<Node<String>> out = new HashSet<>();
		out.addAll(adjacencyMap.keySet());
		for (Node<String> n : adjacencyMap.keySet()) {
			LinkedList<Edge<Node<String>>> list = adjacencyMap.get(n);
			for (int i = 0; i < list.size(); i++) {
				out.add(list.get(i).getSource());
				out.add(list.get(i).getTarget());
			}
		}
		return out;
	}

	/**
	 * @return returns the set of all edges in the graph
	 */
	protected Set<Edge<Node<String>>> getAllEdges() {
		Set<Edge<Node<String>>> out = new HashSet<>();
		for (Node<String> n : adjacencyMap.keySet()) {
			LinkedList<Edge<Node<String>>> list = adjacencyMap.get(n);
			for (int i = 0; i < list.size(); i++) {
				out.add(list.get(i));
			}
		}
		return out;
	}

	/**
	 * @return returns the set of twitter handles in the graph
	 */
	public Set<TwitterHandle> getUsersFromNodes() {
		Set<TwitterHandle> users = new LinkedHashSet<TwitterHandle>();
		for (Node<String> n : getAllNodes()) {
			users.add(new TwitterHandle(n.getValue()));
		}
		return users;
	}

}
