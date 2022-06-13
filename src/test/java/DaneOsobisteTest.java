import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;


import static org.junit.jupiter.api.Assertions.*;

class DaneOsobisteTest {
    @Test
    void toStringOK() throws IncorrectPeselFormat {
        var daneOsobiste = new DaneOsobiste(1, "Kacper", "Szczesny", "12345678910", LocalDate.parse("2003-12-01"));
        assertEquals(daneOsobiste.toString(), "id: 1 imie: Kacper nazwisko: Szczesny data urodzenia: 2003-12-01 PESEL: 12345678910");
    }

    @Test
    void dateParamsOK() throws IncorrectPeselFormat {
        var daneOsobiste = new DaneOsobiste(1, "Kacper", "Szczesny", "12345678910", LocalDate.parse("2003-12-01"));
        assertEquals(daneOsobiste.getDateOfBirth().getMonth(), Month.DECEMBER);
        assertEquals(daneOsobiste.getDateOfBirth().getYear(), 2003);
        assertEquals(daneOsobiste.getDateOfBirth().getDayOfMonth(), 1);
    }

    @Test
    void dateParseOK() {
        assertThrows(DateTimeParseException.class, () -> new DaneOsobiste(1, "Kacper", "Szczesny", "12345678910", LocalDate.parse("20031-12-01")));
        assertThrows(DateTimeParseException.class, () -> new DaneOsobiste(1, "Kacper", "Szczesny", "12345678910", LocalDate.parse("aa-12-01")));
        assertThrows(DateTimeParseException.class, () -> new DaneOsobiste(1, "Kacper", "Szczesny", "12345678910", LocalDate.parse("2003-31-01")));
        assertThrows(DateTimeParseException.class, () -> new DaneOsobiste(1, "Kacper", "Szczesny", "12345678910", LocalDate.parse("20031-12-41")));
        assertDoesNotThrow(() -> new DaneOsobiste(1, "Kacper", "Szczesny", "12345678910", LocalDate.parse("2003-12-01")));
    }

    @Test
    void PeselEq11() {
        assertDoesNotThrow(() -> new DaneOsobiste(1, "Kacper", "Szczesny", "12345678910", LocalDate.parse("2003-12-01")));
        assertThrows(IncorrectPeselFormat.class, () -> new DaneOsobiste(1, "Kacper", "Szczesny", "123456789106", LocalDate.parse("2003-12-01")));
        assertThrows(IncorrectPeselFormat.class, () -> new DaneOsobiste(1, "Kacper", "Szczesny", "123456789", LocalDate.parse("2003-12-01")));
        assertThrows(IncorrectPeselFormat.class, () -> new DaneOsobiste(1, "Kacper", "Szczesny", "a2345678910", LocalDate.parse("2003-12-01")));
    }


}