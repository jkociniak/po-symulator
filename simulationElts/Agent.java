package simulationElts;

import data.Dane;

import java.util.*;

public class Agent {
    private static double smiertelnosc;
    private static double prawdWyzdrowienia;
    private static double prawdZarazenia;
    private static double prawdSpotkania;
    private static double prawdTowarzyski;
    private static final KomparatorSpotkan cmp = new KomparatorSpotkan();
    private static Random gen;

    private final boolean towarzyski;
    private LinkedList<Agent> znajomi; //Lista znajomych agenta.
    private StanAgenta stan;
    private PriorityQueue<Spotkanie> spotkania; //Kolejka priorytetowa spotkań (priorytet to dzień spotkania, potem jego numer).


    /* Funkcja ustawiająca parametry zachowania agentów na podstawie danych z obiektu klasy Dane */
    public static void setParameters (Dane dane) {
        smiertelnosc = dane.getSmiertelnosc();
        prawdWyzdrowienia = dane.getPrawdWyzdrowienia();
        prawdZarazenia = dane.getPrawdZarazenia();
        prawdSpotkania = dane.getPrawdSpotkania();
        prawdTowarzyski = dane.getPrawdTowarzyski();
        gen = dane.getGen();
    }

    public Agent () {
        this.towarzyski = gen.nextDouble() < prawdTowarzyski;
        this.znajomi = new LinkedList<>();
        this.stan = StanAgenta.ZDROWY;
        this.spotkania = new PriorityQueue<>(1000, cmp);
    }

    public StanAgenta getStan() {
        return stan;
    }


    public PriorityQueue<Spotkanie> getSpotkania() {
        return spotkania;
    }

    public static double getPrawdZarazenia() {
        return prawdZarazenia;
    }

    public boolean isTowarzyski() {
        return towarzyski;
    }

    public void zachoruj () {this.stan = StanAgenta.ZARAZONY;}

    public boolean zaaktualizujStan () {
        if (this.stan == StanAgenta.ZARAZONY) {
            /*Jesli agent umrze, usuwamy go ze znajomych innych
             agentów oraz usuwamy spotkania w których bierze udział. */

            if (gen.nextDouble() < smiertelnosc) {
                for (Agent a : this.znajomi)
                    a.usunZnajomego(this);

                Iterator<Spotkanie> iter = this.spotkania.iterator();
                while (iter.hasNext()) {
                    Spotkanie s = iter.next();
                    s.usunSpotkanieUDrugiego(this);
                    iter.remove();
                }

                return true;
            }

            else if (gen.nextDouble() < prawdWyzdrowienia)
                this.stan = StanAgenta.ODPORNY;
        }

        return false;
    }

    public void dodajZnajomego (Agent a) {
        if (!this.znajomi.contains(a)) {
            this.znajomi.add(a);
            a.znajomi.add(this);
        }
    }

    private void usunZnajomego (Agent a) {
        this.znajomi.remove(a);
    }

    private void zaplanujSpotkanie (Agent a, int dzien) {
        Spotkanie s = new Spotkanie(this, a, dzien);
        spotkania.add(s);
        a.getSpotkania().add(s);
    }


    public void losujSpotkania(int dzien, int koniec) {
        if (this.znajomi.isEmpty() || dzien == koniec)
            return;

        if (this.stan != StanAgenta.ZARAZONY) {
            while (gen.nextDouble() < prawdSpotkania) {
                //Wektor potencjalnych agentów z którymi this może się spotkać.
                ArrayList<Agent> potencjalni = new ArrayList<>(this.znajomi);

                if (this.towarzyski) {
                    for (Agent a : this.znajomi)
                        for (Agent b : a.znajomi)
                            if (!potencjalni.contains(b))
                                potencjalni.add(b);

                    potencjalni.remove(this);
                }

                int wylosowany = gen.nextInt(potencjalni.size());
                int dzienSpotkania = gen.nextInt(koniec - dzien) + dzien + 1;

                zaplanujSpotkanie(potencjalni.get(wylosowany), dzienSpotkania);
            }
        }

        else {
            double nowePrawdSpotkania = this.towarzyski ? prawdSpotkania : prawdSpotkania/2;

            while (gen.nextDouble() < nowePrawdSpotkania) {
                //Wektor potencjalnych agentów z którymi this może się spotkać.
                ArrayList<Agent> potencjalni = new ArrayList<>(this.znajomi);

                int wylosowany = gen.nextInt(potencjalni.size());
                int dzienSpotkania = gen.nextInt(koniec - dzien) + dzien + 1;

                zaplanujSpotkanie(potencjalni.get(wylosowany), dzienSpotkania);
            }
        }
    }
}
