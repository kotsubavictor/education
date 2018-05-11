package server.controller.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import server.WebAppConst;
import server.data.Equipment;
import server.data.HelloMessage;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo(WebAppConst.WEBSOCKET_TOPIC_EQUIPMENT)
    public Equipment greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Equipment("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!", 22);
    }
}