var logsController = function($scope, $http) {


	$scope.logs = {};
 
	$scope.logs = {
			enableFiltering: true,		
			paginationPageSizes: [25, 50, 75],
		    paginationPageSize: 25
	};
	
  	$scope.logs.columnDefs = [
  		{name:'Application',field:'application', width:'15%',cellTemplate: ' <div class="ngCellText" ng-class="col.colIndex()">&nbsp;<a href="#/application/{{ row.entity[col.field].application_id }}">{{ row.entity[col.field].application_id }}</a><span ng-cell-text></span></div>'},
  		{name:'Host',field:'host', width:'15%'},
  		{name:'Logger',field:'logger', width:'10%'},
    	{name:'Level',field:'level', width:'5%'},
    	{name:"stamp",field:'stamp', width:'10%', cellTemplate: ' <div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>&nbsp;{{ row.entity[col.field] * 1000| date:\'yyyy-MM-dd HH:mm:ss\' }}</span></div>'},
    	{name: 'message',field:'message'},
  	];
 
  $http.get('api/log/list' )
    .success(function(data) {
      $scope.logs.data = data;
    });

	
};