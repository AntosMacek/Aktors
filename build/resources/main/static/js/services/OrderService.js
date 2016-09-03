angular.module('AktorsatorApp').factory('OrderService', ['$http', '$q', function ($http, $q) {

    var REST_SERVICE_URI = 'http://localhost:8080/order/';

    var factory = {
        fetchAllOrders: fetchAllOrders,
        createOrder: createOrder,
        deleteOrder: deleteOrder,
        updateOrders: updateOrders
    };

    return factory;

    function fetchAllOrders() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while fetching Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function createOrder(order) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, order)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while creating Order');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteOrder(orderNr) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + orderNr)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while deleting Order');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function updateOrders() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI + 'update')
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while updating Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);