package com.coderhouse.ProyectoFinalHernanSandroni.controller;


import com.coderhouse.ProyectoFinalHernanSandroni.middleware.ResponseHandler;
import com.coderhouse.ProyectoFinalHernanSandroni.model.InvoiceDTO;
import com.coderhouse.ProyectoFinalHernanSandroni.model.InvoiceWithDetailsDTO;
import com.coderhouse.ProyectoFinalHernanSandroni.model.RequestInvoice;
import com.coderhouse.ProyectoFinalHernanSandroni.service.ClientService;
import com.coderhouse.ProyectoFinalHernanSandroni.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    ClientService clientService;

    // creo una invoice
    @PostMapping
    public ResponseEntity<Object> postInvoice (@RequestBody RequestInvoice reqInvoice) {
        try {
            System.out.println(reqInvoice);
            InvoiceDTO data = invoiceService.postInvoice(reqInvoice);
            return ResponseHandler.generateResponse(
                    "Invoice created successful",
                    HttpStatus.OK,
                    data
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    // creo un get para buscar a un invoice por id
    @GetMapping(path = "{invoice_id}")
    public ResponseEntity<Object> getInvoiceById (@PathVariable int invoice_id) {
        try {
            System.out.println(invoice_id);
            InvoiceWithDetailsDTO data = invoiceService.getInvoiceById(invoice_id);
            return ResponseHandler.generateResponse(
                    "Get Invoice by Id successful",
                    HttpStatus.OK,
                    data
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    // creo un get para buscar las invoices de un client por id
    @GetMapping(path = "/getInvoicesByClientId/{clientId}")
    public ResponseEntity<Object> getInvoicesByClientId (@PathVariable int clientId){
        try {
            List<InvoiceDTO> data = invoiceService.getInvoicesByClientId(clientId);
            return ResponseHandler.generateResponse(
                    "Get Invoices by Client id successful",
                    HttpStatus.OK,
                    data
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }
}