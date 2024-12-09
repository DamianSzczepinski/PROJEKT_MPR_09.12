import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.edu.pjatk.MPR_projekt_s30136.model.Monkey;
import pl.edu.pjatk.MPR_projekt_s30136.repository.MonkeyRepository;
import pl.edu.pjatk.MPR_projekt_s30136.services.MonkeyService;
import pl.edu.pjatk.MPR_projekt_s30136.services.StringUtilsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MonkeyServiceTest {

    @Mock
    private MonkeyRepository monkeyRepository;

    @Mock
    private StringUtilsService stringUtilsService;

    @InjectMocks
    private MonkeyService monkeyService;

    @Test
    public void testAddMonkey() {
        Monkey monkey = new Monkey("TestMonkey", "Brown");

        ArgumentCaptor<Monkey> monkeyCaptor = ArgumentCaptor.forClass(Monkey.class);

        monkeyService.addMonkey(monkey);
        verify(monkeyRepository).save(monkeyCaptor.capture());

        Monkey capturedMonkey = monkeyCaptor.getValue();
        assertEquals("TESTMONKEY", capturedMonkey.getName());
        assertEquals("BROWN", capturedMonkey.getColor());
    }

    @Test
    public void testGeneratePdf() throws Exception {
        Monkey monkey = new Monkey("TestMonkey", "Brown");
        monkey.setId(1L);

        byte[] pdfContent = monkeyService.generatePdf(monkey);

        assertNotNull(pdfContent);
        assertTrue(pdfContent.length > 0);
    }
}
