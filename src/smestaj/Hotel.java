package smestaj;

import adresa.Adresa;
import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;

import java.io.*;
import java.util.ArrayList;

public class Hotel extends Smestaj implements Serijalizacija<Hotel, Integer>, Kreiranje {
    private Integer brojZvezdica;

    public Hotel() {
    }

    public Hotel(double povrsina, int brojKreveta, TipPansiona tipPansiona, Adresa adresa, Integer brojZvezdica) {
        super(povrsina, brojKreveta, tipPansiona, adresa);
        this.brojZvezdica = brojZvezdica;
    }

    public Integer getBrojZvezdica() {
        return brojZvezdica;
    }

    public void setBrojZvezdica(Integer brojZvezdica) {
        this.brojZvezdica = brojZvezdica;
    }

    @Override
    public void uCSV(Hotel hotel){
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/hoteli.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            StringBuffer red = new StringBuffer();
            red.append(hotel.getPovrsina());
            red.append(",");
            red.append(hotel.getBrojKreveta());
            red.append(",");
            red.append(hotel.getTipPansiona());
            red.append(",");
            red.append(hotel.getAdresa().getId());
            red.append(",");
            red.append(hotel.getBrojZvezdica());
        lines.add(String.valueOf(red));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/hoteli.csv"))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Hotel izCSV(Integer adresa) {
        String csvFile = "data/hoteli.csv";
        String red;
        String splitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] data = red.split(splitBy);
                if(Integer.parseInt(data[3]) == adresa) {
                    Hotel hotel = new Hotel();
                    hotel.setPovrsina(Double.parseDouble(data[0]));
                    hotel.setBrojKreveta(Integer.parseInt(data[1]));
                    hotel.setTipPansiona(TipPansiona.valueOf(data[2]));
                    hotel.setAdresa(new Adresa().izCSV(Integer.parseInt(data[3])));
                    hotel.setBrojZvezdica(Integer.parseInt(data[4]));
                    return hotel;
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
            return 1500 * dani + 132 * this.brojZvezdica;
        } else if (this.getTipPansiona().toString().equals("nocenjeSaDoruckom")) {
            return 1900 * dani + 132 * this.brojZvezdica;
        } else if (this.getTipPansiona().toString().equals("polupansion")) {
            return 2300 * dani + 132 * this.brojZvezdica;
        } else if (this.getTipPansiona().toString().equals("punPansion")) {
            return 3000 * dani + 132 * this.brojZvezdica;
        } else if (this.getTipPansiona().toString().equals("allInclusive")) {
            return 4000 * dani + 132 * this.brojZvezdica;
        } else {
            return null;
        }
    }

    @Override
    public void otkazi() {
        if(this.getTipPansiona().toString().equals("nocenje")) {
            System.out.println(1500 + 132 * this.brojZvezdica);
        } else if (this.getTipPansiona().toString().equals("nocenjeSaDoruckom")) {
            System.out.println(1900 + 132 * this.brojZvezdica);
        } else if (this.getTipPansiona().toString().equals("polupansion")) {
            System.out.println(2300 + 132 * this.brojZvezdica);
        } else if (this.getTipPansiona().toString().equals("punPansion")) {
            System.out.println(3000 + 132 * this.brojZvezdica);
        } else if (this.getTipPansiona().toString().equals("allInclusive")) {
            System.out.println(4000 + 132 * this.brojZvezdica);
        }
    }

    @Override
    public void kreirajFajl() {
        String nazivFajla = "data/hoteli.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                Hotel hotel = new Hotel(10.0,2,TipPansiona.allInclusive,new Adresa().izCSV(1),5);
                hotel.uCSV(hotel);
                hotel = new Hotel(30.0,2,TipPansiona.nocenje,new Adresa().izCSV(2),2);
                hotel.uCSV(hotel);
                hotel = new Hotel(40.0,1,TipPansiona.punPansion,new Adresa().izCSV(8),4);
                hotel.uCSV(hotel);
                hotel = new Hotel(30.0,1,TipPansiona.punPansion,new Adresa().izCSV(9),4);
                hotel.uCSV(hotel);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
