package IO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import data.Dane;
import simulationElts.*;



public class Output {
    private Dane dane;
    private FileWriter output;

    private void bladZapisu () {
        System.out.println("Błąd zapisu raportu do pliku");
        System.exit(1);
    }

    private void safeFlush () {
        try {
            this.output.flush();
        } catch (IOException e){
            bladZapisu();
        }
    }

    public Output (Dane dane) {
        this.dane = dane;

        try {
            this.output = new FileWriter(dane.getPlikZRaportem());
        } catch (IOException e) {
            bladZapisu();
        } finally {
            safeFlush();
        }
    }

    private void wypiszDane () {
        try {
            this.output.write("# twoje wyniki powinny zawierać te komentarze\n");
            this.output.write("seed=" + dane.getSeed() + "\n");
            this.output.write("liczbaAgentów=" + dane.getLiczbaAgentow() + "\n");
            this.output.write("prawdTowarzyski=" + dane.getPrawdTowarzyski() + "\n");
            this.output.write("prawdSpotkania=" + dane.getPrawdSpotkania() + "\n");
            this.output.write("prawdZarażenia=" + dane.getPrawdZarazenia() + "\n");
            this.output.write("prawdWyzdrowienia=" + dane.getPrawdWyzdrowienia() + "\n");
            this.output.write("śmiertelność=" + dane.getSmiertelnosc() + "\n");
            this.output.write("liczbaDni=" + dane.getLiczbaDni() + "\n");
            this.output.write("śrZnajomych=" + dane.getSrZnajomych() + "\n");
            this.output.write("\n");
        } catch (IOException e) {
            bladZapisu();
        } finally {
            safeFlush();
        }
    }

    private void wypiszAgentow () {
        try {
            this.output.write("# agenci jako: id typ lub id* typ dla chorego\n");

            ArrayList<Agent> agents = dane.getAgenci();

            for (int i = 0; i < agents.size(); i++) {
                this.output.write(Integer.toString(i+1));

                if (agents.get(i).getStan() == StanAgenta.ZARAZONY)
                    this.output.write("*");

                this.output.write(" ");

                if (agents.get(i).isTowarzyski())
                    this.output.write("towarzyski\n");
                else
                    this.output.write("zwykły\n");
            }

            this.output.write("\n");
        } catch (IOException e) {
            bladZapisu();
        } finally {
            safeFlush();
        }
    }

    private void wypiszGraf () {
        try {
            this.output.write("# graf\n");

            ArrayList<LinkedList<Integer>> agents = dane.getGraf();

            for (int i = 0; i < agents.size(); i++) {
                this.output.write(Integer.toString(i+1));

                for (Integer j : agents.get(i))
                    this.output.write(" " + Integer.toString(j+1));

                this.output.write("\n");
            }

            this.output.write("\n");
        } catch (IOException e) {
            bladZapisu();
        } finally {
            safeFlush();
        }
    }

    public void inicjuj () {
        wypiszDane();
        wypiszAgentow();
        wypiszGraf();

        try {
            this.output.write("# liczność w kolejnych dniach\n");
        } catch (IOException e) {
            bladZapisu();
        } finally {
            safeFlush();
        }
    }

    public void wypiszInfo () {
        int zdrowi = 0;
        int chorzy = 0;
        int odporni = 0;

        for (Agent a : dane.getAgenci()) {
            StanAgenta s = a.getStan();
            switch (s) {
                case ZDROWY: zdrowi++; break;
                case ZARAZONY: chorzy++; break;
                case ODPORNY: odporni++; break;
            }
        }

        try {
            this.output.write(zdrowi + " " + chorzy + " " + odporni + "\n");
        } catch (IOException e) {
            bladZapisu();
        } finally {
            safeFlush();
        }
    }
}
