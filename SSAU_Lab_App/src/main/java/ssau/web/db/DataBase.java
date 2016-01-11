package ssau.web.db;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ssau.lab.Game;
import ssau.lab.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBase {

    private final Connection connection;
    private final String url = "jdbc:mysql://127.0.0.1:3306/ssau";
    private final String name = "root";
    private final String password = "a26014680";

    public DataBase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver connected...");
        connection = DriverManager.getConnection(url, name, password);
        System.out.println("Data base connected...");
    }

    public void close() throws SQLException {
        connection.close();
        System.out.println("Data base disconnected...");
    }


    @Nullable
    public synchronized Genre updateGenre(@NotNull final Genre genre) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE genre SET genre_name = ? WHERE genre_id = ?");

            preparedStatement.setString(1, genre.getGenreName());

            preparedStatement.setString(2, genre.getGenreId());
            if (preparedStatement.executeUpdate() != 1) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        return genre;
    }

    @Nullable
    public synchronized Game updateGame(@NotNull final Game game)  {
        try {
            PreparedStatement preparedStatementGame = connection.prepareStatement("UPDATE game SET game_name = ?, game_company = ? WHERE game_id = ?");

            preparedStatementGame.setString(1, game.getGameName());

            preparedStatementGame.setString(2, game.getGameCompany());
            preparedStatementGame.setString(3, game.getGameId());
            if (preparedStatementGame.executeUpdate() != 1) {
                return null;
            }

            List<String> oldGenreIdList = new ArrayList<>();
            List<String> newGenreIdList = new ArrayList<>();
            List<String> needToAdd = new ArrayList<>();
            List<String> needToDelete = new ArrayList<>();

            for (final Genre genre : game.getGenreList()) {
                newGenreIdList.add(genre.getGenreId());
            }

            PreparedStatement preparedStatementRelation = connection.prepareStatement("SELECT genre_id FROM relation WHERE game_Id = ?");
            preparedStatementRelation.setString(1, game.getGameId());
            ResultSet resultSet = preparedStatementRelation.executeQuery();
            while (resultSet.next()) {
                oldGenreIdList.add(resultSet.getString(1));
            }

            for (final String id : oldGenreIdList) {
                if (!newGenreIdList.contains(id)) {
                    needToDelete.add(id);
                }
            }

            for (final String id : newGenreIdList) {
                if (!oldGenreIdList.contains(id)) {
                    needToAdd.add(id);
                }
            }

            for (final String id : needToAdd) {
                PreparedStatement preparedStatementAdd = connection.prepareStatement("INSERT INTO relation VALUES (NULL, ?, ?)");
                preparedStatementAdd.setString(1, game.getGameId());
                preparedStatementAdd.setString(2, id);
                if (preparedStatementAdd.executeUpdate() != 1) {
                    return null;
                }
            }

            for (final String id : needToDelete) {
                PreparedStatement preparedStatementDelete = connection.prepareStatement("DELETE FROM relation WHERE game_id = ? AND genre_id = ?");
                preparedStatementDelete.setString(1, game.getGameId());
                preparedStatementDelete.setString(2, id);
                if (preparedStatementDelete.executeUpdate() != 1) {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return game;
    }

    @Nullable
    public synchronized Genre createGenre(@NotNull final Genre genre) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO genre VALUES (?, ?)");
            preparedStatement.setString(1, genre.getGenreId());
            preparedStatement.setString(2, genre.getGenreName());
            if(preparedStatement.executeUpdate() != 1) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return genre;

    }

    @Nullable
    public synchronized Game createGame(@NotNull final Game game) {
        try {
            PreparedStatement preparedStatementGame = connection.prepareStatement("INSERT INTO game VALUES (?, ?, ?)");
            preparedStatementGame.setString(1, game.getGameId());
            preparedStatementGame.setString(2, game.getGameName());
            preparedStatementGame.setString(3, game.getGameCompany());
            if (preparedStatementGame.executeUpdate() != 1) {
                return null;
            }

            List<String> needToAdd = new ArrayList<>();

            for(final Genre genre : game.getGenreList()) {
                needToAdd.add(genre.getGenreId());
            }

            for(final String id : needToAdd) {
                PreparedStatement preparedStatementAdd = connection.prepareStatement("INSERT INTO relation VALUES (NULL, ?, ?)");
                preparedStatementAdd.setString(1, game.getGameId());
                preparedStatementAdd.setString(2, id);
                if(preparedStatementAdd.executeUpdate() != 1) {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return game;
    }

    @Nullable
    public synchronized Genre deleteGenre(@NotNull final Genre genre) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM genre WHERE genre_id = ?");
            preparedStatement.setString(1, genre.getGenreId());
            if(preparedStatement.executeUpdate() != 1) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return genre;
    }

    @Nullable
    public synchronized Game deleteGame(@NotNull final Game game) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM game WHERE game_id = ?");
            preparedStatement.setString(1, game.getGameId());
            if(preparedStatement.executeUpdate() != 1) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return game;
    }

    @Nullable
    public synchronized Genre getGenreById(@NotNull final String genreId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM genre WHERE genre_id = ?");
            preparedStatement.setString(1, genreId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Genre(resultSet.getString(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public synchronized Game getGameById(@NotNull final String gameId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM game WHERE genre_id = ?");
            preparedStatement.setString(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            final Game game =  new Game(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));

            List<String> genreIdList = new ArrayList<>();

            PreparedStatement preparedStatementRelation = connection.prepareStatement("SELECT genre_id FROM relation WHERE game_Id = ?");
            preparedStatementRelation.setString(1, game.getGameId());
            ResultSet resultSetGenresId = preparedStatementRelation.executeQuery();
            while (resultSetGenresId.next()) {
                genreIdList.add(resultSetGenresId.getString(1));
            }

            for(final String genreId : genreIdList) {
               final Genre genre = getGenreById(genreId);
               if(genre != null) {
                   game.getGenreList().add(genre);
               }
            }

            return game;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @NotNull
    public synchronized List<Genre> getAllGenres() {
        try {
            List<Genre> genreList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM genre");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
               genreList.add(new Genre(resultSet.getString(1), resultSet.getString(2)));
            }
            return genreList;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    @NotNull
    public synchronized List<Game> getAllGames() {
        try {
            List<Game> gameList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM game");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Game game = new Game(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));

                List<String> genreIdList = new ArrayList<>();

                PreparedStatement preparedStatementRelation = connection.prepareStatement("SELECT genre_id FROM relation WHERE game_Id = ?");
                preparedStatementRelation.setString(1, game.getGameId());
                ResultSet resultSetGenresId = preparedStatementRelation.executeQuery();
                while (resultSetGenresId.next()) {
                    genreIdList.add(resultSetGenresId.getString(1));
                }

                for (final String genreId : genreIdList) {
                    final Genre genre = getGenreById(genreId);
                    if (genre != null) {
                        game.getGenreList().add(genre);
                    }
                }
                gameList.add(game);
            }

            return gameList;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }
}

