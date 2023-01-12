<%@page import="java.io.IOException"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="Process.*"%>
<%@page import="Process.Translator"%>
<%@page import="Process.QuickSort"%>
<%@page import="Process.GoogleQuery"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Coffee Master</title>
<link rel="stylesheet" href="SearchResult.css">
</head>
<body>
	<div class="search-div">
		<h1 class="heading">Coffee Master</h1>
		<div class="form-div">
			<form id="search-form" name="search-form" class="form">
				<div class="search-field-div">
					<input type="text" class="text-field" maxlength="100"
						name="inputKeyword" id="inputKeyword" placeholder='請輸入關鍵字'>
				</div>
			</form>
		</div>
	</div>
	<div class="result_div">
		<%
		Keyword keyword = new Keyword(request.getParameter("inputKeyword"), 3);
		String keywordName = keyword.getName();
		Translator translator = new Translator();
		try {
			System.out.println("Translated text: " + translator.translate("", "zh-TW", keywordName));
		} catch (IOException e) {
			System.out.println("IOException");
		} catch (Exception e) {
			System.out.println("");
		}
		%>
		
		<%
		GoogleQuery g = new GoogleQuery(request.getParameter("inputKeyword"));
		%>
		<script type="text/javascript">
			
		</script>
		<%
		//GoogleQuery g = new GoogleQuery("貓");
		//HashMap<String, String> result = g.query();
		//QuickSort q = new QuickSort();
		//for (String key : result.keySet()) {
			//q.add(new WebNode(new WebPage(key, result.get(key))));
		//}
		//q.sort();
		//String[][] r = q.output();
		String[][] r = (String[][]) request.getAttribute("query");
		for (int i = 0; i < r.length; i++) {
			String s = r[i][1];
			//s = s.substring(7);
		%>

		<a href=<%=s%>><%=r[i][0]%> </a><br><br>
		<%
		}
		System.out.println("finished");
		%>
	</div>
	
	<div class="relative_div">
					<h1>相關搜尋：</h1>
			
			<%
			
			String[][] relate = (String[][]) request.getAttribute("relate");
			for (int i = 0; i < relate.length; i++) {
				String rel = relate[i][1];
			%>
			<a href='<%=rel%>'><%=relate[i][0]%> </a> <br> <br> <br>
			<%
			}
			%>
		</div>

</body>
</html>
