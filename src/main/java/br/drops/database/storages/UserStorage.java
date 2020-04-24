package br.drops.database.storages;

import br.drops.Drops;
import br.drops.database.MySQLDatabase;
import br.drops.objects.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class UserStorage {

    private MySQLDatabase mySQLDatabase;
    private Gson gson;

    public UserStorage() {
        mySQLDatabase = Drops.getInstance().getMySQLDatabase();
        gson = new GsonBuilder().setPrettyPrinting().create();
        createTable();
    }

    public static final String CREATE = "CREATE TABLE IF NOT EXISTS drops (" +
            "name VARCHAR(16) PRIMARY KEY NOT NULL, " +
            "drops TEXT " +
            ")";

    public static final String SELECT = "SELECT drops FROM drops WHERE name = ?";

    public static final String UPDATE = "REPLACE INTO drops (name, drops) VALUES (?, ?)";


    public void createTable(){
        Connection connection = mySQLDatabase.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(CREATE)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User load(String name) {

        Connection connection = mySQLDatabase.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(SELECT)) {


            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {

                String drops = resultSet.getString("drops");
                Type dropsToken = new TypeToken<Map<Material, Integer>>() {
                }.getType();


                Map<Material, Integer> dropsMap = gson.fromJson(drops, dropsToken);

                return new User(name, dropsMap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(User user) {
        Connection connection = mySQLDatabase.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {

            ps.setString(1, user.getName());
            ps.setString(2, gson.toJson(user.getMaterials()));
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
