import java.time.LocalDate;

public class DaneOsobiste {
    private int id;
    private String name;
    private String lastname;
    private String pesel;
    private LocalDate dateOfBirth;

    public DaneOsobiste(int id, String name, String lastname, String pesel, LocalDate dateOfBirth) throws IncorrectPeselFormat {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        setPesel(pesel);
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) throws IncorrectPeselFormat {
        if (pesel.matches("\\d{11}")) {
            this.pesel = pesel;
        }else {
            throw new IncorrectPeselFormat("PESEL: " + pesel + ", powinnien miec format 11 cyfr");
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
        return "id: " + id + " imie: " + name + " nazwisko: " + lastname + " data urodzenia: " + dateOfBirth + " PESEL: " + pesel;
    }
}

class IncorrectPeselFormat extends Exception {
    public IncorrectPeselFormat(String errorMessage) {
        super(errorMessage);
    }
}

