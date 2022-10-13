package nlu.dw.drawler.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RecordAudit {
    private String id, fileName;
    private LocalDateTime  startDate, endDate;
    private int recordNumber;
    private boolean status;
}
