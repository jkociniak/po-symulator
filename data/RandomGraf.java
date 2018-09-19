package data;

import simulationElts.Agent;

import java.util.ArrayList;
import java.util.LinkedList;

/* Klasa udostępniająca metodę losującą graf na podstawie danych z obiektu klasy Dane.
 * Losuje graf i od razu dodaje odpowiednie połączenia między agentami. */
public class RandomGraf {
    public static void losujGraf (Dane dane) {
        int n = dane.getLiczbaAgentow();
        ArrayList<Agent> agenci = dane.getAgenci();
        ArrayList<LinkedList<Integer>> graf = dane.getGraf();

        //Inicjalizacja.
        for (int i = 0; i<n; i++) {
            graf.add(new LinkedList<>());
            agenci.add(new Agent());
        }

        long oczekiwanaLiczbaKrawedzi = (dane.getSrZnajomych()*n)/2;
        long liczbaKrawedzi = 0;


        /* Losowanie wierzchołków i łączenie ich (jeśli są niepołączone),
           dopóki nie uzyskamy oczekiwanej liczby krawędzi. */
        int a1, a2;
        while (liczbaKrawedzi < oczekiwanaLiczbaKrawedzi) {
            a1 = dane.getGen().nextInt(n);
            a2 = dane.getGen().nextInt(n);
            if (a1 != a2 && !graf.get(a1).contains(a2)) {
                graf.get(a1).add(a2);
                graf.get(a2).add(a1);
                agenci.get(a1).dodajZnajomego(agenci.get(a2));

                liczbaKrawedzi++;
            }
        }

        //Jeden losowy agent musi zachorować.
        agenci.get(dane.getGen().nextInt(n)).zachoruj();
    }
}
