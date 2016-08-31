angular.module('AktorsatorApp').controller('OrderController', ['$scope', 'OrderService', function ($scope, OrderService) {

    $scope.setOrder = function (order) {
        $scope.order = order;
    };

    var self = this;
    self.order = {orderNr: null, client: '', product: '', convertedPrice: '', transactionDate: ''};
    self.orders = [];

    self.submit = submit;
    self.remove = remove;
    self.reset = reset;


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
            console.log('Saving New Order', self.order);
            createOrder(self.order);
        } else {
            updateOrder(self.order, self.order.orderNr);
            console.log('Order updated with number ', self.order.orderNr);
        }
        reset();
    }

    function remove(orderNr) {
        console.log('Order (number) to be deleted', orderNr);
        if (self.order.orderNr === orderNr) {//clean form if the order to be deleted is shown there.
            reset();
        }
        deleteOrder(orderNr);
    }


    function reset() {
        self.order = {orderNr: null, client: '', product: '', convertedPrice: '', transactionDate: ''};
        $scope.orderForm.$setPristine(); //reset Form
    }

}]);