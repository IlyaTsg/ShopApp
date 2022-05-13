package MainFrame;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;


/**
 * PDF Reports from XML-file Generator Class
 */
public class ReportGenerator {
    public static void Generate(String datasource, String xpath, String template, String resultpath) {
        try {
            JRDataSource ds = new JRXmlDataSource(datasource, xpath);

            JasperReport jasperReport = JasperCompileManager.compileReport(template);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, ds);

            JasperExportManager.exportReportToPdfFile(jasperPrint, resultpath);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
