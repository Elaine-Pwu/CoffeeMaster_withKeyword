package Process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
	
	public WebNode parent;
	
//	public WebNode leftchild;
//	public WebNode rightchild;
	
	public ArrayList<WebNode> children;
	public WebPage webPage;	
	public double nodeScore;
	public String url;
	
	public WebNode(WebPage webPage){
		this.webPage = webPage;
		
		this.url=webPage.getUrl();
		
		this.children = new ArrayList<WebNode>();
	}
	
	
	
	public WebPage getPage() {
		return webPage;
	}
	public String getPageName() {
		return webPage.name;
	}
	
	
	public void setNodeScore(KeywordList keywords) throws IOException, FileNotFoundException{
		webPage.setScore(keywords);
		nodeScore = webPage.score;
//		System.out.println(nodeScore);
		
		for(WebNode child : children){
			nodeScore += child.nodeScore;
		}

	}
	
	public void addChild(WebNode child){
		this.children.add(child);
		child.parent = this;
	}
	
	public boolean isTheLastChild(){
		if(this.parent == null) return true;
		ArrayList<WebNode> siblings = this.parent.children;
		
		return this.equals(siblings.get(siblings.size() - 1));
	}
	
	public int getDepth(){
		int retVal = 1;
		WebNode currNode = this;
		while(currNode.parent != null){
			retVal++;
			currNode = currNode.parent;
		}
		return retVal;
	}
	
}
