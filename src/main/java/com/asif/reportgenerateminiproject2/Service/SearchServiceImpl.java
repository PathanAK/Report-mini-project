package com.asif.reportgenerateminiproject2.Service;

import com.asif.reportgenerateminiproject2.Entity.CustomerDetails;
import com.asif.reportgenerateminiproject2.Entity.SearchRequest;
import com.asif.reportgenerateminiproject2.Repository.CustomerRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    CustomerRepository customerRepo;

    @Override
    public List<String> getPlanNames() {
        return customerRepo.getPlanNames();
    }

    @Override
    public List<String> getPlanStatus() {
        return customerRepo.getPlanStatus();
    }

    @Override
    public List<CustomerDetails> getDetailsBaseOnRequest(SearchRequest request) {

        CustomerDetails entity = new CustomerDetails();

        if (request.getPlanName() != null && !request.getPlanStatus().equals("")) {
            entity.setPlanName(request.getPlanName());
        }
        if (request.getPlanStatus() != null && !request.getPlanStatus().equals("")) {
            entity.setPlanStatus(request.getPlanStatus());
        }

        Example<CustomerDetails> example = Example.of(entity);
        List<CustomerDetails> records = customerRepo.findAll(example);
        return records;
    }

    @Override
    public void exportExcel(HttpServletResponse response) {

    }

    @Override
    public void exportPdf(HttpServletResponse response) {

    }
}
