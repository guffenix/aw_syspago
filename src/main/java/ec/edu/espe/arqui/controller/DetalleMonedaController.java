package ec.edu.espe.arqui.controller;

import ec.edu.espe.arqui.entidades.DetalleMoneda;
import ec.edu.espe.arqui.controller.util.JsfUtil;
import ec.edu.espe.arqui.controller.util.JsfUtil.PersistAction;
import ec.edu.espe.arqui.facade.DetalleMonedaFacade;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("detalleMonedaController")
@SessionScoped
public class DetalleMonedaController implements Serializable {

    @EJB
    private ec.edu.espe.arqui.facade.DetalleMonedaFacade ejbFacade;
    private List<DetalleMoneda> items = null;
    private DetalleMoneda selected;

    public DetalleMonedaController() {
    }

    public DetalleMoneda getSelected() {
        return selected;
    }

    public void setSelected(DetalleMoneda selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getDetalleMonedaPK().setTmoId(selected.getTipomoneda().getTmoId().toBigInteger());
        selected.getDetalleMonedaPK().setPagId(selected.getPago().getPagId().toBigInteger());
    }

    protected void initializeEmbeddableKey() {
        selected.setDetalleMonedaPK(new ec.edu.espe.arqui.entidades.DetalleMonedaPK());
    }

    private DetalleMonedaFacade getFacade() {
        return ejbFacade;
    }

    public DetalleMoneda prepareCreate() {
        selected = new DetalleMoneda();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DetalleMonedaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DetalleMonedaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DetalleMonedaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DetalleMoneda> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public DetalleMoneda getDetalleMoneda(ec.edu.espe.arqui.entidades.DetalleMonedaPK id) {
        return getFacade().find(id);
    }

    public List<DetalleMoneda> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DetalleMoneda> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DetalleMoneda.class)
    public static class DetalleMonedaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DetalleMonedaController controller = (DetalleMonedaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "detalleMonedaController");
            return controller.getDetalleMoneda(getKey(value));
        }

        ec.edu.espe.arqui.entidades.DetalleMonedaPK getKey(String value) {
            ec.edu.espe.arqui.entidades.DetalleMonedaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new ec.edu.espe.arqui.entidades.DetalleMonedaPK();
            key.setPagId(new BigInteger(values[0]));
            key.setTmoId(new BigInteger(values[1]));
            return key;
        }

        String getStringKey(ec.edu.espe.arqui.entidades.DetalleMonedaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPagId());
            sb.append(SEPARATOR);
            sb.append(value.getTmoId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DetalleMoneda) {
                DetalleMoneda o = (DetalleMoneda) object;
                return getStringKey(o.getDetalleMonedaPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DetalleMoneda.class.getName()});
                return null;
            }
        }

    }

}
