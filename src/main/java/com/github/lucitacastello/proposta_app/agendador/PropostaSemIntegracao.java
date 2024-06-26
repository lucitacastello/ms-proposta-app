package com.github.lucitacastello.proposta_app.agendador;

import com.github.lucitacastello.proposta_app.repository.PropostaRepository;
import com.github.lucitacastello.proposta_app.service.NotificacaoRabbitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropostaSemIntegracao {

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

    public void buscarPropstasSemIntegracao() {

        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoRabbitService.notificar(proposta, exchange);
                proposta.setIntegrada(true);
                propostaRepository.save(proposta);
            } catch (RuntimeException ex) {
                System.out.println(ex);
            }
        });
    }
}
