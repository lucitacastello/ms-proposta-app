package com.github.lucitacastello.proposta_app.agendador;

import com.github.lucitacastello.proposta_app.entities.Proposta;
import com.github.lucitacastello.proposta_app.repository.PropostaRepository;
import com.github.lucitacastello.proposta_app.service.NotificacaoRabbitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PropostaSemIntegracao {

    //implementação que trabalha com Logger
    private final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    private PropostaRepository propostaRepository;

    private NotificacaoRabbitService notificacaoRabbitService;

    //recupera valores setados em application.properties
    private String exchange;

    public PropostaSemIntegracao(PropostaRepository propostaRepository,
                                 NotificacaoRabbitService notificacaoRabbitService,
                                 @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
    }

    //agendador de tarefas
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropstasSemIntegracao() {

        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoRabbitService.notificar(proposta, exchange);
                atualizarProposta(proposta);
            } catch (RuntimeException ex) {
                logger.error(ex.getMessage());
            }
        });
    }

    //refatorando
    private void atualizarProposta(Proposta proposta) {

        proposta.setIntegrada(true);
        propostaRepository.save(proposta);
    }
}
