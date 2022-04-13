import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static textConst.StringConst.TITLE;

public class StartApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage.setTitle(TITLE);
        Scene sc = new Scene(root, 1168, 783);
        sc.getStylesheets().addAll(StartApp.class.getResource("/css/style.css").toExternalForm());
        stage.setScene(sc);
       //stage.getIcons().add(new Image(StartApp.class.getResource("/image/logo.png").toExternalForm()));
        stage.setResizable(false);
        stage.show();
    }
}
