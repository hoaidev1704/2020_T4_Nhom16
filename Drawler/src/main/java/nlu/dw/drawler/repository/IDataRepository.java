package nlu.dw.drawler.repository;

import nlu.dw.drawler.model.DataRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDataRepository extends CrudRepository<DataRecord, String> {}
