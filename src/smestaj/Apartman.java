package smestaj;

import adresa.Adresa;
import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;

import java.io.*;
import java.util.ArrayList;


public class Apartman extends Smestaj implements Serijalizacija<Apartman, Integer>, Kreiranje {
    public Apartman() {
    }

    public Apartman(Double povrsina, Integer brojKreveta, TipPansiona tipPansiona, Adresa adresa) {
        super(povrsina, brojKreveta, tipPansiona, adresa);
    }

    @Override
    public void uCSV(Apartman apartman){
        ArrayList<String> podaci = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/apartmani.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                podaci.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer noviRed = new StringBuffer();
        noviRed.append(apartman.getPovrsina());
        noviRed.append(",");
        noviRed.append(apartman.getBrojKreveta());
        noviRed.append(",");
        noviRed.append(apartman.getTipPansiona());
        noviRed.append(",");
        noviRed.append(apartman.getAdresa().getId());
        podaci.add(String.valueOf(noviRed));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/apartmani.csv"))) {
            for (String red : podaci) {
                bw.write(red);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Apartman izCSV(Integer adresa) {
        String csvFile = "data/apartmani.csv";
        String red;
        String splitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(splitBy);
                if(Integer.parseInt(podaci[3]) == adresa) {
                    Apartman apartman = new Apartman();
                    apartman.setPovrsina(Double.parseDouble(podaci[0]));
                    apartman.setBrojKreveta(Integer.parseInt(podaci[1]));
                    apartman.setTipPansiona(TipPansiona.valueOf(podaci[2]));
                    apartman.setAdresa(new Adresa().izCSV(Integer.parseInt(podaci[3])));
                    return apartman;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Integer zakupi(Integer dani, String korisnickoIme) {
        if(this.getTipPansiona().toString().equals("nocenje")) {
            return 1500 * dani;
        } else if (this.getTipPansiona().toString().equals("nocenjeSaDoruckom")) {
            return 1900 * dani;
        } else if (this.getTipPansiona().toString().equals("polupansion")) {
            return 2300 * dani;
        } else if (this.getTipPansiona().toString().equals("punPansion")) {
            return 3000 * dani;
        } else if (this.getTipPansiona().toString().equals("allInclusive")) {
            return 4000 * dani;
        } else {
            return null;
        }
    }

    @Override
    public void otkazi() {
        if(this.getTipPansiona().toString().equals("nocenje")) {
            System.out.println(1500);
        } else if (this.getTipPansiona().toString().equals("nocenjeSaDoruckom")) {
            System.out.println(1900);
        } else if (this.getTipPansiona().toString().equals("polupansion")) {
            System.out.println(2300);
        } else if (this.getTipPansiona().toString().equals("punPansion")) {
            System.out.println(3000);
        } else if (this.getTipPansiona().toString().equals("allInclusive")) {
            System.out.println(4000);
        }
    }

    @Override
    public void kreirajFajl() {
        String nazivFajla = "data/apartmani.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                Apartman apartman = new Apartman(50.0,5,TipPansiona.nocenje,new Adresa().izCSV(3));
                apartman.uCSV(apartman);
                apartman = new Apartman(70.0,4,TipPansiona.nocenje,new Adresa().izCSV(4));
                apartman.uCSV(apartman);
                apartman = new Apartman(20.0,1,TipPansiona.nocenje,new Adresa().izCSV(5));
                apartman.uCSV(apartman);
                apartman = new Apartman(40.0,2,TipPansiona.nocenjeSaDoruckom,new Adresa().izCSV(6));
                apartman.uCSV(apartman);
                apartman = new Apartman(30.0,1,TipPansiona.punPansion,new Adresa().izCSV(7));
                apartman.uCSV(apartman);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
