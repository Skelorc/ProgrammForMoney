package views;

import entity.Project;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import lombok.Getter;
import org.controlsfx.control.CheckComboBox;
import views.tables.ProjectTable;

import java.util.Map;

import static models.Model.getModel;

@Getter
public class ProjectView extends AbstractView{
    @FXML
    protected ComboBox<String> cb_from, cb_to, cb_relations,cb_category,cb_status;
    @FXML
    protected CheckComboBox<String> ccb_from, ccb_to, ccb_relations, ccb_category, ccb_status;
    @FXML
    protected Button btn_add,btn_show_all,btn_clear_fields, btn_find, btn_remove ,
                    btn_export, btn_import;
    @FXML
    private TableView<Project> tableView_project;

    @Override
    protected void addValuesToComboBox() {
        Map<String, ObservableList<String>> allParams = getModel().getAllParams();
        cb_from.setItems(allParams.get("from"));
        ccb_from.getItems().addAll(allParams.get("from"));
        cb_to.setItems(allParams.get("to"));
        ccb_to.getItems().addAll(allParams.get("to"));
        cb_relations.setItems(allParams.get("relations"));
        ccb_relations.getItems().addAll(allParams.get("relations"));
        cb_category.setItems(allParams.get("category"));
        ccb_category.getItems().addAll(allParams.get("category"));
        cb_status.setItems(allParams.get("status"));
        ccb_status.getItems().addAll(allParams.get("status"));
    }

    @Override
    protected void createTable() {
        table = new ProjectTable();
        table.createTableView(tableView_project);
    }

    protected void showFilteredProjectsList()
    {
        tableView_project.setItems(getModel().getFilteredProjects());
    }

    protected void showAll()
    {
        tableView_project.setItems(getModel().getProjectForTable(tableView_project.getId()));
    }

    @Override
    public void clearFields() {
        cb_to.getSelectionModel().clearSelection();
        cb_from.getSelectionModel().clearSelection();
        cb_relations.getSelectionModel().clearSelection();
        cb_category.getSelectionModel().clearSelection();
        cb_status.getSelectionModel().clearSelection();
    }

    @Override
    protected void addComboBoxToList() {

    }


}
