import java.util.ArrayList;

public class Node {
	private ArrayList<Node> children;
	private Node parent;
	private String moveDescription; 
	private Table table;
	private int NoOfPoles;
	private double manhattanDistance;
	
	
	public Node(Table table) {
		this.table = table;
		parent=null;
		children= new ArrayList<Node>();
		this.NoOfPoles = table.getNoOfPoles();
	}
	
	public Node(Node parent, String moveDescription, Table table){
		this.parent = parent;
		this.children = new ArrayList<Node>();
		this.moveDescription = moveDescription;
		this.table = table;
		this.NoOfPoles = table.getNoOfPoles();
	}
	
	public Node getParent(){
		return parent;
	}
	
	public double getManhattanDistance() {
		return manhattanDistance;
	}
	
	public Table getTable() {
		return table;
	}
	
	public int getNoOfPoles() {
		return NoOfPoles;
	}
	
	public String getmoveDescription() {
		return moveDescription;
	}
	
	/*There is a variety of methods to estimate how "promising" a given board is. 
	  All of them have to do with trying to concentrate the pegs on the center.
	  (When I played it myself it became evident that leaving pegs isolated in one of the edges was a bad strategy,
	  and one should always try and concentrate them on the center). */
	public double calculateManhattanDistance(int row, int col) {
		double centre_row = table.getNoOfRows()/2;
		double centre_col = table.getNoOfColumns()/2;
		double distance = Math.abs(row-centre_row) + Math.abs(col-centre_col);
		return distance;
	}

	//Checks if there is a valid right move , cell right next to it must be 1 and the next one 2
	public boolean isValidMoveRight(int row,int col) {
		if(table.getListElement(row, col+1)==1 && table.getListElement(row, col+2)==2)
			return true;
		return false;
	}
	
	//Checks if there is a valid left move , cell on the left must be 1 and the next one 2
	public boolean isValidMoveLeft(int row,int col) {
		if(table.getListElement(row, col-1)==1 && table.getListElement(row, col-2)==2)
			return true;
		return false;
	}

	public boolean isValidMoveUp(int row,int col) {
		if(table.getListElement(row-1, col)==1 && table.getListElement(row-2, col)==2)
			return true;
		return false;
	}
	
	public boolean isValidMoveDown(int row,int col) {
		if(table.getListElement(row+1, col)==1 && table.getListElement(row+2, col)==2)
			return true;
		return false;
	}
	
	//Changes the number describing the poles , decreases the number of poles in the game by one , creates a new node of the game as a child of the original node
	public Node moveRight(int row,int col) {
		Table temp = new Table (table.getData(),table.getNoOfPoles());
		temp.setListElement(row, col+2, 1);
		temp.setListElement(row, col, 2);
		temp.setListElement(row, col+1, 2);
		temp.decreaseNoOfPoles();
		String Descr = (row+1) + " " + (col+1) + " " + (row+1) + " " + (col+1+2);
		Node node = new Node(this,Descr,temp);
		node.manhattanDistance = calculateManhattanDistance(row,col+2);
		return node;
	}
	
	//As described on MoveRight
	public Node moveLeft(int row,int col) {
		Table temp = new Table (table.getData(),table.getNoOfPoles());
		temp.setListElement(row, col-2, 1);
		temp.setListElement(row, col, 2);
		temp.setListElement(row, col-1, 2);
		temp.decreaseNoOfPoles();
		String Descr = (row+1) + " " + (col+1) + " " + (row+1) + " " + (col+1-2);
		Node node = new Node(this,Descr,temp);
		manhattanDistance = calculateManhattanDistance(row,col-2);
		return node;
	}
	
	//As described on MoveRight
	public Node moveUp(int row,int col) {
		Table temp = new Table (table.getData(),table.getNoOfPoles());
		temp.setListElement(row-2, col, 1);
		temp.setListElement(row, col, 2);
		temp.setListElement(row-1, col, 2);
		temp.decreaseNoOfPoles();
		String Descr = (row+1) + " " + (col+1) + " " + (row+1-2) + " " + (col+1);
		Node node = new Node(this,Descr,temp);
		manhattanDistance = calculateManhattanDistance(row-2,col);
		return node;
	}
	
	//As described on MoveRight
	public Node moveDown(int row,int col) {
		Table temp = new Table (table.getData(),table.getNoOfPoles());
		temp.setListElement(row+2, col, 1);
		temp.setListElement(row, col, 2);
		temp.setListElement(row+1, col, 2);
		temp.decreaseNoOfPoles();
		String Descr = (row+1) + " " + (col+1) + " " + (row+1+2) + " " + (col+1);
		Node node = new Node(this,Descr,temp);
		manhattanDistance = calculateManhattanDistance(row+2,col);
		return node;
	}
	
	//Checks whole table for poles( ==1 ) , when it finds one checks if there is a valid move and if yes it creates a new child node
	public ArrayList<Node> findChildren() {
		for(int i=0;i<table.getNoOfRows();i++) {
			for(int j=0;j<table.getNoOfColumns();j++) {
				if(table.getListElement(i,j)==1) {
					if(isValidMoveRight(i,j)){
						//if a solution is found stops the search and returns the child;
						if(moveRight(i, j).getNoOfPoles()==1) {
							children = new ArrayList<Node>();
							children.add(moveRight(i,j));
							return children;
						}
						children.add(moveRight(i,j));
						}
					if(isValidMoveLeft(i,j)){
						if(moveLeft(i, j).getNoOfPoles()==1) {//if a solution is found stops the search and returns the child;
							children = new ArrayList<Node>();
							children.add(moveLeft(i,j));
							return children;
						}
						children.add(moveLeft(i,j));
					}
					if(isValidMoveUp(i,j)){
						if(moveUp(i, j).getNoOfPoles()==1) {//if a solution is found stops the search and returns the child;
							children = new ArrayList<Node>();
							children.add(moveUp(i,j));
							return children;
						}
						children.add(moveUp(i,j));
					}
					if(isValidMoveDown(i,j)){
						if(moveDown(i, j).getNoOfPoles()==1) {//if a solution is found stops the search and returns the child;
							children = new ArrayList<Node>();
							children.add(moveDown(i,j));
							return children;
						}
						children.add(moveDown(i,j));
					}
				}
			}
		}
		return children;
	}

}