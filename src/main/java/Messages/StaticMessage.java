package Messages;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static textConst.StringConst.TITLE;


public class StaticMessage {

    private static Stage stage;
    private static Alert alert;


    public static void createErrorAlertDialog(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        String title_for_alert = "Error!";
        alert.setTitle(title_for_alert);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        if(stage != null)
        closeProgressBar("Error!");
        alert.showAndWait();
    }

    public static void createSaveUserAlertDialog(String message) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        String title_for_alert = "Добавление новых данных в базу!";
        alert.setTitle(title_for_alert);
        alert.setHeaderText("Запись добавлена!");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public static void createInfoDialog(String message, Parent root) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        String title_for_alert = "Information!";
        alert.getDialogPane().setStyle("-fx-font-family: Segoe UI; -fx-font-size: 16;-fx-font-weight: Bold;");
        alert.setTitle(title_for_alert);
        alert.setHeaderText("Information!");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public static boolean createConfirmationDialog(String message, String name) {
        alert = new Alert(Alert.AlertType.WARNING, "Вы точно хотите удалить " + message + " " + name + " ?", new ButtonType("Да"), new ButtonType("Нет"));
        alert.showAndWait();
        if (alert.getResult().getText().equals("Да")) {
            return true;
        } else if (alert.getResult().getText().equals("Нет")) {
            return false;
        }
        return false;
    }

    public static boolean createQuestionsDialog(String message) {
        alert = new Alert(Alert.AlertType.NONE, "Вы точно хотите " + message + " ?", new ButtonType("Да"), new ButtonType("Нет"));
        alert.showAndWait();
        if (alert.getResult().getText().equals("Yes")) {
            return true;
        } else if (alert.getResult().getText().equals("No")) {
            return false;
        }
        return false;
    }


    public static void showProgressBar() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        Label label = new Label();
        label.setText("Пожалуйста, дождитесь окончания операции");
        root.getChildren().addAll(label, progressIndicator);
        label.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 350, 75);
        stage = new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void closeProgressBar(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(stage!=null)
                stage.close();
                createInfoDialog(message, null);
            }
        });
    }


}
