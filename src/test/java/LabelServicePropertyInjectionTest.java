import com.marsol.domain.LabelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = LabelService.class)
@TestPropertySource(properties = "directory=testDirectoryValue")
public class LabelServicePropertyInjectionTest {

    @Autowired
    private LabelService labelService;

    @Test
    public void testPropertyInjection() throws Exception {
        //Accedemos al campo privado "Dir" mediante reflection
        Field dirField = LabelService.class.getDeclaredField("Dir");
        dirField.setAccessible(true);
        String dirValue = (String) dirField.get(labelService);

        assertEquals("testDirectoryValue", dirValue);
        System.out.println(dirValue);

    }

}
