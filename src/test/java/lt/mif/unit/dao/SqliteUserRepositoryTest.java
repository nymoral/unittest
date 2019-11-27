package lt.mif.unit.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;


class SqliteUserRepositoryTest {

    @Test
    void connectionUrl() {
        Assertions.assertEquals("jdbc:sqlite:somefiel.db", SqliteUserRepository.connectionUrl(new File("somefiel.db")));
    }
}