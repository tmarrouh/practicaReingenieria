
package capaDatos;

import capaInterfaz.listados.ListadoGrupoPractica;
import capaLogicaNegocio.Convocatoria;
import capaLogicaNegocio.Curso;
import capaLogicaNegocio.GrupoPractica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Clase que gestiona el acceso a la Base de Datos para
 *  altas, bajas, modificaciones y consultas de grupos de prácticas.
 *
 * @author Confiencial
 */
public class ControladorGrupoPractica {

    public ControladorGrupoPractica () {
    }


    /** Método que realiza el alta de un grupo de prácticas. Además, registrará
     *  la tutoría correspondiente y hará reflejar el grupo de prácticas en la/s
     *  evaluación/es de el/los componente/s del grupo.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param grupo contiene la información del grupo de prácticas que se
     *        quiere dar de alta.
     */
    public void darAltaGrupoPractica(GrupoPractica grupo) {

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        boolean dar_baja_grupo = false;
        try {
        if (this.noEstaDadoDeAlta(grupo,dataAccessObject)) {
            this.insertarEnTablaGrupoPractica(grupo, dataAccessObject);
        } else {
            if (this.estaActivo(grupo,dataAccessObject)) {
                throw new RuntimeException("Ya hay un grupo dado de alta con ese código");
            }
            this.reactivarGrupoPractica(grupo,dataAccessObject);
        }
        dataAccessObject.close();
        dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        dar_baja_grupo = true;

        ControladorEvaluacion DAOEvaluacion = new ControladorEvaluacion();

        if (DAOEvaluacion.alumnoTieneGrupoPractica(grupo.getAlumno1(),dataAccessObject)) {
            if (dar_baja_grupo) {
                this.desactivarGrupoPractica(grupo, dataAccessObject);
            }
            throw new RuntimeException("El alumno 1 ya tiene asignado un grupo de prácticas");
        }
        if (grupo.getAlumno2() != null) {
            if (DAOEvaluacion.alumnoTieneGrupoPractica(grupo.getAlumno2(),dataAccessObject)) {
                if (dar_baja_grupo) {
                    this.desactivarGrupoPractica(grupo, dataAccessObject);
                }
                throw new RuntimeException("El alumno 2 ya tiene asignado un grupo de prácticas");
            }
        }

        ControladorTutoria DAOTutoria = new ControladorTutoria();
        DAOTutoria.insertarEnTablaTutoria(grupo.getCodGrupoPractica(),
                                          grupo.getTutor(),
                                          dataAccessObject);

        DAOEvaluacion.actualizarPracticaEnTablaEvaluacion(grupo.getAlumno1(),
                                                  grupo.getCodGrupoPractica(),
                                                  grupo.getNota(),
                                                  dataAccessObject);
        if (grupo.getAlumno2() != null) {
           DAOEvaluacion.actualizarPracticaEnTablaEvaluacion(grupo.getAlumno2(),
                                                     grupo.getCodGrupoPractica(),
                                                     grupo.getNota(),
                                                     dataAccessObject);
        }
        dataAccessObject.close();
        } catch (RuntimeException e) {
            if (dar_baja_grupo) {
                this.desactivarGrupoPractica(grupo,dataAccessObject);
            }
            dataAccessObject.rollback();
            throw new RuntimeException(e.getMessage());
        }
    } // fin del método darAltaGrupoPractica



    /** Método que realiza la baja de un grupo de prácticas. Si ocurre algún error
     *  o existen registros de tutorías o evaluaciones en las que aparezca el grupo,
     *  lanzará una excepción.
     *
     *  @param grupo contiene la información del grupo de prácticas que se quiere
     *         dar de baja.
     *  @param dni1 dni del alumno 1.
     *  @param dni2 dni del alumno 2.
     */
    public void darBajaGrupoPractica(GrupoPractica grupo, String dni1, String dni2) {
        ControladorEvaluacion DAOEvaluacion = new ControladorEvaluacion();
        ControladorTutoria DAOTutoria = new ControladorTutoria();

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try{
            DAOEvaluacion.borrarGrupoPracticaEnTablaEvaluacion(dni1, dataAccessObject);
            if (this.noEstaVacio(dni2)) {
                DAOEvaluacion.borrarGrupoPracticaEnTablaEvaluacion(dni2, dataAccessObject);
            }
            DAOTutoria.borrarTutoriaGrupoPractica(grupo.getCodGrupoPractica(), dataAccessObject);
            this.desactivarGrupoPractica(grupo, dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println("Error "+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    } // fin del método darBajaGrupoPractica




    /** Método que actualiza la Base de Datos con las modificaciones realizadas
     *  en los grupos de prácticas.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param resultado_modificaciones contiene las modificaciones realizadas
     */
    public void actualizarGruposPracticas (List<GrupoPractica> resultado_modificaciones) {
        int tam = resultado_modificaciones.size();
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            for (int i = 0; i < tam; i++){
                this.actualizarGrupoPracticas(resultado_modificaciones.get(i), dataAccessObject);
            }
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    } // fin del método actualizarGruposPracticas


    /** Método que actualiza la Base de Datos con las modificaciones realizadas
     *  en UN grupo de prácticas.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param grupo contiene las modificaciones realizadas en UN grupo de prácticas.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void actualizarGrupoPracticas (GrupoPractica grupo,
                                          DataAccessObject dataAccessObject) {
        this.borrarRegistrosAntiguosGrupoPracticas(grupo.getCodGrupoPractica(),dataAccessObject);
        this.actualizarRegistrosNuevosGrupoPracticas(grupo, dataAccessObject);
    } // fin del método actualizarGrupoPracticas



    /** Método que elimina los registros del grupo de prácticas recibido como
     *  parámetros y su nota de la evaluación de los alumnos miembros del grupo.
     *
     * @param cod código del grupo de prácticas
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void borrarRegistrosAntiguosGrupoPracticas(int cod,
                                                       DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("UPDATE EVALUACION "
                                            + "SET GRUPO_PRACTICA_Cod_GP = null, "
                                            + "    Nota_Pr = 0 "
                                            + "WHERE ((CURSO_Cod_CURSO = ?) "
                                            + "   AND (CONVOCATORIA_idCONVOCATORIA = ?) "
                                            + "   AND (GRUPO_PRACTICA_Cod_GP = ?))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        Curso curso = new Curso();
        Convocatoria convocatoria = new Convocatoria();
        try {
            stmt.setInt(1, curso.getCursoActual());
            stmt.setString(2, convocatoria.getConvocatoriaActual());
            stmt.setInt(3, cod);

            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException ("Problema en borrarRegistrosAntiguosGrupoPracticas");
        }
    } // fin del método borrarRegistrosAntiguosGrupoPracticas


    /** Método que actualiza los registros del grupo de prácticas recibido como
     *  parámetros y su nota en la evaluación de los alumnos miembros del grupo.
     *
     * @param grupo contiene la información actualizada del grupo.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void actualizarRegistrosNuevosGrupoPracticas (GrupoPractica grupo,
                                                          DataAccessObject dataAccessObject) {
        this.actualizarRegistrosAlumnoEnGrupoPracticas(grupo.getAlumno1().getDNI(),
                                                       grupo.getCodGrupoPractica(),
                                                       grupo.getNota(),
                                                       dataAccessObject);
        if (grupo.getAlumno2() != null) {
            this.actualizarRegistrosAlumnoEnGrupoPracticas(grupo.getAlumno2().getDNI(),
                                                           grupo.getCodGrupoPractica(),
                                                           grupo.getNota(),
                                                           dataAccessObject);
        }
    } // fin del método actualizarRegistrosNuevosGrupoPracticas



    /** Método que actualiza los registros del grupo de prácticas recibido como
     *  parámetros y su nota en la evaluación de UNO de los alumnos miembros del grupo.
     *
     * @param dni_alumno contiene el dni de un integrante del grupo.
     * @param cod código del grupo de prácticas.
     * @param nota nota del grupo de prácticas.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void actualizarRegistrosAlumnoEnGrupoPracticas (String dni_alumno,
                                                            int cod, Float nota,
                                                            DataAccessObject dataAccessObject) {
       StringBuilder sql = new StringBuilder("UPDATE EVALUACION "
                                            + "SET GRUPO_PRACTICA_Cod_GP = ?, "
                                            + "    Nota_Pr = ? "
                                            + "WHERE ((CURSO_Cod_CURSO = ?) "
                                            + "   AND (CONVOCATORIA_idCONVOCATORIA = ?) "
                                            + "   AND (ALUMNO_DNI = ?))");

        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        Curso curso = new Curso();
        Convocatoria convocatoria = new Convocatoria();
        try {
            stmt.setInt(1, cod);
            stmt.setFloat(2, nota);
            stmt.setInt(3, curso.getCursoActual());
            stmt.setString(4, convocatoria.getConvocatoriaActual());
            stmt.setString(5, dni_alumno);

            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException ("Problema en borrarRegistrosAntiguosGrupoPracticas");
        }
    } // fin del método actualizarRegistrosAlumnoEnGrupoPracticas



    /** Método que realiza la consulta solicitada a la Base de Datos.
     * 
     * @param grupo contiene la información del grupo de prácticas por el que
     *              se quiere filtrar. Puede ser null.
     * @param dni contiene el dni del alumno por el que se quiere filtrar.
     *            Puede ser null.
     * 
     * @return Si la ejecución es correcta, devolverá el resultado de la consulta.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public List<ListadoGrupoPractica> realizarConsultaGrupoPractica (
                                                            GrupoPractica grupo,
                                                            String dni) {

        StringBuilder sql = new StringBuilder("SELECT DISTINCT  evaluacion.ALUMNO_DNI, "
                                                            + " evaluacion.GRUPO_PRACTICA_Cod_GP,"
                                                            + " tutoria.PROFESOR_Cod_P, "
                                                            + " evaluacion.Nota_Pr "
                                            + "FROM evaluacion, tutoria "
                                            + "WHERE ((tutoria.CURSO_Cod_CURSO = ?) "
                                              + " AND (tutoria.CONVOCATORIA_idCONVOCATORIA = ?) "
                                              + " AND (evaluacion.CURSO_Cod_CURSO = ?) "
                                              + " AND (evaluacion.CONVOCATORIA_idCONVOCATORIA = ?) ");

        List<Object> values = new ArrayList<Object>();
        List<ListadoGrupoPractica> resultado = new ArrayList<ListadoGrupoPractica>();

        Curso curso = new Curso();
        Convocatoria convocatoria = new Convocatoria();
        int curso_actual = curso.getCursoActual();
        String convocatoria_actual = convocatoria.getConvocatoriaActual();
        values.add(curso_actual);
        values.add(convocatoria_actual);
        values.add(curso_actual);
        values.add(convocatoria_actual);

        int cod_aux = grupo.getCodGrupoPractica();
        if (cod_aux != -1) {
            sql.append(" AND (tutoria.GRUPO_PRACTICA_Cod_GP = ?) ");
            values.add(cod_aux);
        }

        if (this.noEstaVacio(dni)) {
            sql.append(" AND (evaluacion.GRUPO_PRACTICA_Cod_GP = ");
            sql.append("         (SELECT evaluacion.GRUPO_PRACTICA_Cod_GP ");
            sql.append("          FROM evaluacion ");
            sql.append("          WHERE ((evaluacion.ALUMNO_DNI = ?) "
                    + "              AND (evaluacion.CURSO_Cod_CURSO = ?) "
                    + "              AND (evaluacion.CONVOCATORIA_idCONVOCATORIA = ?))))  ");
            values.add(dni);
            values.add(curso_actual);
            values.add(convocatoria_actual);
        }

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        ControladorProfesor DAOProfesor = new ControladorProfesor();
        if (grupo.getTutor() != null) {
            sql.append(" AND (tutoria.PROFESOR_Cod_P = ?) ");
            values.add(DAOProfesor.obtenerCodigoProfesor(grupo.getTutor(), dataAccessObject));
        } 
        sql.append(" AND (evaluacion.GRUPO_PRACTICA_Cod_GP = tutoria.GRUPO_PRACTICA_Cod_GP)) "
                 + " ORDER BY (tutoria.GRUPO_PRACTICA_Cod_GP) ");

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
            if (rs.next()) {
                String dni_alumno = null;
                String dni_alumno2 = null;
                int grupo_practica = -1;
                int grupo_practica2 = -2;
                int cod_tutor = -1;
                int cod_tutor2 = -1;
                Float nota = new Float(0);
                Float nota2 = new Float(0);

                dni_alumno = rs.getString(1);
                grupo_practica = rs.getInt(2);
                cod_tutor = rs.getInt(3);
                nota = rs.getFloat(4);

                int num_alumnos_sin_procesar = 1;
                while (rs.next()) {
                    if (num_alumnos_sin_procesar == 0) {
                        dni_alumno = rs.getString(1);
                        grupo_practica = rs.getInt(2);
                        cod_tutor = rs.getInt(3);
                        nota = rs.getFloat(4);
                    } else {
                        dni_alumno2 = rs.getString(1);
                        grupo_practica2 = rs.getInt(2);
                        cod_tutor2 = rs.getInt(3);
                        nota2 = rs.getFloat(4);
                    }

                    num_alumnos_sin_procesar++;

                    if (num_alumnos_sin_procesar == 2) {
                        if (grupo_practica == grupo_practica2) {
                            resultado.add(new ListadoGrupoPractica(grupo_practica,
                                                                   cod_tutor,
                                                                   dni_alumno,
                                                                   dni_alumno2,
                                                                   nota,
                                                                   dataAccessObject));
                            num_alumnos_sin_procesar = 0;
                        } else {
                            resultado.add(new ListadoGrupoPractica(grupo_practica,
                                                                   cod_tutor,
                                                                   dni_alumno,
                                                                   null,
                                                                   nota,
                                                                   dataAccessObject));
                            dni_alumno = dni_alumno2;
                            grupo_practica = grupo_practica2;
                            cod_tutor = cod_tutor2;
                            nota = nota2;

                            num_alumnos_sin_procesar = 1;
                        }
                    }
                }
                if (num_alumnos_sin_procesar == 1) {
                    resultado.add(new ListadoGrupoPractica(grupo_practica,
                                                           cod_tutor,
                                                           dni_alumno,
                                                           null,
                                                           nota,
                                                           dataAccessObject));
                }

 
            }
            rs.close();
            dataAccessObject.close();
            return resultado;

        } catch (SQLException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en realizarConsultaGrupoPractica");
        } catch (RuntimeException e2) {
            dataAccessObject.rollback();
            System.out.println(e2.getMessage()+ " lol");
            throw new RuntimeException("Problema en realizarConsultaGrupoPractica 2");
        } 
    } // fin del método realizarConsultaGrupoPractica



    /** Método que se encarga de insertar los datos del grupo de prácticas
     *  en la Base de Datos.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param grupo contiene la información del grupo de prácticas al cual
     *        se quiere dar de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void insertarEnTablaGrupoPractica(GrupoPractica grupo,
                                              DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("INSERT GRUPO_PRACTICA "
                                            + "VALUES (?,?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try{
            stmt.setInt(1, grupo.getCodGrupoPractica());
            stmt.setInt(2,1);
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Problema en insertarEnTablaGrupoPractica");
        }
    } // fin del método insertarEnTablaGrupoPractica


    /** Metodo que se encarga de desactivar en la tabla "grupo_practica" de la
     *  Base de Datos todos los registros del grupo de prácticas que se quiere
     *  dar de baja. Conviene aclarar que no se elimina el registro de la tabla,
     *  sino que se pone su campo "activo" a cero.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param grupo contiene la información del grupo de prácticas que se quiere
     *        eliminar.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void desactivarGrupoPractica(GrupoPractica grupo, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("UPDATE GRUPO_PRACTICA "
                                            + "SET activo = 0 "
                                            + "WHERE (Cod_GP = ?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setInt(1, grupo.getCodGrupoPractica());
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e1) {
            throw new RuntimeException("problema en desactivarGrupoPractica");
        } catch(RuntimeException e2) {
            throw new RuntimeException(e2.getMessage());
        }
    } // fin del método desactivarGrupoPractica



    /** Método que comprueba si un grupo de prácticas, recibido por parámetro,
     *  está dado de alta en la Base de Datos.
     *
     * @param grupo grupo de prácticas del cual se quiere comprobar si está dado de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si NO está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean noEstaDadoDeAlta(GrupoPractica grupo, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM grupo_practica "
                                            + "WHERE (Cod_GP = ?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setInt(1,grupo.getCodGrupoPractica());
            
            ResultSet rs = stmt.executeQuery();

            boolean ok = rs.next();
            stmt.close();

            return (!ok);
        } catch (SQLException e) {
            throw new RuntimeException("Problema en noEstaDadoDeAlta");
        }
    } // fin del método noEstaDadoDeAlta


    /** Método que comprueba si un grupo de prácticas, recibido por parámetro,
     *  está dado de alta en la Base de Datos.
     *
     * @param grupo grupo de prácticas del cual se quiere comprobar si está dado de alta.
     *
     * @return TRUE si NO está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean noEstaDadoDeAlta(GrupoPractica grupo) {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        boolean resul = this.noEstaDadoDeAlta(grupo, dataAccessObject);
        dataAccessObject.close();
        return resul;
    } // fin del método noEstaDadoDeAlta

    
    /** Método que, al dar de alta un grupo de prácticas que ya había sido dado
     *  de alta anteriormente y se borró en algún momento, reactiva dicho grupo.
     *  Conviene aclarar que no se inserta en la tabla GRUPO_PRACTICA,
     *  sino que se pone su campo activo a 1.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param grupo contiene la información del grupo de prácticas al cual
     *        se quiere dar de alta.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    private void reactivarGrupoPractica(GrupoPractica grupo, DataAccessObject dataAccessObject) {

        StringBuilder sql = new StringBuilder("UPDATE GRUPO_PRACTICA "
                                            + "SET activo = 1 "
                                            + "WHERE (Cod_GP = ?)");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            stmt.setInt(1, grupo.getCodGrupoPractica());
            dataAccessObject.actualizar();
            stmt.close();
        } catch (SQLException e1) {
            dataAccessObject.rollback();
            throw new RuntimeException("problema en reactivarGrupoPractica");
        } 
    } // fin del método reactivarGrupoPractica


    /** Método que comprueba que grupos de prácticas están en "uso" en la convocatoria
     *  actual, y los activa en la Base de Datos. Es decir, pone su campo "activo" a 1.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void reactivarGruposPracticasEnUsoEnConvocatoriaActual(DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder(
                "UPDATE GRUPO_PRACTICA "
              + "SET activo = 1 "
              + "WHERE (Cod_GP IN (SELECT DISTINCT GRUPO_PRACTICA_Cod_GP "
                                 +"FROM tutoria  "
                                 +"WHERE ((CURSO_Cod_CURSO = ?) "
                                 + "  AND (CONVOCATORIA_idCONVOCATORIA = ?)))"
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
    } // fin del método reactivarGruposPracticasEnUsoEnConvocatoriaActual



    /** Método que, al haber un cambio de convocatoria, desactiva TODOS los gurpos
     *  de prácticas. Es decir, pone su campo "activo" a cero.
     *
     */
    public void desactivarGruposPracticas() {
        StringBuilder sql = new StringBuilder( "UPDATE GRUPO_PRACTICA "
                                             + "SET activo = 0 ");
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());

        try {
            dataAccessObject.actualizar();
            dataAccessObject.close();
        } catch (RuntimeException e1) {
            System.out.println(e1.getMessage());
            dataAccessObject.rollback();
            throw new RuntimeException("problema en reactivarGruposPracticasEnUsoEnConvocatoriaActual");
        }
    } // fin del metodo desactivarGruposPracticas



    /** Método que comprueba si un grupo de prácticas, recibido por parámetro,
     *  está dado de alta en la Base de Datos y, además, tiene su campo
     *  activo a 1.
     *
     * @param grupo grupo de prácticas del cual se quiere comprobar si está activo.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si el grupo está activo.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    private boolean estaActivo(GrupoPractica grupo, DataAccessObject dataAccessObject) {
        StringBuilder sql = new StringBuilder("SELECT * "
                                            + "FROM grupo_practica "
                                            + "WHERE ((Cod_GP = ?) AND (activo = 1))");
        PreparedStatement stmt = dataAccessObject.getPreparedStatement(sql.toString());
        try {
            stmt.setInt(1, grupo.getCodGrupoPractica());
            ResultSet rs = stmt.executeQuery();
            boolean ok = rs.next();
            stmt.close();
            return ok;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Ha ocurrido un error al consultar la Base de Datos");
        }
    } // fin del método estaActivo


     /** Método que comprueba si una cadena no está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si no está vacía.
     *
     * @return TRUE si la cadena no está vacía.
      *        FALSE en caso contrario.
     */
    private boolean noEstaVacio(String cadena) {
	return cadena != null && !"".equals(cadena);
    } // fin del método noEstaVacio


} // fin de la clase ControladorGrupoPractica