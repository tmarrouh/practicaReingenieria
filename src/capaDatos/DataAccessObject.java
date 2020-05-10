
package capaDatos;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/** Clase que gestiona el acceso a la Base de Datos.
 *
 * @author Alberto Esteves Correia
 */
public class DataAccessObject {


    private static final String DRIVER = "com.mysql.jdbc.Driver";

    /** Contiene la dirección de la Base de Datos.
     *  Se inicializa al comenzar la aplicación.
     *  El usuario podrá modificarlo en el menú de configuración.
     */    
    public static String URL;

    /** Contiene el usuario para la conexión a la Base de Datos.
     *  Se inicializa al comenzar la aplicación.
     *  El usuario podrá modificarlo en el menú de configuración.
     */
    public static String USER;

    /** Contiene la contraseña para la conexión a la Base de Datos.
     *  Se inicializa al comenzar la aplicación.
     *  El usuario podrá modificarlo en el menú de configuración.
     */
    public static String PASS;

    private static DataAccessObject dataAccessObject=null;
    private PreparedStatement statement = null;
    private Connection connection;

    /** Método que nos devuelve el objeto dataAccessObject estático que tiene la clase.
     * La primera vez que se llama a getDataAccessObjectConnected() crea el objeto DataAccessObject
     * y hará la conexión a la BD. En cualquier caso se hace “return DataAccessObject” para que
     * podamos usar el objeto
     *
     * @return dataAccessObject
     */
    public static DataAccessObject getDataAccessObjectConnected(){
		if (dataAccessObject==null){
                    dataAccessObject = new DataAccessObject();
		}
		dataAccessObject.connect();
	
            return dataAccessObject;

    } // fin del método getDataAccessObjectConnected


    /** Método que devuelve el objeto statement con el que realizaremos
     * las consultas sql.
     *
     * @param sql contiene la consulta sql a realizar
     *
     * @return statement 
     */

    public PreparedStatement getPreparedStatement(String sql){
        try {
            this.statement = connection.prepareStatement(sql);
            return statement;
        }
        catch (SQLException ex) {
            throw new RuntimeException("Problema al obtener el prepared statement"
                                     + " el sql es: "+sql);
        }
    } // fin del método getPreparedStatement

    private DataAccessObject(){}

    /** Este método se encarga de cargar el Driver MySQL y de realizar
     *  la conexión a la Base de Datos. Si ocurre algún error al cargar el Driver
     *  o al intentar conectar a la Base de Datos, el método lanzará una excepción.
     *
     */
    private void connect(){
	try {
		Class.forName(DRIVER);
		connection = DriverManager.getConnection(URL, USER, PASS);
                connection.setAutoCommit(false);
	} catch (ClassNotFoundException e) {
		throw new RuntimeException("problemas de driver");
	} catch (SQLException e) {
		throw new RuntimeException("Ha ocurrido un error al conectar con la Base de Datos");
	}
    } // fin del método connect


   /** Método que ejecuta la consulta SQL para insertar, borrar o actualizar.
     * Si ocurre algún error, lanzará una excepción.
     */
    public void actualizar (){
	try{
            
            this.statement.executeUpdate();
	} catch (SQLException e){
            System.out.println(e.getSQLState());
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException("Error de actualización ");
	}
    } // fin del método actualizar


    /** Método que libera los recursos de la Base de Datos y se encarga de
     *  cerrar la conexión a la Base de Datos.
     *  Si ocurre algún error, lanzará una excepción.
     */
    public void close () {
        try {
            statement.close();
            this.closeConnection();
        }
        catch (SQLException ex) {
            throw new RuntimeException("Problema al cerrar la conexión con la Base de Datos");
        }
    } // fin del método close
	
	
    /** Método que realiza la entrega (commit) de la sentencia sql y 
     *  cierra la conexión a la Base de Datos.
     *  Si ocurre algún error, lanzará una excepción.
     */
    private void closeConnection () {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException ex) {
 //           this.rollback();
            throw new RuntimeException("Problema al cerrar la conexión");
        }
    } // fin del método closeConnection


    /** Método que aborta la transación y devuelve cualquier valor
     *  que fuera modificado a sus valores anteriores.
     *
     */
    public void rollback () {
        try {
            statement.close();
            System.out.println("Rollback 1");
            connection.rollback();
            System.out.println("Rollback 2");
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Problema al hacer rollback");
        }
    } // fin del método rollback

} // fin de la clase DataAccessObject