package com.marsol.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ScaleService {

    Logger logger = LoggerFactory.getLogger(ScaleService.class);
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$"
    );
    @Value("${ipScales}")
    public String ipScales;

    public ScaleService() {

    }

    public List<String> getListIps(){
        try{
            String[] ips = ipScales.split(",");
            List<String> listIps = new ArrayList<>();
            for(String ip : ips){
                String trimmedIp = ip.trim();
                //Validar que sea un formato válido de IP
                if(!IPV4_PATTERN.matcher(trimmedIp).matches()){
                    logger.error("La IP {} no tiene un formato válido, no se tomará en cuenta.", trimmedIp);
                }else{
                    listIps.add(trimmedIp);
                }
            }
            if(!listIps.isEmpty()){
                return listIps;
            }else{
                //logger.error("Error, no se identificaron IPs validas.");
                throw new RuntimeException("Error, no se identificaron IPs validas.");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error, no se identificaron IPs validas.");
        }

    }
}
