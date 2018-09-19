package simulationElts;

import data.Dane;

import java.util.Random;

public class Spotkanie {
    private static Random gen;
    private static int ktoreSpotkanie = 1;
    public int nrSpotkania;
    public int dzien;
    private Agent a1;
    private Agent a2;

    public static void setGen (Dane dane) {
        gen = dane.getGen();
    }

    Spotkanie (Agent a1, Agent a2, int dzien) {
        this.a1 = a1;
        this.a2 = a2;
        this.dzien = dzien;
        this.nrSpotkania = ktoreSpotkanie++;
    }

    public void wykonaj () {
        if (a1.getStan() == StanAgenta.ZARAZONY && a2.getStan() == StanAgenta.ZDROWY) {
            if (gen.nextDouble() < Agent.getPrawdZarazenia())
                a2.zachoruj();
        }

        if (a1.getStan() == StanAgenta.ZDROWY && a2.getStan() == StanAgenta.ZARAZONY) {
            if (gen.nextDouble() < Agent.getPrawdZarazenia())
                a1.zachoruj();
        }

        usun();
    }

    private void usun () {
        a1.getSpotkania().remove(this);
        a2.getSpotkania().remove(this);
    }

    public void usunSpotkanieUDrugiego (Agent a) {
        if (a1 == a)
            a2.getSpotkania().remove(this);
        else if (a2 == a)
            a1.getSpotkania().remove(this);
    }
}
