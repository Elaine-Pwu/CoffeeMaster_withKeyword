package Process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.HashMap;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
	public String searchKeyword;
	public String url;
	public String content;
	public String title;
	public String citeUrl;
	public String relativetitle;
	public String citeUrl2;
	public WordCounter counter;
	
	public String[] keyNoiseLst = {"accounts.google.com", ".php", "www.youtube.com", "shopee.tw", "tiktok", "inline.app","zh.wikipedia.org","www.appledaily.com.tw"};
	

	
	public GoogleQuery(String searchKeyword) {
		this.url = "http://www.google.com/search?q="+searchKeyword+ "+咖啡廳" +"&oe=utf8&num=20";
//		System.out.println(url);
	}
	
	private String fetchContent() throws IOException, FileNotFoundException {
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		conn.setRequestProperty("http.agent", "Chrome/107.0.5304.107");
		conn.setRequestProperty("authorization","Chrome/107.0.5304.107");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line = bufReader.readLine()) != null) {
			retVal += line;
		}
		return retVal;
	}
	

	
	public HashMap<String, String> query() throws IOException, FileNotFoundException {
		if(content == null) {
			content = fetchContent();
		}

		HashMap<String, String> retVal = new HashMap<String, String>();
		HashMap<String, WebTree> retValPro = new HashMap<String, WebTree>();
		/* 
		 * some Jsoup source
		 * https://jsoup.org/apidocs/org/jsoup/nodes/package-summary.html
		 * https://www.1ju.org/jsoup/jsoup-quick-start
 		 */
		
		//using Jsoup analyze html string
		Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
		
		for(Element li : lis) {
			try {
				title = li.select("a").get(0).select(".vvjwJb").text();
				citeUrl = li.select("a").get(0).attr("href");
				
				if(citeUrl.contains("ent.ltn.com.tw") || citeUrl.contains("kellyrosie12.com") || citeUrl.contains("inline.app")||citeUrl.contains("www.appledaily.com.tw")||citeUrl.contains("tw.yahoo.com")) {
					continue;
				}
				
				counter = new WordCounter(citeUrl);
				
				if(title.equals("")) {
					continue;
				}
				
				System.out.println("Title: " + title + " , url: " + citeUrl);
				
				//put title and pair into HashMap
				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {
//				e.printStackTrace();
			}
		}
		return retVal;
	}
	
	public HashMap<String, String> relate() throws IOException {
		if(content == null) {
			content = fetchContent();
		}

		
		
		
		

//		String content = fetchContent();
		HashMap<String, String> retVal = new HashMap<String, String>();

		// using Jsoup analyze html string
		Document doc = Jsoup.parse(content);

		// select particular element(tag) which you want


		Elements lis2 = doc.select("div");
		lis2 = lis2.select("a").select(".Q71vJc");

		
		for (Element li : lis2) {
			
			citeUrl2 = li.select("a").get(0).attr("href");
//			counter = new WordCounter(citeUrl);
			citeUrl2 =this.url+citeUrl2;
			relativetitle = li.select("a").get(0).select(".s3v9rd").text();
			
			

			if (relativetitle.equals("")) {
				continue;
			}

			retVal.put(relativetitle, citeUrl2);	

		}
		return retVal;
	}

	
}
