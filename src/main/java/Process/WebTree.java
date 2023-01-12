package Process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class WebTree {
	public WebNode root;
	
	QuickSort sort;
	
	public WebTree(WebNode root){
		this.root = root;
		sort = new QuickSort();
		
	}
	
	public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
		
		sort =new QuickSort();
		
	}
	
	public void setPostOrderScore(KeywordList keywords) throws IOException, FileNotFoundException{
		setPostOrderScore(root, keywords);
	}
	
	private void setPostOrderScore(WebNode startNode, KeywordList keywords) throws IOException, FileNotFoundException{
		//2. compute the score of children nodes via post-order, then setNodeScore for startNode

		for(WebNode child: startNode.children) {
			setPostOrderScore(child, keywords);
		}
		startNode.setNodeScore(keywords);
	}
	
	public void eularPrintTree(){
		eularPrintTree(root);
	}
	
	public void eularPrintTree(WebNode startNode){
		int nodeDepth = startNode.getDepth();
		
		if(nodeDepth > 1) System.out.print("\n" + repeat("\t", nodeDepth-1));

		System.out.print("(");
		System.out.print(startNode.webPage.name + "," + startNode.url);
		//startNode.nodeScore
		
		//3. print child via pre-order
		for(WebNode web : startNode.children) {
			startNode = web;
			eularPrintTree(startNode);
			eularPrintTree(web);
		}
		
		
		System.out.print(")");
				
//		if(startNode.isTheLastChild()) System.out.print("\n" + repeat("\t", nodeDepth-2));	
	}
	
	private String repeat(String str, int repeat){
		String retVal = "";
		for(int i = 0; i < repeat; i++){
			retVal += str;
		}
		return retVal;
	}

	
	
	public WebNode getRoot() {
		return root;
	}
	
	public void ToSort() {
		sort.add(getRoot());
	}
	
	public double getScore() {
		return root.nodeScore;
	}
	

}
