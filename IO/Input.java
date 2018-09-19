package IO;

import data.Dane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.channels.Channels;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Input {
    public static void pobierzDane (Dane d) throws Exception {
        Properties defProp = new Properties();
        try (FileInputStream stream = new FileInputStream("default.properties");
             Reader reader = Channels.newReader(stream.getChannel(), StandardCharsets.UTF_8.name())) {
            defProp.load(reader);
        } catch (MalformedInputException e) {
            throw new Exception("Plik default.properties nie jest tekstowy");
        } catch (FileNotFoundException e) {
            throw new Exception("Brak pliku default.properties");
        }

        Properties simConf = new Properties();
        try (FileInputStream stream = new FileInputStream("simulation-conf.xml")) {
            simConf.loadFromXML(stream);
        } catch (InvalidPropertiesFormatException e) {
            throw new Exception("Plik simulation-conf.xml nie jest XML");
        } catch (FileNotFoundException e) {
            throw new Exception("Brak pliku simulation-conf.xml");
        }

        pobierzDane(defProp, d);
        pobierzDane(simConf, d);

        sprawdzDane(d);
    }

    private static void sprawdzDane (Dane d) throws Exception {
        Function<String, String> errMsg = k -> "Brak wartości dla klucza " + k;

        String key = "";
        try {
            key = "seed";
            if (d.getSeed() == null)
                throw new Exception();

            key = "liczbaAgentow";
            if (d.getLiczbaAgentow() == null)
                throw new Exception();

            key = "prawdTowarzyski";
            if (d.getPrawdTowarzyski() == null)
                throw new Exception();

            key = "prawdSpotkania";
            if (d.getPrawdSpotkania() == null)
                throw new Exception();

            key = "prawdZarażenia";
            if (d.getPrawdZarazenia() == null)
                throw new Exception();

            key = "prawdWyzdrowienia";
            if (d.getPrawdWyzdrowienia() == null)
                throw new Exception();

            key = "śmiertelność";
            if (d.getSmiertelnosc() == null)
                throw new Exception();

            key = "liczbaDni";
            if (d.getLiczbaDni() == null)
                throw new Exception();

            key = "śrZnajomych";
            if (d.getSrZnajomych() == null)
                throw new Exception();

            key = "plikZRaportem";
            if (d.getPlikZRaportem() == null)
                throw new Exception();
        } catch (Exception e) {
            throw new Exception(errMsg.apply(key));
        }

        if (d.getSrZnajomych() > d.getLiczbaAgentow() - 1)
            throw new Exception("Niedozwolona wartość \"" + d.getSrZnajomych().toString() + "\" dla klucza śrZnajomych");
    }

    private static void pobierzDane (Properties p, Dane d) throws Exception {
        String key = "";
        String value = "";

        BiFunction<String, String, String> errMsg = (k, v) -> "Niedozwolona wartość \"" + v + "\" dla klucza " + k;

        try {
            key = "seed";
            value = p.getProperty(key);
            if (value != null)
                    d.setSeed(Long.parseLong(value));

            key = "liczbaAgentów";
            value = p.getProperty(key);
            if (value != null) {
                d.setLiczbaAgentow(Integer.parseInt(value));
                if (d.getLiczbaAgentow() > 1000000 || d.getLiczbaAgentow() < 1)
                    throw new NumberFormatException();
            }

            key = "prawdTowarzyski";
            value = p.getProperty(key);
            if (value != null) {
                d.setPrawdTowarzyski(Double.parseDouble(value));
                if (d.getPrawdTowarzyski() >= 1 || d.getPrawdTowarzyski() < 0)
                    throw new NumberFormatException();
            }

            key = "prawdSpotkania";
            value = p.getProperty(key);
            if (value != null) {
                d.setPrawdSpotkania(Double.parseDouble(value));
                if (d.getPrawdSpotkania() >= 1 || d.getPrawdSpotkania() < 0)
                    throw new NumberFormatException();
            }

            key = "prawdZarażenia";
            value = p.getProperty(key);
            if (value != null) {
                d.setPrawdZarazenia(Double.parseDouble(value));
                if (d.getPrawdZarazenia() >= 1 || d.getPrawdZarazenia() < 0)
                    throw new NumberFormatException();
            }

            key = "prawdWyzdrowienia";
            value = p.getProperty(key);
            if (value != null) {
                d.setPrawdWyzdrowienia(Double.parseDouble(value));
                if (d.getPrawdWyzdrowienia() >= 1 || d.getPrawdWyzdrowienia() < 0)
                    throw new NumberFormatException();
            }

            key = "śmiertelność";
            value = p.getProperty(key);
            if (value != null) {
                d.setSmiertelnosc(Double.parseDouble(value));
                if (d.getSmiertelnosc() >= 1 || d.getSmiertelnosc() < 0)
                    throw new NumberFormatException();
            }

            key = "liczbaDni";
            value = p.getProperty(key);
            if (value != null) {
                d.setLiczbaDni(Integer.parseInt(value));
                if (d.getLiczbaDni() >= 1000 || d.getLiczbaDni() < 1)
                    throw new NumberFormatException();
            }

            key = "śrZnajomych";
            value = p.getProperty(key);
            if (value != null) {
                d.setSrZnajomych(Integer.parseInt(value));
                if (d.getSrZnajomych() < 0)
                    throw new NumberFormatException();
            }

            key = "plikZRaportem";
            value = p.getProperty(key);
            if (value != null)
                d.setPlikZRaportem(value);
        } catch (NumberFormatException e) {
            throw new Exception(errMsg.apply(key, value));
        }
    }
}
