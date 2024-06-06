package com.hohee.dbp.company.service;

import com.hohee.dbp.company.entity.Company;
import com.hohee.dbp.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public void save(Company company) {
        companyRepository.save(company);
    }
}
