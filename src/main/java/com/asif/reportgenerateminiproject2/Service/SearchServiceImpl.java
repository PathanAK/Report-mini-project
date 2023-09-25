package com.asif.reportgenerateminiproject2.Service;

import com.asif.reportgenerateminiproject2.Entity.CustomerDetails;
import com.asif.reportgenerateminiproject2.Entity.SearchRequest;
import com.asif.reportgenerateminiproject2.Repository.CustomerRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Customer_info");
        XSSFRow headerRow = sheet.createRow(0);

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
            XSSFRow dataRow = sheet.createRow(dataRowIndex);
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
    public void exportPdf(HttpServletResponse response) throws FileNotFoundException {

        List<CustomerDetails> details = customerRepo.findAll();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream("CustomerDetails.pdf"));
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(14);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("List of Customer", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        //set table header data
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font f = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        f.setColor(Color.white);

        cell.setPhrase(new Phrase("ID", f));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name", f));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email ID", f));
        table.addCell(cell);

        cell.setPhrase(new Phrase("PhoneNumber", f));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Gender", f));
        table.addCell(cell);

        cell.setPhrase(new Phrase("SSN", f));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Plan Name", f));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Plan Status", f));
        table.addCell(cell);

        //set table data

        for(CustomerDetails record : details) {
            table.addCell(String.valueOf(record.getId()));
            table.addCell(record.getName());
            table.addCell(record.getEmail());
            table.addCell(String.valueOf(record.getPhoneNumber()));
            table.addCell(record.getGender());
            table.addCell(String.valueOf(record.getSsn()));
            table.addCell(record.getPlanName());
            table.addCell(record.getPlanStatus());

        }

        document.add(table);
        document.close();


    }
}
