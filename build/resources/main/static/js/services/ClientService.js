angular.module('AktorsatorApp').factory('ClientService', ['$http', '$q', function ($http, $q) {

    var REST_SERVICE_URI = 'http://localhost:8080/client/';

    var factory = {
        fetchAllClients: fetchAllClients,
        createClient: createClient,
        updateClient: updateClient,
        deleteClient: deleteClient
    };

    return factory;

    function fetchAllClients() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while fetching Clients');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function createClient(client) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, client)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while creating Client');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }


    function updateClient(client, securityNumber) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI + securityNumber, client)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while updating Client');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteClient(securityNumber) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + securityNumber)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while deleting Client');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);