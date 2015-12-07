package com.mzhang.ligneAzur;

public class Station {
    // e.g. 1,2,10
    private int qrcode;

    private String stationName;

    public int getQrcode() {
	return qrcode;
    }

    public void setQrcode(int qrcode) {
	this.qrcode = qrcode;
    }

    public String getStationName() {
	return stationName;
    }

    public void setStationName(String stationName) {
	this.stationName = stationName;
    }

    public Station(int qrcode, String stationName) {
	this.qrcode = qrcode;
	this.stationName = stationName;
    }

    @Override
    public String toString() {
	// TODO Auto-generated method stub
	return this.stationName + "\thttp://cg06.tsi.cityway.fr/qrcode/" + this.qrcode;
    }
}
