
package capaDatos;

import capaLogicaNegocio.Convocatoria;
import capaLogicaNegocio.Curso;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Clase que gestiona el acceso a la Base de Datos para
 *  altas, bajas, modificaciones y consultas de los exámenes.
 *
 * @author Confiencial
 */
public class ControladorExamen {

    public ControladorExamen () {
    }

    /** Método que da de alta en la Base de Datos un nuevo código de examen
     *  para una nueva convocatoria. Si ocurre algún error, lanzará una excepción.
     */
    public void darAltaNuevoCodExamen() {
        StringBuilder sql = new StringBuilder("INSERT examen "
                                            + "VALUES (?)");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            Curso curso = new Curso();
            Convocatoria convocatoria = new Convocatoria();
            String nuevo_cod_practica = convocatoria.getConvocatoriaActual()+
                                        Integer.toString(curso.getCursoActual());
            stmt.setString(1, nuevo_cod_practica);
            dataAccessObject.actualizar();
            dataAccessObject.close();
        } catch (SQLException ex) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en darAltaNuevoCodExamen");
        }
    } // fin del método darAltaNuevoCodExamen
    

    /** Método que elimina de la Base de Datos, el código de examen de la
     *  convocatoria actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void bajaCodExamenConvocatoriaActual(DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM examen "
                                            + "WHERE (Cod_Ex = ?) ");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            Curso curso = new Curso();
            Convocatoria convocatoria = new Convocatoria();
            String cod_examen_convocatoria_actual = convocatoria.getConvocatoriaActual()+
                                                    Integer.toString(curso.getCursoActual());
            stmt.setString(1, cod_examen_convocatoria_actual);
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Problema en bajaCodExamenConvocatoriaActual");
        }
    } // fin del método bajaCodExamenConvocatoriaActual


} // fin de la clase ControladorExamen