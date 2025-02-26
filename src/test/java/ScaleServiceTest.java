import com.marsol.domain.ScaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class ScaleServiceTest {

    //Caso 1: ipScales tiene un valor válido y se espera obtener una lista de IPS
    @Test
    public void testGetListIps_Valid(){
        ScaleService scaleService = new ScaleService();
        scaleService.ipScales = "10.0.0.1, 10.2.0.3";

        List<String> ips = scaleService.getListIps();

        System.out.println(ips);
        assertNotNull(ips, "la lista no debe ser nula.");
        assertEquals(2, ips.size());
        assertEquals("10.0.0.1", ips.get(0).trim());
        assertEquals("10.2.0.3", ips.get(1).trim());
    }

    //Caso 2: ipScales es nula, lo que espera lance una exepción
    @Test
    public void testGetListIps_Invalid(){
        ScaleService scaleService = new ScaleService();
        scaleService.ipScales = "";

        RuntimeException exception = assertThrows(RuntimeException.class, scaleService::getListIps);
        assertEquals("Error, no se identificaron IPs validas.", exception.getMessage());
    }

    //Caso 3: ipScales tiene valores válidos e inválidos.
    @Test
    public void testGetListIps_ValidAndInvalidValues(){
        ScaleService scaleService = new ScaleService();
        scaleService.ipScales = "10.0.0.1, 10.2.0.3,999.999.999.999, sas";
        List<String> ips = scaleService.getListIps();
        System.out.println(ips);
        assertNotNull(ips, "la lista no debe ser nula.");
        assertEquals(2, ips.size());
        assertEquals("10.0.0.1", ips.get(0).trim());
        assertEquals("10.2.0.3", ips.get(1).trim());
    }

    //Caso 4: ipScales tiene solo valores Invalidos.
    @Test
    public void testGetListIps_InvalidValues(){
        ScaleService scaleService = new ScaleService();
        scaleService.ipScales = "999.999.99.9, sas";
        RuntimeException exception = assertThrows(RuntimeException.class, scaleService::getListIps);
        assertEquals("Error, no se identificaron IPs validas.", exception.getMessage());
    }
}
