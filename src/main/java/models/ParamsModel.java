package models;

import entity.Params;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import service.ParamsService;

import java.util.List;
import java.util.Set;


@Getter
@Setter
public class ParamsModel {

    private Params params;
    private ObservableList<String> observableListParams;

    public void saveParams(String name, String value ) {
        ParamsService paramsService = new ParamsService();
            params = paramsService.findParamsByName(name);
            if (params == null) {
                params = new Params();
                params.setName(name);
                params.getValues().add(value);
                paramsService.saveEntity(params);
            } else {
                params.getValues().add(value);
                paramsService.updateEntity(params);
            }
    }

    public ObservableList<String> getParamsFromWord(String word)
    {
        ParamsService paramsService = new ParamsService();
        Params paramsByName = paramsService.findParamsByName(word);
        Set<String> values = paramsByName.getValues();
        observableListParams = FXCollections.observableArrayList(values);
        return observableListParams;
    }

    public List<Params> getAllParams() {
        ParamsService paramsService = new ParamsService();
        return paramsService.getAllValues();
    }

    public void deleteParams(String params, String value) {
        ParamsService paramsService = new ParamsService();
        paramsService.deleteValueFromParams(params, value);
    }
}
