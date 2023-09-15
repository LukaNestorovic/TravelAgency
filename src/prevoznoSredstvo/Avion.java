package prevoznoSredstvo;

import adresa.Adresa;
import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;

import java.io.*;
import java.util.ArrayList;

public class Avion extends PrevoznoSredstvo implements Serijalizacija<Avion, Integer>, Kreiranje {
    private KlasaAvionskogMesta klasaAvionskogMesta;

    public Avion() {
    }

    public Avion(Integer id, Boolean obrok, Adresa polaznaAdresa, Adresa odredisnaAdresa, KlasaAvionskogMesta klasaAvionskogMesta) {
        super(id, obrok, polaznaAdresa, odredisnaAdresa);
        this.klasaAvionskogMesta = klasaAvionskogMesta;
    }

    public KlasaAvionskogMesta getKlasaAvionskogMesta() {
        return klasaAvionskogMesta;
    }

    public void setKlasaAvionskogMesta(KlasaAvionskogMesta klasaAvionskogMesta) {
        this.klasaAvionskogMesta = klasaAvionskogMesta;
    }

    @Override
    public void uCSV(Avion avion){
        ArrayList<String> podaci = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/avioni.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                podaci.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            StringBuffer noviRed = new StringBuffer();
            noviRed.append(avion.getId());
            noviRed.append(",");
            noviRed.append(avion.isObrok());
            noviRed.append(",");
            noviRed.append(avion.getPolaznaAdresa().getId());
            noviRed.append(",");
            noviRed.append(avion.getOdredisnaAdresa().getId());
            noviRed.append(",");
            noviRed.append(avion.getKlasaAvionskogMesta());
        podaci.add(String.valueOf(noviRed));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/avioni.csv"))) {
            for (String red : podaci) {
                bw.write(red);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Avion izCSV(Integer id) {
        String csvFile = "data/avioni.csv";
        String red;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(Integer.parseInt(podaci[0]) == id) {
                    Avion avion = new Avion();
                    avion.setId(Integer.parseInt(podaci[0]));
                    avion.setObrok(Boolean.parseBoolean(podaci[1]));
                    avion.setPolaznaAdresa(new Adresa().izCSV(Integer.parseInt(podaci[2])));
                    avion.setOdredisnaAdresa(new Adresa().izCSV(Integer.parseInt(podaci[3])));
                    avion.setKlasaAvionskogMesta(KlasaAvionskogMesta.valueOf(podaci[4]));
                    return avion;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ArrayList<Avion> sveIzCSV(Adresa adresa){
        String csvFile = "data/avioni.csv";
        String red;
        ArrayList<Avion> avioni = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null) {
                String[] podaci = red.split(",");
                if(Integer.parseInt(podaci[3]) == adresa.getId()){
                    Avion avion = new Avion();
                    avion.setId(Integer.parseInt(podaci[0]));
                    avion.setObrok(Boolean.parseBoolean(podaci[1]));
                    avion.setPolaznaAdresa(new Adresa().izCSV(Integer.parseInt(podaci[2])));
                    avion.setOdredisnaAdresa(new Adresa().izCSV(Integer.parseInt(podaci[3])));
                    avion.setKlasaAvionskogMesta(KlasaAvionskogMesta.valueOf(podaci[4]));
                    avioni.add(avion);
                }
            }
            return avioni;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer zakupi(Integer dani, String korisnickoIme) {
        if(this.obrok) {
            if(this.klasaAvionskogMesta.toString().equals("ekonomska")) {
                return 540 + 640 + 300;
            } else if (this.klasaAvionskogMesta.toString().equals("biznis")) {
                return 540 + 640 + 1350;
            }
        } else {
            if(this.klasaAvionskogMesta.toString().equals("ekonomska")) {
                return 640 + 300;
            } else if (this.klasaAvionskogMesta.toString().equals("biznis")) {
                return 640 + 1350;
            }
        }
        return null;
    }

    @Override
    public void otkazi() {
        if(this.obrok) {
            if(this.klasaAvionskogMesta.toString().equals("ekonomska")) {
                System.out.println(540 + 640 + 300);
            } else if (this.klasaAvionskogMesta.toString().equals("biznis")) {
                System.out.println(540 + 640 + 1350);
            }
        } else {
            if(this.klasaAvionskogMesta.toString().equals("ekonomska")) {
                System.out.println(640 + 300);
            } else if (this.klasaAvionskogMesta.toString().equals("biznis")) {
                System.out.println(640 + 1350);
            }
        }
    }

    @Override
    public void kreirajFajl() {
        String nazivFajla = "data/avioni.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                Avion avion = new Avion(2,true,new Adresa().izCSV(1),new Adresa().izCSV(2),KlasaAvionskogMesta.ekonomska);
                avion.uCSV(avion);
                avion = new Avion(100,true,new Adresa().izCSV(1),new Adresa().izCSV(3),KlasaAvionskogMesta.biznis);
                avion.uCSV(avion);
                avion = new Avion(65,true,new Adresa().izCSV(4),new Adresa().izCSV(3),KlasaAvionskogMesta.biznis);
                avion.uCSV(avion);
                avion = new Avion(2,true,new Adresa().izCSV(4),new Adresa().izCSV(3),KlasaAvionskogMesta.biznis);
                avion.uCSV(avion);
                avion = new Avion(1,true,new Adresa().izCSV(1),new Adresa().izCSV(5),KlasaAvionskogMesta.biznis);
                avion.uCSV(avion);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
