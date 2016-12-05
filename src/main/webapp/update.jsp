<%@ page language="java" import="com.mts.test.*"%>
<%
    Integer id;
     try {
         id = Integer.decode(request.getParameter("id_input"));

         String name1 = request.getParameter("name1_input");
         String name2 = request.getParameter("name2_input");
         if (name1 != null && name2 != null &&
             name1 != "" && name2 != "") {
             TestDbController.updateRecord(id, name1, name2);
             response.sendRedirect("/TestWebApp");
         }
         else {
              request.setAttribute("action_result", "Incorrect name value.");
              request.getRequestDispatcher("/").forward(request, response);
         }
     } catch (NumberFormatException ex) {
         request.setAttribute("action_result", "Incorrect id.");
         request.getRequestDispatcher("/").forward(request, response);
     }
%>