package rfid.app.service.common.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rfid.app.service.common.model.Product;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {

    @Query(
            "SELECT DISTINCT product FROM Product product " +
            "INNER JOIN FETCH product.components component " +
            "INNER JOIN FETCH component.colorType " +
            "INNER JOIN FETCH component.type " +
            "INNER JOIN product.components conditionComponent " +
            "WHERE conditionComponent.isReal=false " +
            "ORDER BY product.id"
    )
    List<Product> findHasNotRealComponent();

    @Query(
            "SELECT DISTINCT product FROM Product product " +
            "INNER JOIN FETCH product.components component " +
            "INNER JOIN FETCH component.type " +
            "INNER JOIN product.components conditionComponent " +
            "WHERE conditionComponent.id IN(:ids)"
    )
    List<Product> findProductByComponentIds(@Param("ids") Set<Integer> ids);
}
