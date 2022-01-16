import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private static Stage stg;
    private static boolean logged = false;
    public void setlogin(boolean logchk){
        logged = logchk;
    }
    public boolean getlogin(){
        return logged;
    }

    @Override

    public void start(Stage stage) throws IOException {
        boolean log = getlogin();
        if(log==false){
            stg = stage;
            Controller c0 = new Controller();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            fxmlLoader.setController(c0);
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setResizable(false);
            stage.setTitle("Omazon");
            stage.setScene(scene);
            stage.show();
            stage.getIcons().add(new Image("icon.png"));
        }else if(log==true){
            changeScene("Home.fxml",1);
        }
    }
//method for changing scenes
    public void changeScene(String fxml, int type) throws IOException{
        ControllerHome c1 = new ControllerHome();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        fxmlLoader.setController(c1);
        Scene scene = new Scene(fxmlLoader.load(),800,600);
        stg.setResizable(true);
        stg.setScene(scene);
        stg.centerOnScreen();
    }
    public void tosignup(String fxml) throws IOException{
        Controller c0 = new Controller();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        fxmlLoader.setController(c0);
        Scene scene = new Scene(fxmlLoader.load(),600,400);
        stg.setResizable(false);
        stg.setMaximized(false);
        stg.setScene(scene);
        stg.centerOnScreen();
    }
    public static void main(String[] args) {
        launch();
    }
}