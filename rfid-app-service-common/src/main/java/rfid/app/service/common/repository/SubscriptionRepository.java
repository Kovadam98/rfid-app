package rfid.app.service.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rfid.app.service.common.model.Subscription;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription,String> {
}
