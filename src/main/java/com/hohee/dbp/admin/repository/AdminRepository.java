package com.hohee.dbp.admin.repository;

import com.hohee.dbp.api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Company, Long> {
}
