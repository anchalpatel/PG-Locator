package com.example.pgproject;

public class PGDetailsModel {
    String id;
    String pName;
    String forBoys;
    String forGirls;
    String pgCollege;
    String pgUniversity;
    String pgPrice;
    String pgAddress;
    String pgNumber;

    String image;

    String person;
    String food;
    String WashingMachine;
    String ac;
    String wifi;
    String singleBed;
    String doubleBed;
    String multipleBed;

    public PGDetailsModel(String id, String pName, String forBoys, String forGirls, String pgCollege, String pgUniversity, String pgPrice, String pgAddress, String pgNumber, String image, String person, String food, String washingMachine, String ac, String wifi, String singleBed, String doubleBed, String multipleBed) {
        this.id = id;
        this.pName = pName;
        this.forBoys = forBoys;
        this.forGirls = forGirls;
        this.pgCollege = pgCollege;
        this.pgUniversity = pgUniversity;
        this.pgPrice = pgPrice;
        this.pgAddress = pgAddress;
        this.pgNumber = pgNumber;
        this.image = image;
        this.person = person;
        this.food = food;
        WashingMachine = washingMachine;
        this.ac = ac;
        this.wifi = wifi;
        this.singleBed = singleBed;
        this.doubleBed = doubleBed;
        this.multipleBed = multipleBed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getForBoys() {
        return forBoys;
    }

    public void setForBoys(String forBoys) {
        this.forBoys = forBoys;
    }

    public String getForGirls() {
        return forGirls;
    }

    public void setForGirls(String forGirls) {
        this.forGirls = forGirls;
    }

    public String getPgCollege() {
        return pgCollege;
    }

    public void setPgCollege(String pgCollege) {
        this.pgCollege = pgCollege;
    }

    public String getPgUniversity() {
        return pgUniversity;
    }

    public void setPgUniversity(String pgUniversity) {
        this.pgUniversity = pgUniversity;
    }

    public String getPgPrice() {
        return pgPrice;
    }

    public void setPgPrice(String pgPrice) {
        this.pgPrice = pgPrice;
    }

    public String getPgAddress() {
        return pgAddress;
    }

    public void setPgAddress(String pgAddress) {
        this.pgAddress = pgAddress;
    }

    public String getPgNumber() {
        return pgNumber;
    }

    public void setPgNumber(String pgNumber) {
        this.pgNumber = pgNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getWashingMachine() {
        return WashingMachine;
    }

    public void setWashingMachine(String washingMachine) {
        WashingMachine = washingMachine;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getSingleBed() {
        return singleBed;
    }

    public void setSingleBed(String singleBed) {
        this.singleBed = singleBed;
    }

    public String getDoubleBed() {
        return doubleBed;
    }

    public void setDoubleBed(String doubleBed) {
        this.doubleBed = doubleBed;
    }

    public String getMultipleBed() {
        return multipleBed;
    }

    public void setMultipleBed(String multipleBed) {
        this.multipleBed = multipleBed;
    }
}
