

import org.example.A;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SpyTest {

    @Spy
    A spyA; // Crée un espion sur une instance réelle de A

    @Test
    void testComportementSpy() {
        // Vérification du comportement réel (m1 retourne 42, m2 retourne i*i)
        assertEquals(42, spyA.m1());
        assertEquals(100, spyA.m2(10));

        // Vérification des appels
        verify(spyA).m1();
        verify(spyA).m2(10);
    }

    @Test
    void testMockPartielSurSpy() {
        // On modifie le comportement de m2 seulement si i = 42
        doReturn(0).when(spyA).m2(42);

        assertEquals(0, spyA.m2(42));     // Comportement mocké
        assertEquals(4, spyA.m2(2));      // Comportement réel conservé (2*2)
        assertEquals(100, spyA.m2(10));   // Comportement réel conservé (10*10)
    }
}