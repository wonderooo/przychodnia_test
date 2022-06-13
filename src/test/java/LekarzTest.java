import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LekarzTest {
    @Test
    void toStringOK() throws IncorrectNipFormat, IncorrectPeselFormat {
        var lekarz = new Lekarz(new DaneOsobiste(1, "Piotr", "Lis", "12345678910", LocalDate.parse("1999-01-12")), Specjalnosc.INTERNISTA, "888-888-88-88");
        assertEquals(lekarz.toString(), "\u001B[32mLEKARZ\u001B[0m: specjalnosc: INTERNISTA NIP: 888-888-88-88 dane: id: 1 imie: Piotr nazwisko: Lis data urodzenia: 1999-01-12 PESEL: 12345678910");
    }

    @Test
    void SpecjalnoscOK() throws IncorrectNipFormat, IncorrectPeselFormat {
        var lekarz = new Lekarz(new DaneOsobiste(1, "Piotr", "Lis", "12345678910", LocalDate.parse("1999-01-12")), Specjalnosc.INTERNISTA, "888-888-88-88");
        assertEquals(lekarz.getSpecjalnosc(), Specjalnosc.INTERNISTA);
    }

    @Test
    void NipOK(){
        assertDoesNotThrow(() -> new Lekarz(new DaneOsobiste(1, "Piotr", "Lis", "12345678910", LocalDate.parse("1999-01-12")), Specjalnosc.INTERNISTA, "888-888-88-88"));
        assertThrows(IncorrectNipFormat.class, () -> new Lekarz(new DaneOsobiste(1, "Piotr", "Lis", "12345678910", LocalDate.parse("1999-01-12")), Specjalnosc.INTERNISTA, "8888-888-88-88"));
        assertThrows(IncorrectNipFormat.class, () -> new Lekarz(new DaneOsobiste(1, "Piotr", "Lis", "12345678910", LocalDate.parse("1999-01-12")), Specjalnosc.INTERNISTA, "a88-888-88-88"));
        assertThrows(IncorrectNipFormat.class, () -> new Lekarz(new DaneOsobiste(1, "Piotr", "Lis", "12345678910", LocalDate.parse("1999-01-12")), Specjalnosc.INTERNISTA, "88-888-88-88"));
    }

    @Test
    void fileToListFileFoundOK() {
        assertDoesNotThrow(() -> Lekarz.readFileToList("src/files/lekarze.txt"));
        assertThrows(FileNotFoundException.class, () -> Lekarz.readFileToList("src/files/lekarze420.txt"));
    }
    @Test
    void fileToListLekarzOK() throws FileNotFoundException {
        var lekarz = Lekarz.readFileToList("src/files/lekarze.txt").get(0);
        assertEquals(lekarz.getDaneOsobiste().getDateOfBirth(), LocalDate.parse("1965-03-16"));
        assertEquals(lekarz.getDaneOsobiste().getId(), 23);
        assertEquals(lekarz.getDaneOsobiste().getName(), "Monika");
        assertEquals(lekarz.getDaneOsobiste().getLastname(), "Kadaj");
        assertEquals(lekarz.getDaneOsobiste().getPESEL(), "65031687654");
        assertEquals(lekarz.getSpecjalnosc(), Specjalnosc.LARYNGOLOG);
        assertEquals(lekarz.getNIP(), "879-122-69-94");
    }
}