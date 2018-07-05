package server.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import server.mqtt.SonoffMessageHandler;

@Configuration
@IntegrationComponentScan
public class MQTTConfig {

    @Value("${mqtt.host}")
    private String mqttHost;

    @Value("${mqtt.client}")
    private String mqttClient;

    @Value("${mqtt.password}")
    private String mqttPassword;

    @Bean
    @Scope(value = "singleton")
    @Qualifier("mqttInputChannel")
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    @Scope(value = "singleton")
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(mqttHost, mqttClient,
                        "tele/sonoff/+/SENSOR", "tele/sonoff/+/STATE");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    public IntegrationFlow mqttInFlow(SonoffMessageHandler sonoffMessageHandler) {
        return IntegrationFlows.from(mqttInputChannel())
                .handle(sonoffMessageHandler)
                .get();
    }

    @Bean
    @Scope(value = "singleton")
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(mqttHost);
        factory.setUserName(mqttClient);
        factory.setPassword(mqttPassword);
        return factory;
    }

    @Bean
    @Qualifier("mqttOutboundChannel")
    @Scope(value = "singleton")
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @Scope(value = "singleton")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(mqttClient, mqttClientFactory());
        messageHandler.setAsync(true);
        return messageHandler;
    }

    @Bean
    public IntegrationFlow mqttOutFlow() {
        return IntegrationFlows.from(mqttOutboundChannel())
                .handle(mqttOutbound())
                .get();
    }
}
