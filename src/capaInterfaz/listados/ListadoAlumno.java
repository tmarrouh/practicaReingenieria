

package capaInterfaz.listados;

import capaLogicaNegocio.GrupoClase;

/** Clase que contiene la información requerida (de UN alumno) para mostrar
 *  el resultado de una consulta de alumnos.
 *
 * @author Confiencial
 */
public class ListadoAlumno {

    private String num_mat;
    private String dni;
    private String nombre;
    private String apellidos;

    private GrupoClase grupo_clase;

    /** Crea e inicializa un nuevo ListadoAlumno.
     *
     * @param num_mat nº de matrícula del alumno.
     * @param dni dni del alumno.
     * @param nombre nombre del alumno.
     * @param apellidos apellidos del alumno.
     * @param grupo_clase grupo de clase donde está matriculado el alumno.
     */
    public ListadoAlumno (String num_mat, String dni, String nombre,
                          String apellidos, String grupo_clase) {

        this.num_mat = num_mat;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;

        this.grupo_clase = new GrupoClase(grupo_clase);
    } // fin del constructor


    /** Método que devuelve el nº de matrícula del alumno.
     *
     * @return nº de matrícula del alumno.
     */
    public String getNumMatricula () {
        return this.num_mat;
    } // fin del método getNumMatricula


    /** Método que devuelve el dni del alumno.
     *
     * @return dni del alumno.
     */
    public String getDNI () {
        return this.dni;
    } // fin del método getDNI


    /** Método que devuelve el nombre del alumno.
     *
     * @return nombre del alumno.
     */
    public String getNombre () {
        return this.nombre;
    } // fin dle método getNombre


    /** Método que devuelve los apellidos del alumno.
     *
     * @return apellidos del alumno.
     */
    public String getApellidos () {
        return this.apellidos;
    } // fin del método getApellidos


    /** Método que devuelve el grupo de clase donde está matriculado el alumno.
     *
     * @return grupo de clase donde está matriculado el alumno.
     */
    public String getGrupoClase () {
        return this.grupo_clase.getCodGrupoClase();
    } // fin del método getGrupoClase

} // fin de la clase ListadoAlumno