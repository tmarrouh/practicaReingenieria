
package capaDatos;

import capaLogicaNegocio.Convocatoria;
import capaLogicaNegocio.Curso;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Clase que gestiona el acceso a la Base de Datos para
 *  altas, bajas, modificaciones y consultas de las prácticas.
 *
 * @author Confiencial
 */
public class ControladorPractica {

    public ControladorPractica () {
    }

    /** Método que da de alta en la Base de Datos un nuevo código de prácticas
     *  para una nueva convocatoria. Si ocurre algún error, lanzará una excepción.
     */
    public void darAltaNuevoCodPractica() {
        StringBuilder sql = new StringBuilder("INSERT practica "
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
            throw new RuntimeException("Problema en darAltaNuevoCodPractica");
        }
    } // fin del método darAltaNuevoCodPractica



    /** Método que elimina de la Base de Datos, el código de prácticas de la
     *  convocatoria actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void bajaCodPracticaConvocatoriaActual(DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM practica "
                                            + "WHERE (Cod_Practica = ?) ");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            Curso curso = new Curso();
            Convocatoria convocatoria = new Convocatoria();
            String cod_practica_convocatoria_actual = convocatoria.getConvocatoriaActual()+
                                                      Integer.toString(curso.getCursoActual());
            stmt.setString(1, cod_practica_convocatoria_actual);
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Problema en bajaCodPracticaConvocatoriaActual");
        }
    } // fin del método bajaCodPracticaConvocatoriaActual

} // fin de la clase ControladorPractica