package ssau.test;

import ssau.lab.Game;
import ssau.lab.Genre;
import ssau.lab.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ssau.view.*;

public class Test {

    public static void main(String[] args) {

        Model model1 = new Model();

        List<String> games = new ArrayList<String>();
        List<String> genres = new ArrayList<String>();

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });

   /*
        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            Game game = model1.createGame(Integer.toString(rand.nextInt(100)), "2015");
            games.add(Integer.toString(game.getGameId()));
        }

        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            Genre genre = model1.createGenre(Integer.toString(rand.nextInt(100)));
            genres.add(Integer.toString(genre.getGenreId()));
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("[" + i + "] " + games.get(i) + "  " + genres.get(i));
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < i + 1; j++) {
                model1.setGameGenre(games.get(i), genres.get(j));
            }
        }

        model1.removeGenreForGame(games.get(7), genres.get(2));

        
        System.out.println("Game-Genre");
        for (int i = 0; i < 8; i++) {
            List temp = model1.getGameGenre(games.get(i));
            System.out.println("[" + i + "] " + games.get(i));
            for (Object temp1 : temp) {
                System.out.print(temp1 + "  ");
            }
            System.out.println("");
        }

        System.out.println("Genre-Game");
        for (int i = 0; i < 8; i++) {
            List temp = model1.getGenreGame(genres.get(i));
            System.out.println("[" + i + "] " + genres.get(i));
            for (Object temp1 : temp) {
                System.out.print(temp1 + "  ");
            }
            System.out.println("");
        }

    */
    }

}
