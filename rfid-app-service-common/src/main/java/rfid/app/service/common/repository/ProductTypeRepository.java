package rfid.app.service.common.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rfid.app.service.common.model.ProductType;

import java.util.List;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType,Integer> {
    @Query(
            "SELECT DISTINCT productType FROM ProductType productType " +
                    "INNER JOIN FETCH productType.componentTypes componentTypes " +
                    "INNER JOIN FETCH componentTypes.colorTypes")
    List<ProductType> findAll();

    @Query(
            "SELECT productType FROM ProductType productType " +
                    "JOIN FETCH productType.componentTypes componentTypes " +
                    "JOIN FETCH componentTypes.colorTypes " +
                    "WHERE productType.id = :id")
    ProductType findById(@Param("id") int id);
}
