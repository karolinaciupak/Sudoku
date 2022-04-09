/**
 * Klasa z funkcją sprawdzającą czy liczba wpisana przez użytkownika jest prawidłowa
 */
public class Porownanie {
    /**
     * Kostruktor klasy porównanie
     */
    public Porownanie(){

    }

    /**
     *  sprawdza poprawność cyfr w danym wierszu
     * @param w - numer wiersza
     * @param k - numer kolumny
     * @param tablica - sprawdzana plansza
     * @return true or false
     */
    private boolean sprWiersza(int w, int k, int[][] tablica) {
        for (int i = 0; i < 9; i++) {
            if (tablica[w][i] == tablica[w][k] && i != k) return false;
        }
        return true;
    }
    /**
     * sprawdza poprawnosc cyfr w kolumnie
     * @param w - numer wiersza
     * @param k - numer kolumny
     * @param tablica - sprawdzana plansza
     * @return true or false
     */
    private boolean sprKolumny(int w, int k, int[][] tablica) {
        for (int i = 0; i < 9; i++) {
            if (tablica[i][k] == tablica[w][k] && i != w) return false;
        }
        return true;
    }
    /**
     * sprawdza poprawnosc cyfr w kwadracie - sekcji w której znajduje się dany wyraz
     * @param w - numer wiersza
     * @param k - numer kolumny
     * @param tablica - sprawdzana plansza
     * @return true or false
     */
    private boolean sprKwadratu(int w, int k, int[][] tablica) {
        int poczatekWiersza = (w / 3) * 3;
        int poczatekKolumny = (k / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablica[poczatekWiersza + i][poczatekKolumny + j] == tablica[w][k] && poczatekWiersza + i != w && poczatekKolumny + j != k)
                    return false;
            }
        }
        return true;
    }
    /**
     *  sprawdza czy dana komórka ma poprawną liczbę
     * @param w - numer wiersza
     * @param k - numer kolumny
     * @param tablica - sprawdzana plansza
     * @return true or false
     */
    public boolean czyPoprawny(int w, int k, int[][] tablica) {
        return (sprKolumny(w, k, tablica) && sprWiersza(w, k, tablica) && sprKwadratu(w, k, tablica));
    }
}