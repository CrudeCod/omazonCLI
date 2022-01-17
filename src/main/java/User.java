
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable{
    //Basic info
    private String Username;
    private String Password;
    private String email;
    //Customer
    private double balance;
    private Product[] cartProduct;
    private String[] orderHistory;
    private int paymentPassword;

    private int ProductCount=0;
    //Seller
    private double profit;
    private Product[] productsList= new Product[100];
    //  private Product[] productsList;
    private String[] transactionHistory;
    private String[] orderNotifications;

    private static final long serialVersionUID = 1L;
    private String[] shoppingCart = new String[100];

    private int productsInCart=0;

    public int getProductsInCart(){
        return productsInCart;
    }
    public void incrementProductsInCart(){
        this.productsInCart++;
    }

    public void setShoppingCart(String[] shoppingCart){
        this.shoppingCart = shoppingCart;
    }

    public String[] getShoppingCart(){

        return this.shoppingCart;
    }


    public User(String Username,String Password,String email) {
        this.Username=Username;
        this.Password=Password;
        this.email=email;
        this.balance=0;

        //this.cartProduct=cartProduct;
        // this.orderHistory=orderHistory;
        // this.paymentPassword=paymentPassword;
        //   this.profit=profit;
        //  this.productsList=productsList;
        // this.transactionHistory=transactionHistory;
        // this.orderNotifications=orderNotifications;


    }
    public static void SaveToFile(User u){   //add filepath as a parameter
        try{
            FileOutputStream fileOut = new FileOutputStream("Testu\\USERNAMES\\"+u.Username);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(u);
            objectOut.close();
            System.out.println("Successfully written.");


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static User ReadFromFile(String filepath){
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            User u = (User) objectIn.readObject();
            //System.out.println("User successfully read from file.");
            objectIn.close();
            return u;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



    // Basic info
    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
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




    //Customer

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Product[] getCartProduct() {
        return cartProduct;
    }

    public void setCartProduct(Product[] cartProduct) {
        this.cartProduct = cartProduct;
    }

    public String[] getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(String[] orderHistory) {
        this.orderHistory = orderHistory;
    }

    public int getPaymentPassword() {
        return paymentPassword;
    }

    public void setPaymentPassword(int paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    //Seller

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Product[] getProductsList() {
        return productsList;
    }

    public void setProductsList(Product p) {
        this.productsList[ProductCount]=p;
    }

    public String[] getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(String[] transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public String[] getOrderNotifications() {
        return orderNotifications;
    }

    public void setOrderNotifications(String[] orderNotifications) {
        this.orderNotifications = orderNotifications;
    }


    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int productCount) {
        ProductCount = productCount;
    }


}