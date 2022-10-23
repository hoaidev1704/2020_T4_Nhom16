package nl.dw.currency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor()
@NoArgsConstructor
public class DataCrawler {
    private String id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime getDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
    private String urlSource, currency, bank, currencySymbol, currencyName;
    private double buyCash, buyTransfer, price;

}
