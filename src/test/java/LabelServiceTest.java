import com.marsol.domain.LabelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application.properties")
public class LabelServiceTest {

    private LabelService labelService;

    @TempDir
    File tempDir;

    @BeforeEach
    void setUp() throws Exception {
        labelService = new LabelService();
        //Usamos reflection para asignar el directorio temporal a la propiedad Dir
        Field dirField = LabelService.class.getDeclaredField("Dir");
        dirField.setAccessible(true);
        dirField.set(labelService, tempDir.getAbsolutePath());
    }

    //Caso 1: El directorio no existe
    @Test
    public void testGetLabelsFiles_DirectoryNotExist() throws Exception {
        //Asignamos una ruta que no existe
        Field dirField = LabelService.class.getDeclaredField("Dir");
        dirField.setAccessible(true);
        dirField.set(labelService, tempDir.getAbsolutePath()+"\\noExsite");

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> labelService.getLabelsFiles());
        assertTrue(thrown.getMessage().endsWith("no es un directorio."));
    }

    //Caso 2: La ruta asignada es un archivo y no un directorio.
    @Test
    public void testGetLabelsFiles_DirIsNotADirectory() throws Exception {
        //Creamos un archivo en el directorio temporal
        File file = new File(tempDir, "archivo_test.txt");
        Files.write(file.toPath(),"contenido_testing".getBytes());

        Field dirField = LabelService.class.getDeclaredField("Dir");
        dirField.setAccessible(true);
        dirField.set(labelService, file.getAbsolutePath());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> labelService.getLabelsFiles());
        assertTrue(thrown.getMessage().endsWith("no es un directorio."));
    }

    //Caso 3: Directorio vacio, sin archivos lbl
    @Test
    public void testGetLabelsFiles_EmptyDir() throws Exception {
        //tempDir está vacío
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> labelService.getLabelsFiles());
        assertEquals("No se encontraron archivos .lbl", thrown.getMessage());
    }

    //Caso 4: Directorio con archivos .lbl y otros archivos
    @Test
    public void testGetLabelsFiles_FoundLblFilesAndOthers() throws Exception {
        //Creamos un archivo con extension .lbl y con extension diferente
        File lblFile = new File(tempDir, "labelTest.lbl");
        Files.write(lblFile.toPath(),"contenido_testing".getBytes());

        File txtFile = new File(tempDir, "archivo_test.txt");
        Files.write(txtFile.toPath(),"contenido_testing".getBytes());

        List<String> labels = labelService.getLabelsFiles();

        System.out.println(labels);
        assertEquals(1, labels.size());
        assertTrue(labels.get(0).endsWith("labelTest.lbl"));
    }

}
