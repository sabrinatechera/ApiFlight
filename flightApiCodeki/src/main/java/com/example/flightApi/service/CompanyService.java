package com.example.flightApi.service;

import com.example.flightApi.exception.ResourceNotFoundException;
import com.example.flightApi.model.Company;
import com.example.flightApi.model.Flight;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Company createCompany(Company company);

    List<Company> getAllCompanies();

    Optional<Company> findById(Long id);

    Company updateCompany(Long id, Company updatedCompany) throws ResourceNotFoundException;

    void deleteCompany(Long id) throws ResourceNotFoundException;
}
