import java.io.*;

public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private int quantity;
    private Product product;
    private String ownerName;

    public OrderItem(int quantity, Product product, User activeUser) {
        this.quantity = quantity;
        this.product = product;
        this.ownerName = product.getOwnerName();

        User u = User.ReadFromFile("src/database/USERNAMES/"+ownerName);

        activeUser.setBalance(Order.deductWallet(activeUser.getBalance(),ownerName,product.getPrice() * quantity));

        u.setBalance(product.getPrice());
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
