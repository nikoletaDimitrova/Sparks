package com.sap.sparks.service;

import com.sap.sparks.entity.Invoice;
import com.sap.sparks.entity.Trips;

import java.util.List;

public interface    InvoiceService {

    List<Invoice> findAll();

    Invoice findById(int id);

    void save(Invoice invoice);

    void deleteById(int id);

    Invoice createMonthlyInvoice(Integer month, Integer year, String userName);

    Invoice createDailyInvoice(Integer day, Integer month, Integer year, String userName);

    List<Invoice> getInvoiceUsers(String userName);
}


