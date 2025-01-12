package Exercitiul2;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainApp {
    public static void main(String[] args) throws IOException {
        Set<InstrumentMuzical> instrumente = new HashSet<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());

        while (true) {
            System.out.println("\nMeniu:");
            System.out.println("1. Creează instrumente");
            System.out.println("2. Salvează instrumentele în fișier");
            System.out.println("3. Încarcă instrumentele din fișier");
            System.out.println("4. Afișează instrumentele");
            System.out.println("5. Verifică duplicatele în Set");
            System.out.println("6. Șterge instrumentele cu preț mai mare de 3000 RON");
            System.out.println("7. Afișează chitare");
            System.out.println("8. Afișează tobe");
            System.out.println("9. Afișează chitara cu cele mai multe corzi");
            System.out.println("10. Afișează tobele acustice sortate");
            System.out.println("11. Ieșire");
            System.out.print("Alege opțiunea: ");

            Scanner scanner = new Scanner(System.in);
            int optiune = scanner.nextInt();

            switch (optiune) {
                case 1:
                    creeazaInstrumente(instrumente);
                    break;
                case 2:
                    salveazaInstrumenteInFisier(instrumente, mapper);
                    break;
                case 3:
                    incarcaInstrumenteDinFisier(instrumente, mapper);
                    break;
                case 4:
                    afiseazaInstrumente(instrumente);
                    break;
                case 5:
                    verificaDuplicate(instrumente);
                    break;
                case 6:
                    stergeInstrumentePeste3000(instrumente);
                    break;
                case 7:
                    afiseazaChitare(instrumente);
                    break;
                case 8:
                    afiseazaTobe(instrumente);
                    break;
                case 9:
                    afiseazaChitaraCuCeleMaiMulteCorzi(instrumente);
                    break;
                case 10:
                    afiseazaTobeAcusticeSortate(instrumente);
                    break;
                case 11:
                    System.out.println("La revedere!");
                    return;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }

    private static void creeazaInstrumente(Set<InstrumentMuzical> instrumente) {
        instrumente.add(new Chitara("Fender", 2500, Chitara.TipChitara.ELECTRICA, 6));
        instrumente.add(new Chitara("Yamaha", 1800, Chitara.TipChitara.ACUSTICA, 6));
        instrumente.add(new Chitara("Gibson", 3000, Chitara.TipChitara.CLASICA, 6));
        instrumente.add(new SetTobe("Pearl", 3500, SetTobe.TipTobe.ELECTRONICE, 5, 2));
        instrumente.add(new SetTobe("Roland", 4000, SetTobe.TipTobe.ACUSTICE, 7, 3));
        instrumente.add(new SetTobe("Tama", 2500, SetTobe.TipTobe.ACUSTICE, 6, 2));
        System.out.println("Instrumentele au fost adăugate.");
    }

    private static void salveazaInstrumenteInFisier(Set<InstrumentMuzical> instrumente, ObjectMapper mapper) throws IOException {
        mapper.writeValue(new File("instrumente.json"), instrumente);
        System.out.println("Instrumentele au fost salvate în fișierul instrumente.json.");
    }

    private static void incarcaInstrumenteDinFisier(Set<InstrumentMuzical> instrumente, ObjectMapper mapper) throws IOException {
        instrumente.clear();
        instrumente.addAll(mapper.readValue(new File("instrumente.json"), TypeFactory.defaultInstance().constructCollectionType(Set.class, InstrumentMuzical.class)));
        System.out.println("Instrumentele au fost încărcate din fișierul JSON.");
    }

    private static void afiseazaInstrumente(Set<InstrumentMuzical> instrumente) {
        instrumente.forEach(System.out::println);
    }

    private static void verificaDuplicate(Set<InstrumentMuzical> instrumente) {
        boolean adaugat = instrumente.add(new Chitara("Fender", 2500, Chitara.TipChitara.ELECTRICA, 6));
        if (adaugat) {
            System.out.println("Instrumentul a fost adăugat.");
        } else {
            System.out.println("Instrumentul nu a fost adăugat (este duplicat).");
        }
    }

    private static void stergeInstrumentePeste3000(Set<InstrumentMuzical> instrumente) {
        instrumente.removeIf(instrument -> instrument.getPret() > 3000);
        System.out.println("Instrumentele cu preț mai mare de 3000 RON au fost șterse.");
    }

    private static void afiseazaChitare(Set<InstrumentMuzical> instrumente) {
        instrumente.stream()
                .filter(instrument -> instrument instanceof Chitara)
                .forEach(System.out::println);
    }

    private static void afiseazaTobe(Set<InstrumentMuzical> instrumente) {
        instrumente.stream()
                .filter(instrument -> instrument instanceof SetTobe)
                .forEach(System.out::println);
    }

    private static void afiseazaChitaraCuCeleMaiMulteCorzi(Set<InstrumentMuzical> instrumente) {
        instrumente.stream()
                .filter(instrument -> instrument instanceof Chitara)
                .map(instrument -> (Chitara) instrument)
                .max(Comparator.comparingInt(Chitara::getNr_corzi))
                .ifPresent(System.out::println);
    }

    private static void afiseazaTobeAcusticeSortate(Set<InstrumentMuzical> instrumente) {
        instrumente.stream()
                .filter(instrument -> instrument instanceof SetTobe)
                .map(instrument -> (SetTobe) instrument)
                .filter(tobe -> tobe.getTip_tobe() == SetTobe.TipTobe.ACUSTICE)
                .sorted(Comparator.comparingInt(SetTobe::getNr_tobe))
                .forEach(System.out::println);
    }
}
