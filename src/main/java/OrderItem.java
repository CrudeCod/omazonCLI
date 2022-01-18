import java.io.*;

public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private int quantity;
    private Product product;

    public OrderItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

//    public static void SaveToFile(Product product){   //add filepath as a parameter
//        try{
//            FileOutputStream fileOut = new FileOutputStream("src/database/ORDER/"+ product.getProductName());
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//            objectOut.writeObject(product);
//            objectOut.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

//    public static OrderItem ReadFromFile(String filepath){
//        try {
//            FileInputStream fileIn = new FileInputStream(filepath);
//            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
//            OrderItem obj = (OrderItem) objectIn.readObject();
//            objectIn.close();
//            return obj;
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

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
