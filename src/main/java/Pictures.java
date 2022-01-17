import java.io.File;

import javafx.scene.image.Image;

public class Pictures {
    private static String picpath;
    private static Image pic;

    public Image updatepic(String current_product){
        File picfile = new File("src/database/PICTURES/PRODUCTS/"+current_product+".png");
        picpath = picfile.getAbsolutePath();
        pic = new Image("file:///"+picpath);
        return pic;
    }
}
