package dto;

import entity.Project;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
public class Transfer {
    long id;
    private String budget;
    private String name_table;
    private LocalDate date;
    private int amount;

    public Transfer(Project project) {
        this.id = project.getId();
        this.budget = project.getBudget();
        this.name_table = project.getName_table();
        this.date = project.getDate();
        this.amount = project.getAmount();
    }

    public Transfer(long id, String budget, String name_table, LocalDate date) {
        this.id = id;
        this.budget = budget;
        this.name_table = name_table;
        this.date = date;
    }

    public Transfer(Transfer transfer) {
        this.id=transfer.getId();
        this.budget=transfer.getBudget();
        this.name_table = transfer.name_table;
        this.date = transfer.getDate();
        this.amount = transfer.getAmount();
    }

    public Transfer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return id == transfer.id && amount == transfer.amount && Objects.equals(budget, transfer.budget) && Objects.equals(name_table, transfer.name_table) && Objects.equals(date, transfer.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, budget, name_table, date, amount);
    }
}
