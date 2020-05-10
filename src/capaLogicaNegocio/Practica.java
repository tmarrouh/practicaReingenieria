
package capaLogicaNegocio;

import capaDatos.ControladorPractica;
import capaDatos.DataAccessObject;

/** Clase de la capa de Negocio que gestiona todo lo referente a las prácticas.
 *
 * @author Confiencial
 */
public class Practica {


    /** Crea una nueva práctica.
     */
    public Practica () {
    }

    /** Método que da de alta en la Base de Datos un nuevo código de prácticas
     *  para una nueva convocatoria. Si ocurre algún error, lanzará una excepción.
     */
    public void altaNuevoCodPractica(){
        ControladorPractica DAOPractica = new ControladorPractica();
        DAOPractica.darAltaNuevoCodPractica();
    } // fin del método altaNuevoCodPractica

    
    /** Método que elimina de la Base de Datos, el código de prácticas de la
     *  convocatoria actual.
     */
    public void bajaCodPracticaConvocatoriaActual() {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            ControladorPractica DAOPractica = new ControladorPractica();
            DAOPractica.bajaCodPracticaConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en bajaCodPracticaConvocatoriaActual");
        }
    } // fin del método bajaCodPracticaConvocatoriaActual



    /** Método que elimina de la Base de Datos, el código de prácticas de la
     *  convocatoria actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void bajaCodPracticaConvocatoriaActual(DataAccessObject dataAccessObject) {
        try {
            ControladorPractica DAOPractica = new ControladorPractica();
            DAOPractica.bajaCodPracticaConvocatoriaActual(dataAccessObject);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en bajaCodPracticaConvocatoriaActual");
        }
    } // fin del método bajaCodPracticaConvocatoriaActual

} // fin de la clase Practica