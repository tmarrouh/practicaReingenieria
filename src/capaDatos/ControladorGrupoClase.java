
package capaDatos;

import capaLogicaNegocio.GrupoClase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Clase que gestiona el acceso a la Base de Datos para
 * altas, bajas, modificaciones y consultas de grupos de clase.
 *
 * @author Confiencial
 */
public class ControladorGrupoClase {

    public ControladorGrupoClase () {
    }


    /** Método que comprueba, mediante una consulta sql en la Base de Datos,
     * si un grupo de clase está dado de alta.
     *
     * @param grupo_clase contiene la información del grupo de clase del cual
     *        se quiere comprobar si está dado de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si el grupo está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean estaDadoDeAlta(GrupoClase grupo_clase,
                                  DataAccessObject dataAccessObject) {
        
        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM grupo_clase "
                                            + " WHERE ((Cod_GC = ?))");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try{
            stmt.setString(1, grupo_clase.getCodGrupoClase());

            ResultSet rs = stmt.executeQuery();

            boolean ok = rs.next();
            rs.close();
            return ok;
        }
        catch (SQLException ex) {
            throw new RuntimeException("Problema al comprobar que el grupo de clase "
                                     + "está dado de alta");
        }
    } // fin del método estaDadoDeAlta


    /** Metodo que se encarga de dar de alta a un grupo de clase.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param grupo_clase contiene los datos del grupo de clase que se quiere
     *        dar de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void darAltaGrupoClase (GrupoClase grupo_clase,
                                   DataAccessObject dataAccessObject) {
        this.insertarEnTablaGrupoClase(grupo_clase, dataAccessObject);
    } // fin del método darAltaGrupoClase




    /** Método que se encarga de insertar los datos del grupo de clase
     *  en la Base de Datos.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param grupo_clase contiene la información del grupo de clase al cual
     *        se quiere dar de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void insertarEnTablaGrupoClase(GrupoClase grupo_clase,
                                          DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("INSERT GRUPO_CLASE "
                                            + "VALUES (?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try{
            stmt.setString(1, grupo_clase.getCodGrupoClase());
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Problema en insertarEnTablaGrupoClase");
        }
    } // fin del método insertarEnTablaGrupoClase

} // fin de la clase ControladorGrupoClase