import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Klasa odpowiadająca gui z planszą sudoku
 */
public class Okno {
    JButton[][] a = new JButton[9][9];

    /**
     * Całe działanie zawiera się w konstruktorze, tutaj tworzona jest wizualna plansza (pola sudoku), przyciski
     * odpowiedzialne za takie funkcje jak podanie Wskazówki, czy Pokazanie rozwiązania.
     * @param plansza - rozwiązywana plansza
     * @param kopia - wygenerowana rozwiązana plansza
     */
    public Okno(int[][] plansza, int[][] kopia) {
        JFrame f = new JFrame("Sudoku");
        int[][] pole_generowane = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                pole_generowane[i][j] = 0;
            }
        }

        /**
         *  Tutaj tworzone są pola planszy, które są przyciskami, dodatkowo dodany zostaje charakterystyczny dla
         *  sudoku podział na mniejsze kwadraty 3x3
         */
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j<9; j++) {
                a[i][j] = new JButton();
                a[i][j].setBackground(new Color(255, 255, 255, 255));
                if(i%3 == 0 && i != 0) {
                    if (j%3 == 0 && j != 0) {
                        a[i][j].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.BLACK));
                    } else {
                        a[i][j].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.BLACK));
                    }
                } else {
                    if(j%3 == 0 && j != 0) {
                        a[i][j].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.BLACK));
                    } else {
                        a[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                    }
                }
                a[i][j].setFont(new Font("Arial", Font.BOLD, 22));
                a[i][j].setBounds(100 + i * 49, 50 + j * 49, 50, 50);
                f.add(a[i][j]);
                int finalI = i;
                int finalJ = j;

                /**
                 * Dla każdego przycisku generowana jest obsługa jego wciśnięcia.
                 * Dzięki temu nie można np. zmieniać wygenerowanych pól, pola zmieniają swoje kolory, aby plansza
                 * była bardziej czytelna
                 */
                a[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int k = 0; k < 9; k++) {
                            for (int w = 0; w < 9; w++) {
                                if (a[w][k].getBackground().equals(new Color(190,190,190))) {
                                    a[w][k].setBackground(new Color(255, 255, 255, 255));
                                }
                            }
                        }
                        if (pole_generowane[finalI][finalJ] == 1) {
                            JOptionPane.showMessageDialog(f, "Nie można edytować pól wygenerowanych");
                        } else {
                            a[finalI][finalJ].setBackground(new Color(190, 190, 190, 255));

                            /**
                             * Po wciśnięci przycisku generowana jest obsługa wciśnięcia klawiszy na klawiaturze;
                             * Akceptowane są jedynie liczby od 1 do 9, dla innych klawiszy tworzona jest wiadomość
                             * informująca użytkownika, że wcisnął niepoprawny klawisz.
                             * Wybrana liczba jest sprawdzana pod kątem podstawowych zasad sudoku. Jeśli w wybrane
                             * pole można wpisać daną liczbę jest ona tam umieszczana, jeśli nie - użytkownik jest
                             * o tym informowany.
                             */
                            final int[] licznik = {0};
                            a[finalI][finalJ].addKeyListener(new KeyListener() {
                                @Override
                                public void keyTyped(KeyEvent e) {

                                }

                                @Override
                                public void keyPressed(KeyEvent e) {

                                }

                                @Override
                                public void keyReleased(KeyEvent e) {
                                    String key = null;
                                    try {
                                        key = String.valueOf(e.getKeyChar());
                                        int liczba = Integer.valueOf(key);
                                        if (liczba != 0) {
                                            plansza[finalJ][finalI] = liczba;
                                            Porownanie porownanie = new Porownanie();
                                            if (licznik[0] == 0) {
                                                if (porownanie.czyPoprawny(finalJ, finalI, plansza)) {
                                                    a[finalI][finalJ].setText(String.valueOf(liczba));
                                                    a[finalI][finalJ].setBackground(new Color(255, 255, 255, 255));
                                                } else {
                                                    JOptionPane.showMessageDialog(f, "Ta liczba jest już w tym wierszu, kolumnie lub kwadracie");
                                                    plansza[finalJ][finalI] = 0;
                                                    a[finalI][finalJ].setBackground(new Color(255, 255, 255, 255));
                                                }
                                                licznik[0]++;
                                            }


                                        } else {
                                            if (licznik[0] == 0) {
                                                JOptionPane.showMessageDialog(f, "Wprowadź liczbę z zakresu 1-9");
                                                licznik[0]++;
                                            }
                                        }

                                        key = null;

                                    } catch (RuntimeException re) {
                                        if (licznik[0] == 0) {
                                            JOptionPane.showMessageDialog(f, "Wprowadź liczbę z zakresu 1-9");
                                            a[finalI][finalJ].setBackground(new Color(255, 255, 255, 255));
                                            licznik[0]++;
                                        }
                                    }

                                    /**
                                     * Po każdym wypełnieniu pola, sprawdzane jest, czy cała plansza została już
                                     * wypełniona (tj. czy gra została ukończona).
                                     */
                                    int koniec = 0;
                                    for (int i = 0; i < 9; i++) {
                                        for (int j = 0; j < 9; j++) {
                                            try {
                                                if (Integer.valueOf(a[i][j].getText()) != kopia[j][i]) {
                                                    break;
                                                } else {
                                                    koniec++;
                                                }
                                            } catch (NumberFormatException nfe) {

                                            }
                                        }
                                    }
                                    if (koniec == 81) {
                                        JOptionPane.showMessageDialog(f, "Koniec gry");
                                    }

                                }
                            });
                        }
                    }
                });
            }
        }

        Border border = new LineBorder(Color.BLACK, 1);

        /**
         * Tworzenie klawisza odpowiedzialnego za pokazanie rozwiązania danej planszy
         */
        JButton rozwiaz = new JButton();
        rozwiaz.setText("Pokaż rozwiązanie");
        rozwiaz.setBounds(100,520,120,30);
        rozwiaz.setBackground(new Color(255, 255, 255, 255));
        rozwiaz.setBorder(border);
        rozwiaz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] temp = new int[9][9];
                int licznik = 0;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
//                        a[i][j].setText(String.valueOf(kopia[j][i]));
                        temp[i][j] = kopia[j][i];
                        if(kopia[j][i] == 0) {
//                            a[i][j].setText("");
                            licznik++;
                        }
                    }
                }

                if (licznik == 81) {
                    JOptionPane.showMessageDialog(f, "Rozwiązanie aktualnego stanu planszy jest niemożliwe.");
                } else {
                    for (int l = 0; l < 9; l++) {
                        for (int o = 0; o < 9; o++) {
                            a[l][o].setText(String.valueOf(temp[o][l]));
                        }
                    }

//                    int koniec = 0;
//                    for (int i = 0; i < 9; i++) {
//                        for (int j = 0; j < 9; j++) {
//                            if (Integer.valueOf(a[i][j].getText()) != kopia[j][i]) {
//                                break;
//                            } else {
//                                koniec++;
//                            }
//
//                        }
//                    }
//                    if (koniec == 81) {
                        JOptionPane.showMessageDialog(f, "Koniec gry");
//                    }
                }
            }
        });
        f.add(rozwiaz);

        /**
         * Tworzenie przycisku odpowiedzialnego za udzielenie wskazówki
         */
        JButton wskazowka = new JButton();
        wskazowka.setText("Wskazówka");
        wskazowka.setBounds(230,520,90,30);
        wskazowka.setBackground(new Color(255, 255, 255, 255));
        wskazowka.setBorder(border);
        wskazowka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Wskazowka wskazowka1 = new Wskazowka();
                int[][] temp = new int[9][9];
                int[][] temp1 = new int[9][9];
                final int[][] temp2 = new int[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (a[i][j].getText() == "") {
                            temp1[j][i] = 0;
                            temp2[j][i] = 0;
                        } else {
                            temp1[j][i] = Integer.valueOf(a[i][j].getText());
                            temp2[j][i] = Integer.valueOf(a[i][j].getText());
                        }
                    }
                }

                int licznik = 0;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if(kopia[i][j] == 0) {
                            licznik++;
                        }
                    }
                }

                if (licznik != 81) {
                    temp = wskazowka1.pdpp(temp1, kopia);
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            a[i][j].setText(String.valueOf(temp[j][i]));
                            if (temp[j][i] == 0) {
                                a[i][j].setText("");
                            } else {
                                if (temp2[j][i] == 0) {
                                    a[i][j].setBackground(new Color(230, 230, 230, 255));
                                    pole_generowane[i][j] = 1;
                                }
                            }
                        }
                    }
                    int koniec = 0;
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (plansza[i][j] != kopia[i][j]) {
                                break;
                            } else {
                                koniec++;
                            }

                        }
                    }
                    if (koniec == 81) {
                        JOptionPane.showMessageDialog(f, "Koniec gry");
                    }
                } else {
                    JOptionPane.showMessageDialog(f, "Wyświetlenie wskazówki dla aktualnego stanu planszy jest niemożliwe.");
                }
            }
        });
        f.add(wskazowka);

        /**
         * Tworzenie przycisku odpowiedzialnego za zapis aktualnego stanu gry
         */
        JButton zapisz = new JButton();
        zapisz.setText("Zapisz");
        zapisz.setBounds(411,520,70,30);
        zapisz.setBackground(new Color(255, 255, 255, 255));
        zapisz.setBorder(border);
        zapisz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] plansza_temp = new int[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if(a[j][i].getText() == "") {
                            plansza_temp[i][j] = 0;
                        } else {
                            plansza_temp[i][j] = Integer.valueOf(a[j][i].getText());
                        }
                        Zapis temp = new Zapis();
                        try {
                            temp.zapisz(plansza_temp);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
        });
        f.add(zapisz);


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                a[i][j].setText(String.valueOf(plansza[j][i]));
                if(plansza[j][i] == 0) {
                    a[i][j].setText("");
                } else {
                    a[i][j].setBackground(new Color(230, 230, 230, 255));
                    pole_generowane[i][j] = 1;
                }
            }
        }

        /**
         * Tworzenie przycisku odpowiedzialnego za powrót do menu głównego
         */
        JButton menu = new JButton();
        menu.setText("Menu");
        menu.setBounds(491,520,50,30);
        menu.setBackground(new Color(255, 255, 255, 255));
        menu.setBorder(border);
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGui menu_gui = new menuGui();
                menu_gui.buildMenu();
                f.setVisible(false);
                f.dispose();
            }
        });
        f.add(menu);


        f.setSize(650, 650);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }


}
