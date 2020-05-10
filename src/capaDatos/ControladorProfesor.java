
package capaDatos;


import capaInterfaz.listados.ListadoProfesor;
import capaLogicaNegocio.Convocatoria;
import capaLogicaNegocio.Curso;
import capaLogicaNegocio.GrupoClase;
import capaLogicaNegocio.Profesor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/** Clase que gestiona el acceso a la Base de Datos para
 * altas, bajas, modificaciones y consultas de profesores.
 *
 * @author Confiencial
 */
public class ControladorProfesor {

    public ControladorProfesor () {
    }

    
    /** Método que comprueba si un profesor está dado de alta en la Base de Datos.
     *
     * @param profesor contiene los datos del profesor del que se quiere comprobar
     *                 si está dado de alta.
     *
     * @return TRUE si el profesor está dado de alta. 
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean estaDadoDeAlta(Profesor profesor) {

        boolean ok;
        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM profesor "
                                            + "WHERE ((activo = 1) ");
        List<Object> values = new ArrayList<Object>();
            
        if (noEstaVacio(profesor.getNombre())){
            sql.append(" AND (nombre = ?) ");
            values.add(profesor.getNombre());
        }

        if (noEstaVacio(profesor.getApellidos())){
        
            sql.append(" AND (apellidos = ?) ");
            values.add(profesor.getApellidos());
        }
        sql.append(")");
        
        
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try{           
            int i=0;
            for (Object valor: values) {
                stmt.setString(++i, (String)valor);
            }

            ResultSet rs = stmt.executeQuery();
            ok = rs.next();
            dataAccessObject.close();
            rs.close();         
            return ok;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            dataAccessObject.rollback();
            throw new RuntimeException("Problema al comprobar que el profesor esta dado de alta");
        }
    } // fin del método estaDadoDeAlta


    /** Método que se encarga de dar de insertar los datos del profesor que se 
     *  quiere dar de alta en la Base de Datos. 
     *  Si el profesor tiene asociados grupos de clase, se comprobará si están
     *  dados de alta en la Base de Datos y, si no lo están, se darán automáticamente
     *  de alta. Posteriormente, se registrará la asociación del profesor con 
     *  dichos grupos de clase.
     *  Si ocurre algún error, lanzará una excepción.
     * 
     * @param profesor contiene los datos del profesor que se quiere dar de alta.
     */
    public void darAltaProfesor(Profesor profesor){

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();

        try{
            this.insertarEnTablaProfesor(profesor, dataAccessObject);
   
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            throw new RuntimeException(e.getMessage());
        }

        dataAccessObject = dataAccessObject.getDataAccessObjectConnected();

        try {
            this.validarGrupoClase(profesor, profesor.getGrupoClase1(),
                                   dataAccessObject);
            this.validarGrupoClase(profesor, profesor.getGrupoClase2(),
                                   dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e1){
        //    profesor.bajaProfesor();
            dataAccessObject.rollback();
            throw new RuntimeException(e1.getMessage());
        }
    } // fin del método darAltaProfesor



    /** Método que comprueba si se ha introducido algún grupo de clase, y en ese
     *  caso, si está dado ya de alta. Si no lo está, lo da de alta.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param profesor contiene la información del profesor que imparte el grupo de clase.
     * @param grupo_clase contiene la información del grupo de clase a validar.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void validarGrupoClase(Profesor profesor, GrupoClase grupo_clase,
                                  DataAccessObject dataAccessObject) {
         if (this.noEstaVacio(grupo_clase.getCodGrupoClase())){
                grupo_clase.validarGrupoClase(dataAccessObject);
                this.insertarEnTablaImparte(profesor,
                                        grupo_clase.getCodGrupoClase(),
                                        dataAccessObject);
        }
    } // fin del método validarGrupoClase


    
    /** Método que se encarga de insertar los datos del profesor en la Base de Datos
     * Si ocurre algún error, lanzará una excepción.
     *
     * @param profesor contiene los datos del profesor del que se quiere dar de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void insertarEnTablaProfesor(Profesor profesor,
                                        DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("INSERT profesor VALUES (?,?,?,?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            stmt.setInt(1, profesor.getCodProfesor());
            stmt.setString(2, profesor.getNombre());
            stmt.setString(3, profesor.getApellidos());
            stmt.setInt(4, 1);

            dataAccessObject.actualizar();
            stmt.close();
            }
        catch (SQLException ex) {
            throw new RuntimeException("problema en insertarEnTablaProfesor");
            }
    } // fin del método insertarEnTablaProfesor


    /** Método que se encarga de insertar los datos de los grupos de clase
     *  que imparte el profesor que se quiere dar de alta en la Base de Datos.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param profesor contiene los datos del profesor del que se quiere
     *        dar de alta.
     * @param cod_grupo_clase contiene el código del grupo de clase que imparte
     *        el profesor.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void insertarEnTablaImparte(Profesor profesor, String cod_grupo_clase,
                                        DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("INSERT imparte VALUES (?,?,?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        Curso curso = new Curso();

        try {
            System.out.println(profesor.getCodProfesor()+" "+cod_grupo_clase+" "+curso.getCursoActual());
            stmt.setInt(1, profesor.getCodProfesor());
            stmt.setString(2, cod_grupo_clase);
            stmt.setInt(3, curso.getCursoActual());

            dataAccessObject.actualizar();
            stmt.close();
            }
        catch (SQLException ex) {
            throw new RuntimeException("problema en insertarEnTablaImparte");
            }
    } // fin del método intertarEnTablaImparte



    /** Método que se encarga de eliminar los datos del profesor en la Base de Datos.
     * Eliminará cualquier registro de clases impartidas, grupos de prácticas
     * tutorizados y el propio registro de los datos del profesor.
     * Si ocurre algún error, lanzará una excepción.
     *
     * @param profesor contiene los datos del profesor del que se quiere dar de baja.
     */
    public void darBajaProfesor(Profesor profesor) {

        int cod_profesor;
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            cod_profesor = this.obtenerCodigoProfesor(profesor, dataAccessObject);

            if (this.estaRegistradoEnTutoriaActualmente(cod_profesor, dataAccessObject)) {
                throw new RuntimeException("Revise que no esté tutorizando ningún grupo de prácticas");
            }
            this.borrarEnTablaImparte(cod_profesor, dataAccessObject);
        //    this.borrarEnTablaTutoria(cod_profesor, dataAccessObject);
            if (this.estaRegistradoEnImparte(cod_profesor, dataAccessObject)
             ||(this.estaRegistradoEnTutoria(cod_profesor, dataAccessObject))) {
                this.desactivarProfesor(cod_profesor, dataAccessObject);
            } else {
                this.borrarEnTablaProfesor(cod_profesor, dataAccessObject);
            }

            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            throw new RuntimeException(e.getMessage());
        }
    } // fin del método darBajaProfesor




    /** Método que comprueba que profesores tienen tutorías
     *  en la convocatoria actual, y los activa en la Base de Datos.
     *  Es decir, pone su campo "activo" a 1.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void reactivarProfesoresConTutoriasEnConvocatoriaActual(DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder(
                "UPDATE PROFESOR "
              + "SET activo = 1 "
              + "WHERE (Cod_P IN (SELECT DISTINCT PROFESOR_Cod_P "
                                 +"FROM tutoria  "
                                 +"WHERE ((CURSO_Cod_CURSO = ?) "
                                 +"  AND (CONVOCATORIA_idCONVOCATORIA = ?)))"
                    + ")");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            Curso curso = new Curso();
            Convocatoria convocatoria = new Convocatoria();
            stmt.setInt(1, curso.getCursoActual());
            stmt.setString(2, convocatoria.getConvocatoriaActual());
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
            throw new RuntimeException("problema en reactivarGruposPracticasEnUsoEnConvocatoriaActual");
        }
    } // fin del método reactivarProfesoresConTutoriasEnConvocatoriaActual



    /** Método que comprueba que profesores imparten clases
     *  en la convocatoria actual, y los activa en la Base de Datos.
     *  Es decir, pone su campo "activo" a 1.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void reactivarProfesoresConImparteEnConvocatoriaActual(DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder(
                "UPDATE PROFESOR "
              + "SET activo = 1 "
              + "WHERE (Cod_P IN (SELECT DISTINCT PROFESOR_Cod_P "
                                 +"FROM imparte  "
                                 +"WHERE ((CURSO_Cod_CURSO = ?)))"
                    + ")");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            Curso curso = new Curso();
            stmt.setInt(1, curso.getCursoActual());
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
            throw new RuntimeException("problema en reactivarGruposPracticasEnUsoEnConvocatoriaActual");
        }
    } // fin del método reactivarProfesoresConImparteEnConvocatoriaActual


    /** Método que elimina de la Base de Datos todos los registros de imparticiones
     *  de clase dados de alta en el curso actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void eliminarImparticionesCursoActual(DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM imparte "
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
    } // fin del método eliminarImparticionesCursoActual


    /** Método que consulta en la Base de Datos el código de un profesor
     *  dado por parámetro.
     *
     * @param profesor contiene la información del profesor del cual queremos
     *        saber su código.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return Si el método se ejecuta correctamente, devolverá el valor del
     *         código del profesor.
     *         Si no encuentra en la Base de Datos al profesor, devolverá -1.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public int obtenerCodigoProfesor(Profesor profesor, DataAccessObject dataAccessObject) {

        int cod_profesor;

        StringBuilder sql = new StringBuilder(
                                 "SELECT Cod_P "
                               + "FROM profesor "
                               + "WHERE ((nombre = ?) AND (apellidos = ?))");
        List<Object> values = new ArrayList<Object>();
        values.add(profesor.getNombre());
        values.add(profesor.getApellidos());
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        int i=0;
        for (Object valor: values) {
            try {
                stmt.setString(++i, (String) valor);
            } catch (SQLException ex) {
                dataAccessObject.rollback();
                throw new RuntimeException("Error en el for Object valor: values");
            }
        }

        try{
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
               cod_profesor = rs.getInt(1); 
            } else {
               cod_profesor = -1;
            }
            rs.close();
            stmt.close();
            return cod_profesor;

        } catch (SQLException ex) {
            dataAccessObject.rollback();
            throw new RuntimeException("problema al calcular el codigo del profesor");
        }
    } // fin del método obtenerCodigoProfesor


    

    /** Método que comprueba si un profesor imparte actualmente algún grupo de clase.
     *
     * @param cod_profesor contiene el código del profesor.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si imparte, al menos, un grupo de clase.
     *         FALSE si no imparte ninguno.
     *         Si ocurre algún error, lanzará una excepción.
     */
    private boolean estaRegistradoEnImparteActualmente(int cod_profesor,
                                            DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM imparte "
                                            + "WHERE ((PROFESOR_Cod_P = ?) "
                                              + "AND (CURSO_Cod_CURSO = ?))");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {

            Curso curso = new Curso();
            stmt.setInt(1, cod_profesor);
            stmt.setInt(2, curso.getCursoActual());
            ResultSet rs = stmt.executeQuery();
            
            boolean ok = rs.next();
            rs.close();
            stmt.close();
            
            return ok;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en estaRegistradoEnImparteActualmente");
        }
    } // fin del método estaRegistradoEnImparteActualmente



    /** Método que comprueba si un profesor tutoriza a algún grupo de prácticas.
     *
     * @param cod_profesor contiene el código del profesor.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si tutoriza a, al menos, un grupo de prácticas.
     *         FALSE si no tutoriza ninguno.
     *         Si ocurre algún error, lanzará una excepción.
     */
    private boolean estaRegistradoEnTutoriaActualmente(int cod_profesor,
                                            DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM tutoria "
                                            + "WHERE ((PROFESOR_Cod_P = ?) "
                                               + "AND (CURSO_Cod_CURSO = ?) "
                                               + "AND (CONVOCATORIA_idCONVOCATORIA = ?))");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            Curso curso = new Curso();
            Convocatoria convocatoria = new Convocatoria();

            stmt.setInt(1, cod_profesor);
            stmt.setInt(2, curso.getCursoActual());
            stmt.setString(3, convocatoria.getConvocatoriaActual());

            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            rs.close();
            stmt.close();
            return ok;
        } catch (SQLException e) {
            throw new RuntimeException("Problema en estaRegistradoEnTutoriaActualmente");
        }
    } // fin del método estaRegistradoEnTutoriaActualmente


   
    /** Método que comprueba si un profesor imparte o ha impartido
     *  algún grupo de clase.
     *
     * @param cod_profesor contiene el código del profesor.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si imparte, al menos, un grupo de clase.
     *         FALSE si no imparte ninguno.
     *         Si ocurre algún error, lanzará una excepción.
     */
    private boolean estaRegistradoEnImparte(int cod_profesor,
                                            DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM imparte "
                                            + "WHERE (PROFESOR_Cod_P = ?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {

            stmt.setInt(1, cod_profesor);
            ResultSet rs = stmt.executeQuery();

            boolean ok = rs.next();
            rs.close();
            stmt.close();

            return ok;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en estaRegistradoEnImparte");
        }
    } // fin del método estaRegistradoEnImparte



    /** Método que comprueba si un profesor tutoriza o ha tutorizado
     *  a algún grupo de prácticas.
     *
     * @param cod_profesor contiene el código del profesor.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si tutoriza a, al menos, un grupo de prácticas.
     *         FALSE si no tutoriza ninguno.
     *         Si ocurre algún error, lanzará una excepción.
     */
    private boolean estaRegistradoEnTutoria(int cod_profesor,
                                            DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM tutoria "
                                            + "WHERE (PROFESOR_Cod_P = ?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {

            stmt.setInt(1, cod_profesor);

            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            rs.close();
            stmt.close();
            return ok;
        } catch (SQLException e) {
            throw new RuntimeException("Problema en estaRegistradoEnTutoria");
        }
    } // fin del método estaRegistradoEnTutoria


    /** Metodo que se encarga de eliminar en la tabla "profesor" de la Base de Datos
     *  todos los registros del profesor que se quiere dar de baja.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param cod_profesor contiene el código del profesor que se quiere dar de baja.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void borrarEnTablaProfesor(int cod_profesor, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM profesor "
                                            + "WHERE (Cod_P = ?)");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try{
            stmt.setInt(1, cod_profesor);
            dataAccessObject.actualizar();
            stmt.close();
        }catch (SQLException e1) {
        //    dataAccessObject.rollback();
            throw new RuntimeException("problema al eliminar en profesor");
        } catch (RuntimeException e2) {
 //           dataAccessObject.rollback();
            throw new RuntimeException(e2.getMessage()); //"problema al eliminar en profesor");
        }
    } // fin del método borrarEnTablaProfesor



    /** Metodo que se encarga de eliminar en la tabla "imparte" de la Base de Datos
     *  todos los registros del profesor que se quiere dar de baja.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param cod_profesor contiene el código del profesor que se quiere dar de baja.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void borrarEnTablaImparte(int cod_profesor, DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM imparte "
                                            + "WHERE ((PROFESOR_Cod_P = ?) "
                                               + "AND (CURSO_Cod_CURSO = ?))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            Curso curso = new Curso();

            stmt.setInt(1, cod_profesor);
            stmt.setInt(2, curso.getCursoActual());
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e1) {
        //    dataAccessObject.rollback();
            throw new RuntimeException("problema al eliminar en imparte");
        } catch(RuntimeException e2) {
    //        dataAccessObject.rollback();
            throw new RuntimeException(e2.getMessage()); //"problema al eliminar en imparte");
        }
    } // fin del método borrarEnTablaImparte



    /** Metodo que se encarga de eliminar en la tabla "tutoria" de la Base de Datos
     *  todos los registros del profesor que se quiere dar de baja.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param cod_profesor contiene el código del profesor que se quiere dar de baja.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void borrarEnTablaTutoria(int cod_profesor, DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("DELETE "
                                            + "FROM tutoria "
                                            + "WHERE ((PROFESOR_Cod_P = ?) "
                                               + "AND (CURSO_Cod_CURSO = ?))");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try{
            Curso curso = new Curso();

            stmt.setInt(1, cod_profesor);
            stmt.setInt(2, curso.getCursoActual());
            dataAccessObject.actualizar();
            stmt.close();
        }catch (SQLException e1) {
        //    dataAccessObject.rollback();
            throw new RuntimeException("problema al eliminar en tutoria");
        } catch (RuntimeException e2) {
//            dataAccessObject.rollback();
            throw new RuntimeException(e2.getMessage()); //"problema al eliminar en tutoria");
        }
    } // fin del método borrarEnTablaTutoria


    /** Metodo que se encarga de desactivar en la tabla "profesor" de la Base de Datos
     *  todos los registros del profesor que se quiere dar de baja. Conviene
     *  aclarar que no se borra el registro de la tabla PROFESOR, sino que se
     *  pone su campo "activo" a cero.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param cod_profesor contiene el código del profesor que se quiere dar de baja.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void desactivarProfesor(int cod_profesor, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("UPDATE PROFESOR"
                                            + "SET activo = 0 "
                                            + "WHERE (Cod_P = ?)");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try{
            stmt.setInt(1, cod_profesor);
            dataAccessObject.actualizar();
            stmt.close();
        }catch (SQLException e1) {
        //    dataAccessObject.rollback();
            throw new RuntimeException("problema al desactivar en profesor");
        } catch (RuntimeException e2) {
 //           dataAccessObject.rollback();
            throw new RuntimeException(e2.getMessage());
        }
    } // fin del método desactivarProfesor


    /** Método que consulta en la Base de Datos las claves de los profesores
     *  y devuelve la mayor de ellas
     *  Si ocurre algún error, lanzará una excepción.
     * 
     * @return Si no hay ningún profesor registrado, devolverá cero.
     *         Si hay profesores registrados, devolverá la mayor de las claves.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public int recuperarUltimoCodProfesor() {

        StringBuilder sql = new StringBuilder("SELECT Cod_P "
                                            + "FROM profesor "
                                            + "WHERE (Cod_P >= ALL (SELECT Cod_P"
                                                                + " FROM profesor))");
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
            throw new RuntimeException("Problema al consultar la clave del último profesor");
        }
    } // fin del método recuperarUltimoCodProfesor


    /** Método que realiza la consulta solicitada a la Base de Datos.
     *
     * @param profesor contiene la información de profesor por la que se quiere
     *        filtrar la consulta. Puede ser null.
     * @param grupo_clase contiene la información de grupo de clase por la que
     *        se quiere filtrar la consulta. Puede ser null.
     *
     * @return Si la ejecución es correcta, devolverá el resultado de la consulta.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public List<ListadoProfesor> realizarConsultaProfesor(Profesor profesor, GrupoClase grupo_clase) {
        
        List<ListadoProfesor> resultado = new ArrayList<ListadoProfesor>();
        
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
      
        try{
            this.anyadirProfesoresConClases(resultado, profesor, grupo_clase, dataAccessObject);
            if (this.estaVacio(grupo_clase.getCodGrupoClase())) {
                this.anyadirProfesorSinClases(resultado, profesor, dataAccessObject);
            }
            dataAccessObject.close();
            return resultado;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            dataAccessObject.rollback();
            throw new RuntimeException("Ha ocurrido un problema al realizar la consulta");
        }
    } // fin del método realizarConsultaProfesor



    /** Método que actualiza los datos de los profesores recibidos por parámetro.
     *
     * @param resultado_consulta contiene la información actualizada de los profesores.
     */
    public void actualizarProfesores(List<ListadoProfesor> resultado_consulta) {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        int tam = resultado_consulta.size();
        try {
            Profesor profesor = new Profesor();
            for (int i = 0; i < tam; i++){
                 profesor = new Profesor(resultado_consulta.get(i).getCodProfesor(),
                                         resultado_consulta.get(i).getNombre(),
                                         resultado_consulta.get(i).getApellidos(),
                                         resultado_consulta.get(i).getGrupoClase1(),
                                         resultado_consulta.get(i).getGrupoClase2());
                 this.actualizarProfesor(profesor, dataAccessObject);
            }
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            throw new RuntimeException("Ha ocurrido un error durante la actualización.");
        }
    } // fin del método actualizarProfesores



    /** Método que actualiza los datos de UN profesor recibido por parámetro.
     *
     * @param profesor contiene la información del profesor.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void actualizarProfesor (Profesor profesor,DataAccessObject dataAccessObject) {

        int cod = profesor.getCodProfesor();
        this.borrarEnTablaImparte(cod, dataAccessObject);

        StringBuilder sql = new StringBuilder("UPDATE PROFESOR "
                                            + "SET Nombre = ?,"
                                            + "    Apellidos = ? "
                                            + "WHERE (Cod_P = ?)");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {

            stmt.setString(1, profesor.getNombre());
            stmt.setString(2, profesor.getApellidos());
            stmt.setInt(3, profesor.getCodProfesor());

            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException ("Problema en actualizarProfesor 1");
        }

        String cod_grupo_clase = profesor.getGrupoClase1().getCodGrupoClase();
        System.out.println("GrupoClase 1: "+cod_grupo_clase);
        if (this.noEstaVacio(cod_grupo_clase)) {
            this.insertarEnTablaImparte(profesor, cod_grupo_clase, dataAccessObject);
        }

        cod_grupo_clase = profesor.getGrupoClase2().getCodGrupoClase();
        System.out.println("GrupoClase 2: "+cod_grupo_clase);
        if (this.noEstaVacio(cod_grupo_clase)) {
            this.insertarEnTablaImparte(profesor, cod_grupo_clase, dataAccessObject);
        }
    } // fin del método actualizarProfesor



    /** Método que consulta en la Base de Datos los profesores que imparten,
     *  al menos, una clase y cumplen con los filtros especificados por el usuario.
     *  El resultado de la consulta lo devolverá a través del parámetro resultado.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param resultado Estructura con la que se devolverá el resultado.
     * @param profesor contiene la información de los filtros aplicados a profesor.
     * @param grupo_clase contiene la información de los filtros aplicados a grupo de clase.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void anyadirProfesoresConClases(List<ListadoProfesor> resultado,
                                           Profesor profesor, GrupoClase grupo_clase,
                                           DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("SELECT profesor.Nombre, profesor.Apellidos, "
                                                  + " imparte.GRUPO_CLASE_Cod_GC,"
                                                  + " profesor.Cod_P "
                                            + "FROM profesor, imparte "
                                            + "WHERE ( (profesor.activo = 1) "
                                               + " AND (imparte.CURSO_Cod_CURSO = ?) ");

        List<Object> values = new ArrayList<Object>();
        Curso curso = new Curso();
        values.add(curso.getCursoActual());

        if (this.noEstaVacio(profesor.getNombre())){
            sql.append(" AND (profesor.Nombre = ?) ");
            values.add(this.obtenerCodigoProfesor(profesor, dataAccessObject));
        }

        if (this.noEstaVacio(profesor.getApellidos())) {
   
            sql.append(" AND (profesor.Apellidos = ?) ");
            values.add(profesor.getApellidos());
        }

        if (this.noEstaVacio(grupo_clase.getCodGrupoClase())) {
            sql.append(" AND (Cod_P IN (SELECT PROFESOR_Cod_P "
                                     + "FROM imparte "
                                     + "WHERE (GRUPO_CLASE_Cod_GC = ?)"
                                     + ")"
                           + ")");
            values.add(grupo_clase.getCodGrupoClase());
        }

        sql.append(" AND (imparte.PROFESOR_Cod_P = profesor.Cod_P)) ");
        
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

            String nombre = "1";
            String apellidos = "2";
            String grupoclase = null;
            String grupoclase2 = null;
            int num_grupos_clase = 0;
            int cod_profesor = -1;

            while (rs.next()) {
                if ((nombre.equals(rs.getString(1))) &&
                    (apellidos.equals(rs.getString(2)))) {

                    num_grupos_clase = 0;
                    grupoclase2 = rs.getString(3);
                    cod_profesor = rs.getInt(4);
                    resultado.add(new ListadoProfesor(cod_profesor, nombre, apellidos, grupoclase,
                                                      grupoclase2));
                } else {
                    if (num_grupos_clase == 1) {
                        resultado.add(new ListadoProfesor(cod_profesor, nombre, apellidos,
                                                          grupoclase, null));
                        num_grupos_clase = 0;
                    }

                    if (num_grupos_clase == 0) {
                        nombre = rs.getString(1);
                        apellidos = rs.getString(2);
                        grupoclase = rs.getString(3);
                        cod_profesor = rs.getInt(4);
                        num_grupos_clase = 1;
                    }
                }
            }
            if (num_grupos_clase == 1) {
                resultado.add(new ListadoProfesor(cod_profesor, nombre, apellidos,
                                                  grupoclase, null));
            }

            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Ha ocurrido un problema en añadirProfesoresConClases");
        }
    } // fin del método añadirProfesoresConClases


    /** Método que consulta en la Base de Datos los profesores que NO imparten, 
     *  ninguna clase y cumplen con los filtros especificados por el usuario.
     *  El resultado de la consulta lo devolverá a través del parámetro resultado.
     *  Si ocurre algún error, lanzará una excepción.
     * 
     * @param resultado Estructura con la que se devolverá el resultado.
     * @param profesor contiene la información de los filtros aplicados a profesor.
     * @param grupo_clase contiene la información de los filtros aplicados a grupo de clase.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void anyadirProfesorSinClases(List<ListadoProfesor> resultado,
                                         Profesor profesor, 
                                         DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT profesor.Nombre, "
                                                           + " profesor.Apellidos, "
                                                           + " profesor.Cod_P "
                                             +" FROM profesor, imparte "
                                            + " WHERE ((profesor.activo = 1) ");

        List<Object> values = new ArrayList<Object>();

        if (this.noEstaVacio(profesor.getNombre())){
            sql.append(" AND (profesor.Nombre = ?) ");
            values.add(profesor.getNombre());
        }

        if (this.noEstaVacio(profesor.getApellidos())) {

            sql.append(" AND (profesor.Apellidos = ?) ");
            values.add(profesor.getApellidos());
        }

        sql.append(" AND (profesor.Cod_P NOT IN (SELECT imparte.PROFESOR_Cod_P "
                                               +"FROM imparte "
                                               +"WHERE ((imparte.CURSO_Cod_CURSO = ?))))) ");
        Curso curso = new Curso();
        values.add(curso.getCursoActual());
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try{
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
            boolean no_resultados = true;
            int cod_profesor = -1;
            String nombre = null;
            String apellidos = null;
            while (rs.next()) {
                no_resultados = false;
                nombre = rs.getString(1);
                apellidos = rs.getString(2);
                cod_profesor = rs.getInt(3);
                resultado.add(new ListadoProfesor(cod_profesor, nombre, apellidos,
                                                  null, null));
            }

            if (no_resultados) {
                // System.out.println("La consulta no ha producido ningún resultado");
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Ha ocurrido un problema en añadirProfesorSinClases");
        }
    } // fin del método añadirProfesorSinClases


    /** Método que, dado un código de profesor, obtiene el nombre del profesor
     *  con ese código.
     *  Si ocurre algún error o no encuentra ningún alumno, lanzará una excepción.
     *
     * @param codigo código del profesor.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return devuelve el nombre del profesor con ese código.
     *         Si ocurre algún error o no encuentra ningún alumno,
     *         lanzará una excepción.
     */
    public String obtenerNombreProfesor(int codigo, DataAccessObject dataAccessObject) {
        
        StringBuilder sql = new StringBuilder("SELECT Nombre "
                                            + "FROM profesor "
                                            + "WHERE (Cod_P = ?) ");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            throw new RuntimeException("No se ha encontrado un profesor con ese código");

        } catch (SQLException e) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en obtenerNombreProfesor");
        }
    } // fin del método obtenerNombreProfesor


    /** Método que, dado un código de profesor, obtiene los apellidos del profesor
     *  con ese código.
     *  Si ocurre algún error o no encuentra ningún alumno, lanzará una excepción.
     *
     * @param codigo código del profesor.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return devuelve los apellidos del profesor con ese código.
     *         Si ocurre algún error o no encuentra ningún alumno,
     *         lanzará una excepción.
     */
    public String obtenerApellidosProfesor(int codigo, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("SELECT Apellidos "
                                            + "FROM profesor "
                                            + "WHERE (Cod_P = ?) ");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
            throw new RuntimeException("No se ha encontrado un profesor con ese código");

        } catch (SQLException e) {
            dataAccessObject.rollback();
            throw new RuntimeException("Problema en obtenerApellidosProfesor");
        }
    } // fin del método obtenerApellidosProfesor



    /** Método que comprueba si una cadena no está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si no está vacía.
     *
     * @return TRUE si la cadena no está vacía.
     *         FALSE en caso contrario.
     */
    private boolean noEstaVacio(String cadena) {
	return cadena != null && !"".equals(cadena);
    }

    /** Método que comprueba si una cadena está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si está vacía.
     *
     * @return TRUE si la cadena está vacía.
     *         FALSE en caso contrario.
     */
    private boolean estaVacio(String cadena) {
	return ((cadena == null) || ("".equals(cadena)));
    } // fin del método estaVacio

} // fin de la clase ControladorProfesor