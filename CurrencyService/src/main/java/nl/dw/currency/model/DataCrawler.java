package nl.dw.currency.model;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor()
@NoArgsConstructor
public class DataCrawler {
    private String id;
    private LocalDateTime getDate, updateDate;
    private String urlSource, currency, bank, currencySymbol, currencyName;
    private double buyCash, buyTransfer, price;

}
