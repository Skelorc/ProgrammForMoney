package views;

import javafx.scene.control.TextField;
import views.tables.Table;

abstract class AbstractView implements View{
    protected Table table;

    @Override
    public void initData() {
        createTable();
        addComboBoxToList();
        addValuesToComboBox();
    }

    protected abstract void addValuesToComboBox();
    protected abstract void addComboBoxToList();
    protected abstract void createTable();

    protected void clearTextField(TextField textfield) {
        textfield.clear();
    }

}
