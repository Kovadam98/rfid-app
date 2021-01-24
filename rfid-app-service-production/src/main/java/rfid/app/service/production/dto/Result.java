package rfid.app.service.production.dto;

import java.util.HashSet;
import java.util.Set;

public class Result {

    private ResultType type;
    private Set<String> messages = new HashSet<String>();


    public ResultType getType() {
        return type;
    }

    public void setType(ResultType type) {
        this.type = type;
    }

    public Set<String> getMessages() {
        return messages;
    }

    public void setMessages(Set<String> messages) {
        this.messages = messages;
    }
}
