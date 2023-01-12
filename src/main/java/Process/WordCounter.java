package Process;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.net.ssl.SSLHandshakeException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import javax.servlet.ServletException;


public class WordCounter {
	private String urlStr;
    private String content;
    private String name;
    
//    long start = System.currentTimeMillis();
//    long end=start+20*1000;
    
    
    public WordCounter(String urlStr){
    	this.urlStr = urlStr;
//    	this.name=name;
    }
    
    private String fetchContent() throws IOException,FileNotFoundException, SSLHandshakeException {
		long start=System.currentTimeMillis();
		System.out.println("runnig...");

//    	do {
    		
	    	URL url = new URL(this.urlStr);
	    	URLConnection conn = url.openConnection();
	    	conn.setRequestProperty("User-Agent","Chrome/107.0.5304.107");
			conn.setRequestProperty("http.agent","Chrome/107.0.5304.107");
			conn.setRequestProperty("authorization","Chrome/107.0.5304.107");
			conn.setRequestProperty("Accept", "*/*");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			
			InputStream in = conn.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			
			String retVal = "";
		
			String line = null;
			
			while ((line = br.readLine()) != null){
			    retVal = retVal + line + "\n";
			}
		return retVal;
//		}while((System.currentTimeMillis()-start)<30000);
    	
    }
		
    
//    private String fetchContent() throws IOException{
//		URL url = new URL(this.urlStr);
//		URLConnection conn = url.openConnection();
//		InputStream in = conn.getInputStream();
//		BufferedReader br = new BufferedReader(new InputStreamReader(in));
//	
//		String retVal = "";
//	
//		String line = null;
//		
//		while ((line = br.readLine()) != null){
//		    retVal = retVal + line + "\n";
//		}
//	
//		return retVal;
//    }
    
    public int BoyerMoore(String T, String P){
          int i = P.length() -1;
          int j = P.length() -1;
          
         while(i<=T.length()-1) {
        	 if(T.charAt(i)==P.charAt(j)) {
            	 if(j==0) {
            		return i;
            	 }else {
            		 i=i-1;
            		 j=j-1;
            	 }
             }else {
            	 int l=last(T.charAt(i),P);
            	 i=i+P.length()-min(j,1+l);
            	 j=P.length()-1;
             }
           
         }
         return -1;
    }

    public int last(char c, String P){
    	    if(P.indexOf(c)!=-1) {
    		int n=P.length()-1;
    		for(int i=n;i>=0;i--) {
    			if(P.charAt(i)==c) {
    				return i;
    			}
    			
    		}
    	}
        return -1;
    }

    public int min(int a, int b){
        if (a < b)
            return a;
        else if (b < a)
            return b;
        else 
            return a;
    }
    
    

    public int countKeyword(String keyword) throws IOException,FileNotFoundException{
 		if (content == null){
// 			System.out.print(System.currentTimeMillis());
// 			long start=System.currentTimeMillis();
// 			do {
 				content = fetchContent();
 				System.out.println("success get content");
// 			}while((System.currentTimeMillis()-start)<30*1000);
 		}
 		
 		content = content.toUpperCase();
 		keyword = keyword.toUpperCase();

 	    int retVal = 0; 

 		int n = content.length();
 		int m = keyword.length();
 		int i = BoyerMoore(content,keyword);
 		while(i != -1) {
 			retVal++;
 			content = content.substring(i+m,n-1);
 			n = content.length();
 			i = BoyerMoore(content,keyword);
 		}
 		
 		return retVal;
     }
}
