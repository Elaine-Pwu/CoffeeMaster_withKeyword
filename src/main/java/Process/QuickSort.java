package Process;

import java.io.IOException;
import java.util.ArrayList;

public class QuickSort {

private ArrayList<WebNode> lst;
private ArrayList<WebTree> treeLst;

	public QuickSort() {
		lst = new ArrayList<WebNode>();
		treeLst = new ArrayList<>();
	}
	
	public void add(WebNode webNode) {
		lst.add(webNode);
	}
	
	public void sort() {
		if(lst.size() == 0)	{
			System.out.println("InvalidOperation");
		}
		else {
			quickSort(0, lst.size()-1);
		}
	}
	
	public void quickSort(int start, int end) {
//		WebNode left=lst.get(start);
//		WebNode right=lst.get(end);
		if(start< end) {
		
			int ind=start;
			WebNode pivot=lst.get(end);
			int index =start;
			for(int i =start;i<end;i++) {
				WebNode n=lst.get(i);
				double a =n.nodeScore + n.webPage.getScore(); 
				double p =n.nodeScore + pivot.webPage.getScore();
				if(a>p){ 
					swap(i,index); 
					index++;
				}
			}
			swap(index, end);
			ind=index;
			quickSort(start,ind-1);
			quickSort(ind+1,end);
		}
	}
	
	
	public void swap(int a, int b) {
		WebNode temp = lst.get(a);
		lst.set(a, lst.get(b));
		lst.set(b, temp);
	}
	
	
	public String[][] output() {
		
		int len = lst.size();
		String[][] matrix = new String[len][2];

//		String[][] matrix=new String[1][2]; for testing
//		matrix[0][0] = (String)"Coffee Nomad";
//		matrix[0][1] = (String)"https://cafenomad.tw";
		
//		ArrayList<WebNode> list=new ArrayList<WebNode>();
//		for(int i =0;i<len;i++) {
////			String n=lst.get(i).webPage.name;
//			WebNode n =lst.get(i);
//			if(list!=null) {
//				if(list.contains(n)) {
//					break;
//				}else {
//					list.add(lst.get(i));
//				}
//			}else {
//				list.add(lst.get(i));
//			}
//			
//		}
		for(int i=0; i<len; i++) {
			matrix[i][0]=(String)((WebNode) lst.get(i)).getPageName().toString();
			matrix[i][1]=(String)((WebNode) lst.get(i)).url.toString();
			System.out.print(lst.get(i).webPage.getScore()+lst.get(i).nodeScore +" ");
			System.out.print(lst.get(i).getPageName()+"\n");
			
		}
		return matrix;
	}
}
