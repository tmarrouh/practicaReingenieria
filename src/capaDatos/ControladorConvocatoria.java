

package capaDatos;

import capaLogicaNegocio.Convocatoria;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Clase que gestiona el acceso a la Base de Datos para
 * altas, bajas, modificaciones y consultas de convocatorias.
 *
 * @author Confiencial
 */
public class ControladorConvocatoria {

    private static ControladorConvocatoria dao = new ControladorConvocatoria ();

    /** Método que, mediante una consulta sql a la Base de Datos, obtiene
     * el código de la convocatoria actual y lo devuelve como resultado.
     * En caso de que ocurra algún error, se lanzará una excepción.
     *
     * @return Si la ejecución del método se realiza satisfactoriamente, devuelve
     *         el código de la convocatoria actual.
     *         Si no encuentra una convocatoria que satisfaga la condición de la
     *         consulta, lanzará una excepción.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public String recuperarConvocatoriaActual () {

        String convocatoriaActual = null;
        StringBuilder sql = new StringBuilder("SELECT idCONVOCATORIA "
                                            + "FROM convocatoria "
                                            + "WHERE (actual = 1)");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
            
        try {            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                convocatoriaActual = rs.getString(1);
                rs.close();
                dataAccessObject.close();
                return convocatoriaActual;
            } else {
                rs.close();
                dataAccessObject.close();
                throw new RuntimeException("No se ha encontrado la convocatoria actual");
            }
        } catch (SQLException ex) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema al consultar convocatoria actual");
         }
    } // fin del método recuperarConvocatoriaActual



    /** Método que actualiza, en la Base de Datos, la convocatoria actual.
     *  Si ocurre algún error, lanzará una excepción.
     */
    public void cambioConvocatoria() {

        String convocatoria_actual, nueva_convocatoria_actual;
        Convocatoria convocatoria = new Convocatoria();
        convocatoria_actual = convocatoria.getConvocatoriaActual();
        if (convocatoria_actual.equals("ordinaria")) {
            nueva_convocatoria_actual = "extraordinaria";
        } else {
            nueva_convocatoria_actual = "ordinaria";
        }
        StringBuilder sql = new StringBuilder("UPDATE CONVOCATORIA "
                                            + "SET actual = 0 "
                                            + "WHERE (idCONVOCATORIA = ?)");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try{
          stmt.setString(1, convocatoria_actual);
          dataAccessObject.actualizar();

          sql = new StringBuilder("UPDATE CONVOCATORIA "
                                + "SET actual = 1 "
                                + "WHERE (idCONVOCATORIA = ?)");

          stmt = dataAccessObject.getPreparedStatement(sql.toString());
          stmt.setString(1, nueva_convocatoria_actual);

          dataAccessObject.actualizar();

          dataAccessObject.close();
        } catch (SQLException ex) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en cambioConvocatoria");
        }
    } // fin del método cambioConvocatoria



} // fin de la clase ControladorConvocatoria