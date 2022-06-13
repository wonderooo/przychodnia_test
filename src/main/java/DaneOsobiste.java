import java.time.LocalDate;
import java.util.regex.Pattern;

public class DaneOsobiste {
    private int id;
    private String name;
    private String lastname;
    private String PESEL;
    private LocalDate dateOfBirth;

    public DaneOsobiste(int id, String name, String lastname, String PESEL, LocalDate dateOfBirth) throws IncorrectPeselFormat {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        setPESEL(PESEL);
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) throws IncorrectPeselFormat {
        Pattern PeselPattern = Pattern.compile("\\d{11}");
        if (PeselPattern.matcher(PESEL).matches()) {
            this.PESEL = PESEL;
        }else {
            throw new IncorrectPeselFormat("PESEL: " + PESEL + ", powinnien miec format 11 cyfr");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString(){
        return "id: " + id + " imie: " + name + " nazwisko: " + lastname + " data urodzenia: " + dateOfBirth + " PESEL: " + PESEL;
    }
}

class IncorrectPeselFormat extends Exception {
    public IncorrectPeselFormat(String errorMessage) {
        super(errorMessage);
    }
}

