package com.escuela.alumno_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion del productor RabbitMQ (alumno-service).
 * Declara el exchange, la cola y el binding, y el converter JSON usado para enviar los eventos.
 */
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "escuela.exchange";
    public static final String INSCRIPCIONES_QUEUE = "inscripciones.queue";
    public static final String ROUTING_KEY_INSCRIPCION = "alumno.inscripto";

    @Bean
    public TopicExchange escuelaExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue inscripcionesQueue() {
        return new Queue(INSCRIPCIONES_QUEUE, true);
    }

    @Bean
    public Binding inscripcionesBinding(Queue inscripcionesQueue, TopicExchange escuelaExchange) {
        return BindingBuilder.bind(inscripcionesQueue).to(escuelaExchange).with(ROUTING_KEY_INSCRIPCION);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
