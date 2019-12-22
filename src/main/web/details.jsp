<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" class="ua.nure.cs.sameliuk.usermanagment.domain.User" scope="session"/>

<html>
<head>
    <title>User management/ Details</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/details" method="post">
    <p>ID: ${user.id}</p>
    <p>First name: ${user.firstName}</p>
    <p>Last name: ${user.lastName}</p>
    <p>Date of Birth: ${user.dateOfBirth}</p>
    <input type="submit" name="backButton" value="Back">
</form>
</body>
</html>
