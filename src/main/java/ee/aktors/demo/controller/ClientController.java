package ee.aktors.demo.controller;

import java.util.List;

import ee.aktors.demo.model.Client;
import ee.aktors.demo.service.ClientService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    private final static Logger logger = Logger.getLogger(ClientController.class);


    //-------------------Retrieve All Clients--------------------------------------------------------

    @RequestMapping(value = "/client/", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> listAllClients() {
        List<Client> clients = clientService.findAllClients();
        if (clients.isEmpty()) {
            return new ResponseEntity<List<Client>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }


    //-------------------Retrieve Single Client--------------------------------------------------------

    @RequestMapping(value = "/client/{securityNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> getClient(@PathVariable("securityNumber") long securityNumber) {
        logger.info("Fetching Client with securityNumber " + securityNumber);
        Client client = clientService.findBySecurityNumber(securityNumber);
        if (client == null) {
            logger.error("Client with securityNumber " + securityNumber + " not found");
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }


    //-------------------Create a Client--------------------------------------------------------

    @RequestMapping(value = "/client/", method = RequestMethod.POST)
    public ResponseEntity<Void> createClient(@RequestBody Client client, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Client " + client.getClientName());

        if (clientService.isClientExist(client)) {
            logger.error("A Client with name " + client.getClientName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        clientService.saveClient(client);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/client/{securityNumber}").buildAndExpand(client.getSecurityNumber()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Client --------------------------------------------------------

    @RequestMapping(value = "/client/{securityNumber}", method = RequestMethod.PUT)
    public ResponseEntity<Client> updateClient(@PathVariable("securityNumber") long securityNumber, @RequestBody Client client) {
        logger.info("Updating Client " + securityNumber);

        Client currentClient = clientService.findBySecurityNumber(securityNumber);

        if (currentClient == null) {
            logger.error("Client with securityNumber " + securityNumber + " not found");
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }

        currentClient.setFirstName(client.getFirstName());
        currentClient.setLastName(client.getLastName());
        currentClient.setPhoneNumber(client.getPhoneNumber());
        currentClient.setCountry(client.getCountry());
        currentClient.setAddress(client.getAddress());

        clientService.updateClient(currentClient);
        return new ResponseEntity<Client>(currentClient, HttpStatus.OK);
    }


    //------------------- Delete a Client --------------------------------------------------------

    @RequestMapping(value = "/client/{securityNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<Client> deleteClient(@PathVariable("securityNumber") long securityNumber) {
        logger.info("Fetching & Deleting Client with securityNumber " + securityNumber);

        Client client = clientService.findBySecurityNumber(securityNumber);
        if (client == null) {
            logger.error("Unable to delete. Client with securityNumber " + securityNumber + " not found");
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }

        clientService.deleteClientBySecurityNumber(securityNumber);
        return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
    }

}
