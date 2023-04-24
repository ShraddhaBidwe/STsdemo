package com.companydatabase.service;

import java.util.List;
import java.util.Optional;
import com.companydatabase.request.CompanyRequest;
import com.companydatabase.response.CompanyResponse;


public interface CompanyService {
   public List<CompanyResponse> getAllCompanies() throws Exception;
   public Optional<CompanyResponse> getCompanyById(Long companyId) throws Exception;  
   public String updateCompany(Long companyId, CompanyRequest companyRequest) throws Exception;
   public String deleteCompany(Long companyId) throws Exception;
   public CompanyResponse createCompany(CompanyRequest companyRequest);

}


// Conveter for all reuest object 
//save / update 
// update if id id exists then set others wise as it is
//response enity for all apis

//save company
//update