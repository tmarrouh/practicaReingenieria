

package capaDatos;

import capaInterfaz.listados.ListadoAlumno;
import capaInterfaz.listados.ListadoEvaluacion;
import capaInterfaz.listados.ListadoHistoricoAlumno;
import capaLogicaNegocio.Alumno;
import capaLogicaNegocio.Curso;
import capaLogicaNegocio.Evaluacion;
import capaLogicaNegocio.GrupoClase;
import capaLogicaNegocio.Matricula;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/** Clase que gestiona el acceso a la Base de Datos para
 * altas, bajas, modificaciones y consultas de alumnos
 *
 * @author Confiencial
 */
public class ControladorAlumno {

    public ControladorAlumno () {

    }

    /** Método que comprueba si un alumno está dado de alta en la Base de Datos.
     *
     * @param alumno Contiene los datos del alumno del que se va a comprobar
     *        si está dado de alta.
     *
     * @return TRUE si el alumno está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, se lanzará una excepción.
     */
    public boolean estaDadoDeAlta(Alumno alumno) {

        StringBuilder sql = new StringBuilder("SELECT DNI "
                                           + "FROM alumno "
                                           + "WHERE (DNI = ?)");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, alumno.getDNI());

            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            rs.close();
            dataAccessObject.close();
            return ok;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en estaDadoDeAlta");
        }
    } // fin del método estaDadoDeAlta


    /** Método que comprueba si un alumno está dado de alta en la Base de Datos
     *  en el curso actual.
     *
     * @param alumno Contiene los datos del alumno del que se va a comprobar
     *        si está dado de alta.
     *
     * @return TRUE si el alumno está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, se lanzará una excepción.
     */
    public boolean estaDadoDeAltaEnCursoActual(Alumno alumno) {
        StringBuilder sql = new StringBuilder("SELECT ALUMNO_DNI "
                                           + "FROM matricula "
                                           + "WHERE ((ALUMNO_DNI = ?) "
                                           + "   AND (CURSO_Cod_CURSO = ?))");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            Curso curso = new Curso();
            stmt.setString(1, alumno.getDNI());
            stmt.setInt(2, curso.getCursoActual());
            
            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            rs.close();
            dataAccessObject.close();
            return ok;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en estaDadoDeAltaEnCursoActual");
        }
    } // fin del método estaDadoDeAltaEnCursoActual



    /** Método que comprueba si un alumno no está dado de alta en la Base de Datos.
     *
     * @param alumno Contiene los datos del alumno del que se va a comprobar
     *        si no está dado de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     * 
     * @return TRUE si el alumno no está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, se lanzará una excepción.
     */
    public boolean noEstaDadoDeAlta(Alumno alumno, DataAccessObject dataAccessObject) {
        return (! this.estaDadoDeAlta(alumno,dataAccessObject));
    } // fin del método noEstaDadoDeAlta



    /** Método que comprueba si un nº de matrícula está dado de alta en la
     *  Base de Datos.
     *
     * @param alumno Contiene los datos del alumno del que se va a comprobar
     *        si está dado de alta.
     *
     * @return TRUE si el nº de matrícula está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, se lanzará una excepción.
     */
    public boolean numMatriculaEstaDadaDeAlta(Alumno alumno) {

        StringBuilder sql = new StringBuilder("SELECT N_Mat "
                                           + "FROM alumno "
                                           + "WHERE (N_Mat = ?)");

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, alumno.getN_Mat());

            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            rs.close();
            dataAccessObject.close();
            return ok;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en numMatriculaEstaDadaDeAlta");
        }
    } // fin del método numMatriculaEstaDadaDeAlta


    /** Método que comprueba si un nº de matrícula está dado de alta en la
     *  Base de Datos en el curso actual.
     *
     * @param alumno Contiene los datos del alumno del que se va a comprobar
     *        si está dado de alta.
     *
     * @return TRUE si el nº de matrícula está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, se lanzará una excepción.
     */
    public boolean numMatriculaEstaDadaDeAltaEnCursoActual(Alumno alumno) {
        StringBuilder sql = new StringBuilder("SELECT alumno.N_Mat "
                                           + "FROM alumno, matricula "
                                           + "WHERE ((alumno.N_Mat = ?) "
                                           + "   AND (matricula.ALUMNO_DNI = alumno.DNI) "
                                           + "   AND (matricula.CURSO_Cod_CURSO = ?))");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            Curso curso = new Curso();
            stmt.setString(1, alumno.getN_Mat());
            stmt.setInt(2, curso.getCursoActual());

            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            rs.close();
            dataAccessObject.close();
            return ok;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en numMatriculaEstaDadaDeAltaEnCursoActual");
        }
    } // fin del método numMatriculaEstaDadaDeAltaEnCursoActual


    /** Método que comprueba si un alumno está dado de alta en la Base de Datos.
     *
     * @param alumno Contiene los datos del alumno del que se va a comprobar
     *        si está dado de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si el alumno está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, se lanzará una excepción.
     */
    public boolean estaDadoDeAlta(Alumno alumno, DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("SELECT DNI "
                                           + "FROM alumno "
                                           + "WHERE (DNI = ?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, alumno.getDNI());

            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            rs.close();
            return ok;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Problema en estaDadoDeAlta");
        }
    } // fin del método estaDadoDeAlta



    /** Método que se encarga de insertar los datos del alumno en la Base de Datos.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param alumno contiene los datos del alumno que se quiere dar de alta
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.         
     */
    public void intertarEnTablaAlumno (Alumno alumno, DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("INSERT ALUMNO VALUES (?,?,?,?)");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, alumno.getDNI());
            stmt.setString(2, alumno.getN_Mat());
            stmt.setString(3, alumno.getNombre());
            stmt.setString(4, alumno.getApellidos());
            System.out.println(alumno.getDNI());
            System.out.println(alumno.getN_Mat());
            System.out.println(alumno.getNombre());
            System.out.println(alumno.getApellidos());
            dataAccessObject.actualizar();

            stmt.close();

        } catch (SQLException ex) {
      //      dataAccessObject.rollback();
            throw new RuntimeException("Problema en insertarEnTablaAlumno al insertar alumno");
         }
    } // fin del método intertarEnTablaAlumno


    /** Método que se encarga de insertar los datos del alumno en la Base de Datos
     * además de darle de alta en el curso actual y crearle una evaluación.
     * Si ocurre algún error, se lanzará una excepción.
     *
     * @param alumno contiene la información del alumno que se quiere dar de alta.
     * @param matricula contiene la información de la matriculación del alumno
     *        en el curso actual.
     * @param  evaluacion contiene la información de la evaluación del alumno
     *         en el curso y la convocatoria actuales.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void darAltaAlumnoConvocatoriaOrdinaria(Alumno alumno,
                                                   Matricula matricula,
                                                   Evaluacion evaluacion,
                                                   DataAccessObject dataAccessObject){
        boolean repetidor = false;

        if (this.noEstaDadoDeAlta(alumno, dataAccessObject)) {
            this.intertarEnTablaAlumno(alumno, dataAccessObject);
        } else {
            repetidor = true;
        }

        matricula.altaMatricula(dataAccessObject);

        if (repetidor) {
            evaluacion.actualizarNotasAprobadasConvocatoriaAnterior(dataAccessObject);
        }
        evaluacion.altaEvaluacion(dataAccessObject);
    } // fin del método darAltaAlumnoConvocatoriaOrdinaria


    /** Método que se encarga de dar de alta al alumno en la convocatoria
     *  extraordinaria.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param  evaluacion contiene la información de la evaluación del alumno
     *         en el curso y la convocatoria actuales.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void darAltaAlumnoConvocatoriaExtraordinaria (Evaluacion evaluacion,
                                                         DataAccessObject dataAccessObject) {
        evaluacion.actualizarNotasAprobadasConvocatoriaAnterior(dataAccessObject);
        evaluacion.altaEvaluacion(dataAccessObject);
    } // fin del método darAltaAlumnoConvocatoriaExtraordinaria


    /** Método que se encarga de eliminar los datos del alumno en la Base de Datos.
     *  Eliminará cualquier registro de las matrículas realizadas, las evaluaciones
     *  cursadas, las tutorías obtenidas y el propio registro de los datos del
     *  alumno. Si ocurre algún error, lanzará una excepción.
     *
     * @param alumno contiene los datos del alumno al que se quiere dar de baja.
     */
     public void darBajaAlumno(Alumno alumno) {

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();

        try {
        ControladorEvaluacion DAOEvaluacion = new ControladorEvaluacion();
        DAOEvaluacion.borrarEnTablaEvaluacion(alumno.getDNI(), dataAccessObject);
        
        ControladorMatricula DAOMatricula = new ControladorMatricula();
        DAOMatricula.borrarEnTablaMatricula(alumno.getDNI(), dataAccessObject);

        this.borrarEnTablaAlumno(alumno.getDNI(), dataAccessObject);

        dataAccessObject.close();
         } catch (RuntimeException e) {
             dataAccessObject.rollback();
             throw new RuntimeException (e.getMessage());
         }
    } // fin del método darBajaAlumno
   
    
    /** Metodo que se encarga de eliminar en la tabla "alumno" de la Base de Datos
     *  todos los registros del alumno que se quiere dar de baja.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param dni contiene el dni del alumno que se quiere dar de baja.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void borrarEnTablaAlumno(String dni, DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM alumno "
                                            + "WHERE (DNI = ?)");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setString(1, dni);
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e1) {
   //         dataAccessObject.rollback();
            throw new RuntimeException("problema al eliminar en alumno");
        }
    } // fin del método borrarEnTablaAlumno


    /** Método que comprueba las notas obtenidas por los alumnos en convocatoria
     *  ordinaria y, posteriormente, da de alta en la convocatoria extraordinaria
     *  a aquellos alumnos suspensos en convocatoria ordinaria.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param resultado contiene todas las evaluaciones de los alumnos matriculados
     *        en el curso actual.
     */
    public void cambioAConvocatoriaExtraordinaria(List<ListadoEvaluacion> resultado) {
        if (! resultado.isEmpty()){
            DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
            Alumno alumno;
            String convocatoria = "extraordinaria";
            int curso = resultado.get(0).getEvaluacion().getCod_Curso();
            Evaluacion evaluacion_nueva, evaluacion_anterior;
            try {
                for (int i = 0; i < resultado.size(); i++){
                    alumno = resultado.get(i).getAlumno();
                    evaluacion_anterior = resultado.get(i).getEvaluacion();
                    evaluacion_nueva = new Evaluacion(alumno.getDNI(), curso,
                                                      convocatoria);
                    System.out.println("Checkpoint 1");
                    if (evaluacion_anterior.getNota_Final() < ((float)5.0)) {
                        System.out.println("Checkpoint 2");
                        this.darAltaAlumnoConvocatoriaExtraordinaria(evaluacion_nueva,
                                                                     dataAccessObject);
                        System.out.println("Checkpoint 3");
                    }
                }
                dataAccessObject.close();
            } catch (RuntimeException e) {
                dataAccessObject.rollback();
                throw new RuntimeException("Ha ocurrido un error durante la actualización. cambioAConvocatoriaExtraordinaria");
            }
        }
    } // fin del método cambioAConvocatoriaExtraordinaria



    /** Método que realiza la consulta solicitada a la Base de Datos.
     *
     * @param alumno contiene la información de alumno por la que se quiere
     *        filtrar la consulta. Puede ser null.
     * @param grupo_clase contiene la información de grupo de clase por la que
     *        se quiere filtrar la consulta. Puede ser null.
     *
     * @return Si la ejecución es correcta, devolverá el resultado de la consulta.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public List<ListadoAlumno> realizarConsultaAlumno(Alumno alumno,
                                                      GrupoClase grupo_clase) {

        boolean primeraClausulaWhere = true;
        
        StringBuilder sql = new StringBuilder(" SELECT alumno.DNI, alumno.N_Mat, "
                                                   + " alumno.Nombre, "
                                                   + " alumno.Apellidos, "
                                                   + " matricula.GRUPO_CLASE_Cod_GC "
                                            + " FROM alumno, matricula ");

        List<Object> values = new ArrayList<Object>();
        
        if (this.noEstaVacio(alumno.getDNI())){
            sql.append("WHERE ((alumno.DNI = ?) ");
            values.add(alumno.getDNI());
            primeraClausulaWhere = false;
        }
        
        if (this.noEstaVacio(alumno.getN_Mat())) {
            if (! primeraClausulaWhere) {
                sql.append(" AND ");
            } else {
                sql.append("WHERE (");
            }
            sql.append(" (alumno.N_Mat = ?) ");
            values.add(alumno.getN_Mat());
            primeraClausulaWhere = false;
        }

        if (this.noEstaVacio(grupo_clase.getCodGrupoClase())) {

            if (! primeraClausulaWhere) {
                sql.append(" AND ");
            } else {
                sql.append("WHERE (");
            }
            sql.append(" (alumno.DNI IN (SELECT ALUMNO_DNI "
                                      + "FROM matricula "
                                      + "WHERE ((GRUPO_CLASE_Cod_GC = ?) "
                                             + "AND (CURSO_Cod_CURSO = ?))"
                                      + ")"
                       + ")");
            values.add(grupo_clase.getCodGrupoClase());
            Curso curso = new Curso();
            values.add(curso.getCursoActual());
            primeraClausulaWhere = false;

        }

        if (! primeraClausulaWhere) {
            sql.append(" AND ");
        } else {
            sql.append("WHERE (");
        }
        sql.append(" (matricula.ALUMNO_DNI = alumno.DNI)) ");

       
        List<ListadoAlumno> resultado = new ArrayList<ListadoAlumno>();
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        
        try{

            System.out.println(sql.toString());
            int i=0;
            for (Object valor: values) {
                if (valor instanceof String ) {
                    stmt.setString(++i, (String)valor);
                }

                if (valor instanceof Integer) {
                    stmt.setInt(++i, (Integer)valor);
                }
                
            }

            ResultSet rs = stmt.executeQuery();
            String num_matricula;
            String dni;
            String nombre;
            String apellidos;

            String grupoclase;

            while (rs.next()) {
                dni = rs.getString(1);
                num_matricula = rs.getString(2);
                nombre = rs.getString(3);
                apellidos = rs.getString(4);
                grupoclase = rs.getString(5);
                resultado.add(new ListadoAlumno(num_matricula, dni, nombre,
                                                apellidos, grupoclase));
            }

            rs.close();
            dataAccessObject.close();
            return resultado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dataAccessObject.rollback();
            throw new RuntimeException("Ha ocurrido un problema al realizar la consulta");

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            dataAccessObject.rollback();
            throw new RuntimeException("Ha ocurrido un problema al realizar la consulta");
        }
    } // fin del método realizarConsultaAlumno



    /** Método que realiza la consulta solicitada a la Base de Datos.
     *
     * @param dni_alumno contiene el dni del alumno por la que se quiere
     *                   filtrar la consulta. Puede ser null.
     * @param convocatoria contiene la convocatoria por el cual se quiere filtrar
     *        la consulta. Puede ser null.
     * @param curso contiene el curso por el cual se quiere filtrar la consulta.
     *        Puede ser null.
     *
     * @return Si la ejecución es correcta, devolverá el resultado de la consulta.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public List<ListadoHistoricoAlumno> realizarConsultaHistoricoAlumno(String dni_alumno,
                                                                        String convocatoria,
                                                                        int curso) {

        StringBuilder sql = new StringBuilder("SELECT evaluacion.ALUMNO_DNI, "
                                                   + "evaluacion.CURSO_Cod_CURSO, "
                                                   + "evaluacion.CONVOCATORIA_idCONVOCATORIA, "
                                                   + "evaluacion.Nota_P1, evaluacion.Nota_P2, "
                                                   + "evaluacion.Nota_P3, evaluacion.Nota_P4, "
                                                   + "evaluacion.Nota_Pr, evaluacion.Nota_Final "
                                            + "FROM evaluacion, alumno "
                                            + "WHERE ((alumno.DNI = evaluacion.ALUMNO_DNI) ");

        List<Object> values = new ArrayList<Object>();
        List<ListadoHistoricoAlumno> resultado = new ArrayList<ListadoHistoricoAlumno>();

        if (this.noEstaVacio(dni_alumno)) {
            sql.append(" AND (evaluacion.ALUMNO_DNI = ?) ");
            values.add(dni_alumno);
        }

        if (this.noEstaVacio(convocatoria)) {
            sql.append(" AND (evaluacion.CONVOCATORIA_idCONVOCATORIA = ?) ");
            values.add(convocatoria);
        }

        if (curso != -1) {
            sql.append(" AND (evaluacion.CURSO_Cod_CURSO = ?) ");
            values.add(curso);
        }

        sql.append(")");

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try{
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

            while (rs.next()) {
                resultado.add(new ListadoHistoricoAlumno(rs.getString(1), rs.getInt(2),
                                                         rs.getString(3),
                                                         (rs.getFloat(4) + rs.getFloat(5)
                                                        + rs.getFloat(6) + rs.getFloat(7)),
                                                          rs.getFloat(8), rs.getFloat(9),
                                                          dataAccessObject));
            }

            rs.close();
            dataAccessObject.close();
            return resultado;            
        } catch (SQLException e1) {
            dataAccessObject.rollback();
            throw new RuntimeException("Ha ocurrido un error al acceder a la Base de Datos");
        } catch (RuntimeException e2) {
            dataAccessObject.rollback();
            throw new RuntimeException("Ha ocurrido un error al acceder a la Base de Datos");
        }
    } // fin del método realizarConsultaHistoricoAlumno


    /** Método que, dado un dni, obtiene el nombre del alumno con ese dni. 
     *  Si ocurre algún error o no encuentra ningún alumno, lanzará una excepción.
     * 
     * @param dni dni del alumno.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return devuelve el nombre del alumno con ese dni. 
     *         Si ocurre algún error o no encuentra ningún alumno, 
     *         lanzará una excepción.
     */
    public String obtenerNombreAlumno(String dni, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("SELECT Nombre "
                                            + "FROM alumno "
                                            + "WHERE (dni = ?) ");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
            throw new RuntimeException("No se ha encontrado un alumno con ese DNI");

        } catch (SQLException e) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en obtenerNombreAlumno");
        }
    } // fin del método obtenerNombreAlumno


    /** Método que, dado un dni, obtiene los apellidos del alumno con ese dni.
     *  Si ocurre algún error o no encuentra ningún alumno, lanzará una excepción.
     *
     * @param dni dni del alumno.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return devuelve los apellidos del alumno con ese dni.
     *         Si ocurre algún error o no encuentra ningún alumno,
     *         lanzará una excepción.
     */
    public String obtenerApellidosAlumno(String dni,DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("SELECT Apellidos "
                                            + "FROM alumno "
                                            + "WHERE (DNI = ?) ");;
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
            throw new RuntimeException("No se ha encontrado un alumno con ese DNI");

        } catch (SQLException e) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en obtenerApellidosAlumno");
        }
    } // fin del método obtenerApellidosAlumno


    /** Método que, dado un dni, obtiene el nº de matrícula del alumno con ese dni.
     *  Si ocurre algún error o no encuentra ningún alumno, lanzará una excepción.
     *
     * @param dni dni del alumno.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return devuelve el nombre del alumno con ese dni.
     *         Si ocurre algún error o no encuentra ningún alumno,
     *         lanzará una excepción.
     */
    public String obtenerNumMatriculaAlumno(String dni, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("SELECT N_Mat "
                                            + "FROM alumno "
                                            + "WHERE (dni = ?) ");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
            throw new RuntimeException("No se ha encontrado un alumno con ese DNI");

        } catch (SQLException e) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en obtenerNumMatriculaAlumno");
        }
    } // fin del método obtenerNumMatriculaAlumno


  

    /** Método que comprueba si una cadena no está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si no está vacía.
     *
     * @return TRUE si la cadena no está vacía. FALSE en caso contrario.
     */
    private boolean noEstaVacio(String cadena) {
	return ((cadena != null) && (!"".equals(cadena)));
    }

    /** Método que actualiza en la Base de Datos, los datos de un alumno recibido
     *  por parámetro.
     *
     * @param alumno contiene los nuevos datos personales del alumno.
     * @param grupo_clase contiene el nuevo grupo de clase del alumno.
     * @param dni_antiguo contiene el dni del alumno.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void actualizarAlumno(Alumno alumno, GrupoClase grupo_clase,
                                 String dni_antiguo, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("UPDATE ALUMNO "
                                            + "SET DNI = ?,"
                                            + "    N_Mat = ?,"
                                            + "    Nombre = ?, "
                                            + "    Apellidos = ? "
                                            + "WHERE (DNI = ?)");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            stmt.setString(1, alumno.getDNI());
            stmt.setString(2, alumno.getN_Mat());
            stmt.setString(3, alumno.getNombre());
            stmt.setString(4, alumno.getApellidos());
            stmt.setString(5, dni_antiguo);

            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException ("Problema en actualizarAlumno 1");
        }

        sql = new StringBuilder("UPDATE MATRICULA "
                              + "SET GRUPO_CLASE_Cod_GC = ? "
                              + "WHERE ((ALUMNO_DNI = ?) "
                              + "AND (CURSO_Cod_CURSO = ?))");

        if (grupo_clase.noEstaDadoDeAlta(dataAccessObject)) {
            grupo_clase.altaGrupoClase(dataAccessObject);
        }
        stmt = dataAccessObject.getPreparedStatement(sql.toString());
        Curso curso = new Curso();

        try {

            stmt.setString(1, grupo_clase.getCodGrupoClase());
            stmt.setString(2, alumno.getDNI());
            stmt.setInt(3, curso.getCursoActual());

            dataAccessObject.actualizar();
            stmt.close();
            System.out.println("Adios");
      //      dataAccessObject.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException ("Problema en actualizarAlumno 2");
        }
    } // fin del método actualizarAlumno






} // fin de la clase ControladorAlumno