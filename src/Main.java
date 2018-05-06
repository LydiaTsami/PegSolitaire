
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String method=null,input,output;
		input = System.getProperty("user.dir");
		output = System.getProperty("user.dir");
		if(args[0].contains("depth")) {
			method="depth";
		}
		else if(args[0].contains("best")) {
			method="best";
		}
			
		
		input += "\\"+ args[1];
		output += "\\" +args[2];
		
		new Game(method,input,output);
		
	}
}
