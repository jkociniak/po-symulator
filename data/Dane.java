package data;

import IO.Input;
import simulationElts.Agent;
import simulationElts.Spotkanie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Dane {
    private Integer numerDnia;
    private Random gen;
    private Long seed;
    private Integer liczbaAgentow;
    private Double prawdTowarzyski;
    private Double prawdSpotkania;
    private Double prawdZarazenia;
    private Double prawdWyzdrowienia;
    private Double smiertelnosc;
    private Integer liczbaDni;
    private Integer srZnajomych;
    private String plikZRaportem;
    private ArrayList<LinkedList<Integer>> graf;
    private ArrayList<Agent> agenci;


    /* Parametr pobierz specyfikuje czy od razu pobierać dane.
    *  Możliwe, że ktoż będzie chciaż ustawić je samodzielnie.*/
    public Dane (boolean pobierz) {
        numerDnia = 1;
        seed = null;
        liczbaAgentow = null;
        prawdTowarzyski = null;
        prawdSpotkania = null;
        prawdZarazenia = null;
        prawdWyzdrowienia = null;
        smiertelnosc = null;
        liczbaDni = null;
        srZnajomych = null;
        plikZRaportem = null;

        if (pobierz) {
            try {
                Input.pobierzDane(this);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }

            gen = new Random(seed);
            graf = new ArrayList<>();
            agenci = new ArrayList<>();

            Agent.setParameters(this);
            Spotkanie.setGen(this);

            RandomGraf.losujGraf(this);
        }
    }

    public ArrayList<Agent> getAgenci() {
        return agenci;
    }

    public ArrayList<LinkedList<Integer>> getGraf() {
        return graf;
    }

    public Random getGen() {
        return gen;
    }

    public Integer getNumerDnia() {
        return numerDnia;
    }

    public void incrNumerDnia() {
        this.numerDnia++;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public Integer getLiczbaAgentow() {
        return liczbaAgentow;
    }

    public void setLiczbaAgentow(Integer liczbaAgentow) {
        this.liczbaAgentow = liczbaAgentow;
    }

    public Double getPrawdTowarzyski() {
        return prawdTowarzyski;
    }

    public void setPrawdTowarzyski(Double prawdTowarzyski) {
        this.prawdTowarzyski = prawdTowarzyski;
    }

    public Double getPrawdSpotkania() {
        return prawdSpotkania;
    }

    public void setPrawdSpotkania(Double prawdSpotkania) {
        this.prawdSpotkania = prawdSpotkania;
    }

    public Double getPrawdZarazenia() {
        return prawdZarazenia;
    }

    public void setPrawdZarazenia(Double prawdZarazenia) {
        this.prawdZarazenia = prawdZarazenia;
    }

    public Double getPrawdWyzdrowienia() {
        return prawdWyzdrowienia;
    }

    public void setPrawdWyzdrowienia(Double prawdWyzdrowienia) {
        this.prawdWyzdrowienia = prawdWyzdrowienia;
    }

    public Double getSmiertelnosc() {
        return smiertelnosc;
    }

    public void setSmiertelnosc(Double smiertelnosc) {
        this.smiertelnosc = smiertelnosc;
    }

    public Integer getLiczbaDni() {
        return liczbaDni;
    }

    public void setLiczbaDni(Integer liczbaDni) {
        this.liczbaDni = liczbaDni;
    }

    public Integer getSrZnajomych() {
        return srZnajomych;
    }

    public void setSrZnajomych(Integer srZnajomych) {
        this.srZnajomych = srZnajomych;
    }

    public String getPlikZRaportem() {
        return plikZRaportem;
    }

    public void setPlikZRaportem(String plikZRaportem) {
        this.plikZRaportem = plikZRaportem;
    }
}
