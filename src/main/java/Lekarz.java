import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

enum Specjalnosc {
    LARYNGOLOG("laryngolog"),
    NEFROLOG("nefrolog"),
    UROLOG("urolog"),
    OKULISTA("okulista"),
    PEDIATRA("pediatra"),
    ORTOPEDA("ortopeda"),
    ALERGOLOG("alergolog"),
    DERMATOLOG("dermatolog"),
    CHIRURG("chirurg"),
    NEUROLOG("neurolog"),
    KARDIOLOG("kardiolog"),
    REUMATOLOG("reumatolog"),
    INTERNISTA("internista"),
    ONKOLOG("onkolog"),
    ENDOKRYNOLOG("endokrynolog");
    private final String text;
    Specjalnosc(String text){
        this.text = text;
    }

    public static Optional<Specjalnosc> get(String str) {
        return Arrays.stream(Specjalnosc.values())
                .filter(x -> x.text.equals(str))
                .findFirst();
    }
}

public class Lekarz implements Comparable<Lekarz> {
    private DaneOsobiste daneOsobiste;
    private Specjalnosc specjalnosc;
    private String NIP;

    public Lekarz(DaneOsobiste daneOsobiste, Specjalnosc specjalnosc, String NIP) throws IncorrectNipFormat {
        this.daneOsobiste = daneOsobiste;
        this.specjalnosc = specjalnosc;
        setNIP(NIP);
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) throws IncorrectNipFormat {
        Pattern NipPattern = Pattern.compile("\\d{3}-\\d{3}-\\d{2}-\\d{2}");
        if (NipPattern.matcher(NIP).matches()) {
            this.NIP = NIP;
        } else {
            throw new IncorrectNipFormat("NIP: " + NIP + ", powinnien miec format 10 cyrf xxx-xxx-xx-xx");
        }
    }

    public DaneOsobiste getDaneOsobiste() {
        return daneOsobiste;
    }

    public void setDaneOsobiste(DaneOsobiste daneOsobiste) {
        this.daneOsobiste = daneOsobiste;
    }

    public Specjalnosc getSpecjalnosc() {
        return specjalnosc;
    }

    public void setSpecjalnosc(Specjalnosc specjalnosc) {
        this.specjalnosc = specjalnosc;
    }

    public static ArrayList<Lekarz> readFileToList(String path) throws FileNotFoundException {
        var reader = new BufferedReader(new FileReader(path));
        ArrayList<Lekarz> lekarze = new ArrayList<>();
        Stream<String> lines = reader.lines().skip(1);
        lines.forEach(line -> {
            String[] params = line.split("\t");
            String[] date = params[4].trim().split("-");
            if (date[1].length() == 1){
                date[1] = "0" + date[1];
            }
            if (date[2].length() == 1){
                date[2] = "0" + date[2];
            }
            String dateRes = date[0] + "-" + date[1] + "-" + date[2];
            try {
                Lekarz lekarz = new Lekarz(new DaneOsobiste(Integer.parseInt(params[0]), params[2], params[1], params[6], LocalDate.parse(dateRes)), Specjalnosc.get(params[3]).orElseThrow(() -> new SpecjalnoscNotFound("Nie znaleziono specjalnosci " + params[3])), params[5]);
                for (Lekarz l : lekarze) {
                    if (l.getDaneOsobiste().getId() == lekarz.getDaneOsobiste().getId() || l.getDaneOsobiste().getPESEL().equals(lekarz.getDaneOsobiste().getPESEL())){
                        throw new LekarzWithUniqueParameterExists("Lekarz z id i PESEL: " + lekarz.getDaneOsobiste().getId() + ", " + lekarz.getDaneOsobiste().getPESEL() + " juz istnieje");
                    }
                }
                lekarze.add(lekarz);
            } catch (IncorrectPeselFormat | IncorrectNipFormat | SpecjalnoscNotFound | LekarzWithUniqueParameterExists e) {
                e.printStackTrace();
            }
        });
        return lekarze;
    }

    public static Lekarz getLekarzById(ArrayList<Lekarz> lekarze, int id) throws LekarzWithIdNotFound {
        for (Lekarz lekarz : lekarze){
            if(lekarz.getDaneOsobiste().getId() == id){
                return lekarz;
            }
        }
        throw new LekarzWithIdNotFound("Nie ma id: " + id + " w liscie lekarzy: " + lekarze);
    }

    @Override
    public String toString(){
        return Color.GREEN + "LEKARZ" + Color.RESET + ": specjalnosc: " + specjalnosc + " NIP: " + NIP + " dane: " + daneOsobiste;
    }

    @Override
    public int compareTo(Lekarz innyLekarz) {
        if (this.daneOsobiste.getDateOfBirth().isBefore(innyLekarz.daneOsobiste.getDateOfBirth())){
            return -1;
        }else {
            return 1;
        }
    }
}

class IncorrectNipFormat extends Exception {
    public IncorrectNipFormat(String errorMessage) {
        super(errorMessage);
    }
}

class SpecjalnoscNotFound extends Exception {
    public SpecjalnoscNotFound(String errorMessage) {
        super(errorMessage);
    }
}

class LekarzWithIdNotFound extends Exception {
    public LekarzWithIdNotFound(String errorMessage) {
        super(errorMessage);
    }
}

class LekarzWithUniqueParameterExists extends Exception{
    public LekarzWithUniqueParameterExists(String errorMessage) {
        super(errorMessage);
    }
}
