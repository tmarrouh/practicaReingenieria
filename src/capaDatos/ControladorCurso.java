

package capaDatos;

import capaLogicaNegocio.Curso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Clase que gestiona el acceso a la Base de Datos para
 * altas, bajas, modificaciones y consultas de cursos
 *
 * @author Confiencial
 */
public class ControladorCurso {

    private static ControladorCurso dao = new ControladorCurso ();


    public ControladorCurso () {
    }

    /** Método que se encarga de insertar los datos del curso en la Base de Datos
     * Si ocurre algún error, lanzará una excepción.
     *
     * @param curso contiene los datos del curso del que se quiere dar de alta.
     */
    public void insertarEnTablaCurso(Curso curso) {

        StringBuilder sql = new StringBuilder("INSERT curso "
                                            + "VALUES (?,?)");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            stmt.setInt(1, curso.getCurso());
            stmt.setInt(2, curso.getActual());
            dataAccessObject.actualizar();
            dataAccessObject.close();
        } catch (SQLException ex) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en insertarEnTablaCurso");
        }
    } // fin del método insertarEnTablaCurso



    /** Método que, mediante una consulta sql a la Base de Datos, obtiene
     *  el código del curso actual y lo devuelve como resultado. En caso de
     *  que ocurra algún error, se lanzará una excepción.
     * 
     * @return Si la ejecución del método se realiza satisfactoriamente, devuelve
     *         el código de la convocatoria actual.
     *         Si no encuentra una convocatoria que satisfaga la condición de la
     *         consulta, lanzará una excepción.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public int recuperarCursoActual () {

        int cursoActual;
        StringBuilder sql = new StringBuilder("SELECT Cod_CURSO "
                                            + "FROM curso "
                                            + "WHERE (actual = 1)");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cursoActual = rs.getInt(1);
                rs.close();
                dataAccessObject.close();
                return cursoActual;
            } else {
                rs.close();
                dataAccessObject.close();
                throw new RuntimeException("No se ha encontrado el curso actual");
            }
        }
        catch (SQLException ex) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema al consultar curso actual");
        }
    } // fin del método recuperarCursoActual


    /** Método que elimina de la Base de Datos el curso recibido por parámetro.
     *
     * @param curso_actual curso que se quiere eliminar.
     */
    public void eliminarCurso(int curso_actual) {
        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM curso "
                                            + "WHERE (Cod_CURSO = ?)");

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setInt(1, curso_actual);
            dataAccessObject.actualizar();
            dataAccessObject.close();
        } catch (SQLException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Error en eliminarCurso");
        }
    } // fin del método eliminarCurso


    /** Método que actualiza, en la Base de Datos, el curso actual.
     *
     * @param curso_actual nuevo curso actual.
     */
    public void nuevoCursoActual(int curso_actual) {
        StringBuilder sql = new StringBuilder("UPDATE CURSO "
                                            + "SET actual = 1 "
                                            + "WHERE (Cod_CURSO = ?)");

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setInt(1, curso_actual);
            dataAccessObject.actualizar();
            dataAccessObject.close();
        } catch (SQLException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Error en nuevoCursoActual");
        }
    } // fin del método nuevoCursoActual


    /** Método que desactiva en la Base de Datos el curso actual.
     *  Es decir, pone el flag "actual" a cero.
     *  OJO: el curso NO se elimina de la Base de Datos, sólo se desactiva
     *  su flag "actual".
     *
     * @param curso_actual curso actual (que se va a desactivar).
     */
    public void desactivarCursoActual(int curso_actual) {
        StringBuilder sql = new StringBuilder("UPDATE CURSO "
                                            + "SET actual = 0 "
                                            + "WHERE (Cod_CURSO = ?)");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setInt(1, curso_actual);
            dataAccessObject.actualizar();
            dataAccessObject.close();
        } catch (SQLException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Error en desactivarCursoActual");
        }
    } // fin del método desactivarCursoActual    

} // fin de la clase CotroladorCurso