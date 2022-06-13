import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        var pacjenci = Pacjent.readFileToList("src/files/pacjenci.txt");
        var lekarze = Lekarz.readFileToList("src/files/lekarze.txt");
        var wizyty = Wizyta.readFileToList("src/files/wizyty.txt", lekarze, pacjenci);
        System.out.println("Znaleziono " + Color.RED + lekarzNajwiecejWizyt(wizyty).size() + Color.RESET + " lekarzy z najwieksza iloscia wizyt (" + Color.GREEN + lekarzNajwiecejWizyt(wizyty).get(0).getValue() + Color.RESET + "): ");
        lekarzNajwiecejWizyt(wizyty).forEach(x -> System.out.println(x.getKey()));

        System.out.println();
        System.out.println("Znaleziono " + Color.RED + pacjentNajwiecejWizyt(wizyty).size() + Color.RESET + " pacjentow z najwieksza iloscia wizyt (" + Color.GREEN + pacjentNajwiecejWizyt(wizyty).get(0).getValue() + Color.RESET + "): ");
        pacjentNajwiecejWizyt(wizyty).forEach(x -> System.out.println(x.getKey()));

        System.out.println();
        System.out.println("Znaleziono "+ Color.RED + specjalonscNajwiecej(wizyty).size() + Color.RESET + " specjalnosci z najwieksza iloscia wizyt (" + Color.GREEN + specjalonscNajwiecej(wizyty).get(0).getValue() + Color.RESET + "): ");
        specjalonscNajwiecej(wizyty).forEach(x -> System.out.println(x.getKey()));

        System.out.println();
        System.out.println("Znaleziono " + Color.RED + rokNajwiecej(wizyty).size() + Color.RESET +" lat z najwieksza iloscia wizyt (" + Color.GREEN + rokNajwiecej(wizyty).get(0).getValue() + Color.RESET + "): ");
        rokNajwiecej(wizyty).forEach(x -> System.out.println(x.getKey()));

        System.out.println();
        System.out.println(Color.YELLOW + "TOP 5 NAJSTARSZYCH LEKARZY" + Color.RESET);
        for (Lekarz l : top5NajstarszychLekarzy(lekarze)){
            System.out.println(l);
        }

        System.out.println();
        if (pacjenci5RoznychLekarzy(wizyty)){
            System.out.println(Color.RED + "Jest pacjent z 5 roznymi lakarzami" + Color.RESET);
        }else {
            System.out.println(Color.RED + "Nie ma pacjenta z 5 roznymi lekarzami" + Color.RESET);
        }

        System.out.println();
        if (lekarzeTylko1Pacjent(wizyty)){
            System.out.println(Color.RED + "Jest lekarz z tylko 1 pacjentem" + Color.RESET);
        }else {
            System.out.println(Color.RED + "Nie ma lekarza tylko z 1 pacjentem" + Color.RESET);
        }
    }

    public static ArrayList<Map.Entry<Lekarz, Integer>> lekarzNajwiecejWizyt(ArrayList<Wizyta> wizyty) throws FirstElementNotFound {
        var liczbaWizytLekarzy = new HashMap<Lekarz, Integer>();
        for (Wizyta wizyta : wizyty){
            if (liczbaWizytLekarzy.containsKey(wizyta.getLekarz())){
                liczbaWizytLekarzy.replace(wizyta.getLekarz(), liczbaWizytLekarzy.get(wizyta.getLekarz()) + 1);
            }else {
                liczbaWizytLekarzy.put(wizyta.getLekarz(), 1);
            }
        }
        ArrayList<Map.Entry<Lekarz, Integer>> lekarzNajwiecej = new ArrayList<>(List.of(liczbaWizytLekarzy.entrySet().stream().findFirst().orElseThrow(() -> new FirstElementNotFound("Nie znaleziono 1 elementu"))));
        for (Map.Entry<Lekarz, Integer> liczbaWizytLekarza : liczbaWizytLekarzy.entrySet()){
             if (liczbaWizytLekarza.getValue() > lekarzNajwiecej.get(0).getValue()){
                 lekarzNajwiecej.clear();
                 lekarzNajwiecej.add(liczbaWizytLekarza);
             }else if (liczbaWizytLekarza.getValue().equals(lekarzNajwiecej.get(0).getValue())){
                 lekarzNajwiecej.add(liczbaWizytLekarza);
             }
        }
        return lekarzNajwiecej;
    }

    public static ArrayList<Map.Entry<Pacjent, Integer>> pacjentNajwiecejWizyt(ArrayList<Wizyta> wizyty) throws FirstElementNotFound {
        var liczbaWizytPacjentow = new HashMap<Pacjent, Integer>();
        for (Wizyta wizyta : wizyty){
            if (liczbaWizytPacjentow.containsKey(wizyta.getPacjent())){
                liczbaWizytPacjentow.replace(wizyta.getPacjent(), liczbaWizytPacjentow.get(wizyta.getPacjent()) + 1);
            }else {
                liczbaWizytPacjentow.put(wizyta.getPacjent(), 1);
            }
        }
        ArrayList<Map.Entry<Pacjent, Integer>> pacjentNajwiecej = new ArrayList<>(List.of(liczbaWizytPacjentow.entrySet().stream().findFirst().orElseThrow(() -> new FirstElementNotFound("Nie znaleziono 1 elementu"))));
        for (Map.Entry<Pacjent, Integer> liczbaWizytPacjenta : liczbaWizytPacjentow.entrySet()){
            if (liczbaWizytPacjenta.getValue() > pacjentNajwiecej.get(0).getValue()){
                pacjentNajwiecej.clear();
                pacjentNajwiecej.add(liczbaWizytPacjenta);
            }else if (liczbaWizytPacjenta.getValue().equals(pacjentNajwiecej.get(0).getValue())){
                pacjentNajwiecej.add(liczbaWizytPacjenta);
            }
        }
        return pacjentNajwiecej;
    }

    public static ArrayList<Map.Entry<Specjalnosc, Integer>> specjalonscNajwiecej(ArrayList<Wizyta> wizyty) throws FirstElementNotFound {
        var liczbaSpecjalnosci = new HashMap<Specjalnosc, Integer>();
        for (Wizyta wizyta : wizyty){
            if (liczbaSpecjalnosci.containsKey(wizyta.getLekarz().getSpecjalnosc())){
                liczbaSpecjalnosci.replace(wizyta.getLekarz().getSpecjalnosc(), liczbaSpecjalnosci.get(wizyta.getLekarz().getSpecjalnosc()) + 1);
            }else {
                liczbaSpecjalnosci.put(wizyta.getLekarz().getSpecjalnosc(), 1);
            }
        }
        ArrayList<Map.Entry<Specjalnosc, Integer>> specjalnoscNajwiecej = new ArrayList<>(List.of(liczbaSpecjalnosci.entrySet().stream().findFirst().orElseThrow(() -> new FirstElementNotFound("Nie znaleziono 1 elementu"))));
        for (Map.Entry<Specjalnosc, Integer> liczbaSpecjalnosciPoj : liczbaSpecjalnosci.entrySet()){
            if (liczbaSpecjalnosciPoj.getValue() > specjalnoscNajwiecej.get(0).getValue()){
                specjalnoscNajwiecej.clear();
                specjalnoscNajwiecej.add(liczbaSpecjalnosciPoj);
            } else if (liczbaSpecjalnosciPoj.getValue().equals(specjalnoscNajwiecej.get(0).getValue())){
                specjalnoscNajwiecej.add(liczbaSpecjalnosciPoj);
            }
        }
        return specjalnoscNajwiecej;
    }

    public static ArrayList<Map.Entry<Integer, Integer>> rokNajwiecej(ArrayList<Wizyta> wizyty) throws FirstElementNotFound {
        var liczbaNaDanyRok = new HashMap<Integer, Integer>();
        for (Wizyta wizyta : wizyty){
            if (liczbaNaDanyRok.containsKey(wizyta.getDataWizyty().getYear())){
                liczbaNaDanyRok.replace(wizyta.getDataWizyty().getYear(), liczbaNaDanyRok.get(wizyta.getDataWizyty().getYear()) + 1 );
            }else {
                liczbaNaDanyRok.put(wizyta.getDataWizyty().getYear(), 1);
            }
        }
        ArrayList<Map.Entry<Integer, Integer>> rokNajwiecej = new ArrayList<>(List.of(liczbaNaDanyRok.entrySet().stream().findFirst().orElseThrow(() -> new FirstElementNotFound("Nie znaleziono 1 elementu"))));
        for (Map.Entry<Integer, Integer> liczbaNaDanyRokPoj : liczbaNaDanyRok.entrySet()){
            if (liczbaNaDanyRokPoj.getValue() > rokNajwiecej.get(0).getValue()){
                rokNajwiecej.clear();
                rokNajwiecej.add(liczbaNaDanyRokPoj);
            } else if (liczbaNaDanyRokPoj.getValue().equals(rokNajwiecej.get(0).getValue())){
                rokNajwiecej.add(liczbaNaDanyRokPoj);
            }
        }
        return rokNajwiecej;
    }

    public static Lekarz[] top5NajstarszychLekarzy(ArrayList<Lekarz> lekarze){
        Collections.sort(lekarze);
        return new Lekarz[]{lekarze.get(0), lekarze.get(1), lekarze.get(2), lekarze.get(3), lekarze.get(4)};
    }

    public static boolean pacjenci5RoznychLekarzy(ArrayList<Wizyta> wizyty){
        var lekarzeDangegoPacjenta = new HashMap<Pacjent, ArrayList<Lekarz>>();
        for (Wizyta wizyta : wizyty){
            if (lekarzeDangegoPacjenta.containsKey(wizyta.getPacjent())){
                if (!lekarzeDangegoPacjenta.get(wizyta.getPacjent()).contains(wizyta.getLekarz())){
                    lekarzeDangegoPacjenta.get(wizyta.getPacjent()).add(wizyta.getLekarz());
                }
            }else {
                lekarzeDangegoPacjenta.put(wizyta.getPacjent(), new ArrayList<>(List.of(wizyta.getLekarz())));
            }
        }
        var res = lekarzeDangegoPacjenta.entrySet().stream().filter(x -> x.getValue().size() >= 5).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return res.size() != 0;
    }

    public static boolean lekarzeTylko1Pacjent(ArrayList<Wizyta> wizyty){
        var pacjenciDanegoLekarza = new HashMap<Lekarz, ArrayList<Pacjent>>();
        for (Wizyta wizyta : wizyty) {
            if (pacjenciDanegoLekarza.containsKey(wizyta.getLekarz())){
                if (!pacjenciDanegoLekarza.get(wizyta.getLekarz()).contains(wizyta.getPacjent())){
                    pacjenciDanegoLekarza.get(wizyta.getLekarz()).add(wizyta.getPacjent());
                }
            }else {
                pacjenciDanegoLekarza.put(wizyta.getLekarz(), new ArrayList<>(List.of(wizyta.getPacjent())));
            }
        }
        var res = pacjenciDanegoLekarza.entrySet().stream().filter(x -> x.getValue().size() == 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return res.size() != 0;
    }
}

class FirstElementNotFound extends Exception {
    public FirstElementNotFound(String errorMessage) {
        super(errorMessage);
    }
}
