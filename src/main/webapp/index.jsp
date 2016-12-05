<html>
    <head>
        <title>TestWebApp</title>
    </head>
    <body>
        <%@ page language="java" isELIgnored="false" import="com.mts.test.*, java.util.List, javax.naming.InitialContext, javax.sql.DataSource, java.sql.Connection"%>
        <%
            String errorResult = (String)request.getAttribute("action_result");
            if (errorResult != null)
                out.println(errorResult);
        %>
        <br>

        <form action="insert.jsp" method="POST">
            <table border="2">
                <tr>
                    <th>First Name</th>
                    <th>Second Name</th>
                </tr>
                <tr>
                    <td><input type="text" name="name1_input"></td>
                    <td><input type="text" name="name2_input"></td>
                </tr>
            </table>
            <input type="submit" value="Insert">
        </form>
        <br>
        <br>
        <form action="update.jsp" method="POST">
            <table border="2">
                <tr>
                    <th>Identifier</th>
                    <th>First Name</th>
                    <th>Second Name</th>
                </tr>
                <tr>
                    <td><input type="text" name="id_input"></td>
                    <td><input type="text" name="name1_input"></td>
                    <td><input type="text" name="name2_input"></td>
                </tr>
            </table>
            <input type="submit" value="Update">
        </form>
        <br>
        <br>

        <a href="/TestWebApp/?field=name1&sortOrder=ASC">Java ASC sort by name1</a>
        <a href="/TestWebApp/?field=name1&sortOrder=DESC">Java DESC sort by name1</a>
        <br>
        <a href="/TestWebApp/?field=name2&sortOrder=ASC">Java ASC sort by name2</a>
        <a href="/TestWebApp/?field=name2&sortOrder=DESC">Java DESC sort by name2</a>


        <table border="2">
            <tr>
                <th>id</th>
                <th>name1</th>
                <th>name2</th>
                <th>action</th>
            </tr>
            <%
                String sortField = request.getParameter("field");
                String sortOrder = request.getParameter("sortOrder");
                List<Record> result = TestDbController.getData(sortField, sortOrder);

                for (int i = 0; i < result.size(); i++) {
            %>
                    <tr>
                        <td><%= result.get(i).id %></td>
                        <td><%= result.get(i).name1 %></td>
                        <td><%= result.get(i).name2 %></td>
                        <td><a href="/TestWebApp/delete.jsp?id=<%= result.get(i).id %>">Delete</a>
                        </td>
                    </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
