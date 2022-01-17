import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControllerHome extends Controller{
    //fxml
    @FXML
    private Hyperlink username_display, username_current;
    @FXML
    private TextField search,product_name,product_price,product_stock, product_desc;
    @FXML
    private ChoiceBox<String> category_select, sort_choice, category_choice, sort_updown;
    @FXML
    private ImageView profpic, productpic;
    @FXML
    private Label productname, productprice, productdesc, salescount, stockleft, username, email, name_error, price_error, stock_error, search_lbl;
    @FXML
    private ListView<String> list, usr_lst, prdct_lst;
    @FXML
    private VBox vbox;

    private String[] category_list = {"Sports and Outdoor", "Games and Hobbies", "Machines and Gadgets", 
    "Fashion and Accessories (men)",  "Fashion and Accessories (women)", "Home and Living", "Other"};
    private String[] sort_list = {"Alphabetical", "Price"};
    private String[] category_show = {"All", "Sports and Outdoor", "Games and Hobbies", "Machines and Gadgets", 
    "Fashion and Accessories (men)",  "Fashion and Accessories (women)", "Home and Living", "Other"};
    private String[] updownn_list = {"Ascending", "Descending"};

    private static ArrayList<String> list_items;
    private static String picpath, sort_chosen, category_chosen, ascending_descending, srch, display_user = current_user;
    private static Image pic;
    private static boolean updown;

    ObservableList<String> list_view, srch_usr_view, srch_prdct_view;

    FileChooser upload = new FileChooser();

    public void initialize(){
        username_display.setText(username_current);
        try {
            category_select.getItems().addAll(category_list);
        } catch (NullPointerException e0) {
        try {
            productname.setText(active_product.getProductName());
            productprice.setText("Price: $ "+String.format("%.2f",active_product.getPrice()));
            salescount.setText("Amount sold: "+Integer.toString(active_product.getSalesCount()));
            stockleft.setText("Stock left: "+Integer.toString(active_product.getStockCount()));
            productdesc.setText(active_product.getDescription());
            productpic.setImage(pic);
        } catch (NullPointerException e1) {
        try {
            category_choice.getItems().addAll(category_show);
            sort_choice.getItems().addAll(sort_list);
            sort_updown.getItems().addAll(updownn_list);
            category_choice.setOnAction(this::sortingmethod);
            sort_choice.setOnAction(this::sortingmethod);
            sort_updown.setOnAction(this::sortingmethod);

            sortingaction();
        } catch (NullPointerException e2) {
        try {
            User usr = (User) User.ReadFromFile("src/database/USERNAMES/"+display_user);
            username.setText(usr.getUsername());
            email.setText(usr.getEmail());
        } catch (NullPointerException e3) {
        try {
            search_lbl.setText("Search results for: "+srch);
            searchingaction(srch);
        } catch(NullPointerException e4){
        //try new
        }}}}}
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
    public void tomyprofile(ActionEvent event) throws IOException{
        display_user = current_user;
        changeScene("profile.fxml");
    }
    //*products
    public void tomyproducts(ActionEvent event) throws IOException{
        changeScene("myproduct.fxml");
    }
    public void toaddproduct(ActionEvent event) throws IOException{
        changeScene("addproduct.fxml");
    }
    public void toviewproduct(ActionEvent event)throws IOException{
        changeScene("product.fxml");
    }
    public void toallproducts(ActionEvent event) throws IOException{
        sort_chosen = "Alphabetical";
        category_chosen = "All";
        ascending_descending = "Ascending";
        changeScene("productlist.fxml");
    }
    //*favourites etc
    public void tofavourite(ActionEvent event) throws IOException{
        changeScene("favourites.fxml");
    }
    public void tocart(ActionEvent event) throws IOException{
        changeScene("cart.fxml");
    }
    public void tobalance(ActionEvent event) throws IOException{
        changeScene("balance.fxml");
    }
//search method
    public void searchbutton(ActionEvent event) throws IOException{
        srch = search.getText();
        System.out.println(srch);
        changeScene("searchresult.fxml");
    }
    public void searchingaction(String srch){
        File Productfolder = new File("src/database/PRODUCTS");
        int i = 0;
        int length = Productfolder.listFiles().length;
        String[] productNameList = new String[length];
        String[] sellerNameList = new String[length];
        ArrayList<String> temp_prdct = new ArrayList<String>(), temp_user = new ArrayList<String>();
        String str=srch.trim();
        for(File fileEntry : Productfolder.listFiles()){
            Product k = (Product) Product.ReadFromFile(fileEntry.getAbsolutePath());
            productNameList[i] = k.getProductName();
            sellerNameList[i]=k.getOwnerName();
            i++;
        }
        for(int l=0; l<productNameList.length-1;l++){
            if(sellerNameList[l].equalsIgnoreCase(str)){
                temp_user.add(sellerNameList[l]);
            }
            srch_usr_view = FXCollections.observableArrayList(temp_user);
            GridPane pane_1 = new GridPane();
            Label item_1 = new Label();
            pane_1.add(item_1, 0, 0);
            usr_lst.setItems(srch_usr_view);
            usr_lst.setCellFactory(param -> new SrchCell());
            //list.getItems().addAll(list_items);
            usr_lst.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    display_user = (usr_lst.getSelectionModel().getSelectedItem());
                    try {
                        changeScene("profile.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            if(productNameList[l].equalsIgnoreCase(str)){
                temp_prdct.add(productNameList[l]);
            }
            srch_prdct_view = FXCollections.observableArrayList(temp_prdct);
            GridPane pane_2 = new GridPane();
            Label item_2 = new Label();
            pane_2.add(item_2, 0, 0);
            prdct_lst.setItems(srch_prdct_view);
            prdct_lst.setCellFactory(param -> new Cell());
            //list.getItems().addAll(list_items);
            prdct_lst.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    current_product = (prdct_lst.getSelectionModel().getSelectedItem());
                    updateproduct();
                    try {
                        changeScene("product.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            if (!sellerNameList[l].equalsIgnoreCase(str) && !productNameList[l].equalsIgnoreCase(str)){
                System.out.println("Product or Seller Not Found");
            }
        }
    }
    public void create_product(ActionEvent event) throws IOException{
        Double price = 0.0;
        int stockCount = 0;
        String description = "Product description";
        String productName = product_name.getText();
        try {
            price = Double.parseDouble(product_price.getText());
            price_error.setVisible(false);
        } catch (NumberFormatException e) {
            price_error.setVisible(true);
        } try {
            stockCount = Integer.parseInt(product_stock.getText());
            stock_error.setVisible(false);
        } catch (NumberFormatException e) {
            stock_error.setVisible(true);
        }
        int salescount = 0;
        description = product_desc.getText();
        String category = category_select.getValue();
        if (productName.isBlank()) {
            name_error.setVisible(true);
        } else {
            name_error.setVisible(false);
        }
        if (category.equals("Choose category")) {
            category = "Other";
        }
        if (!productName.isBlank() && !category.equals("Choose category") && price!=0.0 && stockCount!=0) {
            Product createdProduct = new Product(productName,description,price,stockCount,salescount,category,current_user);
            Product.SaveToFile(createdProduct);
            User.SaveToFile(active_user);
            
            File file = new File(picpath);
            BufferedImage bi = ImageIO.read(file);
            File picfolder = new File("src/database/PICTURES/PRODUCTS/"+productName+".png");
            ImageIO.write(bi, "png", picfolder);
            current_product = productName;
            updateproduct();
            updateuser();
            changeScene("product.fxml");
        }
    }
    public void upload_product(){
        upload.setTitle("Choose picture...");
        Stage stg = (Stage) vbox.getScene().getWindow();
        File pic_product = upload.showOpenDialog(stg);
        if (pic_product!=null){
            picpath = pic_product.getAbsolutePath();
            pic = new Image(picpath);
            productpic.setImage(pic);
            Rectangle clip = new Rectangle(200, 200);
            if (pic.getHeight()>pic.getWidth()) {
                productpic.setFitWidth(200);
                productpic.setFitHeight(pic.getHeight());
                productpic.setClip(clip);
            } else {
                productpic.setFitHeight(200);
                productpic.setFitWidth(pic.getWidth());
                productpic.setClip(clip);
            }
        }
    }
    public void sortingmethod(ActionEvent e){
        ascending_descending = sort_updown.getValue();
        sort_chosen = sort_choice.getValue();
        category_chosen = category_choice.getValue();
        sortingaction();
    }
    public void sortingaction(){
        list_view = null;
        list_items = new ArrayList<String>();
        File folder = new File("src/database/PRODUCTS");
        int length = folder.listFiles().length;
        Product[] Parr = new Product[length];
        if (ascending_descending.equalsIgnoreCase("Descending")){
            updown = true;
        } else {
            updown = false;
        }
        if (sort_chosen.equalsIgnoreCase("Price")){
            Parr = active_product.sortPrice(updown);
        }else{
            Parr = active_product.sortAZ(updown);
        }
        if (category_chosen.equalsIgnoreCase("All")) {
            for(int i=0; i<length ; i++){
                String s = Parr[i].getProductName();
                list_items.add(s);
            }
        }else {
            for(int i=0; i<length ; i++){
                if (category_chosen.equalsIgnoreCase(Parr[i].getCategory())){
                    String s = Parr[i].getProductName();
                    list_items.add(s);
                }
            }
        }
        list_view = FXCollections.observableArrayList(list_items);
        GridPane pane = new GridPane();
        Label item = new Label();
        pane.add(item, 0, 0);
        list.setItems(list_view);
        list.setCellFactory(param -> new Cell());
        //list.getItems().addAll(list_items);

        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                current_product = (list.getSelectionModel().getSelectedItem());
                updateproduct();
                try {
                    changeScene("product.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    static class Cell extends ListCell<String>{
        HBox box = new HBox();
        Pane pane = new Pane();
        Label label = new Label("text");
        ImageView image = new ImageView();
        //private static Image pic_update = pictures.updatepic("picture_placeholder_123");
        
        public Cell(){
            super();
            label.setTextFill(Color.WHITE);
            pane.setBackground(new Background(new BackgroundFill(Color.rgb(32, 32, 32), null, null)));
            try{
                box.getChildren().addAll(image, label, pane);
                box.setBackground(new Background(new BackgroundFill(Color.rgb(32, 32, 32), null, null)));
                //box.setHgrow(pane, Priority.ALWAYS);
            } catch (NullPointerException e){
                throw e;
            }
        }
        public void updateItem(String item_, boolean empty_){
            super.updateItem(item_, empty_);
            setText(null);
            setGraphic(null);

            if (item_ != null && !empty_) {
                image.setImage(updatepic(item_));
                Rectangle clip = new Rectangle(50, 50);
                if (pic.getHeight()>pic.getWidth()) {
                    image.setPreserveRatio(true);
                    image.setFitWidth(50);
                    image.setFitHeight(pic.getHeight());
                    image.setClip(clip);
                } else {
                    image.setPreserveRatio(true);
                    image.setFitHeight(50);
                    image.setFitWidth(pic.getWidth());
                    image.setClip(clip);
                }
                Product p = (Product) Product.ReadFromFile("src/database/PRODUCTS/"+item_);
                String price_string = String.format("%.2f",p.getPrice());
                label.setText("\t"+item_+"\n\tPrice: $ "+price_string+"\n\tCategory: "+p.getCategory());
                setGraphic(box);
            }
        }
    }
    public static Image updatepic(String img){
        File picfile = new File("src/database/PICTURES/PRODUCTS/"+img+".png");
        picpath = picfile.getAbsolutePath();
        pic = new Image("file:///"+picpath);
        return pic;
    }
    static class SrchCell extends ListCell<String>{
        HBox box = new HBox();
        Pane pane = new Pane();
        Label label = new Label("text");
        ImageView image = new ImageView();
        //private static Image pic_update = pictures.updatepic("picture_placeholder_123");
        
        public SrchCell(){
            super();
            label.setTextFill(Color.WHITE);
            pane.setBackground(new Background(new BackgroundFill(Color.rgb(32, 32, 32), null, null)));
            try{
                box.getChildren().addAll(/*image,*/ label, pane);
                box.setBackground(new Background(new BackgroundFill(Color.rgb(32, 32, 32), null, null)));
                //box.setHgrow(pane, Priority.ALWAYS);
            } catch (NullPointerException e){
                throw e;
            }
        }
        public void updateItem(String item_, boolean empty_){
            super.updateItem(item_, empty_);
            setText(null);
            setGraphic(null);

            if (item_ != null && !empty_) {
                //image.setImage(updatepic(item_));
                //image.setFitHeight(50);
                User u = (User) User.ReadFromFile("src/database/USERNAMES/"+item_);
                label.setText(u.getUsername());
                setGraphic(box);
            }
        }
    }
    
}

