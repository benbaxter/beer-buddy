
var library = (function() {'use strict';

	function getFormAsJson(formId) {
		var array = $("#" + formId).serializeArray();
	    var data = {};

	    $.map(array, function(n, i){
	        data[n['name']] = n['value'];
	    });
	    
	    return JSON.stringify(data);
	}
	
	function prepareUrl(url) {
		return beerBuddyContext.contextPath + url;
	}

	return {
		getFormAsJson : function(formId) {
			return getFormAsJson(formId);
		}, 
		prepareUrl : function(url) {
			return prepareUrl(url);
		}
	};
})();
