var logsController = function($scope, $http) {


	$scope.logs = {};
 
	$scope.logs = {
			enableFiltering: true,		
			paginationPageSizes: [25, 50, 75],
		    paginationPageSize: 25
	};
	
  	$scope.logs.columnDefs = [
  		{name:'Application',field:'application', width:'15%',cellTemplate: ' <div class="ngCellText" ng-class="col.colIndex()">&nbsp;<a href="#/application/{{ row.entity[col.field].application_id }}">{{ row.entity[col.field].applicationName }}</a><span ng-cell-text></span></div>'},
  		{name:'Logger',field:'logger', width:'10%'},
    	{name:'Level',field:'level', width:'5%'},
    	{name:"stamp",field:'stamp', width:'10%', cellTemplate: ' <div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>&nbsp;{{ row.entity[col.field] * 1000| date:\'yyyy-MM-dd HH:mm:ss\' }}</span></div>'},
    	{name: 'message',field:'message'},
  	];
 
  $http.get('api/log/list' )
    .success(function(data) {
      $scope.logs.data = data;
      
      
		
      $http.get('api/application/list' )
         .success(function(data) {
           $scope.applications = data;
           
           for (var i = 0; i < $scope.logs.data.length; i++) {      	    
        	   for (var j = 0; j < data.length; j++) {  	    
            	   if($scope.logs.data[i].application.application_id == data[j].application_id){
            		   $scope.logs.data[i].application.applicationName = data[j].applicationName;
            	   }
               }
           }
           
      });
      
    
      
    });

	
};