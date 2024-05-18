package com.example.flightApi.controller;

import com.example.flightApi.exception.ResourceNotFoundException;
import com.example.flightApi.model.Company;
import com.example.flightApi.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
public class CompanyController {


    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @CrossOrigin
    @GetMapping("")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PostMapping("create")
    public Company createCompany(@RequestBody Company company) {

        return companyService.createCompany(company);
    }

    @GetMapping("/{id}")
    public Optional<Company> getCompanyById(@PathVariable Long id) throws ResourceNotFoundException {
        return companyService.findById(id);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company updatedCompany) {
        return companyService.updateCompany(id, updatedCompany);
    }

    @DeleteMapping("delete/{id}")
    public String deleteCompany(@PathVariable Long id) throws ResourceNotFoundException {
        try {
            companyService.deleteCompany(id);
            return "compañia borrada correctamente";
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            return"no se encontro la compania";
        }

    }
}