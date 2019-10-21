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

'use strict';

/* App Module */

var leshanApp = angular.module('leshanApp',[ 
        'ngRoute',
        'clientControllers',
        'objectDirectives',
        'instanceDirectives',
        'resourceDirectives',
        'resourceFormDirectives',
        'lwResourcesServices',
        'securityControllers',
        'uiDialogServices',
        'modalInstanceControllers',
        'ui.bootstrap',
        'app.filters'
]);

leshanApp.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
    $routeProvider.
        when('/clients',           { templateUrl : 'partials/client-list.html',   controller : 'ClientListCtrl' }).
        when('/clients/:clientId', { templateUrl : 'partials/client-detail.html', controller : 'ClientDetailCtrl' }).
        when('/security',          { templateUrl : 'partials/security-list.html', controller : 'SecurityCtrl' }).
        when('/device/:macaddress', { templateUrl : 'partials/client-detail.html', controller : 'DeviceDetailCtrl' }).
        otherwise({ redirectTo : '/clients' });
}]);


angular.module('app.filters', []).filter('titlecase', function() {
    return function (input) {
        var smallWords = /^(a|an|and|as|at|but|by|en|for|if|in|nor|of|on|or|per|the|to|vs?\.?|via)$/i;

        input = input.toLowerCase();
        return input.replace(/[A-Za-z0-9\u00C0-\u00FF]+[^\s-]*/g, function(match, index, title) {
            if (index > 0 && index + match.length !== title.length &&
                match.search(smallWords) > -1 && title.charAt(index - 2) !== ":" &&
                (title.charAt(index + match.length) !== '-' || title.charAt(index - 1) === '-') &&
                title.charAt(index - 1).search(/[^\s-]/) < 0) {
                return match.toLowerCase();
            }

            if (match.substr(1).search(/[A-Z]|\../) > -1) {
                return match;
            }

            return match.charAt(0).toUpperCase() + match.substr(1);
        });
    }
});
