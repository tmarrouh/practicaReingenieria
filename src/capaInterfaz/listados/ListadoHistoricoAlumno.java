
package capaInterfaz.listados;

import capaDatos.DataAccessObject;
import capaLogicaNegocio.Alumno;

/** Clase que contiene la información requerida (de UN alumno para UN curso y
 *  UNA convocatoria) para mostrar el resultado de una consulta de histórico de alumnos.
 *
 * @author Confiencial
 */
public class ListadoHistoricoAlumno {

    private Alumno alumno;
    private String curso;
    private String convocatoria;
    private String nota_pr;
    private String nota_ex;
    private String nota_final;

    /** Crea e inicializa un nuevo ListadoHistoricoAlumno.
     *
     * @param dni_alumno dni del alumno.
     * @param curso curso académico en el que se obtieron las calificaciones.
     * @param convocatoria convocatoria en la que se obtieron las calificaciones..
     * @param nota_ex nota obtenida en el examen teórico.
     * @param nota_pr nota obtenida en la práctica.
     * @param nota_final nota final obtenida en la asignatura.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public ListadoHistoricoAlumno (String dni_alumno, int curso, String convocatoria,
                                   Float nota_ex, Float nota_pr, Float nota_final,
                                   DataAccessObject dataAccessObject) {
        this.alumno = new Alumno(dni_alumno);
        this.alumno.obtenerDatosAlumno(dataAccessObject);

        this.curso = Integer.toString(curso);
        this.convocatoria = convocatoria;

        this.nota_ex = Float.toString(nota_ex);
        this.nota_pr = Float.toString(nota_pr);
        this.nota_final = Float.toString(nota_final);
    } // fin del constructor

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


    /** Método que devuelve el curso académico en el que se obtuvieron las calificaciones.
     *
     * @return curso académico en el que se obtuvieron las calificaciones.
     */
    public String getCurso() {
        return this.curso;
    } // fin del método getCurso


    /** Método que devuelve la convocatoria en la que se obtuvieron las calificaciones.
     *
     * @return convocatoria en la que se obtuvieron las calificaciones.
     */
    public String getConvocatoria() {
        return this.convocatoria;
    } // fin del método getConvocatoria


    /** Método que devuelve la nota de prácticas obtenida.
     *
     * @return nota de prácticas obtenida.
     */
    public String getNota_Pr() {
        return this.nota_pr;
    } // fin del método getNota_Pr


    /** Método que devuelve la nota de examen obtenida.
     *
     * @return nota de examen obtenida.
     */
    public String getNota_Ex() {
        return this.nota_ex;
    } // fin del método getNota_Ex


    /** Método que devuelve la nota final obtenida.
     *
     * @return nota final obtenida.
     */
    public String getNota_Final() {
        return this.nota_final;
    } // fin del método getNota_Final


    /** Método que devuelve la nota final obtenida.
     *
     * @return nota final obtenida.
     */
    public float getNota_FinalEnFloat() {
        return Float.parseFloat(this.nota_final);
    } // fin del método getNota_Final

} // fin de la clase ListadoHistoricoAlumno