package cl.duoc.ETF.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_MENSAJES =
            "etf.cursos.queue";

    public static final String EXCHANGE_MENSAJES =
            "etf.cursos.exchange";

    public static final String ROUTING_KEY_MENSAJES =
            "etf.cursos.key";

    @Bean
    public Queue mensajesQueue() {
        return QueueBuilder
                .durable(QUEUE_MENSAJES)
                .build();
    }
/**direct */
    @Bean
    public DirectExchange mensajesExchange() {
        return new DirectExchange(
                EXCHANGE_MENSAJES
        );
    }

    @Bean
    public Binding mensajesBinding(
            Queue mensajesQueue,
            DirectExchange mensajesExchange
    ) {
        return BindingBuilder
                .bind(mensajesQueue)
                .to(mensajesExchange)
                .with(ROUTING_KEY_MENSAJES);
    }

    @Bean
public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter(
            "com.sumativa.etf.dto"
    );
}

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        RabbitTemplate rabbitTemplate =
                new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(
                jsonMessageConverter
        );

        rabbitTemplate.setReceiveTimeout(5000);

        return rabbitTemplate;
    }
}
