var adminController = function($scope, $http) {
	
	$scope.clearLogs = function () {
		
		var unixStamp = $scope.logs.clearTo.getTime()/1000;
		
		$http.get('api/log/clear?to='+unixStamp).
	    success(function(data) {
	    	alert(data + " logs cleared to date " + $scope.logs.clearTo);
	    });
		
	};
	
	
	$scope.addApp = function () {
		
		var appAddCall = {"applicationName":$scope.add.application};
		
		$http.post('api/application/add', appAddCall,{headers: {'Content-Type': 'application/json; charset=utf-8'}}).
		  success(function(data, status, headers, config) {
			  alert("Application successfully added with code:" + data);
		  }).
		  error(function(data, status, headers, config) {
			  alert("An error occured!");
			  console.log(data);
		  });
		
	};
	
	//$scope.logs = {"clearFrom":new Date()};
	
	
};