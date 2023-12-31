package com.asif.reportgenerateminiproject2.Service;

import com.asif.reportgenerateminiproject2.Entity.CustomerDetails;
import com.asif.reportgenerateminiproject2.Entity.SearchRequest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface SearchService {

    public List<CustomerDetails> getAllDetails();
    public List<String> getPlanNames();
    public List<String> getPlanStatus();
    public List<CustomerDetails> getDetailsBaseOnRequest(SearchRequest request);
    public void exportExcel(HttpServletResponse response) throws IOException;
    public void exportPdf(HttpServletResponse response) throws IOException;

}


