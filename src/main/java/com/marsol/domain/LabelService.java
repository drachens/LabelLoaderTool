package com.marsol.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {

    Logger logger = LoggerFactory.getLogger(LabelService.class);

    @Value("${directory:sas}")
    private String Dir;


    public LabelService() {
    }

    public List<String> getLabelsFiles(){
        System.out.println("Directory: " + Dir);
        File directory = new File(Dir);
        if(!directory.exists() || !directory.isDirectory()){
            //logger.error("{} no es un directorio.", directory);
            throw new RuntimeException(directory+" no es un directorio.");
        }

        //Filtrar archivos formato .lbl
        String[] files = directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".lbl");
            }
        });

        //Verificar si se encontraron archivos
        if(files == null || files.length == 0){
            //logger.error("No se encontraron archivos .lbl en {}",directory.getAbsolutePath());
            throw new RuntimeException("No se encontraron archivos .lbl");
        } else{
            List<String> labels = new ArrayList<>();
            for(String file : files){
                labels.add(directory + File.separator + file);
            }
            logger.info("Etiquetas encontradas: {}",labels.size());
            return labels;
        }
    }
}
