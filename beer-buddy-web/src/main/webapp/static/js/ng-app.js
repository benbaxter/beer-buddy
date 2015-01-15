
var app = angular.module('beer-buddy-app', ['ngMaterial', 'ui.router', 'ngResource', 'ngMessages']);

app.config(['$stateProvider', '$urlRouterProvider', "$httpProvider",   
            function($stateProvider, $urlRouterProvider, $httpProvider) {

	var csrfToken = angular.element(document.querySelector("meta[name='_csrf']")).attr("content");
	var csrfHeader = angular.element(document.querySelector("meta[name='_csrf_header']")).attr("content");
	if( csrfHeader && csrfHeader !== "" ) {
		$httpProvider.defaults.headers.common[csrfHeader] = csrfToken;
	}

	$urlRouterProvider.otherwise("/home");

	$stateProvider
	    .state('home', {
	      url: "/home",
	      templateUrl: "/static/partials/home.html",
	      controller: "HomeController"
	    })
	    
	   .state('login', {
	      url: "/login",
	      templateUrl: "/static/partials/login.html",
	      controller: "LoginController"
	    })
	    
	    .state('logout', {
	      url: "/logout",
	      templateUrl: "/static/partials/login.html",
	      controller: "LogoutController"
	    })
	  ;
	
}])

;

