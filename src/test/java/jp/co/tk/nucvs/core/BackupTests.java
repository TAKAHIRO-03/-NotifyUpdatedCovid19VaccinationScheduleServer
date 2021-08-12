package jp.co.tk.nucvs.core;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import lombok.val;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class BackupTests {
    
    @Autowired
    DataSource dataSource;

    private final String BACK_UP_FILE_PATH = "src/test/resouces/dbunit/backup.xml";

    @Test
    void 正常にバックアップが作成出来るか() throws Exception {
        val con = new DatabaseConnection(dataSource.getConnection());
        val queryDataSet = new QueryDataSet(con);
        queryDataSet.addTable("user_info");
        queryDataSet.addTable("covid19_vaccination_venue");
        queryDataSet.addTable("covid19_vaccination_schedule");
        FlatXmlDataSet.write(queryDataSet, new FileOutputStream(BACK_UP_FILE_PATH));
        val file = new File(BACK_UP_FILE_PATH);
        assertTrue(file.exists());
        assertTrue(1 <= file.length());
    }

}
