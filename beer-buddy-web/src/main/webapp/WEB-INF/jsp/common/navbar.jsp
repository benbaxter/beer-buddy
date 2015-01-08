<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<nav class="navbar navbar-default" data-role="navigation">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<c:url value='/home.html'/>">AngularJS</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="${activeNav == 'intro' ? 'active' : '' }">
					<a href="<c:url value='/intro.html'/>">Intro</a>
				</li>
				<li class="${activeNav == 'todo' ? 'active' : '' }">
					<a href="<c:url value='/todo.html'/>">Todo</a>
				</li>
				<li class="${activeNav == 'library' ? 'active' : '' }">
					<a href="<c:url value='/library.html'/>">Library</a>
				</li>
				<li class="${activeNav == 'api-spec' ? 'active' : '' }">
					<a href="<c:url value='/api-spec.html'/>">Api</a>
				</li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>