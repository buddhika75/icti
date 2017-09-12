/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.entity;

/**
 *
 * @author User
 */
public class ComputerSummery {
    Institution institution;
    Institution department;
    Long numberOfComputers;
    Double valueOfComputers;
    int id;

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

    public Long getNumberOfComputers() {
        return numberOfComputers;
    }

    public void setNumberOfComputers(Long numberOfComputers) {
        this.numberOfComputers = numberOfComputers;
    }

    public Double getValueOfComputers() {
        return valueOfComputers;
    }

    public void setValueOfComputers(Double valueOfComputers) {
        this.valueOfComputers = valueOfComputers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
