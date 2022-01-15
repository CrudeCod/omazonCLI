
import java.io.*;
import java.util.Arrays;
import java.util.regex.*;
public class Product implements Serializable{
    //----------------------------------\\
    //@Serial
    private static final long serialVersionUID = 1L;
    //----------------------------------\\
    private String productName;
    private String description;
    private Double price;
    private int stockCount;
    private int salesCount;
    private String[] reviews;
    private Boolean bestSelling;
    private String category;

    private String ownerName;

    static File Productfolder = new File("C:\\Testu\\PRODUCTS");
    //----------------------------------\\
    public Product(String productName, String description, Double price, int stockCount, int salesCount,String category, String ownerName) {
        this.productName = productName;

        this.description = description;
        this.price = price;
        this.stockCount = stockCount;
        this.salesCount = salesCount;
        setCategory(category);

        this.ownerName = ownerName;
     //   this.reviews = reviews;
    //    this.bestSelling = bestSelling;
    }
    //saveToFile

    public static void SaveToFile(Product product){   //add filepath as a parameter
        try{
            FileOutputStream fileOut = new FileOutputStream("Testu\\PRODUCTS\\"+product.productName);
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

    public void putIntoCart(User user){
        String[] newShoppingCart = new String[100];
        User.initializeShoppingCart(newShoppingCart);
        for(int i=0; i<user.getShoppingCart().length;i++){
            newShoppingCart[i]=user.getShoppingCart()[i];
        }
        newShoppingCart[user.getProductsInCart()]=this.getProductName();
        user.setShoppingCart(newShoppingCart);
        User.SaveToFile(user);
    }

    //to display product info, this is temporary, can be changed according to need
    public void productDisplay(){
        System.out.println("* Product: "+this.productName);
        System.out.println("* Price: "+this.price);
        System.out.println("* Category: "+this.category);
        System.out.println("* Seller: "+this.ownerName);
        System.out.println("* Stock: "+this.stockCount);
//        System.out.println("* Product ratings: * * * * ("+this.salesCount+")");
        System.out.println("* Description:\n"+this.description);
        System.out.println("* Product Reviews:");
        for (String review : this.reviews) {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(review);
        }
        System.out.println("----------------------------------------------------------------------------------------");

    }

    public static Product[] sortAZ(Boolean descending){//if in case you want to sort it in descending order
        int i = 0;
        int length = Productfolder.listFiles().length;
        Product[] Parr = new Product[length];
        for(File fileEntry : Productfolder.listFiles()){
            Product p = (Product) Product.ReadFromFile(fileEntry.getAbsolutePath());
            Parr[i] = p;
            i++;
        }

        //sorting alphabetically
        if (!descending)Arrays.sort(Parr, (a, b) -> a.productName.compareTo(b.productName));
        else Arrays.sort(Parr, (a, b) -> b.productName.compareTo(a.productName));

        int j = 1;
        for (Product p:Parr){
            System.out.println(j+". "+p.productName + ", RM "+p.price);
            j++;
        }
        return Parr;
    }
    @SuppressWarnings("empty-statement")
    public static void SearchForProduct(String productOrSellerName){
        int i = 0;
        int j = 0;
        String c = "(.*)";

        productOrSellerName.toLowerCase();

        int length = Productfolder.listFiles().length;
        String[] productNameList = new String[length];
        String[] sellerNameList = new String[length];

        for(File fileEntry : Productfolder.listFiles()){
            Product k = (Product) Product.ReadFromFile(fileEntry.getAbsolutePath());
            productNameList[i] = k.getProductName();
            sellerNameList[i]=k.getOwnerName();
            i++;
            productNameList[i].toLowerCase();
            sellerNameList[i].toLowerCase();
            if (productNameList[i].matches(c+productOrSellerName+c)) {
                System.out.println(j+". "+k.getProductName()+ ", RM "+k.getPrice());
                j++;
            }else if (sellerNameList[i].matches(c+productOrSellerName+c)) {
                System.out.println(j+". "+k.getProductName()+ ", RM "+k.getPrice()+", Seller: "+k.getOwnerName());
                j++;
            }
        }
        if (j == 0) System.out.println("Product or Seller Not Found");
    }

    
    /* for (int i=0 ;i< size-1; i++){
         if(array[i]==value){
            System.out.println("Element found index is :"+ i);
         }else{
            System.out.println("Element not found");
         }*/



    public static Product[] displayCategory(String category, Boolean sortPrice){
        int i = 0;
        int length = Productfolder.listFiles().length;
        Product[] Parr = new Product[length];
        for(File fileEntry : Productfolder.listFiles()){
            Product p = (Product) Product.ReadFromFile(fileEntry.getAbsolutePath());
            if (p.category.equalsIgnoreCase(category)){
                Parr[i] = p;
                i++;
            }
        }
        //sorts according to price
        if (sortPrice){Arrays.sort(Parr, (a,b) -> (int)(a.price - b.price));}
        int j = 1;
        for (Product p:Parr){
            System.out.println(j+". "+p.productName+ ", RM "+p.price);
            j++;
        }
        return Parr;
    }

    public static Product[] sortPrice(Boolean descending){//if in case you want to sort it in descending order
        int i = 0;
        int length = Productfolder.listFiles().length;
        Product[] Parr = new Product[length];
        for(File fileEntry : Productfolder.listFiles()){
            Product p = (Product) Product.ReadFromFile(fileEntry.getAbsolutePath());
            Parr[i] = p;
            i++;
        }

        //sorting according to price
        if (!descending)Arrays.sort(Parr, (a, b) -> (int)(a.price - b.price));
        else Arrays.sort(Parr, (a, b) -> (int)(b.price - a.price));

        int j = 1;
        for (Product p:Parr){
            System.out.println(j+". "+p.productName + ", RM "+p.price);
            j++;
        }
        return Parr;
    }
    
    public void updateProduct(int quantityBought){
        int currentStockCount = this.stockCount;
        if (quantityBought>currentStockCount) System.out.println("Warning!: Only "+currentStockCount+" left in Stock");
        else {
            this.stockCount = currentStockCount - quantityBought;
            this.salesCount = currentStockCount + quantityBought;
            SaveToFile(this);
        }
    }
    public void updateProduct(String newReview){
        String[] temp = new String[(this.reviews).length+1];
        int i = 0;
        for (String r:this.reviews){
            temp[i] = r;
            i++;
        }
        temp[i]= newReview;
        this.reviews = temp;
        SaveToFile(this);
    }
    public void printBestSelling(int top_n){//top_n means top 3, top 4 or top 5 etc best selling products to be displayed
        int i = 0;
        int length = Productfolder.listFiles().length;
        Product[] Parr = new Product[length];
        for(File fileEntry : Productfolder.listFiles()){
            Product p = (Product) Product.ReadFromFile(fileEntry.getAbsolutePath());
            Parr[i] = p;
            i++;
        }

        //sorting according to salesCount
        Arrays.sort(Parr, (a, b) -> (int)(a.salesCount - b.salesCount));

        int j = 1;
        System.out.println("Top "+top_n+" best selling products");
        for (Product p:Parr){
            System.out.println(j+". "+p.productName + ", RM "+p.price);
            j++;
            if (j == top_n+1) break;
        }
    }


    //----------------------------------\\

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        SaveToFile(this);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
        SaveToFile(this);
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
        SaveToFile(this);
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
        SaveToFile(this);
    }

    public String[] getReviews() {
        return reviews;
    }
    //the getter and setter for reviews has to be changed later.
    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }

    public Boolean getBestSelling() {
        return bestSelling;
    }

    public void setBestSelling(Boolean bestSelling) {
        this.bestSelling = bestSelling;
        SaveToFile(this);
    }
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        SaveToFile(this);
    }

    //Category
    public void setCategory(String choice){
        switch (choice) {
            case "1" : this.category = "Sports and Outdoor";
            case "2" : this.category = "Games and Hobbies";
            case "3" : this.category = "Machines and Gadgets";
            case "4" : this.category = "Fashion and Accessories (men)";
            case "5" : this.category = "Fashion and Accessories (women)";
            case "6" : this.category = "Home and Living";
            case "0" : this.category = "Other";
            default : this.category = "Other";

        }
        SaveToFile(this);
    }
    public String getCategory() {
        return category;
    }
    //----------------------------------\\
}
