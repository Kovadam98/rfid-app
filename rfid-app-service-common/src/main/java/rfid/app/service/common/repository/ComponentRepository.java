package rfid.app.service.common.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rfid.app.service.common.model.Component;

import java.util.List;
import java.util.Set;

@Repository
public interface ComponentRepository extends CrudRepository<Component,Integer> {

    @Query(
            "SELECT DISTINCT component FROM Component component " +
            "WHERE component.isReal = true " +
            "AND component.product IS NULL " +
            "AND component.id IN(:ids)"
    )
    List<Component> getRealComponentsFromIds(@Param("ids") Set<Integer> ids);
}
