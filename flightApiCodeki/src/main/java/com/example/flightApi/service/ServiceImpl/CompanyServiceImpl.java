package com.example.flightApi.service.ServiceImpl;

import com.example.flightApi.exception.ResourceNotFoundException;
import com.example.flightApi.model.Company;
import com.example.flightApi.repository.CompanyRepository;
import com.example.flightApi.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company updateCompany(Long id, Company updatedCompany) throws ResourceNotFoundException {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setName(updatedCompany.getName());
                    company.setBanner(updatedCompany.getBanner());
                    return companyRepository.save(company);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
    }

    @Override
    public void deleteCompany(Long id) throws ResourceNotFoundException{
       Company company= companyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Company","id",id));
       companyRepository.deleteById(company.getId());

    }
}


