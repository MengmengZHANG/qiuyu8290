package com.mzhang.ligneAzur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mzhang.sae.mysql.Datasource;

public class StationDAO {
    public List<Station> findAll() {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	List<Station> result = null;
	try {
	    connection = Datasource.getInstance().getConnection();
	    String updateString = "SELECT qrcode,stationName FROM station";
	    statement = connection.prepareStatement(updateString);
	    resultSet = statement.executeQuery();
	    result = new ArrayList<Station>();
	    while (resultSet.next()) {
		int qrcode = resultSet.getInt("qrcode");
		String stationName = resultSet.getString("stationName");
		Station item = new Station(qrcode, stationName);
		result.add(item);
	    }
	} catch (Exception e) {
	    System.out.println(e);
	} finally {
	    close(connection, statement, resultSet);
	}
	return result;
    }

    public boolean exist(Station station) {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	boolean isExist = false;

	try {
	    connection = Datasource.getInstance().getConnection();
	    String updateString = "SELECT EXISTS(SELECT * FROM station WHERE qrcode = ? AND stationName = ?)";
	    statement = connection.prepareStatement(updateString);
	    statement.setInt(1, station.getQrcode());
	    statement.setString(2, station.getStationName());
	    resultSet = statement.executeQuery();

	    if (resultSet.next()) {
		if (resultSet.getInt(1) != 0)
		    isExist = true;
	    }
	} catch (Exception e) {
	    System.out.println(e);
	} finally {
	    close(connection, statement, resultSet);
	    return isExist;
	}
    }

    public void insert(Station station) {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	try {
	    connection = Datasource.getInstance().getConnection();
	    String updateString = "INSERT INTO station (qrcode,stationName) VALUES (?, ?);";
	    statement = connection.prepareStatement(updateString);
	    statement.setInt(1, station.getQrcode());
	    statement.setString(2, station.getStationName());
	    statement.executeUpdate();
	} catch (Exception e) {
	    System.out.println(e);
	} finally {
	    close(connection, statement, resultSet);
	}
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
}
