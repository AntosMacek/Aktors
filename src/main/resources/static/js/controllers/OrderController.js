angular.module('AktorsatorApp').controller('OrderController', ['$scope', 'OrderService', function ($scope, OrderService) {

    $scope.setOrder = function (order) {
        $scope.order = order;
    };

    var self = this;
    self.order = {orderNr: null, client: {}, product: {}, convertedPrice: '', transactionDate: ''};
    self.orders = [];

    self.submit = submit;
    self.remove = remove;
    self.reset = reset;
    self.updateOrders = updateOrders;


    fetchAllOrders();

    function fetchAllOrders() {
        OrderService.fetchAllOrders()
            .then(
                function (d) {
                    self.orders = d;
                },
                function (errResponse) {
                    console.error('Error while fetching Orders');
                }
            );
    }

    function createOrder(order) {
        OrderService.createOrder(order)
            .then(
                fetchAllOrders,
                function (errResponse) {
                    console.error('Error while creating Order');
                }
            );
    }

    function deleteOrder(orderNr) {
        OrderService.deleteOrder(orderNr)
            .then(
                fetchAllOrders,
                function (errResponse) {
                    console.error('Error while deleting Order');
                }
            );
    }

    function submit() {
        if (self.order.orderNr === null) {
            console.log('Trying to save new order...', self.order);
            createOrder(self.order);
        }
        reset();
    }

    function remove(orderNr) {
        console.log('Order (number) to be deleted', orderNr);
        if (self.order.orderNr === orderNr) {
            reset();
        }
        deleteOrder(orderNr);
    }


    function reset() {
        self.order = {orderNr: null, client: {}, product: {}, convertedPrice: '', transactionDate: ''};
        $scope.orderForm.$setPristine();
    }

    function updateOrders() {
        console.log('Orders to be updated');
        OrderService.updateOrders()
            .then(
                fetchAllOrders,
                function (errResponse) {
                    console.error('Error while updating Orders');
                }
            );
    }

}]);