package nl.dw.currency.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Exchange")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exchange {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private LocalDateTime getDate, updateDate;
	private String urlSource;
	private double buyCash, buyTransfer, price;
	@ManyToOne(cascade = CascadeType.ALL)
	private Bank bank;
	@ManyToOne(cascade = CascadeType.ALL)
	private Currency currency;
}
