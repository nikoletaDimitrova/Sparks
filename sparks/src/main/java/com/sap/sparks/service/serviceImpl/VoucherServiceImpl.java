package com.sap.sparks.service.serviceImpl;

import com.sap.sparks.entity.Users;
import com.sap.sparks.entity.Voucher;
import com.sap.sparks.repositories.UsersRepository;
import com.sap.sparks.repositories.VoucherRepository;
import com.sap.sparks.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.List;
import java.util.Optional;


@Service
public class VoucherServiceImpl implements VoucherService {


    private VoucherRepository voucherRepository;

    @Autowired
    GenericWebApplicationContext mediator;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findById(int id) {
        Optional<Voucher> result = voucherRepository.findById(id);

        Voucher voucher = null;

        if (result.isPresent()) {
            voucher = result.get();
        } else {
            throw new RuntimeException("Did not find voucher id - " + id);
        }

        return voucher;
    }

    @Override
    public void save(Voucher voucher) {
        Users staffUser = (Users)mediator.getBean("usertemp");
        voucher.setUser(staffUser);
        Users user = usersRepository.findByUserName(voucher.getUser().getUserName());
        voucher.setUser(user);
        voucherRepository.save(voucher);
        List<Voucher> userList = user.getVouchers();
        List<Voucher> userStaffList = staffUser.getStaffVouchers();
        userList.add(voucher);
        userStaffList.add(voucher);
        user.setVouchers(userList);
        staffUser.setStaffVouchers(userStaffList);
        usersRepository.save(user);
        usersRepository.save(staffUser);

    }

    @Override
    public void deleteById(int theId) {
        voucherRepository.deleteById(theId);
    }

}


