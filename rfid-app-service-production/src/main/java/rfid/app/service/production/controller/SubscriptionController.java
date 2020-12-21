package rfid.app.service.production.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rfid.app.service.common.model.Subscription;
import rfid.app.service.common.repository.SubscriptionRepository;

import javax.transaction.Transactional;

@RestController
public class SubscriptionController {
    private final SubscriptionRepository subscriptionRepository;

    SubscriptionController(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    @PostMapping(value = "/subscribe")
    public Subscription subscribe(@RequestBody Subscription subscription) {
        subscriptionRepository.deleteAll();
        subscriptionRepository.save(subscription);
        return subscription;
    }

    @PostMapping(value = "/unsubscribe")
    public void unsubscribe(@RequestBody Subscription subscription) {
        subscriptionRepository.deleteById(subscription.getEndPoint());
    }
}
