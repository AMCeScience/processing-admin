<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% // Ajax url declarations %>
<portlet:resourceURL var="ajaxSearchUrl" id="SearchProjects"></portlet:resourceURL>
<portlet:resourceURL var="ajaxDoSearchAndUpdateUrl" id="doSearchAndUpdate"></portlet:resourceURL>
<portlet:resourceURL var="ajaxUpdateStatusUrl" id="StatusUpdaterAjax"></portlet:resourceURL>
<portlet:resourceURL var="ajaxCancelUrl" id="SubmissionCancel"></portlet:resourceURL>
<portlet:resourceURL var="ajaxResumeUrl" id="SubmissionResume"></portlet:resourceURL>
<portlet:resourceURL var="ajaxResumeAllUrl" id="SubmissionResumeAll"></portlet:resourceURL>
<portlet:resourceURL var="getDetailsUrl" id="SubmissionDetails"></portlet:resourceURL>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>
    <head>
        <!-- load CSS files -->
        <link href="${pageContext.request.contextPath}/css/styles.css?v=126" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/jquery-ui.structure.css?v=101" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/jquery-ui.theme.css?v=101" rel="stylesheet" type="text/css"/>
        
        <!-- load javascript libraries -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/logger.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min.js"></script>
        
        <script type="text/javascript">
        	$('body').data('ajax-urls', {
                "searchUrl": "<%= ajaxSearchUrl %>",
                "searchAndUpdateUrl": "<%= ajaxDoSearchAndUpdateUrl %>",
                "updateStatusUrl": "<%= ajaxUpdateStatusUrl %>",
                "cancelUrl": "<%= ajaxCancelUrl %>",
               	"resumeUrl": "<%= ajaxResumeUrl %>",
            	"resumeAllUrl": "<%= ajaxResumeAllUrl %>",
            	"getDetailsUrl": "<%= getDetailsUrl %>"
            });
        </script>
        
        <!-- load javascript function files -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajax-functions.js?v=112"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/project-functions.js?v=128"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js?v=112"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/combined.js?v=171"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/search.js?v=126"></script>
    </head>
    <body>
        <div id="portlet-wrapper">
            <div id="content">