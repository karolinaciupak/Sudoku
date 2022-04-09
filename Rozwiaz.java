/**
 * Klasa rozwiązująca sudoku - potrzebna gdy użytkownik sam wpisuje swoją plansze i gdy jest ona wczytywana z pliku
 */

public class Rozwiaz {
    private int[][] plansza; //plansza do rozwiązania

    /**
     * konstruktor klasy Rozwiaz odpowiadający za wczytanie planszy do rozwiązania
     * @param plansza - plansza, która zostanie rozwiązana
     */
    public Rozwiaz(int[][] plansza) {
        this.plansza = new int[9][9];

        for (int i = 0; i < 9; i++) {
            System.arraycopy(plansza[i], 0, this.plansza[i], 0, 9);
        }
    }

    /**
     * Sprawdzenie czy liczba znajduje się w danym wierszu
     * @param w - numer wiersza
     * @param liczba - szukana liczba
     * @return true or false
     */
    private boolean isInRow(int w, int liczba) {
        for (int i = 0; i < 9; i++)
            if (plansza[w][i] == liczba)
                return true;

        return false;
    }

    /**
     * Sprawdzenie czy liczba znajduje się w danej kolumnie
     * @param k - numer kolumny
     * @param liczba - szukana liczba
     * @return true or false
     */
    private boolean isInCol(int k, int liczba) {
        for (int i = 0; i < 9; i++)
            if (plansza[i][k] == liczba)
                return true;

        return false;
    }

    /**
     * Sprawdzenie czy liczba znajduje się w kwadracie, który zawiera daną komórkę
     * @param w -  numer wiersza
     * @param k - numer kolumny
     * @param liczba - szukana liczba
     * @return true or false
     */
    private boolean isInBox(int w, int k, int liczba) {
        int r = w - w % 3;
        int c = k - k % 3;

        for (int i = r; i < r + 3; i++)
            for (int j = c; j < c + 3; j++)
                if (plansza[i][j] == liczba)
                    return true;

        return false;
    }

    /**
     * Sprawdzenie czy dana liczba może być wpisana w daną komórkę
     * @param w - numer wiersza
     * @param k - numer kolumny
     * @param liczba - sprawdzana liczba
     * @return
     */
    private boolean isOk(int w, int k, int liczba) {
        return !isInRow(w, liczba) && !isInCol(k, liczba) && !isInBox(w, k, liczba);
    }

    /**
     * funkcja sprawdzająca czy da się rozwiązać planszę i rozwiązująca ją, jeśli to możliwe
     * @return true or false
     */
    public boolean solve() {
        for (int w = 0; w < 9; w++) {
            for (int k = 0; k < 9; k++) {
                if (plansza[w][k] == 0) {
                    for (int liczba = 1; liczba <= 9; liczba++) {
                        if (isOk(w, k, liczba)) {
                            plansza[w][k] = liczba;

                            if (solve()) {
                                return true;
                            } else {
                                plansza[w][k] = 0;
                            }
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }

    /**
     *  Funkcja zwracająca rozwiązaną plansze
     * @return plansza_zwrot - rozwiązana plansza
     */
    int [][] rozw() {
        int[][] plansza_zwrot = new int[9][9];
        solve();
        for (int i = 0; i < 9; i++) {
            System.arraycopy(plansza[i], 0, plansza_zwrot[i], 0, 9);
        }
        return plansza_zwrot;
    }
}