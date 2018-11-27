package lt.mif.unit.dao;

import lt.mif.unit.models.User;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SqliteUserRepository implements UserRepository, Closeable {
    private final Connection connection;

    public SqliteUserRepository(File dbFile) throws SQLException {
        this(DriverManager.getConnection(connectionUrl(dbFile)));
    }

    static String connectionUrl(File dbFile) {
        return String.format("jdbc:sqlite:%s", dbFile.getPath());
    }

    public SqliteUserRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTables() {
        try (PreparedStatement st = connection.prepareStatement("" +
                "CREATE TABLE User(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "name VARCHAR(256),\n" +
                "email VARCHAR(256),\n" +
                "password VARCHAR(256),\n" +
                "locked BOOLEAN,\n" +
                "deleted BOOLEAN)")) {
            st.execute();
        } catch (SQLException ignored) {
            // Ignored if tables exist.
        }
    }

    @Override
    public List<User> findActiveUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement("SELECT u.id, u.name, u.email, u.password, u.locked, u.deleted from User u where u.deleted = false")) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    users.add(extract(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return users;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        try (PreparedStatement st = connection.prepareStatement("SELECT u.id, u.name, u.email, u.password, u.locked, u.deleted from User u where u.deleted = false and u.id = ?")) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                } else {
                    return Optional.of(extract(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private User extract(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong(1));
        u.setName(rs.getString(2));
        u.setEmail(rs.getString(3));
        u.setPassword(rs.getString(4));
        u.setLocked(rs.getBoolean(5));
        u.setDeleted(rs.getBoolean(6));
        return u;
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement st = connection.prepareStatement("update User set deleted = true where id = ?")) {
            st.setLong(1, id);
            int nUpdated = st.executeUpdate();
            return nUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean lockById(Long id) {
        return setLocked(id, true);
    }

    @Override
    public boolean unlockById(Long id) {
        return setLocked(id, false);
    }

    private boolean setLocked(Long id, boolean desiredValue) {
        try (PreparedStatement st = connection.prepareStatement("update User set locked = ? where id = ? and locked = ?")) {
            st.setBoolean(1, desiredValue);
            st.setLong(2, id);
            st.setBoolean(3, !desiredValue);
            int nUpdated = st.executeUpdate();
            return nUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Long saveUser(User user) {
        if (user.getId() == null) {
            return saveNewUser(user);
        } else {
            return saveExisting(user);
        }
    }

    private Long saveNewUser(User user) {
        try (PreparedStatement st = connection.prepareStatement("insert into User (name, email, password, locked, deleted) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            setUserFields(st, user);
            int nAffected = st.executeUpdate();
            if (nAffected == 0) {
                throw new SQLException("No rows affected by the insert");
            }
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Long saveExisting(User user) {
        try (PreparedStatement st = connection.prepareStatement("update User u set u.name=?, u.email=?, u.password=?, u.locked=?, u.deleted=? where u.id=?")) {
            setUserFields(st, user);
            st.setLong(6, user.getId());
            int nAffected = st.executeUpdate();
            if (nAffected == 0) {
                throw new SQLException("No rows affected by the update");
            }
            return user.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setUserFields(PreparedStatement st, User user) throws SQLException {
        st.setString(1, user.getName());
        st.setString(2, user.getEmail());
        st.setString(3, user.getPassword());
        st.setBoolean(4, user.isLocked());
        st.setBoolean(5, user.isDeleted());
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
