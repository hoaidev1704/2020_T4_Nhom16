package nlu.dw.drawler.repository;

import nlu.dw.drawler.model.RecordAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuditRepository extends JpaRepository<RecordAudit, Long> {
    boolean existsRecordAuditByFileName(String fileName);
}
