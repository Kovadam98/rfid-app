package rfid.app.service.production.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import rfid.app.service.production.dto.Result;
import rfid.app.service.production.dto.ProductDto;

@Service
public class NotificationService {

    private final SimpMessagingTemplate template;

    public NotificationService(SimpMessagingTemplate template){
        this.template = template;
    }

    public void notifyNewComponents(ProductDto productDto){
        this.template.convertAndSend("/notif/items", productDto);
    }

    public void notifyMessage(Result result) {
        this.template.convertAndSend("/notif/result", result);
    }
}
