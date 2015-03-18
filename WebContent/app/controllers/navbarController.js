var navbarController = function($scope,$http) {
 		
 $http.get('api/application/list' )
    .success(function(data) {
      $scope.applications = data;
    })
    .error(function(data, status, headers, config) {
       console.log("ERROR!");
    });
};