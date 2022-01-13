import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
public class Main {
    public static boolean loggedIn=false;
    public static User activeUser = greetingscreen();

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println(activeUser.getUsername()+" is logged in.");
        mainscreen();
        while(loggedIn){
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t Welcome user!");
            System.out.println("\t\t\t\t 1. Sell");
            System.out.println("\t\t\t\t**==============================================================**");
            String answer = s.next();
            if(answer.equals("1")){
                sell();
            }

        }

    }
    public static User greetingscreen(){
        Scanner s = new Scanner(System.in);
        User blankUser = new User("Guest","Guest", "Guest");
        System.out.println("\t\t\t\t============================================");
        System.out.println("\t\t\t\t Welcome user! Please login or register");
        System.out.println("\t\t\t\t 1. Login");
        System.out.println("\t\t\t\t 2. Register");
        System.out.println("\t\t\t\t Press any other key to quit.");
        System.out.println("\t\t\t\t============================================");
        if(s.next().equals("1")){
            return login();
        } else if(s.next().equals("2")){
            register();
        }else{

        }
        return blankUser;
    }
    public static User login(){
        String username;
        String password;
        Scanner s = new Scanner(System.in);
        User blankUser = new User("Blank","blank", "blankk");
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("\t\t\t\t Welcome user! Please enter your username and then password");
        System.out.println("\t\t\t\t**==============================================================**");
        username = s.next();
        password = s.next();
        File folder = new File("C:\\Testu\\USERNAMES");
        for(File fileEntry : folder.listFiles()){
            User u = (User) User.ReadFromFile(fileEntry.getAbsolutePath());
            if(username.equals(u.getUsername()) && password.equals(u.getPassword())) {
                    loggedIn=true;
                    return u;
            }else{
                System.out.println("Wrong username or password!");
                loggedIn=false;
            }

        }
    return blankUser;

    }
    public static void register(){
        Scanner s = new Scanner(System.in);
        System.out.println("\t\t\t\t**==========================================================================**");
        System.out.println("\t\t\t\t Welcome user! Please enter your username, password, and email to register!");
        System.out.println("\t\t\t\t**==========================================================================**");
        String username;
        String password;
        String email;
        System.out.println("Please enter your username: ");
        username = s.next();
        System.out.println("Please enter your password: ");
        password = s.next();
        System.out.println("Please enter your email: ");
        email = s.next();

        User user = new User(username, password, email);
        User.SaveToFile(user);




    }
    public static void mainscreen(){
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("\t\t\t\t Welcome "+activeUser.getUsername()+"! Please enjoy your stay!");
        System.out.println("\t\t\t\t**==============================================================**");
    }
    public static void sell(){
        while(loggedIn) {
            Scanner s = new Scanner(System.in);
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t Welcome user to the seller perspective!");
            System.out.println("\t\t\t\t 1. Put up product");
            System.out.println("\t\t\t\t 2. View existing listings");
            System.out.println("\t\t\t\t 3. Edit existing listings");
            System.out.println("\t\t\t\t 4. Go back");
            System.out.println("\t\t\t\t 5. Exit");
            System.out.println("\t\t\t\t**==============================================================**");
            String answer = s.next();
            if(answer.equals("1")){
                String productName;
                String description;
                double price;
                int stockCount;
                int salescount = 0;

                System.out.println("Please type the product name:");
                productName = s.next();
                System.out.println("Please type the product description:");
                description = s.next();
                System.out.println("Please type the product price:");
                price = Double.parseDouble(s.next());
                System.out.println("Please type the product stock count:");
                stockCount = Integer.parseInt(s.next());
                Product createdProduct = new Product(productName,description,price,stockCount,salescount,activeUser.getUsername());
                Product.SaveToFile(createdProduct);
                User.SaveToFile(activeUser);


            }
            if(answer.equals("2")){
                File folder = new File("C:\\Testu\\PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");

                for(File fileEntry : folder.listFiles()){
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if(p.getOwnerName().equals(activeUser.getUsername()))
                    System.out.println(p.getProductName());
                }

            }
            if(answer.equals("3")){
                File folder = new File("C:\\Testu\\PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");
                Scanner scanner = new Scanner(System.in);
                String ans;
                for(File fileEntry : folder.listFiles()){
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if(p.getOwnerName().equals(activeUser.getUsername()))
                        System.out.println(p.getProductName());
                }
                System.out.println("\t\t\t\t =======WRITE THE FULL NAME OF PRODUCT TO EDIT=========");
                ans = s.next();
                for(File fileEntry : folder.listFiles()){
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if(p.getOwnerName().equals(activeUser.getUsername()) && p.getProductName().equals(ans)){
                        String description;
                        double price;
                        int stockCount;

                        System.out.println("Please type the product description:");
                        description = s.next();
                        System.out.println("Please type the product price:");
                        price = Double.parseDouble(s.next());
                        System.out.println("Please type the product stock count:");
                        stockCount = Integer.parseInt(s.next());
                        p.setDescription(description);
                        p.setPrice(price);
                        p.setStockCount(stockCount);
                        Product.SaveToFile(p);



                    }

                }

            }

        }
    }
}
