package com.mzhang.rmbCurrency;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mzhang.sae.mysql.Datasource;

public class RmbCurrencyDAO {
    // date format: YYYY-MM-DD
    public List<RmbCurrency> findAll(Currency currency) {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	List<RmbCurrency> result = null;
	try {
	    connection = Datasource.getInstance().getConnection();
	    String updateString = "SELECT date,govIntermediatePrice FROM rmbCurrency WHERE currency = ?";
	    statement = connection.prepareStatement(updateString);
	    statement.setString(1, currency.toString());
	    resultSet = statement.executeQuery();
	    result = new ArrayList<RmbCurrency>();
	    while (resultSet.next()) {
		String date = resultSet.getString("date");
		Float govIntermediatePrice = resultSet.getFloat("govIntermediatePrice");
		RmbCurrency item = new RmbCurrency(date, currency, govIntermediatePrice);
		result.add(item);
	    }
	} catch (Exception e) {
	    System.out.println(e);
	} finally {
	    close(connection, statement, resultSet);
	}
	return result;
    }

    private void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
	if (resultSet != null)
	    try {
		resultSet.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	if (statement != null)
	    try {
		statement.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	if (connection != null)
	    try {
		connection.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
    }

    public List<RmbCurrency> find(String dateFrom, String dateTo, Currency currency)
	    throws IOException, SQLException, PropertyVetoException {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	List<RmbCurrency> result = null;
	try {
	    connection = Datasource.getInstance().getConnection();
	    String updateString = "SELECT date,govIntermediatePrice FROM rmbCurrency WHERE currency = ? AND date >= ? AND date <= ?";
	    statement = connection.prepareStatement(updateString);
	    statement.setString(1, currency.toString());
	    statement.setString(2, dateFrom);
	    statement.setString(3, dateTo);
	    resultSet = statement.executeQuery();
	    result = new ArrayList<RmbCurrency>();
	    while (resultSet.next()) {
		String date = resultSet.getString("date");
		Float govIntermediatePrice = resultSet.getFloat("govIntermediatePrice");
		RmbCurrency item = new RmbCurrency(date, currency, govIntermediatePrice);
		result.add(item);
	    }
	} catch (Exception e) {
	    System.out.println(e);
	} finally {
	    close(connection, statement, resultSet);
	}

	return result;
    }

    public RmbCurrency find(String date, Currency currency) throws SQLException, IOException, PropertyVetoException {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	RmbCurrency result = null;
	try {
	    connection = Datasource.getInstance().getConnection();
	    String updateString = "SELECT govIntermediatePrice FROM rmbCurrency WHERE currency = ? AND date = ?";
	    statement = connection.prepareStatement(updateString);
	    statement.setString(1, currency.toString());
	    statement.setString(2, date);
	    resultSet = statement.executeQuery();

	    if (resultSet.next()) {
		Float govIntermediatePrice = resultSet.getFloat("govIntermediatePrice");
		result = new RmbCurrency(date, currency, govIntermediatePrice);
	    }
	} catch (Exception e) {
	    System.out.println(e);

	} finally {
	    close(connection, statement, resultSet);
	}
	return result;
    }

    public boolean exist(String date, Currency currency) throws SQLException, IOException, PropertyVetoException {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	boolean result = false;
	try {
	    connection = Datasource.getInstance().getConnection();
	    String updateString = "SELECT EXISTS(SELECT * FROM rmbCurrency WHERE currency = ? AND date = ?)";
	    statement = connection.prepareStatement(updateString);
	    statement.setString(1, currency.toString());
	    statement.setString(2, date);
	    resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		result = true;
	    }
	} catch (Exception e) {
	    System.out.println(e);

	} finally {
	    close(connection, statement, resultSet);
	}
	return result;
    }

    public void insert(RmbCurrency rmbCurrency) throws SQLException, IOException, PropertyVetoException {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	try {
	    connection = Datasource.getInstance().getConnection();
	    String sql = "INSERT INTO rmbCurrency (date,currency, govIntermediatePrice) VALUES (?,?,?);";
	    statement = connection.prepareStatement(sql);
	    statement.setString(1, rmbCurrency.getDate());
	    statement.setString(2, rmbCurrency.getCurrency().toString());
	    statement.setFloat(3, rmbCurrency.getGovIntermediatePrice());
	    statement.executeUpdate();
	} catch (Exception e) {
	    System.out.println(e);
	} finally {
	    close(connection, statement, resultSet);
	}
    }
}
