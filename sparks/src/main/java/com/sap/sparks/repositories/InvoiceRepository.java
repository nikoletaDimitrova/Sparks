package com.sap.sparks.repositories;

import com.sap.sparks.entity.Invoice;
import com.sap.sparks.entity.Trips;
import com.sap.sparks.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("select t from Invoice t " +
            "join Users u on t.user = u.id " +
            "where u.userName = ?1 ")
    List<Invoice> getInvoiceUsers(String userName);

}
