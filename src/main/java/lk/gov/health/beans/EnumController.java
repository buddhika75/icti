/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.beans;

import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import lk.gov.health.entity.AreaType;
import lk.gov.health.entity.ComputerType;
import lk.gov.health.entity.HddType;
import lk.gov.health.entity.InstitutionType;
import lk.gov.health.entity.ItemType;
import lk.gov.health.entity.Month;
import lk.gov.health.entity.OperatingSystem;
import lk.gov.health.entity.PrivilegeType;
import lk.gov.health.entity.Processor;
import lk.gov.health.entity.PurchasedOrTransferred;
import lk.gov.health.entity.Quarter;
import lk.gov.health.entity.Ram;

/**
 *
 * @author User
 */
@Named(value = "enumController")
@ApplicationScoped
public class EnumController {

    /**
     * Creates a new instance of EnumController
     */
    public EnumController() {
    }

    public Month[] getMonths() {
        return Month.values();
    }

    public Quarter[] getQuarters() {
        return Quarter.values();
    }

    public AreaType[] getAreaTypes() {
        return AreaType.values();
    }
    
    
    public PrivilegeType[] getPrivilegeTypes() {
        return PrivilegeType.values();
    }

    public InstitutionType[] getInstitutionTypes() {
        return InstitutionType.values();
    }

    public ItemType[] getItemTypes() {
        return ItemType.values();
    }
    
   
    public PurchasedOrTransferred[] getPurchasedOrTransferreds(){
        return PurchasedOrTransferred.values();
    }
    
    public ComputerType[] getComputerTypes(){
        return ComputerType.values();
    }
    
    public HddType[] getHddTypes(){
        return HddType.values();
    }
    
    public OperatingSystem[] getOperatingSystems(){
        return OperatingSystem.values();
    }

    public Processor[] getProcessors(){
        return Processor.values();
    }
    
    public Ram[] getRams(){
        return Ram.values();
    }
}
