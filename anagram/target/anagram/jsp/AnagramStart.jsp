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
	
	<script language="javascript" type="text/javascript" src="./../js/jquery-1.3.2.min.js"></script>
	
	<!-- <link href="./../css/login.css" rel="stylesheet" type="text/css" /> -->
  	
	<script type="text/javascript" charset="utf-8">
		
		var hostName = null;

		function getHostName() {
			if (hostName==undefined ||  hostName=="" || hostName==null){
				return "";
			} else{
				return 'https://'+hostName;
			}
		}
	
		function setHostName(value) {
			hostName = value;
			rePost = true;
		}

/* 		function changePassword(){
			$.post(
				'./../ChangePwdServlet',
				$(document.forms.changePwdServlet).serialize(),
				function (data) {
					$("#errorLabel").empty();
					if (data.e == "confirm"){
						redirect();
					}
					else if(data.e == "ko"){
						$("#errorLabel").html(i18n.getString("ccmd.manager.changepwd.problems"));	
                    }
                    else if(data.e == "weak"){
                        $("#errorLabel").html(i18n.getString("ccmd.manager.changepwd.weak"));   
                    }
                    else if(data.e == "pwdDifferent"){
                        $("#errorLabel").html(i18n.getString("ccmd.manager.changepwd.password.different"));   
					}
                    else if(data.e == "invalidDate"){
                    	redirect();  
					}
                    else{
						$("#changePasswordContent").hide();
						$("#leftBlockTitle").hide();
						$("#errorLabel").hide();
						$("#finalDiv").show();
						
					}
				},"json"
			);
		}
	
		function verify() {
			$("#errorLabel").empty();
			var a = $("#newPassword").val();
			var b = $("#confirmPassword").val();
			if (a == "") {
				$("#errorLabel").html(i18n.getString("ccmd.manager.changepwd.empty.password"));
			} else if (b != a) {
				$("#errorLabel").html(i18n.getString("ccmd.manager.changepwd.empty.confirm"));
			}
			else {
				changePassword();
			}
		}
 */	
		
		$(document).ready(function(){
			$("#finalDiv").hide();
		})
	
	</script>
	</head>

	<body id="main">	
		<div id="container">
			<form name="AnagramStartServlet" method="get" action="/AnagramStartServlet.do">
			
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
							</p>
							<p>
								<a class="button" href="javascript:verify();" onclick="this.blur();">
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