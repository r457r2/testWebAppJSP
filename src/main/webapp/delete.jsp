<%@ page language="java" import="com.mts.test.*, java.lang.NumberFormatException, java.sql.SQLException, javax.naming.NamingException"%>
<%
    String idStr = request.getParameter("id");
    if (idStr != null) {
        try {
            Integer id = Integer.decode(idStr);
            TestDbController.deleteRecord(id);
            response.sendRedirect("/TestWebApp");
        } catch (SQLException e) {
            request.setAttribute("action_result", "Error while working with db: " + e.getMessage());
            request.getRequestDispatcher("/").forward(request, response);
        } catch (NamingException e) {
            request.setAttribute("action_result", "Error while loading context with db credentials (be sure resource named \"jdbc/MySQLtest\"): " + e.getMessage());
            request.getRequestDispatcher("/").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("action_result", "Incorrect id to DELETE: " + e.getMessage());
            request.getRequestDispatcher("/").forward(request, response);
        }
    }
    else {
        request.setAttribute("action_result", "No id param to DELETE");
        request.getRequestDispatcher("/").forward(request, response);
    }
%>