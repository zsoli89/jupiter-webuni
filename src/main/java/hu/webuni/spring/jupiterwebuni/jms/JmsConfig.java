package hu.webuni.spring.jupiterwebuni.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

//    hogy json formatumban menjenek az uzenetek
    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

//    szeretnénk kettő különböző jms szerverhez kapcsolódni

    @Bean
    public ConnectionFactory financeConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        return connectionFactory;
    }

    //TODO: jms-serverben megoldani, hogy 61617-es porton induljon, és utána itt átírni a portot
//    @Bean
//    public ConnectionFactory educationConnectionFactory() {
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
//        return connectionFactory;
//    }

//    @Bean
//    public JmsTemplate educationTemplate(ObjectMapper objectMapper) {
//        JmsTemplate jmsTemplate = new JmsTemplate();
//        jmsTemplate.setConnectionFactory(educationConnectionFactory());
//        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter(objectMapper));
//        return jmsTemplate;
//    }

//    ezek azok a factory amik azokat az objektumokat menedzselik amikre jmsListener annotáciot tettem
//    amikor a jmslistenerrel feliratkozom uzenetekre queuera vagy topicra meg kell mondanom hogy melyik
//    jmslistenerContainerFactoryvel akarok dolgozni
    @Bean
    public JmsListenerContainerFactory<?> financeFactory(ConnectionFactory financeConnectionFactory,
                                                         DefaultJmsListenerContainerFactoryConfigurer configurer) {

        return setPubSubAndDurableSubscription(financeConnectionFactory, configurer, "university-app");
    }


    private JmsListenerContainerFactory<?> setPubSubAndDurableSubscription(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer,
            String clientId) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setClientId(clientId);
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);

        return factory;
    }

//    @Bean
//    public JmsListenerContainerFactory<?> educationFactory(ConnectionFactory educationConnectionFactory,
//                                                           DefaultJmsListenerContainerFactoryConfigurer configurer) {
//
//        return setPubSubAndDurableSubscription(educationConnectionFactory, configurer, "university-app");
//    }

}
