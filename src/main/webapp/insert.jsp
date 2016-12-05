<%@ page language="java" import="com.mts.test.*"%>
  <%
      String name1 = request.getParameter("name1_input");
      String name2 = request.getParameter("name2_input");

      if (name1 != null && name2 != null &&
          name1 != "" && name2 != "") {
              TestDbController.addRecord(name1, name2);
      }

      response.sendRedirect("/TestWebApp");
  %>