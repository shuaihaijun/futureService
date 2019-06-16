package com.future.service.com;

import com.future.entity.com.FuComBroker;
import com.future.entity.com.FuComServer;
import org.springframework.stereotype.Service;

@Service
public class ServerService {


    /**
     * 根据服务器名称 查询服务器信息
     * @param serverName
     * @return
     */
    public FuComServer findByServerName(String serverName){

        return null;
    }


    /**
     * 保存服务器信息
     * @param fuComServer
     * @return
     */
    public long saveComServer(FuComServer fuComServer){
        return 0;
    }

    /**
     * 保存交易商信息
     * @param fuComBroker
     * @return
     */
    public long saveBroker(FuComBroker fuComBroker){
        return 0;
    }

}
