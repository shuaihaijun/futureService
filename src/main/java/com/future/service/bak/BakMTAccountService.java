package com.future.service.bak;
import com.future.common.util.DateUtil;
import com.jfx.Broker;
import com.jfx.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Deprecated
@Service
public class BakMTAccountService {

    Logger log = LoggerFactory.getLogger(BakMTAccountService.class);
    @Value("${userTermServerHost}")
    String userTermServerHost;
    @Value("${userTermServerPort}")
    int userTermServerPort;

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
            strategy.connect(userTermServerHost,userTermServerPort,broker,username,password);
            log.info(username+" :--- connect end   ---:"+ DateUtil.getCurrDateTime());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
        return true;
    }

    /**
     * 断开MT连接
     * @param strategy
     * @param broker
     * @param username
     * @param password
     * @return
     */
    public boolean disConnect(Strategy strategy,Broker broker,String username,String password){
        if(ObjectUtils.isEmpty(strategy)){
            strategy=new Strategy();
        }
        try {
            log.info(username+" :--- connect begin ---:"+ DateUtil.getCurrDateTime());
            strategy.connect(userTermServerHost,userTermServerPort,broker,username,password);
            log.info(username+" :--- connect end   ---:"+ DateUtil.getCurrDateTime());
            strategy.close(true);
            log.info(username+" :--- disconnect end   ---:"+ DateUtil.getCurrDateTime());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
        return true;
    }

}
