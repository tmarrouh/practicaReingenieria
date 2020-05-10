
package capaLogicaNegocio;

/** Clase de la capa de Negocio que gestiona todo lo referente a los
 *  tipos de evaluación.
 *
 * @author Confiencial
 */
public class TipoEvaluacion {

    private float peso_ex;
    private float peso_pr;
    private float peso_evC;

    private boolean ev_c;

    /** Crea un nuevo tipo de evaluación e inicializa los atributos peso_ex,
     *  peso_pr y peso_evC.
     *
     * @param es_evC indica el tipo de evaluación (contínua o no).
     */
    public TipoEvaluacion(boolean es_evC) {
        if (es_evC) {
            this.peso_ex = (float) 0.40;
            this.peso_pr = (float) 0.40;
            this.peso_evC = (float) 0.20;
        } else {
            this.peso_ex = (float) 0.80;
            this.peso_pr = (float) 0.20;
            this.peso_evC = (float) 0.00;
        }
    } // fin del constructor


    /** Método que devuelve el porcentaje que aporta sobre la nota final, la
     *  nota del examen teórico.
     *
     * @return porcentaje que aporta sobre la nota final, la
     *         nota del examen teórico.
     */
    public Float getPesoEx() {
        return this.peso_ex;
    } // fin del método getPesoEx


    /** Método que devuelve el porcentaje que aporta sobre la nota final, la
     *  nota de la práctica.
     *
     * @return porcentaje que aporta sobre la nota final, la
     *         nota de la práctica.
     */
    public Float getPesoPr() {
        return this.peso_pr;
    } // fin del método getPesoPr


    /** Método que devuelve el porcentaje que aporta sobre la nota final, la
     *  nota de la evaluación continua.
     *
     * @return porcentaje que aporta sobre la nota final, la
     *         nota de la evaluación continua.
     */
    public Float getPesoEvC() {
        return this.peso_evC;
    } // fin del método getPesoEvC
 
} // fin de la clase TipoEvaluacion