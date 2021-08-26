package ru.tfs.diploma.diff;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.DiffToChangeLog;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@SpringJUnitConfig(locations = "/test_application_context.xml")
class DummyTest {

//    @Autowired
//    private SpringLiquibase liquibase;

    @Autowired
    HikariDataSource notEmptyHDS;

    @Autowired
    @Qualifier("emptyDataBase")
    HikariDataSource emptyHDS;

    @Test
    public void test() throws SQLException, LiquibaseException, ParserConfigurationException, IOException {

        diff(notEmptyHDS.getConnection(), emptyHDS.getConnection());

    }

    private void diff(Connection referenceConnection, Connection targetConnection) throws LiquibaseException, IOException, ParserConfigurationException {

        Liquibase liquibase = null;

        try {

//            Database referenceDatabase = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(notEmptyHDS.getConnection()));
//            Database targetDatabase = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(emptyHDS.getConnection()));

            Database referenceDatabase = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(referenceConnection));
            Database targetDatabase = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(targetConnection));


            liquibase = new Liquibase("", new FileSystemResourceAccessor(), referenceDatabase);
            DiffResult diffResult = liquibase.diff(referenceDatabase, targetDatabase, new CompareControl());

            new DiffToChangeLog(diffResult, new DiffOutputControl()).print(System.out);

        } finally {
            if (liquibase != null) {
                liquibase.forceReleaseLocks();
            }
        }
    }

}