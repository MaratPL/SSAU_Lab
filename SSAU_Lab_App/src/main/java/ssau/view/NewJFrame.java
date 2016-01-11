package ssau.view;

import com.thoughtworks.xstream.XStream;
import ssau.client.Client;
import ssau.client.ServerResponseListener;
import ssau.lab.Game;
import ssau.lab.Genre;
import ssau.protocol.OperationType;
import ssau.protocol.Protocol;
import ssau.view.updateDialog.UpdateGameDialog;
import ssau.view.updateDialog.UpdateGenreDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.bind.JAXBException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewJFrame extends javax.swing.JFrame {

    public NewJFrame(Client client, ObjectOutputStream oos, ObjectInputStream ois) {
        this.client = client;
        this.oos = oos;
        this.ois = ois;
        initComponents();
    }

    public String gameName;
    public String gameData;
    public String genreName;
    private Client client;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Client getClient(){
        return client;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        gamePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        gameNameTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        gameDataTextField1 = new javax.swing.JTextField();
        createGameButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        allGamesTable1 = new javax.swing.JTable();
        deleteGameButton1 = new javax.swing.JButton();
        updateGameButton1 = new javax.swing.JButton();
        genrePanel = new javax.swing.JPanel();
        genreNameTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        createGenreButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        allGenreTable1 = new javax.swing.JTable();
        deleteGenreButton1 = new javax.swing.JButton();
        updateGenreButton1 = new javax.swing.JButton();
        saveKey = new javax.swing.JButton();
        loadKey = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Game Name ");

        gameNameTextField1.setToolTipText("");
        gameNameTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameNameTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Game Company");

        gameDataTextField1.setToolTipText("");
        gameDataTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameDataTextField1ActionPerformed(evt);
            }
        });

        createGameButton1.setText("Create Game");
        createGameButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createGameButton1MouseClicked(evt);
            }
        });

        jLabel3.setText("All Games Table");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        allGamesTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "game id", "game Name", "game Company", "game Genre"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        //------------MY CODE
        final JComboBox gameGenresColumnComboBox = new JComboBox<>();
        gameGenresColumnComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameGenresColumnComboBox.removeAllItems();
                allGamesTable1.getSelectedColumn();
                int i = allGamesTable1.getSelectedRow();
                if (i != -1) {
                    DefaultTableModel model = (DefaultTableModel) allGamesTable1.getModel();
                    List<Genre> temp = client.getModel().getGameById(model.getValueAt(i, 0).toString()).getGenreList();
                    for (final Genre genre : temp) {
                        gameGenresColumnComboBox.addItem(genre.getGenreName());
                    }
                }
            }
        });

        TableColumn gameGenesColumn = allGamesTable1.getColumnModel().getColumn(3);
        gameGenesColumn.setCellEditor(new DefaultCellEditor(gameGenresColumnComboBox));
        //-----------

        jScrollPane1.setViewportView(allGamesTable1);
        if (allGamesTable1.getColumnModel().getColumnCount() > 0) {
            allGamesTable1.getColumnModel().getColumn(1).setCellEditor(null);
        }


        // ------------MY CODE
        allGamesTable1.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e){

                // i = the index of the selected row
                int i = allGamesTable1.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) allGamesTable1.getModel();
                gameNameTextField1.setText(model.getValueAt(i, 1).toString());
                gameDataTextField1.setText(model.getValueAt(i, 2).toString());
            }
        });

        //=-------------------

        deleteGameButton1.setText("Delete Game");
        deleteGameButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    deleteGameMouseClicked(evt);
                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        updateGameButton1.setText("Update Game");
        updateGameButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    updateGameButton1MouseClicked(evt);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, " ошибка обновления ", "ошибка обновления", JOptionPane.ERROR_MESSAGE);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        });

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
                gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(gamePanelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                        .addComponent(gameDataTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                        .addComponent(createGameButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(gameNameTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                        .addComponent(deleteGameButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(updateGameButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(27, 27, 27)
                                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        gamePanelLayout.setVerticalGroup(
                gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(gamePanelLayout.createSequentialGroup()
                                                .addComponent(gameNameTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(gameDataTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(updateGameButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(deleteGameButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(createGameButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
                                .addContainerGap())
        );

        jTabbedPane1.addTab("Games", gamePanel);

        genreNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genreNameTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setText("Genre Name");

        createGenreButton1.setText("Create Genre");
        createGenreButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createGenreButton1MouseClicked(evt);
            }
        });

        jLabel5.setText("All Genres Table");

        allGenreTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "genre id", "genre Name"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        //-------------MY CODE
        allGenreTable1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // i = the index of the selected row
                int i = allGenreTable1.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) allGenreTable1.getModel();
                genreNameTextField.setText(model.getValueAt(i, 1).toString());
            }
        });
        //-------------
        jScrollPane2.setViewportView(allGenreTable1);

        deleteGenreButton1.setText("Delete Genre");
        deleteGenreButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    deleteGenreButton1MouseClicked(evt);
                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        updateGenreButton1.setText("Update Genre");
        updateGenreButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    updateGenreButton1MouseClicked(evt);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, " ошибка обновления ", "ошибка обновления", JOptionPane.ERROR_MESSAGE);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        });
        updateGenreButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateGenreButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout genrePanelLayout = new javax.swing.GroupLayout(genrePanel);
        genrePanel.setLayout(genrePanelLayout);
        genrePanelLayout.setHorizontalGroup(
                genrePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(genrePanelLayout.createSequentialGroup()
                                .addGroup(genrePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(genrePanelLayout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addGroup(genrePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, genrePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(genreNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, genrePanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(genrePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(updateGenreButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(deleteGenreButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(createGenreButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(26, 26, 26)
                                .addGroup(genrePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(22, Short.MAX_VALUE))
        );
        genrePanelLayout.setVerticalGroup(
                genrePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(genrePanelLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(genrePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(genrePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(genrePanelLayout.createSequentialGroup()
                                                .addComponent(genreNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(31, 31, 31)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(updateGenreButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(deleteGenreButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(createGenreButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2))
                                .addContainerGap())
        );

        jTabbedPane1.addTab("Genres", genrePanel);

        saveKey.setText("Save");
        saveKey.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveKeyMouseClicked(evt);
            }
        });

        loadKey.setText("Refresh");
        loadKey.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadKeyMouseClicked(evt);
            }
        });
        loadKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadKeyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(loadKey, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(saveKey, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(loadKey, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(saveKey, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(17, 17, 17))
                                        .addComponent(jTabbedPane1))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>


    //?? ??????? ???????? ? COMBO BOX
    private void updateGameButton1MouseClicked(java.awt.event.MouseEvent evt) throws IOException, JAXBException {
        int i = allGamesTable1.getSelectedRow();
        if(i>=0){
            DefaultTableModel model = (DefaultTableModel) allGamesTable1.getModel();
            String gameId = model.getValueAt(i,0).toString();
            Game game = client.getModel().getGameById(gameId);
            client.sendRequestForEditGame(game.getGameId());
        }else {
            JOptionPane.showMessageDialog(null, " ошибка обновления ", "Ни одна игра не выбрана", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void beginUpdateGame(Game game){
        List<Genre> genres = client.getModel().getAllGenres();
        Map<String, String> genresMap = new HashMap<String, String>();
        for (Genre genre: genres){
            genresMap.put(genre.getGenreName(), genre.getGenreId());
        }
        UpdateGameDialog dialog = new UpdateGameDialog(game, genresMap, client);
        dialog.setVisible(true);
    }

    private void deleteGameMouseClicked(java.awt.event.MouseEvent evt) throws JAXBException, IOException, ClassNotFoundException {
        int i = allGamesTable1.getSelectedRow();
        if(i>=0){
            DefaultTableModel model = (DefaultTableModel) allGamesTable1.getModel();
            String deletedGmaeId = model.getValueAt(i,0).toString();
            client.removeGameById(deletedGmaeId);

            model.setRowCount(0);

            List<Game> allGames = client.getModel().getAllGames();
            for(final Game game:allGames) {
                if (game.getGenreList().size() != 0) {
                    String genre = game.getGenreList().get(0).getGenreName();
                    model.addRow(new String[]{game.getGameId(), game.getGameName(), game.getGameCompany(), genre});
                } else {
                    model.addRow(new String[]{game.getGameId(), game.getGameName(), game.getGameCompany()});
                }
            }
        }else {
            System.out.println("Delete Game Error!!!");
        }
    }

    private void createGameButton1MouseClicked(java.awt.event.MouseEvent evt) {
        gameName = gameNameTextField1.getText();
        gameData = gameDataTextField1.getText();

        List<Genre> tempList = new ArrayList<Genre>();
        try {
            client.addGame(gameName, gameData, tempList);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, " ошибка добавления ", "ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        updateGamesTable();
//        client.getModel().addGame(gameName,gameData,tempList);
//
//        DefaultTableModel model = (DefaultTableModel) allGamesTable1.getModel();
//        model.setRowCount(0);
//
//        List<Game> allGames = client.getModel().getAllGames();
//        for(final Game game:allGames) {
//            if (game.getGenreList().size() != 0) {
//                String genre = game.getGenreList().get(0).getGenreName();
//                model.addRow(new String[]{game.getGameId(), game.getGameName(), game.getGameCompany(), genre});
//            } else {
//                model.addRow(new String[]{game.getGameId(), game.getGameName(), game.getGameCompany()});
//            }
//        }
    }

    //?????? ?????? ????????? ?????? ??? ? ????????? (???????? ??? ?????????)

    private void updateGenreButton1MouseClicked(java.awt.event.MouseEvent evt) throws IOException, JAXBException {
        int i = allGenreTable1.getSelectedRow();
        if(i>=0){
            DefaultTableModel model = (DefaultTableModel) allGenreTable1.getModel();
            String genreId = model.getValueAt(i,0).toString();
            Genre genre = client.getModel().getGenreById(genreId);
            client.sendRequestForEditGenre(genre.getGenreId());
        }else {
            JOptionPane.showMessageDialog(null, " ошибка обновления ", "Ни один жанр не выбран", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void beginUpdateGenre(Genre genre){
        UpdateGenreDialog dialog = new UpdateGenreDialog(genre, client);
        dialog.setVisible(true);
    }


    private void deleteGenreButton1MouseClicked(java.awt.event.MouseEvent evt) throws JAXBException, IOException, ClassNotFoundException {
        int i = allGenreTable1.getSelectedRow();
        if(i>=0){
            DefaultTableModel model = (DefaultTableModel) allGenreTable1.getModel();
            String deletedGenreId = model.getValueAt(i,0).toString();
            client.removeGenreById(deletedGenreId);

            model.setRowCount(0);

            List<Genre> allGenres = client.getModel().getAllGenres();
            for(final Genre genre:allGenres){
                model.addRow(new String[]{genre.getGenreId(), genre.getGenreName()});
            }
        }else {
            System.out.println("Delete Genre Error!!!");
        }
    }

    private void createGenreButton1MouseClicked(java.awt.event.MouseEvent evt) {
        genreName = genreNameTextField.getText();
        try {
            client.addGenre(genreName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, " ошибка добавления ", "ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, " ошибка добавления ", "ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        updateGenresTable();

    }

    public void updateGamesTable(){
        DefaultTableModel model = (DefaultTableModel) allGamesTable1.getModel();
        model.setRowCount(0);

        List<Game> allGames = client.getModel().getAllGames();
        for(final Game game:allGames) {
            if (game.getGenreList().size() != 0) {
                String genre = game.getGenreList().get(0).getGenreName();
                model.addRow(new String[]{game.getGameId(), game.getGameName(), game.getGameCompany(), genre});
            } else {
                model.addRow(new String[]{game.getGameId(), game.getGameName(), game.getGameCompany()});
            }
        }
    }

    public void updateGenresTable(){
        DefaultTableModel model = (DefaultTableModel) allGenreTable1.getModel();
        model.setRowCount(0);

        List<Genre> allGenre = client.getModel().getAllGenres();
        for(final Genre genre:allGenre){
            model.addRow(new String[]{genre.getGenreId(), genre.getGenreName()});
        }
    }

    private void updateGenreButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void gameDataTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void gameNameTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void genreNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void loadKeyActionPerformed(java.awt.event.ActionEvent evt) {
        updateGamesTable();
        updateGenresTable();
    }

    private void saveKeyMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
    }

    private void loadKeyMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
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

        final Socket socket = new Socket("localhost", 4444);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        final Client client = new Client(oos, ois);
        Protocol protocol = new Protocol(client.getId(), OperationType.SUBSCRIBE, null, null);
        XStream xs = new XStream();
        oos.writeObject(xs.toXML(protocol));
        oos.flush();
        final NewJFrame frame = new NewJFrame(client, oos , ois);
        frame.setVisible(true);
        ServerResponseListener listener = new ServerResponseListener(socket, oos, ois, frame);
        listener.start();
    }

    // Variables declaration - do not modify
    private javax.swing.JTable allGamesTable1;
    private javax.swing.JTable allGenreTable1;
    private javax.swing.JButton createGameButton1;
    private javax.swing.JButton createGenreButton1;
    private javax.swing.JButton deleteGameButton1;
    private javax.swing.JButton deleteGenreButton1;
    private javax.swing.JTextField gameDataTextField1;
    private javax.swing.JTextField gameNameTextField1;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JTextField genreNameTextField;
    private javax.swing.JPanel genrePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton loadKey;
    private javax.swing.JButton saveKey;
    private javax.swing.JButton updateGameButton1;
    private javax.swing.JButton updateGenreButton1;
    // End of variables declaration                   
}
