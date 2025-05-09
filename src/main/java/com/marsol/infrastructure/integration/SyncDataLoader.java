package com.marsol.infrastructure.integration;

import com.marsol.infrastructure.adapter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SyncDataLoader {
    private SyncSDKIntf sync;
    private static final Logger logger = LoggerFactory.getLogger(SyncDataLoader.class);
    public SyncDataLoader() {
        this.sync = SyncManager.getInstance();
    }
    public void Finalize(){
        SyncManager.finalizeInstance();
    }

    public boolean loadFormatLabel(String filename, String ipString, int user){
        long result;
        int ip = SyncSDKDefine.ipToLong(ipString);
        TSDKOnProgressEvent onProgress = (var1, var2, var3, var4) -> {
            //var1 : ErrorCode
            //var2 : nIndex
            //var3 : nTotal
            //var4 : nUserDataCode
            String errorMessage = ErrorTranslator.getErrorMessage(var1);
            if(var1 != 0 && var1 != 1 && var1 != 2){
                logger.error("[ERROR EN CARGA DE BALANZA] ErrorCode {}: {} en indice: {} de {} elementos.",var1,errorMessage,var2,var3);
            }
            if(var1 == 0){
                logger.info("[CARGA DE BALANZA REALIZADA] Se ha cargado el formato de etiqueta ({}) a balanza: {}.",var3,ipString);
            }
            if(var1 == -1){
                logger.error("[ERROR EN CARGA DE BALANZA] Se ha producido un error inesperado durante la carga de la balanaza IP: {}",ipString);
            }
        };
        try{
            //logger.info("Cargando Etiqueta {} en balanza {}",filename,ipString);
            result = sync.SDK_ExecTaskA(ip,0,8192,filename,onProgress,user);

            sync.SDK_WaitForTask(result);
            //logger.info("Etiqueta cargada.");
            return true;
        }catch(Exception e){
            logger.error("Error durante la carga de la etiqueta.");
            return false;
        }
    }
    public boolean loadBackgroundLabel(String filename, String ipString, int user){
        long result;
        int ip = SyncSDKDefine.ipToLong(ipString);
        TSDKOnProgressEvent onProgress = (var1, var2, var3, var4) -> {
            //var1 : ErrorCode
            //var2 : nIndex
            //var3 : nTotal
            //var4 : nUserDataCode
            String errorMessage = ErrorTranslator.getErrorMessage(var1);
            if(var1 != 0 && var1 != 1 && var1 != 2){
                logger.error("[ERROR EN CARGA DE BALANZA] ErrorCode {}: {} en indice: {} de {} elementos.",var1,errorMessage,var2,var3);
            }
            if(var1 == 0){
                logger.info("[CARGA DE BALANZA REALIZADA] Se ha cargado el fondo de etiqueta ({}) a balanza: {}.",var3, ipString);
            }
            if(var1 == -1){
                logger.error("[ERROR EN CARGA DE BALANZA] Se ha producido un error inesperado durante la carga de la balanaza IP: {}",ipString);
            }
        };
        try{
            //logger.info("Cargando Etiqueta {} en balanza {}",filename,ipString);
            result = sync.SDK_ExecTaskA(ip,0,8193,filename,onProgress,user);
            sync.SDK_WaitForTask(result);
            //logger.info("Etiqueta cargada.");
            return true;
        }catch(Exception e){
            logger.error("Error durante la carga de la etiqueta.");
            return false;
        }
    }
    public boolean loadFileLabel(String filename, String ipString, int user){
        long result;
        int ip = SyncSDKDefine.ipToLong(ipString);
        TSDKOnProgressEvent onProgress = (var1, var2, var3, var4) -> {
            //var1 : ErrorCode
            //var2 : nIndex
            //var3 : nTotal
            //var4 : nUserDataCode
            String errorMessage = ErrorTranslator.getErrorMessage(var1);
            if(var1 != 0 && var1 != 1 && var1 != 2){
                logger.error("[ERROR EN CARGA DE BALANZA] ErrorCode {}: {} en indice: {} de {} elementos.",var1,errorMessage,var2,var3);
            }
            if(var1 == 0){
                logger.info("[CARGA DE BALANZA REALIZADA] Se ha cargado el archivo de etiqueta ({}) a balanza: {}.",var3, ipString);
            }
            if(var1 == -1){
                logger.error("[ERROR EN CARGA DE BALANZA] Se ha producido un error inesperado durante la carga de la balanaza IP: {}",ipString);
            }
        };
        try{
            //logger.info("Cargando Etiqueta {} en balanza {}",filename,ipString);
            result = sync.SDK_ExecTaskA(ip,0,8194,filename,onProgress,user);
            sync.SDK_WaitForTask(result);
            //logger.info("Etiqueta cargada.");
            return true;
        }catch(Exception e){
            logger.error("Error durante la carga de la etiqueta.");
            return false;
        }
    }
}