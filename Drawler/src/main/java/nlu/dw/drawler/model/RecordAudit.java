package nlu.dw.drawler.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name ="Audit")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RecordAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String fileName;
    private LocalDateTime  startDate, endDate;
    private int recordNumber;
    private boolean status = true;
}
