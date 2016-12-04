<%@ page language="java" import="com.mts.test.*"%>
<%
    Integer id = Integer.decode(request.getParameter("id"));
    TestDbBean.deleteRecord(id);

    response.sendRedirect("/TestWebApp");
%>