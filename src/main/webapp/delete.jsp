<%@ page language="java" import="com.mts.test.*"%>
<%
    Integer id = Integer.decode(request.getParameter("id"));
    TestDbController.deleteRecord(id);

    response.sendRedirect("/TestWebApp");
%>