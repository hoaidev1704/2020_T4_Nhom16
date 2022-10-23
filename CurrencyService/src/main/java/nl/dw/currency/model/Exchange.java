package nl.dw.currency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
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
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime getDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;
	private String urlSource;
	private double buyCash, buyTransfer, price;
	@ManyToOne(cascade = CascadeType.ALL)
	private Bank bank;
	@ManyToOne(cascade = CascadeType.ALL)
	private Currency currency;
}
