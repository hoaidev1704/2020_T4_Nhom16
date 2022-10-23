package nl.dw.currency.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Currency", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames="symbol"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String name;
}
