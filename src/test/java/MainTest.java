import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void najstarsiLekarzeOK() throws IncorrectPeselFormat, IncorrectNipFormat {
        var lekarze = new ArrayList<>(List.of(new Lekarz[]{
                new Lekarz(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                new Lekarz(new DaneOsobiste(2, "test1", "testn1", "12345678911", LocalDate.parse("2006-05-13")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                new Lekarz(new DaneOsobiste(3, "test1", "testn1", "12345678912", LocalDate.parse("2003-12-01")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                new Lekarz(new DaneOsobiste(4, "test1", "testn1", "12345678913", LocalDate.parse("1999-09-26")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                new Lekarz(new DaneOsobiste(5, "test1", "testn1", "12345678914", LocalDate.parse("1999-09-27")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                }
        ));
        Collections.sort(lekarze);
        assertEquals(lekarze.get(0).getDaneOsobiste().getDateOfBirth(), LocalDate.parse("1999-09-26"));
        assertEquals(lekarze.get(1).getDaneOsobiste().getDateOfBirth(), LocalDate.parse("1999-09-27"));
        assertEquals(lekarze.get(2).getDaneOsobiste().getDateOfBirth(), LocalDate.parse("2003-12-01"));
        assertEquals(lekarze.get(3).getDaneOsobiste().getDateOfBirth(), LocalDate.parse("2005-01-03"));
        assertEquals(lekarze.get(4).getDaneOsobiste().getDateOfBirth(), LocalDate.parse("2006-05-13"));
    }

    @Test
    void pacjent5RoznychLekarzyOK() throws Exception {
        var lekarze = new ArrayList<>(List.of(new Lekarz[]{
                        new Lekarz(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                        new Lekarz(new DaneOsobiste(2, "test1", "testn1", "12345678911", LocalDate.parse("2006-05-13")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                        new Lekarz(new DaneOsobiste(3, "test1", "testn1", "12345678912", LocalDate.parse("2003-12-01")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                        new Lekarz(new DaneOsobiste(4, "test1", "testn1", "12345678913", LocalDate.parse("1999-09-26")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                        new Lekarz(new DaneOsobiste(5, "test1", "testn1", "12345678914", LocalDate.parse("1999-09-27")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                }
        ));
        var pacjenci = new ArrayList<>(List.of(new Pacjent[]{
                        new Pacjent(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03")))
                }
        ));
        var wizyty1 = new ArrayList<>(List.of(new Wizyta[]{
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(1), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(2), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(3), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(4), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                }
        ));
        var wizyty2 = new ArrayList<>(List.of(new Wizyta[]{
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(2), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(3), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(4), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                }
        ));
        assertTrue(Main.pacjenci5RoznychLekarzy(wizyty1));
        assertFalse(Main.pacjenci5RoznychLekarzy(wizyty2));
    }

    @Test
    void lekarzTylko1PacjentOK() throws Exception {
        var lekarze = new ArrayList<>(List.of(new Lekarz[]{
                        new Lekarz(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                }
        ));
        var pacjenci = new ArrayList<>(List.of(new Pacjent[]{
                        new Pacjent(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03"))),
                        new Pacjent(new DaneOsobiste(2, "test1", "testn1", "12345678911", LocalDate.parse("2005-01-03"))),
                        new Pacjent(new DaneOsobiste(3, "test1", "testn1", "12345678912", LocalDate.parse("2005-01-03"))),
                }
        ));
        var wizyty1 = new ArrayList<>(List.of(new Wizyta[]{
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(1), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(2), LocalDate.parse("1999-01-12")),
                }
        ));
        var wizyty2 = new ArrayList<>(List.of(new Wizyta[]{
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                }
        ));
        assertFalse(Main.lekarzeTylko1Pacjent(wizyty1));
        assertTrue(Main.lekarzeTylko1Pacjent(wizyty2));
    }

    @Test
    void najwiecejRocznieOK() throws Exception {
        var lekarze = new ArrayList<>(List.of(new Lekarz[]{
                        new Lekarz(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                }
        ));
        var pacjenci = new ArrayList<>(List.of(new Pacjent[]{
                        new Pacjent(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03"))),
                }
        ));
        var wizyty1 = new ArrayList<>(List.of(new Wizyta[]{
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1998-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1998-01-12")),
                }
        ));
        var wizyty2 = new ArrayList<Wizyta>();
        assertEquals(Main.rokNajwiecejWizyt(wizyty1).get(0).getKey(), 1999);
        assertEquals(Main.rokNajwiecejWizyt(wizyty1).get(0).getValue().size(), 4);
    }

    @Test
    void specjalnoscNajwiecejOK() throws Exception {
        var lekarze = new ArrayList<>(List.of(new Lekarz[]{
                        new Lekarz(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                        new Lekarz(new DaneOsobiste(2, "test1", "testn1", "12345678911", LocalDate.parse("2005-01-03")), Specjalnosc.UROLOG, "123-123-12-12"),
                }
        ));
        var pacjenci = new ArrayList<>(List.of(new Pacjent[]{
                        new Pacjent(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03"))),
                }
        ));
        var wizyty1 = new ArrayList<>(List.of(new Wizyta[]{
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(1), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(1), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1998-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1998-01-12")),
                }
        ));
        var wizyty2 = new ArrayList<Wizyta>();
        assertEquals(Main.specjalnoscNajwiecej(wizyty1).get(0).getKey(), Specjalnosc.LARYNGOLOG);
        assertEquals(Main.specjalnoscNajwiecej(wizyty1).get(0).getValue().size(), 4);
    }

    @Test
    void pacjentNajwiecejWizytOK() throws Exception {
        var lekarze = new ArrayList<>(List.of(new Lekarz[]{
                        new Lekarz(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                }
        ));
        var pacjenci = new ArrayList<>(List.of(new Pacjent[]{
                        new Pacjent(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03"))),
                        new Pacjent(new DaneOsobiste(2, "test1", "testn1", "12345678911", LocalDate.parse("2005-01-03"))),
                        new Pacjent(new DaneOsobiste(3, "test1", "testn1", "12345678912", LocalDate.parse("2005-01-03"))),
                        new Pacjent(new DaneOsobiste(4, "test1", "testn1", "12345678913", LocalDate.parse("2005-01-03"))),
                        new Pacjent(new DaneOsobiste(5, "test1", "testn1", "12345678914", LocalDate.parse("2005-01-03"))),
                }
        ));
        var wizyty1 = new ArrayList<>(List.of(new Wizyta[]{
                        new Wizyta(lekarze.get(0), pacjenci.get(1), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(2), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1998-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(3), LocalDate.parse("1998-01-12")),
                }
        ));
        var wizyty2 = new ArrayList<Wizyta>();
        assertEquals(Main.genericNajwiecejWizyt(pacjenci).get(0).getDaneOsobiste().getId(), 1);
    }

    @Test
    void lekarzNajwiecejWizytOK() throws Exception {
        var lekarze = new ArrayList<>(List.of(new Lekarz[]{
                        new Lekarz(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03")), Specjalnosc.LARYNGOLOG, "123-123-12-12"),
                        new Lekarz(new DaneOsobiste(2, "test1", "testn1", "12345678911", LocalDate.parse("2005-01-03")), Specjalnosc.UROLOG, "123-123-12-12"),
                }
        ));
        var pacjenci = new ArrayList<>(List.of(new Pacjent[]{
                        new Pacjent(new DaneOsobiste(1, "test1", "testn1", "12345678910", LocalDate.parse("2005-01-03"))),
                        new Pacjent(new DaneOsobiste(2, "test1", "testn1", "12345678911", LocalDate.parse("2005-01-03"))),
                }
        ));
        var wizyty1 = new ArrayList<>(List.of(new Wizyta[]{
                        new Wizyta(lekarze.get(0), pacjenci.get(1), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(1), pacjenci.get(0), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(1), pacjenci.get(1), LocalDate.parse("1999-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(0), LocalDate.parse("1998-01-12")),
                        new Wizyta(lekarze.get(0), pacjenci.get(1), LocalDate.parse("1998-01-12")),
                }
        ));
        var wizyty2 = new ArrayList<Wizyta>();
        assertEquals(Main.genericNajwiecejWizyt(lekarze).get(0).getDaneOsobiste().getId(), 1);
    }
}