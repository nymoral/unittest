package lt.mif.unit.dao;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class SqliteUserRepositoryTest {

    @Test
    public void connectionUrl() {
        assertEquals("jdbc:sqlite:C:/sqlite/db/test.db", SqliteUserRepository.connectionUrl(new File("C:/sqlite/db/test.db")));
        assertEquals("jdbc:sqlite:somefiel.db", SqliteUserRepository.connectionUrl(new File("somefiel.db")));
    }
}