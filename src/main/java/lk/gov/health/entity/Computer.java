/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author User
 */
@Entity
public class Computer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String inventoryNo;
    String serial;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfPurchase;
    @ManyToOne
    Institution department;
    @Enumerated(EnumType.STRING)
    PurchasedOrTransferred purchasedOrTransferredFrom;
    @ManyToOne
    Institution fromInstitution;
    String make;
    String model;
    @Enumerated(EnumType.STRING)
    ComputerType type;
    @Enumerated(EnumType.STRING)
    Processor processor;
    Double speed =0.0;
    @Enumerated(EnumType.STRING)
    Ram ram;
    Double ramCapasity =0.0;
    @Enumerated(EnumType.STRING)
    HddType hddType;
    Double hddCapasity =0.0;
    Boolean fdd;
    Boolean cdr;
    Boolean soundCard;
    Boolean tvCard;
    Boolean networkCard;
    Boolean externalModerm;
    Boolean internalModerm;

    Boolean other1;
    String otherComopnent1;
    Boolean other2;
    String otherComopnent2;
    Boolean other3;
    String otherComopnent3;
    Boolean other4;
    String otherComopnent4;
    Boolean other5;
    String otherComopnent5;
    Boolean other6;
    String otherComopnent6;
    Boolean other7;
    String otherComopnent7;
    Double subTotalCost2_1To2_17 =0.0;

    Boolean keyboard;
    String keyboardSerial;
    Double keyboardCost =0.0;

    Boolean mouse;
    String mouseSerial;
    Double mouseCost =0.0;

    Boolean internalMicrophone;
    Boolean externalMicrophone;

    String microphoneSerial;
    Double internalMicrophoneCost =0.0;
    Double externalMicrophoneCost =0.0;

    Boolean scanner;
    String scannerMake;
    String scannerModel;
    String scannerSerial;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date scannerDateOfPurchase;
    private Double scannerCost =0.0;

    String monitorMake;
    String monitorModel;
    String monitorSerial;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date monitorDateOfPurchase;
    Double monitorCost =0.0;

    Boolean printer;
    String printerMake;
    String printerModel;
    String printerSerial;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date printerDateOfPurchase;
    Double printerCost =0.0;

    String speakerMake;
    String speakerModel;
    String speakerSerial;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date speakerDateOfPurchase;
    Double speakerCost =0.0;

    Boolean ups;
    String upsMake;
    String upsModel;
    String upsSerial;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date upsDateOfPurchase;
    Double upsCost =0.0;

    String stabilizerMake;
    String stabilizerModel;
    String stabilizerSerial;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date stabilizerDateOfPurchase;
    Double stabilizerCost =0.0;

    String cablesMake;
    String cablesDescription;
    String cablesModel;
    String cablesSerial;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date cablesDateOfPurchase;
    Double cablesCost =0.0;

    String othersMake;
    String othersDescription;
    String othersModel;
    String othersSerial;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date othersDateOfPurchase;
    Double othersCost =0.0;

    @Enumerated(EnumType.STRING)
    OperatingSystem operatingSystem;
    Double operatingSystemCost =0.0;
    
    Boolean internetEmail;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date internetEmailDateOfPurchase;
    Double internetEmailCost =0.0;
    
    String additionalSoftware1Description;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date additionalSoftware1DateOfPurchase;
    Double additionalSoftware1Cost =0.0;
    
    String additionalSoftware2Description;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date additionalSoftware2DateOfPurchase;
    Double additionalSoftware2Cost =0.0;

    String additionalSoftware3Description;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date additionalSoftware3DateOfPurchase;
    Double additionalSoftware3Cost =0.0;

    String additionalSoftware4Description;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date additionalSoftware4DateOfPurchase;
    Double additionalSoftware4Cost =0.0;

    String additionalSoftware5Description;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date additionalSoftware5DateOfPurchase;
    Double additionalSoftware5Cost =0.0;

    String additionalSoftware6Description;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date additionalSoftware6DateOfPurchase;
    Double additionalSoftware6Cost =0.0;

    String additionalSoftware7Description;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date additionalSoftware7DateOfPurchase;
    Double additionalSoftware7Cost =0.0;

    String additionalSoftware8Description;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date additionalSoftware8DateOfPurchase;
    Double additionalSoftware8Cost =0.0;

    String additionalSoftware9Description;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date additionalSoftware9DateOfPurchase;
    Double additionalSoftware9Cost =0.0;

    String additionalSoftware10Description;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date additionalSoftware10DateOfPurchase;
    Double additionalSoftware10Cost =0.0;

    Double totalCost =0.0;
    @ManyToOne
    Institution maintainedBy;
    @Lob
    String importantInformation;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date warrantyValidTill;
    @Lob
    String warranty;
    @ManyToOne
    WebUser usedBy;
    @ManyToOne
    WebUser inventoryBelongsTo;
    @ManyToOne
    Institution institution;

    public Boolean getPrinter() {
        return printer;
    }

    public void setPrinter(Boolean printer) {
        this.printer = printer;
    }

    public Boolean getUps() {
        return ups;
    }

    public void setUps(Boolean ups) {
        this.ups = ups;
    }

    
    
    public WebUser getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(WebUser usedBy) {
        this.usedBy = usedBy;
    }

    public WebUser getInventoryBelongsTo() {
        return inventoryBelongsTo;
    }

    public void setInventoryBelongsTo(WebUser inventoryBelongsTo) {
        this.inventoryBelongsTo = inventoryBelongsTo;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Institution getDepartment() {
        return department;
    }

    public void setDepartment(Institution department) {
        this.department = department;
    }

    public PurchasedOrTransferred getPurchasedOrTransferredFrom() {
        return purchasedOrTransferredFrom;
    }

    public void setPurchasedOrTransferredFrom(PurchasedOrTransferred purchasedOrTransferredFrom) {
        this.purchasedOrTransferredFrom = purchasedOrTransferredFrom;
    }

    public Institution getFromInstitution() {
        return fromInstitution;
    }

    public void setFromInstitution(Institution fromInstitution) {
        this.fromInstitution = fromInstitution;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ComputerType getType() {
        return type;
    }

    public void setType(ComputerType type) {
        this.type = type;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Ram getRam() {
        return ram;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public Double getRamCapasity() {
        return ramCapasity;
    }

    public void setRamCapasity(Double ramCapasity) {
        this.ramCapasity = ramCapasity;
    }

    public HddType getHddType() {
        return hddType;
    }

    public void setHddType(HddType hddType) {
        this.hddType = hddType;
    }

    public Double getHddCapasity() {
        return hddCapasity;
    }

    public void setHddCapasity(Double hddCapasity) {
        this.hddCapasity = hddCapasity;
    }

    public Boolean getFdd() {
        return fdd;
    }

    public void setFdd(Boolean fdd) {
        this.fdd = fdd;
    }

    public Boolean getCdr() {
        return cdr;
    }

    public void setCdr(Boolean cdr) {
        this.cdr = cdr;
    }

    public Boolean getSoundCard() {
        return soundCard;
    }

    public void setSoundCard(Boolean soundCard) {
        this.soundCard = soundCard;
    }

    public Boolean getTvCard() {
        return tvCard;
    }

    public void setTvCard(Boolean tvCard) {
        this.tvCard = tvCard;
    }

    public Boolean getNetworkCard() {
        return networkCard;
    }

    public void setNetworkCard(Boolean networkCard) {
        this.networkCard = networkCard;
    }

    public Boolean getExternalModerm() {
        return externalModerm;
    }

    public void setExternalModerm(Boolean externalModerm) {
        this.externalModerm = externalModerm;
    }

    public Boolean getInternalModerm() {
        return internalModerm;
    }

    public void setInternalModerm(Boolean internalModerm) {
        this.internalModerm = internalModerm;
    }

    public Boolean getOther1() {
        return other1;
    }

    public void setOther1(Boolean other1) {
        this.other1 = other1;
    }

    public String getOtherComopnent1() {
        return otherComopnent1;
    }

    public void setOtherComopnent1(String otherComopnent1) {
        this.otherComopnent1 = otherComopnent1;
    }

    public Boolean getOther2() {
        return other2;
    }

    public void setOther2(Boolean other2) {
        this.other2 = other2;
    }

    public String getOtherComopnent2() {
        return otherComopnent2;
    }

    public void setOtherComopnent2(String otherComopnent2) {
        this.otherComopnent2 = otherComopnent2;
    }

    public Boolean getOther3() {
        return other3;
    }

    public void setOther3(Boolean other3) {
        this.other3 = other3;
    }

    public String getOtherComopnent3() {
        return otherComopnent3;
    }

    public void setOtherComopnent3(String otherComopnent3) {
        this.otherComopnent3 = otherComopnent3;
    }

    public Boolean getOther4() {
        return other4;
    }

    public void setOther4(Boolean other4) {
        this.other4 = other4;
    }

    public String getOtherComopnent4() {
        return otherComopnent4;
    }

    public void setOtherComopnent4(String otherComopnent4) {
        this.otherComopnent4 = otherComopnent4;
    }

    public Boolean getOther5() {
        return other5;
    }

    public void setOther5(Boolean other5) {
        this.other5 = other5;
    }

    public String getOtherComopnent5() {
        return otherComopnent5;
    }

    public void setOtherComopnent5(String otherComopnent5) {
        this.otherComopnent5 = otherComopnent5;
    }

    public Boolean getOther6() {
        return other6;
    }

    public void setOther6(Boolean other6) {
        this.other6 = other6;
    }

    public String getOtherComopnent6() {
        return otherComopnent6;
    }

    public void setOtherComopnent6(String otherComopnent6) {
        this.otherComopnent6 = otherComopnent6;
    }

    public Boolean getOther7() {
        return other7;
    }

    public void setOther7(Boolean other7) {
        this.other7 = other7;
    }

    public String getOtherComopnent7() {
        return otherComopnent7;
    }

    public void setOtherComopnent7(String otherComopnent7) {
        this.otherComopnent7 = otherComopnent7;
    }

    public Double getSubTotalCost2_1To2_17() {
        return subTotalCost2_1To2_17;
    }

    public void setSubTotalCost2_1To2_17(Double subTotalCost2_1To2_17) {
        this.subTotalCost2_1To2_17 = subTotalCost2_1To2_17;
    }

    public Boolean getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Boolean keyboard) {
        this.keyboard = keyboard;
    }

    public String getKeyboardSerial() {
        return keyboardSerial;
    }

    public void setKeyboardSerial(String keyboardSerial) {
        this.keyboardSerial = keyboardSerial;
    }

    public Double getKeyboardCost() {
        return keyboardCost;
    }

    public void setKeyboardCost(Double keyboardCost) {
        this.keyboardCost = keyboardCost;
    }

    public Boolean getMouse() {
        return mouse;
    }

    public void setMouse(Boolean mouse) {
        this.mouse = mouse;
    }

    public String getMouseSerial() {
        return mouseSerial;
    }

    public void setMouseSerial(String mouseSerial) {
        this.mouseSerial = mouseSerial;
    }

    public Double getMouseCost() {
        return mouseCost;
    }

    public void setMouseCost(Double mouseCost) {
        this.mouseCost = mouseCost;
    }

    public Boolean getInternalMicrophone() {
        return internalMicrophone;
    }

    public void setInternalMicrophone(Boolean internalMicrophone) {
        this.internalMicrophone = internalMicrophone;
    }

    public Boolean getExternalMicrophone() {
        return externalMicrophone;
    }

    public void setExternalMicrophone(Boolean externalMicrophone) {
        this.externalMicrophone = externalMicrophone;
    }

    public String getMicrophoneSerial() {
        return microphoneSerial;
    }

    public void setMicrophoneSerial(String microphoneSerial) {
        this.microphoneSerial = microphoneSerial;
    }

    public Double getInternalMicrophoneCost() {
        return internalMicrophoneCost;
    }

    public void setInternalMicrophoneCost(Double internalMicrophoneCost) {
        this.internalMicrophoneCost = internalMicrophoneCost;
    }

    public Double getExternalMicrophoneCost() {
        return externalMicrophoneCost;
    }

    public void setExternalMicrophoneCost(Double externalMicrophoneCost) {
        this.externalMicrophoneCost = externalMicrophoneCost;
    }

    public Boolean getScanner() {
        return scanner;
    }

    public void setScanner(Boolean scanner) {
        this.scanner = scanner;
    }

    public String getScannerMake() {
        return scannerMake;
    }

    public void setScannerMake(String scannerMake) {
        this.scannerMake = scannerMake;
    }

    public String getScannerModel() {
        return scannerModel;
    }

    public void setScannerModel(String scannerModel) {
        this.scannerModel = scannerModel;
    }

    public String getScannerSerial() {
        return scannerSerial;
    }

    public void setScannerSerial(String scannerSerial) {
        this.scannerSerial = scannerSerial;
    }

    public Date getScannerDateOfPurchase() {
        return scannerDateOfPurchase;
    }

    public void setScannerDateOfPurchase(Date scannerDateOfPurchase) {
        this.scannerDateOfPurchase = scannerDateOfPurchase;
    }

    public String getMonitorMake() {
        return monitorMake;
    }

    public void setMonitorMake(String monitorMake) {
        this.monitorMake = monitorMake;
    }

    public String getMonitorModel() {
        return monitorModel;
    }

    public void setMonitorModel(String monitorModel) {
        this.monitorModel = monitorModel;
    }

    public String getMonitorSerial() {
        return monitorSerial;
    }

    public void setMonitorSerial(String monitorSerial) {
        this.monitorSerial = monitorSerial;
    }

    public Date getMonitorDateOfPurchase() {
        return monitorDateOfPurchase;
    }

    public void setMonitorDateOfPurchase(Date monitorDateOfPurchase) {
        this.monitorDateOfPurchase = monitorDateOfPurchase;
    }

    public Double getMonitorCost() {
        return monitorCost;
    }

    public void setMonitorCost(Double monitorCost) {
        this.monitorCost = monitorCost;
    }

    public String getPrinterMake() {
        return printerMake;
    }

    public void setPrinterMake(String printerMake) {
        this.printerMake = printerMake;
    }

    public String getPrinterModel() {
        return printerModel;
    }

    public void setPrinterModel(String printerModel) {
        this.printerModel = printerModel;
    }

    public String getPrinterSerial() {
        return printerSerial;
    }

    public void setPrinterSerial(String printerSerial) {
        this.printerSerial = printerSerial;
    }

    public Date getPrinterDateOfPurchase() {
        return printerDateOfPurchase;
    }

    public void setPrinterDateOfPurchase(Date printerDateOfPurchase) {
        this.printerDateOfPurchase = printerDateOfPurchase;
    }

    public Double getPrinterCost() {
        return printerCost;
    }

    public void setPrinterCost(Double printerCost) {
        this.printerCost = printerCost;
    }

    public String getSpeakerMake() {
        return speakerMake;
    }

    public void setSpeakerMake(String speakerMake) {
        this.speakerMake = speakerMake;
    }

    public String getSpeakerModel() {
        return speakerModel;
    }

    public void setSpeakerModel(String speakerModel) {
        this.speakerModel = speakerModel;
    }

    public String getSpeakerSerial() {
        return speakerSerial;
    }

    public void setSpeakerSerial(String speakerSerial) {
        this.speakerSerial = speakerSerial;
    }

    public Date getSpeakerDateOfPurchase() {
        return speakerDateOfPurchase;
    }

    public void setSpeakerDateOfPurchase(Date speakerDateOfPurchase) {
        this.speakerDateOfPurchase = speakerDateOfPurchase;
    }

    public Double getSpeakerCost() {
        return speakerCost;
    }

    public void setSpeakerCost(Double speakerCost) {
        this.speakerCost = speakerCost;
    }

    public String getUpsMake() {
        return upsMake;
    }

    public void setUpsMake(String upsMake) {
        this.upsMake = upsMake;
    }

    public String getUpsModel() {
        return upsModel;
    }

    public void setUpsModel(String upsModel) {
        this.upsModel = upsModel;
    }

    public String getUpsSerial() {
        return upsSerial;
    }

    public void setUpsSerial(String upsSerial) {
        this.upsSerial = upsSerial;
    }

    public Date getUpsDateOfPurchase() {
        return upsDateOfPurchase;
    }

    public void setUpsDateOfPurchase(Date upsDateOfPurchase) {
        this.upsDateOfPurchase = upsDateOfPurchase;
    }

    public Double getUpsCost() {
        return upsCost;
    }

    public void setUpsCost(Double upsCost) {
        this.upsCost = upsCost;
    }

    public String getStabilizerMake() {
        return stabilizerMake;
    }

    public void setStabilizerMake(String stabilizerMake) {
        this.stabilizerMake = stabilizerMake;
    }

    public String getStabilizerModel() {
        return stabilizerModel;
    }

    public void setStabilizerModel(String stabilizerModel) {
        this.stabilizerModel = stabilizerModel;
    }

    public String getStabilizerSerial() {
        return stabilizerSerial;
    }

    public void setStabilizerSerial(String stabilizerSerial) {
        this.stabilizerSerial = stabilizerSerial;
    }

    public Date getStabilizerDateOfPurchase() {
        return stabilizerDateOfPurchase;
    }

    public void setStabilizerDateOfPurchase(Date stabilizerDateOfPurchase) {
        this.stabilizerDateOfPurchase = stabilizerDateOfPurchase;
    }

    public Double getStabilizerCost() {
        return stabilizerCost;
    }

    public void setStabilizerCost(Double stabilizerCost) {
        this.stabilizerCost = stabilizerCost;
    }

    public String getCablesMake() {
        return cablesMake;
    }

    public void setCablesMake(String cablesMake) {
        this.cablesMake = cablesMake;
    }

    public String getCablesDescription() {
        return cablesDescription;
    }

    public void setCablesDescription(String cablesDescription) {
        this.cablesDescription = cablesDescription;
    }

    public String getCablesModel() {
        return cablesModel;
    }

    public void setCablesModel(String cablesModel) {
        this.cablesModel = cablesModel;
    }

    public String getCablesSerial() {
        return cablesSerial;
    }

    public void setCablesSerial(String cablesSerial) {
        this.cablesSerial = cablesSerial;
    }

    public Date getCablesDateOfPurchase() {
        return cablesDateOfPurchase;
    }

    public void setCablesDateOfPurchase(Date cablesDateOfPurchase) {
        this.cablesDateOfPurchase = cablesDateOfPurchase;
    }

    public Double getCablesCost() {
        return cablesCost;
    }

    public void setCablesCost(Double cablesCost) {
        this.cablesCost = cablesCost;
    }

    public String getOthersMake() {
        return othersMake;
    }

    public void setOthersMake(String othersMake) {
        this.othersMake = othersMake;
    }

    public String getOthersDescription() {
        return othersDescription;
    }

    public void setOthersDescription(String othersDescription) {
        this.othersDescription = othersDescription;
    }

    public String getOthersModel() {
        return othersModel;
    }

    public void setOthersModel(String othersModel) {
        this.othersModel = othersModel;
    }

    public String getOthersSerial() {
        return othersSerial;
    }

    public void setOthersSerial(String othersSerial) {
        this.othersSerial = othersSerial;
    }

    public Date getOthersDateOfPurchase() {
        return othersDateOfPurchase;
    }

    public void setOthersDateOfPurchase(Date othersDateOfPurchase) {
        this.othersDateOfPurchase = othersDateOfPurchase;
    }

    public Double getOthersCost() {
        return othersCost;
    }

    public void setOthersCost(Double othersCost) {
        this.othersCost = othersCost;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Double getOperatingSystemCost() {
        return operatingSystemCost;
    }

    public void setOperatingSystemCost(Double operatingSystemCost) {
        this.operatingSystemCost = operatingSystemCost;
    }

    public Boolean getInternetEmail() {
        return internetEmail;
    }

    public void setInternetEmail(Boolean internetEmail) {
        this.internetEmail = internetEmail;
    }

    public Date getInternetEmailDateOfPurchase() {
        return internetEmailDateOfPurchase;
    }

    public void setInternetEmailDateOfPurchase(Date internetEmailDateOfPurchase) {
        this.internetEmailDateOfPurchase = internetEmailDateOfPurchase;
    }

    public Double getInternetEmailCost() {
        return internetEmailCost;
    }

    public void setInternetEmailCost(Double internetEmailCost) {
        this.internetEmailCost = internetEmailCost;
    }

    public String getAdditionalSoftware1Description() {
        return additionalSoftware1Description;
    }

    public void setAdditionalSoftware1Description(String additionalSoftware1Description) {
        this.additionalSoftware1Description = additionalSoftware1Description;
    }

    public Date getAdditionalSoftware1DateOfPurchase() {
        return additionalSoftware1DateOfPurchase;
    }

    public void setAdditionalSoftware1DateOfPurchase(Date additionalSoftware1DateOfPurchase) {
        this.additionalSoftware1DateOfPurchase = additionalSoftware1DateOfPurchase;
    }

    public Double getAdditionalSoftware1Cost() {
        return additionalSoftware1Cost;
    }

    public void setAdditionalSoftware1Cost(Double additionalSoftware1Cost) {
        this.additionalSoftware1Cost = additionalSoftware1Cost;
    }

    public String getAdditionalSoftware2Description() {
        return additionalSoftware2Description;
    }

    public void setAdditionalSoftware2Description(String additionalSoftware2Description) {
        this.additionalSoftware2Description = additionalSoftware2Description;
    }

    public Date getAdditionalSoftware2DateOfPurchase() {
        return additionalSoftware2DateOfPurchase;
    }

    public void setAdditionalSoftware2DateOfPurchase(Date additionalSoftware2DateOfPurchase) {
        this.additionalSoftware2DateOfPurchase = additionalSoftware2DateOfPurchase;
    }

    public Double getAdditionalSoftware2Cost() {
        return additionalSoftware2Cost;
    }

    public void setAdditionalSoftware2Cost(Double additionalSoftware2Cost) {
        this.additionalSoftware2Cost = additionalSoftware2Cost;
    }

    public String getAdditionalSoftware3Description() {
        return additionalSoftware3Description;
    }

    public void setAdditionalSoftware3Description(String additionalSoftware3Description) {
        this.additionalSoftware3Description = additionalSoftware3Description;
    }

    public Date getAdditionalSoftware3DateOfPurchase() {
        return additionalSoftware3DateOfPurchase;
    }

    public void setAdditionalSoftware3DateOfPurchase(Date additionalSoftware3DateOfPurchase) {
        this.additionalSoftware3DateOfPurchase = additionalSoftware3DateOfPurchase;
    }

    public Double getAdditionalSoftware3Cost() {
        return additionalSoftware3Cost;
    }

    public void setAdditionalSoftware3Cost(Double additionalSoftware3Cost) {
        this.additionalSoftware3Cost = additionalSoftware3Cost;
    }

    public String getAdditionalSoftware4Description() {
        return additionalSoftware4Description;
    }

    public void setAdditionalSoftware4Description(String additionalSoftware4Description) {
        this.additionalSoftware4Description = additionalSoftware4Description;
    }

    public Date getAdditionalSoftware4DateOfPurchase() {
        return additionalSoftware4DateOfPurchase;
    }

    public void setAdditionalSoftware4DateOfPurchase(Date additionalSoftware4DateOfPurchase) {
        this.additionalSoftware4DateOfPurchase = additionalSoftware4DateOfPurchase;
    }

    public Double getAdditionalSoftware4Cost() {
        return additionalSoftware4Cost;
    }

    public void setAdditionalSoftware4Cost(Double additionalSoftware4Cost) {
        this.additionalSoftware4Cost = additionalSoftware4Cost;
    }

    public String getAdditionalSoftware5Description() {
        return additionalSoftware5Description;
    }

    public void setAdditionalSoftware5Description(String additionalSoftware5Description) {
        this.additionalSoftware5Description = additionalSoftware5Description;
    }

    public Date getAdditionalSoftware5DateOfPurchase() {
        return additionalSoftware5DateOfPurchase;
    }

    public void setAdditionalSoftware5DateOfPurchase(Date additionalSoftware5DateOfPurchase) {
        this.additionalSoftware5DateOfPurchase = additionalSoftware5DateOfPurchase;
    }

    public Double getAdditionalSoftware5Cost() {
        return additionalSoftware5Cost;
    }

    public void setAdditionalSoftware5Cost(Double additionalSoftware5Cost) {
        this.additionalSoftware5Cost = additionalSoftware5Cost;
    }

    public String getAdditionalSoftware6Description() {
        return additionalSoftware6Description;
    }

    public void setAdditionalSoftware6Description(String additionalSoftware6Description) {
        this.additionalSoftware6Description = additionalSoftware6Description;
    }

    public Date getAdditionalSoftware6DateOfPurchase() {
        return additionalSoftware6DateOfPurchase;
    }

    public void setAdditionalSoftware6DateOfPurchase(Date additionalSoftware6DateOfPurchase) {
        this.additionalSoftware6DateOfPurchase = additionalSoftware6DateOfPurchase;
    }

    public Double getAdditionalSoftware6Cost() {
        return additionalSoftware6Cost;
    }

    public void setAdditionalSoftware6Cost(Double additionalSoftware6Cost) {
        this.additionalSoftware6Cost = additionalSoftware6Cost;
    }

    public String getAdditionalSoftware7Description() {
        return additionalSoftware7Description;
    }

    public void setAdditionalSoftware7Description(String additionalSoftware7Description) {
        this.additionalSoftware7Description = additionalSoftware7Description;
    }

    public Date getAdditionalSoftware7DateOfPurchase() {
        return additionalSoftware7DateOfPurchase;
    }

    public void setAdditionalSoftware7DateOfPurchase(Date additionalSoftware7DateOfPurchase) {
        this.additionalSoftware7DateOfPurchase = additionalSoftware7DateOfPurchase;
    }

    public Double getAdditionalSoftware7Cost() {
        return additionalSoftware7Cost;
    }

    public void setAdditionalSoftware7Cost(Double additionalSoftware7Cost) {
        this.additionalSoftware7Cost = additionalSoftware7Cost;
    }

    public String getAdditionalSoftware8Description() {
        return additionalSoftware8Description;
    }

    public void setAdditionalSoftware8Description(String additionalSoftware8Description) {
        this.additionalSoftware8Description = additionalSoftware8Description;
    }

    public Date getAdditionalSoftware8DateOfPurchase() {
        return additionalSoftware8DateOfPurchase;
    }

    public void setAdditionalSoftware8DateOfPurchase(Date additionalSoftware8DateOfPurchase) {
        this.additionalSoftware8DateOfPurchase = additionalSoftware8DateOfPurchase;
    }

    public Double getAdditionalSoftware8Cost() {
        return additionalSoftware8Cost;
    }

    public void setAdditionalSoftware8Cost(Double additionalSoftware8Cost) {
        this.additionalSoftware8Cost = additionalSoftware8Cost;
    }

    public String getAdditionalSoftware9Description() {
        return additionalSoftware9Description;
    }

    public void setAdditionalSoftware9Description(String additionalSoftware9Description) {
        this.additionalSoftware9Description = additionalSoftware9Description;
    }

    public Date getAdditionalSoftware9DateOfPurchase() {
        return additionalSoftware9DateOfPurchase;
    }

    public void setAdditionalSoftware9DateOfPurchase(Date additionalSoftware9DateOfPurchase) {
        this.additionalSoftware9DateOfPurchase = additionalSoftware9DateOfPurchase;
    }

    public Double getAdditionalSoftware9Cost() {
        return additionalSoftware9Cost;
    }

    public void setAdditionalSoftware9Cost(Double additionalSoftware9Cost) {
        this.additionalSoftware9Cost = additionalSoftware9Cost;
    }

    public String getAdditionalSoftware10Description() {
        return additionalSoftware10Description;
    }

    public void setAdditionalSoftware10Description(String additionalSoftware10Description) {
        this.additionalSoftware10Description = additionalSoftware10Description;
    }

    public Date getAdditionalSoftware10DateOfPurchase() {
        return additionalSoftware10DateOfPurchase;
    }

    public void setAdditionalSoftware10DateOfPurchase(Date additionalSoftware10DateOfPurchase) {
        this.additionalSoftware10DateOfPurchase = additionalSoftware10DateOfPurchase;
    }

    public Double getAdditionalSoftware10Cost() {
        return additionalSoftware10Cost;
    }

    public void setAdditionalSoftware10Cost(Double additionalSoftware10Cost) {
        this.additionalSoftware10Cost = additionalSoftware10Cost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Institution getMaintainedBy() {
        return maintainedBy;
    }

    public void setMaintainedBy(Institution maintainedBy) {
        this.maintainedBy = maintainedBy;
    }

    public String getImportantInformation() {
        return importantInformation;
    }

    public void setImportantInformation(String importantInformation) {
        this.importantInformation = importantInformation;
    }

    public Date getWarrantyValidTill() {
        return warrantyValidTill;
    }

    public void setWarrantyValidTill(Date warrantyValidTill) {
        this.warrantyValidTill = warrantyValidTill;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Computer)) {
            return false;
        }
        Computer other = (Computer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.gov.health.entity.Computer[ id=" + id + " ]";
    }

    public Double getScannerCost() {
        return scannerCost;
    }

    public void setScannerCost(Double scannerCost) {
        this.scannerCost = scannerCost;
    }

}
