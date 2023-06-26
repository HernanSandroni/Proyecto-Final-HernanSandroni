package com.coderhouse.ProyectoFinalHernanSandroni.service;


import com.coderhouse.ProyectoFinalHernanSandroni.model.*;
import com.coderhouse.ProyectoFinalHernanSandroni.repository.ClientRepository;
import com.coderhouse.ProyectoFinalHernanSandroni.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private InvoiceDetailService invoiceDetailService;
    @Autowired
    private ClientService clientService;
    public InvoiceDTO postInvoice (RequestInvoice requestInvoice) throws Exception {

        // validacion de existencia de Cliente y Producto
        Client clientExist = clientService.getClient(requestInvoice.getClient_id());
        List<Product> productList = productService.getProductsById(requestInvoice.getProduct_list());

        // metodo de validacion
        this.updateStock(requestInvoice.getProduct_list());

        //Calculo del total
        double total = 0;
        int i = 0;
        for (Product product:
                productList) {
            total += product.getPrice() * requestInvoice.getProduct_list().get(i).getQuantity();
            i++;
        }

        //Instanciamos un objeto invoice
        Invoice invoiceCreated = new Invoice();

        //Setteamos la fecha del invoice
        invoiceCreated.setCreated_at(new Date().toString());

        //Setteamos al cliente dentro del invoice
        invoiceCreated.setClient(clientExist);

        invoiceCreated.setTotal(total);
        //Guardamos el invoice antes de guardar el detalle
        invoiceCreated = invoiceRepository.save(invoiceCreated);

        //Settamos los invoice_details y los guardamos
        i = 0;
        for (Product productForDetail:
                productList) {
            InvoiceDetail newInvoice = new InvoiceDetail();
            newInvoice.setPrice(productForDetail.getPrice());
            newInvoice.setInvoice(invoiceCreated);
            newInvoice.setProduct(productForDetail);
            newInvoice.setQuantity(requestInvoice.getProduct_list().get(i).getQuantity());
            invoiceDetailService.saveInvoiceDetail(newInvoice);
            i++;
        }

        // retornamos el DTO
        return new InvoiceDTO(
                invoiceCreated.getId(),
                invoiceCreated.getCreated_at(),
                invoiceCreated.getTotal()
        );
    }

    // creo un metodo para validar que la quantity sea menor al stock del product y
    // que reste la cantidad pedida en la invoice al stock
    private void updateStock(List<RequestProductDetail> product_list) throws Exception {
        for(RequestProductDetail prd : product_list){
            Product product = productService.getProduct(prd.getProductId());
            if(product.getStock() >= prd.getQuantity()){
                product.substractStock(prd.getQuantity());
                productService.updateProduct(product, product.getId());
            } else {
                throw new Exception(String.format("Product with code %s does not have enough stock", prd.getProductId()));
            }
        }
    }

    // creo la logica para los endpoints restantes hechos en el controller
    public List<InvoiceDTO> getInvoicesByClientId (int clientId) throws Exception {
        System.out.println(clientId);
        List<InvoiceDTO> clientFound = invoiceRepository.getInvoicesByClientById(clientId);
        if (clientFound.isEmpty()){
            throw new Exception("invoices of Client: " + clientId + ", not found");
        }
        return clientFound;
    }

    public InvoiceWithDetailsDTO getInvoiceById (int invoice_id) throws Exception {
        Optional<Invoice> invoiceFound = invoiceRepository.findById(invoice_id);
        if (invoiceFound.isEmpty()) {
            throw new Exception("Invoice not found");
        }

        List<InvoiceDetailDTO> invoice_details = invoiceDetailService.getInvoiceDetailsByInvoiceId(invoice_id);
        return new InvoiceWithDetailsDTO(
                invoiceFound.get().getClient().getId(),
                invoiceFound.get().getId(),
                invoiceFound.get().getCreated_at(),
                invoiceFound.get().getTotal(),
                invoice_details
        );
    }
}