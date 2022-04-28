package models;

import entity.Params;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import lombok.Getter;
import lombok.Setter;
import service.ParamsService;

import java.util.*;
import java.util.stream.Collectors;


@Getter
@Setter
public class ParamsModel {

    private Params params;
    private Map<String, ObservableList<String>> map_params;

    public void saveParams(String name, String value ) {
        ParamsService paramsService = new ParamsService();
            params = paramsService.findParamsByName(name);
            if (params == null) {
                params = new Params();
                String name_param = name.substring(name.lastIndexOf("_")+1);
                params.setName(name_param);
                params.getValues().add(value);
                paramsService.saveOrUpdate(params);
                map_params.put(params.getName(), FXCollections.observableList(new ArrayList<>(params.getValues())));
            } else {
                params.getValues().add(value);
                paramsService.saveOrUpdate(params);
                ObservableList<String> strings = map_params.get(params.getName());
                strings.add(value);
                map_params.put(params.getName(), strings);
            }

    }

    public void deleteParams(String params, String value) {
        ParamsService paramsService = new ParamsService();
        paramsService.deleteValueFromParams(params, value);
        ObservableList<String> strings = map_params.get(params);
        strings.remove(value);
        map_params.put(params,strings);
    }

    public ParamsModel() {
        ParamsService paramsService = new ParamsService();
        map_params = new HashMap<>();
        List<Params> allValues = paramsService.getAllValues();
        for (Params x : allValues) {
            if(!x.getValues().isEmpty())
            map_params.put(x.getName(), FXCollections.observableArrayList(new ArrayList<>(x.getValues())));
        }
    }

    public ObservableList<String> getParamsFromWord(String word) {
        ObservableList<String> list = map_params.get(word);
        return list;
    }


}
