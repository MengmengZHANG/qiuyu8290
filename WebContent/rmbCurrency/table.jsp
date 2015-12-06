<%@page import="com.mzhang.sae.mysql.RmbCurrency"%>
<%@page import="java.util.List"%>
<%@page import="com.mzhang.sae.mysql.RmbCurrencyDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<title>RmbCurrencyTable</title>
</head>
<body>
<div class="container">
<table class="table">
<thead>
  <tr>
    <th>Date</th>
    <th>Currency</th>
    <th>GovIntermediatePrice</th>
    <th>
  </tr>
</thead>
<tbody>
<%
	RmbCurrencyDAO dao = new RmbCurrencyDAO();
	List<RmbCurrency> results=  dao.findAll(com.mzhang.sae.mysql.Currency.USD);
	for(RmbCurrency rmbCurrency: results){
	    out.println("<tr><td>" + rmbCurrency.getDate() + "</td><td>"+rmbCurrency.getCurrency() + "</td><td>" + rmbCurrency.getGovIntermediatePrice() + "</td><tr>");
	}
%>
</tbody>
</table>
</body>
</html>