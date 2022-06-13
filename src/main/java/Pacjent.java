import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Pacjent {
    private DaneOsobiste daneOsobiste;

    public Pacjent(DaneOsobiste daneOsobiste) {
        this.daneOsobiste = daneOsobiste;
    }

    public DaneOsobiste getDaneOsobiste() {
        return daneOsobiste;
    }

    public void setDaneOsobiste(DaneOsobiste daneOsobiste) {
        this.daneOsobiste = daneOsobiste;
    }

    public static ArrayList<Pacjent> readFileToList(String path) throws FileNotFoundException{
        var reader = new BufferedReader(new FileReader(path));
        ArrayList<Pacjent> pacjenci = new ArrayList<>();
        Stream<String> lines = reader.lines().skip(1);
        lines.forEach(line -> {
            String[] params = line.split("\t");
            String[] date = params[4].split("-");
            if (date[1].length() == 1){
                date[1] = "0" + date[1];
            }
            if (date[2].length() == 1){
                date[2] = "0" + date[2];
            }
            String dateRes = date[0] + "-" + date[1] + "-" + date[2];
            try {
                Pacjent pacjent = new Pacjent(new DaneOsobiste(Integer.parseInt(params[0]), params[2], params[1], params[3], LocalDate.parse(dateRes)));
                for (Pacjent p : pacjenci) {
                    if (p.getDaneOsobiste().getId() == pacjent.getDaneOsobiste().getId() || p.getDaneOsobiste().getPESEL().equals(pacjent.getDaneOsobiste().getPESEL())){
                        throw new PacjentWithUniqueParametersExists("Pacjent z id i Pesel " + pacjent.getDaneOsobiste().getId() + " " + pacjent.getDaneOsobiste().getPESEL() + " juz istnieje");
                    }
                }
                pacjenci.add(pacjent);
            } catch (IncorrectPeselFormat | PacjentWithUniqueParametersExists e) {
                e.printStackTrace();
            }
        });
        return pacjenci;
    }

    public static Pacjent getPacjentById(ArrayList<Pacjent> pacjenci, int id) throws PacjentWithIdNotFound {
        for (Pacjent pacjent : pacjenci){
            if(pacjent.getDaneOsobiste().getId() == id){
                return pacjent;
            }
        }
        throw new PacjentWithIdNotFound("Nie ma id: " + id + " w liscie pacjentow: " + pacjenci);
    }

    @Override
    public String toString(){
        return Color.GREEN + "PACJENT" + Color.RESET + ": dane: " + daneOsobiste;
    }
}

class PacjentWithIdNotFound extends Exception {
    public PacjentWithIdNotFound(String errorMessage) {
        super(errorMessage);
    }
}
class PacjentWithUniqueParametersExists extends Exception {
    public PacjentWithUniqueParametersExists(String errorMessage) {
        super(errorMessage);
    }
}
