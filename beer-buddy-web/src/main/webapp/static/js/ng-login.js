angular.module('rate-this-app')

.controller('LoginController', [ '$scope', '$rootScope', '$state',
	function($scope, $rootScope, $state) {
		
	$rootScope.menu = $rootScope.menu || {};
	$rootScope.menu.currentPage = "Login";

	$scope.user = $scope.user || {};
	
	$scope.submitLogin = function() {
		
		$scope.user.isLoggedIn = true;
		$scope.user.name = "Benjamin Baxter";
		
		$rootScope.user = $scope.user;
		
		$state.go("home");
		
	};
	
	}])

;
