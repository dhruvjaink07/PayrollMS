package com.example.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pms.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
