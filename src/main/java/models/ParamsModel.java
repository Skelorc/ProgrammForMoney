package models;

import entity.Params;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import service.ParamsService;

import java.util.*;

import static textConst.StringConst.*;


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
                if(!params.getValues().contains(value)) {
                    params.getValues().add(value);
                    ObservableList<String> strings = map_params.get(params.getName());
                    strings.add(value);
                    Collections.sort(strings);
                    paramsService.saveOrUpdate(params);
                    map_params.put(params.getName(), strings);
                }
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
        map_params.put(FROM,FXCollections.observableArrayList(new ArrayList<>()));
        map_params.put(TO,FXCollections.observableArrayList(new ArrayList<>()));
        map_params.put(CURRENCY,FXCollections.observableArrayList(new ArrayList<>()));
        map_params.put(NCC,FXCollections.observableArrayList(new ArrayList<>()));
        map_params.put(TYPE,FXCollections.observableArrayList(new ArrayList<>()));
        map_params.put(RELATIONS,FXCollections.observableArrayList(new ArrayList<>()));
        map_params.put(BUDGET,FXCollections.observableArrayList(new ArrayList<>()));
        map_params.put(STATUS,FXCollections.observableArrayList(new ArrayList<>()));
        map_params.put(CATEGORY,FXCollections.observableArrayList(new ArrayList<>()));
        List<Params> allValues = paramsService.getAllValues();
        for (Params x : allValues) {
            Set<String> values = x.getValues();
            if(!values.isEmpty()) {
                ArrayList<String> list = new ArrayList<>(values);
                Collections.sort(list);
                map_params.put(x.getName(), FXCollections.observableArrayList(new ArrayList<>(list)));
            }
        }
    }

    public ObservableList<String> getParamsFromWord(String word) {
        ObservableList<String> list = map_params.get(word);
        return list;
    }

    public boolean checkParams(String params, String value) {
        ObservableList<String> strings = map_params.get(params);
        if(strings != null && !strings.isEmpty())
        {
            if(strings.contains(value))
            {
                return true;
            }
        }
        else
            return false;
        return false;
    }
}
