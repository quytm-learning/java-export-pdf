package com.tmq.temp.exportpdf;

import freemarker.template.Configuration;
import freemarker.template.Template;
import mobileskips.bootstrap.MobileSkipsWebInit;
import mobileskips.persistence.entity.Mobileskips_purchase_order;
import mobileskips.services.ContextHelper;
import mobileskips.services.PurchaseOrderService;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tmq on 02/06/2017.
 */
public class exporter_test {

    public void exportLikeMSkips() throws Exception {
        String BASE_PATH = MobileSkipsWebInit.context.getRealPath("");

        Configuration cfg = new Configuration();
//        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
//        cfg.setDefaultEncoding("UTF-8");
//        cfg.setLocale(Locale.US);
//        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        cfg.setDirectoryForTemplateLoading(new File(getClass().getResource("/").getFile()));
        Template template = cfg.getTemplate("template.ftl");

        File htmlTemp = new File(BASE_PATH + "output.html");
        Writer fileWriter = new FileWriter(htmlTemp);

        try {
            template.process(getData(), fileWriter);
        } finally {
            fileWriter.close();

            ITextRenderer iTextRenderer = new ITextRenderer();
            iTextRenderer.setDocument(htmlTemp);
            iTextRenderer.layout();

            File outputPdf = new File(BASE_PATH + "output.pdf");
            System.out.println(outputPdf.getAbsolutePath());
            FileOutputStream fileOutputStream = new FileOutputStream(outputPdf);

            iTextRenderer.createPDF(fileOutputStream, true);

            System.out.println("PDF Created!");
        }
    }


    private Map<String, Object> getData() {
        Map<String, Object> input = new HashMap<>();

        String poId = "REF2017055365";
        PurchaseOrderService purchaseOrderService = ContextHelper.getBean(PurchaseOrderService.class);
        Mobileskips_purchase_order purchaseOrder = purchaseOrderService.getPurchaseOrderInfoByPOId(poId);

        input.put("purchaseOrder", purchaseOrder);

        return input;
    }
}
