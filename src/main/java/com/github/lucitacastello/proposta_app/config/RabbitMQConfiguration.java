package com.github.lucitacastello.proposta_app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    //pegando valor da application.properties
    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchange;

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

    //criando Exchange
    @Bean
    public FanoutExchange criarFanoutExchangePropostaPendente(){
        return ExchangeBuilder.fanoutExchange(exchange).build();
    }

    // binding da aplicação
    //passando a fila Queue e a Exchange
    @Bean
    public Binding criarBindingPropostaPendenteMsAnaliseCredito(){
        return BindingBuilder.bind(criarFilaPropostaPendenteMsAnaliseCredito())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaPendenteMsNotificacao(){
        return BindingBuilder.bind(criarFilaPropostaPendenteMsNotificacao())
                .to(criarFanoutExchangePropostaPendente());
    }

    //criando nosso próprio RabbitTemplate - para não ter erro
    @Bean
//    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
    //MessageConverter Interface
    public MessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();

    }
    //simpleMessageConverter - default e não funciona
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}
