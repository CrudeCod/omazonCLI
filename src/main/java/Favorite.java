import java.io.*;
import java.util.ArrayList;

public class Favorite implements Serializable{
    private static final long serialVersionUID = 1L;

    private String username;
    private ArrayList<Product> list = new ArrayList<Product>();

    Favorite(String username) {
        this.username = username;
    }

    public void addFavorite(Product product) {
        this.list.add(product);
        saveToFile(this);
    }

    public ArrayList<Product> getList() {
        return list;
    }

    public void saveToFile(Favorite fav){   //add filepath as a parameter
        try{
            FileOutputStream fileOut = new FileOutputStream("src/database/FAVORITES/"+ this.username);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(fav);
            objectOut.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updateFavorite(Product product) {
        File thisProduct = new File("src/database/FAVORITES/"+ this.username);
        thisProduct.delete();
        addFavorite(product);
        saveToFile(this);
    }

    public static Favorite ReadFromFile(String filepath){
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Favorite obj = (Favorite) objectIn.readObject();
            objectIn.close();
            return obj;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getUsername() {
        return username;
    }
}
