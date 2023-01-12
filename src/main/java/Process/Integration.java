package Process;

import java.io.IOException;
import java.net.UnknownHostException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
/**
 * Servlet implementation class Integration
 */
@WebServlet("/Intergation")
public class Integration extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	public ArrayList<WebPage> page;
	public ArrayList<WebNode> node;
	public KeywordList key;
	
    public Integration() throws IOException{
        super();
    }
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UnsupportedEncodingException, FileNotFoundException, UnknownHostException {

    	response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if(request.getParameter("inputKeyword")== null) {
			String requestUri = request.getRequestURI();
			request.setAttribute("requestUri", requestUri);
			request.getRequestDispatcher("Search.jsp").forward(request, response);
			return;
		}
		
		
		
		key = new KeywordList();
		try{
			File input = new File("pro_input.txt");
			if(input.exists()!=true) {
				key.add(new Keyword("咖啡廳",5.0));
				key.add(new Keyword("咖啡",5.0));
				key.add(new Keyword("coffee",5.0));
				key.add(new Keyword("Cafe",5.0));
				key.add(new Keyword("甜點",3.0));
				key.add(new Keyword("下午茶",3.0));
				key.add(new Keyword("文青",3.0));
				key.add(new Keyword("讀書",3.0));
				key.add(new Keyword("不限時",3.0));
				key.add(new Keyword("星巴克",2.0));
				key.add(new Keyword("路易莎",2.0));
				key.add(new Keyword("伯朗",2.0));
				
			}else {
				Scanner read = new Scanner(input);
				while(read.hasNextLine()) {
					String inputkey=read.next();
					double value = (double)read.nextInt();
					Keyword keyword= new Keyword(inputkey, value);
					key.add(keyword);
				}
				read.close();
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("pro_input.txt Not Found");
			e.printStackTrace();
		}


		
		String inputKeyword = request.getParameter("inputKeyword");
		
		if(isContainChinese(inputKeyword)==false) {
			Translator translator = new Translator();
			inputKeyword=inputKeyword.toUpperCase();
			inputKeyword = translator.translate("", "zh-TW", inputKeyword);
			System.out.println("input keyword is translated");
		}else {
			System.out.print(inputKeyword);
		}

		
		  
		GoogleQuery google = new GoogleQuery(inputKeyword);
		HashMap<String, String> query = google.query();
		HashMap<String, String> relate = google.relate();
		
        page = new ArrayList<WebPage>();
        node = new ArrayList<WebNode>();
        
		for(String title: query.keySet()) {
			String url = query.get(title);
			int trash = url.indexOf("&sa");
			if(trash != -1) {
				url = url.substring(0, trash);
			}
			url = url.substring(7);
			String url_de = URLDecoder.decode(url, "UTF-8");

			page.add(new WebPage(title, url_de));

			
		}
		System.out.println("page size:");
		System.out.println(page.size());
		
		for(WebPage p:page) {

			WebNode root = new WebNode(p);
//			try {
//				WebTree webTree = google.getAllLink(root);
//				webTree.setPostOrderScore(key);
//				System.out.println("Sub link total: " + Double.toString(webTree.getScore()));
//				treList.add(webTree);
//				p.setScore(key);
				node.add(root);
//			}catch(IOException e) {
//				e.printStackTrace();
//			}
		}
		System.out.println("node len");
		System.out.println(node.size());
		
		
		QuickSort q = new QuickSort();
		for (WebNode webNode : node) {
			q.add(webNode);
		}


		q.sort();

		String[][] s = q.output();

		System.out.println("s len: ");
		System.out.print(s.length);

		request.setAttribute("query", s);
		
		QuickSort q2 = new QuickSort();
		for (String relateUrl:relate.keySet()) {
			String title = relateUrl;

			String url = relate.get(relateUrl);

			
			q2.add(new WebNode(new WebPage(relateUrl,url)));
			System.out.printf("related title:%s,url:%s\n", title, url);
			
		}


		String[][] r = q2.output();
		request.setAttribute("relate", r);


		

		
		request.getRequestDispatcher("SearchResult.jsp").forward(request, response); 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,FileNotFoundException {

		doGet(request, response);
	}
	
	public static boolean isContainChinese(String str) {
		Pattern p=Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m=p.matcher(str);
		if(m.matches()) {
			return true;
		}
		return false;
	}


}
