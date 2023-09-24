package com.asif.reportgenerateminiproject2.Controller;

import com.asif.reportgenerateminiproject2.Entity.CustomerDetails;
import com.asif.reportgenerateminiproject2.Service.SearchService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDetails>> getAllDetails() {
        return new ResponseEntity<>(searchService.getAllDetails(), HttpStatus.OK);
    }

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=CustomerDetails.xls";
        response.setHeader(headerValue, headerValue);
        searchService.exportExcel(response);
    }
}
