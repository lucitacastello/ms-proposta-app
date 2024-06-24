package com.github.lucitacastello.proposta_app.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    //filas
    //proposta cadastrada tem que ser consumida pelo ms-analise-credito
    public Queue criarFilaPropostaPendenteMsAnaliseCredito(){
        //fila durável - não perde os dados mesmo se o RabbitMQ cair
        // pode criar as filas em cada MS,
        // serão criadas todas as filas aqui
        return QueueBuilder.durable("proposta-pendente.ms-analise-credito").build();
    }

    //serviço de notificação manda SMS sobre a proposta cadastrada com sucesso
    public Queue criarFilaPropostaPendenteMsNotificacao(){

        return QueueBuilder.durable("proposta-pendente.ms-notificacao").build();
    }

    public Queue criarFilaPropostaConcluidaMsProposta(){

        return QueueBuilder.durable("proposta-concluida.ms-proposta").build();
    }

    public Queue criarFilaPropostaConcluidaMsNotificacao(){

        return QueueBuilder.durable("proposta-concluida.ms-notificacao").build();
    }

}
