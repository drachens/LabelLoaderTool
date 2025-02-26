import com.marsol.domain.LabelService;
import com.marsol.domain.ScaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ScaleService.class)
@TestPropertySource(properties = "ipScales=192.5.5.5, 192.3.3.3")
public class ScaleServicePropertyInjectionTest {
    @Autowired
    private ScaleService scaleService;

    @Test
    public void testPropertyInjection() throws Exception{
        //Accedemos al campo privado "Dir" mediante reflection
        Field ipField = ScaleService.class.getDeclaredField("ipScales");
        ipField.setAccessible(true);
        String ipValue = (String) ipField.get(scaleService);

        assertEquals("192.5.5.5, 192.3.3.3", ipValue);
        System.out.println(ipValue);
    }
}
