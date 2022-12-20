package br.com.moser.service;

import br.com.moser.model.Cambio;
import br.com.moser.repository.CambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Juliano Moser
 */
@Service
public class CambioService {

    @Autowired
    private CambioRepository cambioRepository;

    public Cambio findByFromAndTo(String from, String to) {
        return cambioRepository.findByFromAndTo(from, to)
                .stream().findAny().orElseThrow(() -> new RuntimeException("Currency Unsupported"));
    }
}
