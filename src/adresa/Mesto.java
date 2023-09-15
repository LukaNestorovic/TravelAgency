package adresa;


import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;

import java.io.*;
import java.util.ArrayList;

public class Mesto implements Serijalizacija<Mesto, String>, Kreiranje {
    private String naziv;
    private String postanskiBroj;
    private Drzava drzava;

    public Mesto() {
    }

    public Mesto(String naziv, String postanskiBroj, Drzava drzava) {
        this.naziv = naziv;
        this.postanskiBroj = postanskiBroj;
        this.drzava = drzava;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    @Override
    public Mesto izCSV(String naziv) {
        String csvFile = "data/mesta.csv";
        String red;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(podaci[0].equals(naziv)) {
                    Mesto mesto = new Mesto();
                    mesto.setNaziv(podaci[0]);
                    mesto.setPostanskiBroj(podaci[1]);
                    mesto.setDrzava(new Drzava().izCSV(podaci[2]));
                    return mesto;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void uCSV(Mesto mesto) {
        ArrayList<String> podaci = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/mesta.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                podaci.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer noviRed = new StringBuffer();
        noviRed.append(mesto.getNaziv());
        noviRed.append(",");
        noviRed.append(mesto.getPostanskiBroj());
        noviRed.append(",");
        noviRed.append(mesto.getDrzava().getNaziv());
        podaci.add(String.valueOf(noviRed));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/mesta.csv"))) {
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
        String nazivFajla = "data/mesta.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                Mesto mesto = new Mesto("Oslo", "1000", new Drzava().izCSV("Norveska"));
                mesto.uCSV(mesto);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
