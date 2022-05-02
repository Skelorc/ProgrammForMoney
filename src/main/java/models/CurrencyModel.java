package models;

import entity.Currency;
import entity.Relations;
import entity.TypeCurrency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import service.CurrencyService;
import service.TypeCurrencyService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class CurrencyModel {

    private Currency currency;
    private Relations relations;
    private ObservableList<Currency> list_currency;
    private ObservableList<String> list_name_columns;
    private ObservableList<String> list_types;

    public CurrencyModel() {
        addData();
    }

    public void createTypeCurrency(String type) {
        TypeCurrencyService service = new TypeCurrencyService();
        TypeCurrency typeCurrency = service.findByType(type);
        if (typeCurrency == null) {
            typeCurrency = new TypeCurrency();
            typeCurrency.setType(type);
            list_types.add(type);
            service.saveOrUpdate(typeCurrency);
        }
    }
    public void removeTypeCurrency(String selectedItem) {
        TypeCurrencyService typeCurrencyService = new TypeCurrencyService();
        TypeCurrency byType = typeCurrencyService.findByType(selectedItem);
        list_types.remove(byType.getType());
        typeCurrencyService.delete(byType);
    }

    public void addNewRelations(String first_value, String second_value) throws Exception {
        CurrencyService currencyService = new CurrencyService();
        for (int i = 0; i < list_currency.size(); i++) {
            Currency currency = list_currency.get(i);
            relations = new Relations();
            relations.setRelation(first_value + "/" + second_value);
            relations.setCurrency(currency);
            relations.setValue_relation("");
            boolean added = currency.getRelations_list().add(relations);
            if (added) {
                list_name_columns.add(relations.getRelation());
                currencyService.saveOrUpdate(currency);
                list_currency.set(i, currency);
            } else {
                throw new Exception("Error! Already exists!");
            }
        }
    }


    public void deleteRelations(String relation) {
        CurrencyService service = new CurrencyService();
        for (int i = 0; i < list_currency.size(); i++) {
            Currency currency = list_currency.get(i);
            int index = list_currency.indexOf(currency);
            relations = currency.getRelations_list().stream().filter(x -> x.getRelation().equals(relation)).findAny().orElse(null);
            currency.getRelations_list().remove(relations);
            service.saveOrUpdate(currency);
            list_currency.set(index, currency);
        }
        list_name_columns.remove(relation);
    }


    private void addData() {
        CurrencyService currencyService = new CurrencyService();
        TypeCurrencyService typeCurrencyService = new TypeCurrencyService();
        List<TypeCurrency> allTypeCurrencyList = typeCurrencyService.getAll();
        list_currency = FXCollections.observableList(currencyService.getAllCurrency());
        list_types = FXCollections.observableArrayList(new ArrayList<>());
        allTypeCurrencyList.stream().forEach(x -> list_types.add(x.getType()));
        createNamesForColumns(list_currency);
    }


    private void createNamesForColumns(List<Currency> allCurrency) {
        list_name_columns = FXCollections.observableList(new ArrayList());
        for (int i = 0; i < allCurrency.size(); i++) {
            Set<Relations> relations_list = allCurrency.get(i).getRelations_list();
            for (Relations r : relations_list) {
                if (!list_name_columns.contains(r.getRelation()))
                    list_name_columns.add(r.getRelation());
            }
        }
    }

    public void addCurrencyToList(Currency currency) {
        list_currency.add(currency);
    }

    public void updateCurrencyDate(Currency currency, LocalDate localDate) {
        Currency old = list_currency.stream().filter(x -> x.getId() == currency.getId()).findAny().orElse(null);
        old.setDate(localDate);
        CurrencyService service = new CurrencyService();
        service.saveOrUpdate(old);
    }


    public void updateCurrencyRelations(Currency currency, Relations relations, String relation) {
        System.out.println(currency.getId());
        for (int i = 0; i < list_currency.size(); i++) {
            Currency from_list = list_currency.get(i);
            if(from_list.getId()== currency.getId())
            {
                from_list.getRelations_list().remove(relations);
                relations.setValue_relation(relation);
                from_list.getRelations_list().add(relations);
                from_list.setDate(currency.getDate());
                from_list.setRelations_list(currency.getRelations_list());
                CurrencyService service = new CurrencyService();
                service.saveOrUpdate(from_list);
            }
        }
    }

    public void delete(int index) {
        currency = list_currency.get(index);
        CurrencyService currencyService = new CurrencyService();
        currencyService.delete(currency);
        list_currency.remove(index);
        currency = null;
    }

    public void addListCurrencyFromExcel(List<Currency> list) {
        CurrencyService currencyService = new CurrencyService();
        for (int i = 0; i < list.size(); i++) {
            Currency currency = list.get(i);
            Currency byId = currencyService.findById(currency.getId());
            if(byId != null) {
                byId.setDate(currency.getDate());
                byId.setRelations_list(currency.getRelations_list());
                currencyService.saveOrUpdate(byId);
            }
            else
                currencyService.saveOrUpdate(currency);
        }
        list_currency.clear();
        list_currency.addAll(FXCollections.observableList(currencyService.getAllCurrency()));
    }
}























