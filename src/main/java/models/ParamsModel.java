package models;

import entity.Params;
import lombok.Getter;
import lombok.Setter;
import service.ParamsService;

import java.util.List;


@Getter
@Setter
public class ParamsModel {

    private ParamsService paramsService = new ParamsService();;
    private Params params;

    public void saveParams(String name, String value ) {
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

    public Params getParamsFromWord(String word)
    {
        return paramsService.findParamsByName(word);
    }

    public List<Params> getAllParams() {
        return paramsService.getAllValues();
    }

    public void deleteParams(String params, String value) {
        paramsService.deleteValueFromParams(params, value);
    }
}
