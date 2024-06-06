package com.hohee.dbp.api;

import com.hohee.dbp.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiRepository extends JpaRepository<Company, Long> {
    // 업종명 부분 일치 검색
    List<Company> findByTpbizContaining(String tpbiz);

    // 상호명 부분 일치 검색
    List<Company> findByConmContaining(String conm);
}
