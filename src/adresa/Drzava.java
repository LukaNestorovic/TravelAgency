package adresa;


import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;

import java.io.*;
import java.util.ArrayList;

public class Drzava implements Serijalizacija<Drzava, String>, Kreiranje {
    private String naziv;
    private ArrayList<Mesto> mesta;

    public Drzava() {
    }

    public Drzava(String naziv) {
        this.naziv = naziv;

    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ArrayList<Mesto> getMesta() {
        return mesta;
    }

    public void setMesta(ArrayList<Mesto> mesta) {
        this.mesta = mesta;
    }

    @Override
    public Drzava izCSV(String naziv) {
        String csvFile = "data/drzave.csv";
        String red;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(podaci[0].equals(naziv)) {
                    Drzava drzava = new Drzava();
                    drzava.setNaziv(podaci[0]);
                    return drzava;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void uCSV(Drzava drzava) {
        ArrayList<String> podaci = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/drzave.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                podaci.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer noviRed = new StringBuffer();
        noviRed.append(drzava.getNaziv());
        podaci.add(String.valueOf(noviRed));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/drzave.csv"))) {
            for (String red : podaci) {
                bw.write(red);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void kreirajFajl() {
        String nazivFajla = "data/drzave.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                Drzava drzava = new Drzava("Norveska");
                drzava.uCSV(drzava);
                drzava = new Drzava("Svedska");
                drzava.uCSV(drzava);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
