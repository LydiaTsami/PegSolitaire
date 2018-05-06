import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {

	String method,input,output;
	private Node solution,root;
	int NoOfLines,NoOfColumns,NoOfPoles=0;
	ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>();
	Table roottable;
	
	public Game(String method,String input,String output) {
		this.method = method;
		this.input = input;
		this.output = output;
		
		Retrieve(input);
		
		if(data == null) {
			System.out.println("There is no data");
		}
		else startGame();
			
	}
	
	public void startGame() {
		long startTime = System.currentTimeMillis();
		long endTime;
		System.out.println("Game started succesfully!");
		System.out.println("AI finding a solution");
		
		root = new Node(roottable);
		Node solution=null;
		if(method=="depth") {
			DFS dfs = new DFS(root);
			solution = dfs.startDFS();
		}
		else if(method=="best") {
			BestFS best = new BestFS(root);
			solution = best.startBestFS();
		}
		if(solution!=null) {
			endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Solution found in " +TimeUnit.MILLISECONDS.toMillis(totalTime) + " milliseconds");
			System.out.println("Saved at " + output);
			Save(solution);
			check_Solution(solution);
		}
	}
	
	public void Retrieve(String input) {
		int temp;
		try
		{
			File f = new File(input);
			Scanner scan = new Scanner(f);
			NoOfLines = scan.nextInt();
			NoOfColumns = scan.nextInt();
			
			while(scan.hasNextLine()) {
				scan.nextLine();
				ArrayList<Integer> readline= new ArrayList<Integer>();
				for(int j=0;j<NoOfColumns;j++) {
					temp = scan.nextInt();
					if(temp == 1)
						NoOfPoles++;
					readline.add(temp);
				}
				data.add(readline);
			}
			scan.close();
			roottable = new Table(data , NoOfPoles);
		}catch(IOException i){
			System.out.println("Failed to read file");
			i.printStackTrace();
		}
	}
	
	public void Save(Node solution){
		int moveCount=0;
		ArrayList<String> Description = new ArrayList<String>();
		while(solution!=root){
			Description.add(0, solution.getmoveDescription());
			solution = solution.getParent();
			moveCount++;
			}
		Description.add(0, Integer.toString(moveCount));
		
	    PrintWriter pw;
		try {
			FileOutputStream out = new FileOutputStream(output);
			pw = new PrintWriter(new FileOutputStream(output));
		    for (String desc : Description)
		        pw.println(desc);
		    pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Couldn't save to: " + output);
			e.printStackTrace();
		}
	}
	
	public void check_Solution(Node solution) {
		Table temp = new Table (solution.getTable().getData(),solution.getTable().getNoOfPoles());
		int NoOfMoves;
		ArrayList<Integer> move = new ArrayList<Integer>();
		ArrayList<String> Description = new ArrayList<String>();
		while(solution!=root){
			Description.add(solution.getmoveDescription());
			solution = solution.getParent();
		}
		for(String desc :Description) {
			String desc1 = desc.replaceAll("\\s+","");
			move.add(Character.getNumericValue(desc1.charAt(0)));
			move.add(Character.getNumericValue(desc1.charAt(1)));
			move.add(Character.getNumericValue(desc1.charAt(2)));
			move.add(Character.getNumericValue(desc1.charAt(3)));

			temp.setListElement(move.get(2)-1, move.get(3)-1, 2);
			temp.setListElement(move.get(0)-1, move.get(1)-1, 1);
			temp.setListElement((move.get(0)+move.get(2))/2-1, (move.get(3)+move.get(1))/2-1, 1);
			move.clear();
		}
		if(temp.getData().equals(root.getTable().getData())) {
			System.out.println("Solution is Correct");
		}
		else
			System.out.println("Solution is incorrect");
	}
	
	
}