package com.mzhang.sae.mysql;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sina.sae.util.SaeUserInfo;

public class Datasource {
    private static Datasource datasource;
    private ComboPooledDataSource cpds;

    private Datasource() throws IOException, SQLException, PropertyVetoException {
	cpds = new ComboPooledDataSource();
	cpds.setDriverClass("com.mysql.jdbc.Driver");

	// sae
	cpds.setJdbcUrl("jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_qiuyu8290");
	cpds.setUser(SaeUserInfo.getAccessKey());
	cpds.setPassword(SaeUserInfo.getSecretKey());

	// local
	// cpds.setJdbcUrl("jdbc:mysql://localhost:3306/app_qiuyu8290");
	// cpds.setUser("root");
	// cpds.setPassword("admin");

	// the settings below are optional -- c3p0 can work with defaults
	cpds.setMinPoolSize(5);
	cpds.setMaxPoolSize(20);

	cpds.setMaxStatements(200);
	cpds.setInitialPoolSize(5);
	cpds.setMaxIdleTime(15);
	cpds.setIdleConnectionTestPeriod(5);
	cpds.setTestConnectionOnCheckin(false);
	cpds.setTestConnectionOnCheckout(false);
	cpds.setPreferredTestQuery("SELECT 1 FROM rmbCurrency");
    }

    public static Datasource getInstance() throws IOException, SQLException, PropertyVetoException {
	if (datasource == null) {
	    datasource = new Datasource();
	    return datasource;
	} else {
	    return datasource;
	}
    }

    public Connection getConnection() throws SQLException {
	return this.cpds.getConnection();
    }

}
