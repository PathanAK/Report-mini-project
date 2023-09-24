package com.asif.reportgenerateminiproject2.Service;

import com.asif.reportgenerateminiproject2.Entity.CustomerDetails;
import com.asif.reportgenerateminiproject2.Entity.SearchRequest;
import com.asif.reportgenerateminiproject2.Repository.CustomerRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public List<CustomerDetails> getAllDetails() {
        return customerRepo.findAll();
    }

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
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<CustomerDetails> details = customerRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);

        // Define headers
        headerRow.createCell(0).setCellValue("Id");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("PhoneNumber");
        headerRow.createCell(4).setCellValue("Gender");
        headerRow.createCell(5).setCellValue("SSN");
        headerRow.createCell(6).setCellValue("PlanName");
        headerRow.createCell(7).setCellValue("PlanStatus");

        int dataRowIndex = 1;

        for (CustomerDetails detail : details) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(detail.getId());
            dataRow.createCell(1).setCellValue(detail.getName());
            dataRow.createCell(2).setCellValue(detail.getEmail());
            dataRow.createCell(3).setCellValue(detail.getPhoneNumber());
            dataRow.createCell(4).setCellValue(detail.getGender()); // Corrected index
            dataRow.createCell(5).setCellValue(detail.getSsn()); // Corrected index
            dataRow.createCell(6).setCellValue(detail.getPlanName()); // Corrected index
            dataRow.createCell(7).setCellValue(detail.getPlanStatus()); // Corrected index
            dataRowIndex++;
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=CustomerDetails.xls");

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        // Close the workbook and output stream
        workbook.close();
        outputStream.flush();
        outputStream.close();
    }


    @Override
    public void exportPdf(HttpServletResponse response) {

    }
}
