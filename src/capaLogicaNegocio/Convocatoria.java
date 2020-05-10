
package capaLogicaNegocio;

import capaDatos.ControladorConvocatoria;
import capaDatos.DataAccessObject;

/** Clase que contiene la información de la convocatoria.
 *  El atributo público convocatoria contendrá el curso actual.
 *
 * @author Confiencial
 */
public class Convocatoria {

    private static String convocatoria_actual;

    private String cod_convocatoria = null;
    private int actual;

    /** Crea una nueva convocatoria.
     *
     */
    public Convocatoria () {
    } // fin del constructor

    /** Crea una nueva convocatoria e incializa el atributo convocatoria.
     *
     * @param convocatoria código de la convocatoria.
     */
    public Convocatoria (String convocatoria) {
        this.cod_convocatoria = convocatoria;
    } // fin del constructor

    /** Crea una nueva convocatoria e incializa los atributos convocatoria y actual.
     *
     * @param convocatoria código de la convocatoria.
     * @param actual indica si la convocatoria es la que se cursa actualmente o no.
     */
    public Convocatoria (String convocatoria, int actual) {
        this.cod_convocatoria = convocatoria;
        this.actual = actual;
    } // fin del constructor

    /** Método que cambia, en la Base de Datos, la convocatoria actual.
     *  Si la convocatoria actual es "ordinaria", cambia a "extraordinaria"
     *  y viceversa.
     */
    public void cambioConvocatoria() {
        ControladorConvocatoria DAOConvocatoria = new ControladorConvocatoria();
        DAOConvocatoria.cambioConvocatoria();
        GrupoPractica grupos = new GrupoPractica();
        grupos.desactivarGruposPracticas();
    } // fin del método cambioConvocatoria


    /** Método que realiza la recuperación del BackUp de datos cuando
     *  el usuario pulsa el botón de "deshacer última decisión".
     *  Se eliminarán TODOS los registros de la convocatoria
     *  extraordinaria del curso actual, y recuperará los datos de la convocatoria
     *  anterior. Es decir, el estado de la Base de Datos y de la aplicación
     *  será el último estado en el que se encontraba antes de cambiar de
     *  convocatoria.
     */
    public void BackUpConvocatoria() {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.eliminarEvaluacionesConvocatoriaActual(dataAccessObject);
            Tutoria tutorias = new Tutoria();
            tutorias.eliminarTutoriasConvocatoriaActual(dataAccessObject);
            Examen examen = new Examen();
            examen.bajaCodExamenConvocatoriaActual(dataAccessObject);
            Practica practica = new Practica();
            practica.bajaCodPracticaConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en BackUpConvocatoria");
        }

        this.cambioConvocatoria();
        this.recuperarConvocatoriaActual();

        dataAccessObject = DataAccessObject.getDataAccessObjectConnected();

        try{
            GrupoPractica grupos = new GrupoPractica();
            Profesor profesores = new Profesor();
            grupos.reactivarGruposPracticasEnUsoEnConvocatoriaActual(dataAccessObject);
            profesores.reactivarProfesoresConRegistrosEnConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en BackUpConvocatoria 2");
        }
    } // fin del método BackUpConvocatoria


    /** Método que, a través de la clase ControladorConvocatoria, consulta en la
     * Base de Datos cual es la convocatoria actual y lo asigna al atributo
     * estático convocatoria_actual.
     */
    @SuppressWarnings("static-access")
    public void recuperarConvocatoriaActual() {
        ControladorConvocatoria DAOConvocatoria = new ControladorConvocatoria ();
        this.convocatoria_actual = DAOConvocatoria.recuperarConvocatoriaActual ();
    } // fin del método recuperarConvocatoriaActual


    /** Método que devuelve el código de la convocatoria.
     *
     * @return código de la convocatoria.
     */
    public String getConvocatoria() {
        return this.cod_convocatoria;
    } // fin del método getConvocatoria


    /** Método que devuelve el atributo actual de la convocatoria.
     *  Indica si la convocatoria es la que se cursa actualmente o no.
     *
     * @return código de la convocatoria.
     */
    public int getActual() {
        return this.actual;
    }


    @SuppressWarnings("static-access")
    /** Método que devuelve el código de la convocatoria actual
     * 
     * @return código de la convocatoria actual en forma de cadena (String)
     */
    public String getConvocatoriaActual() {
        return this.convocatoria_actual;
    } // fin del método getConvocatoriaActual

} // fin de la clase Convocatoria