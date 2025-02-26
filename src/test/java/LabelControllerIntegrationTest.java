import com.marsol.application.LabelController;
import com.marsol.domain.LabelService;
import com.marsol.domain.ScaleService;
import com.marsol.infrastructure.integration.SyncDataLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest(classes = {LabelControllerIntegrationTest.TestConfig.class,LabelController.class})
@TestPropertySource("classpath:application.properties")
public class LabelControllerIntegrationTest {

    @Autowired
    private LabelController labelController;

    //Usamos SpyBean para monitorear las invocaciones de SyncDataLoader
    @SpyBean
    private SyncDataLoader syncDataLoader;

    @Test
    public void testLoadLabelsIntegration(){
        labelController.loadLabels();
        // Con la configuración de test definida, LabelService devuelve 2 etiquetas y ScaleService 2 IPs.
        // Por lo tanto, se espera que se invoque loadLabel() 4 veces, una para cada combinación.
        verify(syncDataLoader, times(1)).loadLabel("ruta/label1.lbl", "192.168.1.1");
        verify(syncDataLoader, times(1)).loadLabel("ruta/label2.lbl", "192.168.1.1");
        verify(syncDataLoader, times(1)).loadLabel("ruta/label1.lbl", "192.1.1.1");
        verify(syncDataLoader, times(1)).loadLabel("ruta/label2.lbl", "192.1.1.1");
    }

    @TestConfiguration
    static class TestConfig {
        //Definimos un LabelService de prueba que retorna una lista fija de archivos.
        @Bean
        @Primary
        public LabelService labelService() {
            return new LabelService(){
                @Override
                public List<String> getLabelsFiles(){
                    return Arrays.asList("ruta/label1.lbl","ruta/label2.lbl");
                }
            };
        }

        //Definimos un ScaleService de prueba que retorna una lista fija de IPs.
        @Bean
        @Primary
        public ScaleService scaleService(){
            return new ScaleService();
        }

        //Implementamos syncDataLoader con una version dummy
        @Bean
        @Primary
        public SyncDataLoader syncDataLoader(){
            return new SyncDataLoader(){
                @Override
                public boolean loadLabel(String label, String ip){
                    return true;
                }
            };
        }
    }
}
