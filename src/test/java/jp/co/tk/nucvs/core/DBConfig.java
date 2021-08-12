package jp.co.tk.nucvs.core;


import javax.sql.DataSource;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.val;


@Configuration
public class DBConfig {
   /*
    * テスト用のDBコネクションファクトリのセットアップ
    */
    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DataSource dataSource) {
        val cf = new DatabaseDataSourceConnectionFactoryBean();
        val dc = new DatabaseConfigBean();
        dc.setDatatypeFactory(new PostgresqlDataTypeFactory());
        cf.setDatabaseConfig(dc);
        cf.setDataSource(dataSource);
        return cf;
    }
}
