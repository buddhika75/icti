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
public class AreaSummery {
    int id;
    Area area;
    int count;
    int r;
    int b;
    int g;
    String rgb;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    
    
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public String getRgb() {
        rgb = String.format("#%02x%02x%02x", r, g, b); 
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }
    
    
}
