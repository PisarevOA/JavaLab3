package lab.dao;

import lab.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionBuilder {
    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return con;

//        String RulesConn = "jdbc:postgresql://ulnp1umgzuhuwofu8l1r:qO1YtsumNhycV39jutrMiUCSbaJrOS@b2u2bvuvyxjj1rmlp22u-postgresql.services.clever-cloud.com:5432/b2u2bvuvyxjj1rmlp22u";
////        Properties prop = new Properties();
////        prop.setProperty("ssl","true");
////        prop.setProperty("sslmode","verify-full");
////        prop.setProperty("user","ulnp1umgzuhuwofu8l1r");
////        prop.setProperty("password","qO1YtsumNhycV39jutrMiUCSbaJrOS");
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            System.out.println("Connection");
//            return DriverManager.getConnection(RulesConn);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return null;
    }
}
