package io.ride.dao;

import io.ride.util.DatabaseUitl;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;

public abstract class BaseDao {
    private DataSource dataSource = DatabaseUitl.getCpds();
    protected QueryRunner runner = new QueryRunner(dataSource);

}
