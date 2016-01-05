package ssau.view.updateDialog;
import ssau.client.Client;
import ssau.lab.Genre;
import ssau.view.UIHelper;

import javax.swing.*;

/**
 * Created by kolesnikov on 22.12.15.
 */

import java.awt.*;
import java.awt.event.ActionEvent;

public class UpdateGenreDialog extends AbstractDialog {


    private Client client;
    private JTextField genreTextBox;
    private Genre oldGenre;
    private JButton okButton;

    public UpdateGenreDialog(Genre genre, Client client) {
        this.client = client;
        this.oldGenre = genre;
        initializeControls();
        initializeView();
    }

    private void initializeControls() {
        genreTextBox = new JTextField(oldGenre.getGenreName());
        genreTextBox.setSize(30, 60);
        okButton = new JButton(new AbstractAction("ОК") {
            public void actionPerformed(ActionEvent e) {
                okButtonActionPerformed(e);
            }
        });
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        try {
            client.updateGenre(oldGenre.getGenreId(), genreTextBox.getText());
        } catch (Exception er) {
            JOptionPane.showMessageDialog(null, " ошибка обновления ", "ошибка обновления", JOptionPane.ERROR_MESSAGE);
        }
        this.dispose();//закрытие диалогового окна
    }


    private void initializeView() {
        setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(new Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2));
        setPreferredSize(new Dimension(540, 150));
        setTitle("Редактирование жанра");
        getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        setLayout(new BorderLayout());
        add(UIHelper.vBox(
                "Новое название жанра",
                genreTextBox,
                30,
                UIHelper.hBox(new JLabel(""), 3, okButton)
        ), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

}
