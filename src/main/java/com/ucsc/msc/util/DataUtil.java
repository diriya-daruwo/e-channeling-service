package com.ucsc.msc.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * The utility class for create database connection.
 *
 * @author Lahiru Yapa
 */
public class DataUtil {

    private DataSource ds;

    public DataUtil() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/medicare");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public Connection getDataConnection() throws SQLException {
        return ds.getConnection();
    }

}
