package rfid.app.backend.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import rfid.app.backend.dto.ProductDto;
import rfid.app.backend.dto.Result;

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
