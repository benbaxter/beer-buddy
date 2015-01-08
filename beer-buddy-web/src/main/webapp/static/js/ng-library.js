angular.module('rate-this-app')

.controller('LibraryController', [ '$scope', 'LibraryService', 
	function($scope, LibraryService) {

		var loadBooks = function() {
			$scope.books = LibraryService.getAll();
		};
		$scope.refresh = function() {
			loadBooks();
		};
		//preload books
		loadBooks();
		$scope.newBook = {};
		$scope.save = function(){
			LibraryService.save($scope.newBook);
			loadBooks();
			$scope.newBook = {};
		};
		$scope.remove = function(book){
			LibraryService.remove(book);
			loadBooks();
		};
		$scope.search = function() {
			$scope.books = LibraryService.get($scope.criteria);
		};
		
		
	}])

.service('LibraryService', ['$resource', function($resource) {
	var Library = $resource('/library/:criteria/:id');
	
	return {
		getAll : function() {
			return Library.query();
		}
		, get : function(criteria) {
			return Library.query({criteria : criteria});
		}
		, save : function(book, success) {
			var newBook = new Library(book);
			newBook.$save(function() {
				success();
			});
		}
		, remove : function(book) {
			var deleteBook = new Library(book);
			deleteBook.$delete({id: book.id});
		}
	};	
}])

;
