package nlu.dw.drawler.model;

import lombok.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataRecord {
    private UUID id;
    private LocalDateTime getDate, updateDate;
    private String urlSource, currency, bank, currencySymbol, currencyName;
    private double buyCash, buyTransfer, price;

}
