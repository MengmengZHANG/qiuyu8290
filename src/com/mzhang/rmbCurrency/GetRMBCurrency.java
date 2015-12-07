package com.mzhang.rmbCurrency;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import com.jaunt.component.Table;

public class GetRMBCurrency implements Runnable {
    private Date start;

    private Date end;

    public GetRMBCurrency(Date start, Date end) {
	this.start = start;
	this.end = end;
    }

    // http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action?projectBean.startDate=2015-09-01&projectBean.endDate=2015-11-06&queryYN=true
    public static void main(String[] args) throws ParseException {
	final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	GregorianCalendar gcal = new GregorianCalendar();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Date startDate = format.parse("2005-01-01");
	Date end = format.parse("2016-01-01");
	gcal.setTime(startDate);

	while (gcal.getTime().before(end)) {
	    Date from = gcal.getTime();
	    gcal.add(Calendar.DAY_OF_YEAR, 60);
	    Date to = gcal.getTime();
	    new GetRMBCurrency(from, to).run();
	}
	// scheduler.scheduleAtFixedRate(new GetRMBCurrency(startDate, endDate),
	// 0, 10, TimeUnit.SECONDS);
    }

    public void run() {
	try {
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    String startDateStr = format.format(start);
	    String endDateStr = format.format(end);
	    String url = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action?projectBean.startDate="
		    + startDateStr + "&projectBean.endDate=" + endDateStr + "&queryYN=true";

	    UserAgent userAgent = new UserAgent();

	    userAgent.visit(url);

	    Table table = userAgent.doc.getTable("<table id=InfoTable>");
	    if (table == null) {
		return;
	    }
	    System.out.println("Date\t\tusd\teuro");
	    Elements rows = table.getCol(1);
	    int rowSize = rows.size();

	    for (int rowIndex = 1; rowIndex < rowSize; rowIndex++) {
		Element date = table.getCell(0, rowIndex);
		String dateStr = date.innerHTML().toString().replaceAll("\\s+", "").replaceAll("&nbsp;", "");

		Element usd = table.getCell(1, rowIndex);
		String usdStr = usd.innerHTML().toString().replaceAll("\\s+", "").replaceAll("&nbsp;", "");

		Element euro = table.getCell(2, rowIndex);
		String euroStr = euro.innerHTML().toString().replaceAll("\\s+", "").replaceAll("&nbsp;", "");

		RmbCurrency rmbEURCurrency = new RmbCurrency(dateStr, Currency.EUR, Float.parseFloat(euroStr));
		RmbCurrency rmbUSDCurrency = new RmbCurrency(dateStr, Currency.USD, Float.parseFloat(usdStr));

		RmbCurrencyDAO dao = new RmbCurrencyDAO();
		if (!dao.exist(dateStr, Currency.EUR)) {
		    new RmbCurrencyDAO().insert(rmbEURCurrency);
		    System.out.println(dateStr + "\tEUR\t" + euroStr);
		}
		if (!dao.exist(dateStr, Currency.USD)) {
		    new RmbCurrencyDAO().insert(rmbUSDCurrency);
		    System.out.println(dateStr + "\tUSD\t" + usdStr);
		}
	    }
	} catch (JauntException | SQLException | IOException | PropertyVetoException e) {
	    System.err.println(e);
	}

    }
}