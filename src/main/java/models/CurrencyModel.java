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

import static messages.StaticMessage.createErrorAlertDialog;


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
            service.saveEntity(typeCurrency);
        }
    }

    public void delete(Currency currency) {
        CurrencyService currencyService = new CurrencyService();
        currencyService.delete(currency);
        list_currency.remove(currency);
    }

    public void removeTypeCurrency(String selectedItem) {
        TypeCurrencyService typeCurrencyService = new TypeCurrencyService();
        TypeCurrency byType = typeCurrencyService.findByType(selectedItem);
        list_types.remove(byType.getType());
        typeCurrencyService.delete(byType);
    }

    public void addNewRelations(String first_value, String second_value) throws Exception {
        CurrencyService currencyService = new CurrencyService();
        ArrayList<Currency> list = (ArrayList<Currency>) currencyService.findListByDate(LocalDate.now());
        if (list.isEmpty()) {
            currency = new Currency();
            currency.setDate(LocalDate.now());
            for (String rel : list_name_columns) {
                relations = new Relations();
                relations.setRelation(rel);
                relations.setCurrency(currency);
                currency.getRelations_list().add(relations);
            }
            createRelations(currency, first_value, second_value);
            currencyService.saveEntity(currency);
            list_currency.add(currency);
        } else {
            for (int i = 0; i < list.size(); i++) {
                Currency cur = list.get(i);
                int j = list_currency.indexOf(cur);
                createRelations(cur, first_value, second_value);
                currencyService.updateEntity(cur);
                list_currency.set(j, cur);
            }
        }
    }

    private void createRelations(Currency currency, String first_value, String second_value) throws Exception {
        relations = new Relations();
        relations.setRelation(first_value + "/" + second_value);
        relations.setCurrency(currency);
        relations.setValue("");
        boolean added = currency.getRelations_list().add(relations);
        if (added)
            list_name_columns.add(relations.getRelation());
        else {
            throw new Exception();
        }
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

    public void updateCurrency(Currency currency) {
        CurrencyService service = new CurrencyService();
        Currency old = service.findById(currency.getId());
        int j = list_currency.indexOf(old);
        old.setDate(currency.getDate());
        old.setRelations_list(currency.getRelations_list());
        service.updateEntity(old);
        list_currency.set(j, old);
    }

    public void deleteRelations(String relation) {
        CurrencyService service = new CurrencyService();
        for (int i = 0; i < list_currency.size(); i++) {
            Currency currency = list_currency.get(i);
            int index = list_currency.indexOf(currency);
            relations = currency.getRelations_list().stream().filter(x -> x.getRelation().equals(relation)).findAny().orElse(null);
            currency.getRelations_list().remove(relations);
            service.updateEntity(currency);
            list_currency.set(index,currency);
        }
        list_name_columns.remove(relation);
    }
}























