package com.marsol.application;

import com.marsol.domain.LabelService;
import com.marsol.domain.ScaleService;
import com.marsol.infrastructure.adapter.SyncManager;
import com.marsol.infrastructure.integration.SyncDataLoader;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelController {

    @Value("${directory}")
    private String directory;

    Logger logger = LoggerFactory.getLogger(LabelController.class);

    private final LabelService labelService;
    private SyncDataLoader syncDataLoader;
    private final ScaleService scaleService;

    @Autowired
    public LabelController(LabelService labelService, ScaleService scaleService) {
        this.labelService = labelService;
        this.scaleService = scaleService;
    }

    public void loadLabels(){
        try{
            List<String> labelsFiles = labelService.getLabelsFiles();
            logger.info("LabelsFiles: {}", labelsFiles);
            List<String> listIps = scaleService.getListIps();
            logger.info("ListIps: {}", listIps);
            for(String ip : listIps){
                syncDataLoader = new SyncDataLoader();
                syncDataLoader.loadFormatLabel(directory,ip,111);
                syncDataLoader.loadFileLabel(directory,ip,111);
                syncDataLoader.loadBackgroundLabel(directory, ip, 111);
            }
        }catch(RuntimeException e){
            logger.error(e.getMessage());
        }
    }
}
