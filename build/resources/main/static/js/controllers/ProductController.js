angular.module('AktorsatorApp').controller('ProductController', ['$scope', 'ProductService', function ($scope, ProductService) {
    var self = this;
    self.product = {barcode: null, name: '', basePrice: '', description: '', releaseDate: ''};
    self.products = [];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;


    fetchAllProducts();

    function fetchAllProducts() {
        ProductService.fetchAllProducts()
            .then(
                function (d) {
                    self.products = d;
                },
                function (errResponse) {
                    console.error('Error while fetching Products');
                }
            );
    }

    function createProduct(product) {
        ProductService.createProduct(product)
            .then(
                fetchAllProducts,
                function (errResponse) {
                    console.error('Error while creating Product');
                }
            );
    }

    function updateProduct(product, barcode) {
        ProductService.updateProduct(product, barcode)
            .then(
                fetchAllProducts,
                function (errResponse) {
                    console.error('Error while updating Product');
                }
            );
    }

    function deleteProduct(barcode) {
        ProductService.deleteProduct(barcode)
            .then(
                fetchAllProducts,
                function (errResponse) {
                    console.error('Error while deleting Product');
                }
            );
    }

    function submit() {
        if (self.product.barcode === null) {
            console.log('Trying to save new product...', self.product);
            createProduct(self.product);
        } else {
            updateProduct(self.product, self.product.barcode);
            console.log('Product updated with barcode ', self.product.barcode);
        }
        reset();
    }

    function edit(barcode) {
        console.log('barcode to be edited', barcode);
        for (var i = 0; i < self.products.length; i++) {
            if (self.products[i].barcode === barcode) {
                self.product = angular.copy(self.products[i]);
                break;
            }
        }
    }

    function remove(barcode) {
        console.log('barcode to be deleted', barcode);
        if (self.product.barcode === barcode) {
            reset();
        }
        deleteProduct(barcode);
    }


    function reset() {
        self.product = {barcode: null, name: '', basePrice: '', description: '', releaseDate: ''};
        $scope.productForm.$setPristine();
    }

}]);