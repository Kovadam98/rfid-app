package rfid.app.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rfid.app.backend.model.Subscription;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription,String> {
}
