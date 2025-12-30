
import org.example.C;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class StaticFinalTest {

    @Test
    void testMockFinalMethod() {
        // Mockito gère le "final" automatiquement avec l'extension inline
        C mockC = mock(C.class);
        when(mockC.m2(10)).thenReturn(0);

        assertEquals(0, mockC.m2(10));
    }

    @Test
    void testMockStaticMethod() {
        // Pour les méthodes statiques, on utilise un bloc try-with-resources
        try (MockedStatic<C> mockedStatic = mockStatic(C.class)) {
            mockedStatic.when(C::m1).thenReturn(100);

            assertEquals(100, C.m1());
            mockedStatic.verify(C::m1);
        }

        // En dehors du bloc, la méthode redevient réelle
        assertEquals(42, C.m1());
    }
}