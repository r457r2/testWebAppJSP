<%@ page language="java" import="com.mts.test.*, java.sql.SQLException, javax.naming.NamingException"%>
  <%
    String name1 = request.getParameter("name1_input");
    String name2 = request.getParameter("name2_input");

    if (name1 != null && name2 != null &&
        name1 != "" && name2 != "") {
        try {
            TestDbController.insertRecord(name1, name2);
            response.sendRedirect("/TestWebApp");
        } catch (SQLException e) {
            request.setAttribute("action_result", "Error while working with db: " + e.getMessage());
            request.getRequestDispatcher("/").forward(request, response);
        } catch (NamingException e) {
            request.setAttribute("action_result", "Error while loading context with db credentials (be sure resource named \"jdbc/MySQLtest\"): " + e.getMessage());
            request.getRequestDispatcher("/").forward(request, response);
        }
    }
    else {
        request.setAttribute("action_result", "No name1 or name2 param to INSERT");
        request.getRequestDispatcher("/").forward(request, response);
    }
  %>