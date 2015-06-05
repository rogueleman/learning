<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" session="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.leman.anagram.servlets.GetWordsFromDictionaryServlet"%>
<html>
	<head>
	<title>Anagram Start</title>
	
	<script language="javascript" type="text/javascript" src="./../js/jquery-1.3.2.min.js"></script>
	
	<!-- <link href="./../css/login.css" rel="stylesheet" type="text/css" /> -->
  	
	<script type="text/javascript" charset="utf-8">
		
 		function verifyAnagram(){
			$.post(
				'./../GetWordsFromDictionaryServlet',
				$(document.forms.GetWordsFromDictionaryForm).serialize(),
				function (data) {
					$("#errorLabel").empty();
					if(data.e == "ko") {
						alert("ko");
						$("#errorLabel").html("KO");
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
				$("#errorLabel").html("Error");
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
			<form name="GetWordsFromDictionaryForm">
			
				<div class="containerTop"></div>
				<div class="containerBackground">
					<div class="containerContent">
					
						<div class="hrule"></div>
						
						<div id="changePasswordContent">
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