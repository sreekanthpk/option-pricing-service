package com.sree.price;

import com.sree.model.StockPrice;
import com.sree.model.Tick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@EnableScheduling
public class PriceSoketController {

    List<StockPrice> stock = new CopyOnWriteArrayList<>();


    @MessageMapping("/price")
    @SendTo("/topic/stock")
    public Tick stock(StockPrice price) throws Exception {
        stock.add(price);
        return getTick(price);

    }

    private Tick getTick(StockPrice price) throws IOException {
        URL url = new URL("https://finance.yahoo.com/quote/"+price.getStock());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        int begin = content.indexOf("<span class=\"Trsdu(0.3s) Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(b)\"");
        String s = content.substring(begin);
        int end = s.indexOf("</span>");
        int begin2 = s.indexOf(">");
        String quote = s.substring(begin2+1,end);
        double quoteValue = Double.valueOf(HtmlUtils.htmlEscape(quote).replace(",",""));
        double callPrice = BlackScholes.callPrice(quoteValue, price.getStrike(),price.getInterestRate(),price.getImpliedVolatility(),price.getTime());
        double putPrice = BlackScholes.putPrice(quoteValue, price.getStrike(),price.getInterestRate(),price.getImpliedVolatility(),price.getTime());

        return new Tick( price.getStock(), quoteValue, price.getStrike(), putPrice, callPrice);
    }

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedRate = 1000)
    public void greeting() throws InterruptedException, IOException {

        for(StockPrice p:stock){
            this.template.convertAndSend("/topic/stock", getTick(p));
        }
    }

}