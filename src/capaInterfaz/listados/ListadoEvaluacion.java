
package capaInterfaz.listados;

import capaLogicaNegocio.Alumno;
import capaLogicaNegocio.Convocatoria;
import capaLogicaNegocio.Curso;
import capaLogicaNegocio.Evaluacion;

/** Clase que contiene la información requerida (de las calificaciones de UNA
 *  evaluación para un alumno) para mostrar el resultado de una consulta de
 *  calificaciones.
 *
 * @author Confiencial
 */
public class ListadoEvaluacion {

    private Alumno alumno;
    private Curso curso;
    private Convocatoria convocatoria;
    private Evaluacion evaluacion;

    /** Crea e inicializa un nuevo ListadoEvaluacion.
     *
     * @param alumno objeto de la clase Alumno que contiene los datos del alumno.
     * @param evaluacion objeto de la clase Evaluacion que contiene los datos de la evaluación.
     */
    public ListadoEvaluacion (Alumno alumno, Evaluacion evaluacion) {
        this.alumno = alumno;
        this.evaluacion = evaluacion;
        this.curso = new Curso(evaluacion.getCod_Curso());
        this.convocatoria = new Convocatoria(evaluacion.getCod_Convocatoria());
    } // fin del constructor 

    /** Método que devuelve el código de evaluación.
     *
     * @return código de evaluación.
     */
    public int getCodEv() {
        return this.evaluacion.codEvaluacion();
    } // fin del método getCodEv


    /** Método que devuelve el curso de la evaluación.
     *
     * @return curso de la evaluación.
     */
    public String getCurso() {
        return this.curso.getCursoEnString();
    } // fin del método getCurso


    /** Método que devuelve la convocatoria de la evaluación.
     *
     * @return convocatoria de la evaluación.
     */
    public String getConvocatoria() {
        return this.convocatoria.getConvocatoria();
    } // fin del método getConvocatoria


    /** Método que devuelve el nº de matrícula del alumno.
     *
     * @return nº de matrícula del alumno.
     */
    public String getN_MatAlumno() {
        return alumno.getN_Mat();
    } // fin del método getN_MatAlumno


    /** Método que devuelve el dni del alumno.
     *
     * @return dni del alumno.
     */
    public String getDNIAlumno() {
        return alumno.getDNI();
    } // fin del método getDNIAlumno


    /** Método que devuelve el nombre del alumno.
     *
     * @return nombre del alumno.
     */
    public String getNombreAlumno() {
        return alumno.getNombre();
    } // fin del método getNombreAlumno


    /** Método que devuelve los apellidos del alumno.
     *
     * @return apellidos del alumno.
     */
    public String getApellidosAlumno() {
        return this.alumno.getApellidos();
    } // fin del método getApellidosAlumno


    /** Método que devuelve el tipo de evaluación.
     *
     * @return tipo de evaluación.
     */
    public String getEv_Continua() {
        if (this.evaluacion.getEs_Ev_Continua()) {
            return "SI";
        }
        return "NO";
    } // fin del método getEv_Continua


    /** Método que indica si el examen de la evaluación está convalidado o no.
     *
     * @return TRUE si el examen está convalidado.
     *         FALSE en caso contrario.
     */
    public boolean getEx_Convalidado() {
       return this.evaluacion.getExamenConvalidado();
    } // fin del método getEx_Convalidado


    /** Método que indica si la práctica de la evaluación está convalidada o no.
     *
     * @return TRUE si la práctica está convalidada.
     *         FALSE en caso contrario.
     */
    public boolean getPr_Convalidada() {
       return this.evaluacion.getPracticaConvalidada();
    } // fin del método getPr_Convalidada


    /** Método que devuelve la nota obtenida de evaluación continua.
     *
     * @return nota obtenida de evaluación continua.
     */
    public String getNota_Ev_Continua() {
        return Float.toString(this.evaluacion.getNota_Ev_C());
    } // fin del método getNota_Ev_Continua


    /** Método que devuelve la nota obtenida de la práctica en formato String.
     *
     * @return nota obtenida de la práctica.
     */
    public String getNotaPractica() {
        return Float.toString(this.evaluacion.getNota_Practica());
    } // fin del método getNotaPractica


    /** Método que devuelve la nota obtenida en el ejercicio 1 del examen.
     *
     * @return nota obtenida en el ejercicio 1 del examen.
     */
    public String getNota_P1() {
        return Float.toString(this.evaluacion.getNota_P1());
    } // fin del método getNota_P1


    /** Método que devuelve la nota obtenida en el ejercicio 2 del examen.
     *
     * @return nota obtenida en el ejercicio 2 del examen.
     */
    public String getNota_P2() {
        return Float.toString(this.evaluacion.getNota_P2());
    } // fin del método getNota_P2


    /** Método que devuelve la nota obtenida en el ejercicio 3 del examen.
     *
     * @return nota obtenida en el ejercicio 3 del examen.
     */
    public String getNota_P3() {
        return Float.toString(this.evaluacion.getNota_P3());
    } // fin del método getNota_P3


    /** Método que devuelve la nota obtenida en el ejercicio 4 del examen.
     *
     * @return nota obtenida en el ejercicio 4 del examen.
     */
    public String getNota_P4() {
        return Float.toString(this.evaluacion.getNota_P4());
    } // fin del método getNota_P4


    /** Método que devuelve la nota total obtenida en el examen.
     *
     * @return nota total obtenida en el examen.
     */
    public String getNota_Ex() {
        return Float.toString(this.evaluacion.getNota_Ex());
    } // fin del método getNota_Ex


    /** Método que devuelve la nota obtenida de la práctica.
     *
     * @return nota obtenida de la práctica.
     */
    public String getNota_Pr() {
        return Float.toString(this.evaluacion.getNota_Practica());
    } // fin del método getNota_Pr


    /** Método que devuelve la nota final obtenida en la asignatura.
     *
     * @return nota final obtenida en la asignatura.
     */
    public String getNota_Final() {
        return Float.toString(this.evaluacion.getNota_Final());
    } // fin del método getNota_Final


    /** Método que devuelve el código del examen realizado.
     *
     * @return código del examen realizado.
     */
    public String getCodExamen() {
        return this.evaluacion.getCod_Examen();
    } // fin del método getCodExamen


    /** Método que devuelve el código de la práctica realizada.
     *
     * @return código de la práctica realizada.
     */
    public String getCodPractica() {
        return this.evaluacion.getCod_Practica();
    } // fin del método getCodPractica


    /** Método que devuelve el objeto de la clase Alumno que contiene los datos
     *  del alumno.
     *
     * @return objeto de la clase Alumno que contiene los datos
     *         del alumno.
     */
    public Alumno getAlumno()  {
        return this.alumno;
    } // fin del método getAlumno


    /** Método que devuelve el objeto de la clase Evaluacion que contiene los datos
     *  de la evaluación.
     *
     * @return objeto de la clase Evaluacion que contiene los datos
     *         de la evaluación.
     */
    public Evaluacion getEvaluacion() {
        return evaluacion;
    }// fin del método getEvaluacion

} // fin de la clase ListadoEvaluacion