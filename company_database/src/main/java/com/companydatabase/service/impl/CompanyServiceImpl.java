package com.companydatabase.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.companydatabase.entity.Company;
import com.companydatabase.entity.Users;
import com.companydatabase.repository.CompanyRepository;
import com.companydatabase.request.CompanyRequest;
import com.companydatabase.response.CompanyResponse;
import com.companydatabase.service.CompanyService;
import com.companydatabase.successfullmessage.MessageSuccessfull;
import com.companydatabase.transformer.RequestConverter;
import com.companydatabase.transformer.ResponceConverter;


@Service
public class CompanyServiceImpl implements CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;
  
    @Autowired
    private MessageSuccessfull messageSuccessfull;
    @Override
    public List<CompanyResponse> getAllCompanies()  throws Exception{
        try {
            List<Company> companies = companyRepository.findAll();
            return companies.stream().map(ResponceConverter::convertToResponse).collect(Collectors.toList());
        } catch (Exception e) {
             return Collections.emptyList();
        	
        }
    }

    @Override
    public Optional<CompanyResponse> getCompanyById(Long companyId)  throws Exception{
        try {
            Optional<Company> companyOptional = companyRepository.findById(companyId);
            if (companyOptional.isPresent()) {
                CompanyResponse companyResponse = ResponceConverter.convertToResponse(companyOptional.get());
                return Optional.of(companyResponse);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    
    @Override
    public String updateCompany(Long companyId, CompanyRequest companyRequest) throws Exception {
        try {
            Optional<Company> optionalCompany = companyRepository.findById(companyId);
            if (optionalCompany.isPresent()) {
                Company company = optionalCompany.get();
                RequestConverter.updateEntity(company, companyRequest);
                companyRepository.save(company);
                return messageSuccessfull.dataUpdated("Company");
            } else {
                return "Company not found.";
            }
        } catch (Exception e) {
            return "Error occurred at the time of updating.";
        }
    }


    @Override
    public String deleteCompany(Long companyId)  throws Exception{
        try {
            Optional<Company> optionalCompany = companyRepository.findById(companyId);
            if (optionalCompany.isPresent()) {
                Company company = optionalCompany.get();
                for (Users user : company.getUsers()) {
                    user.setDeleted(true);
                }

                company.setIsDeleted(true); 
                companyRepository.save(company);
                return "Company deleted successfully.";
            } else {
                return "Company not found.";
            }
        } catch (Exception e) {
            return "Error occurred at the time of deleting.";
        }
    }


	@Override
	public CompanyResponse createCompany(CompanyRequest companyRequest) {		
		Company company = RequestConverter.toEntity(companyRequest);
		Company com = this.companyRepository.save(company);
		return ResponceConverter.convertToResponse(com);
		
	}

}
	


	


