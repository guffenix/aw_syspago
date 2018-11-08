
package ec.edu.espe.arqui;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author bigdata09
 */
@ManagedBean
public class Util {
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
     
}
