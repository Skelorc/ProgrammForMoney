package models;

import entity.DataObject;
import entity.Params;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

import static Messages.StaticMessage.createErrorAlertDialog;

@Getter
public class Model {
    private final DataModel dataModel = new DataModel();
    private final ParamsModel paramsModel = new ParamsModel();

    public void saveNewDataObject(ComboBox<String> cb_to, ComboBox<String> cb_from, ComboBox<String> cb_currency, ComboBox<String> cb_cc,
                                  ComboBox<String> cb_type, DatePicker datePicker, ComboBox<String> cb_budget, ComboBox<String> cb_amount,
                                  TextField description,
                                  ComboBox<String> cb_relations, ComboBox<String> cb_category, ComboBox<String> cb_status)
    {
        try{
            String to = cb_to.getSelectionModel().getSelectedItem();
            String from = cb_from.getSelectionModel().getSelectedItem();
            String currency = cb_currency.getSelectionModel().getSelectedItem();
            String cc = cb_cc.getSelectionModel().getSelectedItem();
            String type = cb_type.getSelectionModel().getSelectedItem();
            LocalDate value = datePicker.getValue();
            String budget = cb_budget.getSelectionModel().getSelectedItem();
            String amount = cb_amount.getSelectionModel().getSelectedItem();
            String desc = description.getText();
            dataModel.createNewData(to,from,currency,cc,type,value,budget,amount,desc);
            if(cb_relations.getSelectionModel().getSelectedItem() != null)
            {
                dataModel.getDataObject().setRelations(cb_relations.getSelectionModel().getSelectedItem());
            }
            if(cb_category.getSelectionModel().getSelectedItem() != null)
            {
                dataModel.getDataObject().setCategory(cb_category.getSelectionModel().getSelectedItem());
            }
            if(cb_status.getSelectionModel().getSelectedItem() != null)
            {
                dataModel.getDataObject().setStatus(cb_status.getSelectionModel().getSelectedItem());
            }
            dataModel.createNewData();
        }

        catch (NullPointerException e)
        {
            createErrorAlertDialog("Error create new Object! You must select all obligatory params!");
        }

    }

    public List<DataObject> getAllDataObjects()
    {
        return dataModel.getAllData();
    }

    public void saveNewParams(ComboBox<String> box,TextField textField)
    {
        if(!textField.getText().isEmpty())
        {
            paramsModel.saveParams(box.getId(), textField.getText());
        }
        else {
            createErrorAlertDialog("Empty fields - " + textField.getId());
            throw new NullPointerException("Empty Fields!");
        }
    }


    public Params getParamsByWord(String word)
    {
        return paramsModel.getParamsFromWord(word);
    }

    public List<Params> getAllParams()
    {
       return  paramsModel.getAllParams();
    }

    public void removeParams(ComboBox<String> comboBox)
    {
        String name_params = comboBox.getId();
        String value = comboBox.getSelectionModel().getSelectedItem();
        try {
            paramsModel.deleteParams(name_params, value);
        }
        catch (NullPointerException e)
        {
            createErrorAlertDialog("Error! Please, set value in " + comboBox.getPromptText());
        }
    }
}
