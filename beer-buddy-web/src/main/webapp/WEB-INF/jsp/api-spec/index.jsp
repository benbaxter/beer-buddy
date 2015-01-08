<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<head>
<title>Api Spec</title>
</head>
<body>
	<div class="page-header">
		<h1>Api Spec</h1>
	</div>
	
	<table class="table table-striped">
		<thead>
			<tr><th>Method</th><th>Endpoint</th><th>Description</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${api}" var="a">
				<c:forEach items="${a.value}" var="value">
					<tr>
						<td>
							<c:choose>
								<c:when test="${empty value.methods}"><span class="label label-primary">GET</span></c:when>
								<c:otherwise>
									<c:forEach items="${value.methods}" var="method">
										<c:choose>
											<c:when test="${method == 'GET'}"><c:set var="methodClass" value="primary" /></c:when>
											<c:when test="${method == 'POST'}"><c:set var="methodClass" value="success" /></c:when>
											<c:when test="${method == 'PUT'}"><c:set var="methodClass" value="warning" /></c:when>
											<c:when test="${method == 'DELETE'}"><c:set var="methodClass" value="danger" /></c:when>
											<c:otherwise><c:set var="methodClass" value="primary" /></c:otherwise>
										</c:choose>
										<span class="label label-${methodClass}">
										${method}
										</span>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
						<c:forEach items="${value.patterns}" var="url">
							&quot;${url}&quot;
						</c:forEach>	
						</td>
						<td>${value.description}</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
	
</body>