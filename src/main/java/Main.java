import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static boolean loggedIn = false;
    public static boolean selling = false;
    public static boolean checkingShoppingCart = false;
    public static boolean checkBalance = false;
    public static boolean checkFavorite = false;
    public static boolean managingAccount = false;
    public static boolean shopping = false;
    public static boolean isSeller = false;
    public static boolean buyProduct = false;
    public static boolean checkingOrderHistory = false;
    public static User activeUser = null;

    public static File productFolder = new File("src\\database\\PRODUCTS");

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        while (true) {
            if (!loggedIn) {
                greetingScreen();
            } else {
                // System.out.println(activeUser.getUsername() + " is logged in.\n");
                mainscreen();
                Product prod = Product.ReadFromFile("src/database/PRODUCTS/Pencil");
                // todo: fix the shopping cart
                // prod.putIntoCart(activeUser);
                System.out.println("\t\t\t\t**==============================================================**");
                System.out.println("\t\t\t\t Current user: " + activeUser.getUsername() + "!");
                System.out.println("\t\t\t\t 1. Sell product");
                System.out.println("\t\t\t\t 2. Buy product (Go to Homepage)");
//                System.out.println("\t\t\t\t 3. Check favorite");
//                System.out.println("\t\t\t\t 4. Check balance");
//                System.out.println("\t\t\t\t 5. Check shopping cart");
//                System.out.println("\t\t\t\t 6. Check order history");
                System.out.println("\t\t\t\t 3. Manage account");
                System.out.println("\t\t\t\t 4. Log out");
                System.out.println("\t\t\t\t 0. EXIT");
                System.out.println("\t\t\t\t**==============================================================**");
                String answer = s.next();
                if (answer.equals("1")) {
                    selling = true;
                    sell();
                } else if (answer.equals("2")) {
                    shopping = true;
                    homepage();
                } else if (answer.equals("3")) {
                    managingAccount = true;
                    manageAccount();
                } else if (answer.equals("4")) {
                    loggedIn = false;
                    activeUser = null;
                } else if (answer.equals("0")) {
                    System.exit(0);
                }
            }

        }
    }

    public static void homepage() {
        Scanner s = new Scanner(System.in);
        while (loggedIn && shopping) {
            System.out.println("\n");
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
                productDetail(bestSellingProdArray[choice - 1]);
            } else if (answer.equals("A")) {
                System.out.print("Please enter the product name or seller name: ");
                String ans = s.next();
                Product[] searchedProdArray = Product.SearchForProduct(ans);
                System.out.print("What to do next (1, 2, 3, ..) (0 to go back): ");
                int ans0 = s.nextInt();
                if (ans0 == 0) homepage();
                productDetail(searchedProdArray[ans0 - 1]);
            } else if (answer.equals("B")) {
                checkBalance = true;
                checkBalance();
            } else if (answer.equals("C")) {
                checkingShoppingCart = true;
                shoppingCart();
            } else if (answer.equals("D")) {
                buyProduct = true;
                buyProduct();
            } else if (answer.equals("E")) {
                shopping = false;
            } else {
                System.exit(0);
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

//        activeUser.set

        loggedIn = activeUser != null;

        if (loggedIn) {
            File folder = new File("src/database/ORDER");
            File folderFav = new File("src/database/FAVORITES");

            for (File fileEntry : folder.listFiles()) {
                Order p = Order.ReadFromFile(fileEntry.getAbsolutePath());
                if (p.getPurchaserName().equals(activeUser.getUsername()))
                    activeUser.addOrderHistory(p);
            }

            for (File fileEntry : folderFav.listFiles()) {
                Favorite p = Favorite.ReadFromFile(fileEntry.getAbsolutePath());
                if (p.getUsername().equals(activeUser.getUsername())) {
                    activeUser.setFavoriteList(p);
                }
            }

            //notified show
        }
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
        int paymentPassword;

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

        if (!checkEmailValidity(email)) {
            System.out.println("Invalid email address");
            register();
        }

        System.out.println("Please enter your payment password: ");
        paymentPassword = s.nextInt();

        user.register(username, email, password, paymentPassword);
        user.initFavorite(username);

        System.out.println("User successfully registered.");

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
            System.out.println("\t\t\t\t 4. Delete existing product");
            System.out.println("\t\t\t\t 5. Check profit");
            System.out.println("\t\t\t\t 6. Go back");
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

                System.out.print("Please type the product name:");
                productName = s.next();
                System.out.print("Please type the product description:");
                String catchline = s.nextLine();
                description = s.nextLine();
                System.out.print("Please type the product price:");
                price = Double.parseDouble(s.next());
                System.out.print("Please type the product stock count:");
                stockCount = Integer.parseInt(s.next());

                // category
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
                System.out.println("Choose a category for your products (1-6):");
                category = s.next();
                // -------------------------------//
                Product createdProduct = new Product(productName, description, price, stockCount, salescount, category,
                        activeUser.getUsername());
                Product.SaveToFile(createdProduct);
//                User.SaveToFile(activeUser);

            }
            if (answer.equals("2")) {
                File folder = new File("src/database/PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");

                int i = 0;
                for (File fileEntry : folder.listFiles()) {
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if (p.getOwnerName().equals(activeUser.getUsername())) {
                        System.out.println((i + 1) + ". " + p.getProductName());
                        i++;
                    }
                }
            }
            if (answer.equals("3")) {
                File folder = new File("src/database/PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");
                String ans;
                int i = 0;
                for (File fileEntry : folder.listFiles()) {
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if (p.getOwnerName().equals(activeUser.getUsername()))
                        System.out.println((i + 1) + ". " + p.getProductName());
                    i++;
                }
                System.out.println("\t\t\t\t =======WRITE THE FULL NAME OF PRODUCT TO EDIT=========");
                System.out.println("\t\t\t\t Enter 0 to go back");
                ans = s.next();
                if (ans.equals("0")) {
                    sell();
                } else {
                    for (File fileEntry : folder.listFiles()) {
                        Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                        if (p.getOwnerName().equals(activeUser.getUsername()) && p.getProductName().equalsIgnoreCase(ans)) {
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

                            System.out.println(
                                    "\t\t\t\t**==============================================================**");
                            System.out.println("\t\t\t\tCategories:");
                            System.out.println("\t\t\t\t 1. Sports and Outdoor");
                            System.out.println("\t\t\t\t 2. Games and Hobbies");
                            System.out.println("\t\t\t\t 3. Machines and Gadgets");
                            System.out.println("\t\t\t\t 4. Fashion and Accessories (men)");
                            System.out.println("\t\t\t\t 5. Fashion and Accessories (women)");
                            System.out.println("\t\t\t\t 6. Home and Living");
                            System.out.println("\t\t\t\t 0. Other");
                            System.out.println(
                                    "\t\t\t\t**==============================================================**");
                            System.out.println("Choose a category for your products:");
                            category = s.next();

                            p.setProductName(prodName);
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
                File folder = new File("src/database/PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");
                Scanner scanner = new Scanner(System.in);
                String ans;
                int i = 0;
                for (File fileEntry : folder.listFiles()) {
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if (p.getOwnerName().equals(activeUser.getUsername()))
                        System.out.println((i + 1) + ". " + p.getProductName());
                    i++;
                }
                System.out.println("\t\t\t\t =======WRITE THE FULL NAME OF PRODUCT TO EDIT=========");
                System.out.println("\t\t\t\t Enter 0 to go back");
                ans = s.next();
                if (ans.equals("0")) {
                    sell();
                } else {
                    System.out.println("Are you sure that you want to delete your account?");
                    System.out.println("Please enter your password to confirm.");
                    if (activeUser.getPassword().equals(s.next())) {
                        File thisUser = new File("Testu\\PRODUCTS\\" + ans);
                        if (thisUser.delete()) {
                            System.out.println("Successfully deleted your product");
                            sell();
                        } else {
                            System.out.println("The name entered is incorrect retry");
                            sell();
                        }
                    } else {
                        System.out.println("Wrong password! Please try again");
                        sell();
                    }
                }
            }
            if (answer.equals("5")) {
                managingAccount = true;
                checkTransactionsAndProfits();
            }
            if (answer.equals("6")) {
                selling = false;
            }
            if (answer.equals("0")) {
                System.exit(0);
            }
        }

    }

    public static void buyProduct() {
        while (loggedIn && buyProduct) {
            Scanner s = new Scanner(System.in);
            ArrayList<Product> products = new ArrayList<Product>();

            String answer;
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t In stock product on Omazon!");
            System.out.println("\t\t\t\t**==============================================================**");

            //choose category
            System.out.println(
                    "\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\tCategories:");
            System.out.println("\t\t\t\t 1. Sports and Outdoor");
            System.out.println("\t\t\t\t 2. Games and Hobbies");
            System.out.println("\t\t\t\t 3. Machines and Gadgets");
            System.out.println("\t\t\t\t 4. Fashion and Accessories (men)");
            System.out.println("\t\t\t\t 5. Fashion and Accessories (women)");
            System.out.println("\t\t\t\t 6. Home and Living");
            System.out.println("\t\t\t\t 7. Other");
            System.out.println("\t\t\t\t 8. All");
            System.out.println("\t\t\t\t 0. Go back");
            System.out.println(
                    "\t\t\t\t**==============================================================**");
            System.out.println("Choose a category to list:");
            String category = s.next();

            if (category.equals("0")) {
                buyProduct = false;
            } else {
                if (!category.equals("8") && !category.equals("0")) {
                    String categoryName;
                    switch (category) {
                        case "1":
                            categoryName = "Sports and Outdoor";
                            break;
                        case "2":
                            categoryName = "Games and Hobbies";
                            break;
                        case "3":
                            categoryName = "Machines and Gadgets";
                            break;
                        case "4":
                            categoryName = "Fashion and Accessories (men)";
                            break;
                        case "5":
                            categoryName = "Fashion and Accessories (women)";
                            break;
                        case "6":
                            categoryName = "Home and Living";
                            break;
                        default:
                            categoryName = "Other";
                            break;
                    }
                    System.out.println("Category: " + categoryName);
                    File folder = new File("src/database/PRODUCTS");
                    Scanner scanner = new Scanner(System.in);
                    String ans;
                    int index = 0;
                    for (File fileEntry : folder.listFiles()) {
                        Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                        if (p.getCategory().equals(categoryName) && (!p.getOwnerName().equals(activeUser.getUsername()))) {
                            System.out.println(index + 1 + ". " + p.getProductName());
                            products.add(p);
                            index++;
                        }
                    }
                    if (index == 0) {
                        System.out.println("\t\t\t\tNo product in this category currently.");
                    }
                } else {
                    File folder = new File("src/database/PRODUCTS");
                    Scanner scanner = new Scanner(System.in);
                    String ans;
                    int index = 0;
                    for (File fileEntry : folder.listFiles()) {
                        Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());

                        if (!p.getOwnerName().equals(activeUser.getUsername())) {
                            System.out.println(index + 1 + ". " + p.getProductName());
                            products.add(p);
                            index++;
                        }
                    }
                }

                System.out.println("Select product to buy.");
                int option = s.nextInt();

                if ((option - 1) < products.size()) {
                    productDetail(products.get(option - 1));
                } else {
                    System.out.println("Product is not in list.");
                }
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

            for (String string : activeUser.getShoppingCart()) {
                if (string != null) {
                    System.out.println(string);
                }
            }

            System.out.println("\t\t\t\t Press 0 to go back");
            System.out.println("\t\t\t\t Press 1 to remove product from shopping cart");
            System.out.println("\t\t\t\t Press 2 to proceed to buy all products in the shopping cart");
            answer = s.next();

            if (answer.equals("0")) {
                checkingShoppingCart = false;
            } else if (answer.equals("1")) {
                System.out.println("\t\t\t\t Please write out the name of the product you wish to remove:");
                answer = s.next();
                String[] shop = activeUser.getShoppingCart();

                for (int i = 0; i < shop.length; i++) {
                    if (shop[i] != null) {
                        if (shop[i].equals(answer)) {
                            shop[i] = null;
                        }
                    }
                }
                activeUser.setShoppingCart(shop);
            } else if (answer.equals("2")) {
                //todo: implement buying
                String[] shoppingCart = activeUser.getShoppingCart();
                ArrayList<String> names = new ArrayList<>();
                //Order order = new Order(activeUser.getUsername(),prod);
                boolean failedToBuy = false;
                for (String a : shoppingCart) {
                    for (File f : productFolder.listFiles()) {
                        Product product = Product.ReadFromFile(f.getAbsolutePath());
                        if (a != null) {
                            if (a.equals(product.getProductName())) {
                                //todo: check if enough money
                                OrderItem item = new OrderItem(1, product, activeUser);
                            }
                        }
                    }
                }

                if (failedToBuy) {
                    System.out.println("Failed to buy due to insufficient funds.");
                } else {
                    String[] shop = new String[100];
                    activeUser.setShoppingCart(shop);
                    User.SaveToFile(activeUser);
                    System.out.println("Thank you for buying");
                }
            } else {
                shoppingCart();
            }
        }
    }

    public static void manageAccount() {
        while (loggedIn && managingAccount) {
            Scanner s = new Scanner(System.in);
            String answer;
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t Editing the user: " + activeUser.getUsername() + "!");
            System.out.println("\t\t\t\t 1. Edit username/password/email");
            System.out.println("\t\t\t\t 2. Set/Edit payment password");
            System.out.println("\t\t\t\t 3. Delete account");
            System.out.println("\t\t\t\t 4. Check history");
            System.out.println("\t\t\t\t 0. Go back");
            System.out.println("\t\t\t\t**==============================================================**");
            answer = s.next();

            if (answer.equals("1")) {
                System.out.println("\t\t\t\t 1. Edit username");
                System.out.println("\t\t\t\t 2. Edit password");
                System.out.println("\t\t\t\t 3. Edit email");
                System.out.println("\t\t\t\t 0. Go back");
                answer = s.next();
                if (answer.equals("1")) {
                    System.out.println("Please enter your password to confirm.");
                    if (activeUser.getPassword().equals(s.next())) {
                        System.out.println("\t\t\t\t Enter a new username:");
                        String newUsername = s.next();
                        activeUser.updateUsername(newUsername);
                        System.out.println("\t\t\t\t Username changed successfully!");
                    } else {
                        System.out.println("Wrong password! Please try again");
                        manageAccount();
                    }
                }
                if (answer.equals("2")) {
                    //todo: make it harder to change the password by requiring the last password
                    System.out.println("\t\t\t\t Please enter your old password.");
                    if (activeUser.getPassword().equals(s.next())) {
                        System.out.println("\t\t\t\t Enter a new password");
                        String newPassword = s.next();
                        activeUser.updatePassword(newPassword);
                        System.out.println("\t\t\t\t Password changed successfully!");
                    } else {
                        System.out.println("\t\t\t\t Wrong password! Please try again");
                        manageAccount();
                    }
                }
                if (answer.equals("3")) {
                    System.out.println("\t\t\t\t Please enter your password to confirm.");
                    if (activeUser.getPassword().equals(s.next())) {
                        System.out.println("\t\t\t\t Please enter a new email address:");
                        String newEmail = s.next();
                        if (checkEmailValidity(newEmail)) {
                            activeUser.updateEmail(newEmail);
                            System.out.println("\t\t\t\t Email changed successfully!");
                        } else {
                            System.out.println("Email is not valid.");
                        }
                        manageAccount();
                    } else {
                        System.out.println("\t\t\t\t Wrong password! Please try again");
                        manageAccount();
                    }
                }
                if (answer.equals("0")) {
                    manageAccount();
                }
            } else if (answer.equals("2")) {
                System.out.println("\t\t\t\t Please enter your password to confirm.");
                if (activeUser.getPassword().equals(s.next())) {
                    System.out.println("\t\t\t\t Enter your new payment password");
                    int newPassword = s.nextInt();
                    activeUser.updatePaymentPassword(newPassword);
                    System.out.println("\t\t\t\t Payment password changed successfully!");
                } else {
                    System.out.println("\t\t\t\t Wrong password! Please try again");
                    manageAccount();
                }
            }
            if (answer.equals("3")) {
                System.out.println("\t\t\t\t Are you sure that you want to delete your account?");
                System.out.println("\t\t\t\t Please enter your password to confirm.");
                String password = s.next();
                if (activeUser.getPassword().equals(password)) {
                    activeUser.delete();
                    System.out.println("Account deleted.");
                    activeUser = null;
                    loggedIn = false;
                } else {
                    System.out.println("\t\t\t\t Wrong password! Please try again");
                    manageAccount();
                }
            }
            if (answer.equals("4")) {
                checkingOrderHistory = true;
                orderHistory();
            }
            if (answer.equals("0")) {
                managingAccount = false;
            }
        }
    }

    public static boolean checkEmailValidity(String email) {
        // todo: maybe expand the check later
        if (email.contains("@") && email.contains(".com")) {
            return true;
        }
        return false;
    }

    public static void checkTransactionsAndProfits() {
        while (loggedIn && isSeller) {
            Scanner keyboard = new Scanner(System.in);
            String answer;
            ArrayList<Double> profitList = new ArrayList<Double>();
            for (int i = 0; i < profitList.size(); i++) {
                profitList.add(activeUser.getProfit());
            }
            System.out.println("\t\t\t\t**==============================================================**");
            System.out.println("\t\t\t\t 1. Would you like to view the transaction list?");
            System.out.println("\t\t\t\t 2. Would you like to view profits for your products?");
            System.out.println("\t\t\t\t 0. Go Back");
            System.out.println("\t\t\t\t**==============================================================**");
            answer = keyboard.next();
            if (answer.equals("1")) {
                System.out.println("\t\t\t\tThe List of your Transaction: " + activeUser.getTransactionHistory());
            } else if (answer.equals("2")) {
                System.out.println("\t\t\t\t The List of Your Product profits: " + profitList);
            } else if (answer.equals(0)) {
                isSeller = false;
            } else {
                System.out.println("Please enter a value from the given options");
            }
        }
    }

    public static void checkBalance() {
        while (loggedIn && checkBalance) {
            Scanner s = new Scanner(System.in);
            System.out.println("\t\t\t\t**==============================================================**");
            System.out
                    .println("\t\t\t\tYour current account balance: " + String.format("%.2f", activeUser.getBalance()));
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
                String expiry = s.next();
                System.out.println("Please enter your cvv number: ");
                String cvv = s.next();
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

    public static void productDetail(Product product) {
        Scanner s = new Scanner(System.in);
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("\t\t\t\tProduct Detail");
        product.productDisplay();

        System.out.println("1. Buy Now");
        System.out.println("2. Add to cart");
        System.out.println("3. Add to favorite");
        System.out.println("4. Back to home");
        String option = s.next();

        if (option.equals("1")) {
            System.out.println("Please enter quantity of item to purchase");
            int quantity = s.nextInt();
            //stock not enough validation
            if (quantity > product.getStockCount()) {
                System.out.println("Stock is not enough. Please reduce or contact customer service.");
            } else {
                System.out.println("Please enter the delivery address");
                String address = s.next();

                System.out.println("Please enter payment password");
                int paymentPassword = s.nextInt();

                if (activeUser.getPaymentPassword() == paymentPassword) {
                    OrderItem orderItems = new OrderItem(quantity, product, activeUser);
                    OrderItem[] orders = new OrderItem[]{orderItems};

                    Order order = new Order(activeUser.getUsername(), product.getOwnerName(), address, orders);
                    product.alterStockCount(quantity);

                    //if balance not enough validator
                    if (!(activeUser.getBalance() < order.getTotalPrice())) {
                        order.saveToFile(order);
                        activeUser.addOrderHistory(order);
//                        activeUser.setBalance(order.deductWallet(activeUser.getBalance(), activeUser.getUsername()));
                        System.out.println("Purchased successfully! Thank you, we will notify the seller to ship out your item.");
                    } else {
                        System.out.println("Balance is not enough! Please top up and try again.");
                    }
                } else {
                    System.out.println("Wrong payment password! Please try again.");
                }
            }
            buyProduct = false;
        } else if (option.equals("2")) {
            product.putIntoCart(activeUser);
            System.out.println("Added product to cart");
        } else if (option.equals("3")) {
            activeUser.addFavorite(product);
            System.out.println("Product added to favorite list");
        } else {
            buyProduct();
        }
    }

    public static void orderHistory() {
        while (loggedIn && checkingOrderHistory) {
            Scanner s = new Scanner(System.in);
            int index = 0;
            System.out.println("\t\t\t\t**==============================================================**");
            System.out
                    .println("\t\t\t\tYour latest order history");
            for (Order order : activeUser.getOrderHistory()) {
                System.out.println("\t\t\t\t" + (index + 1) + ". " + order.getId() + "\t\t\tRM " + String.format("%.2f", order.getTotalPrice()));
            }
            System.out.println("\t\t\t\t0. Go back");
            System.out.println("\t\t\t\t**==============================================================**");

            int select = s.nextInt();

            if (select == 0) {
                checkingOrderHistory = false;
            } else {
                System.out.println("Select order to view.");
                if ((select - 1) < activeUser.getOrderHistory().size()) {
                    orderDetail(activeUser.getOrderHistory().get(select - 1));
                } else {
                    System.out.println("Product is not in list.");
                }
            }
        }
    }

    public static void orderDetail(Order order) {
        System.out.println(order.getSellerName());
        Scanner s = new Scanner(System.in);
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("\t\t\t\tOrder Detail");
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("- Product name: " + order.getId());
        System.out.println("- Sold by: " + order.getSellerName());
        System.out.println("- Price: RM " + String.format("%.2f", order.getTotalPrice()));
        System.out.println("- Delivery address: " + order.getDeliveryAddress());
        System.out.println("- Products: ");
        for (OrderItem item : order.getOrderItems()) {
            System.out.println("\t - " + item.getProduct().getProductName() + " x " + item.getQuantity());
        }
    }

    public static void checkFavorite() {
        Scanner s = new Scanner(System.in);
        int index = 0;
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("\t\t\t\tFavorite List");
        System.out.println("\t\t\t\t**==============================================================**");
        for (Product product : activeUser.getFavoriteList().getList()) {
            System.out.println((index + 1) + ". " + product.getProductName());
        }
    }
}