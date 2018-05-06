import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFS {
	private Node rootNode,solutionNode;
	private PriorityQueue<Node> priorityQueue;
	
	public BestFS(Node rootNode) {
		this.rootNode = rootNode;
	}
	
	public Node startBestFS() {
		int min;
		// BestFS uses priorityQueue data structure
		priorityQueue = new PriorityQueue<Node>(new Comparator<Node>() {
			 @Override
			    public int compare(Node a, Node b) {
			        return (int) (a.getManhattanDistance() - b.getManhattanDistance()); 
			    }
		});
		
		Node temp ;
		ArrayList<Node> expandedNodes = new ArrayList<Node>();
		priorityQueue.add(rootNode);//Pushes the root node in the stack to start the search
		while(!priorityQueue.isEmpty()) {
			temp=priorityQueue.remove();
			if(temp.getNoOfPoles()==1) {// if GetNoOfPoles == 1 then this is the final node left 
				solutionNode=temp; 
				return solutionNode; // returns the final node which is the solution to the game
			}
			
			expandedNodes = temp.findChildren();
			for(Node node: expandedNodes) {
				priorityQueue.add(node);
			}
			expandedNodes.clear();
		}
		return null;
	}
}
