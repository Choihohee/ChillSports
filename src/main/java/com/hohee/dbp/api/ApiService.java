package com.hohee.dbp.api;

import com.hohee.dbp.company.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    @Autowired
    private ApiRepository apiRepository;

    public void saveAll(List<Company> companyList) {
        apiRepository.saveAll(companyList);
    }
    public List<Company> getAllCompanies() {
        return apiRepository.findAll();
    }

    // 업종을 기준으로 부분 일치 검색하는 서비스 메서드
    public List<Company> searchByTpbiz(String keyword) {
        return apiRepository.findByTpbizContaining(keyword);
    }

    // 상호명을 기준으로 부분 일치 검색하는 서비스 메서드
    public List<Company> searchByConm(String keyword) {
        return apiRepository.findByConmContaining(keyword);
    }
}
