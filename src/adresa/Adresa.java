package adresa;




import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;

import java.io.*;
import java.util.ArrayList;


public class Adresa implements Serijalizacija<Adresa, Integer>, Kreiranje {
    private Integer id;
    private String ulica;
    private String broj;
    private Mesto mesto;

    public Adresa() {
    }

    public Adresa(Integer id, String ulica, String broj, Mesto mesto) {
        this.id = id;
        this.ulica = ulica;
        this.broj = broj;
        this.mesto = mesto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @Override
    public Adresa izCSV(Integer id) {
        String csvFile = "data/adrese.csv";
        String red;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(Integer.parseInt(podaci[0]) == id) {
                    Adresa adresa = new Adresa();
                    adresa.setId(Integer.parseInt(podaci[0]));
                    adresa.setBroj(podaci[2]);
                    adresa.setUlica(podaci[1]);
                    adresa.setMesto(new Mesto().izCSV(podaci[3]));
                    return adresa;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void uCSV(Adresa adresa) {
        ArrayList<String> podaci = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/adrese.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                podaci.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer noviRed = new StringBuffer();
        noviRed.append(adresa.getId());
        noviRed.append(",");
        noviRed.append(adresa.getUlica());
        noviRed.append(",");
        noviRed.append(adresa.getBroj());
        noviRed.append(",");
        noviRed.append(adresa.getMesto().getNaziv());
        podaci.add(String.valueOf(noviRed));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/adrese.csv"))) {
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
        String nazivFajla = "data/adrese.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                Adresa adresa = new Adresa(1,"ulica","broj1",new Mesto().izCSV("Oslo"));
                adresa.uCSV(adresa);
                adresa = new Adresa(2,"ulica","broj2",new Mesto().izCSV("Oslo"));
                adresa.uCSV(adresa);
                adresa = new Adresa(3,"ulica","broj3",new Mesto().izCSV("Oslo"));
                adresa.uCSV(adresa);
                adresa = new Adresa(4,"ulica","broj4",new Mesto().izCSV("Oslo"));
                adresa.uCSV(adresa);
                adresa = new Adresa(5,"ulica","broj5",new Mesto().izCSV("Oslo"));
                adresa.uCSV(adresa);
                adresa = new Adresa(6,"ulica","broj6",new Mesto().izCSV("Oslo"));
                adresa.uCSV(adresa);
                adresa = new Adresa(7,"ulica","broj7",new Mesto().izCSV("Oslo"));
                adresa.uCSV(adresa);
                adresa = new Adresa(8,"ulica","broj8",new Mesto().izCSV("Oslo"));
                adresa.uCSV(adresa);
                adresa = new Adresa(9,"ulica","broj9",new Mesto().izCSV("Oslo"));
                adresa.uCSV(adresa);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
