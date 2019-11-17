package ifpe.recife.tads.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;

public class DataSet {

    private static final String XML_FILE = "/dbunit/dataset.xml";

    @SuppressWarnings("UseSpecificCatch")
    public static void inserirDados() {
        Connection connection = null;
        IDatabaseConnection db_connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alerta_recife", "root", "root");
            db_connection = new DatabaseConnection(connection, "alerta_recife");
            DatabaseConfig dbConfig = db_connection.getConfig();
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
            dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            InputStream in = DataSet.class.getResourceAsStream(XML_FILE);
            IDataSet dataSet = builder.build(in);
            DatabaseOperation.CLEAN_INSERT.execute(db_connection, dataSet);
        } catch (Exception ex) {
            System.out.printf("A conexao falhou");
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (db_connection != null) {
                    db_connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
    }
}