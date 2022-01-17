import connect.DB;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Order implements Serializable {
    private String id;
    private String purchaserName;
    private String sellerName;
    private double totalPrice;
    private String deliveryAddress;
    private String trackingNumber = "";
    private OrderItem[] orderItems;

    public Order(String purchaserName, String sellerName, String deliveryAddress, OrderItem[] orderItems) {
        this.purchaserName = purchaserName;
        this.sellerName = sellerName;
        this.deliveryAddress = deliveryAddress;
        this.orderItems = orderItems;
        this.id = UUID.randomUUID().toString().replace("-", "");
        calTotalPrice();
    }

    public void saveToFile(Order order){   //add filepath as a parameter
        try{
            FileOutputStream fileOut = new FileOutputStream("src/database/ORDER/"+ this.id);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(order);
            objectOut.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Order ReadFromFile(){
        try {
            FileInputStream fileIn = new FileInputStream("src/database/ORDER/"+ this.id);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Order obj = (Order) objectIn.readObject();
            objectIn.close();
            return obj;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void calTotalPrice() {
        for(OrderItem items : orderItems) {
            totalPrice += items.getQuantity() * items.getProduct().getPrice();
        }
    }

    public double deductWallet(double balance, String username) {
        balance -= totalPrice;

        String sql = "UPDATE Users SET credit_balance = ? WHERE username = ?";

        try (Connection conn = DB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, balance);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return balance;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
