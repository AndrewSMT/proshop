package com.andrew.proshop.service.product.pdf;

import com.andrew.proshop.dto.FileWrapperDto;
import com.andrew.proshop.exception.EntityCouldNotBeFoundException;
import com.andrew.proshop.model.Check;
import com.andrew.proshop.model.Product;
import com.andrew.proshop.model.User;
import com.andrew.proshop.repository.CheckRepository;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CheckPdfGeneratorImpl implements CheckPdfGenerator {

    private final CheckRepository checkRepository;

    @Override
    public void generateCheckPdf(HttpServletResponse response, Long id) throws DocumentException, IOException {

        Check check = checkRepository.findById(id).orElseThrow(() ->
                new EntityCouldNotBeFoundException(String.format("Check with id %d could not found", id)));

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Check", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, check);

        document.add(table);

        document.close();
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.TIMES);

        cell.setPhrase(new Phrase("Product name", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("EAN", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table, Check check) {
        List<Product> listProducts = check.getProducts();
        for (Product product : listProducts) {
            table.addCell(product.getName());
            table.addCell(String.valueOf(product.getEAN()));
            table.addCell(String.format("%s UAH", product.getPrice()));
        }
        table.addCell("");
        table.addCell("");
        table.addCell(String.format("%s UAH", check.getCheckSum()));
    }
}
