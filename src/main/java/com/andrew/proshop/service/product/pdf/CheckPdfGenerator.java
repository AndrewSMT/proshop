package com.andrew.proshop.service.product.pdf;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CheckPdfGenerator {

    void generateCheckPdf(HttpServletResponse response, Long id) throws IOException;
}
