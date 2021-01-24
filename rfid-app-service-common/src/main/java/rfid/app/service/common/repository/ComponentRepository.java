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
            "AND component.id IN(:ids)"
    )
    List<Component> getRealComponentsFromIds(@Param("ids") Set<Integer> ids);

    @Query(
            "SELECT DISTINCT component FROM Component component " +
            "INNER JOIN FETCH component.type type " +
            "WHERE component.isReal = true " +
            "AND component.type.id = :typeId"
    )
    List<Component> getNextComponentsByTypeId(@Param("typeId") Integer typeId);
}
