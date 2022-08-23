package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import platform.models.Code;

public interface CodeRepository extends CrudRepository<Code, String> {
}
