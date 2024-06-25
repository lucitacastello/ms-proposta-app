package com.github.lucitacastello.proposta_app.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    //filas
    //proposta cadastrada tem que ser consumida pelo ms-analise-credito
    @Bean
    public Queue criarFilaPropostaPendenteMsAnaliseCredito(){
        //fila durável - não perde os dados mesmo se o RabbitMQ cair
        // pode criar as filas em cada MS,
        // serão criadas todas as filas aqui
        return QueueBuilder.durable("proposta-pendente.ms-analise-credito").build();
    }

    //serviço de notificação manda SMS sobre a proposta cadastrada com sucesso
    @Bean
    public Queue criarFilaPropostaPendenteMsNotificacao(){

        return QueueBuilder.durable("proposta-pendente.ms-notificacao").build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsProposta(){

        return QueueBuilder.durable("proposta-concluida.ms-proposta").build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsNotificacao(){

        return QueueBuilder.durable("proposta-concluida.ms-notificacao").build();
    }

    // o spring já cria uma implementação de ConnectionFactory
    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory){

        return new RabbitAdmin(connectionFactory);
    }

    // recebe um RabbitAdmin como parâmetro
    //passando um Evento para o Listener e inicializa o RabbitAdmin
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
        //para o Java ter permissões para realizar operações no modo RabbitMQ
    }



}
