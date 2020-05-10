

package capaDatos;

import capaLogicaNegocio.Curso;
import capaLogicaNegocio.GrupoClase;
import capaLogicaNegocio.Matricula;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/** Clase que gestiona el acceso a la Base de Datos para
 *  altas, bajas, modificaciones y consultas de matrículas de alumnos.
 *
 * @author Confiencial
 */
public class ControladorMatricula {

    public ControladorMatricula () {
        }

    /** Método que consulta en la Base de Datos las claves de las matrículas
     *  y devuelve la mayor de ellas.
     *
     * @return Si no hay ninguna matrícula registrada, devolverá cero.
     *         Si hay matrículas registradas, devolverá la mayor de las claves.
     *         Si ocurre algún error, devolverá -1.
     */
    public int recuperarUltimoCodMatricula () {

        StringBuilder sql = new StringBuilder (
                     "SELECT Cod_MATRICULA "
                   + "FROM matricula "
                   + "WHERE (Cod_MATRICULA >= ALL (SELECT Cod_MATRICULA"
                                               + " FROM matricula))");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            ResultSet rs = stmt.executeQuery();
            int ultimo_cod;
            if (rs.next()) {
                ultimo_cod = rs.getInt(1);
            } else {
                ultimo_cod = 0;
               }

            rs.close();
            dataAccessObject.close();
            return ultimo_cod;
        }
        catch (SQLException ex) {
            dataAccessObject.rollback();
            System.out.println("Problema al consultar la clave de la última matricula");
        }
        return -1;
    } // fin del método recuperarUltimoCodMatricula


    /** Método que se encarga de insertar los datos de la matrícula 
     * en la Base de Datos.
     *
     * @param matricula contiene los datos de la matricula que se quiere dar de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void insertarEnTablaMatricula(Matricula matricula, DataAccessObject dataAccessObject) {

        try {
            GrupoClase grupo_clase = new GrupoClase (matricula.getGrupo_Clase());
            grupo_clase.validarGrupoClase(dataAccessObject);
        } catch (RuntimeException e){
            throw new RuntimeException("Error al dar de alta el grupo de clase");
        }
        

        StringBuilder sql = new StringBuilder("INSERT matricula VALUES (?,?,?,?)");
           
        try {
            PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
            stmt.setInt(1, matricula.getNuevoCodMatricula());
            stmt.setString(2, matricula.getDNI());
            stmt.setString(3, matricula.getGrupo_Clase());
            stmt.setInt(4, matricula.getCod_Curso());

            dataAccessObject.actualizar();
            stmt.close();
            } catch (SQLException ex) {
            throw new RuntimeException("problema en darAltaMatricula");
            }       
   } // fin del método insertarEnTablaMatricula




    /** Método que elimina de la Base de Datos todas las matrículas dadas de alta
     *  en el curso actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void eliminarMatriculasCursoActual(DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM matricula "
                                            + "WHERE (CURSO_Cod_CURSO = ?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            Curso curso = new Curso();
            stmt.setInt(1, curso.getCursoActual());
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("problema en eliminarMatriculasCursoActual");
        }
    } // fin del método eliminarMatriculasCursoActual



    /** Metodo que se encarga de eliminar en la tabla "matricula" de la Base de Datos
     *  todos los registros del alumno que se quiere dar de baja.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param dni contiene el dni del alumno que se quiere dar de baja.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void borrarEnTablaMatricula(String dni, DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM matricula "
                                            + "WHERE (ALUMNO_DNI = ?)");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, dni);
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e1) {
    //        dataAccessObject.rollback();
            throw new RuntimeException("problema al eliminar en matricula");
        } 
    } // fin del método borrarEnTablaMatricula




    /** Método que comprueba si una matrícula está dada de alta en la Base de Datos.
     *
     * @param matricula contiene los datos de la matricula de la que se quiere
     *                  comprobar si está dada de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si la matrícula está dado de alta. 
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean estaDadaDeAlta(Matricula matricula, DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM matricula "
                                            + "WHERE ((ALUMNO_DNI = ?) "
                                                   + "AND (CURSO_Cod_CURSO = ?))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try{
            stmt.setString(1, matricula.getDNI());
            stmt.setInt(2, matricula.getCod_Curso());
            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            rs.close();
            stmt.close();
            return ok;
            } catch (SQLException ex) {
            throw new RuntimeException("problema en estaDadaDeAlta");
            }
    } // fin del método estaDadaDeAlta

} // fin de la clase ControladorMatricula