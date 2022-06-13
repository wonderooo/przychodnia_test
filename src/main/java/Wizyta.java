import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Wizyta {
    private Lekarz lekarz;
    private Pacjent pacjent;
    private LocalDate dataWizyty;

    public Wizyta(Lekarz lekarz, Pacjent pacjent, LocalDate dataWizyty) {
        this.lekarz = lekarz;
        this.pacjent = pacjent;
        this.dataWizyty = dataWizyty;
    }

    public static ArrayList<Wizyta> readFileToList(String path, ArrayList<Lekarz> lekarze, ArrayList<Pacjent> pacjenci) throws FileNotFoundException {
        var reader = new BufferedReader(new FileReader(path));
        ArrayList<Wizyta> wizyty = new ArrayList<>();
        Stream<String> lines = reader.lines().skip(1);
        lines.forEach(line -> {
            String[] params = line.split("\t");
            String[] date = params[2].split("-");
            if (date[1].length() == 1){
                date[1] = "0" + date[1];
            }
            if (date[2].length() == 1){
                date[2] = "0" + date[2];
            }
            String dateRes = date[0] + "-" + date[1] + "-" + date[2];
            try {
                wizyty.add(new Wizyta(Lekarz.getLekarzById(lekarze ,Integer.parseInt(params[0])), Pacjent.getPacjentById(pacjenci ,Integer.parseInt(params[1])), LocalDate.parse(dateRes)));
            } catch (LekarzWithIdNotFound | PacjentWithIdNotFound e) {
                e.printStackTrace();
            }
        });
        return wizyty;
    }

    @Override
    public String toString(){
        return "WIZYTA: dnia: " + dataWizyty + ", " + pacjent + ", u " + lekarz;
    }

    public Lekarz getLekarz() {
        return lekarz;
    }

    public void setLekarz(Lekarz lekarz) {
        this.lekarz = lekarz;
    }

    public Pacjent getPacjent() {
        return pacjent;
    }

    public void setPacjent(Pacjent pacjent) {
        this.pacjent = pacjent;
    }

    public LocalDate getDataWizyty() {
        return dataWizyty;
    }

    public void setDataWizyty(LocalDate dataWizyty) {
        this.dataWizyty = dataWizyty;
    }
}
