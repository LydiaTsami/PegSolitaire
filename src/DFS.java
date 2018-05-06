import java.util.ArrayList;
import java.util.Stack;

public class DFS {
	private Node rootNode,solutionNode;
	int moveCount;
	
	public DFS(Node rootNode) {
		this.rootNode = rootNode;
	}
	
	public Node startDFS() {
		long startTime = System.currentTimeMillis();
		// DFS uses Stack data structure
		Stack<Node> stack = new Stack<Node>();
		Node temp ;
		ArrayList<Node> expandedNodes = new ArrayList<Node>();
		
		stack.push(rootNode);//Pushes the root node in the stack to start the search
		while(!stack.empty()) {
			temp=stack.pop();
			if(temp.getNoOfPoles()==1) {// if GetNoOfPoles == 1 then this is the final node left 
				solutionNode=temp; 
				return solutionNode; // returns the final node which is the solution to the game
			}
			
			expandedNodes = temp.findChildren();
			for(Node node: expandedNodes) {
			stack.push(node);
			}
			expandedNodes.clear();
		}
		return null;
	}
}