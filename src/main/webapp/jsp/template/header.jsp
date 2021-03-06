<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% // Ajax url declarations %>
<portlet:resourceURL var="ajaxSearchUrl" 		 id="SearchProjects"></portlet:resourceURL>
<portlet:resourceURL var="ajaxCancelUrl" 		 id="SubmissionCancel"></portlet:resourceURL>
<portlet:resourceURL var="ajaxResumeUrl" 		 id="SubmissionResume"></portlet:resourceURL>
<portlet:resourceURL var="ajaxResumeAllUrl" 	 id="SubmissionResumeAll"></portlet:resourceURL>
<portlet:resourceURL var="ajaxGetDetailsUrl" 	 id="SubmissionDetails"></portlet:resourceURL>
<portlet:resourceURL var="ajaxSearchUrl" 		 id="SearchProjects"></portlet:resourceURL>
<portlet:resourceURL var="ajaxPartialResultUrl"  id="PartialResult"></portlet:resourceURL>
<portlet:resourceURL var="ajaxUpdateStatusUrl" 	 id="StatusUpdater"></portlet:resourceURL>
<portlet:resourceURL var="ajaxDownloadOutputUrl" id="Downloader"></portlet:resourceURL>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>
    <head>
        <!-- load CSS files -->
        <link href="${pageContext.request.contextPath}/css/styles.css?v=144" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/jquery-ui.structure.css?v=104" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/jquery-ui.theme.css?v=106" rel="stylesheet" type="text/css"/>
        
        <!-- load javascript libraries -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/logger.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.flot.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.flot.categories.min.js"></script>
	
		<!-- set javascript variables -->
		<script type="text/javascript">
			$('body').data('context-path', "${pageContext.request.contextPath}");
			
        	$('body').data('ajax-urls', {
                "searchUrl": "<%= ajaxSearchUrl %>",
                "partialResultUrl": "<%= ajaxPartialResultUrl %>",
                "getDetailsUrl": "<%= ajaxGetDetailsUrl %>",
                "downloadOutputUrl": "<%= ajaxDownloadOutputUrl %>"
            });
		</script>
		
		<% if (request.isUserInRole("administrator")) { %>
	        <script type="text/javascript">
	        	var body_data = $('body').data('ajax-urls');
	        	
	        	$('body').data('ajax-urls', $.extend(body_data, {
	                "updateStatusUrl": "<%= ajaxUpdateStatusUrl %>",
	                "cancelUrl": "<%= ajaxCancelUrl %>",
	               	"resumeUrl": "<%= ajaxResumeUrl %>",
	            	"resumeAllUrl": "<%= ajaxResumeAllUrl %>"
	            }));
	        </script>
        <% } %>
	
		<!-- load generic javascript files -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js?v=117"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/html-functions.js?v=133"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/search.js?v=127"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/combined.js?v=183"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/user-level/output-graph.js?v=174"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/user-level/output-table.js?v=112"></script>
			
		<!-- load role specific javascript files -->        
    	<% if (request.isUserInRole("administrator")) { %>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/admin-level/ajax-functions.js?v=114"></script>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/admin-level/project-functions.js?v=132"></script>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/admin-level/project-html-functions.js?v=183"></script>
    	<% } else { %>
    		<script type="text/javascript" src="${pageContext.request.contextPath}/js/user-level/ajax-functions.js?v=113"></script>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/user-level/project-functions.js?v=130"></script>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/user-level/project-html-functions.js?v=186"></script>
    	<% } %>
    </head>
    <body>
        <div id="portlet-wrapper">
            <div id="content">