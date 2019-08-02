package com.future.service.mt;
import com.future.common.util.DateUtil;
import com.jfx.Broker;
import com.jfx.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class MTAccountService {

    Logger log = LoggerFactory.getLogger(MTAccountService.class);
    @Value("${termServerHost}")
    String termServerHost;
    @Value("${termServerPort}")
    int termServerPort;

    /**
     * 连接Mt服务器
     * @param strategy
     * @param broker
     * @param username
     * @param password
     * @return
     */
    public boolean getConnect(Strategy strategy,Broker broker,String username,String password){
        if(ObjectUtils.isEmpty(strategy)){
            strategy=new Strategy();
        }
        try {
            log.info(username+" :--- connect begin ---:"+ DateUtil.getCurrDateTime());
            strategy.connect(termServerHost,termServerPort,broker,username,password);
            log.info(username+" :--- connect end   ---:"+ DateUtil.getCurrDateTime());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
        return true;
    }

}
