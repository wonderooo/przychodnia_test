import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PacjentTest {
    @Test
    void toStringOK() throws IncorrectPeselFormat {
        var pacjent = new Pacjent(new DaneOsobiste(2, "jakub", "lis", "12345678910", LocalDate.parse("1867-12-12")));
        assertEquals(pacjent.toString(), "\u001B[32mPACJENT\u001B[0m: dane: id: 2 imie: jakub nazwisko: lis data urodzenia: 1867-12-12 PESEL: 12345678910");
    }

    @Test
    void fileToListFileFoundOK() {
        assertDoesNotThrow(() -> Pacjent.readFileToList("src/files/pacjenci.txt"));
        assertThrows(FileNotFoundException.class, () -> Pacjent.readFileToList("src/files/pacjenci420.txt"));
    }
    @Test
    void fileToListPacjentOK() throws FileNotFoundException {
        var pacjent = Pacjent.readFileToList("src/files/pacjenci.txt").get(0);
        assertEquals(pacjent.getDaneOsobiste().getDateOfBirth(), LocalDate.parse("2001-01-13"));
        assertEquals(pacjent.getDaneOsobiste().getId(), 100);
        assertEquals(pacjent.getDaneOsobiste().getName(), "Waldemar");
        assertEquals(pacjent.getDaneOsobiste().getLastname(), "Kowal");
        assertEquals(pacjent.getDaneOsobiste().getPesel(), "01211309876");
    }
}