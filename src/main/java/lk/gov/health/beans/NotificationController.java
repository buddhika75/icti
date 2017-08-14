package lk.gov.health.beans;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import lk.gov.health.entity.Notification;
import lk.gov.health.beans.util.JsfUtil;
import lk.gov.health.beans.util.JsfUtil.PersistAction;
import lk.gov.health.faces.NotificationFacade;

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
import org.primefaces.model.UploadedFile;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import lk.gov.health.entity.Area;
import lk.gov.health.entity.AreaSummery;
import lk.gov.health.entity.AreaType;
import lk.gov.health.entity.Coordinate;
import lk.gov.health.entity.Institution;
import lk.gov.health.entity.Sex;
import lk.gov.health.faces.CoordinateFacade;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polygon;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

@Named("notificationController")
@SessionScoped
public class NotificationController implements Serializable {

    @EJB
    private lk.gov.health.faces.NotificationFacade ejbFacade;

    @Inject
    InstitutionController institutionController;
    @Inject
    AreaController areaController;
    @Inject
    CoordinateFacade coordinateFacade;
    @Inject
    WebUserController webUserController;
    @Inject
    AppController appController;

    private List<Notification> items = null;
    private Notification selected;
    private UploadedFile file;

    private Date fromDate;
    private Date toDate;
    private Area mohArea;
    private Area rdhsArea;
    private Area pdhsArea;
    Area selectedGn;
    Area selectedMoh;

    private List<AreaSummery> areaSummerys;
    List<Notification> areaNotifications;
    AreaSummery selectedAreaSummery;

    List<AreaSummery> allAreaSummerys;
    List<Notification> allAreaNotifications;

    List<Notification> notificationsToSave;
    String message = "";
    private TimelineModel model;
    private MapModel polygonModel;
    private MapModel provincialDengueMap;

    public AppController getAppController() {
        return appController;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public List<AreaSummery> getAllAreaSummerys() {
        if (appController.getAllAreaSummerys() == null) {
            createProvincialDengueMap();
        }
        allAreaSummerys = appController.getAllAreaSummerys();
        return allAreaSummerys;
    }

    public void setAllAreaSummerys(List<AreaSummery> allAreaSummerys) {
        this.allAreaSummerys = allAreaSummerys;
    }

    public List<Notification> getAllAreaNotifications() {
        return allAreaNotifications;
    }

    public void setAllAreaNotifications(List<Notification> allAreaNotifications) {
        this.allAreaNotifications = allAreaNotifications;
    }

    public MapModel getProvincialDengueMap() {
        if (appController.getProvincialDengueMap() == null) {
            createProvincialDengueMap();
        }
        provincialDengueMap = appController.getProvincialDengueMap();
        return provincialDengueMap;
    }

    public void setProvincialDengueMap(MapModel provincialDengueMap) {
        this.provincialDengueMap = provincialDengueMap;
    }

    public void createProvincialDengueMap() {
        System.out.println("creating provincial dengue map");
        List<Area> mohs = areaController.getMohAreas();
        allAreaSummerys = new ArrayList<AreaSummery>();
        allAreaNotifications = new ArrayList<Notification>();
        int count = 1;
        for (Area a : mohs) {
            AreaSummery as = new AreaSummery();
            as.setArea(a);
            as.setCount(0);
            as.setId(count);
            allAreaSummerys.add(as);
            count++;
        }
        System.out.println("allAreaSummerys = " + allAreaSummerys.size());
        Map m = new HashMap();
        String j;
        j = "select n "
                + " from Notification n "
                + " where n.SendDate between :sdf and :sdt ";
        m.put("sdf", getFromDate());
        m.put("sdt", getToDate());
        allAreaNotifications = getFacade().findBySQL(j, m);
        for (AreaSummery as : allAreaSummerys) {
            for (Notification n : allAreaNotifications) {
                if (as.getArea().equals(n.getMoh())) {
                    as.setCount(as.getCount() + 1);
                }
            }
        }
        System.out.println("allAreaNotifications = " + allAreaNotifications);
        createProvincialMap();
    }

    public String toUploadExcelFile() {
        return "/notification/upload_excel";
    }

    public String toListNotificationsByMoh() {
        return "/notification/moh_notifications";
    }

    public void addMohAreasToNotifications() {
        List<Notification> ns = new ArrayList<Notification>();
        String j;
        j = "select n "
                + " from Notification n "
                + " where n.moh is null";
        ns = getFacade().findBySQL(j);
        for (Notification n : ns) {
            if (n.getGnDivision() != null) {
                n.setMoh(n.getGnDivision().getMohArea());
                getFacade().edit(n);
            }
        }
    }

    public void addDistrictsToNotifications() {
        List<Notification> ns = new ArrayList<Notification>();
        String j;
        j = "select n "
                + " from Notification n "
                + " where n.district is null";
        ns = getFacade().findBySQL(j);
        for (Notification n : ns) {
            if (n.getGnDivision() != null) {
                n.setDistrict(n.getGnDivision().getRdhsArea());
                getFacade().edit(n);
            }
        }
    }

    public void removeDuplicates() {
        List<Notification> ns;
        String j;
        j = "select n "
                + " from Notification n";
        ns = getFacade().findBySQL(j);
        for (Notification n : ns) {
            j = "select n "
                    + " from Notificaion n "
                    + " where n.patientsName=:name"
                    + " and n.tel=:tel";
            Map m = new HashMap();
            m.put("name", n.getPatientsName());
            m.put("tel", n.getTel());
        }
    }

    public String listMohAreaNotifications() {
        areaNotifications = new ArrayList<Notification>();
        Map m = new HashMap();
        String j;
        j = "select n "
                + " from Notification n "
                + " where n.moh=:moh "
                + " and n.SendDate between :sdf and :sdt ";
        m.put("moh", mohArea);
        m.put("sdf", fromDate);
        m.put("sdt", toDate);
        areaNotifications = getFacade().findBySQL(j, m);
        return "/notification/moh_notifications";
    }

    public String deleteNotification() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to delete");
            return "";
        }
        getFacade().remove(selected);
        JsfUtil.addSuccessMessage("Deleted");
        return listMohAreaNotifications();
    }

    public String listMohAreaSummeries() {
        List<Area> gns = areaController.getGnAreasOfMoh(mohArea);
        areaSummerys = new ArrayList<AreaSummery>();
        areaNotifications = new ArrayList<Notification>();
        int count = 1;
        for (Area a : gns) {
            AreaSummery as = new AreaSummery();
            as.setArea(a);
            as.setCount(0);
            as.setId(count);
            areaSummerys.add(as);
            count++;
        }
        Map m = new HashMap();
        String j;
        j = "select n "
                + " from Notification n "
                + " where n.moh=:moh "
                + " and n.SendDate between :sdf and :sdt ";
        m.put("moh", mohArea);
        m.put("sdf", fromDate);
        m.put("sdt", toDate);
        areaNotifications = getFacade().findBySQL(j, m);
        for (AreaSummery as : areaSummerys) {
            for (Notification n : areaNotifications) {
                if (as.getArea().equals(n.getGnDivision())) {
                    as.setCount(as.getCount() + 1);
                }
            }
        }
        markGnMapForSummeries();
        return "/notification/moh_summery";
    }

    public String toAnalyzeGnByMoh() {
        return "/notification/moh_summery";
    }

    private void createProvincialMap() {
        provincialDengueMap = new DefaultMapModel();
        int maxCount = 0;
        for (AreaSummery a : allAreaSummerys) {
            if (maxCount < a.getCount()) {
                maxCount = a.getCount();
            }
        }
        for (AreaSummery a : allAreaSummerys) {
            Polygon polygon = new Polygon();
            LatLng cord = new LatLng(a.getArea().getCentreLatitude(), a.getArea().getCentreLongitude());
            a.setR(255);
            Double d;
            Integer co = a.getCount();
            d = ((double) co / (double) maxCount) * 255.0;
            System.out.println("d = " + d);
            int i = d.intValue();
            System.out.println("i = " + i);
            a.setG(255 - i);
            a.setB(255 - i);

//            if (maxCount < 25) {
//                a.setG(255 - (a.getCount() * 10));
//                a.setB(255 - (a.getCount() * 10));
//            } else if (maxCount < 255) {
//                a.setG(255 - a.getCount());
//                a.setB(255 - a.getCount());
//            } else if (maxCount < 2550) {
//                a.setG(255 - (a.getCount() / 10));
//                a.setB(255 - (a.getCount() / 10));
//            } else if (maxCount < 25500) {
//                a.setG(255 - (a.getCount() / 100));
//                a.setB(255 - (a.getCount() / 100));
//            }
            String j = "select c from Coordinate c where c.area=:a";
            Map m = new HashMap();
            m.put("a", a.getArea());
            List<Coordinate> cs = coordinateFacade.findBySQL(j, m);
            for (Coordinate c : cs) {
                LatLng coord = new LatLng(c.getLatitude(), c.getLongitude());
                polygon.getPaths().add(coord);
            }
            polygon.setStrokeColor("#FF9900");
            polygon.setFillColor(a.getRgb());
            polygon.setStrokeOpacity(1);
            polygon.setFillOpacity(0.9);
            polygon.setData(a.getArea().getId());
            provincialDengueMap.addOverlay(polygon);
        }
        appController.setProvincialDengueMap(provincialDengueMap);
        appController.setAllAreaSummerys(allAreaSummerys);
    }

//    public void createTimeLineOfNotifications() {
//        model = new TimelineModel();
//        for (Notification s : areaNotifications) {
//            if (s != null && s.getGnDivision() != null && s.getSendDate() != null) {
//                model.add(new TimelineEvent(s.getGnDivision().getName(), s.getSendDate()));
//            }
//        }
//    }
    private void markGnMapForSummeries() {
        polygonModel = new DefaultMapModel();
        int maxCount = 0;
        for (AreaSummery a : areaSummerys) {
            if (maxCount < a.getCount()) {
                maxCount = a.getCount();
            }
        }
        for (AreaSummery a : areaSummerys) {
            Polygon polygon = new Polygon();
            LatLng cord = new LatLng(a.getArea().getCentreLatitude(), a.getArea().getCentreLongitude());
            a.setR(255);
            Double d;
            Integer co = a.getCount();
            d = ((double) co / (double) maxCount) * 255.0;
            System.out.println("d = " + d);
            int i = d.intValue();
            System.out.println("i = " + i);
            a.setG(255 - i);
            a.setB(255 - i);

//            if (maxCount < 25) {
//                a.setG(255 - (a.getCount() * 10));
//                a.setB(255 - (a.getCount() * 10));
//            } else if (maxCount < 255) {
//                a.setG(255 - a.getCount());
//                a.setB(255 - a.getCount());
//            } else if (maxCount < 2550) {
//                a.setG(255 - (a.getCount() / 10));
//                a.setB(255 - (a.getCount() / 10));
//            } else if (maxCount < 25500) {
//                a.setG(255 - (a.getCount() / 100));
//                a.setB(255 - (a.getCount() / 100));
//            }
            String j = "select c from Coordinate c where c.area=:a";
            Map m = new HashMap();
            m.put("a", a.getArea());
            List<Coordinate> cs = coordinateFacade.findBySQL(j, m);
            for (Coordinate c : cs) {
                LatLng coord = new LatLng(c.getLatitude(), c.getLongitude());
                polygon.getPaths().add(coord);
            }
            polygon.setStrokeColor("#FF9900");
            polygon.setFillColor(a.getRgb());
            polygon.setStrokeOpacity(1);
            polygon.setFillOpacity(0.9);
            polygon.setData(a.getArea().getId());
            polygonModel.addOverlay(polygon);
//            polygonModel.addOverlay(new Marker(cord, a.getArea().getName(), "zelenjava.png", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
        }
    }

    public void onGnPolygonSelect(OverlaySelectEvent event) {
        Polygon polygon = (Polygon) event.getOverlay();
        selectedGn = getAreaController().getAreaById(Long.parseLong(polygon.getData().toString()));
        selectedAreaSummery = null;
        for (AreaSummery as : areaSummerys) {
            if (as.getArea().equals(selectedGn)) {
                selectedAreaSummery = as;
            }
        }
    }

    public void onPdPolygonSelect(OverlaySelectEvent event) {
        Polygon polygon = (Polygon) event.getOverlay();
        selectedMoh = getAreaController().getAreaById(Long.parseLong(polygon.getData().toString()));
        selectedAreaSummery = null;
        for (AreaSummery as : allAreaSummerys) {
            if (as.getArea().equals(selectedMoh)) {
                selectedAreaSummery = as;
            }
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String uploadNotificationExcelFile() {
        if (file == null || "".equals(file.getFileName())) {
            return "";
        }
        if (file == null) {
            JsfUtil.addErrorMessage("Please select an CSV File");
            return "";
        }

        notificationsToSave = new ArrayList<Notification>();

        InputStream in;
        JsfUtil.addSuccessMessage(file.getFileName() + " file uploaded.");
        try {
            JsfUtil.addSuccessMessage(file.getFileName());
            in = file.getInputstream();
            File f;
            f = new File(Calendar.getInstance().getTimeInMillis() + file.getFileName());
            FileOutputStream out = new FileOutputStream(f);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();

//            FileInputStream excelFile = new FileInputStream(new File(f.getAbsolutePath()));
//            Workbook workbook = new XSSFWorkbook(excelFile);
//            
            Workbook workbook = WorkbookFactory.create(new File(f.getAbsolutePath()));

            DataFormatter formatter = new DataFormatter();

            JsfUtil.addSuccessMessage("Excel File Opened");

            Sheet sheet1 = workbook.getSheetAt(0);

            int rowCount = 0;
            for (Row row : sheet1) {
//                System.out.println("row = " + row);
                if (rowCount > 4) {

                    Notification n = new Notification();

                    for (Cell cell : row) {
                        CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                        System.out.print(cellRef.formatAsString());
                        String strVal = "";
                        Date dateVal = null;
                        int intVal = 0;
                        Double dblVal = 0.00;
                        Boolean boolVal = false;
                        switch (cell.getCellTypeEnum()) {
                            case STRING:
                                strVal = cell.getRichStringCellValue().getString();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    dateVal = cell.getDateCellValue();
                                    strVal = dateVal + "";
                                } else {
                                    dblVal = cell.getNumericCellValue();
                                    intVal = dblVal.intValue();
                                    strVal = dblVal + "";
                                }
                                break;
                            case BOOLEAN:
                                boolVal = cell.getBooleanCellValue();
                                break;
                            case FORMULA:
                                System.out.println(cell.getCellFormula());
                                break;
                            case BLANK:
                                System.out.println();
                                break;
                            default:
                                System.out.println();
                        }
                        System.out.println("colNo = " + cell.getColumnIndex());
                        switch (cell.getColumnIndex()) {
                            case 0:
                                if (dblVal == 0.0) {
                                    continue;
                                }
                                strVal = formatter.formatCellValue(cell);
                                n.setSerialNo(strVal);
                                n.setPid(dblVal.longValue());
                                break;
                            case 1:
                                System.out.println("dateVal = " + dateVal);
                                if (dateVal == null) {
                                    dateVal = webUserController.getDateFromString(strVal);
                                }
                                n.setSendDate(dateVal);
                                System.out.println("n.getSendDate() = " + n.getSendDate());
                                break;
                            case 2:
                                Institution hospital = institutionController.getInstitutionsByName(strVal);
                                if (hospital == null) {
                                    hospital = institutionController.createHospital(strVal);
//                                    message += strVal + " is not a recognised hospital.\n";
                                }
                                n.setHospital(hospital);
                                break;
                            case 3:
                                if (dateVal == null) {
                                    dateVal = webUserController.getDateFromString(strVal);
                                }
                                n.setAddmitedDate(dateVal);
                                break;
                            case 4:
                                strVal = formatter.formatCellValue(cell);
                                n.setBht(strVal);
                                break;
                            case 5:
                                n.setPatientsName(strVal);
                                break;
                            case 6:
                                if (intVal == 0) {
                                    strVal = strVal.replaceAll("\\D+", "");
                                    try {
                                        intVal = Integer.parseUnsignedInt(strVal);
                                    } catch (NumberFormatException ex) {
                                        intVal = 999;
                                        System.out.println("strVal = " + strVal);
                                        System.out.println("ex = " + ex.getMessage());
                                    }
                                }
                                n.setAge(intVal);
                                break;
                            case 7:
                                if (strVal.trim().toLowerCase().equals("male")) {
                                    n.setGender(Sex.Male);
                                } else {
                                    n.setGender(Sex.Female);
                                }
                                break;
                            case 8:
                                Area district = areaController.getArea(strVal, AreaType.District);
                                n.setDistrict(district);
                                break;
                            case 9:
                                Area moh = areaController.getArea(strVal, AreaType.MOH);
                                if (moh == null) {
                                    message += strVal + " is not a recognised MOH Area. + \n";
                                }
                                n.setMoh(moh);
                                break;
                            case 11:
                                strVal = formatter.formatCellValue(cell);
                                if (!strVal.isEmpty()) {
                                    strVal = strVal.replaceAll("\\s+", "");
                                    Area gnArea = areaController.getArea(strVal, AreaType.GN);
                                    n.setGnDivision(gnArea);
                                    if (gnArea == null) {
                                        JsfUtil.addErrorMessage(strVal + " is not a recognised GN Area");
                                        message += strVal + " is not a recognised GN Area. + \n";
                                    }
                                }
                                break;
                            case 10:
                                if (n.getGnDivision() != null && n.getGnDivision().getParentArea() != null) {
                                    n.setPhiArea(n.getGnDivision().getParentArea().getParentArea());
                                }
                                break;
                            case 12:
                                n.setAddress(strVal);
                                break;
                            case 13:
                                strVal = formatter.formatCellValue(cell);
                                n.setTel(strVal);
                                break;
                        }

                    }
                    notificationsToSave.add(n);
                }
                rowCount++;
            }
            JsfUtil.addSuccessMessage("Succesfully added " + rowCount + " notifications.");
        } catch (IOException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
            return "";
        } catch (InvalidFormatException ex) {
            Logger.getLogger(NotificationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncryptedDocumentException ex) {
            Logger.getLogger(NotificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/notification/save_uploads";
    }

    public String saveUploadedData() {
        for (Notification n : notificationsToSave) {
            if (n.getPid() != null) {
                String j = "Select n "
                        + " from Notification n "
                        + " where (n.pid = :pid or n.tel=:tel)";
                Map m = new HashMap();
                m.put("pid", n.getPid());
                m.put("tel", n.getTel());
                Notification previousNotif = getFacade().findFirstBySQL(j, m);
                if (previousNotif == null) {
                    getFacade().create(n);
                } else {
                    if (n.getGnDivision() == null && previousNotif.getGnDivision() != null) {
                        previousNotif.setPid(n.getPid());
                        getFacade().edit(previousNotif);
                        System.out.println("Previous one saved");
                    } else {
                        if (previousNotif != null) {
                            getFacade().remove(previousNotif);
                        }
                        getFacade().create(n);
                        System.out.println("new one saved.");
                    }
                }
            }
        }
        JsfUtil.addSuccessMessage("Data Saved");
        notificationsToSave = new ArrayList<Notification>();
        return toUploadExcelFile();
    }

    public NotificationController() {
    }

    public Notification getSelected() {
        return selected;
    }

    public void setSelected(Notification selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private NotificationFacade getFacade() {
        return ejbFacade;
    }

    public Notification prepareCreate() {
        selected = new Notification();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleDengue").getString("NotificationCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleDengue").getString("NotificationUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleDengue").getString("NotificationDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Notification> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleDengue").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleDengue").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Notification getNotification(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Notification> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Notification> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public Date getFromDate() {
        if (fromDate == null) {
            fromDate = webUserController.getThirtyDaysBack();
        }
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        if (toDate == null) {
            toDate = new Date();
        }
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Area getMohArea() {
        return mohArea;
    }

    public void setMohArea(Area mohArea) {
        this.mohArea = mohArea;
    }

    public Area getRdhsArea() {
        return rdhsArea;
    }

    public void setRdhsArea(Area rdhsArea) {
        this.rdhsArea = rdhsArea;
    }

    public Area getPdhsArea() {
        return pdhsArea;
    }

    public void setPdhsArea(Area pdhsArea) {
        this.pdhsArea = pdhsArea;
    }

    public List<AreaSummery> getAreaSummerys() {
        return areaSummerys;
    }

    public void setAreaSummerys(List<AreaSummery> areaSummerys) {
        this.areaSummerys = areaSummerys;
    }

    public List<Notification> getNotificationsToSave() {
        return notificationsToSave;
    }

    public void setNotificationsToSave(List<Notification> notificationsToSave) {
        this.notificationsToSave = notificationsToSave;
    }

    public TimelineModel getModel() {
        return model;
    }

    public void setModel(TimelineModel model) {
        this.model = model;
    }

    public MapModel getPolygonModel() {
        return polygonModel;
    }

    public void setPolygonModel(MapModel polygonModel) {
        this.polygonModel = polygonModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Notification> getAreaNotifications() {
        return areaNotifications;
    }

    public void setAreaNotifications(List<Notification> areaNotifications) {
        this.areaNotifications = areaNotifications;
    }

    public NotificationFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(NotificationFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public InstitutionController getInstitutionController() {
        return institutionController;
    }

    public void setInstitutionController(InstitutionController institutionController) {
        this.institutionController = institutionController;
    }

    public AreaController getAreaController() {
        return areaController;
    }

    public void setAreaController(AreaController areaController) {
        this.areaController = areaController;
    }

    public CoordinateFacade getCoordinateFacade() {
        return coordinateFacade;
    }

    public void setCoordinateFacade(CoordinateFacade coordinateFacade) {
        this.coordinateFacade = coordinateFacade;
    }

    public WebUserController getWebUserController() {
        return webUserController;
    }

    public void setWebUserController(WebUserController webUserController) {
        this.webUserController = webUserController;
    }

    public Area getSelectedGn() {
        return selectedGn;
    }

    public void setSelectedGn(Area selectedGn) {
        this.selectedGn = selectedGn;
    }

    public Area getSelectedMoh() {
        return selectedMoh;
    }

    public void setSelectedMoh(Area selectedMoh) {
        this.selectedMoh = selectedMoh;
    }

    public AreaSummery getSelectedAreaSummery() {
        return selectedAreaSummery;
    }

    public void setSelectedAreaSummery(AreaSummery selectedAreaSummery) {
        this.selectedAreaSummery = selectedAreaSummery;
    }

    @FacesConverter(forClass = Notification.class)
    public static class NotificationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NotificationController controller = (NotificationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "notificationController");
            return controller.getNotification(getKey(value));
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
            if (object instanceof Notification) {
                Notification o = (Notification) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Notification.class.getName()});
                return null;
            }
        }

    }

}
