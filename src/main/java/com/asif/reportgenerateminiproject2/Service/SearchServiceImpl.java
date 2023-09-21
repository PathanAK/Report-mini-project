package com.asif.reportgenerateminiproject2.Service;

import com.asif.reportgenerateminiproject2.Entity.CustomerDetails;
import com.asif.reportgenerateminiproject2.Entity.SearchRequest;
import com.asif.reportgenerateminiproject2.Repository.CustomerRepository;
import jakarta.servlet.http.HttpServletResponse;
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

        return null;
    }

    @Override
    public void exportExcel(HttpServletResponse response) {

    }

    @Override
    public void exportPdf(HttpServletResponse response) {

    }
}
