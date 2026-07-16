package cl.duoc.ETF.service;


import cl.duoc.ETF.config.RabbitMQConfig;
import cl.duoc.ETF.dto.MensajeCursoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MensajeProductorService {

    private final RabbitTemplate rabbitTemplate;

    public MensajeProductorService(
            RabbitTemplate rabbitTemplate
    ) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensaje(
            MensajeCursoDTO mensaje
    ) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_MENSAJES,
                RabbitMQConfig.ROUTING_KEY_MENSAJES,
                mensaje
        );

        System.out.println(
                "Mensaje enviado a RabbitMQ."
        );

        System.out.println(
                "Tipo de evento: "
                        + mensaje.getTipoEvento()
        );

        System.out.println(
                "Estudiante: "
                        + mensaje.getEstudianteId()
        );
    }
}
