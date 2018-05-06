import java.util.ArrayList;

public class Table {
		private ArrayList<ArrayList<Integer>> data; // 0-> out of bounds, 2-> free position, 1->pole in position
		private int NoOfPoles;

		public Table(ArrayList<ArrayList<Integer>> data, int NoOfPoles) {
			this.data = data;
			this.NoOfPoles = NoOfPoles;
		}
		
		public int getListElement(int row,int col) {
			if(row>=0 && row<data.size() && col>=0 && col<data.get(0).size())
				return data.get(row).get(col);
			return -1;
		}
		
		public void setListElement(int row, int col,int element) {
			data.get(row).set(col, element);
		}
		
		public void decreaseNoOfPoles() {
			NoOfPoles--;
		}
		
		public int getNoOfPoles() {
			return NoOfPoles;
		}
		
		public int getNoOfRows() {
			return data.size();
		}
		
		public int getNoOfColumns() {
			return data.get(0).size();
		}
		
		public ArrayList<ArrayList<Integer>> getData() {//duplicate the data in the ArrayLists and return the result (deep copy)
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			for(ArrayList<Integer> d : data) {
				temp.add((ArrayList<Integer>)d.clone());
			}
			return temp;
	}
}