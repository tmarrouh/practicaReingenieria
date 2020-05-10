

package capaDatos;

import capaLogicaNegocio.Convocatoria;
import capaLogicaNegocio.Curso;
import capaLogicaNegocio.GrupoPractica;
import capaLogicaNegocio.Profesor;
import capaLogicaNegocio.Tutoria;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Clase que gestiona el acceso a la Base de Datos para
 *  altas, bajas, modificaciones y consultas de tutorías.
 *
 * @author Confiencial
 */
public class ControladorTutoria {

    public ControladorTutoria() {
    }


 /** Método que se encarga de insertar los datos del la tutoría
     *  en la Base de Datos.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param cod_grupo contiene el código del grupo de prácticas.
     * @param tutor contiene la información del tutor del grupo.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void insertarEnTablaTutoria( int cod_grupo, Profesor tutor,
                                        DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("INSERT TUTORIA "
                                            + "VALUES (?,?,?,?,?)");
        
        int cod_tutor = tutor.getCodProfesor(); 
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        Curso curso = new Curso ();
        Convocatoria convocatoria = new Convocatoria();
        Tutoria tutoria = new Tutoria();
        
        try{

            System.out.println("Cod_tutor = "+cod_tutor);
            stmt.setInt(1, tutoria.getNuevoCodTutoria());
            stmt.setInt(2, cod_tutor);
            stmt.setInt(3, cod_grupo); 
            stmt.setInt(4, curso.getCursoActual());
            stmt.setString(5, convocatoria.getConvocatoriaActual());

            dataAccessObject.actualizar();

            stmt.close();
        } catch (SQLException e) {
            System.out.println("insertarEnTablaTutoria "+e.getMessage());
            throw new RuntimeException("Problema en insertarEnTablaTutoria");
        }
    } // fin del método insertarEnTablaTutoria



    /** Método que elimina de la Base de Datos TODAS las tutorías registradas
     *  para la convocatoria actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void eliminarTutoriasConvocatoriaActual(DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM tutoria "
                                            + "WHERE ((CURSO_Cod_CURSO = ?) "
                                            + "AND (CONVOCATORIA_idCONVOCATORIA = ?))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try{
            Convocatoria convocatoria = new Convocatoria();
            Curso curso = new Curso();
            stmt.setInt(1, curso.getCursoActual());
            stmt.setString(2, convocatoria.getConvocatoriaActual());
            dataAccessObject.actualizar();
            stmt.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Problema en eliminarEvaluacionesConvocatoriaActual");
        }
    } // fin del método eliminarTutoriasConvocatoriaActual



    /** Método que comprueba si un profesor está tutorizando, al menos, un
     *  grupo de prácticas.
     *
     * @param profesor contiene la información del profesor.
     *
     * @return TRUE si tiene, al menos, una tutoría.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean tieneTutorias(Profesor profesor) {
        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM tutoria "
                                            + "WHERE (PROFESOR_Cod_P = ?) ");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            stmt.setInt(1, profesor.getCodProfesor());
            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            dataAccessObject.close();
            return ok;
        } catch (SQLException e1) {
            dataAccessObject.rollback();
            throw new RuntimeException("Ha ocurrido un error la acceder a la Base de Datos");
        } catch (RuntimeException e2) {
            dataAccessObject.rollback();
            throw new RuntimeException("Ha ocurrido un error la acceder a la Base de Datos");
        }
    } // fin del método tieneTutorias


    
    /** Método que consulta en la Base de Datos las claves de las tutorías
     *  y devuelve la mayor de ellas
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @return Si no hay ninguna tutoría registrada, devolverá cero.
     *         Si hay tutorías registradas, devolverá la mayor de las claves.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public int recuperarUltimoCodTutoria() {
        StringBuilder sql = new StringBuilder("SELECT Cod_TUTORIA "
                                            + "FROM tutoria "
                                            + "WHERE (Cod_TUTORIA >= ALL (SELECT Cod_TUTORIA"
                                                                + " FROM tutoria))");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            ResultSet rs = stmt.executeQuery();
            int ultimo_cod = 0;
            if (rs.next()) {
                ultimo_cod = rs.getInt(1);
            }
            rs.close();
            dataAccessObject.close();
            return ultimo_cod;
        }
        catch (SQLException ex) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema al consultar la clave de la última tutoría");
        }
    } // fin del método recuperarUltimoCodTutoria



    /** Método que comprueba si un grupo de prácticas está registrado en alguna
     *  de las tutorías de prácticas.
     *  Si ocurre algún error, devolverá una excepción.
     *
     * @param grupo contiene la información del grupo de prácticas.
     *
     * @return TRUE si existe en al menos una tutoría.
     *         FALSE si no existe en ninguna tutoría.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean estaRegistradoGrupoPractica(GrupoPractica grupo) {

        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM tutoria "
                                            + "WHERE (GRUPO_PRACTICA_Cod_GP = ?)");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setInt(1, grupo.getCodGrupoPractica());

            ResultSet rs = stmt.executeQuery();

            boolean ok = rs.next();
            dataAccessObject.close();
            rs.close();
            return ok;
        } catch (SQLException e) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en estaRegistradoGrupoPractica de tutoria");
        }
    } // fin del método estaRegistradoGrupoPractica



    /** Método que elimina del registro de tutoría de profesor, la correspondiente
     *  tutoría al grupo de prácticas.
     *
     * @param codGrupoPractica Código del grupo de prácticas tutorizado.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void borrarTutoriaGrupoPractica(int codGrupoPractica, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM TUTORIA "
                                            + "WHERE ((GRUPO_PRACTICA_Cod_GP = ?) "
                                            + "    AND (CURSO_Cod_CURSO = ?) "
                                            + "    AND (CONVOCATORIA_idCONVOCATORIA = ?))");

        try {
            Curso curso = new Curso();
            Convocatoria convocatoria = new Convocatoria();
            PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
            stmt.setInt(1, codGrupoPractica);
            stmt.setInt(2, curso.getCursoActual());
            stmt.setString(3, convocatoria.getConvocatoriaActual());
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("borrarTutoriaGrupoPractica");
            throw new RuntimeException("Error al actualizar la Base de Datos.");
        }
    } // fin del método borrarTutoriaGrupoPractica

} // fin de la clase ControladorTutoria