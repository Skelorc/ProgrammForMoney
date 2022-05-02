package utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class MyFileChooser {

    private static FileChooser fileChooser = new FileChooser();

    public static File createFileChooser() {
        if(fileChooser == null)
            fileChooser = new FileChooser();
        Stage stage = new Stage();
        fileChooser.setTitle("Выберите файл для загрузки");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Файлы Excel","*.xlsx","*.xls")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null)
        {
            return selectedFile;
        }
        else
        {
            return null;
        }
    }


}
