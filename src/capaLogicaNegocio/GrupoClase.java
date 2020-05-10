

package capaLogicaNegocio;

import capaDatos.ControladorGrupoClase;
import capaDatos.DataAccessObject;

/** Clase de la capa de Negocio que gestiona todo lo referente a
 * los grupos de clase.
 *
 * @author Confiencial
 */
public class GrupoClase {

    private String cod_grupo_clase;


    /** Crea un nuevo grupo de clase e inicializa el atributo cod_grupo_clase.
     *
     * @param grupo código del grupo de clase.
     */
    public GrupoClase (String grupo) {
        this.cod_grupo_clase = grupo;
    } // fin del constructor


    /** Método que comprueba si un grupo de clase está dado de alta en la 
     *  Base de Datos.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si el grupo está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean estaDadoDeAlta(DataAccessObject dataAccessObject) {
        ControladorGrupoClase DAOGrupoClase = new ControladorGrupoClase();
        return DAOGrupoClase.estaDadoDeAlta(this, dataAccessObject);
    } // fin del método estaDadoDeAlta



    /** Método que comprueba si un grupo de clase no está dado de alta en la
     *  Base de Datos.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     *
     * @return TRUE si el grupo no está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean noEstaDadoDeAlta(DataAccessObject dataAccessObject) {
        return (! this.estaDadoDeAlta(dataAccessObject));
    } // fin del método noEstaDadoDeAlta


    /** Método que comprueba si un grupo de clase no está dado de alta en la
     *  Base de Datos.
     *
     * @return TRUE si el grupo no está dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean noEstaDadoDeAlta() {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            boolean ok = (! this.estaDadoDeAlta(dataAccessObject));
            dataAccessObject.close();
            return ok;
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            throw new RuntimeException(e.getMessage());
        }

    } // fin del método noEstaDadoDeAlta


    /** Método que comprueba si un grupo de clase está dado de alta y, si lo está,
     *  lanza una excepción. En caso negativo, llama al método darAltaGrupoClase
     *  de la capa de Datos y lo da de alta.
     *  Si ocurre algún error, lanzará una excepción.
     * 
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void altaGrupoClase(DataAccessObject dataAccessObject) {
        ControladorGrupoClase DAOGrupoClase = new ControladorGrupoClase();
        DAOGrupoClase.darAltaGrupoClase(this, dataAccessObject);
    } // fin del método altaGrupoClase


    /** Método que comprueba si un grupo de clase está dado de alta y, si lo está,
     *  lanza una excepción. En caso negativo, llama al método darAltaGrupoClase
     *  de la capa de Datos y lo da de alta.
     *  Si ocurre algún error, lanzará una excepción.
     *
     */
    public void altaGrupoClase() {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            ControladorGrupoClase DAOGrupoClase = new ControladorGrupoClase();
            DAOGrupoClase.darAltaGrupoClase(this, dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e){
            dataAccessObject.rollback();
            throw new RuntimeException ("Error al actualizar la Base de Datos");
        }
    } // fin del método altaGrupoClase


    /** Método que comprueba si un grupo de clase está dado de alta en
     *  la Base de Datos y, si no lo está, lo da de alta.
     *  Si ocurre algún error, lanzará una excepción.
     * 
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void validarGrupoClase(DataAccessObject dataAccessObject) {

        ControladorGrupoClase DAOGrupoClase = new ControladorGrupoClase();

        if ( this.noEstaDadoDeAlta(dataAccessObject)){
            DAOGrupoClase.insertarEnTablaGrupoClase(this, dataAccessObject);
        }
    } // fin del método validarGrupoClase
    

    /** Método que comprueba si una cadena está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si está vacía.
     *
     * @return TRUE si la cadena está vacía. FALSE en caso contrario.
     */
    private boolean noEstaVacio(String cadena) {
	return cadena != null && !"".equals(cadena);
    } // fin del método noEstaVacio


    /** Método que devuelve el código del grupo de clase.
     *
     * @return código del grupo de clase.
     */
    public String getCodGrupoClase() {
        return this.cod_grupo_clase;
    } // fin del método getCodGrupoClase

} // fin de la clase GrupoClase