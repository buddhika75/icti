package lk.gov.health.beans;

import lk.gov.health.entity.Institution;
import lk.gov.health.beans.util.JsfUtil;
import lk.gov.health.beans.util.JsfUtil.PersistAction;
import lk.gov.health.faces.InstitutionFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import lk.gov.health.entity.Area;
import lk.gov.health.entity.InstitutionType;

@Named
@SessionScoped
public class InstitutionController implements Serializable {

    @EJB
    private lk.gov.health.faces.InstitutionFacade ejbFacade;
    private List<Institution> items = null;
    private List<Institution> depts = null;
    private List<Institution> ins = null;
    private List<Institution> coms = null;
    private Institution selected;

    public String toAddSchool() {
        selected = new Institution();
        selected.setType(InstitutionType.Instituon);
        return "/institution/add_school";
    }

    public String saveNewSchool() {
        selected.setCreateAt(new Date());
        getFacade().create(selected);
        JsfUtil.addSuccessMessage("New School Created");
        selected = null;
        return "/institution/index";
    }

    public List<Institution> getDepts() {
        if (depts == null) {
            depts = getInstitutions(InstitutionType.Department);
        }
        return depts;
    }

    public void setDepts(List<Institution> depts) {
        this.depts = depts;
    }

    public List<Institution> getIns() {
        if (ins == null) {
            ins = getInstitutions(InstitutionType.Instituon);
        }
        return ins;
    }

    public void setIns(List<Institution> ins) {
        this.ins = ins;
    }

    public List<Institution> getComs() {
        if (coms == null) {
            coms = getInstitutions(InstitutionType.Company);
        }
        return coms;
    }

    public void setComs(List<Institution> coms) {
        this.coms = coms;
    }

    public Institution createHospital(String hospitalName) {
        Institution mi = new Institution();
        mi.setType(InstitutionType.Hospital);
        mi.setName(hospitalName);
        getFacade().create(mi);
        return mi;
    }
    
    public List<Institution> getInstitutions(InstitutionType type, Institution parent) {
        String j;
        Map m = new HashMap();
        j = "select i from "
                + " Institution i "
                + " where i.name is not null";
        if (type != null) {
            j += " and i.type=:t ";
            m.put("t", type);
        }
        if (parent != null) {
            j += " and i.parent=:p ";
            m.put("p", parent);
        }
        j+= " order by i.name";
        return getFacade().findBySQL(j, m);
    }

    public List<Institution> getInstitutions(InstitutionType type, Area phiArea, Area area, Area educationalZone) {
        String j;
        Map m = new HashMap();
        j = "select i from "
                + " Institution i "
                + " where i.name is not null";
        if (type != null) {
            j += " and i.type=:t ";
            m.put("t", type);
        }
        if (phiArea != null) {
            j += " and i.phiArea=:p ";
            m.put("p", phiArea);
        }
        if (area != null) {
            j += " and (i.phiArea=:a or i.phiArea.parentArea=:a or i.phiArea.parentArea.parentArea=:a  or i.phiArea.parentArea.parentArea.parentArea=:a ) ";
            m.put("a", area);
        }
        if (educationalZone != null) {
            j += " and i.educationalZone=:e ";
            m.put("e", phiArea);
        }
        return getFacade().findBySQL(j, m);
    }

    public List<Institution> getInstitutions(InstitutionType type) {
        String j;
        Map m = new HashMap();
        j = "select i from "
                + " Institution i "
                + " where i.name is not null";
        if (type != null) {
            j += " and i.type=:t ";
            m.put("t", type);
        }
        return getFacade().findBySQL(j, m);
    }

    public Institution getInstitutionsByName(String name) {
        String j;
        Map m = new HashMap();
        j = "select i from "
                + " Institution i "
                + " where upper(i.name) =:n ";
        m.put("n", name.trim().toUpperCase());
//        System.out.println("m = " + m);
//        System.out.println("j = " + j);
        return getFacade().findFirstBySQL(j, m);
    }

    public InstitutionController() {
    }

    public Institution getSelected() {
        return selected;
    }

    public void setSelected(Institution selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InstitutionFacade getFacade() {
        return ejbFacade;
    }

    public Institution prepareCreate() {
        selected = new Institution();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InstitutionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            coms = null;
            ins = null;
            depts = null;
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InstitutionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("InstitutionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            coms = null;
            ins = null;
            depts = null;
        }
    }

    public List<Institution> getItems() {
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

    public List<Institution> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Institution> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Institution.class)
    public static class InstitutionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InstitutionController controller = (InstitutionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "institutionController");
            return controller.getFacade().find(getKey(value));
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
            if (object instanceof Institution) {
                Institution o = (Institution) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Institution.class.getName()});
                return null;
            }
        }

    }

}
