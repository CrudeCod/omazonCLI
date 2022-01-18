import connect.DB;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    // Basic info
    private String Username;
    private String Password;
    private String email;
    File Productfolder = new File("src/database/PRODUCTS/");
    //Customer
    private double balance;
    private Product[] cartProduct;
    private ArrayList<Order> orderHistory = new ArrayList<Order>();
    private Favorite favoriteList;
    private int paymentPassword;
    private int ProductCount = 0;
    // Seller
    private double profit;
    private Product[] productsList = new Product[100];
    // private Product[] productsList;
    private String[] transactionHistory;
    private String[] orderNotifications;
    private String[] shoppingCart = new String[100];

    private int productsInCart = 0;

    public User() {
    }

    public User(String Username, String Password, String email) {
        this.Username = Username;
        this.Password = Password;
        this.email = email;
        this.balance = 0;

        // this.cartProduct=cartProduct;
        // this.orderHistory=orderHistory;
        // this.paymentPassword=paymentPassword;
        // this.profit=profit;
        // this.productsList=productsList;
        // this.transactionHistory=transactionHistory;
        // this.orderNotifications=orderNotifications;
    }

    public static void initializeShoppingCart(String[] shoppingCart) {
        for (int i = 0; i < shoppingCart.length; i++) {
            shoppingCart[i] = "";
        }
    }

    public static void SaveToFile(User u) { // add filepath as a parameter
        try {
            FileOutputStream fileOut = new FileOutputStream("src/database/USERNAMES/" + u.Username);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(u);
            objectOut.close();
            System.out.println("Successfully written.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User ReadFromFile(String filepath) {
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            User u = (User) objectIn.readObject();
            // System.out.println("User successfully read from file.");
            objectIn.close();
            return u;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void initFavorite(String username) {
        Favorite fav = new Favorite(username);
        fav.saveToFile(fav);
    }

    public int getProductsInCart() {
        return productsInCart;
    }

    public void incrementProductsInCart() {
        this.productsInCart++;
    }

    public String[] getShoppingCart() {

        return this.shoppingCart;
    }

    public void setShoppingCart(String[] shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    // Basic info

    public String getUsername() {
        return Username;
    }

    public void setUsername(String newUsername) {
        this.Username = newUsername;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Customer

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
        SaveToFile(this);
    }

    public Product[] getCartProduct() {
        return cartProduct;
    }
    public void setCartProduct(Product[] cartProduct) {
        this.cartProduct = cartProduct;
        SaveToFile(this);
    }

    public void addOrderHistory(Order order) {
        this.orderHistory.add(order);
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    public int getPaymentPassword() {
        return paymentPassword;
    }
    public void setPaymentPassword(int paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    // Seller

    public double getProfit() {
        return profit;
    }
    public void setProfit(double profit) {
        this.profit = profit;
        SaveToFile(this);
    }

    public Product[] getProductsList() {
        return productsList;
    }
    public void setProductsList(Product p) {
        this.productsList[ProductCount]=p;
        SaveToFile(this);
    }

    public String[] getTransactionHistory() {
        return transactionHistory;
    }
    public void setTransactionHistory(String[] transactionHistory) {
        this.transactionHistory = transactionHistory;
        SaveToFile(this);
    }

    public String[] getOrderNotifications() {
        return orderNotifications;
    }
    public void setOrderNotifications(String[] orderNotifications) {
        this.orderNotifications = orderNotifications;
        SaveToFile(this);
    }

    public int getProductCount() {
        return ProductCount;
    }
    public void setProductCount(int productCount) {
        ProductCount = productCount;
        SaveToFile(this);
    }

    public void register(String username, String email, String password, int paymentPassword) {
        String sql = "INSERT INTO Users(username, email, password, payment_password) VALUES(?,?,?,?)";

        try (Connection conn = DB.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setInt(4, paymentPassword);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        ResultSet rs = null;

        try (Connection conn = DB.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                this.Username = rs.getString("username");
                this.email = rs.getString("email");
                this.Password = rs.getString("password");
                this.paymentPassword = rs.getInt("payment_password");
                this.balance = rs.getDouble("credit_balance");
                return this;
            } else {
                System.out.println("Wrong username or password! Please try again.\n");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // check username
    public boolean checkUsername(String username) {
        String sql = "SELECT username FROM Users WHERE username = ?";
        boolean checkUser = false;
        ResultSet rs = null;

        try (Connection conn = DB.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next())
                checkUser = true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return checkUser;
    }

    public boolean checkEmail(String email) {
        String sql = "SELECT email FROM Users WHERE email = ?";
        boolean checkUser = false;
        ResultSet rs = null;

        try (Connection conn = DB.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next())
                checkUser = true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return checkUser;
    }

    public void topUpBalance(double balance) {
        this.balance += balance;

        String sql = "UPDATE Users SET credit_balance = ? WHERE username = ?";

        try (Connection conn = DB.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, this.balance);
            pstmt.setString(2, this.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAccount(String username, String email, String password) {

    }

    public void updateUsername(String username) {
        String sql = "UPDATE Users SET username = ? WHERE username = ?";

        try (Connection conn = DB.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, this.getUsername());
            this.setUsername(username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePassword(String password) {
        String sql = "UPDATE Users SET password = ? WHERE username = ?";

        try (Connection conn = DB.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, password);
            pstmt.setString(2, this.getUsername());
            this.setPassword(password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePaymentPassword(int password) {
        String sql = "UPDATE Users SET payment_password = ? WHERE username = ?";

        try (Connection conn = DB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, password);
            pstmt.setString(2, this.getUsername());
            this.setPaymentPassword(password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateEmail(String email) {
        String sql = "UPDATE Users SET email = ? WHERE username = ?";

        try (Connection conn = DB.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, this.getUsername());
            this.setPassword(email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete() {
        String sql = "DELETE FROM Users WHERE username = ?";

        try (Connection conn = DB.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Favorite getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(Favorite favoriteList) {
        this.favoriteList = favoriteList;
    }

    public void addFavorite(Product product) {
        favoriteList.updateFavorite(product);
//        this.favoriteList = favoriteList;
    }
}
