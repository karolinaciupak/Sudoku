import java.io.FileNotFoundException;


/**
 * Klasa Sudoku - klasa generująca plansze
 */
public class Sudoku {
    public int[][] plansza = new int[9][9]; // plansza sudoku, na której będzie grać użytkownik
    public int[][] kopia = new int[9][9]; // poprawnie wypełniona plansza
    public int poziomTrudnosci; // parametr określający trudność gry (1-3), gdy jest równy 0, to generuje się pusta plansza ( z samymi zerami)

    /**
     * konstruktor klasy sudoku
     */
    public Sudoku() {
    }


    /**
     * konstruktor klasy sudoku generujący nową planszę o określonym poziomie trudności (1-3)
     * @param poziomTrudnosci - poziom trudności (0-3)
     * @throws FileNotFoundException
     */
    public Sudoku(int poziomTrudnosci) throws FileNotFoundException {
        this.poziomTrudnosci = poziomTrudnosci;
        if (this.poziomTrudnosci == 0) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    plansza[i][j] = 0;
                }
            }
        } else {
            generuj();
        }
    }
// SPRAWDZENIE POPRAWNOŚCI WPISYWANYCH LICZB

    /**
     *  sprawdza poprawność cyfr w danym wierszu
     * @param w - numer wiersza
     * @param k - numer kolumny
     * @return true or false
     */
    private boolean sprWiersza(int w, int k) {
        for (int i = 0; i < 9; i++) {
            if (plansza[w][i] == plansza[w][k] && i != k) return false;
        }
        return true;
    }

    /**
     * sprawdza poprawnosc cyfr w kolumnie
     * @param w - numer wiersza
     * @param k - numer kolumny
     * @return true or false
     */
    private boolean sprKolumny(int w, int k) {
        for (int i = 0; i < 9; i++) {
            if (plansza[i][k] == plansza[w][k] && i != w) return false;
        }
        return true;
    }
    /**
     * sprawdza poprawnosc cyfr w kwadracie - sekcji w której znajduje się dany wyraz
     * @param w - numer wiersza
     * @param k - numer kolumny
     * @return true or false
     */

    private boolean sprKwadratu(int w, int k) {
        int poczatekWiersza = (w / 3) * 3;
        int poczatekKolumny = (k / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (plansza[poczatekWiersza + i][poczatekKolumny + j] == plansza[w][k] && poczatekWiersza + i != w && poczatekKolumny + j != k)
                    return false;
            }
        }
        return true;
    }

    /**
     *  sprawdza czy dana komórka ma poprawną liczbę
     * @param w - numer wiersza
     * @param k - numer kolumny
     * @return true or false
     */
    private boolean czyPoprawny(int w, int k) {
        return (sprKolumny(w, k) && sprWiersza(w, k) && sprKwadratu(w, k));
    }



//GENEROWANIE PLANSZY

    /**
     * sprawdza czy istnieje rozwiazanie (podmieniając wartości w tablicy plansza)
     *
     * @return true or false
     */
    boolean rozwiaz(int kierunek) {
        for (int wiersz = 0; wiersz < 9; wiersz++) {
            for (int kolumna = 0; kolumna < 9; kolumna++) {
                if (this.plansza[wiersz][kolumna] == 0) {
                    if (kierunek == 0) {
                        for (int i = 1; i < 10; i++) {
                            this.plansza[wiersz][kolumna] = i;
                            if (czyPoprawny(wiersz, kolumna) && this.rozwiaz(0))
                                return true;
                            this.plansza[wiersz][kolumna] = 0;
                        }
                    } else {
                        for (int i = 9; i > 0; i--) {
                            this.plansza[wiersz][kolumna] = i;
                            if (czyPoprawny(wiersz, kolumna) && this.rozwiaz(0))
                                return true;
                            this.plansza[wiersz][kolumna] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * generuje plansze sudoku i rozwiązaną kopię
     */
    public void generuj() {
        int doUsuniecia, w , k ;
        switch (poziomTrudnosci) {
            case 1 -> doUsuniecia = 40;
            case 2 -> doUsuniecia = 50;
            case 3 -> doUsuniecia = 60;
            default -> {
                System.out.println("Niepoprawny poziom trudnosci!");
                return;
            }
        }

        for (int i = 0; i < 8; ) {
            w = (int) (Math.random() * 10) % 9;
            k = (int) (Math.random() * 10) % 9;
            if (plansza[w][k] == 0) {
                plansza[w][k] = ((int) (Math.random() * 10) % 9) + 1;
                if (czyPoprawny(w, k)) i++;
                else plansza[w][k] = 0;
            }
        }



        if (!this.rozwiaz(0)) {
            this.plansza = new int[9][9];
            this.generuj();
        } else {
            int[][] por = new int[9][9];
            Sudoku temp = new Sudoku(), temp2 = new Sudoku(), temp3 = new Sudoku();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    por[i][j] = temp.plansza[i][j] = temp2.plansza[i][j] = this.plansza[i][j];
                }
            }

            for(int i=0; i<9; i++){
                System.arraycopy(plansza[i], 0, this.kopia[i], 0, 9);
            }

            int wartosc;
            for (int a = 0; a < doUsuniecia; ) {
                w = (int) (Math.random() * 10) % 9;
                k = (int) (Math.random() * 10) % 9;
                if (temp.plansza[w][k] != 0) {
                    wartosc = temp.plansza[w][k];
                    temp2.plansza[k][w] = 0;
                    temp.plansza[w][k] = 0;
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            this.plansza[i][j] = temp.plansza[i][j];
                            temp3.plansza[i][j] = temp2.plansza[i][j];
                        }
                    }
                    this.rozwiaz(1);
                    temp3.rozwiaz(0);
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (por[i][j] != plansza[i][j] && por[i][j] != temp3.plansza[j][i]) {
                                i = 10;
                                j = 10;
                                temp.plansza[w][k] = wartosc;
                                temp3.plansza[k][w] = wartosc;
                            }
                        }
                    }
                    a++;
                }
            }
            for (int i = 0; i < 9; i++) {
                System.arraycopy(temp.plansza[i], 0, this.plansza[i], 0, 9);
            }
        }
    }


}

