package com.asif.reportgenerateminiproject2.Controller;

import com.asif.reportgenerateminiproject2.Entity.CustomerDetails;
import com.asif.reportgenerateminiproject2.Entity.SearchRequest;
import com.asif.reportgenerateminiproject2.Service.SearchService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/plannames")
    public ResponseEntity<List<String>> getAllPlanNames() {
        return new ResponseEntity<>(searchService.getPlanNames(), HttpStatus.OK);
    }

    @GetMapping("/planstatus")
    public ResponseEntity<List<String>> getAllStatusNames() {
        return new ResponseEntity<>(searchService.getPlanStatus(), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<CustomerDetails>> getDetailsBasedOnSearch(@RequestBody SearchRequest request) {
        List<CustomerDetails> detailsBaseOnRequest = searchService.getDetailsBaseOnRequest(request);
        return new ResponseEntity<>(detailsBaseOnRequest, HttpStatus.OK);
    }

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=CustomerDetails.xls";
        response.setHeader(headerKey, headerValue);
        searchService.exportExcel(response);
        response.flushBuffer();
    }

    @GetMapping("/pdf")
    public void generatePdfReport(HttpServletResponse response) throws Exception {

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=CustomerDetails.pdf";
        response.setHeader(headerKey, headerValue);
        searchService.exportPdf(response);
        response.flushBuffer();

    }
}
