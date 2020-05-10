

package capaInterfaz.listados;

import capaDatos.DataAccessObject;
import capaLogicaNegocio.Alumno;
import capaLogicaNegocio.Profesor;

/** Clase que contiene la información requerida (de UN grupo de prácticas)
 *  para mostrar el resultado de una consulta de grupos de prácticas.
 *
 * @author Confiencial
 */
public class ListadoGrupoPractica {

    private String cod_grupo;
    private Profesor tutor;
    private Alumno alumno1;
    private Alumno alumno2;
    private String nota;

    /** Crea e inicializa un nuevo ListadoGrupoPractica.
     *
     * @param cod código del grupo de prácticas.
     * @param cod_tutor código del profesor tutor del grupo de prácticas.
     * @param dni_alumno dni del integrante 1 del grupo.
     * @param dni_alumno2 dni del integrante 2 del grupo.
     * @param nota nota obtenida.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public ListadoGrupoPractica (int cod, int cod_tutor,
                                 String dni_alumno, String dni_alumno2, Float nota,
                                 DataAccessObject dataAccessObject) {
        try {
            this.cod_grupo = Integer.toString(cod);
            this.tutor = new Profesor(cod_tutor);
            this.tutor.obtenerDatos(cod_tutor, dataAccessObject);
            alumno1 = new Alumno(dni_alumno);
            alumno1.obtenerDatosAlumno(dataAccessObject);
            if (this.noEstaVacio(dni_alumno2)) {
                alumno2 = new Alumno(dni_alumno2);
                alumno2.obtenerDatosAlumno(dataAccessObject);
            } else {
                alumno2 = null;
            }
            this.nota = Float.toString(nota);
        } catch (NumberFormatException e1) {
            System.out.println(e1.getMessage());
            throw new RuntimeException ("Ha ocurrido un error al intentar conectar a la BDD 1");
        } catch (RuntimeException e2) {
            System.out.println(e2.getMessage());
            throw new RuntimeException ("Ha ocurrido un error al intentar conectar a la BDD 2");
        }
    } // fin del constructor



    /** Método que, recibiendo el dni de un alumno, obtiene y agrega los datos
     *  del mismo al listado. Si ocurre algún error o no encuentra un alumno
     *  con ese dni, lanzará una excepción.
     * 
     * @param dni_alumno dni del alumno que se quiere añadir al listado.
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void anyadirAlumno(String dni_alumno, DataAccessObject dataAccessObject) {
        alumno2 = new Alumno(dni_alumno);
        alumno2.obtenerDatosAlumno(dataAccessObject);
    }

    /** Método que devuelve el código del grupo de prácticas.
     *
     * @return código del grupo de prácticas.
     */
    public String getCodGrupo () {
        return this.cod_grupo;
    } // fin del método getCodGrupo


    /** Método que devuelve el objeto de la clase Profesor que contiene los datos
     *  del profesor tutor del grupo de prácticas.
     *
     * @return objeto de la clase Profesor que contiene los datos
     *         del profesor tutor del grupo de prácticas.
     */
    public Profesor getTutor () {
        return this.tutor;
    } // fin del método getTutor


    /** Método que devuelve el objeto de la clase Alumno que contiene los datos
     *  del integrante 1 del grupo de prácticas.
     *
     * @return objeto de la clase Alumno que contiene los datos
     *         del integrante 1 del grupo de prácticas.
     */
    public Alumno getAlumno1 () {
        return this.alumno1;
    } // fin del método getAlumno1


    /** Método que devuelve el objeto de la clase Alumno que contiene los datos
     *  del integrante 2 del grupo de prácticas.
     *
     * @return objeto de la clase Alumno que contiene los datos
     *         del integrante 2 del grupo de prácticas.
     */
    public Alumno getAlumno2 () {
        return this.alumno2;
    } // fin del método getAlumno2


    /** Método que devuelve la nota obtenida por el grupo de prácticas.
     *
     * @return nota obtenida por el grupo de prácticas.
     */
    public String getNota () {
        return this.nota;
    } // fin del método getNota


    /** Método que devuelve el nombre y apellidos, en formato String, del
     *  integrante 1 del grupo de prácticas.
     *
     * @return nombre y apellidos, en formato String, del
     *         integrante 1 del grupo de prácticas.
     */
    public String getAlumno1EnString() {
        return (alumno1.getNombre()+" "+alumno1.getApellidos());
    } // fin del método getAlumno1EnString


    /** Método que devuelve el nombre y apellidos, en formato String, del
     *  integrante 2 del grupo de prácticas.
     *
     * @return nombre y apellidos, en formato String, del
     *         integrante 2 del grupo de prácticas.
     */
    public String getAlumno2EnString() {
        return (alumno2.getNombre()+" "+alumno2.getApellidos());
    } // fin del método getAlumno2EnString


    /** Método que devuelve el nombre y apellidos, en formato String, del
     *  profesor tutor del grupo de prácticas.
     *
     * @return nombre y apellidos, en formato String, del
     *         profesor tutor del grupo de prácticas.
     */
    public String getTutorEnString() {
        return (tutor.getNombre()+" "+tutor.getApellidos());
    } // fin del método getTutorEnString



     /** Método que comprueba si una cadena no está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si no está vacía.
     *
     * @return TRUE si la cadena está vacía. FALSE en caso contrario.
     */
    private boolean noEstaVacio(String cadena) {
	return cadena != null && !"".equals(cadena);
    } // fin del método noEstaVacio

} // fin de la clase ListadoGrupoPractica