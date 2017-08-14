package lk.gov.health.beans;

import lk.gov.health.entity.Computer;
import lk.gov.health.beans.util.JsfUtil;
import lk.gov.health.beans.util.JsfUtil.PersistAction;
import lk.gov.health.faces.ComputerFacade;

import java.io.Serializable;
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

@Named("computerController")
@SessionScoped
public class ComputerController implements Serializable {

    @EJB
    private lk.gov.health.faces.ComputerFacade ejbFacade;
    private List<Computer> items = null;
    private Computer selected;

    
    public String toAddComputer(){
        selected = new Computer();
        return "/computer/add_computer";
    }
    
    public String saveComputer(){
        getFacade().create(selected);
        JsfUtil.addSuccessMessage("Saved");
        return toAddComputer();
    }
    
    public ComputerController() {
    }

    public Computer getSelected() {
        return selected;
    }

    public void setSelected(Computer selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ComputerFacade getFacade() {
        return ejbFacade;
    }

    public Computer prepareCreate() {
        selected = new Computer();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleItem").getString("ComputerCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleItem").getString("ComputerUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleItem").getString("ComputerDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Computer> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleItem").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleItem").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Computer getComputer(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Computer> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Computer> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Computer.class)
    public static class ComputerControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ComputerController controller = (ComputerController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "computerController");
            return controller.getComputer(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Computer) {
                Computer o = (Computer) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Computer.class.getName()});
                return null;
            }
        }

    }

}
