package com.hohee.dbp.admin.service;

import com.hohee.dbp.api.entity.Company;
import com.hohee.dbp.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository companyRepository;

    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public void save(Company company) {
        companyRepository.save(company);
    }
}
