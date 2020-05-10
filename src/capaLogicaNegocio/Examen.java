

package capaLogicaNegocio;

import capaDatos.ControladorExamen;
import capaDatos.DataAccessObject;

/** Clase de la capa de Negocio que gestiona todo lo referente a los exámenes.
 *
 * @author Confiencial
 */
public class Examen {

    /** Crea un nuevo examen.
     */
    public Examen () {
    }

    /** Método que da de alta en la Base de Datos un nuevo código de examen
     *  para una nueva convocatoria. Si ocurre algún error, lanzará una excepción.
     */
    public void altaNuevoCodExamen(){
        ControladorExamen DAOExamen = new ControladorExamen();
        DAOExamen.darAltaNuevoCodExamen();
    } // fin del método altaNuevoCodPractica


    /** Método que elimina de la Base de Datos, el código de examen de la
     *  convocatoria actual.
     */
    public void bajaCodExamenConvocatoriaActual() {

        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            ControladorExamen DAOExamen = new ControladorExamen();
            DAOExamen.bajaCodExamenConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en bajaCodExamenConvocatoriaActual");            
        }
    } // fin del método bajaCodExamenConvocatoriaActual

    
    /** Método que elimina de la Base de Datos, el código de examen de la
     *  convocatoria actual.
     * 
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void bajaCodExamenConvocatoriaActual(DataAccessObject dataAccessObject) {
        ControladorExamen DAOExamen = new ControladorExamen();
        DAOExamen.bajaCodExamenConvocatoriaActual(dataAccessObject);
    } // fin del método bajaCodExamenConvocatoriaActual

} // fin de la clase Examen