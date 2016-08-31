angular.module('AktorsatorApp').controller('ClientController', ['$scope', 'ClientService', function ($scope, ClientService) {
    var self = this;
    self.client = {securityNumber: null, firstName: '', lastName: '', phoneNumber: '', country: '', address: '', represent: ''};
    self.clients = [];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;


    fetchAllClients();

    function fetchAllClients() {
        ClientService.fetchAllClients()
            .then(
                function (d) {
                    self.clients = d;
                },
                function (errResponse) {
                    console.error('Error while fetching Clients');
                }
            );
    }

    function createClient(client) {
        ClientService.createClient(client)
            .then(
                fetchAllClients,
                function (errResponse) {
                    console.error('Error while creating Client');
                }
            );
    }

    function updateClient(client, securityNumber) {
        ClientService.updateClient(client, securityNumber)
            .then(
                fetchAllClients,
                function (errResponse) {
                    console.error('Error while updating Client');
                }
            );
    }

    function deleteClient(securityNumber) {
        ClientService.deleteClient(securityNumber)
            .then(
                fetchAllClients,
                function (errResponse) {
                    console.error('Error while deleting Client');
                }
            );
    }

    function submit() {
        if (self.client.securityNumber === null) {
            console.log('Trying to save new client...', self.client);
            createClient(self.client);
        } else {
            updateClient(self.client, self.client.securityNumber);
            console.log('Client updated with securityNumber ', self.client.securityNumber);
        }
        reset();
    }

    function edit(securityNumber) {
        console.log('securityNumber to be edited', securityNumber);
        for (var i = 0; i < self.clients.length; i++) {
            if (self.clients[i].securityNumber === securityNumber) {
                self.client = angular.copy(self.clients[i]);
                break;
            }
        }
    }

    function remove(securityNumber) {
        console.log('securityNumber to be deleted', securityNumber);
        if (self.client.securityNumber === securityNumber) {
            reset();
        }
        deleteClient(securityNumber);
    }


    function reset() {
        self.client = {securityNumber: null, firstName: '', lastName: '', phoneNumber: '', country: '', address: ''};
        $scope.clientForm.$setPristine();
    }

}]);