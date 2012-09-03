<%@ page session="true" %>
<html>
    <body>
        <%
            if (request.getParameter("logout")!=null)
            {
                session.removeAttribute("openid");
                session.removeAttribute("openid-claimed");
                session.removeAttribute("email");
                response.sendRedirect(request.getParameter("dest"));
        %>
        
            <%
                }
                    if (session.getAttribute("openid")==null) {
                        session.setAttribute("openid-redirect-dest", request.getParameter("dest"));
            %>
        <form method="POST" action="consumer_redirect.jsp">
            <strong>OpenID:</strong>
            <input type="text" name="openid" size="60" value="https://www.google.com/accounts/o8/id"/><br>
            <input type="submit"/>
        </form>
        <%	
        } else {

        %>
        Logged in as <%= session.getAttribute("openid") %> with email: <%= session.getAttribute("email") %><p>
            <a href="?logout=true">Log out</a>

            <% } %>
    </body>
</html>
