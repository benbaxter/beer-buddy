
var app = angular.module('rate-this-app', ['ngMaterial', 'ui.router', 'ngResource']);

app.config(['$stateProvider', '$urlRouterProvider',  
            function($stateProvider, $urlRouterProvider) {

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
	  ;
	
}]);

