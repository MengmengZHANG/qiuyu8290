package com.mzhang.ligneAzur;

import java.text.ParseException;

import com.jaunt.Element;
import com.jaunt.UserAgent;

public class Test200 implements Runnable {
    private int qrcode;

    public Test200(int qrcode) {
	this.qrcode = qrcode;
    }

    // http://cg06.tsi.cityway.fr/qrcode/549
    public static void main(String[] args) throws ParseException, InterruptedException {
	for (int i = 1000; i <= 1100; i++) {
	    new Test200(i).run();
	    Thread.sleep(1000);
	}
    }

    public void run() {
	try {
	    String url = "http://cg06.tsi.cityway.fr/qrcode/" + this.qrcode;
	    UserAgent userAgent = new UserAgent();
	    userAgent.visit(url);

	    Element here = userAgent.doc.findFirst("<div class=here>");
	    if (here == null) {
		return;
	    }
	    String stationName = here.findFirst("span class=txtbold").getText();
	    if (" - ".equals(stationName))
		return;
	    Station station = new Station(qrcode, stationName);
	    StationDAO dao = new StationDAO();
	    if (!dao.exist(station)) {
		dao.insert(station);
		System.out.println(station);
	    }

	} catch (Exception e) {
	}
    }

}