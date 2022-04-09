/**
 * Klasa zawierająca funkcję, która generuje wskazówkę
 */
public class Wskazowka {
    /**
     * Konstuktor klasy wskazówka
     */
    public Wskazowka(){

    }

    /**
     * funkcja generująca wskazówkę - poprzez znalezienie pustego miejsca (z 0) i wstawienie tam liczby z wypełnionej planszy
     * @param tablica - rozwiązywana plansza
     * @param tablica2 - wypełniona plansza
     * @return plansza uzupełniona o wskazówkę
     */
    public int[][] pdpp(int[][] tablica, int[][] tablica2){
        int  w = (int) (Math.random() * 10) % 9;
        int  k = (int) (Math.random() * 10) % 9;
        if(tablica[w][k] != 0){
            pdpp(tablica,tablica2);
        }
        tablica[w][k] = tablica2[w][k];
        return tablica;
    }
}