import java.io.*;

public class OrderItem implements Serializable {
    private int quantity;
    private Product product;
    private String ownerName;
    private static File userFolder = new File("src/database/USERNAMES/");

    public OrderItem(int quantity, Product product, User activeUser) {
        this.quantity = quantity;
        this.product = product;
        this.ownerName = product.getOwnerName();
        User u = User.ReadFromFile("src/database/USERNAMES/"+ownerName);

        activeUser.setBalance(Order.deductWallet(activeUser.getBalance(),ownerName,product.getPrice()));
        u.setBalance(u.getBalance()+ product.getPrice());
        User.SaveToFile(activeUser);
        User.SaveToFile(u);

    }

    public static void SaveToFile(Product product){   //add filepath as a parameter
        try{
            FileOutputStream fileOut = new FileOutputStream("src/database/ORDER/"+ product.getProductName());
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(product);
            objectOut.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Product ReadFromFile(String filepath){
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Product obj = (Product) objectIn.readObject();
            objectIn.close();
            return obj;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
