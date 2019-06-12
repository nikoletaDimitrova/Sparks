package com.sap.sparks.service.serviceImpl;

import com.sap.sparks.entity.Invoice;
import com.sap.sparks.entity.Trips;
import com.sap.sparks.entity.Users;
import com.sap.sparks.repositories.InvoiceRepository;
import com.sap.sparks.service.InvoiceService;
import com.sap.sparks.service.TripsService;
import com.sap.sparks.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class InvoiceServiceImpl implements InvoiceService {


    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private TripsService tripsService;

    @Autowired
    private UsersService usersService;

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice findById(int id) {
        Optional<Invoice> result = invoiceRepository.findById(id);

        Invoice invoice = null;

        if (result.isPresent()) {
            invoice = result.get();
        } else {
            throw new RuntimeException("Did not find invoice id - " + id);
        }

        return invoice;
    }

    @Override
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public void deleteById(int theId) {
        invoiceRepository.deleteById(theId);
    }

    @Override
    public Invoice createMonthlyInvoice(Integer month, Integer year, String userName) {
        List<Trips> trips = tripsService.findTripsPerMonth(month, year, userName);
        Invoice invoice = savingDetails(trips, userName);
        invoice.setTypeInvoice(Invoice.TypeInvoice.monthly);
        save(invoice);
        return invoice;
    }

    @Override
    public Invoice createDailyInvoice(Integer day, Integer month, Integer year, String userName) {
        List<Trips> trips = tripsService.findTripsPerDay(day, month, year, userName);
        Invoice invoice = savingDetails(trips, userName);
        invoice.setTypeInvoice(Invoice.TypeInvoice.daily);
        save(invoice);
        return invoice;

    }

    private Invoice savingDetails(List<Trips> trips, String userName){
        Invoice invoice = new Invoice();
        invoice.setTrips(trips);
        double totalMoney = 0;
        for(int i=0; i < trips.size(); i++){
            totalMoney+= trips.get(i).getPrice();
        }
        invoice.setTotalCost(totalMoney);
        invoice.setTypeInvoice(Invoice.TypeInvoice.daily);
        Users user = usersService.findByUserName(userName);
        invoice.setUser(user);
        return invoice;
    }

    @Override
    public List<Invoice> getInvoiceUsers(String userName) {
        return invoiceRepository.getInvoiceUsers(userName);
    }
}


