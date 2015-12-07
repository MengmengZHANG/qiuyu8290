<%@page import="java.util.List"%>
<%@page import="com.mzhang.ligneAzur.StationDAO"%>
<%@page import="com.mzhang.ligneAzur.Station"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<title>Ligne d'azur horaire</title>
</head>
<body>
<div class="container">
<table class="table">
<caption>Check realtime timetable for buses in 06.</caption>
<thead>
  <tr>
    <th>StationName</th>
  </tr>
</thead>
<tbody>
<%
	StationDAO dao = new StationDAO();
	List<Station> results=  dao.findAll();
	for(Station station: results){
	    out.println("<tr><td><a href='http://cg06.tsi.cityway.fr/qrcode/" +station.getQrcode()+"'>" + station.getStationName() + "</a></td><tr>");
	}
%>
</tbody>
</table>
</body>
</html>