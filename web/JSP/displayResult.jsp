<%-- 
    Document   : displayResult
    Created on : Jul 30, 2015, 7:02:57 PM
    Author     : Hamza
--%>
<%@page import="OutputData.OutputSentencesStrings"%>
<%@page import="javax.script.ScriptEngineManager"%>
<%@page import="java.awt.TextArea"%>
<%@page import="javax.swing.text.Highlighter"%>
<%@page import="Common.StringProperty"%>
<%@page import="Common.SimilarDataProperties"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="Common.DisplayDataProperty" %>
<
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript" src="${pageContext.request.contextPath}/JavaScript/Highlightor.js"></script>
<!--<script type="text/javascript">

  var myHilitor = new Hilitor("content");
  myHilitor.apply("highlight words");

</script>-->
<script>
//higlightTxtInTextarea('txtArea', 'baba');



</script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Plagiarism Detection Project</title>
<link href='http://fonts.googleapis.com/css?family=Abel' rel='stylesheet' type='text/css'/>
<link href="${pageContext.request.contextPath}/Style/style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
    <div id="wrapper">
	<div id="header-wrapper" class="container">
            <div id="header" class="container">
		<div id="logo">
			<h1><a href="#">Plagiarism Detection Project </a></h1>
		</div>
                <div id="menu">
			<ul>
				<li class="current_page_item"><a href="${pageContext.request.contextPath}">Homepage</a></li>
			</ul>
		</div>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/images/img03.png" width="1000" height="40" alt="" />
            </div>
	</div>
	<!-- end #header -->
	<div id="page">
            <div id="content_display">
		<div class="post">
                    <%--<h2 class="title"><a href="#">Results</a></h2>
                        <div style="clear: both;">&nbsp;</div>--%>
                    <%--<div class="entry">--%>
                    <h3>Suspicious File</h3>
                        <br />
                        <h3><div style="text-align: center;font-weight:bold;color:#000"><%=request.getAttribute("Ratio")%> % similarity index</div></h3> <br />
                    <table width="100%">
                        <tr>
                            <td width="435">
                                <h3>Suspicious File Data</h3> <br />
                            </td>
                            <td width="435">
                                <h3>Source Data</h3> <br />
                            </td>
                        </tr>
                        <tr>
                            
                            
                            <td width="435" style="vertical-align:  top; height: 500px">
                                <!--<textarea rows="25" cols="58" readonly="yes" id="txtSuspeciousFile"> -->
                                <% 
                                    List<List<String>> oCopyList = new ArrayList();
                                    //ArrayList<String> oDisplayList = (ArrayList<String>)request.getAttribute("DisplayList");
                                    OutputSentencesStrings oDisplayObj = (OutputSentencesStrings) request.getAttribute("DisplayList");
                                    if(oDisplayObj != null) {
                                        oCopyList = oDisplayObj.getCopiedFileList();
                                        if(oCopyList != null && oCopyList.size() > 0) {
                                            List<String> oList = oCopyList.get(0);
                                            for(int i = 0; i < oList.size(); i++) {
                                                //String sFileText = oDisplayList.get(0).toString();
                                                out.print(oList.get(i).toString());                                    
                                            }
                                        }
                                        else {
                                            out.println("No match found");
                                        }
                                    }
                                    else {
                                        out.println("No match found..");
                                    }
                                %>   
                                <!--</textarea> -->
                            </td> 
                            <td width="435" style="vertical-align:  top; height: 500px">
                                <!--<textarea rows="25" cols="58" readonly="yes" id="txtSource" text=""> -->
                                        <% 
                                            //ArrayList<DisplayDataProperties> oDisplayList = (ArrayList<DisplayDataProperties>)request.getAttribute("DisplayList");
                                    
                                            //OutputSentencesStrings oDisplayObj = (OutputSentencesStrings) request.getAttribute("DisplayList");
                                            if(oDisplayObj != null)
                                            {
                                                List<DisplayDataProperty> displayDataPropertiesList = oDisplayObj.getDisplayDataPropertiesList();
                                                int counter = 0;
                                                for(int nCount = 0; nCount < displayDataPropertiesList.size(); nCount++)
                                                {
                                                    DisplayDataProperty displayDataProperties = displayDataPropertiesList.get(nCount);
                                                    List<StringProperty> stringProperties = displayDataProperties.getStringProperties();
                                                    if(stringProperties != null && stringProperties.size() > 0) {
                                                        counter++;
                                                        out.println("<div style=\"color:"+ stringProperties.get(0).getColor()+"\">");
                                                        out.println("<div style=\"height: 100; overflow: auto; border: 1px solid black;margin:5px 0\">");
                                                        out.println("<div style=\"font-weight:bold\">");
                                                        out.println("Similarity:" + displayDataProperties.getSimilarDataProperties().getSimilarityValue());
                                                        out.println("</div>");
                                                        out.println("<div>");
                                                        out.println("Source" + counter + ":" + displayDataProperties.getSimilarDataProperties().getUrl());
                                                        out.println("</div>");
                                                        out.println("<div>");
                                                        out.println("Length:" + stringProperties.get(0).getLengthInInternetFile());
                                                        out.println("</div>");
                                                        out.println("<div>");
                                                        out.println("Words:" + stringProperties.get(0).getNumberOfWords());
                                                        out.println("</div>");

                                                        out.println("\r\n");
                                                        out.println("\r\n");
                                                        out.println("\r\n");
                                                        out.println("\r\n<b>---------------------------------------------------</b>");
                                                        out.println("</div>");
                                                        out.println("</div>");
                                                    }
                                                }
                                                /*
                                                List<List<String>> oSourceList = oDisplayObj.getSourceFileList();
                                                if(oSourceList != null && oSourceList.size() > 0 && oCopyList != null)
                                                {
                                                    for(int nCount = 0; nCount < oSourceList.size(); nCount++)
                                                    {
                                                        List<String> oList = oCopyList.get(0);
                                                        out.println("<div style=\"height: 5\"></div>");
                                                        out.println("<div style=\"height: 100; overflow: auto; border: 1px solid black\">");
                                                        out.println("Source"+ (nCount + 1));
                                                        out.println("\r\n");
                                                        if(oList != null && oList.size() > 0)
                                                        {
                                                            for(int j = 0; j < oList.size(); j++)
                                                            {
                                                                out.println(oList.get(nCount).toString());
                                                            }
                                                        }
                                                        out.println("\r\n");
                                                        out.println("\r\n");
                                                        out.println("\r\n<b>---------------------------------------------------</b>");                                        
                                                        out.println("</div>");
                                                    }
                                                }
                                                */
                                            }
                                        %>
                            </td>                            
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                    <%--</div>--%>
		</div>
                <div style="clear: both;">&nbsp;</div>
            </div>
            <!-- end #content -->
<!--            <div id="sidebar">
                <div class="post">
                    <h3>Source Files</h3> <br />
                </div>
                <div style="clear: both;">&nbsp;</div>
            </div>-->
            <!-- end #sidebar -->
            <div style="clear: both;">&nbsp;</div>
	</div>
	<div class="container">
            <img src="${pageContext.request.contextPath}/images/img03.png" width="1000" height="40" alt="" />
        </div>
	<!-- end #page -->
    </div>
    <!-- end #footer -->
</body>
</html>