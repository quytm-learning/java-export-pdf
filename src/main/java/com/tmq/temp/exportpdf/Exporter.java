package com.tmq.temp.exportpdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by tmq on 01/06/2017.
 */
public class Exporter {

    public void export() throws Exception{
        Configuration cfg = configuration();
        Map<String, Object> input = getData();
        Template template = cfg.getTemplate("template.ftl");

        convertToPdf(template, input);
    }

    private Configuration configuration() throws IOException {
        Configuration cfg = new Configuration();
//        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
//        cfg.setDefaultEncoding("UTF-8");
//        cfg.setLocale(Locale.US);
//        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        cfg.setDirectoryForTemplateLoading(new File(getClass().getResource("/").getFile()));

        return cfg;
    }

    private Map<String, Object> getData() {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("title", "Tittle Template");
        input.put("user", new UserInfo("Trần Minh Quý", "this is favorite", "this is activity"));

        return input;
    }

    private void convertToPdf(Template template, Map<String, Object> input) throws Exception {
        File htmlTemp = new File("output.html");
        Writer fileWriter = new FileWriter(htmlTemp);
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();

            ITextRenderer iTextRenderer = new ITextRenderer();
            iTextRenderer.setDocument(htmlTemp);
            iTextRenderer.layout();

            File outputPdf = new File("output.pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(outputPdf);

            iTextRenderer.createPDF(fileOutputStream, true);

            System.out.println( "PDF Created!" );
        }
    }
}
