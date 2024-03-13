package com.example.pgproject;

public class PGDetailsModel {
    int id;
    String pName;
    String pgType;
    String pgCollege;
    String pgUniversity;
    int pgPrice;
    String pgAddress;
    long pgNumber;

    String image;
    int beds;
    int person;
    boolean food;
    boolean WashingMachine;
    boolean ac;
    boolean wifi;

    public String getPgAddress() {
        return pgAddress;
    }

    public void setPgAddress(String pgAddress) {
        this.pgAddress = pgAddress;
    }


    public PGDetailsModel(int id, String pName, String pgType, String pgCollege, String pgUniversity, int pgPrice, String pgAddress, long pgNumber, String image, int beds, int person, boolean food, boolean washingMachine, boolean ac, boolean wifi) {
        this.id = id;
        this.pName = pName;
        this.pgType = pgType;
        this.pgCollege = pgCollege;
        this.pgUniversity = pgUniversity;
        this.pgPrice = pgPrice;
        this.pgAddress = pgAddress;
        this.pgNumber = pgNumber;
        this.image = image;
        this.beds = beds;
        this.person = person;
        this.food = food;
        this.WashingMachine = washingMachine;
        this.ac = ac;
        this.wifi = wifi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPgType() {
        return pgType;
    }

    public void setPgType(String pgType) {
        this.pgType = pgType;
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

    public int getPgPrice() {
        return pgPrice;
    }

    public void setPgPrice(int pgPrice) {
        this.pgPrice = pgPrice;
    }

    public long getPgNumber() {
        return pgNumber;
    }

    public void setPgNumber(long pgNumber) {
        this.pgNumber = pgNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isWashingMachine() {
        return WashingMachine;
    }

    public void setWashingMachine(boolean washingMachine) {
        WashingMachine = washingMachine;
    }

    public boolean isAc() {
        return ac;
    }

    public void setAc(boolean ac) {
        this.ac = ac;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }



}
