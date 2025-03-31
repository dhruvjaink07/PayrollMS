package com.example.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pms.entity.Company;
import com.example.pms.repository.CompanyRepository;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company getCompanyById(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public Company updateCompany(Integer id, Company updatedCompany) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            company.setName(updatedCompany.getName());
            company.setAddress(updatedCompany.getAddress());
            company.setContact(updatedCompany.getContact());
            company.setEmail(updatedCompany.getEmail());
            return companyRepository.save(company);
        }
        return null;
    }

    public void deleteCompany(Integer id) {
        companyRepository.deleteById(id);
    }
}
