	// create the module and name it scotchApp
var senpaiApp = angular.module('senpaiApp', ['ngRoute', 'ui.grid', 'ui.grid.pagination']);

senpaiApp.controller("ApplicationController", applicationController);
senpaiApp.controller("NavbarController", navbarController);
senpaiApp.controller("LogsController", logsController);
senpaiApp.controller("AdminController", adminController);

senpaiApp.config(appConfig);

senpaiApp.directive('autoComplete', function($timeout) {
    return function(scope, iElement, iAttrs) {
    	
            iElement.autocomplete({
                source: scope[iAttrs.uiItems],
                select: function() {
                    $timeout(function() {
                      iElement.trigger('input');
                    }, 0);
                }
            });
    };
});

senpaiApp.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 32) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
});