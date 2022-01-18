

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
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
    private ArrayList<String> reviews = new ArrayList<>();
    private Boolean bestSelling;
    private String category;

    private String ownerName;

    private static File Productfolder = new File("src/database/PRODUCTS");
    //----------------------------------\\
    public Product(String productName, String description, Double price, int stockCount, int salesCount,String category, String ownerName) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockCount = stockCount;
        this.salesCount = salesCount;
        setCategory(category);
        this.ownerName = ownerName;
    }
    //saveToFile

    public static void SaveToFile(Product product){   //add filepath as a parameter
        try{
            FileOutputStream fileOut = new FileOutputStream("src/database/PRODUCTS/"+product.productName);
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
        for(int i=0; i<user.getShoppingCart().length;i++){
            newShoppingCart[i]=user.getShoppingCart()[i];
        }
        newShoppingCart[user.getProductsInCart()]=this.getProductName();
        user.setShoppingCart(newShoppingCart);
        user.incrementProductsInCart();
//        User.SaveToFile(user);
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
//        if (reviews.get(0) != null && reviews.size() > 0) {
//            for (int i =0; i<reviews.size();i++) {
//                if (reviews.get(i) == null) continue;
//                else {
//                    System.out.println("----------------------------------------------------------------------------------------");
//                    System.out.println(reviews.get(i));
//                }
//            }
//        } else {
//            System.out.println("No reviews yet.");
//        }
        System.out.println("No reviews yet.");

        System.out.println("----------------------------------------------------------------------------------------");

    }

    public Product[] sortAZ(Boolean descending){//if in case you want to sort it in descending order
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
    public static Product[] SearchForProduct(String productOrSellerName){
        int i = 0;
        int length = Productfolder.listFiles().length;
        String c = "(.*)";
        String str0 =productOrSellerName.trim();
        String str = str0.toLowerCase();

        Product[] ProductList = new Product[length];
        Product[] matchedList = new Product[length];

        for(File fileEntry : Productfolder.listFiles()){
            Product k = (Product) Product.ReadFromFile(fileEntry.getAbsolutePath());
            ProductList[i] = k;
            i++;
        }
        int j = 0;
        for(int l=0; l<ProductList.length;l++){
            if(ProductList[l].productName.toLowerCase().matches(c+str+c)){
                System.out.println((j+1)+". "+ ProductList[l].productName+", RM"+ProductList[l].price+", Seller: "+ProductList[l].ownerName);
                matchedList[j] = ProductList[l];
                j++;
            }else if(ProductList[l].ownerName.toLowerCase().matches(c+str+c)){
                System.out.println((j+1)+". "+ ProductList[l].productName+", RM"+ProductList[l].price+", Seller: "+ProductList[l].ownerName);
                matchedList[j] = ProductList[l];
                j++;
            }
        }
        if (j == 0){ System.out.println("Seller or Product not found"); return null;}
        else return matchedList;


    }

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

    public Product[] sortPrice(Boolean descending){//if in case you want to sort it in descending order
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

    public void updateProductReview(String customerName, String newReview){
//        String[] temp = new String[(this.reviews).length+1];
//        int i = 0;
//        for (String r:this.reviews){
//            temp[i] = r;
//            i++;
//        }
//        temp[i]= customerName+":\n\t"+newReview;
//        this.reviews = temp;
//        SaveToFile(this);
    }

    //----------------------------------\\

    public String getProductName() {
        return productName;
    }
    public void setProductName(String prodName) {
        Product prodWithPreviousName = this;
        File thisProduct = new File("src/database/PRODUCTS/"+prodWithPreviousName.getProductName());
        thisProduct.delete();
        prodWithPreviousName.productName = prodName;
        SaveToFile(prodWithPreviousName);
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

    public ArrayList<String> getReviews() {
        System.out.println("* Product Reviews:");
        for (String review : this.reviews) {
            if (review == null) continue;
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(review);
        }
        System.out.println("----------------------------------------------------------------------------------------");
        return this.reviews;
    }

    public void addComment(int reviewNumber,String sellerName, String newComment) {
        String temp = reviews.get(reviewNumber)+"\n\t\t Comment from Seller("+sellerName+"):\n\t\t\t"+newComment;
        reviews.set(reviewNumber, temp);
        SaveToFile(this);
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

    public void alterStockCount(int quantity) {
        stockCount-=quantity;
        salesCount++;
        SaveToFile(this);
    }

    public static Product[] printBestSelling(int top_n){//top_n means top 3, top 4 or top 5 etc best selling products to be displayed
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
        return Parr;
    }
}
