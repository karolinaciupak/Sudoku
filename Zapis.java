import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Klasa odpowiadająca za zapis i odczyt planszy
 */
public class Zapis {

    private String plik_wydruk;
    private String plik_odczyt;

    /**
     * Funkcja zapisująca plansze do pliku
     * @param plansza - plansza do zapisania
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void zapisz(int[][] plansza) throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream("zapis.txt");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        OutputStreamWriter osw = new OutputStreamWriter(bos);
        for (int i = 0; i<plansza.length; i++) {
            for (int j = 0; j<plansza[i].length; j++) {
                int cell = plansza[i][j];
                String cellString = String.valueOf(cell);
                osw.write(cellString);
                if (j != plansza[i].length - 1) {
                    osw.write(" ");
                }
            }
            if (i != plansza.length - 1) {
                osw.write("\n");
            } else {}

        }
        osw.close();
    }

    /**
     * Funkcja odpowiadjąca za odczyt planszy
     * @return plansza - odczytana plansza
     * @throws FileNotFoundException
     * @throws IOException
     */
    public int[][] odczyt() throws FileNotFoundException, IOException{
        int[][] plansza = new int[9][9];

        FileInputStream fis = new FileInputStream("zapis.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);
        InputStreamReader isr = new InputStreamReader(bis);
        LineNumberReader lnr = new LineNumberReader(isr);
        String line = null;
        String[] temp = new String[9];
        int i = 0;
        while ((line= lnr.readLine()) != null) {
            temp = line.split(" ");
            for (int j = 0; j<9; j++) {
                if(i == 9) break;
                plansza[i][j] = Integer.parseInt(temp[j]);
            }
            i++;
        }
        return plansza;
    }


    /**
     * kod testujący działanie dla przykładowej, losowo wypełnionej planszy
     */
//    public static void main(String args[]) {
//
//        int[][] plansza = new int[9][9];
//        for (int i = 0; i<plansza.length; i++) {
//            for (int j = 0; j<plansza[i].length; j++) {
//                int randomNum = ThreadLocalRandom.current().nextInt(1, 9 + 1);
//                plansza[i][j] = randomNum;
//            }
//        }
//
//        Zapis test = new Zapis();
//
//        try {
//            test.zapisz(plansza);
//        } catch (IOException ioe) {
//            System.out.println("Coś poszło nie tak...");
//        }
//
//        try {
//            test.odczyt();
//        } catch (IOException ioe) {
//            System.out.println("Coś poszło nie tak...");
//        }
//
//    }
}
