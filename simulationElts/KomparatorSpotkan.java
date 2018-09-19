package simulationElts;

import java.util.Comparator;

public class KomparatorSpotkan implements Comparator<Spotkanie> {
    @Override
    public int compare(Spotkanie o1, Spotkanie o2) {
        int wynik = Integer.compare(o1.dzien, o2.dzien);

        if (wynik == 0)
            wynik = Integer.compare(o1.nrSpotkania, o2.nrSpotkania);

        return wynik;
    }
}
