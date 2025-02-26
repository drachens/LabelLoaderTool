package com.marsol.application;

import com.marsol.domain.LabelService;
import com.marsol.domain.ScaleService;
import com.marsol.infrastructure.integration.SyncDataLoader;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelController {

    Logger logger = LoggerFactory.getLogger(LabelController.class);

    private final LabelService labelService;
    private SyncDataLoader syncDataLoader;
    private ScaleService scaleService;

    @Autowired
    public LabelController(LabelService labelService, SyncDataLoader syncDataLoader, ScaleService scaleService) {
        this.labelService = labelService;
        this.syncDataLoader = syncDataLoader;
        this.scaleService = scaleService;
    }

    public void loadLabels(){
        try{
            List<String> labelsFiles = labelService.getLabelsFiles();
            logger.info("LabelsFiles: {}", labelsFiles);
            List<String> listIps = scaleService.getListIps();
            logger.info("ListIps: {}", listIps);
            for(String ip : listIps){
                for(String label : labelsFiles){
                    try{
                        if(syncDataLoader.loadFormatLabel(label, ip) &&
                                syncDataLoader.loadBackgroundLabel(label, ip) &&
                                syncDataLoader.loadFileLabel(label, ip)){
                            logger.info("Etiqueta {} cargada en balanza {}", label, ip);
                        }
                    }catch (Exception e){
                        logger.error(e.getMessage());
                    }
                }
            }
        }catch(RuntimeException e){
            logger.error(e.getMessage());
        }
    }
}
