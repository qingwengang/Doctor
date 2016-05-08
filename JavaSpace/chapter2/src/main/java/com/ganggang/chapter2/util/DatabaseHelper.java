package com.ganggang.chapter2.util;

import com.sun.javafx.collections.MappingChange;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map;
/**
 * Created by wgqing on 2015/11/10.
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static  final ThreadLocal<Connection> CONNECTION_HOLDER=new ThreadLocal<Connection>();
    private static final BasicDataSource DATA_SOURCE;
    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");
        DATA_SOURCE=new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection fail", e);
                throw  new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    public static void closeConnection() {
        Connection conn=CONNECTION_HOLDER.get();
        if(conn!=null){
            try{
            conn.close();}catch (SQLException e){
                LOGGER.error("closeConnection fail", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }


    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = new ArrayList<T>();
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("queryEntityList error",e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entityList;
    }
    public static <T> T queryEntity(Class<T> entityClass,String sql,Object... params){
        T entity;
        try{
            Connection conn = getConnection();
            entity=QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        }catch (SQLException e) {
            LOGGER.error("queryEntityList error",e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entity;
    }
    public static List<Map<String,Object>> executeQuery(String sql,Object... params){
        List<Map<String,Object>> result;
        try{
            Connection conn = getConnection();
            result=QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
        }catch (SQLException e){
            LOGGER.error("queryEntityList error",e);
            throw new RuntimeException(e);
        }
        return result;
    }
    public static int executeUpdate(String sql,Object... params){
        int rows=0;
        try{
            Connection conn = getConnection();
            rows=QUERY_RUNNER.update(conn,sql,params);
        }catch (SQLException e){
            LOGGER.error("queryEntityList error",e);
            throw new RuntimeException(e);
        }
        return rows;
    }
}
