package com.nickmafra.demo.service;

import com.nickmafra.demo.infra.jms.EnviadorFila;
import com.nickmafra.demo.model.Mensagem;
import com.nickmafra.demo.repository.MensagemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;
    @Autowired
    private EnviadorFila enviadorFila;
    @Autowired
    private DateService dateService;

    public Page<Mensagem> listar(Pageable pageable) {
        return mensagemRepository.findAll(pageable);
    }

    public void enviar(Mensagem mensagem) {
        mensagem.setDataEnvio(dateService.agora());
        enviadorFila.enviarMensagem(mensagem);
    }

    public void salvar(Mensagem mensagem) {
        log.debug("Salvando mensagem.");
        mensagem.setDataRecebimento(dateService.agora());
        mensagemRepository.save(mensagem);
    }
}
