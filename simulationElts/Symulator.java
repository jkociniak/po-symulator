package simulationElts;

import IO.*;
import data.Dane;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Symulator {
    private Dane dane;
    private Output raport;

    public Symulator (boolean pobierz) {
        try {
            this.dane = new Dane(pobierz);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        this.raport = new Output(this.dane);
    }

    private void wykonajDzien () {
        ArrayList<Agent> agenci = this.dane.getAgenci();

        //Sprawdzenie czy agenci umarli czy wyzdrowieli. Jeśli umarł to usuwamy go z symulacji.
        agenci.removeIf(Agent::zaaktualizujStan);

        int nrDnia = this.dane.getNumerDnia();
        int koniecSymulacji = this.dane.getLiczbaDni();

        //Losujemy spotkania.
        for (Agent a : agenci)
            a.losujSpotkania(nrDnia, koniecSymulacji);

        PriorityQueue<Spotkanie> s;

        //Wykonujemy spotkania zaplanowane na ten dzień.
        for (Agent a : agenci) {
            s = a.getSpotkania();

            while (!s.isEmpty() && s.peek().dzien == nrDnia)
                s.poll().wykonaj();
        }
    }

    public void wykonajSymulacje () {
        this.raport.inicjuj(); //Wypisz początkowe dane i parametry.
        int koniecSymulacji = this.dane.getLiczbaDni();

        while (dane.getNumerDnia() <= koniecSymulacji) {
            wykonajDzien();
            this.raport.wypiszInfo();
            this.dane.incrNumerDnia();
        }
    }
}
