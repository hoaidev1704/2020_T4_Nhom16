package nlu.dw.drawler.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "DataCrawl")
@Getter
@Setter
@Builder
@AllArgsConstructor()
@NoArgsConstructor
public class DataRecord {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private LocalDateTime getDate, updateDate;
    private String urlSource, currency, bank, currencySymbol, currencyName;
    private double buyCash, buyTransfer, price;

}
