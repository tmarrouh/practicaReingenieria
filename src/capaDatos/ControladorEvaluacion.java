

package capaDatos;

import capaInterfaz.listados.ListadoEvaluacion;
import capaLogicaNegocio.Alumno;
import capaLogicaNegocio.Convocatoria;
import capaLogicaNegocio.Curso;
import capaLogicaNegocio.Evaluacion;
import capaLogicaNegocio.GrupoPractica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/** Clase que gestiona el acceso a la Base de Datos para
 * altas, bajas, modificaciones y consultas de evaluaciones.
 *
 * @author Confiencial
 */
public class ControladorEvaluacion {

    public ControladorEvaluacion () {
    }


    /** Método que actualiza en la Base de Datos, las calificaciones de los alumnos
     *  tras una modificación por parte del usuario (Módulo Modificar calificaciones).
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param resultado contiene las modificaciones realizadas.
     */
    public void actualizarNotasEvaluaciones (List<Evaluacion> resultado) {
        int tam = resultado.size();
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            for (int i = 0; i < tam; i++){
                this.actualizarNotasEvaluacion(resultado.get(i), dataAccessObject);
            }
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    } // fin del método actualizarNotasEvaluaciones


    /** Método que actualiza en la Base de Datos, las calificaciones de UN alumno
     *  tras una modificación por parte del usuario (Módulo Modificar calificaciones).
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param evaluacion contiene las modificaciones realizadas.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void actualizarNotasEvaluacion (Evaluacion evaluacion,
                                           DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("UPDATE EVALUACION "
                                            + "SET TIPO_EVALUACION_Ev_Continua = ?, "
                                            + "    PRACTICA_Cod_Practica = ?, "
                                            + "    EXAMEN_Cod_Ex = ?, "
                                            + "    Nota_Pr = ?, "
                                            + "    Nota_Ev_C = ?, "
                                            + "    Nota_P1 = ?, "
                                            + "    Nota_P2 = ?, "
                                            + "    Nota_P3 = ?, "
                                            + "    Nota_P4 = ?, "
                                            + "    Nota_Final = ?, "
                                            + "    Practica_convalidada = ?, "
                                            + "    Examen_convalidado = ? "
                                            + "WHERE (Cod_Evaluacion = ?) ");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setBoolean(1, evaluacion.getEs_Ev_Continua());
            stmt.setString(2, evaluacion.getCod_Practica());
            stmt.setString(3, evaluacion.getCod_Examen());
            stmt.setFloat(4, evaluacion.getNota_Practica());
            stmt.setFloat(5, evaluacion.getNota_Ev_C());
            stmt.setFloat(6, evaluacion.getNota_P1());
            stmt.setFloat(7, evaluacion.getNota_P2());
            stmt.setFloat(8, evaluacion.getNota_P3());
            stmt.setFloat(9, evaluacion.getNota_P4());
            stmt.setFloat(10, evaluacion.getNota_Final());
            stmt.setBoolean(11, evaluacion.getPracticaConvalidada());
            stmt.setBoolean(12, evaluacion.getExamenConvalidado());
            stmt.setInt(13, evaluacion.codEvaluacion());

            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException ("Problema en actualizarNotasEvaluacion");
        }
    } // fin del método actualizarNotasEvaluaciones


    /** Método que se encarga de insertar los datos de la evaluación
     *  en la Base de Datos.
     *  Si ocurre algún error, se lanzará una excepción.
     *
     * @param evaluacion contiene los datos de la evaluación que se quiere dar de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     */
    public void intertarEnTablaEvaluacion(Evaluacion evaluacion,
                                  DataAccessObject dataAccessObject) {

        
        StringBuilder sql = new StringBuilder("INSERT EVALUACION (Cod_Evaluacion, "
                                                               + "ALUMNO_DNI, "
                                                               + "TIPO_EVALUACION_Ev_Continua, "
                                                               + "PRACTICA_Cod_Practica, "
                                                               + "EXAMEN_Cod_Ex, "
                                                               + "Nota_Pr, "
                                                               + "Nota_Ev_C, "
                                                               + "Nota_P1, "
                                                               + "Nota_P2, "
                                                               + "Nota_P3, "
                                                               + "Nota_P4, "
                                                               + "Nota_Final, "
                                                               + "CURSO_Cod_CURSO, "
                                                               + "CONVOCATORIA_idCONVOCATORIA, "
                                                               + "Practica_convalidada, "
                                                               + "Examen_convalidado) "
                                            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        List<Object> values = new ArrayList<Object>();
        values.add(evaluacion.getNuevoCodEvaluacion());
        values.add(evaluacion.getDNI());
        values.add(evaluacion.getEs_Ev_Continua());
        values.add(evaluacion.getCod_Practica());
        values.add(evaluacion.getCod_Examen());
        values.add(evaluacion.getNota_Practica());
        values.add(evaluacion.getNota_Ev_C());
        values.add(evaluacion.getNota_P1());
        values.add(evaluacion.getNota_P2());
        values.add(evaluacion.getNota_P3());
        values.add(evaluacion.getNota_P4());
        values.add(evaluacion.getNota_Final());
        values.add(evaluacion.getCod_Curso());
        values.add(evaluacion.getCod_Convocatoria());
        values.add(evaluacion.getPracticaConvalidada());
        values.add(evaluacion.getExamenConvalidado());

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            int i=0;
            for (Object valor: values) {
                if (valor instanceof String){
                    stmt.setString(++i, (String)valor);
                }
                if (valor instanceof Float){
                    stmt.setFloat(++i, (Float)valor);
                }
                if (valor instanceof Integer){
                    stmt.setInt(++i, (Integer)valor);
                }
                if (valor instanceof Boolean){
                    stmt.setBoolean(++i, (Boolean)valor);
                }   
            }

            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Problema en insertarEnTablaEvaluacion");
        }
    } // fin del método intertarEnTablaEvaluacion



    /** Método que elimina de la Base de Datos TODAS las evaluaciones registradas
     *  para la convocatoria actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void eliminarEvaluacionesConvocatoriaActual(DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM evaluacion "
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
    } // fin del método eliminarEvaluacionesConvocatoriaActual



    /** Método que comprueba si una evaluación está dada de alta en la Base de Datos.
     *
     * @param evaluacion contiene los datos de la evaluación de la que se quiere
     *                   comprobar si está dada de alta.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si la matrícula está dada de alta.
     *         FALSE si la matrícula no está dada de alta.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean estaDadaDeAlta(Evaluacion evaluacion, DataAccessObject dataAccessObject) {
        
        StringBuilder sql = new StringBuilder(
                     "SELECT * "
                   + "FROM evaluacion "
                   + "WHERE ((ALUMNO_DNI = ?) AND (CURSO_Cod_CURSO = ?) "
                   + "AND (CONVOCATORIA_idCONVOCATORIA = ?))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try{
            stmt.setString(1, evaluacion.getDNI());
            stmt.setInt(2, evaluacion.getCod_Curso());
            stmt.setString(3, evaluacion.getCod_Convocatoria());
            ResultSet rs = stmt.executeQuery();

            boolean ok = rs.next();
            stmt.close();

            return ok;
        }
        catch (SQLException ex) {
            throw new RuntimeException("Problema al comprobar que la evaluacion esta dada de alta");
        }
    } // fin del método estaDadaDeAlta


    /** Método que consulta en la Base de Datos las claves de las evaluaciones
     *  y devuelve la mayor de ellas.
     *
     * @return Si no hay ninguna evaluación registrada, devolverá cero.
     *         Si hay evaluaciones registradas, devolverá la mayor de las claves.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public int recuperarUltimoCodEvaluacion() {

        int ultimo_cod = 0;
        StringBuilder sql = new StringBuilder (
                     "SELECT Cod_Evaluacion "
                   + "FROM evaluacion "
                   + "WHERE (Cod_Evaluacion >= ALL (SELECT Cod_Evaluacion"
                                                + " FROM evaluacion))");

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        
        try {
            ResultSet rs = stmt.executeQuery();
            

            if (rs.next()) {
                ultimo_cod = rs.getInt(1);
            }
            rs.close();
            dataAccessObject.close();
            return ultimo_cod;
        }
        catch (SQLException ex) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema al consultar la clave de la última evaluación");
        }
    } // fin del método recuperarUltimoCodEvaluacion



    /** Método que comprueba que si el alumno ha aprobado el examen en la última
     *  convocatoria cursada.
     *
     * @param dni_alumno contiene el DNI del alumno.
     * @param curso contiene el curso en el que se va a comprobar si tiene
     *        el examen aprobado.
     * @param convocatoria_anterior contiene la convocatoria en la que
     *        se va a comprobar si tiene el examen aprobado.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si tiene el examen aprobado. 
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean tieneExamenAprobado(String dni_alumno, int curso,
                                       String convocatoria_anterior,
                                       DataAccessObject dataAccessObject) {
        boolean ok;
        StringBuilder sql = new StringBuilder (
                        "SELECT * "
                      + "FROM evaluacion "
                      + "WHERE ((ALUMNO_DNI = ?) AND (CURSO_Cod_CURSO = ?) "
                      + "AND (CONVOCATORIA_idCONVOCATORIA = ?) "
                      + "AND ((Nota_P1 + Nota_P2 + Nota_P3 + Nota_P4) >= 5))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, dni_alumno);
            stmt.setInt(2, curso);
            stmt.setString(3, convocatoria_anterior);

            ResultSet rs = stmt.executeQuery();
            ok = rs.next();
            rs.close();
            stmt.close();
            return ok;
        }
        catch (SQLException ex) {
  //          dataAccessObject.rollback();
            throw new RuntimeException("Problema al comprobar que el alumno"
                               + "tiene el examen aprobado en al conv. anterior");
        }
    } // fin del método tieneExamenAprobado



    /** Método que comprueba que si el alumno ha aprobado la práctica en la última
     *  convocatoria cursada.
     *
     * @param dni_alumno contiene el DNI del alumno.
     * @param curso contiene el curso en el que se va a comprobar si tiene
     *        la práctica aprobada.
     * @param convocatoria_anterior contiene la convocatoria en la que
     *        se va a comprobar si tiene la práctica aprobada.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si tiene la práctica aprobada. 
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean tienePracticaAprobada(String dni_alumno, int curso,
                                       String convocatoria_anterior,
                                       DataAccessObject dataAccessObject) {
        boolean ok;
        StringBuilder sql = new StringBuilder (
                        "SELECT * "
                      + "FROM evaluacion "
                      + "WHERE ((ALUMNO_DNI = ?) AND (CURSO_Cod_CURSO = ?) "
                      + "AND (CONVOCATORIA_idCONVOCATORIA = ?) "
                      + "AND (Nota_Pr >= 5))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, dni_alumno);
            stmt.setInt(2, curso);
            stmt.setString(3, convocatoria_anterior);

            ResultSet rs = stmt.executeQuery();
            ok = rs.next();
            rs.close();
            stmt.close();
            return ok;
        }
        catch (SQLException ex) {
  //          dataAccessObject.rollback();
            throw new RuntimeException("Problema al comprobar que el alumno"
                               + "tiene la práctica aprobada en al conv. anterior");
        }
    } // fin del método tienePracticaAprobada


    /** Método que comprueba en la Base de Datos si un alumno tiene el examen
     *  aprobado en la última convocatoria cursada. En caso afirmativo,
     *  devuelve la nota obtenida en el problema indicado por parámetro.
     *
     * @param dni_alumno contiene el DNI del alumno.
     * @param num_problema contiene el número de problema del que queremos obtener la nota.
     * @param curso contiene el curso en el que vamos a comprobar la nota obtenida.
     * @param convocatoria_anterior contiene la convocatoria en la que vamos a
     *        comprobar la nota obtenida.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return Si tiene el examen aprobado, devolverá la nota obtenida en el
     *         problema del examen recibido por parámetro.
     *         Si no tiene el examen aprobado, devolverá 0.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public float obtenerNotaPiExamen(String dni_alumno, int num_problema, int curso,
                                     String convocatoria_anterior,
                                     DataAccessObject dataAccessObject) {
        float nota = 0;
        StringBuilder sql = new StringBuilder(
                "SELECT Nota_P1, Nota_P2, Nota_P3, Nota_P4 "
              + "FROM evaluacion "
              + "WHERE ((ALUMNO_DNI = ?) AND (CURSO_Cod_CURSO = ?) "
                      + "AND (CONVOCATORIA_idCONVOCATORIA = ?)"
                      + "AND ((Nota_P1 + Nota_P2 + Nota_P3 + Nota_P4) >= 5))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, dni_alumno);
            stmt.setInt(2, curso);
            stmt.setString(3, convocatoria_anterior);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nota = rs.getFloat(num_problema);
			}
                rs.close();
                stmt.close();
                return nota;
        }
        catch (SQLException ex) {
//            dataAccessObject.rollback();
            throw new RuntimeException("Problema al obtener la nota de un problema del examen");
        }
    } // fin del método obtenerNotaPiExamen




    /** Método que comprueba en la Base de Datos si un alumno tiene la práctica
     *  aprobada en el curso y convocatoria recibidos por parámetro.
     *  En caso afirmativo, devuelve la nota obtenida.
     *
     * @param dni_alumno contiene el DNI del alumno.
     * @param curso contiene el curso en el que vamos a comprobar la nota obtenida.
     * @param convocatoria contiene la convocatoria en la que vamos a
     *        comprobar la nota obtenida.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return Si tiene la práctica aprobada, devolverá la nota obtenida.
     *         Si no tiene la práctica aprobada, devolverá 0.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public float obtenerNotaPractica(String dni_alumno,
                                     int curso, String convocatoria,
                                     DataAccessObject dataAccessObject) {

        float nota = 0;
        StringBuilder sql = new StringBuilder(
                "SELECT Nota_Pr "
              + "FROM evaluacion "
              + "WHERE ((ALUMNO_DNI = ?) AND (CURSO_Cod_CURSO = ?) "
                      + "AND (CONVOCATORIA_idCONVOCATORIA = ?)"
                      + "AND (Nota_Pr >= 5))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, dni_alumno);
            stmt.setInt(2, curso);
            stmt.setString(3, convocatoria);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nota = rs.getFloat(1);
			}
                rs.close();
                stmt.close();
                return nota;
        }
        catch (SQLException ex) {
//            dataAccessObject.rollback();
            throw new RuntimeException("Problema al obtener la nota de la práctica");
        }
    } // fin del método obtenerNotaPractica


    /** Método que actualiza el grupo de prácticas y la nota de prácticas para
     *  un alumno. Si ocurre algún error, lanzará una excepción.
     *
     * @param alumno contiene la información del alumno.
     * @param cod_grupo_practica contiene el código del grupo de prácticas
     * @param nota contiene la nota de prácticas.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void actualizarPracticaEnTablaEvaluacion(Alumno alumno, 
                                                    int cod_grupo_practica,
                                                    float nota,
                                                    DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("UPDATE EVALUACION "
                                            + "SET GRUPO_PRACTICA_Cod_GP = ?,"
                                            + "    Nota_Pr = ? "
                                            + "WHERE ((ALUMNO_DNI = ?)"
                                            + "  AND (CURSO_Cod_CURSO = ?)"
                                            + "  AND (CONVOCATORIA_idCONVOCATORIA = ?))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        Curso curso = new Curso ();
        Convocatoria convocatoria = new Convocatoria();
        try {
            stmt.setInt(1, cod_grupo_practica);
            stmt.setFloat(2, nota);
            stmt.setString(3, alumno.getDNI());
            stmt.setInt(4, curso.getCursoActual());
            stmt.setString(5, convocatoria.getConvocatoriaActual());

            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException ex) {
            throw new RuntimeException ("Problema en actualizarPracticaEnTablaEvaluacion");
        }
    } //  fin del método actualizarPracticaEnTablaEvaluacion



    /** Metodo que se encarga de eliminar en la tabla "evaluacion" de la Base de Datos
     *  todos los registros del alumno que se quiere dar de baja.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param dni contiene el dni del alumno que se quiere dar de baja.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void borrarEnTablaEvaluacion(String dni, DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM evaluacion "
                                            + "WHERE (ALUMNO_DNI = ?)");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, dni);
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e1) {
//            dataAccessObject.rollback();
            throw new RuntimeException("problema al eliminar en evaluacion");
        }
    } // fin del método borrarEnTablaEvaluacion



    /** Método que elimina del registro de evaluación de un alumno, el grupo de prácticas
     *  y la correspondiente nota.
     *
     * @param dni dni del alumno
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void borrarGrupoPracticaEnTablaEvaluacion(String dni,
                                                     DataAccessObject dataAccessObject) {
        System.out.println("Estoy en borrarGrupoPracticaEnTablaEvaluacion");
        StringBuilder sql = new StringBuilder("UPDATE EVALUACION "
                                            + "SET GRUPO_PRACTICA_Cod_GP = null,"
                                            + "    Nota_Pr = 0 "
                                            + "WHERE ((CURSO_Cod_CURSO = ?) "
                                            + "   AND (CONVOCATORIA_idCONVOCATORIA = ?)"
                                            + "   AND (ALUMNO_DNI = ?))");

        try {
            Curso curso = new Curso();
            Convocatoria convocatoria = new Convocatoria();
            PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
            stmt.setInt(1, curso.getCursoActual());
            stmt.setString(2, convocatoria.getConvocatoriaActual());
            stmt.setString(3, dni);
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("borrarGrupoPracticaEnTablaEvaluacion");
            throw new RuntimeException("Error al actualizar la Base de Datos.");
        }
    } // fin del método borrarGrupoPracticaEnTablaEvaluacion


    /** Método que comprueba si un grupo de prácticas está registrado en alguna
     *  de las evaluaciones de los alumnos.
     *  Si ocurre algún error, devolverá una excepción.
     *
     * @param grupo contiene la información del grupo de prácticas.
     *
     * @return TRUE si existe en al menos una evaluación.
     *         FALSE si no existe en ninguna evaluación.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean estaRegistradoGrupoPractica(GrupoPractica grupo) {

        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM evaluacion "
                                            + "WHERE ((GRUPO_PRACTICA_Cod_GP = ?) "
                                             + " AND (CURSO_Cod_CURSO = ?) "
                                             + " AND (CONVOCATORIA_idCONVOCATORIA = ?))");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            Curso curso = new Curso();
            Convocatoria convocatoria = new Convocatoria();

            stmt.setInt(1, grupo.getCodGrupoPractica());
            stmt.setString(2, curso.getCursoActualEnString());
            stmt.setString(3, convocatoria.getConvocatoria());

            ResultSet rs = stmt.executeQuery();

            boolean ok = rs.next();
            dataAccessObject.close();
            rs.close();
            return ok;
        } catch (SQLException e) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en estaRegistradoGrupoPractica de evaluacion");
        }
    } // fin del método estaRegistradoGrupoPractica


    
    /** Método que comprueba si un alumno ya tiene asignado un grupo de prácticas
     *  en el curso y la convocatoria actuales.
     *
     * @param alumno alumno del cual se quiere comprobar si ya tiene asignado un grupo de prácticas.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si el alumno ya tiene asignado un grupo.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean alumnoTieneGrupoPractica(Alumno alumno, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder(" SELECT GRUPO_PRACTICA_Cod_GP "
                                            + " FROM evaluacion "
                                            + " WHERE ((ALUMNO_DNI = ?) "
                                                + " AND (CURSO_Cod_CURSO = ?) "
                                                + " AND (CONVOCATORIA_idCONVOCATORIA = ?))");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            Curso curso = new Curso();
            Convocatoria convocatoria = new Convocatoria();

            stmt.setString(1, alumno.getDNI());
            stmt.setInt(2, curso.getCursoActual());
            stmt.setString(3, convocatoria.getConvocatoriaActual());

            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            System.out.println(ok);
            int cod_grupo = rs.getInt(1);
            System.out.println(cod_grupo);
            rs.close();
            stmt.close();

            return (cod_grupo != 0);
        } catch (SQLException e) {
            throw new RuntimeException (" Ha ocurrido un error al comprobar el si el alumno "
                                       +"ya tiene un grupo de prácticas asignado");
        }
    } // fin del método alumnoTieneGrupoPractica


    /** Método que devuelve el código del examen que tiene convalidado un alumno.
     *  Sólo comprobará la última convocatoria cursada, ya que el código del examen
     *  se va actualizando en cada cambio de convocatoria.
     *
     * @param dni_alumno dni del alumno que tiene convalidado el examen.
     * @param curso curso correspondiente a la última convocatoria cursada
     * @param convocatoria_anterior última convocatoria cursada.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return devolverá el código del examen convalidado.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public String obtenerCodExamenAprobado(String dni_alumno, int curso,
                                           String convocatoria_anterior,
                                           DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder(" SELECT EXAMEN_Cod_Ex "
                                            + " FROM evaluacion "
                                            + " WHERE ((ALUMNO_DNI = ?) "
                                                + " AND (CURSO_Cod_CURSO = ?) "
                                                + " AND (CONVOCATORIA_idCONVOCATORIA = ?))");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, dni_alumno);
            stmt.setInt(2, curso);
            stmt.setString(3, convocatoria_anterior);

            ResultSet rs = stmt.executeQuery();
            String resultado;
            if (rs.next()) {
                resultado = rs.getString(1);
            } else {
                resultado = null;
            }
            
            stmt.close();
            rs.close();
            return  resultado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException (" Ha ocurrido un error al comprobar el código"
                                      + " del examen convalidado");
        }
    } // fin del método obtenerCodExamenAprobado


    /** Método que devuelve el código del examen que tiene convalidado un alumno.
     *  Sólo comprobará la última convocatoria cursada, ya que el código del examen
     *  se va actualizando en cada cambio de convocatoria.
     *
     * @param dni_alumno dni del alumno que tiene convalidado el examen.
     * @param curso curso correspondiente a la última convocatoria cursada
     * @param convocatoria_anterior última convocatoria cursada.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return devolverá el código del examen convalidado.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public String obtenerCodPracticaAprobada(String dni_alumno, int curso,
                                             String convocatoria_anterior,
                                             DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder(" SELECT PRACTICA_Cod_Practica "
                                            + " FROM evaluacion "
                                            + " WHERE ((ALUMNO_DNI = ?) "
                                                + " AND (CURSO_Cod_CURSO = ?) "
                                                + " AND (CONVOCATORIA_idCONVOCATORIA = ?))");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, dni_alumno);
            stmt.setInt(2, curso);
            stmt.setString(3, convocatoria_anterior);

            ResultSet rs = stmt.executeQuery();

            String resultado;

            if (rs.next()) {
                resultado = rs.getString(1);
            } else {
                resultado = null;
            }
            stmt.close();
            rs.close();
            return  resultado;
        } catch (SQLException e) {
            throw new RuntimeException (" Ha ocurrido un error al comprobar el código"
                                      + " de la práctica convalidada");
        }
    } // fin del método obtenerCodPracticaAprobada


    /** Método que realiza la consulta solicitada a la Base de Datos.
     *
     * @param dni dni del alumno por el que se quiere filtrar la consulta.
     *        Puede ser null.
     * @param n_mat nº de matrícula del alumnos por el que se quiere filtrar
     *        la consulta. Puede ser null.
     * @param grupo_clase grupo de clase por el que se quiere filtrar la consulta.
     *        Puede ser null.
     * @param convocatoria convocatoria por la que se quiere filtrar la consulta.
     *        NO puede ser null. Si la convocatoria actual es ordinaria, necesariamente
     *        el filtro tomará el valor 'ordinaria'. Si la convocatoria actual es
     *        extraordinaria, podrá tomar los valores 'ordinaria' o 'extraordinaria'.
     * @param curso curso por la que se quiere filtrar la consulta. Puede ser null.
     *
     * @return Si la ejecución ha sido correcta, devuelve el resultado de la consulta.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public List<ListadoEvaluacion> realizarConsultarEvaluacion(String dni,
                                                               String n_mat,
                                                               String grupo_clase,
                                                               String convocatoria,
                                                               int curso) {
        boolean filtro_curso = false;
        boolean filtro_convocatoria = false;
        StringBuilder sql = new StringBuilder("SELECT evaluacion.Cod_Evaluacion, "
                                                  + " evaluacion.ALUMNO_DNI, "
                                                  + " evaluacion.TIPO_EVALUACION_Ev_Continua, "
                                                  + " evaluacion.Nota_Pr, "
                                                  + " evaluacion.Nota_Ev_C, "
                                                  + " evaluacion.Nota_P1, "
                                                  + " evaluacion.Nota_P2, "
                                                  + " evaluacion.Nota_P3, "
                                                  + " evaluacion.Nota_P4, "
                                                  + " evaluacion.Nota_Final, "
                                                  + " evaluacion.Practica_convalidada, "
                                                  + " evaluacion.Examen_convalidado, "
                                                  + " evaluacion.CONVOCATORIA_idCONVOCATORIA, "
                                                  + " evaluacion.CURSO_Cod_CURSO, "
                                                  + " evaluacion.EXAMEN_Cod_Ex, "
                                                  + " evaluacion.PRACTICA_Cod_Practica, "
                                                  + " alumno.N_Mat "
                                            + "FROM evaluacion, alumno, matricula "
                                            + "WHERE ((evaluacion.ALUMNO_DNI = alumno.DNI) "
                                            + " AND (evaluacion.ALUMNO_DNI = matricula.ALUMNO_DNI) "
                                            + " AND (evaluacion.CURSO_Cod_CURSO = matricula.CURSO_Cod_CURSO) ");


        List<Object> values = new ArrayList<Object>();
        List<ListadoEvaluacion> resultado = new ArrayList<ListadoEvaluacion>();


        if (this.noEstaVacio(convocatoria)) {
            filtro_convocatoria = true;
            sql.append(" AND (evaluacion.CONVOCATORIA_idCONVOCATORIA = ?) ");
            values.add(convocatoria);            
        }

        if (curso != -1) {
            filtro_curso = true;
            sql.append( " AND (evaluacion.CURSO_Cod_CURSO = ?) ");
            values.add(curso);
        }

        if (this.noEstaVacio(dni)) {
            sql.append(" AND (evaluacion.ALUMNO_DNI = ?) ");
            values.add(dni);
        }

        if (this.noEstaVacio(n_mat)) {
            sql.append(" AND (evaluacion.ALUMNO_DNI = (SELECT DNI "
                                                   + " FROM alumno "
                                                   + " WHERE (N_Mat = ?))) ");
            values.add(n_mat);
        }

        if (this.noEstaVacio(grupo_clase)) {
            sql.append(" AND (evaluacion.ALUMNO_DNI IN (SELECT ALUMNO_DNI "
                                                     +" FROM matricula "
                                                     +" WHERE (matricula.GRUPO_CLASE_Cod_GC = ?))) ");
            values.add(grupo_clase);
        }

        sql.append(" ) ");
        sql.append(" ORDER BY ");

        
        if (!filtro_curso){
            sql.append(" CURSO_Cod_CURSO DESC, ");
        }

        if (!filtro_convocatoria) {
            sql.append(" CONVOCATORIA_idCONVOCATORIA DESC, ");
        }

        sql.append(" alumno.N_Mat ASC ");

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            System.out.println(sql.toString());
            int i=0;
            for (Object valor: values) {
                if (valor instanceof Integer) {
                    stmt.setInt(++i, (Integer)valor);
                }

                if (valor instanceof String) {
                    stmt.setString(++i, (String)valor);
                }
            }
            ResultSet rs = stmt.executeQuery();
            boolean ev_continua;
            Alumno alumno;
            Evaluacion evaluacion;
            while (rs.next()) {
                alumno = new Alumno(rs.getString(2));
                alumno.obtenerDatosAlumno(dataAccessObject);

                ev_continua = (rs.getInt(3) == 1);
                evaluacion = new Evaluacion (rs.getInt(1), rs.getString(2),
                                             rs.getString(17), ev_continua,
                                             rs.getFloat(4), rs.getFloat(5),
                                             rs.getFloat(6), rs.getFloat(7),
                                             rs.getFloat(8), rs.getFloat(9),
                                             rs.getFloat(10), rs.getBoolean(11),
                                             rs.getBoolean(12), rs.getString(13),
                                             rs.getInt(14), rs.getString(15),
                                             rs.getString(16));
                resultado.add(new ListadoEvaluacion(alumno, evaluacion));
            }
            rs.close();
            dataAccessObject.close();
            return resultado;
        } catch (SQLException e1) {
            dataAccessObject.rollback();
            System.out.println(e1.getMessage());
            throw new RuntimeException("Problema en realizarConsultaEvaluacion");
        } catch (RuntimeException e2) {
            dataAccessObject.rollback();
            System.out.println(e2.getMessage());
            throw new RuntimeException("Problema en realizarConsultaEvaluacion");
        }
    } // fin del método realizarConsultaEvaluacion


    /** Método que comprueba si una cadena no está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si está vacía.
     *
     * @return TRUE si la cadena no está vacía.
     *         FALSE en caso contrario.
     */
    public boolean noEstaVacio(String cadena) {
	return cadena != null && !"".equals(cadena);
    }

} // fin de la clase ControladorEvaluacion