package entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Project implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "from_location")
    private String from;
    @Column(name = "to_project")
    private String to;

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
    private String name_table;

    public Project(String from, String to, String currency, String NCC, String type, LocalDate date, String budget, String amount, String description, String name_table) {
        this.from = from;
        this.to = to;
        this.currency = currency;
        this.NCC = NCC;
        this.type = type;
        this.date = date;
        this.budget = budget;
        this.amount = amount;
        this.description = description;
        this.name_table = name_table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project that = (Project) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(currency, that.currency) && Objects.equals(NCC, that.NCC) && Objects.equals(type, that.type) && Objects.equals(date, that.date) && Objects.equals(budget, that.budget) && Objects.equals(amount, that.amount) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, currency, NCC, type, date, budget, amount, description);
    }

    public String getMonth()
    {
        return String.valueOf(date.getMonth().getValue());
    }
    public String getYear()
    {
        return String.valueOf(date.getYear());
    }

}
