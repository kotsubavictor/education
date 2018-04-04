package test;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;

public class H2SqlDB {
    public static void main(String[] args) throws Exception {
//
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test");
        conn.close();

//
        JdbcConnectionPool cp = JdbcConnectionPool.
                create("jdbc:h2:~/test", "sa", "sa");
        conn = cp.getConnection();
        conn.close();
        cp.dispose();
    }
}
