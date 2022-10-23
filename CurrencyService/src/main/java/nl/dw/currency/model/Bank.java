package nl.dw.currency.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Bank", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
}
