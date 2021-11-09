package com.Pos10Max.POS10APIMAX.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ino;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Column(nullable = false, unique = true)
    @NotNull
    private String iname;

    @NotNull
    private String iname1;

    @Column(columnDefinition = "double default '0'")
    @NotNull
    private double prate;

    @Column(columnDefinition = "double default '0'")
    @NotNull
    private int taxp;

    @Column(columnDefinition = "double default '0'")
    @NotNull
    private double mrp;

    @Column(columnDefinition = "double default '0'")
    @NotNull
    private double rprice;

    @Column(columnDefinition = "double default '0'")
    @NotNull
    private double wprice;

    @Column(columnDefinition = "varchar(255) default '.'")
    private String cat;

    @Column(columnDefinition = "varchar(255) default '.'")
    private String menu;

    @Column(columnDefinition = "varchar(255) default '.'")
    private String hsn;

    @Column(columnDefinition = "varchar(255) default '.'")
    private String udes;

    @Column(columnDefinition = "double default '0'")
    private double minstock;

    @Column(columnDefinition = "double default '0'")
    private double maxstock;

    @Column(columnDefinition = "varchar(255) default 'null'")
    private String rack;

    @Column(columnDefinition = "double default '0'")
    private double disp;

    @Column(columnDefinition = "double default '0'")
    private double ostock;

    public long getIno() {
        return ino;
    }

    public void setIno(long ino) {
        this.ino = ino;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getIname1() {
        return iname1;
    }

    public void setIname1(String iname1) {
        this.iname1 = iname1;
    }

    public double getPrate() {
        return prate;
    }

    public void setPrate(double prate) {
        this.prate = prate;
    }

    public int getTaxp() {
        return taxp;
    }

    public void setTaxp(int taxp) {
        this.taxp = taxp;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getRprice() {
        return rprice;
    }

    public void setRprice(double rprice) {
        this.rprice = rprice;
    }

    public double getWprice() {
        return wprice;
    }

    public void setWprice(double wprice) {
        this.wprice = wprice;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public String getUdes() {
        return udes;
    }

    public void setUdes(String udes) {
        this.udes = udes;
    }

    public double getMinstock() {
        return minstock;
    }

    public void setMinstock(double minstock) {
        this.minstock = minstock;
    }

    public double getMaxstock() {
        return maxstock;
    }

    public void setMaxstock(double maxstock) {
        this.maxstock = maxstock;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public double getDisp() {
        return disp;
    }

    public void setDisp(double disp) {
        this.disp = disp;
    }

    public double getOstock() {
        return ostock;
    }

    public void setOstock(double ostock) {
        this.ostock = ostock;
    }
}
