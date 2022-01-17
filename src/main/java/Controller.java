import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Controller extends HelloApplication{
    @FXML
    private TextField usernameField;
    @FXML
    private Label user_dont_exist;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label incorrect_password;
    @FXML
    private TextField new_email;
    @FXML
    private TextField new_username;
    @FXML
    private PasswordField create_password;
    @FXML
    private PasswordField confirm_password;
    @FXML
    private Label username_taken;
    @FXML
    private Label password_not_match;
    @FXML
    private Label email_taken;
    @FXML
    private Label password_blank;
    @FXML
    private Hyperlink username_display;
    @FXML
    private Label username_label;
    @FXML
    private TextField product_name;
    @FXML
    private TextField product_price;
    @FXML
    private TextField product_stock;
    @FXML
    private TextField product_desc;
    @FXML
    private ChoiceBox<String> category_select;
    @FXML
    private TextField search;
    @FXML
    private ImageView profpic;

    public static String current_user;
    public static String current_product;
    public static User active_user = new User("Guest", null, null);
    public static Product active_product = new Product("productName", "description", 1.5, 1, 1, "category", "ownerName");

    public void updateuser() {
        active_user = (User) User.ReadFromFile("src/database/USERNAMES/"+current_user);
    }
    public void updateproduct(){
        active_product = (Product) Product.ReadFromFile("src/database/PRODUCTS/"+current_product);
    }

//go to different pages
    public void userSignUp(ActionEvent event) throws IOException{
        tosignup("signup.fxml");
    }
    public void bacckbutton(ActionEvent event) throws IOException {
        tosignup("login.fxml");
    }
    //*logout and home
    public void logout(ActionEvent event) throws IOException {
        setlogin(false);
        tosignup("login.fxml");
    }
    public void tohome(ActionEvent event)throws IOException{
        changeScene("home.fxml");
    }
    //*profile
    public void toprofile(ActionEvent event) throws IOException{
        changeScene("profile.fxml");
    }
    //*products
    public void toallproducts(ActionEvent event) throws IOException{
        changeScene("productlist.fxml");
    }
    public void tomyproducts(ActionEvent event) throws IOException{
        changeScene("myproduct.fxml");
    }
    public void toaddproduct(ActionEvent event) throws IOException{
        changeScene("addproduct.fxml");
    }
    //*favourites
    public void tofavourite(ActionEvent event) throws IOException{
        changeScene("favourites.fxml");
    }

//login method
    public void userLogin(ActionEvent event) throws IOException {
        //check for login credentials first
        String login_username = usernameField.getText();
        String login_password = passwordField.getText();
        current_user = login_username;
        File folder = new File("src/database/USERNAMES/");
        for(File fileEntry : folder.listFiles()){
            User u = (User) User.ReadFromFile(fileEntry.getPath());
            //check username
            if (login_username.isBlank()){
                user_dont_exist.setVisible(true);
                user_dont_exist.setText("*Please enter a username");
            } else if(!login_username.equals(u.getUsername())){
                user_dont_exist.setVisible(true);
                user_dont_exist.setText("*User do not exist, create an account");
            }else if(login_username.equals(u.getUsername())){
                user_dont_exist.setVisible(false);
            }
            //check password
            if(!login_password.equals(u.getPassword())){
                incorrect_password.setVisible(true);
            }else if(login_password.equals(u.getPassword())){
                incorrect_password.setVisible(false);
            }
            if(!login_password.equals(u.getPassword()) && !login_username.equals(u.getUsername())){
                incorrect_password.setVisible(false);
            }
            //login if all green
            if(login_username.equals(u.getUsername()) && login_password.equals(u.getPassword())) {
                setlogin(true);
                current_user=login_username;
                updateuser();
                tohome(event);
            }
        }
    }

//register method
    public void Register_signup(ActionEvent event){
        String email_register = new_email.getText();
        String username_register = new_username.getText();
        String password_register = create_password.getText();
        String password_check = confirm_password.getText();
        boolean existance_ = true;
        File folder = new File("src/database/USERNAMES/");
        for(File fileEntry : folder.listFiles()){
            User u = (User) User.ReadFromFile(fileEntry.getPath());
            //all green
            if(!username_register.equals(u.getUsername()) && !email_register.equals(u.getEmail()) && !password_register.isBlank() && password_register.equals(password_check)){
                //visuals
                email_taken.setVisible(false);
                username_taken.setVisible(false);
                password_blank.setVisible(false);
                password_not_match.setVisible(false);
                existance_ = false;
            }
            //username check
            if(username_register.isBlank()){
                username_taken.setVisible(true);
                username_taken.setText("*Please enter a username");
                existance_=true;
            }else if(username_register.equals(u.getUsername())){
                username_taken.setVisible(true);
                username_taken.setText("*Username already Taken");
                existance_=true;
            }else if(!username_register.equals(u.getUsername())){
                username_taken.setVisible(false);
            }
            //email check
            if(email_register.isBlank()){
                email_taken.setVisible(true);
                email_taken.setText("*Please enter an e-mail");
                existance_=true;
            }else if(email_register.equals(u.getEmail())){
                email_taken.setVisible(true);
                email_taken.setText("*E-mail already exists");
                existance_=true;
            }else if(!email_register.equals(u.getEmail())){
                email_taken.setVisible(false);
            }if (email_register.contains("@") || email_register.contains(".com")) {
                email_taken.setVisible(false);
            }else if (email_register.contains("@") && email_register.contains(".com")){
                email_taken.setVisible(true);
                email_taken.setText("*Please enter a valid e-mail address");
                existance_=true;
            }
            //password check
            if(password_register.isBlank()){
                password_blank.setVisible(true);
                password_blank.setText("*Please enter a password");
                existance_=true;
            }else if (!password_register.isBlank()) {
                password_blank.setVisible(false);
            }
            //confirm password check
            if(!password_register.equals(password_check)){
                password_not_match.setVisible(true);
                existance_=true;
            }else if(password_register.equals(password_check)){
                password_not_match.setVisible(false);
            }
        }
        if(!existance_){
            //write to database
            User user = new User(username_register, password_register, email_register);
            User.SaveToFile(user);
            //go to home
            try {
                tosignup("registersuccess.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
