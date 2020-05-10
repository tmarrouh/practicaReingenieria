
package capaInterfaz.listados;

import capaLogicaNegocio.GrupoClase;

/** Clase que contiene la información requerida (de UN profesor) para mostrar
 *  el resultado de una consulta de profesores.
 *
 * @author Confiencial
 */
public class ListadoProfesor {

    private int cod_profesor;

    private String nombre = null;
    private String apellidos = null;

    private GrupoClase grupo_clase1 = null;
    private GrupoClase grupo_clase2 = null;

    /** Crea e inicializa un nuevo ListadoProfesor.
     *
     * @param cod_profesor código interno del profesor.
     * @param nombre nombre del profesor.
     * @param apellidos apellidos del profesor.
     * @param grupo_clase1 primer grupo de clase impartido.
     * @param grupo_clase2 segundo grupo de clase impartido.
     */
    public ListadoProfesor (int cod_profesor, String nombre, String apellidos,
                            String grupo_clase1, String grupo_clase2) {
        this.cod_profesor = cod_profesor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.grupo_clase1 = new GrupoClase(grupo_clase1);
        this.grupo_clase2 = new GrupoClase(grupo_clase2);
    } // fin del constructor


    /** Método que devuelve el código interno del profesor.
     *
     * @return código interno del profesor.
     */
    public int getCodProfesor () {
        return this.cod_profesor;
    } // fin del método getCodProfesor


    /** Método que devuelve el nombre del profesor.
     *
     * @return nombre del profesor.
     */
    public String getNombre () {
        return this.nombre;
    } // fin del método getNombre


    /** Método que devuelve los apellidos del profesor.
     *
     * @return apellidos del profesor.
     */
    public String getApellidos () {
        return this.apellidos;
    } // fin del método getApellidos


    /** Método que devuelve el primer grupo de clase impartido por el profesor.
     *
     * @return primer grupo de clase impartido por el profesor.
     */
    public String getGrupoClase1 () {
        return this.grupo_clase1.getCodGrupoClase();
    } // fin del método getGrupoClase1


    /** Método que devuelve el segundo grupo de clase impartido por el profesor.
     *
     * @return segundo grupo de clase impartido por el profesor.
     */
    public String getGrupoClase2 () {
        return this.grupo_clase2.getCodGrupoClase();
    } // fin del método getGrupoClase2
    
} // fin de la clase ListadoProfesor