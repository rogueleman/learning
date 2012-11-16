<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" session="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.leman.anagram.servlets.AnagramStartServlet"%>
<html>
	<head>
	<title>Anagram Start</title>
	
	<script language="javascript" type="text/javascript" src="./../anagram/js/jquery-1.3.2.min.js"></script>
	
	<!-- <link href="./../css/login.css" rel="stylesheet" type="text/css" /> -->
  	
	<script type="text/javascript" charset="utf-8">
		
 		function verifyAnagram(){
			$.post(
				'./../anagram/AnagramStartServlet',
				$(document.forms.AnagramStartServletForm).serialize(),
				function (data) {
					$("#errorLabel").empty();
					if(data.e == "ko") {
						alert("ko");
						$("#errorLabel").html(i18n.getString("ccmd.manager.changepwd.problems"));	
                    } else {
                    	alert("else");
						$("#errorLabel").hide();
					}
				},"json"
			);
		}
		
		function verifyField() {
			$("#errorLabel").empty();
			var a = $("#text").val();
			if (a == "") {
				$("#errorLabel").html(i18n.getString("ccmd.manager.changepwd.empty.password"));
			}
			else {
				verifyAnagram();
			}
		}

		
		$(document).ready(function(){
		});
	
	</script>
	</head>

	<body id="main">
	Body	
		<div id="container">
			<form name="AnagramStartServletForm">
			
				<div class="containerTop"></div>
				<div class="containerBackground">
					<div class="containerContent">
					
						<div class="hrule"></div>
						
						<div id="changePasswordContent">
							<p>
							<%String word = (String)request.getAttribute("word"); %>
							<%=word%>
							</p>
							<p>
								Scrie aici:<br/>
								<input type="text" id="text" name="text" class="inputText" autocomplete="off" />
								<input type="hidden" id="word" name="word" value="<%=word%>">  
								<input type="hidden" id="anagramEntity" name="anagramEntity" value="<%=request.getAttribute("anagramEntity")%>">  
							</p>
							<p>
								<a class="button" href="javascript:verifyField();" onclick="this.blur();">
									<span>Submit</span>
								</a>
								
								
							</p>
						</div>

						<h4 id="errorLabel"></h4>
						
					</div>
				</div>
			</form>
		</div> 
		
	</body>
</html>