
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static boolean loggedIn=false;
    public static boolean selling=false;
    public static boolean checkingShoppingCart=false;
    public static boolean managingAccount = false;
    public static boolean shopping = false;
    public static User activeUser = greetingscreen();

    public static File usernameFolder = new File("src\\database\\USERNAMES");
    public static File productFolder = new File("src\\database\\PRODUCTS");

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println(activeUser.getUsername()+" is logged in.");
        mainscreen();
     //   Product prod = Product.ReadFromFile("testu\\PRODUCTS\\Pencil");
        //todo: fix the shopping cart
     //   prod.putIntoCart(activeUser);
     //   prod = Product.ReadFromFile("testu\\PRODUCTS\\dragon");
     //   prod.putIntoCart(activeUser);
        while(loggedIn){
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t Current user: "+activeUser.getUsername()+"!");
            System.out.println("\t\t\t\t 1. Sell product");
            System.out.println("\t\t\t\t 2. Buy product (Go to Homepage)");
            System.out.println("\t\t\t\t 3. Manage the account");
            System.out.println("\t\t\t\t 0. EXIT");
            System.out.println("\t\t\t\t**==============================================================**");
            String answer = s.next();
            if(answer.equals("1")){
                selling=true;
                sell();
            }
            if (answer.equals("2")){
                shopping = true;
                homepage();
            }else if(answer.equals("0")){
                loggedIn=false;
            }else if(answer.equals("3")){
                managingAccount=true;
                manageaccount();
            }

        }

    }

    public static void homepage(){
        Scanner s = new Scanner(System.in);
        while (loggedIn&&shopping) {
            Product[] bestSellingProdArray = Product.printBestSelling(3);
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t Current user: " + activeUser.getUsername() + "!");
            System.out.println("\t\t\t\t A. Search");
            System.out.println("\t\t\t\t B. Check Balance");
            System.out.println("\t\t\t\t C. Go to cart");
            System.out.println("\t\t\t\t D. Go to category");
            System.out.println("\t\t\t\t E. Go back");
            System.out.println("\t\t\t\t F. EXIT");
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.print("What to do next (1-3) (A-F): ");
            String answer = s.next();
            if (answer.equals("1") || answer.equals("2") || answer.equals("3")) {
                int choice = Integer.parseInt(answer);
                productpage(bestSellingProdArray[choice - 1]);
            }
            else if (answer.equals("A")) {
                System.out.print("Please enter the product name or seller name: ");
                String ans = s.next();
                Product[] searchedProdArray = Product.SearchForProduct(ans);
                System.out.print("What to do next (1, 2, 3, ..) (0 to go back): ");
                int ans0 = s.nextInt();
                if (ans0 == 0) homepage();
                productpage(searchedProdArray[ans0 - 1]);
            }
            else if (answer.equals("B")) {
                System.out.println("Your current balance is: " + activeUser.getBalance());
                //todo: give option to change balance
            }
            else if (answer.equals("C")) {
                checkingShoppingCart=true;
                shoppingCart();
            }
            else if (answer.equals("D")) {
                System.out.println("\t\t\t\t**==============================================================**");
                System.out.println("\t\t\t\tCategories:");
                System.out.println("\t\t\t\t 1. Sports and Outdoor");
                System.out.println("\t\t\t\t 2. Games and Hobbies");
                System.out.println("\t\t\t\t 3. Machines and Gadgets");
                System.out.println("\t\t\t\t 4. Fashion and Accessories (men)");
                System.out.println("\t\t\t\t 5. Fashion and Accessories (women)");
                System.out.println("\t\t\t\t 6. Home and Living");
                System.out.println("\t\t\t\t 0. Other");
                System.out.println("\t\t\t\t**==============================================================**");
                System.out.print("Choose a category for your products (write the name):");
                String category = s.next();
                Product[] categoryArray = Product.displayCategory(category, true);
                System.out.print("Choose a product (1, 2, 3, ..) (0 to go back): ");
                int ans0 = s.nextInt();
                if (ans0 == 0) homepage();
                productpage(categoryArray[ans0 - 1]);
            }
            else if (answer.equals("E")){
                shopping = false;
            }else{
                System.exit(0);
            }
        }
    }
    public static void productpage(Product p){
        Scanner s = new Scanner(System.in);
        p.productDisplay();
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("\t\t\t\t Current user: " + activeUser.getUsername() + "!");
        System.out.println("\t\t\t\t 1. Add to favorites");
        System.out.println("\t\t\t\t 2. Add to cart");
        System.out.println("\t\t\t\t 3. Buy now");
        System.out.println("\t\t\t\t 0. Go back");
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.print("What to do next (1-3): ");
        int ans = s.nextInt();
        if (ans == 1){
            //todo: add to  favorites
        }else if (ans == 2){
            //todo: add to  cart
            p.putIntoCart(activeUser);
        }else if (ans == 3){
            //todo: add to  cart and then to go shopping cart
            p.putIntoCart(activeUser);
            checkingShoppingCart=true;
            shoppingCart();
        }else homepage();
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
        int ans = s.nextInt();
        if(ans == 1){
            return login();
        } else if(ans == 2){
            register();
        }else{
            System.exit(0);
        }
        return blankUser;
    }
    public static User login(){
        String username;
        String password;
        Scanner s = new Scanner(System.in);
        User blankUser = new User("Blank","blank", "blankk");
        System.out.println("\t\t\t\t**==================================================================**");
        System.out.println("\t\t\t\t Welcome user! To Log in Please enter your username and then password");
        System.out.println("\t\t\t\t**==================================================================**");
        System.out.println("Please enter your username: ");
        username = s.next();
        System.out.println("Please enter your password: ");
        password = s.next();
        File folder = new File("src\\database\\USERNAMES");
        int i = 0;
        for(File fileEntry : folder.listFiles()){
            User u = (User) User.ReadFromFile(fileEntry.getAbsolutePath());
            if(username.equals(u.getUsername()) && password.equals(u.getPassword())) {
                loggedIn=true;
                i++;
                return u;
            }

        }
        if (i == 0) {
            System.out.println("Wrong username or password!");
            System.out.println("Please Retry");
            loggedIn = false;
            greetingscreen();
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
        File folder = new File("src\\database\\USERNAMES");
        for(File fileEntry : folder.listFiles()){
            User u = (User) User.ReadFromFile(fileEntry.getAbsolutePath());
            if(username.equals(u.getUsername())){
                System.out.println("Username taken.");
                register();
            }
        }
        System.out.println("Please enter your email: ");
        email = s.next();
        for(File fileEntry : folder.listFiles()){
            User u = (User) User.ReadFromFile(fileEntry.getAbsolutePath());
            if(username.equals(u.getEmail())){
                System.out.println("Email taken.");
                register();
            }
        }
        System.out.println("Please enter your new password: ");
        password = s.next();

        User user = new User(username, password, email);
        User.SaveToFile(user);

        login();
    }

    public static void mainscreen(){
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("\t\t\t\t Welcome "+activeUser.getUsername()+"! Please enjoy your stay!");
        System.out.println("\t\t\t\t**==============================================================**");
    }

    public static void sell(){
        while(loggedIn&&selling) {
            Scanner s = new Scanner(System.in);
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t Welcome user to the seller perspective!");
            System.out.println("\t\t\t\t 1. Put up product");
            System.out.println("\t\t\t\t 2. View existing listings");
            System.out.println("\t\t\t\t 3. Edit existing listings");
            System.out.println("\t\t\t\t 4. Delete existing product");
            System.out.println("\t\t\t\t 6. Go back");
            System.out.println("\t\t\t\t 0. Exit");
            System.out.println("\t\t\t\t**==============================================================**");
            String answer = s.next();
            if(answer.equals("1")){
                String productName;
                String description;
                String category;
                double price;
                int stockCount;
                int salescount = 0;

                System.out.print("Please type the product name:");
                productName = s.next();
                System.out.print("Please type the product description:");
                String catchline = s.nextLine();
                description = s.nextLine();
                System.out.print("Please type the product price:");
                price = Double.parseDouble(s.next());
                System.out.print("Please type the product stock count:");
                stockCount = Integer.parseInt(s.next());


                //category
                System.out.println("\t\t\t\t**==============================================================**");
                System.out.println("\t\t\t\tCategories:");
                System.out.println("\t\t\t\t 1. Sports and Outdoor");
                System.out.println("\t\t\t\t 2. Games and Hobbies");
                System.out.println("\t\t\t\t 3. Machines and Gadgets");
                System.out.println("\t\t\t\t 4. Fashion and Accessories (men)");
                System.out.println("\t\t\t\t 5. Fashion and Accessories (women)");
                System.out.println("\t\t\t\t 6. Home and Living");
                System.out.println("\t\t\t\t 0. Other");
                System.out.println("\t\t\t\t**==============================================================**");
                System.out.print("Choose a category for your products (1-6):");
                category=s.next();
                //-------------------------------//
                Product createdProduct = new Product(productName,description,price,stockCount,salescount,category,activeUser.getUsername());
                Product.SaveToFile(createdProduct);
                User.SaveToFile(activeUser);

            }
            if(answer.equals("2")){

                File folder = new File("Testu\\PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");
                int i = 0;
                for(File fileEntry : folder.listFiles()){
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if(p.getOwnerName().equals(activeUser.getUsername())) {
                        System.out.println((i+1)+". "+p.getProductName());
                        i++;
                    }
                }
            }
            if(answer.equals("3")){
                File folder = new File("Testu\\PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");
                Scanner scanner = new Scanner(System.in);
                String ans;
                int i = 0;
                for(File fileEntry : folder.listFiles()){
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if(p.getOwnerName().equals(activeUser.getUsername()))
                        System.out.println((i+1)+". "+p.getProductName());
                        i++;
                }
                System.out.println("\t\t\t\t =======WRITE THE FULL NAME OF PRODUCT TO EDIT=========");
                System.out.println("\t\t\t\t Enter 0 to go back");
                ans = s.next();
                if(ans.equals("0")){
                    sell();
                }else{
                    for(File fileEntry : folder.listFiles()){
                        Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                        if(p.getOwnerName().equals(activeUser.getUsername()) && p.getProductName().equalsIgnoreCase(ans)){
                            String description, category;
                            double price;
                            int stockCount;

                            System.out.println("Please type the product description:");
                            String prodName = s.nextLine();
                            System.out.println("Please type the product description:");
                            String catchline = s.nextLine();
                            description = s.nextLine();
                            System.out.println("Please type the product price:");
                            price = Double.parseDouble(s.next());
                            System.out.println("Please type the product stock count:");
                            stockCount = Integer.parseInt(s.next());

                            System.out.println("\t\t\t\t**==============================================================**");
                            System.out.println("\t\t\t\tCategories:");
                            System.out.println("\t\t\t\t 1. Sports and Outdoor");
                            System.out.println("\t\t\t\t 2. Games and Hobbies");
                            System.out.println("\t\t\t\t 3. Machines and Gadgets");
                            System.out.println("\t\t\t\t 4. Fashion and Accessories (men)");
                            System.out.println("\t\t\t\t 5. Fashion and Accessories (women)");
                            System.out.println("\t\t\t\t 6. Home and Living");
                            System.out.println("\t\t\t\t 0. Other");
                            System.out.println("\t\t\t\t**==============================================================**");
                            System.out.println("Choose a category for your products:");
                            category=s.next();

                            p.setProductName(prodName);
                            p.setCategory(category);
                            p.setDescription(description);
                            p.setPrice(price);
                            p.setStockCount(stockCount);
                            Product.SaveToFile(p);
                        }
                    }
                }
            }
            if(answer.equals("4")){
                File folder = new File("Testu\\PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");
                Scanner scanner = new Scanner(System.in);
                String ans;
                int i = 0;
                for(File fileEntry : folder.listFiles()){
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if(p.getOwnerName().equals(activeUser.getUsername()))
                        System.out.println((i+1)+". "+p.getProductName());
                    i++;
                }
                System.out.println("\t\t\t\t =======WRITE THE FULL NAME OF PRODUCT TO EDIT=========");
                System.out.println("\t\t\t\t Enter 0 to go back");
                ans = s.next();
                if(ans.equals("0")){
                    sell();
                }else{
                    System.out.println("Are you sure that you want to delete your account?");
                    System.out.println("Please enter your password to confirm.");
                    if(activeUser.getPassword().equals(s.next())){
                        File thisUser = new File("Testu\\PRODUCTS\\"+ans);
                        if (thisUser.delete()){
                            System.out.println("Successfully deleted your product");
                            sell();
                        }
                        else{
                            System.out.println("The name entered is incorrect retry");
                            sell();
                        }
                    }else{
                        System.out.println("Wrong password! Please try again");
                        sell();
                    }
                }
            }
            if (answer.equals("6")){
                selling=false;
            }
            if(answer.equals("5")){
                managingAccount = true;
                checkTransactionsAndProfits();
            }
            if(answer.equals("0")){
                System.exit(0);
            }
        }
    }

    //todo: (Remove product from shopping cart) and (proceed to pay) not available
    public static void shoppingCart(){
        while(loggedIn&&checkingShoppingCart){
            Scanner s = new Scanner(System.in);
            String answer;
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t Below is the list of the products in your shopping cart!");
            System.out.println("\t\t\t\t**==============================================================**");

            for(String string : activeUser.getShoppingCart()){
                if(string == null){

                }else{
                    System.out.println(string);
                }
            }
            System.out.println("\t\t\t\t Press 0 to go back");
            System.out.println("\t\t\t\t Press 1 to remove product from shopping cart");
            System.out.println("\t\t\t\t Press 2 to proceed to buy all products in the shopping cart");
            answer = s.next();
            if(answer.equals("0")){
                checkingShoppingCart=false;
            }else if(answer.equals("1")){
                System.out.println("\t\t\t\t Please write out the name of the product you wish to remove:");
                answer = s.next();
                String[] shop = activeUser.getShoppingCart();

                for(int i=0;i<shop.length;i++){
                    if(shop[i]!=null){
                        if(shop[i].equals(answer)){
                            shop[i]=null;
                        }

                    }
                }
                activeUser.setShoppingCart(shop);
            } else if(answer.equals("2")){
                //todo: implement buying
                System.out.println("Thank you for buying");
            }
            else{
                shoppingCart();
            }
        }
    }

    public static void manageaccount(){
        while(loggedIn&&managingAccount){
            Scanner s = new Scanner(System.in);
            String answer;
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t Editing the user: "+activeUser.getUsername()+"!");
            System.out.println("\t\t\t\t 1. Edit username/password/email");
            System.out.println("\t\t\t\t 2. Set/Edit payment password");
            System.out.println("\t\t\t\t 3. Delete account");
            System.out.println("\t\t\t\t 0. Go back");
            System.out.println("\t\t\t\t**==============================================================**");
            answer=s.next();

            if(answer.equals("1")){
                System.out.println("\t\t\t\t 1. Edit username");
                System.out.println("\t\t\t\t 2. Edit password");
                System.out.println("\t\t\t\t 3. Edit email");
                System.out.println("\t\t\t\t 0. Go back");
                answer=s.next();
                if(answer.equals("1")){
                    System.out.println("Please enter your password to confirm.");
                    if(activeUser.getPassword().equals(s.next())){
                        System.out.println("Please enter a new user name:");
                        activeUser.setUsername(s.next());
                        User.SaveToFile(activeUser);
                        System.out.println("Username changed successfully!");
                    }else{
                        System.out.println("Wrong password! Please try again");
                        manageaccount();
                    }
                }
                if(answer.equals("2")){
                    //todo: make it harder to change the password by requiring the last password
                        System.out.println("Please enter your old password");
                        if (activeUser.getPassword().equals(s.next())) {
                            System.out.println("Enter a new password");
                            String newPassword = s.next();
                            activeUser.setPassword(newPassword);
                            User.SaveToFile(activeUser);
                            System.out.println("Password changed successfully!");
                        } else {
                            System.out.println("Wrong password! Please try again");
                            manageaccount();
                        }
                }
                if(answer.equals("3")){
                    System.out.println("Please enter your password to confirm.");
                    if(activeUser.getPassword().equals(s.next())){
                        System.out.println("Please enter a new email address:");
                        activeUser.setEmail(s.next());
                        User.SaveToFile(activeUser);
                        System.out.println("Email changed successfully!");
                    }else{
                        System.out.println("Wrong password! Please try again");
                        manageaccount();
                    }

                }
                if(answer.equals("0")){
                    manageaccount();
                }
            }
            if (answer.equals("2")){
                System.out.println("Please enter your password");
                if (activeUser.getPassword().equals(s.next())) {
                    System.out.println("Enter a new payment password (must be an integer)");
                    int newPassword = s.nextInt();
                    activeUser.setPaymentPassword(newPassword);
                    User.SaveToFile(activeUser);
                    System.out.println("Payment Password changed successfully!");
                } else {
                    System.out.println("Wrong password! Please try again");
                    manageaccount();
                }
            }
            if(answer.equals("3")){
                System.out.println("Are you sure that you want to delete your account?");
                System.out.println("Please enter your password to confirm.");
                if(activeUser.getPassword().equals(s.next())){
                    File thisUser = new File("Testu\\USERNAMES\\"+activeUser.getUsername());
                    thisUser.delete();
                    System.out.println("Successfully deleted your account");
                    loggedIn=false;
                    greetingscreen();
                }else{
                    System.out.println("Wrong password! Please try again");
                    manageaccount();
                }
            }
            if(answer.equals("0")){
                managingAccount=false;
            }
        }
    }
    public static void checkTransactionsAndProfits(){
        while(loggedIn&&managingAccount){
            Scanner keyboard = new Scanner(System.in);
            String answer;
            ArrayList<Double> profitList = new ArrayList<Double>();
            for(int i=0;i<profitList.size();i++){
                profitList.add(activeUser.getProfit());
            }
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t1. Would you like to view the transaction list?");
            System.out.println("\t\t\t\t2. Woould you like to view profits for the your products?");
            System.out.println("\t\t\t\t0. Go Back");
            System.out.println("\t\t\t\t**==============================================================**");
            answer=keyboard.next();
            if(answer.equals("1")){
                System.out.println("\t\t\t\tThe List of your Transaction: "+ activeUser.getTransactionHistory());
            }
            else if(answer.equals("2")){
                System.out.println("\t\t\t\t The List of Your Product profits: "+ profitList);
            }
            else if(answer.equals(0)){
                managingAccount=false;
            }
            else{
                System.out.println("Please enter a value from the given options");
            }
        }
    }

}
