package br.com.moser.controller;

import br.com.moser.model.Cambio;
import br.com.moser.service.CambioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Juliano Moser
 */
@Slf4j
@Tag(name = "Cambio endpoint")
@RestController
@RequestMapping("cambio-service")
public class CambioController {

    @Autowired
    private Environment environment;

    @Autowired
    private CambioService cambioService;

    @Operation(summary = "Get cambio from currency")
    @GetMapping("/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable("amount") BigDecimal amount,
                            @PathVariable("from") String from,
                            @PathVariable("to") String to) {

        log.info("getCambio is called whit -> {}, {} and {}", amount, from, to);

        var cambio = cambioService.findByFromAndTo(from, to);
        var port = environment.getProperty("local.server.port");

        BigDecimal conversionFactor = cambio.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);

        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        cambio.setEnvironment(port);

        return cambio;
    }
}
