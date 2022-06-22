package dto;

import entity.Project;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static textConst.StringConst.*;

@Getter
@Setter
public class Transaction {
    private String from;
    private String to;
    private String relations;
    private String NCC;
    private String type;
    private Set<Transfer> transfers_set = new HashSet<>();

    public Transaction(Project project) {
        this.from = project.getFrom();
        this.to = project.getTo();
        this.relations = project.getRelations();
        this.NCC = project.getNCC();
        this.type = project.getType();
    }

    public Transaction() {
    }

    public void addTransferFromProject(Project project) {
        Transfer transfer = new Transfer(project);
        transfers_set.add(transfer);
    }

    public Integer getValueByMonth(Month month) {
        Transfer transfer = transfers_set.stream()
                .filter(x -> x.getDate().getMonth().equals(month))
                .findFirst()
                .orElse(null);
        if (transfer != null)
            return transfer.getAmount();
        else
            return null;
    }

    public Integer getValueByDate(LocalDate report_date, LocalDate date_by_value) {
        Transfer transfer = transfers_set.stream().filter(x -> x.getDate().equals(date_by_value) && x.getName_table().equals(ID_TABLE_DATA)
                        || (x.getDate().getMonth()
                        .equals(date_by_value.getMonth()) && x.getName_table().equals(ID_TABLE_AVERAGE))
                        && (date_by_value.isAfter(report_date) || date_by_value.equals(report_date)))
                        .findFirst()
                        .orElse(null);
        if (transfer != null)
            return transfer.getAmount();
        return null;
    }

    public Integer getValueByYear(LocalDate year)
    {
        int result = 0;
        List<Transfer> values_by_year = transfers_set.stream()
                .filter(x -> x.getDate().getYear() == year.getYear())
                .collect(Collectors.toList());
        for (Transfer transfer : values_by_year) {
            result +=transfer.getAmount();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(relations, that.relations) && Objects.equals(NCC, that.NCC) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, relations, NCC, type);
    }
}
