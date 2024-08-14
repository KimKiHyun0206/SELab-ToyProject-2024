import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import org.junit.jupiter.api.Test;

@Slf4j
public class test {
    @Test
    public void testJDBCConnection() {
        String url = "jdbc:mysql://localhost:3306/quiz_db"; // 서버 주소
        String userName = "root"; //  접속자 id
        String password = "0000"; // 접속자 pw

        // JDBC 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("JDBC 드라이버를 로드하는데에 문제 발생", e);
        }

        // 접속
        try (Connection con = DriverManager.getConnection(url, userName, password)) {
            log.info("연결 완료!");
        } catch (SQLException e) {
            log.error("연결 오류 발생.", e);
        }
    }
}
