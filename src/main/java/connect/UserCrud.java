//package connect;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * @author sqlitetutorial.net
// */
//public class UserCrud {
//
////    /**
////     * @param args the command line arguments
////     */
////    public static void main(String[] args) {
////
////        UserCrud app = new UserCrud();
////        // insert three new rows
////        app.insert("Raw Materials", 3000);
////        app.insert("Semifinished Goods", 4000);
////        app.insert("Finished Goods", 5000);
////    }
//
//
//    /**
//     * Insert a new row into the warehouses table
//     *
//     * @param username
//     * @param email
//     * @param password
//     */
//    public void register(String username, String email, String password) {
//        String sql = "INSERT INTO Users(username,email, password) VALUES(?,?, ?)";
//
//        try (Connection conn = DB.connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, username);
//            pstmt.setString(2, email);
//            pstmt.setString(3, password);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public Object login(String username, String password) {
//        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
//        Object checkUser = false;
//        ResultSet rs = null;
//
//        try (Connection conn = DB.connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, username);
//            pstmt.setString(2, password);
//            rs = pstmt.executeQuery();
//
//            if (rs.next())
//                checkUser = rs.next();
//
//            System.out.println(rs.getString("username"));
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return checkUser;
//    }
//
//    //check username
//    public boolean checkUsername(String username) {
//        String sql = "SELECT username FROM Users WHERE username = ?";
//        boolean checkUser = false;
//        ResultSet rs = null;
//
//        try (Connection conn = DB.connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, username);
//            rs = pstmt.executeQuery();
//
//            if (rs.next())
//                checkUser = true;
//
//            System.out.println(rs.getString("username"));
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return checkUser;
//    }
//
//    public boolean checkEmail(String email) {
//        String sql = "SELECT email FROM Users WHERE email = ?";
//        boolean checkUser = false;
//        ResultSet rs = null;
//
//        try (Connection conn = DB.connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, email);
//            rs = pstmt.executeQuery();
//
//            if (rs.next())
//                checkUser = true;
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return checkUser;
//    }
//}