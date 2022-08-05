package vttp2022.workshop17.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.workshop17.models.Currency;
import vttp2022.workshop17.models.Query;
import vttp2022.workshop17.service.CurrencySvc;

@Controller
@RequestMapping(path = "/exchange")
public class RatesExchangeController {
    private static final Logger logger = LoggerFactory.getLogger(RatesExchangeController.class);

    @Autowired
    private CurrencySvc currySvc;

    @GetMapping
    public String exchange(@RequestParam(required = true) String to,
            @RequestParam(required = true) String from, @RequestParam(required = true) String amount,
            Model model) {
        Query q = new Query();
        q.setTo(to);
        q.setFrom(from);
        q.setAmount(new BigDecimal(amount));
        Optional<Currency> optCurry = currySvc.convertExchangeRates(q);
        if (optCurry.isEmpty()) {
            model.addAttribute("currency", new Currency());
            return "exchange";
        }
        logger.info("<<<<<" + q.getFrom() + "****" + q.getTo());
        model.addAttribute("currency", optCurry.get());
        model.addAttribute("fromCurry", q.getFrom());
        model.addAttribute("toCurry", q.getTo());
        return "exchange";
    }
    
}
