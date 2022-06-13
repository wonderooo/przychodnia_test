import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WizytaTest {
    @Test
    void fileToListFileFoundOK() throws FileNotFoundException {
        var lekarze = Lekarz.readFileToList("src/files/lekarze.txt");
        var pacjenci = Pacjent.readFileToList("src/files/pacjenci.txt");
        assertDoesNotThrow(() -> Wizyta.readFileToList("src/files/wizyty.txt", lekarze, pacjenci));
        assertThrows(FileNotFoundException.class, () -> Wizyta.readFileToList("src/files/wizyty420.txt", lekarze, pacjenci));
    }

    @Test
    void fileToListPacjentOK() throws FileNotFoundException{
        var lekarze = Lekarz.readFileToList("src/files/lekarze.txt");
        var pacjenci = Pacjent.readFileToList("src/files/pacjenci.txt");
        var wizyta = Wizyta.readFileToList("src/files/wizyty.txt", lekarze, pacjenci).get(0);
        assertEquals(wizyta.getPacjent().getDaneOsobiste().getId(), 124);
        assertEquals(wizyta.getPacjent().getDaneOsobiste().getName(), "Hubert");
        assertEquals(wizyta.getPacjent().getDaneOsobiste().getLastname(), "Witkowski");
        assertEquals(wizyta.getPacjent().getDaneOsobiste().getPESEL(), "88030422354");
        assertEquals(wizyta.getPacjent().getDaneOsobiste().getDateOfBirth(), LocalDate.parse("1988-03-04"));

        assertEquals(wizyta.getLekarz().getDaneOsobiste().getId(), 23);
        assertEquals(wizyta.getLekarz().getDaneOsobiste().getName(), "Monika");
        assertEquals(wizyta.getLekarz().getDaneOsobiste().getLastname(), "Kadaj");
        assertEquals(wizyta.getLekarz().getDaneOsobiste().getPESEL(), "65031687654");
        assertEquals(wizyta.getLekarz().getDaneOsobiste().getDateOfBirth(), LocalDate.parse("1965-03-16"));
        assertEquals(wizyta.getLekarz().getSpecjalnosc(), Specjalnosc.LARYNGOLOG);
        assertEquals(wizyta.getLekarz().getNIP(), "879-122-69-94");

        assertEquals(wizyta.getDataWizyty(), LocalDate.parse("2006-12-13"));
    }

}