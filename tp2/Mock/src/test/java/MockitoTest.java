

import org.example.I;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.AdditionalMatchers.leq;

@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    @Mock
    I mockI;

    @Test
    void testValeurParDefaut() throws Exception {
        // Un mock retourne 0 par défaut pour un int
        assertEquals(0, mockI.methodeInt());
    }

    @Test
    void testSuccessiveValuesAndVerify() throws Exception {
        // Mock retourne 1, 2, 3 puis 4
        when(mockI.methodeInt()).thenReturn(1, 2, 3, 4);

        assertEquals(1, mockI.methodeInt());
        assertEquals(2, mockI.methodeInt());
        assertEquals(3, mockI.methodeInt());
        assertEquals(4, mockI.methodeInt());

        // Vérification du nombre d'appels
        verify(mockI, times(4)).methodeInt();

        // Après les 4 valeurs, il retourne la dernière (4)
        assertEquals(4, mockI.methodeInt());
    }

    @Test
    void testExceptionMethodeInt() throws Exception {
        when(mockI.methodeInt()).thenThrow(new Exception("Erreur Mock"));
        assertThrows(Exception.class, () -> mockI.methodeInt());
    }

    @Test
    void testExceptionVoid() throws Exception {
        doThrow(new Exception()).when(mockI).methodeVoid();
        assertThrows(Exception.class, () -> mockI.methodeVoid());
    }

    @Test
    void testParametresSpecifiques() {
        when(mockI.methodeParam(3)).thenReturn(3);
        when(mockI.methodeParam(5)).thenReturn(10);

        assertEquals(0, mockI.methodeParam(1)); // Non mocké -> 0
        assertEquals(3, mockI.methodeParam(3));
        assertEquals(10, mockI.methodeParam(5));
    }

    @Test
    void testMatchersEntiers() {
        // Utilisation de matchers : > 10 retourne 42, sinon 0
        when(mockI.methodeParam(gt(10))).thenReturn(42);
        when(mockI.methodeParam(leq(10))).thenReturn(0);

        assertEquals(42, mockI.methodeParam(15));
        assertEquals(0, mockI.methodeParam(5));
    }

    @Test
    void testArgumentMatcherList() {
        // Retourne 42 si contient "42" ou taille == 1
        when(mockI.methodeParamArrayList(argThat(list ->
                list != null && (list.contains("42") || list.size() == 1)
        ))).thenReturn(42);

        ArrayList<String> l1 = new ArrayList<>();
        l1.add("test");
        assertEquals(42, mockI.methodeParamArrayList(l1)); // Taille 1

        ArrayList<String> l2 = new ArrayList<>();
        l2.add("a"); l2.add("42");
        assertEquals(42, mockI.methodeParamArrayList(l2)); // Contient "42"
    }

    @Test
    void testMatcherPersonnaliseListe() {
        // Utilisation d'un ArgumentMatcher avec une lambda (demandé dans le point 7 du TP)
        when(mockI.methodeParamArrayList(argThat(list ->
                list != null && (list.contains("42") || list.size() == 1)
        ))).thenReturn(42);

        // Tests des cas
        assertEquals(42, mockI.methodeParamArrayList(new ArrayList<>(List.of("42")))); // contient "42"
        assertEquals(42, mockI.methodeParamArrayList(new ArrayList<>(List.of("un seul")))); // taille 1
        assertEquals(0, mockI.methodeParamArrayList(new ArrayList<>(List.of("A", "B")))); // cas par défaut
    }
}
