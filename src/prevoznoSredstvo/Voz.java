package prevoznoSredstvo;

import adresa.Adresa;
import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;
import korisnik.Klijent;

import java.io.*;
import java.util.ArrayList;

public class Voz extends PrevoznoSredstvo implements Serijalizacija<Voz, Integer>, Kreiranje {
    private KlasaVoznogMesta klasaVoznogMesta;

    public Voz() {
    }

    public Voz(Integer id, Boolean obrok, Adresa polaznaAdresa, Adresa odredisnaAdresa, KlasaVoznogMesta klasaVoznogMesta) {
        super(id, obrok, polaznaAdresa, odredisnaAdresa);
        this.klasaVoznogMesta = klasaVoznogMesta;
    }

    public KlasaVoznogMesta getKlasaVoznogMesta() {
        return klasaVoznogMesta;
    }

    public void setKlasaVoznogMesta(KlasaVoznogMesta klasaVoznogMesta) {
        this.klasaVoznogMesta = klasaVoznogMesta;
    }

    @Override
    public void uCSV(Voz voz){
        ArrayList<String> podaci = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/vozovi.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                podaci.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            StringBuffer noviRed = new StringBuffer();
            noviRed.append(voz.getId());
            noviRed.append(",");
            noviRed.append(voz.isObrok());
            noviRed.append(",");
            noviRed.append(voz.getPolaznaAdresa().getId());
            noviRed.append(",");
            noviRed.append(voz.getOdredisnaAdresa().getId());
            noviRed.append(",");
            noviRed.append(voz.getKlasaVoznogMesta().toString());
        podaci.add(String.valueOf(noviRed));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/vozovi.csv"))) {
            for (String red : podaci) {
                bw.write(red);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Voz izCSV(Integer id) {
        String csvFile = "data/vozovi.csv";
        String red;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(Integer.parseInt(podaci[0]) == id) {
                    Voz voz = new Voz();
                    voz.setId(Integer.parseInt(podaci[0]));
                    voz.setObrok(Boolean.parseBoolean(podaci[1]));
                    voz.setPolaznaAdresa(new Adresa().izCSV(Integer.parseInt(podaci[2])));
                    voz.setOdredisnaAdresa(new Adresa().izCSV(Integer.parseInt(podaci[3])));
                    voz.setKlasaVoznogMesta(KlasaVoznogMesta.valueOf(podaci[4]));
                    return voz;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ArrayList<Voz> sveIzCSV(Adresa adresa){
        String csvFile = "data/vozovi.csv";
        String red;
        ArrayList<Voz> vozovi = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null) {
                String[] podaci = red.split(",");
                if(Integer.parseInt(podaci[3]) == adresa.getId()){
                    Voz voz = new Voz();
                    voz.setId(Integer.parseInt(podaci[0]));
                    voz.setObrok(Boolean.parseBoolean(podaci[1]));
                    voz.setPolaznaAdresa(new Adresa().izCSV(Integer.parseInt(podaci[2])));
                    voz.setOdredisnaAdresa(new Adresa().izCSV(Integer.parseInt(podaci[3])));
                    voz.setKlasaVoznogMesta(KlasaVoznogMesta.valueOf(podaci[4]));
                    vozovi.add(voz);
                }
            }
            return vozovi;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer zakupi(Integer dani, String korisnickoIme) {
        if(this.obrok) {
            if(this.klasaVoznogMesta.toString().equals("prviRazred")) {
                return 540 + 640 + 510;
            } else if (this.klasaVoznogMesta.toString().equals("spavacaKola")) {
                return 540 + 640 + 1450;
            }
        } else {
            if(this.klasaVoznogMesta.toString().equals("prviRazred")) {
                return 640 + 510;
            } else if (this.klasaVoznogMesta.toString().equals("spavacaKola")) {
                return 640 + 1450;
            }
        }
        return null;
    }

    @Override
    public void otkazi() {
        if(this.obrok) {
            if(this.klasaVoznogMesta.toString().equals("prviRazred")) {
                System.out.println(540 + 640 + 510);
            } else if (this.klasaVoznogMesta.toString().equals("spavacaKola")) {
                System.out.println(540 + 640 + 1450);
            }
        } else {
            if(this.klasaVoznogMesta.toString().equals("prviRazred")) {
                System.out.println(640 + 510);
            } else if (this.klasaVoznogMesta.toString().equals("spavacaKola")) {
                System.out.println(640 + 1450);
            }
        }
    }

    @Override
    public void kreirajFajl() {
        String nazivFajla = "data/vozovi.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                Voz voz = new Voz(1,true,new Adresa().izCSV(1),new Adresa().izCSV(2),KlasaVoznogMesta.prviRazred);
                voz.uCSV(voz);
                voz = new Voz(3,true,new Adresa().izCSV(5),new Adresa().izCSV(2),KlasaVoznogMesta.prviRazred);
                voz.uCSV(voz);
                voz = new Voz(4,true,new Adresa().izCSV(7),new Adresa().izCSV(3),KlasaVoznogMesta.spavacaKola);
                voz.uCSV(voz);
                voz = new Voz(5,true,new Adresa().izCSV(1),new Adresa().izCSV(1),KlasaVoznogMesta.drugiRazred);
                voz.uCSV(voz);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
