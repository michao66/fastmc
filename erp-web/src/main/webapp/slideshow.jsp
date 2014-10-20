<%@ page isELIgnored ="false"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="/WEB-INF/functionTag.tld" prefix="functionTag" %>
<%@page import="org.springframework.security.core.userdetails.*"%>
<%@page import="org.springframework.security.core.*"%>
<%@page import="org.springframework.security.web.*"%>
<%@page import="org.springframework.security.authentication.*"%>
<%@page import="cn.fastmc.web.captcha.BadCaptchaException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<style>
	    body { font-family: Helvetica, Arial, sans-serif; line-height: 1.3em; -webkit-font-smoothing: antialiased; }
	    .container {
	        width: 90%;
	        margin: 20px auto;
	        background-color: #FFF;
	        padding: 20px;
	    }
	    pre, code {
        font-family: Monaco, Menlo, Consolas, "Courier New", monospace;
        font-size: 12px;
        color: #333;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
      }
      pre { border: 1px solid #CCC; background-color: #EEE; color: #333; padding: 10px; overflow: scroll; }
      code { padding: 2px 4px; background-color: #F7F7F9; border: 1px solid #E1E1E8; color: #D14; }
	</style>
</head>
<body>
    <div class="container">
        <h1>Slideshow Demo</h1>
        <p>This demonstrates a commonly-asked question: how do I use Backstretch to create a slideshow of background images? Easy! Just pass in an array of image paths.</p>
        <pre>&lt;script src=&quot;//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js&quot;&gt;&lt;/script&gt;
&lt;script src=&quot;jquery.backstretch.min.js&quot;&gt;&lt;/script&gt;
&lt;script&gt;
    $.backstretch([
      &quot;pot-holder.jpg&quot;,
      &quot;coffee.jpg&quot;,
      &quot;dome.jpg&quot;
      ], {
        fade: 750,
        duration: 4000
    });
&lt;/script&gt;</pre>
    </div>
<script type="text/javascript" src="<%=basePath%>resource/plugins/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>resource/plugins/backstretch/jquery.backstretch.min.js"></script>
	<script>
        $.backstretch([
          "pot-holder.jpg",
          "coffee.jpg",
          "dome.jpg"
        ], {
            fade: 750,
            duration: 4000
        });
    </script>
</body>
</html>