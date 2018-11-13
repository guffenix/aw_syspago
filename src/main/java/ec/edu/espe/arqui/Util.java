
package ec.edu.espe.arqui;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.component.export.PDFOptions;

/**
 *
 * @author bigdata09
 */
@ManagedBean
public class Util implements Serializable {
     
    private PDFOptions pdfOpt;
    
    public void customizationOptions() {
    
        pdfOpt = new PDFOptions();
        pdfOpt.setFacetBgColor("#F88017");
        pdfOpt.setFacetFontColor("#0000ff");
        pdfOpt.setFacetFontStyle("BOLD");
        pdfOpt.setCellFontSize("10");
    
    }
    
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        
//        pdf.setPageSize(PageSize.A4);
        pdf.setPageSize(PageSize.A4.rotate()); 
        pdf.open();
        HeaderFooter header = new HeaderFooter(new Phrase("<b>Arquitectura de Software</b> Comprobante de Pago"), false);
        pdf.setHeader(header);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "imagenes" + File.separator + "logo.png";
        Image miLogo = Image.getInstance(logo);
        miLogo.scaleToFit(60, 60);

        pdf.add(new Paragraph("Sistema de Pagos"));
        pdf.add(miLogo);
    }
 
    
    public void ejemplo() {
        addMessage("Opcion1", "Ejecucion 1");
    }
    public void ejemplo2() {
        addMessage("Opcion2", "Ejecucion 2");
    }
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public PDFOptions getPdfOpt() {
        return pdfOpt;
    }

    public void setPdfOpt(PDFOptions pdfOpt) {
        this.pdfOpt = pdfOpt;
    }
     
}
