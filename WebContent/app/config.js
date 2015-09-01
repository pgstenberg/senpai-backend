
	
	
var appConfig = function($routeProvider) {

		$routeProvider
			.when('/application/:applicationCode', {
				templateUrl : 'views/applicationView.html',
				controller  : 'ApplicationController'
			})
			.when('/home', {
				templateUrl : 'views/homeView.html',
				controller  : 'HomeController'
			})
			.when('/admin', {
				templateUrl : 'views/adminView.html',
				controller  : 'AdminController'
			})
			.otherwise({ redirectTo: '/home' });
		
		
};