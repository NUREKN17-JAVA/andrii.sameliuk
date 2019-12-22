<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="ua.nure.cs.sameliuk.usermanagment.domain.User" scope="session"/>
<html>
<head>
    <title>User management/ Edit</title>
</head>
<body>
<form action="<%request.getContextPath();%>/edit" method="post">
    <input type="hidden" name="id" value="${user.id}">
    First name <input type="text" name="firstName" value="${user.firstName}"><br>
    Last name <input type="text" name="lastName" value="${user.lastName}"><br>
    <fmt:parseDate value="${user.dateOfBirth}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
    Date of Birth <input type="text" name="date"
                         value="<fmt:formatDate value="${parsedDate}" type="date" pattern="yyyy-MM-dd"/>"><br>
    <input type="submit" name="okButton" value="Ok">
    <input type="submit" name="cancelButton" value="Cancel">
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert('${requestScope.error}');
    </script>
</c:if>
</body>
</html>
