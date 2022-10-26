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
    public enum STATUS {
        SUCESS, FAIL
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String fileName;
    private LocalDateTime  startDate, endDate;
    private int recordNumber;
    @Enumerated(EnumType.STRING)
    private STATUS status;
    @Column(name = "data", columnDefinition = "TEXT")
    private String content;
    
}
