package entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table (name = "dataobjects")
@NoArgsConstructor
@Getter
@Setter
public class DataObject implements BaseEntity{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String from_location;
    private String to_project;
    private String currency;
    private String NCC;
    private String type;
    private LocalDate date;
    private String budget;
    private String amount;
    private String description;
    private String relations;
    private String category;
    private String status;


    public DataObject(String from_location, String to_project, String currency, String NCC, String type, LocalDate date, String budget, String amount, String description) {
        this.from_location = from_location;
        this.to_project = to_project;
        this.currency = currency;
        this.NCC = NCC;
        this.type = type;
        this.date = date;
        this.budget = budget;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataObject that = (DataObject) o;
        return Objects.equals(from_location, that.from_location) && Objects.equals(to_project, that.to_project) && Objects.equals(currency, that.currency) && Objects.equals(NCC, that.NCC) && Objects.equals(type, that.type) && Objects.equals(date, that.date) && Objects.equals(budget, that.budget) && Objects.equals(amount, that.amount) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from_location, to_project, currency, NCC, type, date, budget, amount, description);
    }
}
