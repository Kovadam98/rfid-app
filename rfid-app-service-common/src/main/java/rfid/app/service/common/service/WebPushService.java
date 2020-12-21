package rfid.app.service.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rfid.app.service.common.model.Subscription;
import rfid.app.service.common.repository.SubscriptionRepository;

@Service
public class WebPushService {
    private final SubscriptionRepository subscriptionRepository;
    private final PushService pushService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public WebPushService(
            SubscriptionRepository subscriptionRepository,
            @Value("${public-key}") String publicKey,
            @Value("${private-key}") String privateKey) {
        this.subscriptionRepository = subscriptionRepository;
        pushService = new PushService();
        try{
            pushService.setPublicKey(publicKey);
            pushService.setPrivateKey(privateKey);
        }
        catch (Exception ignored) { }
    }

    public void send(String message){
        try{
            for (Subscription subscription: subscriptionRepository.findAll()) {
                Notification notification = new Notification(
                        subscription.getEndPoint(),
                        subscription.getPublicKey(),
                        subscription.getAuth(),
                        objectMapper.writeValueAsBytes(message));
                pushService.send(notification);
            }
        } catch(Exception ignored) {}

    }
}
