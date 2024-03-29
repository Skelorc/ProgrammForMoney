package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "relations")
@NoArgsConstructor
@Getter
@Setter
public class Relations implements BaseEntity,Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id",nullable = false)
    private Currency currency;

    private String relation;
    private String value_relation;

    public Relations(Currency currency, String relation, String value_relation) {
        this.currency = currency;
        this.relation = relation;
        this.value_relation = value_relation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relations relations = (Relations) o;
        return Objects.equals(relation, relations.relation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation);
    }

    @Override
    public String toString() {
        return "Relations{" +
                "id=" + id +
                ", currency=" + currency +
                ", relation='" + relation + '\'' +
                ", value='" + value_relation + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
