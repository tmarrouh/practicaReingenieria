
package capaLogicaNegocio;

import capaDatos.ControladorTutoria;
import capaDatos.DataAccessObject;


/** Clase de la capa de Negocio que gestiona todo lo referente a las tutorías
 *  de los grupos de prácticas.
 *
 * @author Confiencial
 */
public class Tutoria {

    private static int cod_ultima_tutoria;
/*
    private Alumno alumno1;
    private Alumno alumno2;
    private Profesor tutor;
    private GrupoPractica grupo_practica; */

    /** Crea una nueva tutoría */
    public Tutoria () {
    }




    /** Método que comprueba al arrancar el programa, cual es la clave de la
     * última tutoría dada de alta. Esto se comprueba para, si se da una nueva
     * matrícula de alta, que no se repita la clave con otras matrículas.
     */
    @SuppressWarnings("static-access")
    public void recuperarUltimoCodTutoria() {
        ControladorTutoria DAOTutoria = new ControladorTutoria();
        this.cod_ultima_tutoria = DAOTutoria.recuperarUltimoCodTutoria();
    } // fin del método recuperarUltimoCodTutoria


    /** Método que elimina de la Base de Datos TODAS las tutorías registradas
     *  para la convocatoria actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void eliminarTutoriasConvocatoriaActual(DataAccessObject dataAccessObject) {
        ControladorTutoria DAOTutoria = new ControladorTutoria();
        DAOTutoria.eliminarTutoriasConvocatoriaActual(dataAccessObject);
    } // fin del método eliminarTutoriasConvocatoriaActual



    @SuppressWarnings("static-access")
    /** Método que devuelve el último código de tutoría dado de alta.
     *
     * @return el último código de tutoría dado de alta.
     */
    public int getCodUltimaTutoria () {
        return this.cod_ultima_tutoria;
    } // fin del método getCodUltimaTutoria


    @SuppressWarnings("static-access")
    /** Método que devuelve un NUEVO código de tutoría.
     *  Es decir, un código que no esté usado.
     *
     * @return un NUEVO código de tutoría.
     */
    public int getNuevoCodTutoria () {
        this.cod_ultima_tutoria++;
        return this.cod_ultima_tutoria;
    } // fin del método getNuevoCodTutoria

} // fin de la clase Tutoria