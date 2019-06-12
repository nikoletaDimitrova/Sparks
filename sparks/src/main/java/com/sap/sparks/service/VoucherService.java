package com.sap.sparks.service;

import com.sap.sparks.entity.Voucher;

import java.util.List;

public interface VoucherService {

    List<Voucher> findAll();

    Voucher findById(int id);

    void save(Voucher user);

    void deleteById(int id);


}


