import com.marsol.Main;
import com.marsol.application.LabelController;
import com.marsol.domain.LabelService;
import com.marsol.domain.ScaleService;
import com.marsol.infrastructure.integration.SyncDataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class LabelControllerTest {

    @Mock
    private LabelService labelService;

    @Mock
    private ScaleService scaleService;

    @Mock
    private SyncDataLoader syncDataLoader;

    @InjectMocks
    private LabelController labelController;


    //
    @Test
    public void testLoadLabels_getLabelsFiles(){
        //Configuramos el mock para que devuleva una lista de archivos.
        List<String> fakeLabels = Arrays.asList("ruta/label1.lbl","ruta/label2.lbl","ruta/label3.lbl");
        when(labelService.getLabelsFiles()).thenReturn(fakeLabels);

        //Llamamos al metodo a testear
        labelController.loadLabels();

        //Verificamos
        verify(labelService, times(1)).getLabelsFiles();
    }

    @Test void testLoadLables_getScalesIPs(){
        //Configuramos el mock para que devuelva una lista de archivos.
        List<String> fakeIps = Arrays.asList("192.1.1.1","192.2.2.2");
        when(scaleService.getListIps()).thenReturn(fakeIps);
        List<String> fakeLabels = Arrays.asList();
        when(labelService.getLabelsFiles()).thenReturn(fakeLabels);

        //Llamamos al metodo a testear
        labelController.loadLabels();
        verify(scaleService, times(1)).getListIps();
    }

}
