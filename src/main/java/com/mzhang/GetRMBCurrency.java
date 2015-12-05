package com.mzhang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

  public static void main(String[] args) throws ParseException {
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate = format.parse("2015-09-01");
    Date endDate = format.parse("2015-09-01");

    scheduler.scheduleAtFixedRate(new GetRMBCurrency(startDate, endDate), 0, 10, TimeUnit.SECONDS);
  }

  public void run() {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String startDateStr = format.format(start);
      String endDateStr = format.format(end);
      String url = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action?projectBean.startDate=" +
          startDateStr + "&projectBean.endDate=" + endDateStr + "&queryYN=true";

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

        System.out.println(dateStr + "\t" + usdStr + "\t" + euroStr);
      }
    }
    catch (JauntException e) {
      System.err.println(e);
    }

  }
}