import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static boolean loggedIn = false;
    public static boolean selling = false;
    public static boolean checkingShoppingCart = false;
    public static boolean checkBalance = false;
    public static boolean managingAccount = false;
    public static boolean buyProduct = false;
    public static User activeUser = null;

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
                System.out.println("\t\t\t\t 2. Buy product");
                System.out.println("\t\t\t\t 3. Check favorite");
                System.out.println("\t\t\t\t 4. Check balance");
                System.out.println("\t\t\t\t 5. Check shopping cart");
                System.out.println("\t\t\t\t 6. Manage account");
                System.out.println("\t\t\t\t 7. Log out");
                System.out.println("\t\t\t\t 0. EXIT");
                System.out.println("\t\t\t\t**==============================================================**");
                String answer = s.next();
                if (answer.equals("1")) {
                    selling = true;
                    sell();
                } else if (answer.equals("2")) {
                    buyProduct = true;
                    buyProduct();
                } else if (answer.equals("4")) {
                    checkBalance = true;
                    checkBalance();
                } else if (answer.equals("5")) {
                    checkingShoppingCart = true;
                    shoppingCart();
                } else if (answer.equals("6")) {
                    managingAccount = true;
                    manageAccount();
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

        System.out.println("User successfully registered.");
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

                System.out.println("Please type the product name:");
                productName = s.next();
                System.out.println("Please type the product description:");
                String catchline = s.nextLine();
                description = s.nextLine();
                System.out.println("Please type the product price:");
                price = Double.parseDouble(s.next());
                System.out.println("Please type the product stock count:");
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
                System.out.println("Choose a category for your products:");
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

                for (File fileEntry : folder.listFiles()) {
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if (p.getOwnerName().equals(activeUser.getUsername()))
                        System.out.println(p.getProductName());
                }

            }
            if (answer.equals("3")) {
                File folder = new File("src/database/PRODUCTS");
                System.out.println("\t\t\t\t =======BELOW LIE YOUR PRODUCTS=========");
                Scanner scanner = new Scanner(System.in);
                String ans;
                for (File fileEntry : folder.listFiles()) {
                    Product p = Product.ReadFromFile(fileEntry.getAbsolutePath());
                    if (p.getOwnerName().equals(activeUser.getUsername()))
                        System.out.println(p.getProductName());
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
            System.out.println("\t\t\t\t 0. All");
            System.out.println(
                    "\t\t\t\t**==============================================================**");
            System.out.println("Choose a category to list:");
            String category = s.next();

            if (!category.equals("0")) {
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

            //select product
//            for (String string : activeUser.getShoppingCart()) {
//                if (string == null) {
//
//                } else {
//                    System.out.println(string);
//                }
//            }

            System.out.println("\t\t\t\t Press 0 to go back");
            answer = s.next();
            if (answer.equals("0")) {
                buyProduct = false;
            } else {
                shoppingCart();
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
                if (string == null) {

                } else {
                    System.out.println(string);
                }

            }
            System.out.println("\t\t\t\t Press 0 to go back");
            answer = s.next();
            if (answer.equals("0")) {
                checkingShoppingCart = false;
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
            System.out.println("\t\t\t\t 2. Set payment password");
            System.out.println("\t\t\t\t 3. Delete account");
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
                    System.out.println("\t\t\t\t Enter a new username:");
                    String newUsername = s.next();
                    activeUser.updateUsername(newUsername);
                    System.out.println("\t\t\t\t Username changed successfully!");
                }
                if (answer.equals("2")) {
                    System.out.println("\t\t\t\t Please enter your password to confirm.");
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
        System.out.println("\t\t\t\t**==============================================================**");
        System.out.println("- Product name: " + product.getProductName());
        System.out.println("- Product category: " + product.getCategory());
        System.out.println("- Price: RM " + String.format("%.2f", product.getPrice()));
        System.out.println("- Stock: " + product.getStockCount());
        System.out.println("- Description: " + product.getDescription());
        System.out.println("- Product reviews: \n");

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
                buyProduct = false;
            }

            System.out.println("Please enter the delivery address");
            String address = s.next();

            OrderItem orderItems = new OrderItem(quantity, product);
            OrderItem[] orders = new OrderItem[]{orderItems};

            Order order = new Order(activeUser.getUsername(), product.getOwnerName(), address, orders);
            product.alterStockCount(quantity);

            //if balance not enough validator
            if (!(activeUser.getBalance() < order.getTotalPrice())) {
                order.saveToFile(order);
                activeUser.setBalance(order.deductWallet(activeUser.getBalance(), activeUser.getUsername()));
                System.out.println("Purchased successfully! Thank you.");
            } else {
                System.out.println("Balance is not enough! Please top up and try again.");
            }

            buyProduct = false;
        }
    }
}