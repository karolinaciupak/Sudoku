import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import javax.swing.*;

/**
 * Klasa odpowiadająca za gui z wyborem czy chce się zaczać od nowa czy kontynuować
 */

public class newGame {
    JFrame ramka;
    JButton levelEasy;
    JButton levelMedium;
    JButton levelHard;
    JButton create;
    JLabel welcomeText;

    /**
     * Funkcja deklarująca przyciski dzięki którym wybiera się poziom gry lub kreowanie własnej planszy
     */


    public void zbudujGui() {
        ramka = new JFrame();
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.setSize(400, 400);
        levelEasy = new JButton();
        levelEasy.setText("Level Easy");
        levelMedium = new JButton();
        levelMedium.setText("Level Medium");
        levelHard = new JButton();
        levelHard.setText("Level Hard");
        create = new JButton();
        create.setText("Create your own level!");
/**
 * Dodawanie przycisków do panelu
 */


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 1));
        welcomeText = new JLabel("Wybierz poziom gry");
        panel.add(levelEasy);
        panel.add(levelMedium);
        panel.add(levelHard);
        panel.add(create);
        panel2.add(welcomeText);
        panel2.add(panel);
        ramka.add(panel2);
        ramka.setVisible(true);

/**
 * przypisanie funckji odpowiedzalnych za generowanie planszy do przycisków
 */

        levelEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Zapis test = new Zapis();
                    Sudoku sudoku = new Sudoku(1);
                    new Okno(sudoku.plansza,sudoku.kopia);
                    ramka.setVisible(false);
                    ramka.dispose();

                } catch (FileNotFoundException fne) {
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
        levelMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Zapis test = new Zapis();
                    Sudoku sudoku = new Sudoku(2);
                    new Okno(sudoku.plansza,sudoku.kopia);
                    ramka.setVisible(false);
                    ramka.dispose();
                } catch (FileNotFoundException fne) {
                }

            }
        });
        levelHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Zapis test = new Zapis();
                    Sudoku sudoku = new Sudoku(3);
                    new Okno(sudoku.plansza,sudoku.kopia);
                    ramka.setVisible(false);
                    ramka.dispose();
                } catch (FileNotFoundException fne) {
                }

            }
        });
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Zapis test = new Zapis();
                    Sudoku sudoku = new Sudoku(0);
                    new Okno(sudoku.plansza,sudoku.kopia);
                    ramka.setVisible(false);
                    ramka.dispose();
                } catch (FileNotFoundException fne) {
                }

            }
        });

        ramka.setLocationRelativeTo(null);
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
