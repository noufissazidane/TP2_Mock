import org.example.SommeArgents;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SommeArgentsMockTest {

    // 1. Point 3 du cours : Utilisation de l'annotation @Mock au lieu de mock()
    @Mock
    SommeArgents mockS2;

    @Test
    void testAddAvecMockEtMatcher() throws Exception {
        SommeArgents s1 = new SommeArgents(10, "EUR");

        // 2. Point 3 du cours : Utilisation d'un Matcher (anyString)
        // Ici, peu importe l'unité demandée, le mock répondra "EUR"
        when(mockS2.getUnite()).thenReturn("EUR");
        when(mockS2.getQuantite()).thenReturn(5);

        SommeArgents resultat = s1.add(mockS2);

        assertEquals(15, resultat.getQuantite());
        // 3. Point 4 du cours : Vérification
        verify(mockS2).getUnite();
    }

    @Test
    void testAvecSpy() {
        // 4. Point 3 du cours : Le Spy (Objet réel espionné)
        SommeArgents s1 = new SommeArgents(10, "EUR");
        SommeArgents spyS1 = spy(s1);

        // On force getQuantite à renvoyer 100, mais getUnite restera réel ("EUR")
        doReturn(100).when(spyS1).getQuantite();

        assertEquals(100, spyS1.getQuantite()); // Valeur stubbée
        assertEquals("EUR", spyS1.getUnite());   // Valeur réelle
    }

    @Test
    void testAddDeviseDifferente_ProvoqueException() throws Exception {
        SommeArgents s1 = new SommeArgents(10, "EUR");

        when(mockS2.getUnite()).thenReturn("USD");

        assertThrows(Exception.class, () -> s1.add(mockS2));
    }

    @Test
    void testAdd_SituationArtificielle() throws Exception {
        SommeArgents s1 = new SommeArgents(10, "EUR");
        SommeArgents mockS2 = mock(SommeArgents.class);

        // Situation artificielle : on force le mock à retourner une quantité négative
        // ou un comportement bizarre que la vraie classe ne permettrait pas facilement
        when(mockS2.getUnite()).thenReturn("EUR");
        when(mockS2.getQuantite()).thenReturn(-50);

        SommeArgents resultat = s1.add(mockS2);

        // On vérifie que notre logique de calcul (10 + (-50)) fonctionne
        assertEquals(-40, resultat.getQuantite());
    }
}