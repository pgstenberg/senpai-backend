
var applicationController = function($rootScope, $scope, $http, $routeParams,$interval) {

	
	
	 $scope.postMethod = function () {
	 
		$scope.methodButtonDisabled = true;
		
		var remoteCall = {"remoteCode":$scope.remote.remote_id, 
				"methodName":$scope.remote.method.methodName,
				"parameters":$scope.remote.method.parameters};
		
		console.log(remoteCall);
		
		$http.post('api/remote/method/call', remoteCall,{headers: {'Content-Type': 'application/json; charset=utf-8'}}).
		  success(function(data, status, headers, config) {
			  $scope.methodButtonDisabled = false;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.methodButtonDisabled = false;
		  });
		
		 
		 
	 };
  	
  $scope.loadRemoteData = function () { $http.get('api/remote/list?app='+$routeParams.applicationCode).
	    success(function(data) {
	    	$scope.remotes = data;
	    	$scope.remote = $scope.remotes[0];
	    	if (typeof $scope.remote != "undefined") {
	    		$scope.loadRemoteMethods();
	    	}
	    });}; 
	
	    $scope.loadRemoteMethods = function () { $http.get('api/remote/method/list?rmi='+$scope.remote.remote_id).
		    success(function(data) {
		    	$scope.remote.methods = data;
		    	$scope.remote.method = $scope.remote.methods[0];
		   });}; 
	
	
	
	
   $scope.loadLogData = function () { $http.get('api/log/list?app='+$routeParams.applicationCode+'&limit=15').
    success(function(data) {
      $scope.logs = data;
    });};
    
    $scope.loadApplicationData = function () { $http.get('api/application/get?app='+$routeParams.applicationCode).
    success(function(data) {
      $scope.application = data;
    });};
    
    $scope.loadLogData();
    $scope.loadApplicationData();
    $scope.loadRemoteData();
   
   
    
    $scope.refresher = $interval(function(){
		$scope.loadLogData();
	},1000);
    
    $rootScope.$on('$locationChangeSuccess', function() {
        $interval.cancel($scope.refresher);
      });
	
};