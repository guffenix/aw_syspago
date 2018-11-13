package ec.edu.espe.arqui;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import ec.edu.espe.arqui.entidades.Usuario;
import ec.edu.espe.arqui.facade.UsuarioFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @EJB
    private UsuarioFacade userFacade;
    private Usuario userGood;

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
        if(username != null && password != null){
            userGood = userFacade.obtenerUsuarioLogin(username, password);
//        if (username != null && username.equals("admin") && password != null && password.equals("admin")) {
        if (userGood != null) {
            loggedIn = true;

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            contextPath = origRequest.getContextPath();
            try {
                agregarCookie(origRequest, userGood.getUsuNombre(), userGood.getUsuId().toString());
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
        
    }

    private void agregarCookie(HttpServletRequest request,String user, String id) {
        HttpSession session = request.getSession();
        session.setAttribute("usuario", user);
        session.setAttribute("idUsuario", id);

    }

    public String getSaludo() {
        return saludo;
    }

    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }

    public UsuarioFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UsuarioFacade userFacade) {
        this.userFacade = userFacade;
    }

    public Usuario getUserGood() {
        return userGood;
    }

    public void setUserGood(Usuario userGood) {
        this.userGood = userGood;
    }
}
