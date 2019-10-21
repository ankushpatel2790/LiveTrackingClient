/*******************************************************************************
 * Copyright (c) 2013-2015 Sierra Wireless and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *     Sierra Wireless - initial API and implementation
 *******************************************************************************/

angular.module('securityControllers', [])

.controller('SecurityCtrl', [
    '$scope',
    '$http',
    'dialog',
    '$location',
    function SecurityCtrl($scope, $http, dialog, $location) {

        // update navbar
        angular.element("#navbar").children().removeClass('active');
        angular.element("#security-navlink").addClass('active');

        // get the list of security info by end-point
        $http.get('api/security/clients'). error(function(data, status, headers, config){
            $scope.error = "Unable to get the clients security info list: " + status + " " + data  
            console.error($scope.error)
        }).success(function(data, status, headers, config) {
            $scope.securityInfos = {}
            for (var i = 0; i < data.length; i++) {
                $scope.securityInfos[data[i].endpoint] = data[i];
            }
        });

        $http.get('api/security/server'). error(function(data, status, headers, config){
            $scope.error = "Unable to get the server security info list: " + status + " " + data  
            console.error($scope.error)
        }).success(function(data, status, headers, config) {
               $scope.serverSecurityInfo = data;
        });

        $scope.remove = function(endpoint) {
            $http({method: 'DELETE', url: "api/security/clients/" + endpoint, headers:{'Content-Type': 'text/plain'}})
            .success(function(data, status, headers, config) {
                delete $scope.securityInfos[endpoint];
           }).error(function(data, status, headers, config) {
               errormessage = "Unable to remove security info for endpoint " + endpoint + ": " + status + " - " + data;
               dialog.open(errormessage);
               console.error(errormessage);
            });
        }

        $scope.save = function() {
            $scope.$broadcast('show-errors-check-validity');
            $scope.onSaveClick=true;
            if ($scope.form.$valid) {
                if($scope.securityMode == "psk") {
                    var security = {endpoint: $scope.endpoint,  identity : $scope.pskIdentity , preSharedKey : $scope.pskValue, bootstrap: $scope.isBootstap, ipAddress: $scope.bootStrapServerIp};
                }
                else if($scope.securityMode == "nosec") {
                    var security = {endpoint: $scope.endpoint, bootstrap: $scope.isBootstap, ipAddress: $scope.bootStrapServerIp};
                } else {
                    var security = {endpoint: $scope.endpoint, rpk : { x : $scope.rpkXValue , y : $scope.rpkYValue, params : $scope.rpkParamsValue || $scope.defaultParams}};
                }
                if(security) {
                    $http({method: 'POST', url: "api/clients/", data: security, headers:{'Content-Type': 'text/plain'}})
                    .success(function(data, status, headers, config) {
						console.log(data)
						$scope.onSaveClick=false;
						if(data == 'SUCCESS'){
							$('#newSecurityModal').modal('hide');
                        $location.path('/clients');
                        $scope.securityInfos[$scope.endpoint] = security;
						} else{
							errormessage = "Unable to add security info for endpoint " + $scope.endpoint + ": " + status + " - " + data;
							dialog.open(errormessage);
						}
						
                        
                       
                    }).error(function(data, status, headers, config) {
                        errormessage = "Unable to add security info for endpoint " + $scope.endpoint + ": " + status + " - " + data;
                        dialog.open(errormessage);
                        console.error(errormessage)
                    });
                }
            }
        }

        $scope.showModal = function() {
            $('#newSecurityModal').modal('show');
            $scope.$broadcast('show-errors-reset');
            $scope.endpoint = ''
            $scope.securityMode = 'psk'
            $scope.pskIdentity = ''
            $scope.pskValue = ''
            $scope.rpkXValue = ''
            $scope.rpkYValue = ''
            $scope.defaultParams = 'secp256r1'
       }
}])


/* directive to toggle error class on input fields */
.directive('showErrors', function($timeout) {
    return {
        restrict : 'A',
        require : '^form',
        link : function(scope, el, attrs, formCtrl) {
            // find the text box element, which has the 'name' attribute
            var inputEl = el[0].querySelector("[name]");
            // convert the native text box element to an angular element
            var inputNgEl = angular.element(inputEl);
            // get the name on the text box
            var inputName = inputNgEl.attr('name');

            // only apply the has-error class after the user leaves the text box
            inputNgEl.bind('blur', function() {
                el.toggleClass('has-error', formCtrl[inputName].$invalid);
            });

            scope.$on('show-errors-check-validity', function() {
                el.toggleClass('has-error', formCtrl[inputName].$invalid);
            });

            scope.$on('show-errors-reset', function() {
                $timeout(function() {
                    el.removeClass('has-error');
                }, 0, false);
            });
        }
    }
})


lwClientControllers.controller('DeviceDetailCtrl', [
    '$scope',
    '$location',
    '$routeParams',
    '$http',
    'lwResources',
    '$filter',
    function($scope, $location, $routeParams, $http, lwResources,$filter) {
        // update navbar
        angular.element("#navbar").children().removeClass('active');
        angular.element("#security-navlink").addClass('active');

        // free resource when controller is destroyed
        $scope.$on('$destroy', function(){
            if ($scope.eventsource){
                $scope.eventsource.close()
            }
        });

        // default format
        $scope.settings={};
        $scope.settings.multi = {format:"TLV"};
        $scope.settings.single = {format:"TLV"};

        $scope.clientId = $routeParams.macaddress;

        // get client details
        
        $http.get('clientdetails.json')
        .error(function(data, status, headers, config) {
            $scope.error = "Unable get client " + $routeParams.clientId+" : "+ status + " " + data;  
            console.error($scope.error);
        })
        .success(function(data, status, headers, config) {
            $scope.client = data;

            // update resource tree with client details
            lwResources.buildResourceTree($scope.client.rootPath, $scope.client.objectLinks, function (objects){
                $scope.objects = objects;
            });

            // listen for clients registration/deregistration/observe
            $scope.eventsource = new EventSource('event?ep=' + $routeParams.clientId);

            var registerCallback = function(msg) {
                $scope.$apply(function() {
                    $scope.deregistered = false;
                    $scope.client = JSON.parse(msg.data);
                    lwResources.buildResourceTree($scope.client.rootPath, $scope.client.objectLinks, function (objects){
                        $scope.objects = objects;
                    });
                });
            }
            $scope.eventsource.addEventListener('REGISTRATION', registerCallback, false);

            var deregisterCallback = function(msg) {
                $scope.$apply(function() {
                    $scope.deregistered = true;
                    $scope.client = null;
                });
            }
            $scope.eventsource.addEventListener('DEREGISTRATION', deregisterCallback, false);

            var notificationCallback = function(msg) {
                $scope.$apply(function() {
                    var content = JSON.parse(msg.data);
                    var resource = lwResources.findResource($scope.objects, content.res);
                    if (resource) {
                        if("value" in content.val) {
                            // single value
                            resource.value = content.val.value
                        }
                        else if("values" in content.val) {
                            // multiple instances
                            var tab = new Array();
                            for (var i in content.val.values) {
                                tab.push(i+"="+content.val.values[i])
                            }
                            resource.value = tab.join(", ");
                        }
                        resource.valuesupposed = false;
                        resource.observed = true;

                        var formattedDate = $filter('date')(new Date(), 'HH:mm:ss.sss');
                        resource.tooltip = formattedDate;
                    } else {
                        // instance?
                        var instance = lwResources.findInstance($scope.objects, content.res);
                        if (instance) {
                            instance.observed = true;
                            for(var i in content.val.resources) {
                                var tlvresource = content.val.resources[i];
                                resource = lwResources.addResource(instance.parent, instance, tlvresource.id, null)
                                if("value" in tlvresource) {
                                    // single value
                                    resource.value = tlvresource.value
                                } else if("values" in tlvresource) {
                                    // multiple instances
                                    var tab = new Array();
                                    for (var j in tlvresource.values) {
                                        tab.push(j+"="+tlvresource.values[j])
                                    }
                                    resource.value = tab.join(", ");
                                }
                                resource.valuesupposed = false;
                                resource.tooltip = formattedDate;
                            }
                        }
                    } // TODO object level
                });
            };
            $scope.eventsource.addEventListener('NOTIFICATION', notificationCallback, false);

            $scope.coaplogs = [];
            var coapLogCallback = function(msg) {
                $scope.$apply(function() {
                    var log = JSON.parse(msg.data);
                    log.date = $filter('date')(new Date(log.timestamp), 'HH:mm:ss.sss');
                    if (256 < $scope.coaplogs.length) $scope.coaplogs.shift();
                    $scope.coaplogs.push(log);
                });
            };
            $scope.eventsource.addEventListener('COAPLOG', coapLogCallback, false);

            // coap logs hidden by default
            $scope.coapLogsCollapsed = true;
            $scope.toggleCoapLogs = function() {
                $scope.coapLogsCollapsed = !$scope.coapLogsCollapsed;
            };
        });
    }]);
