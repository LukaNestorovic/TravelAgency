package aranzman;


import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;
import prevoznoSredstvo.Avion;
import prevoznoSredstvo.PrevoznoSredstvo;
import prevoznoSredstvo.Voz;
import smestaj.Apartman;
import smestaj.Hotel;
import smestaj.Smestaj;


import java.io.*;
import java.time.LocalDate;

import java.util.ArrayList;

public class Aranzman implements Serijalizacija<Aranzman, Integer>, Kreiranje {
    private Integer id;
    private Boolean osiguran;
    private Boolean grupni;
    private LocalDate datumPolaska;
    private LocalDate datumPovratka;
    private Smestaj smestaj;
    private PrevoznoSredstvo prevoznoSredstvo;

    public Aranzman() {
    }

    public Aranzman(Integer id, Boolean osiguran, Boolean grupni, LocalDate datumPolaska, LocalDate datumPovratka, Smestaj smestaj, PrevoznoSredstvo prevoznoSredstvo) {
        this.id = id;
        this.osiguran = osiguran;
        this.grupni = grupni;
        this.datumPolaska = datumPolaska;
        this.datumPovratka = datumPovratka;
        this.smestaj = smestaj;
        this.prevoznoSredstvo = prevoznoSredstvo;
    }

    public Boolean getOsiguran() {
        return osiguran;
    }

    public void setOsiguran(Boolean osiguran) {
        this.osiguran = osiguran;
    }

    public Boolean getGrupni() {
        return grupni;
    }

    public void setGrupni(Boolean grupni) {
        this.grupni = grupni;
    }

    public LocalDate getDatumPolaska() {
        return datumPolaska;
    }

    public void setDatumPolaska(LocalDate datumPolaska) {
        this.datumPolaska = datumPolaska;
    }

    public LocalDate getDatumPovratka() {
        return datumPovratka;
    }

    public void setDatumPovratka(LocalDate datumPovratka) {
        this.datumPovratka = datumPovratka;
    }

    public Smestaj getSmestaj() {
        return smestaj;
    }

    public void setSmestaj(Smestaj smestaj) {
        this.smestaj = smestaj;
    }

    public PrevoznoSredstvo getPrevoznoSredstvo() {
        return prevoznoSredstvo;
    }

    public void setPrevoznoSredstvo(PrevoznoSredstvo prevoznoSredstvo) {
        this.prevoznoSredstvo = prevoznoSredstvo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void uCSV(Aranzman aranzman){
        ArrayList<String> podaci = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/aranzmani.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                podaci.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer noviRed = new StringBuffer();
        noviRed.append(aranzman.getId());
        noviRed.append(",");
        noviRed.append(aranzman.getOsiguran());
        noviRed.append(",");
        noviRed.append(aranzman.getGrupni());
        noviRed.append(",");
        noviRed.append(aranzman.getDatumPolaska());
        noviRed.append(",");
        noviRed.append(aranzman.getDatumPovratka());
        noviRed.append(",");
        noviRed.append(aranzman.getSmestaj().getAdresa().getId());
        noviRed.append(",");
        noviRed.append(aranzman.getPrevoznoSredstvo().getId());
        podaci.add(String.valueOf(noviRed));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/aranzmani.csv"))) {
            for (String red : podaci) {
                bw.write(red);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Aranzman izCSV(Integer id) {
        String csvFile = "data/aranzmani.csv";
        String red;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(Integer.parseInt(podaci[0]) == id) {
                    Aranzman aranzman = new Aranzman();
                    aranzman.setId(Integer.parseInt(podaci[0]));
                    aranzman.setOsiguran(Boolean.valueOf(podaci[1]));
                    aranzman.setGrupni(Boolean.valueOf(podaci[2]));
                    aranzman.setDatumPolaska(LocalDate.parse(podaci[3]));
                    aranzman.setDatumPovratka(LocalDate.parse(podaci[4]));
                    aranzman.setSmestaj(new Apartman().izCSV(Integer.parseInt(podaci[5])));
                    if(aranzman.getSmestaj() == null){
                        aranzman.setSmestaj(new Hotel().izCSV(Integer.parseInt(podaci[5])));
                    }
                    aranzman.setPrevoznoSredstvo(new Voz().izCSV(Integer.parseInt(podaci[6])));
                    if(aranzman.getPrevoznoSredstvo() == null){
                        aranzman.setPrevoznoSredstvo(new Avion().izCSV(Integer.parseInt(podaci[6])));
                    }
                    return aranzman;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void brisanje(Integer id){
        ArrayList<String> redovi = new ArrayList<>();
        ArrayList<String> izmenjeni = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/aranzmani.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                redovi.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line: redovi){
            String podaci[] = line.split(",");
            if(Integer.parseInt(podaci[0]) == id){
                podaci = null;
            }
            if(podaci != null) {
                StringBuffer noviRed = new StringBuffer();
                noviRed.append(podaci[0]);
                noviRed.append(",");
                noviRed.append(podaci[1]);
                noviRed.append(",");
                noviRed.append(podaci[2]);
                noviRed.append(",");
                noviRed.append(podaci[3]);
                noviRed.append(",");
                noviRed.append(podaci[4]);
                noviRed.append(",");
                noviRed.append(podaci[5]);
                noviRed.append(",");
                noviRed.append(podaci[6]);
                izmenjeni.add(String.valueOf(noviRed));
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/aranzmani.csv"))) {
            for (String line : izmenjeni) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void kreirajFajl() {
        String nazivFajla = "data/aranzmani.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                Aranzman aranzman = new Aranzman(2,true,false,LocalDate.of(2023,9,7),LocalDate.of(2023,9,9),new Apartman().izCSV(3),new Avion().izCSV(65));
                aranzman.uCSV(aranzman);
                aranzman = new Aranzman(5,true,false,LocalDate.of(2023,10,12),LocalDate.of(2023,10,19),new Apartman().izCSV(5),new Voz().izCSV(1));
                aranzman.uCSV(aranzman);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
