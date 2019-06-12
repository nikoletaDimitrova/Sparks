package com.sap.sparks.controllers;

import com.sap.sparks.entity.Voucher;
import com.sap.sparks.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;


    @GetMapping("/findAllVouchers")
    public ResponseEntity<List<Voucher>> findAll() {
        List<Voucher> vouchers = voucherService.findAll();

        return new ResponseEntity<>(vouchers, HttpStatus.OK);
    }

    @GetMapping("/voucher/{id}")
    public ResponseEntity<Voucher> getVouchers(@PathVariable int id) {

        Voucher voucher = voucherService.findById(id);

        if (voucher == null) {
            throw new RuntimeException("Voucher id not found - " + id);
        }

        return new ResponseEntity<>(voucher, HttpStatus.OK);
    }


    @PostMapping("/addVoucher")
    public ResponseEntity<Voucher> addVoucher(@RequestBody Voucher voucher) {
        if(voucher == null) {
            //exception
        }
        voucherService.save(voucher);
        return new ResponseEntity<>(voucher, HttpStatus.OK);
    }


    @PutMapping("/updateVoucher/{id}")
    public ResponseEntity<Voucher> updateVoucher(@PathVariable("id") Integer id,  @RequestBody Voucher voucher) {
        Voucher existingVoucher = voucherService.findById(id);
        if(existingVoucher == null){
            throw new RuntimeException("Voucher doesn't exist with id: " + id);
        }
        voucher.setId(existingVoucher.getId());
        voucherService.save(voucher);

        return new ResponseEntity<>(voucher, HttpStatus.OK);
    }


    @DeleteMapping("/deleteVoucher/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable("id") int id) {

        Voucher tempVoucher = voucherService.findById(id);

        if (tempVoucher == null) {
            throw new RuntimeException("Voucher id not found - " + id);
        }

        voucherService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}