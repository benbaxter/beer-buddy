angular.module('rate-this-app')

.controller('HomeController', [ '$scope', '$rootScope', 'BeerService', 
	function($scope, $rootScope, BeerService) {

		$rootScope.menu = $rootScope.menu || {};
		//clear out page title so that default takes over
		delete $rootScope.menu.currentPage;
		
		$scope.page = -1;
		$scope.totalPages = 1;
		$scope.lastPage = false;
		
		$scope.hasMore = function() {
			return ! $scope.lastPage; 
		};
		
		$scope.beers = $scope.beers || [];
		$scope.nextPage = function() {
			BeerService.getPage($scope.page + 1, function(page) {
				$scope.beers = angular.copy(page.content, $scope.beers);
				$scope.page = page.number;
				$scope.totalPages = page.totalPages;
				$scope.lastPage = page.last;
			});
		};
	
		//load the first page...
		$scope.nextPage();
		
		var tabs = [
            { title: 'All'}
           ];
		BeerService.getTypes(function(ts){
			angular.forEach(ts, function(type) {
				$scope.tabs.push({title: type});
			});
		});
          $scope.tabs = tabs;
          $scope.selectedIndex = 0;
          $scope.announceSelected = announceSelected;
          $scope.announceDeselected = announceDeselected;
          $scope.addTab = function (title, view) {
            view = view || title + " Content View";
            tabs.push({ title: title, content: view, disabled: false});
          };
          $scope.removeTab = function (tab) {
            for (var j = 0; j < tabs.length; j++) {
              if (tab.title == tabs[j].title) {
                $scope.tabs.splice(j, 1);
                break;
              }
            }
          };
          function announceDeselected(tab) {
              $scope.farewell = 'Goodbye ' + tab.title + '!';
            }
            function announceSelected(tab) {
              $scope.greeting = 'Hello ' + tab.title + '!';
            }
	}])

;


angular.module('rate-this-app')

.service('BeerService', [ '$resource', function($resource) {
	
	var baseUrl = "/beers/";
	
	var BeerApi = $resource(baseUrl + '/:id');
	
	var Types = $resource(baseUrl + '/types');
	
	return {
		getPage : function(page, callback) {
			return BeerApi.get({page: page}, callback);
		}
		, get : function(id) {
			return BeerApi.get({id : id});
		}
		, getTypes : function(callback) {
			return Types.query(callback);
		}
		
	};
	
	
	
}]);