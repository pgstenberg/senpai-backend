
	
	
var appConfig = function($routeProvider) {

		$routeProvider
			.when('/application/:applicationCode', {
				templateUrl : 'views/applicationView.html',
				controller  : 'ApplicationController'
			})
			.when('/logs', {
				templateUrl : 'views/logsView.html',
				controller  : 'LogsController'
			})
			.when('/admin', {
				templateUrl : 'views/adminView.html',
				controller  : 'AdminController'
			})
			.otherwise({ redirectTo: '/logs' });
		
		
};