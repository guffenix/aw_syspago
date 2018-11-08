package ec.edu.espe.arqui;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

/**
 *
 * @author bigdata09
 */
@ManagedBean
public class UserLoginView {

    private String username;

    private String password;
    
    private String saludo = "Bienvenido";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login(ActionEvent event) {
        FacesMessage message = null;
        boolean loggedIn = false;
        String contextPath = "";
        if (username != null && username.equals("admin") && password != null && password.equals("admin")) {
            loggedIn = true;

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            contextPath = origRequest.getContextPath();
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(contextPath + "/u/seleccion-servicio.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", username);
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Loggin Error", "Credenciales Invalidas");
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
    }

    public String getSaludo() {
        return saludo;
    }

    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }
}
