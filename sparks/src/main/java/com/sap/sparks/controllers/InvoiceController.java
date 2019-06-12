package com.sap.sparks.controllers;

import com.sap.sparks.entity.Invoice;
import com.sap.sparks.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(path = "/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/findAllInvoices")
    public ResponseEntity<List<Invoice>> findAll() {
        List<Invoice> allInvoices =  invoiceService.findAll();
        return new ResponseEntity<>(allInvoices, HttpStatus.OK);
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable int id) {

        Invoice invoice = invoiceService.findById(id);

        if (invoice == null) {
            throw new RuntimeException("Invoice id not found - " + id);
        }

        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }


    @PostMapping("/addInvoices")
    public ResponseEntity<Invoice> addInvoice(@RequestBody Invoice invoice) {
        if(invoice == null){
            //exception
        }
        invoiceService.save(invoice);
       return new ResponseEntity<>(invoice, HttpStatus.OK);
    }


    @PutMapping("/updateInvoices/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable("id") Integer id, @RequestBody Invoice invoice) {
        Invoice currentInvoice = invoiceService.findById(id);
        if(currentInvoice != null){
            throw  new RuntimeException("Invoice doesn't exist with id: " + id);
        }
        invoice.setId(invoice.getId());
        invoiceService.save(invoice);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }


    @DeleteMapping("/deleteInvoice/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("id") int id) {

        Invoice tempInvoice = invoiceService.findById(id);

        if (tempInvoice == null) {
            throw new RuntimeException("Invoice id not found - " + id);
        }

        invoiceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/monthlyInvoice")
    public ResponseEntity<Invoice> createMonthlyInvoice(@RequestParam("month") String month, @RequestParam("year") String year, @RequestParam("userName") String userName){
       Integer monthInt = Integer.parseInt(month);
       Integer yearInt = Integer.parseInt(year);
       Invoice invoice = invoiceService.createMonthlyInvoice(monthInt, yearInt, userName);

       return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @PostMapping("/dailyInvoice")
    public ResponseEntity<Invoice> createDailyInvoice(@RequestParam("day") String day, @RequestParam("month") String month, @RequestParam("year") String year, @RequestParam("userName") String userName){
        Integer dayInt = Integer.parseInt(day);
        Integer monthInt = Integer.parseInt(month);
        Integer yearInt = Integer.parseInt(year);
        Invoice invoice = invoiceService.createDailyInvoice(dayInt, monthInt, yearInt, userName);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @GetMapping("/getAllInvoiceForUser/{userName}")
    public ResponseEntity<List<Invoice>> getAllInvoicesPerUser(@PathVariable("userName") String userName){
        List<Invoice> invoices = invoiceService.getInvoiceUsers(userName);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }
}
