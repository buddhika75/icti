package lk.gov.health.beans;

import lk.gov.health.entity.Computer;
import lk.gov.health.beans.util.JsfUtil;
import lk.gov.health.beans.util.JsfUtil.PersistAction;
import lk.gov.health.faces.ComputerFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.inject.Inject;
import lk.gov.health.entity.ComputerSummery;
import lk.gov.health.entity.Institution;
import lk.gov.health.entity.InstitutionType;
import lk.gov.health.entity.WebUser;

@Named("computerController")
@SessionScoped
public class ComputerController implements Serializable {

    @EJB
    private lk.gov.health.faces.ComputerFacade ejbFacade;

    @Inject
    InstitutionController institutionController;

    private List<Computer> items = null;
    private List<Computer> selectedItems = null;
    List<ComputerSummery> selectedSummeries = null;
    private Computer selected;

    Institution institution;
    Institution department;
    WebUser user;
    double costTotal;

    public double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(double costTotal) {
        this.costTotal = costTotal;
    }
    
    

    public String listInstitutionComputers() {
        selectedItems = null;
        costTotal =0.0;
        if (institution == null) {
            selectedItems = new ArrayList<Computer>();
        } else {
            String j = "Select c from Computer c "
                    + " where c.institution=:ins "
                    + " ";
            Map m = new HashMap();
            m.put("ins", institution);
            selectedItems = getFacade().findBySQL(j, m);
        }
        for(Computer c:selectedItems){
            costTotal += c.getTotalCost();
        }
        return "/computer/list_institution_computers";
    }

    public String listDepartmentComputers() {
        selectedItems = null;
        costTotal =0.0;
        if (department == null) {
            selectedItems = new ArrayList<Computer>();
        } else {
            String j = "Select c from Computer c "
                    + " where c.department=:dep "
                    + " ";
            Map m = new HashMap();
            m.put("dep", department);
            selectedItems = getFacade().findBySQL(j, m);
        }
         for(Computer c:selectedItems){
            costTotal += c.getTotalCost();
        }
        return "/computer/list_department_computers";
    }

    public String listPeronsInventoryComputers() {
        selectedItems = null;
        if (user == null) {
            selectedItems = new ArrayList<Computer>();
        } else {
            String j = "Select c from Computer c "
                    + " where c.inventoryBelongsTo=:u "
                    + " ";
            Map m = new HashMap();
            m.put("u", user);
            selectedItems = getFacade().findBySQL(j, m);
        }
        return "/computer/list_persons_inventory_computers";
    }

    public String listPersonalUseComputers() {
        selectedItems = null;
        if (user == null) {
            selectedItems = new ArrayList<Computer>();
        } else {
            String j = "Select c from Computer c "
                    + " where c.usedBy=:u "
                    + " ";
            Map m = new HashMap();
            m.put("u", user);
            selectedItems = getFacade().findBySQL(j, m);
        }
        return "/computer/list_persons_use_computers";
    }

    public String listInstitutionSummeryByDepartment() {
        selectedItems = null;
        selectedSummeries = new ArrayList<ComputerSummery>();
        if (institution == null) {
            selectedItems = new ArrayList<Computer>();
        } else {
            String j = "Select c from Computer c "
                    + " where c.institution=:ins "
                    + " ";
            Map m = new HashMap();
            m.put("ins", institution);
            selectedItems = getFacade().findBySQL(j, m);
        }
        List<Institution> deps = institutionController.getInstitutions(InstitutionType.Department, institution);

        ComputerSummery csn = new ComputerSummery();
        csn.setDepartment(null);
        csn.setId(0);
        selectedSummeries.add(csn);
        int i = 1;
        for (Institution dep : deps) {
            ComputerSummery cs = new ComputerSummery();
            cs.setDepartment(dep);
            cs.setId(i);
            selectedSummeries.add(cs);
            i++;
        }

        for (Computer c : selectedItems) {
            for (ComputerSummery cs : selectedSummeries) {
                if (cs.getDepartment() == null && c.getDepartment() == null) {
                    cs.setNumberOfComputers(cs.getNumberOfComputers() + 1);
                    cs.setValueOfComputers(cs.getValueOfComputers() + c.getTotalCost());
                } else if (c.getDepartment().equals(cs.getDepartment())) {
                    cs.setNumberOfComputers(cs.getNumberOfComputers() + 1);
                    cs.setValueOfComputers(cs.getValueOfComputers() + c.getTotalCost());
                }
            }
        }

        return "/computer/list_institution_summery";
    }

    public List<ComputerSummery> getSelectedSummeries() {
        return selectedSummeries;
    }

    public void setSelectedSummeries(List<ComputerSummery> selectedSummeries) {
        this.selectedSummeries = selectedSummeries;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Institution getDepartment() {
        return department;
    }

    public void setDepartment(Institution department) {
        this.department = department;
    }

    public WebUser getUser() {
        return user;
    }

    public void setUser(WebUser user) {
        this.user = user;
    }

    public List<Computer> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Computer> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public String toAddComputer() {
        selected = new Computer();
        return "/computer/add_computer";
    }

    public String saveComputer() {
        if (selected.getId() == null) {
            getFacade().create(selected);
            JsfUtil.addSuccessMessage("Saved");
        } else {
            getFacade().edit(selected);
            JsfUtil.addSuccessMessage("Updated");
        }
        Computer t = new Computer();
        t.setInstitution(selected.getInstitution());
        t.setDepartment(selected.getDepartment());
        t.setMaintainedBy(selected.getMaintainedBy());
        selected = t;
        return "/computer/add_computer";
    }

    public void calculateTotals() {
        if (selected == null) {
            return;
        }
        Double t = 0.0;
        t += selected.getSubTotalCost2_1To2_17();
        t += selected.getKeyboardCost();
        t += selected.getMouseCost();
        t += selected.getInternalMicrophoneCost();
        t += selected.getExternalMicrophoneCost();
        t += selected.getScannerCost();
        t += selected.getMonitorCost();
        t += selected.getPrinterCost();
        t += selected.getSpeakerCost();
        t += selected.getUpsCost();
        t += selected.getStabilizerCost();
        t += selected.getCablesCost();
        t += selected.getOthersCost();
        t += selected.getOperatingSystemCost();
        t += selected.getInternetEmailCost();
        t += selected.getAdditionalSoftware1Cost();
        t += selected.getAdditionalSoftware2Cost();
        t += selected.getAdditionalSoftware3Cost();
        t += selected.getAdditionalSoftware4Cost();
        t += selected.getAdditionalSoftware5Cost();
        t += selected.getAdditionalSoftware6Cost();
        t += selected.getAdditionalSoftware7Cost();
        t += selected.getAdditionalSoftware8Cost();
        t += selected.getAdditionalSoftware9Cost();
        t += selected.getAdditionalSoftware10Cost();
        selected.setTotalCost(t);
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
