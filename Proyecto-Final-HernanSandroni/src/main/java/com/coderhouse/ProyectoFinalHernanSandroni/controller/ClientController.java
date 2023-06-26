package com.coderhouse.ProyectoFinalHernanSandroni.controller;

import com.coderhouse.ProyectoFinalHernanSandroni.middleware.ResponseHandler;
import com.coderhouse.ProyectoFinalHernanSandroni.model.Client;
import com.coderhouse.ProyectoFinalHernanSandroni.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "api/v1/client")

public class ClientController {

    @Autowired
    private ClientService clientService;

    // creo al cliente
    @PostMapping
    public ResponseEntity<Object> postClient(@RequestBody Client client) {
        try {
            this.isValid(client); // metodo de validacion
            System.out.println(client);
            Client clientSaved = clientService.postClient(client);
            return ResponseHandler.generateResponse(
                    "Client stored successfully",
                    HttpStatus.OK,
                    clientSaved
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    //Creo un Get para buscar a un cliente por id
    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getClient(@PathVariable() int id) {
        try {
            System.out.println(id);
            Client clientFound = clientService.getClient(id);
            return ResponseHandler.generateResponse(
                    "Client retrieved successfully",
                    HttpStatus.OK,
                    clientFound
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    //creo un Put para actualizar un cliente
    @PutMapping(path = "{client_id}")
    public ResponseEntity<Object> putClient(
            @PathVariable("client_id") int id,
            @RequestBody Client client
    ) {
        try {
            System.out.println(client);
            System.out.println(id);
            clientService.updateClient(client, id);
            return ResponseHandler.generateResponse(
                    "Data updated successfully",
                    HttpStatus.OK,
                    id
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    // creo un Delete para borrar a un cliente
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable() int id) {
        try {
            System.out.println(id);
            clientService.deleteClient(id);
            return ResponseHandler.generateResponse(
                    "Client deleted successfully",
                    HttpStatus.OK,
                    id
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    // creo un metodo para validar si los campos vienen vacios
    private void isValid(Client client) throws Exception {
        if (Objects.isNull(client.getName()) && client.getName().isBlank()) {
            throw new Exception("Client name must not be empty");
        } else if (Objects.isNull(client.getLastname()) && client.getLastname().isBlank()) {
            throw new Exception("Client last name must not be empty");
        } else if (Objects.isNull(client.getDocnumber())) {
            throw new Exception("Client docnumber must not be empty");
        }
    }
}
