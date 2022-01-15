import java.io.File;
import java.util.Scanner;

public class Main {
    public static boolean loggedIn = false;
    public static boolean selling = false;
    public static boolean checkingShoppingCart = false;
    public static boolean checkBalance = false;
    public static User activeUser = null;

    //todo: implement categories(!)
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        while (true) {
            if (!loggedIn) {
                greetingScreen();
            } else {
//                System.out.println(activeUser.getUsername() + " is logged in.\n");
                mainscreen();
                Product prod = Product.ReadFromFile("src/database/PRODUCTS/Pencil");
                //todo: fix the shopping cart
                // prod.putIntoCart(activeUser);
                System.out.println("\t\t\t\t**==============================================================**");
                System.out.println("\t\t\t\t Current user: " + activeUser.getUsername() + "!");
                System.out.println("\t\t\t\t 1. Sell product");
                System.out.println("\t\t\t\t 2. Buy product");
                System.out.println("\t\t\t\t 3. List products");
                System.out.println("\t\t\t\t 4. Check balance");
                System.out.println("\t\t\t\t 5. Check shopping cart");
                System.out.println("\t\t\t\t 6. User setting");
                System.out.println("\t\t\t\t 7. Log out");
                System.out.println("\t\t\t\t 0. EXIT");
                System.out.println("\t\t\t\t**==============================================================**");
                String answer = s.next();
                if (answer.equals("1")) {
                    selling = true;
                    sell();
                } else if (answer.equals("4")) {
                    checkBalance = true;
                    checkBalance();
                } else if (answer.equals("5")) {
                    checkingShoppingCart = true;
                    shoppingCart();
                } else if (answer.equals("7")) {
                    loggedIn = false;
                    activeUser = null;
                } else if (answer.equals("0")) {
                    System.exit(0);
                }
            }
        }
    }

    public static void greetingScreen() {
        Scanner s = new Scanner(System.in);
        System.out.println("\t\t\t\t============================================");
        System.out.println("\t\t\t\t Welcome user! Please login or register");
        System.out.println("\t\t\t\t 1. Login");
        System.out.println("\t\t\t\t 2. Register");
        System.out.println("\t\t\t\t Press any other key to quit.");
        System.out.println("\t\t\t\t============================================");
        String option = s.next();
        if (option.equals("1")) {
            login();
        } else if (option.equals("2")) {
            register();
        } else {
            System.exit(0);
        }
    }

    public static void login() {
        String username;
        String password;
        Scanner s = new Scanner(System.in);
        User user = new User();
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("\t\t\t\t Welcome user! Please enter your username and then password");
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("Please enter your username: ");
        username = s.next();
        System.out.println("Please enter your password: ");
        password = s.next();

        activeUser = user.login(username, password);

        loggedIn = activeUser != null;
    }

    public static void register() {
        Scanner s = new Scanner(System.in);
        User user = new User();
        System.out.println("\t\t\t\t**==========================================================================**");
        System.out.println("\t\t\t\t Welcome user! Please enter your username, password, and email to register!");
        System.out.println("\t\t\t\t**==========================================================================**");
        String username;
        String password;
        String email;

        System.out.println("Please enter your username: ");
        username = s.next();

        if (user.checkUsername(username)) {
            System.out.println("Username taken.");
            register();
        }

        System.out.println("Please enter your password: ");
        password = s.next();
        System.out.println("Please enter your email: ");
        email = s.next();

        if (user.checkEmail(email)) {
            System.out.println("Email taken.");
            register();
        }

        user.register(username, email, password);

        login();
    }

    public static void mainscreen() {
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("\t\t\t\t Welcome " + activeUser.getUsername() + "! Please enjoy your stay!");
        System.out.println("\t\t\t\t**==============================================================**");
    }

    public static void sell() {
        while (loggedIn && selling) {
            Scanner s = new Scanner(System.in);
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t Welcome user to the seller perspective!");
            System.out.println("\t\t\t\t 1. Put up product");
            System.out.println("\t\t\t\t 2. View existing listings");
            System.out.println("\t\t\t\t 3. Edit existing listings");
            System.out.println("\t\t\t\t 4. Go back");
            System.out.println("\t\t\t\t 0. Exit");
            System.out.println("\t\t\t\t**==============================================================**");
            String answer = s.next();
            if (answer.equals("1")) {
                String productName;
                String description;
                String category;
                double price;
                int stockCount;
                int salescount = 0;
                //todo: implement categories(!)
                System.out.println("Please type the product name:");
                productName = s.next();
                System.out.println("Please type the product description:");
                String catchline = s.nextLine();
                description = s.nextLine();
                System.out.println("Please type the product price:");
                price = Double.parseDouble(s.next());
                System.out.println("Please type the product stock count:");
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
                System.out.println("Choose a category for your products:");
                category = s.next();
                //-------------------------------//
                Product createdProduct = new Product(productName, description, price, stockCount, salescount, category, activeUser.getUsername());
                Product.SaveToFile(createdProduct);
                User.SaveToFile(activeUser);

            }
            if (answer.equals("2")) {
                File folder = new File("src/database/PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");

                for (File fileEntry : folder.listFiles()) {
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if (p.getOwnerName().equals(activeUser.getUsername())) System.out.println(p.getProductName());
                }
//todo: implement categories(!)
            }
            if (answer.equals("3")) {
                File folder = new File("src/database/PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");
                Scanner scanner = new Scanner(System.in);
                String ans;
                for (File fileEntry : folder.listFiles()) {
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if (p.getOwnerName().equals(activeUser.getUsername())) System.out.println(p.getProductName());
                }
                System.out.println("\t\t\t\t =======WRITE THE FULL NAME OF PRODUCT TO EDIT=========");
                System.out.println("\t\t\t\t Enter 0 to go back");
                ans = s.next();
                if (ans.equals("0")) {
                    sell();
                } else {
                    for (File fileEntry : folder.listFiles()) {
                        Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                        if (p.getOwnerName().equals(activeUser.getUsername()) && p.getProductName().equals(ans)) {
                            String description, category;
                            double price;
                            int stockCount;

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
                            category = s.next();

                            p.setCategory(category);
                            p.setDescription(description);
                            p.setPrice(price);
                            p.setStockCount(stockCount);
                            Product.SaveToFile(p);
                        }

                    }

                }
                if (answer.equals("4")) {
                    selling = false;
                }

            }
            if (answer.equals("4")) {
                selling = false;
            }
            if (answer.equals("0")) {
                System.exit(0);
            }
        }

    }

    public static void shoppingCart() {
        while (loggedIn && checkingShoppingCart) {
            Scanner s = new Scanner(System.in);
            String answer;
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t Below is the list of the products in your shopping cart!");
            System.out.println("\t\t\t\t**==============================================================**");
            if (activeUser.getProductsInCart() == 0) {
                System.out.println("\t\t\t\t There are no products at all yet. Add some to your cart.");
            } else {
                for (String string : activeUser.getShoppingCart()) {
                    System.out.println(string);
                }
            }

        }
    }

    public static void checkBalance() {
        while (loggedIn && checkBalance) {
            Scanner s = new Scanner(System.in);
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\tYour current account balance: " + String.format("%.2f", activeUser.getBalance()));
            System.out.println("\t\t\t\t 1. Top up account balance");
            System.out.println("\t\t\t\t 2. Go back");
            System.out.println("\t\t\t\t 0. Exit");
            System.out.println("\t\t\t\t**==============================================================**");

            String option = s.next();

            if (option.equals("1")) {
                System.out.println("Please enter your credit card number: ");
                String cardNumber = s.next();
                if (cardNumber.length() != 12) {
                    System.out.println("Please enter valid credit card number. (12 digit)");
                    checkBalance();
                }
                System.out.println("Please enter your card expiry date: ");
                String expiry= s.next();
                System.out.println("Please enter your cvv number: ");
                String cvv= s.next();
                if (cvv.length() != 3) {
                    System.out.println("Please enter valid cvv number. (3 digit)");
                    checkBalance();
                }
                System.out.println("Card is valid! Please enter amount to top up.");
                Double amount = s.nextDouble();
                activeUser.topUpBalance(amount);
                System.out.println("Top up successfull");
            } else if (option.equals("2")) {
                checkBalance = false;
            } else {
                System.exit(0);
            }
        }
    }
}