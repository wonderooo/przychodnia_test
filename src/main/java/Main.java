import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        var pacjenci = Pacjent.readFileToList("src/files/pacjenci.txt");
        var lekarze = Lekarz.readFileToList("src/files/lekarze.txt");
        var wizyty = Wizyta.readFileToList("src/files/wizyty.txt", lekarze, pacjenci);
        System.out.println("Znaleziono " + Color.RED + genericNajwiecejWizyt(lekarze).size() + Color.RESET + " lekarzy z najwieksza iloscia wizyt: ");
        genericNajwiecejWizyt(lekarze).forEach(System.out::println);

        System.out.println();
        System.out.println("Znaleziono " + Color.RED + genericNajwiecejWizyt(pacjenci).size() + Color.RESET + " pacjentow z najwieksza iloscia wizyt: ");
        genericNajwiecejWizyt(pacjenci).forEach(System.out::println);

        System.out.println();
        System.out.println("Znaleziono "+ Color.RED + specjalnoscNajwiecej(wizyty).size() + Color.RESET + " specjalnosci z najwieksza iloscia wizyt: ");
        specjalnoscNajwiecej(wizyty).forEach(x -> System.out.println(x.getKey()));

        System.out.println();
        System.out.println("Znaleziono "+ Color.RED + rokNajwiecejWizyt(wizyty).size() + Color.RESET + " lat z najwieksza iloscia wizyt: ");
        rokNajwiecejWizyt(wizyty).forEach(x -> System.out.println(x.getKey()));

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

    public static <U extends Najwiecej> List<U> genericNajwiecejWizyt(ArrayList<U> osoba) {
        osoba.sort(Comparator.comparingInt(o -> -(o.getWizyty().size())));
        return osoba.stream().filter(x -> x.getWizyty().size() == osoba.get(0).getWizyty().size()).collect(Collectors.toList());
    }

    public static List<Map.Entry<Integer, List<Wizyta>>> rokNajwiecejWizyt(ArrayList<Wizyta> wizyty) {
        Map<Integer, List<Wizyta>> zgrupowaneRocznie = wizyty.stream().collect(Collectors.groupingBy(Wizyta::getYearWizyty));
        List<Map.Entry<Integer, List<Wizyta>>> listaMapy = new ArrayList<>(zgrupowaneRocznie.entrySet());
        listaMapy.sort(Comparator.comparingInt(o -> -(o.getValue().size())));
        return listaMapy.stream().filter(x -> x.getValue().size() == listaMapy.get(0).getValue().size()).collect(Collectors.toList());
    }

    public static List<Map.Entry<Specjalnosc, List<Wizyta>>> specjalnoscNajwiecej(ArrayList<Wizyta> wizyty) {
        Map<Specjalnosc, List<Wizyta>> zgrupowaneRocznie = wizyty.stream().collect(Collectors.groupingBy(Wizyta::getSpecjalnosc));
        List<Map.Entry<Specjalnosc, List<Wizyta>>> listaMapy = new ArrayList<>(zgrupowaneRocznie.entrySet());
        listaMapy.sort(Comparator.comparingInt(o -> -(o.getValue().size())));
        return listaMapy.stream().filter(x -> x.getValue().size() == listaMapy.get(0).getValue().size()).collect(Collectors.toList());
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
