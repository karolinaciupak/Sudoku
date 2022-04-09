import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JMenuBar;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Klasa odpowiedzialna za menu
 */

public class menuGui{
    JFrame ramka;
    JMenuBar menuBar;
    JButton menuContinue, menuNewGame, exit;
    JMenuItem levelEasy, levelMedium, levelHard, create;

    /**
     * Budowanie ramki menu przycisków odpowiedzialnych za wybór poziomu planszy
     */

    public void buildMenu(){
        ramka = new JFrame("Menu");
        ramka.setSize(400, 200);
        menuBar = new JMenuBar();
        menuContinue = new JButton("Kontynuuj");
        menuNewGame = new JButton("Nowa gra");
        exit = new JButton("wyjście");
        menuBar.add(menuContinue);
        menuBar.add(menuNewGame);
        menuBar.add(exit);

        levelEasy = new JMenuItem("Poziom łatwy");
        levelMedium = new JMenu("Poziom średni");
        levelHard = new JMenuItem("Poziom trudny");
        create = new JMenuItem("Stwórz własną planszę");

/**
 * dodanie przycisków do panelu menu
 */

        JPanel panel= new JPanel();
        panel.setLayout(new GridLayout(1,2));
        panel.add(menuContinue);
        panel.add(menuNewGame);
        ramka.add(panel);
        ramka.setVisible(true);
/**
 * przypisanie funckji odpowiedzalnych za generowanie planszy do przycisków o określonych poziomach
 */
        menuNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                newGame NewGame = new newGame();
                NewGame.zbudujGui();
                ramka.setVisible(false);
                ramka.dispose();
            }
        });
        menuContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Zapis odczyt = new Zapis();
                int[][] temp, temp2;
                try {
                    temp = odczyt.odczyt();
                    temp2 = temp;
                    Rozwiaz solve = new Rozwiaz(temp2);
                    new Okno(temp,solve.rozw());
                    ramka.setVisible(false);
                    ramka.dispose();
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(ramka, "Nie ma zapisanej gry, którą możnaby wczytać.");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        });

        ramka.setLocationRelativeTo(null);
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    public static void main(String[] args){
        menuGui appMenu = new menuGui();
        menuGui przyciski = new menuGui();
        przyciski.buildMenu();

    }




}