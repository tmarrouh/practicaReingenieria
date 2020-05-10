

package capaLogicaNegocio;

import capaDatos.ControladorProfesor;
import capaDatos.ControladorTutoria;
import capaDatos.DataAccessObject;
import capaInterfaz.listados.ListadoProfesor;
import java.util.List;

/** Clase de la capa de Negocio que gestiona todo lo referente a los profesores.
 *
 * @author Confiencial
 */
public class Profesor {

    private static int clave_ultimo_profesor;

    private int cod_profesor;

    private String nombre = null;
    private String apellidos = null;

    private GrupoClase grupo_clase1 = null;
    private GrupoClase grupo_clase2 = null;


    /** Crea un nuevo profesor. */
    public Profesor () {
    } // fin del constructor


    /** Crea un nuevo profesor e inicializa el atributo cod_profesor.
     *
     * @param cod código del profesor.
     */
    public Profesor (int cod) {
        this.cod_profesor = cod;
    } // fin del constructor


    /** Crea un nuevo profesor e inicializa los atributos nombre y apellidos.
     *
     * @param nombre nombre del profesor.
     * @param apellidos apellidos del profesor.
     */
    public Profesor (String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    } // fin del constructor


    /** Crea un nuevo profesor e inicializa los atributos nombre, apellidos,
     *  grupo_clase1 y grupo_clase2.
     *
     * @param nombre nombre del profesor.
     * @param apellidos apellidos del profesor.
     * @param grupo_clase1 primer grupo de clase impartido.
     * @param grupo_clase2 segundo grupo de clase impartido.
     */
    public Profesor (String nombre, String apellidos, String grupo_clase1,
                                                      String grupo_clase2) {
        this.cod_profesor = this.getNuevaClaveProfesor();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.grupo_clase1 = new GrupoClase(grupo_clase1);
        this.grupo_clase2 = new GrupoClase(grupo_clase2);
    } // fin del constructor


    /** Crea un nuevo profesor e inicializa los atributos cod_profesor, nombre,
     *  apellidos, grupo_clase1 y grupo_clase2.
     *
     * @param cod código del profesor.
     * @param nombre nombre del profesor.
     * @param apellidos apellidos del profesor.
     * @param grupo_clase1 primer grupo de clase impartido.
     * @param grupo_clase2 segundo grupo de clase impartido.
     */
    public Profesor (int cod, String nombre, String apellidos, String grupo_clase1,
                     String grupo_clase2) {
        this.cod_profesor = cod;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.grupo_clase1 = new GrupoClase(grupo_clase1);
        this.grupo_clase2 = new GrupoClase(grupo_clase2);
    } // fin del constructor


    /** Método que comprueba si el profesor está dado de alta y, si no lo está,
     * llama al método intertarEnTablaProfesor de la clase ControladorProfesor para
     * realizar el alta del mismo.
     * Si también se han introducido grupos de clase, los registrará en la Base de Datos.
     * Si ocurre algún error, lanzará una excepción.
     */
    public void altaProfesor () {

        ControladorProfesor DAOProfesor = new ControladorProfesor ();
        this.validarCampos();
        if (DAOProfesor.estaDadoDeAlta(this)) {
            throw new RuntimeException ("el profesor ya está dado de alta");
        } else {
            DAOProfesor.darAltaProfesor(this);
        }
    } // fin del metodo altaProfesor


    /** Método que comprueba si el profesor está dado de alta y, si lo está,
     * llama al método darBajaProfesor de la clase ControladorProfesor para
     * realizar la baja del mismo. Si ocurre algún error, lanzará una excepción.
     */
    public void bajaProfesor () {

        ControladorProfesor DAOProfesor = new ControladorProfesor ();
        if (DAOProfesor.estaDadoDeAlta(this)) {
            DAOProfesor.darBajaProfesor(this);
        } else {
            throw new RuntimeException("el profesor no está dado de alta");
         }
    } // fin del metodo bajaProfesor


    /** Método que realiza la consulta solicitada y devuelve el resultado.
     *
     * @param grupo_clase contiene la información del grupo de clase introducido
     *        en la consulta. Puede ser null.
     *
     * @return Si la ejecución ha sido correcta, devuelve el resultado de la consulta.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public List<ListadoProfesor> consultarProfesor(GrupoClase grupo_clase) {
        ControladorProfesor DAOProfesor = new ControladorProfesor();
        return DAOProfesor.realizarConsultaProfesor(this, grupo_clase);
    } // fin del método consultarProfesor


    /** Método que actualiza los datos de los profesores recibidos por parámetro.
     *
     * @param resultado_consulta contiene la información actualizada de los profesores.
     */
    public void actualizarProfesores(List<ListadoProfesor> resultado_consulta) {
        ControladorProfesor DAOProfesor = new ControladorProfesor();
        DAOProfesor.actualizarProfesores(resultado_consulta);
    } // fin del método actualizarProfesores



    /** Método que comprueba que profesores tienen tutorías y/o clases impartidas
     *  en la convocatoria actual, y los activa en la Base de Datos.
     *  Es decir, pone su campo "activo" a 1.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void reactivarProfesoresConRegistrosEnConvocatoriaActual(DataAccessObject dataAccessObject) {
        ControladorProfesor DAOProfesor = new ControladorProfesor();
        DAOProfesor.reactivarProfesoresConTutoriasEnConvocatoriaActual(dataAccessObject);
        DAOProfesor.reactivarProfesoresConImparteEnConvocatoriaActual(dataAccessObject);
    } // fin del método reactivarProfesoresConRegistrosEnConvocatoriaActual



    /** Método que elimina de la Base de Datos todos los registros de imparticiones
     *  de clase dados de alta en el curso actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void eliminarImparticionesCursoActual(DataAccessObject dataAccessObject) {
        ControladorProfesor DAOProfesor = new ControladorProfesor();
        DAOProfesor.eliminarImparticionesCursoActual(dataAccessObject);
    } // fin del método eliminarImparticionesCursoActual



    /** Método que obtiene la información de un profesor con un código específico.
     *  Si ocurre algún error o no encuentra ningún profesor, lanzará una excepción.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void obtenerDatos(int codProfesor, DataAccessObject dataAccessObject) {
        ControladorProfesor DAOProfesor = new ControladorProfesor();
        this.nombre = DAOProfesor.obtenerNombreProfesor(this.cod_profesor, dataAccessObject);
        this.apellidos = DAOProfesor.obtenerApellidosProfesor(this.cod_profesor, dataAccessObject);
    } // fin del método obtenerDatos



    /** Método que obtiene la información de un profesor con un código específico.
     *  Si ocurre algún error o no encuentra ningún profesor, lanzará una excepción.
     */
    public void obtenerDatos(int cod_tutor) {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        obtenerDatos(cod_tutor, dataAccessObject);
        dataAccessObject.close();
    } // fin del método obtenerDatos


    /** Método que comprueba si un profesor está tutorizando, al menos, un
     *  grupo de prácticas.
     *
     * @return TRUE si tiene, al menos, una tutoría.
     *         FALSE en caso contrario.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public boolean tieneTutorias() {
        ControladorTutoria DAOTutoria = new ControladorTutoria();
        return DAOTutoria.tieneTutorias(this);
    } // fin del método tieneTutorias


    /** Método que comprueba que se han introducido correctamente los campos
     *  de profesor. En caso negativo, lanzará una excepción.
     */
    private void validarCampos() {
        if (this.estaVacio(nombre)) {
            throw new RuntimeException("Rellene el campo Nombre");
        }

        if (this.estaVacio(apellidos)) {
            throw new RuntimeException("Rellene el campo Apellidos");
        }
    } // fin del método validarCampos





    @SuppressWarnings("static-access")

    /** Método que comprueba al arrancar el programa, cual es la clave del
     * último profesor dado de alta. Esto se comprueba para, si se da un nuevo
     * profesor de alta, que no se repita la clave con otros profesores.
     *
     */
    public void recuperarUltimoCodProfesor() {

        ControladorProfesor DAOProfesor = new ControladorProfesor ();
        this.clave_ultimo_profesor = DAOProfesor.recuperarUltimoCodProfesor();

    } // fin del método recuperarUltimoCodProfesor


    @SuppressWarnings("static-access")
    /** Método que devuelve un NUEVO código de profesor.
     *  Es decir, un código que no esté usado.
     *
     * @return un NUEVO código de profesor.
     */
    public int getNuevaClaveProfesor() {
        this.clave_ultimo_profesor++;
        return this.clave_ultimo_profesor;
    } // fin del método getNuevaClaveProfesor


    @SuppressWarnings("static-access")
    /** Método que devuelve el último código de profesor dado de alta.
     *
     * @return el último código de profesor dado de alta.
     */
    public int getClaveUltimoProfesor() {
        return this.clave_ultimo_profesor;
    } // fin del método getClaveUltimoProfesor


    /** Método que devuelve el nombre del profesor.
     *
     * @return nombre del profesor.
     */
    public String getNombre() {
        return this.nombre;
    } // fin del método getNombre


    /** Método que devuelve los apellidos del profesor.
     *
     * @return apellidos del profesor.
     */
    public String getApellidos() {
        return this.apellidos;
    } // fin del método getApellidos


    /** Método que devuelve el primer grupo de clase impartido por el profesor.
     *
     * @return primer grupo de clase impartido por el profesor.
     */
    public GrupoClase getGrupoClase1() {
        return this.grupo_clase1;
    } // fin del método getGrupoClase1


    /** Método que devuelve el segundo grupo de clase impartido por el profesor.
     *
     * @return segundo grupo de clase impartido por el profesor.
     */
    public GrupoClase getGrupoClase2() {
        return this.grupo_clase2;
    } // fin del método getGrupoClase2


    /** Método que devuelve el código del profesor.
     *
     * @return código del profesor.
     */
    public int getCodProfesor() {
        return this.cod_profesor;
    } // fin del método getCodProfesor



    /** Método que comprueba si una cadena no está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si está vacía.
     *
     * @return TRUE si la cadena no está vacía.
     *         FALSE en caso contrario.
     */
    private boolean noEstaVacio(String cadena) {
	return cadena != null && !"".equals(cadena);
    } // fin del método noEstaVacio


    /** Método que comprueba si una cadena está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si está vacía.
     *
     * @return TRUE si la cadena está vacía.
     *         FALSE en caso contrario.
     */
    private boolean estaVacio(String cadena) {
	return ((cadena == null) || ("".equals(cadena)));
    } // fin del método estaVacio

} // fin de la clase Profesor