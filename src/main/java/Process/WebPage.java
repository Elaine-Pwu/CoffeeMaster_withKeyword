package Process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
	public String url;
	public String name;
	public WordCounter counter;
	public double score;
	
	public WebPage(String name, String url){
		this.url = url;
		this.name = name;
		this.counter = new WordCounter(url);
	}
	
	public void setScore(KeywordList keywords) throws IOException, FileNotFoundException{
		score = 0;

		for(int i =0; i < keywords.size(); i++) {
			score += keywords.get(i).getWeight()*counter.countKeyword(keywords.get(i).getName());
//			score+=counter.countKeyword(keywords.get(i).getName())*keywords.get(i).getWeight();
		
		}
//		for(Keyword k : keywords){		
//			score += k.weight * counter.countKeyword(k.name);	
//		}
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public double getScore() {
		return this.score;
	}

}
