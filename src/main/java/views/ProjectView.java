package views;

import entity.Project;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import lombok.Getter;
import views.tables.ProjectTable;

import java.util.Map;

import static models.Model.getModel;

@Getter
public class ProjectView extends AbstractView{
    @FXML
    protected ComboBox<String> cb_to,cb_from,cb_relations,cb_category,cb_status;
    @FXML
    protected Button btn_add, btn_remove;
    @FXML
    private TableView<Project> tableView;

    @Override
    protected void addValuesToComboBox() {
        ObservableList<String> relations = getModel().getColumns();
        Map<String, ObservableList<String>> allParams = getModel().getAllParams();
        cb_to.setItems(allParams.get("to"));
        cb_from.setItems(allParams.get("from"));
        cb_relations.setItems(relations);
        cb_category.setItems(allParams.get("category"));
        cb_status.setItems(allParams.get("status"));
    }



    @Override
    protected void createTable() {
        table = new ProjectTable();
        table.createTableView(tableView);
    }

    @Override
    public void clearFields() {

    }

    @Override
    protected void addComboBoxToList() {

    }


}
