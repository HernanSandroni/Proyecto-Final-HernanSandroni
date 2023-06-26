package com.coderhouse.ProyectoFinalHernanSandroni.model;


import java.util.List;

public class InvoiceWithDetailsDTO {

    private final int client_id;
    private int invoice_id;
    private String created_at;
    private double total;
    private List<InvoiceDetailDTO> details;

    public InvoiceWithDetailsDTO(int client_id, int invoice_id, String created_at, double total, List<InvoiceDetailDTO> details) {
        this.client_id = client_id;
        this.invoice_id = invoice_id;
        this.created_at = created_at;
        this.total = total;
        this.details = details;
    }

    public List<InvoiceDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<InvoiceDetailDTO> details) {
        this.details = details;
    }
    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public double getTotal() {
        return total;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "client_id=" + client_id +
                ", invoice_id=" + invoice_id +
                ", created_at='" + created_at + '\'' +
                ", total=" + total +
                ", details=" + details +
                '}';
    }
}