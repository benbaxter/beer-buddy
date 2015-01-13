angular.module('beer-buddy-app')

.controller('LoginController', [ '$scope', '$rootScope', '$state', '$resource',
	function($scope, $rootScope, $state, $resource) {

	var User = $resource("/user");
	var Login = $resource("/login");
	
	$rootScope.menu = $rootScope.menu || {};
	$rootScope.menu.currentPage = "Login";

	$scope.user = {};
	$rootScope.user = $scope.user;
	
	var clearErrors = function() {
		delete $scope.loginForm.username.$error.serverError;
		delete $scope.loginForm.username.$error.serverErrorMessage;
		delete $scope.error;
		clearFlags();
	};
	var clearFlags = function() {
		$scope.signupComplete = false;
		$scope.signupInProgress = false;
		$scope.loginInProgress = false;
	};
	clearFlags();
	
	$scope.submitLogin = function() {
		clearErrors();
		if( $scope.loginForm.$valid ) {
			$scope.loginInProgress = true;
			new Login($scope.user).$save(function(response) {
				console.log(response);
				
				$scope.signupComplete = false;
				$scope.user.isLoggedIn = true;
				$scope.user.name = "Benjamin Baxter";
				$rootScope.user = $scope.user;
				
				$scope.loginInProgress = false;
				$state.go("home");
			}, function(error) {
				console.log(error);
				if( error ) {
					$scope.error = error.data || {}; 
				}
				$scope.loginInProgress = false;
			});
		}
	};
	
	
	$scope.submitSignup = function() {
		clearErrors();
		if( $scope.loginForm.$valid ) {
			$scope.signupInProgress = true;
			new User($scope.user).$save(function(response) {
				console.log(response);
				$scope.signupComplete = true;
				$scope.signupInProgress = false;
			}, function(error) {
				console.log(error);
				if( error.data.error ) {
					$scope.loginForm.username.$error.serverError = error.data.error; 
					$scope.loginForm.username.$error.serverErrorMessage = error.data.message;
				}
				$scope.signupInProgress = false;
			});
		}
	};
	
	
	}])

.controller('LogoutController', [ '$scope', '$rootScope', '$state', '$resource',
	function($scope, $rootScope, $state, $resource) {

	var Logout = $resource("/logout");
	
	$rootScope.menu = $rootScope.menu || {};
	$rootScope.menu.currentPage = "Login";

	Logout.get(function(response) {
		$state.go("login");
	});
	
	}])


;
