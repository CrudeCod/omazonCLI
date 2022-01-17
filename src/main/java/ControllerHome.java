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
    private Hyperlink username_display;
    @FXML
    private TextField search,product_name,product_price,product_stock, product_desc;
    @FXML
    private ChoiceBox<String> category_select, sort_choice, category_choice, sort_updown;
    @FXML
    private ImageView profpic, productpic;
    @FXML
    private Label productname, productprice, productdesc, salescount, stockleft, username, email, name_error, price_error, stock_error;
    @FXML
    private ListView<String> list;
    @FXML
    private VBox vbox;

    private String[] category_list = {"Sports and Outdoor", "Games and Hobbies", "Machines and Gadgets", 
    "Fashion and Accessories (men)",  "Fashion and Accessories (women)", "Home and Living", "Other"};
    private String[] sort_list = {"Alphabetical", "Price"};
    private String[] category_show = {"All", "Sports and Outdoor", "Games and Hobbies", "Machines and Gadgets", 
    "Fashion and Accessories (men)",  "Fashion and Accessories (women)", "Home and Living", "Other"};
    private String[] updownn_list = {"Ascending", "Descending"};

    private static ArrayList<String> list_items;
    private static String picpath, sort_chosen, category_chosen, ascending_descending;
    private static Image pic;
    private static boolean updown;

    Pictures pictures = new Pictures();

    ObservableList<String> list_view;

    FileChooser upload = new FileChooser();

    public void initialize(){
        username_display.setText(current_user);
        try {
            category_select.getItems().addAll(category_list);
        } catch (NullPointerException e0) {
        try {
            productname.setText(active_product.getProductName());
            productprice.setText("Price: $ "+String.format("%.2f",active_product.getPrice()));
            salescount.setText("Amount sold: "+Integer.toString(active_product.getSalesCount()));
            stockleft.setText("Stock left: "+Integer.toString(active_product.getStockCount()-active_product.getSalesCount()));
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
            username.setText(active_user.getUsername());
            email.setText(active_user.getEmail());
        } catch (NullPointerException e3) {
            //TODO: handle exception
        }}}}
    }
    //*logout and home
    public void logout(ActionEvent event) throws IOException {
        setlogin(false);
        tosignup("login.fxml");
    }
    public void tohome(ActionEvent event)throws IOException{
        changeScene("home.fxml",1);
    }
    //*profile
    public void toprofile(ActionEvent event) throws IOException{
        changeScene("profile.fxml",5);
    }
    //*products
    public void tomyproducts(ActionEvent event) throws IOException{
        changeScene("myproduct.fxml",1);
    }
    public void toaddproduct(ActionEvent event) throws IOException{
        changeScene("addproduct.fxml",2);
    }
    public void toviewproduct(ActionEvent event)throws IOException{
        changeScene("product.fxml",3);
    }
    public void toallproducts(ActionEvent event) throws IOException{
        sort_chosen = "Alphabetical";
        category_chosen = "All";
        ascending_descending = "Ascending";
        changeScene("productlist.fxml", 4);
    }
    //*favourites
    public void tofavourite(ActionEvent event) throws IOException{
        changeScene("favourites.fxml",1);
    }
//search method
    public void searchbutton(ActionEvent event) throws IOException{
        String srch = search.getText();
        System.out.println(srch);
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
            changeScene("product.fxml",3);
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
                current_product =  (list.getSelectionModel().getSelectedItem());
                updateproduct();
                try {
                    changeScene("product.fxml", 3);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
    static class Cell extends ListCell<String>{
        HBox box = new HBox();
        Pane pane = new Pane();
        Label label = new Label("text");
        static Pictures pictures = new Pictures();
        //private static ImageView image = new ImageView();
        //private static Image pic_update = pictures.updatepic("picture_placeholder_123");
        
        public Cell(){
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
                //pic_update = pictures.updatepic(item_);
                //image.setImage(pic_update);
                //image.setFitHeight(50);
                Product p = (Product) Product.ReadFromFile("src/database/PRODUCTS/"+item_);
                String price_string = String.format("%.2f",p.getPrice());
                label.setText("\t"+item_+"\n\tPrice: $ "+price_string+"\n\tCategory: "+p.getCategory());
                setGraphic(box);
            }
        }
    }
}
