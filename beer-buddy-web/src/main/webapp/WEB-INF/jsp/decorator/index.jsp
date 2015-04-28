<!DOCTYPE html>
<html ng-app="beer-buddy-app" ng-strict-di>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<title><sitemesh:write property='title'/></title>

<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<link rel="stylesheet"
	href="<c:url value='/static/lib/bootstrap/3.2.0/css/bootstrap.min.css' />">

<link rel="stylesheet"
	href="<c:url value='/static/lib/bootstrap/3.2.0/css/bootstrap-theme.min.css'/>">

<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/angular-material.css'/>">
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/themes/amber-theme.css'/>">
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/themes/blue-theme.css'/>">
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/themes/cyan-theme.css'/>">
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/themes/green-theme.css'/>">
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/themes/light-blue-dark-theme.css'/>">
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/themes/light-green-theme.css'/>">
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/themes/lime-theme.css'/>">
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/themes/orange-theme.css'/>">
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/themes/red-theme.css'/>">
<link rel="stylesheet" href="<c:url value='/static/bower_components/angular-material/themes/teal-theme.css'/>">


<link rel="stylesheet" href="<c:url value='/static/css/style.css'/>">


<sitemesh:write property='head'/>

</head>
<body>
	<div class="container theme-showcase" data-role="main">

		<%--
		<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
		--%>
      	<sitemesh:write property='body'/>

	</div>

	<!--
	<script src="<c:url value='/static/lib/angularjs/1.3.2/angular.min.js'/>"></script>
	<script src="<c:url value='/static/lib/angularjs/1.3.2/angular-resource.js'/>"></script>
	 -->
	<script type="text/javascript">
		var beerBuddyContext = {
			contextPath : "<%= request.getContextPath() %>"
			<c:if test="${not empty user}">
			, user : {
				  "name" : "${user.name}",
				  "lastLogin" : "${user.lastLogin}",
				  "username" : "${user.username}",
				  "email" : "${user.email}",
				  "lastLoginInMillis" : ${user.lastLoginInMillis},
				  "isLoggedIn": true
			}
			</c:if> 
		};
	</script>
	<script src="<c:url value='/static/js/main.js'/>"></script>
	<script src="<c:url value='/static/js/ng-app.js'/>"></script>
	<script src="<c:url value='/static/js/ng-home.js'/>"></script>
	<script src="<c:url value='/static/js/ng-login.js'/>"></script>
</body>
</html>
