package ssau.view.updateDialog;

import ssau.client.Client;
import ssau.lab.Game;
import ssau.lab.Genre;
import ssau.view.UIHelper;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class UpdateGameDialog extends AbstractDialog  {

    private Client client;
    private Game oldGame;
    private JTextField gameNameTextBox;
    private JTextField gameCompanyTextBox;
    private Map<String, String> genres; // КЛЮЧ ЭТО ИМЯ А ЗНАЧЕНИЕ ЭТО АЙДИШНИК
    private JPopupMenu genresComboBox;
    private JButton okButton;

    public UpdateGameDialog(Game oldGame, Map<String, String> genres, Client client) {
        this.client = client;
        this.oldGame = oldGame;
        this.genres = genres;
        initializeControls();
        initializeView();
    }

    private void initializeControls() {
        gameNameTextBox = new JTextField();
        gameNameTextBox.setSize(30, 60);
        gameCompanyTextBox = new JTextField();
        gameCompanyTextBox.setSize(30, 60);
        genresComboBox = new JPopupMenu ();
        for (String name : genres.keySet()){
            genresComboBox.add(new JCheckBoxMenuItem(name));
        }
        genresComboBox.setVisible(true);
        genresComboBox.setSize(20, 60);
        okButton = new JButton(new AbstractAction("ОК") {
            public void actionPerformed(ActionEvent e) {
                okButtonActionPerformed(e);
            }
        });
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        ArrayList<Genre> newGenres = new ArrayList<Genre>();
        for (MenuElement elem : genresComboBox.getSubElements()) {
            if(((JCheckBoxMenuItem)elem).isSelected()){
                newGenres.add(client.getModel().getGenreById(genres.get(((JCheckBoxMenuItem) elem).getText())));
            }
        }
        try {
            client.updateGame(oldGame.getGameId(), gameNameTextBox.getText(), gameCompanyTextBox.getText(), newGenres);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, " ошибка обновления ", "ошибка обновления", JOptionPane.ERROR_MESSAGE);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        this.dispose();//закрытие диалогового окна
    }


    private void initializeView() {
        setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(new Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2));
        setPreferredSize(new Dimension(400, 400));
        setTitle("Редактирование игры");
        getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        setLayout(new BorderLayout());
        add(UIHelper.vBox(
                "Новое название Игры",
                gameNameTextBox,
                20,
                "Новая компания разработчик",
                gameCompanyTextBox,
                20,
                "Жанры игры",
                genresComboBox,
                20,
                UIHelper.hBox(new JLabel(""), 3, okButton)
        ), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

}
