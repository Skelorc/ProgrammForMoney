package models;

import entity.DataObject;
import lombok.Getter;
import service.DataService;

import java.time.LocalDate;
import java.util.List;


@Getter
public class DataModel {

   private DataObject dataObject;

    public List<DataObject> getAllData()
    {
        DataService dataService = new DataService();
        List<DataObject> allDataObjects = dataService.getAll();
        return allDataObjects;
    }

    public void createNewData()
    {
        DataService dataService = new DataService();
        dataService.saveEntity(dataObject);
    }

    public void createNewData(String to, String from, String currency, String cc, String type, LocalDate value, String budget, String amount, String desc) {
        dataObject = new DataObject(to,from,currency,cc,type,value,budget,amount,desc);
    }
}
