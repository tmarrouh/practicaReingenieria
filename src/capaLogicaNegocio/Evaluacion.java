
package capaLogicaNegocio;

import capaDatos.ControladorEvaluacion;
import capaDatos.DataAccessObject;
import capaInterfaz.listados.ListadoEvaluacion;
import java.util.List;

/** Clase de la capa de Negocio que gestiona todo lo referente a las evaluaciones.
 *
 * @author Confiencial
 */
public class Evaluacion {

    private static int cod_ultima_evaluacion;

    private int cod_evaluacion;

    private String dni_alumno = null;
    private String n_mat_alumno = null;
    private int cod_curso;
    private String cod_convocatoria = null;
    private String cod_practica = null;
    private String cod_examen = null;

    private int cod_grupo_practicas = -1;
    private boolean ev_continua = true;
    private TipoEvaluacion tipo_evaluacion;

    private float nota_pr = 0;
    private float nota_ev_c = 0;
    private float nota_p1 = 0;
    private float nota_p2 = 0;
    private float nota_p3 = 0;
    private float nota_p4 = 0;
    private float nota_ex = 0;
    private float nota_final = 0;

    private boolean practica_convalidada = false;
    private boolean examen_convalidado = false;


    /** Crea una nueva evaluación.
     *
     */
    public Evaluacion () {
    } // fin del constructor


    /** Crea una nueva evaluación e inicializa el atributo dni.
     *
     * @param dni dni del alumno.
     */
    public Evaluacion (String dni){
        this.dni_alumno = dni;
    } // fin del constructor


    /** Crea una nueva evaluación e inicializa los atributos: cod_evaluacion, dni,
     *  n_mat, ev_continua, nota_pr, nota_ev_c, nota_p1, nota_p2, nota_p3, nota_p4,
     *  nota_final, practica_convalidada, examen_convalidado, convocatoria, curso,
     *  cod_examen y cod_practica.
     *
     * @param cod_evaluacion código de evaluación.
     * @param dni dni del alumno.
     * @param n_mat nº de matrícula del alumno.
     * @param ev_continua tipo de evaluación (contínua o no).
     * @param nota_pr nota de prácticas.
     * @param nota_ev_c nota de evaluación continua.
     * @param nota_p1 nota del ejercicio 1 del examen teórico.
     * @param nota_p2 nota del ejercicio 2 del examen teórico.
     * @param nota_p3 nota del ejercicio 3 del examen teórico.
     * @param nota_p4 nota del ejercicio 4 del examen teórico.
     * @param nota_final nota final de la asignatura.
     * @param practica_convalidada indica si la práctica está convalidada de convocatorias anteriores.
     * @param examen_convalidado indica si el examen está convalidado de convocatorias anteriores.
     * @param convocatoria convocatoria de la evaluación.
     * @param curso curso de la evaluación.
     * @param cod_examen código del examen. Si está convalidado, será el código del examen aprobado.
     * @param cod_practica código de la práctica. Si está convalidada, será el código de la práctica aprobada.
     */
    public Evaluacion (int cod_evaluacion, String dni, String n_mat,
                       boolean ev_continua, Float nota_pr, Float nota_ev_c,
                       Float nota_p1, Float nota_p2, Float nota_p3,
                       Float nota_p4, Float nota_final,
                       boolean practica_convalidada, boolean examen_convalidado,
                       String convocatoria, int curso,  String cod_examen,
                       String cod_practica ) {

        this.cod_evaluacion = cod_evaluacion;

        this.n_mat_alumno = n_mat;
        this.dni_alumno = dni;
        this.ev_continua = ev_continua;
        this.cod_convocatoria = convocatoria;
        this.cod_curso = curso;
        this.tipo_evaluacion = new TipoEvaluacion(ev_continua);
        this.nota_ev_c = nota_ev_c;
        this.nota_pr = nota_pr;

        this.nota_p1 = nota_p1;
        this.nota_p2 = nota_p2;
        this.nota_p3 = nota_p3;
        this.nota_p4 = nota_p4;

        this.nota_ex = this.nota_p1 + this.nota_p2 + this.nota_p3 + this.nota_p4;
        this.nota_final = nota_final;

        this.examen_convalidado = examen_convalidado;
        this.practica_convalidada = practica_convalidada;

        this.cod_examen = cod_examen;
        this.cod_practica = cod_practica;
    } // fin del constructor



    /** Crea una nueva evaluación e inicializa los atributos: cod_evaluacion,
     *  ev_continua, nota_pr, nota_ev_c, nota_p1, nota_p2, nota_p3, nota_p4,
     *  practica_convalidada, examen_convalidado, cod_examen y cod_practica.
     *
     * @param cod_ev código de evaluación.
     * @param nota_p1 nota del ejercicio 1 del examen teórico.
     * @param nota_p2 nota del ejercicio 2 del examen teórico.
     * @param nota_p3 nota del ejercicio 3 del examen teórico.
     * @param nota_p4 nota del ejercicio 4 del examen teórico.
     * @param nota_pr nota de prácticas.
     * @param nota_evC nota de evaluación continua.
     * @param evC tipo de evaluación (contínua o no).
     * @param ex_conv indica si el examen está convalidado de convocatorias anteriores.
     * @param pr_conv indica si la práctica está convalidada de convocatorias anteriores.
     * @param cod_ex código del examen. Si está convalidado, será el código del examen aprobado.
     * @param cod_pr código de la práctica. Si está convalidada, será el código de la práctica aprobada.
     */
    public Evaluacion(int cod_ev, String nota_p1, String nota_p2,
                      String nota_p3, String nota_p4, String nota_pr,
                      String nota_evC, boolean evC, boolean ex_conv,
                      boolean pr_conv, String cod_ex, String cod_pr) {
        this.cod_evaluacion = cod_ev;
        this.nota_p1 = Float.parseFloat(nota_p1);
        this.nota_p2 = Float.parseFloat(nota_p2);
        this.nota_p3 = Float.parseFloat(nota_p3);
        this.nota_p4 = Float.parseFloat(nota_p4);
        this.nota_ex = this.nota_p1 + this.nota_p2 + this.nota_p3 + this.nota_p4;
        this.nota_pr = Float.parseFloat(nota_pr);
        this.nota_ev_c = Float.parseFloat(nota_evC);
        this.ev_continua = evC;

        this.tipo_evaluacion = new TipoEvaluacion(ev_continua);
        this.examen_convalidado = ex_conv;
        this.practica_convalidada = pr_conv;
        this.cod_examen = cod_ex;
        this.cod_practica = cod_pr;
    } // fin del constructor



    /** Crea una nueva evaluación e inicializa los atributos: dni, convocatoria
     *  y curso.
     *
     * @param dni dni del alumno.
     * @param convocatoria convocatoria de la evaluación.
     * @param curso curso de la evaluación.
     */
    public Evaluacion (String dni, int curso, String convocatoria) {

        this.dni_alumno = dni;
        this.cod_curso = curso;
        this.cod_convocatoria = convocatoria;
        if (convocatoria.equals("ordinaria")) {
            this.ev_continua = true;
        }
        else {
            this.ev_continua = false;
        }
        this.tipo_evaluacion = new TipoEvaluacion(ev_continua);
        this.cod_grupo_practicas = -1;

        this.nota_ev_c = 0;
        this.nota_p1 = 0;
        this.nota_p2 = 0;
        this.nota_p3 = 0;
        this.nota_p4 = 0;
        this.nota_pr = 0;
        this.nota_final = 0;
        this.cod_examen = (convocatoria+Integer.toString(curso));
        this.cod_practica = (convocatoria+Integer.toString(curso));
    } // fin del constructor



    /** Método que comprueba si la evaluación  está dada de alta y, si no lo está,
     * llama al método intertarEnTablaEvaluacion de la clase ControladorEvaluacion para
     * realizar el alta de la misma. Si ocurre algún error, lanzará una excepción.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void altaEvaluacion(DataAccessObject dataAccessObject) {
        ControladorEvaluacion DAOEvaluacion = new ControladorEvaluacion ();
        if (DAOEvaluacion.estaDadaDeAlta(this, dataAccessObject)){
            throw new RuntimeException("El alumno ya está dado de alta en la convocatoria actual");
        } else {
            DAOEvaluacion.intertarEnTablaEvaluacion(this, dataAccessObject);
        }
    } // fin del método altaEvaluacion


    /** Método que actualiza en la Base de Datos, las calificaciones de los alumnos
     *  tras una modificación por parte del usuario (Módulo Modificar calificaciones).
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param resultado contiene las modificaciones realizadas.
     */
    public void actualizarNotasEvaluaciones (List<Evaluacion> resultado) {
        ControladorEvaluacion DAOEvaluacion = new ControladorEvaluacion();
        DAOEvaluacion.actualizarNotasEvaluaciones(resultado);
    } // fin del método actualizarNotasEvaluacion


    /** Método que comprueba si un alumno ha aprobado alguna parte de la asignatura
     *  en la convocatoria anterior. En caso afirmativo, actualiza esa nota a la
     *  convocatoria que esté cursando actualmente.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void actualizarNotasAprobadasConvocatoriaAnterior(DataAccessObject dataAccessObject) {

        int curso_convocatoria_anterior;
        String convocatoria_anterior;
        Convocatoria convocatoria = new Convocatoria();
        Curso curso = new Curso();
        this.cod_curso = curso.getCursoActual();
        this.cod_convocatoria = convocatoria.getConvocatoriaActual();
        ControladorEvaluacion DAOEvaluacion = new ControladorEvaluacion ();
        if (this.cod_convocatoria.equals("ordinaria")) {
            curso_convocatoria_anterior = this.cod_curso - 1;
            convocatoria_anterior = "extraordinaria";
        } else {
            curso_convocatoria_anterior = this.cod_curso;
            convocatoria_anterior = "ordinaria";
        }

        try {
            if (DAOEvaluacion.tieneExamenAprobado(this.dni_alumno, curso_convocatoria_anterior, convocatoria_anterior,
                                                  dataAccessObject)) {

                this.nota_p1 = DAOEvaluacion.obtenerNotaPiExamen(this.dni_alumno, 1,
                               curso_convocatoria_anterior, convocatoria_anterior, dataAccessObject);
                this.nota_p2 = DAOEvaluacion.obtenerNotaPiExamen(this.dni_alumno, 2,
                               curso_convocatoria_anterior, convocatoria_anterior, dataAccessObject);
                this.nota_p3 = DAOEvaluacion.obtenerNotaPiExamen(this.dni_alumno, 3,
                               curso_convocatoria_anterior, convocatoria_anterior, dataAccessObject);
                this.nota_p4 = DAOEvaluacion.obtenerNotaPiExamen(this.dni_alumno, 4,
                               curso_convocatoria_anterior, convocatoria_anterior, dataAccessObject);

                this.cod_examen = DAOEvaluacion.obtenerCodExamenAprobado(
                                                  this.dni_alumno,
                                                  curso_convocatoria_anterior, convocatoria_anterior,
                                                  dataAccessObject);

                this.ev_continua = false;
                this.examen_convalidado = true;
                }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage()); //"Error al calcular las notas del examen");
         }

        try {
            if (DAOEvaluacion.tienePracticaAprobada(this.dni_alumno, curso_convocatoria_anterior,
                                                    convocatoria_anterior,
                                                    dataAccessObject)) {

                this.nota_pr = DAOEvaluacion.obtenerNotaPractica(this.dni_alumno, curso_convocatoria_anterior,
                                                                 convocatoria_anterior,
                                                                 dataAccessObject);

                this.cod_practica = DAOEvaluacion.obtenerCodPracticaAprobada(
                                                  this.dni_alumno,
                                                  curso_convocatoria_anterior, convocatoria_anterior,
                                                  dataAccessObject);
                this.ev_continua = false;
                this.practica_convalidada = true;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage()); //"Error al calcular las notas de la practica");
         }

        if ((examen_convalidado) && (practica_convalidada)) {
          //  dataAccessObject.rollback();
            throw new RuntimeException("El alumno ya tiene aprobada la asignatura");
        }
    } // fin del método actualizarNotasAprobadasConvocatoriaAnterior


    /** Método que elimina de la Base de Datos TODAS las evaluaciones registradas
     *  para la convocatoria actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void eliminarEvaluacionesConvocatoriaActual(DataAccessObject dataAccessObject) {
        ControladorEvaluacion DAOEvaluacion = new ControladorEvaluacion();
        DAOEvaluacion.eliminarEvaluacionesConvocatoriaActual(dataAccessObject);
    } // fin del método eliminarEvaluacionesConvocatoriaActual


    /** Método que elimina de la Base de Datos TODAS las evaluaciones registradas
     *  para la convocatoria actual.
     */
    public void eliminarEvaluacionesConvocatoriaActual() {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            ControladorEvaluacion DAOEvaluacion = new ControladorEvaluacion();
            DAOEvaluacion.eliminarEvaluacionesConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    } // fin del método eliminarEvaluacionesConvocatoriaActual



    /** Método que realiza la consulta solicitada y devuelve el resultado.
     *
     * @param dni dni del alumno por el que se quiere filtrar la consulta.
     *        Puede ser null.
     * @param n_mat nº de matrícula del alumnos por el que se quiere filtrar
     *        la consulta. Puede ser null.
     * @param grupo_clase grupo de clase por el que se quiere filtrar la consulta.
     *        Puede ser null.
     * @param convocatoria convocatoria por la que se quiere filtrar la consulta.
     *        Puede ser null.
     * @param curso curso por la que se quiere filtrar la consulta. Puede ser null.
     *
     * @return Si la ejecución ha sido correcta, devuelve el resultado de la consulta.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public List<ListadoEvaluacion> consultarEvaluacion(String dni,
                                                       String n_mat,
                                                       String grupo_clase,
                                                       String convocatoria,
                                                       int curso) {
        ControladorEvaluacion DAOEvaluacion = new ControladorEvaluacion();
        return DAOEvaluacion.realizarConsultarEvaluacion(dni, n_mat, grupo_clase, 
                                                         convocatoria, curso);
    } // fin del método consultarEvaluacion


    /** Método que comprueba al arrancar el programa, cual es la clave de la
     * última evaluacion dada de alta. Esto se comprueba para, si se da una nueva
     * evaluación de alta, que no se repita la clave con otras evaluaciones.
     *
     */
    @SuppressWarnings("static-access")
    public void recuperarUltimoCodEvaluacion() {
        ControladorEvaluacion DAOEvaluacion = new ControladorEvaluacion ();
        this.cod_ultima_evaluacion = DAOEvaluacion.recuperarUltimoCodEvaluacion();
    } // fin del método recuperarUltimoCodEvaluacion




    @SuppressWarnings("static-access")

    /** Método que devuelve un NUEVO código de evaluación.
     *  Es decir, un código que no esté usado.
     *
     * @return un NUEVO código de evaluación.
     */
    public int getNuevoCodEvaluacion() {
        this.cod_ultima_evaluacion++;
        return  this.cod_ultima_evaluacion;
    } // fin del método getNuevoCodEvaluacion



    @SuppressWarnings("static-access")
    /** Método que devuelve el último código de evaluación dado de alta.
     *
     * @return el último código de evaluación dado de alta.
     */
    public int getCodUltimaEvaluacion() {
        return this.cod_ultima_evaluacion;
    } // fin del método getCodUltimaEvaluacion


    /** Método que devuelve el dni del alumno evaluado.
     *
     * @return dni del alumno evaluado.
     */
    public String getDNI() {
        return this.dni_alumno;
    } // fin del método getDNI


    /** Método que devuelve el nº de matrícula del alumno evaluado.
     *
     * @return nº de matrícula del alumno evaluado.
     */
    public String getN_Mat() {
        return this.n_mat_alumno;
    } // fin del método getN_Mat


    /** Método que devuelve el código del curso de la evaluación.
     *
     * @return código del curso de la evaluación.
     */
    public int getCod_Curso() {
        return this.cod_curso;
    } // fin del método getCod_Curso


    /** Método que devuelve el código de la convocatoria de la evaluación.
     *
     * @return código de la convocatoria de la evaluación.
     */
    public String getCod_Convocatoria() {
        return this.cod_convocatoria;
    } // fin del método getCod_Convocatoria


    /** Método que devuelve el código del grupo de prácticas de la evaluación.
     *
     * @return código del grupo de prácticas de la evaluación.
     */
    public int getGrupo_Practica() {
        return  this.cod_grupo_practicas;
    } // fin del método getGrupo_Practica


    /** Método que devuelve el tipo de evaluación.
     *
     * @return TRUE si es evaluación continua.
     *         FALSE en caso contrario.
     */
    public boolean getEs_Ev_Continua() {
        if (ev_continua) {
            return true;
        } else {
            return false;
        }
    } // fin del método getEs_Ev_Continua


    /** Método que devuelve el código de la práctica de la evaluación.
     *
     * @return código de la práctica de la evaluación.
     */
    public String getCod_Practica() {
        return this.cod_practica;
    } // fin del método getCod_Practica


    /** Método que devuelve el código del examen de la evaluación.
     *
     * @return código del examen de la evaluación.
     */
    public String getCod_Examen() {
        return this.cod_examen;
    } // fin del método getCod_Examen



    /** Método que devuelve la nota de prácticas.
     *
     * @return nota de prácticas.
     */
    public float getNota_Practica() {
        return this.nota_pr;
    } // fin del método getNota_Practica


    /** Método que devuelve la nota de evaluación continua.
     *
     * @return nota de evaluación continua.
     */
    public float getNota_Ev_C() {
        return this.nota_ev_c;
    } // fin del método getNota_Ev_C


    /** Método que devuelve la nota del ejercicio 1 del examen teórico.
     *
     * @return nota del ejercicio 1 del examen teórico.
     */
    public float getNota_P1() {
        return this.nota_p1;
    } // fin del método getNota_P1


    /** Método que devuelve la nota del ejercicio 2 del examen teórico.
     *
     * @return nota del ejercicio 2 del examen teórico.
     */
    public float getNota_P2() {
        return this.nota_p2;
    } // fin del método getNota_P2


    /** Método que devuelve la nota del ejercicio 3 del examen teórico.
     *
     * @return nota del ejercicio 3 del examen teórico.
     */
    public float getNota_P3() {
        return this.nota_p3;
    } // fin del método getNota_P3


    /** Método que devuelve la nota del ejercicio 4 del examen teórico.
     *
     * @return nota del ejercicio 4 del examen teórico.
     */
    public float getNota_P4() {
        return this.nota_p4;
    } // fin del método getNota_P4


    /** Método que devuelve la nota total del examen teórico.
     *
     * @return nota total del examen teórico.
     */
    public float getNota_Ex() {
        return (this.nota_ex);
    } // fin del método getNota_Ex


    /** Método que calcula y devuelve la nota final de la asignatura.
     *
     * @return nota final de la asignatura.
     */
    public float getNota_Final() {
        float peso_ex = this.tipo_evaluacion.getPesoEx();
        float peso_pr = this.tipo_evaluacion.getPesoPr();
        float peso_evC = this.tipo_evaluacion.getPesoEvC();
        this.nota_final = (this.nota_ex*peso_ex + 
                           this.nota_pr*peso_pr + 
                           this.nota_ev_c*peso_evC);
        float nota_min_ex;
        float nota_min_pr = (float) 5.0;
        int nota = (int) (this.nota_final*100);
        this.nota_final = (float) (nota / 100.0);
        if (this.ev_continua) {
            nota_min_ex = (float) 4.0;
        } else {
            nota_min_ex = (float) 5.0;
        }
        if ((this.nota_ex >= nota_min_ex) && (this.nota_pr >= nota_min_pr)) {
            return this.nota_final;
        } else {
            if (this.nota_final < 5.0) {
                return this.nota_final;
            } else {
                return ((float) 4.0);
            }
        }            
    } // fin del método getNota_Final


    /** Método que indica si la práctica ha sido convalidada o no.
     *
     * @return TRUE si ha sido convalidada.
     *         FALSE en caso contrario.
     */
    public boolean getPracticaConvalidada() {
        return this.practica_convalidada;
    } // fin del método getPracticaConvalidada


    /** Método que indica si el examen ha sido convalidado o no.
     *
     * @return TRUE si ha sido convalidado.
     *         FALSE en caso contrario.
     */
    public boolean getExamenConvalidado() {
        return this.examen_convalidado;
    } // fin del método getExamenConvalidado


    /** Método que devuelve el código de la evaluación.
     *
     * @return código de la evaluación.
     */
    public int codEvaluacion() {
        return this.cod_evaluacion;
    } // fin del método codEvaluacion

} // fin de la clase Evaluación