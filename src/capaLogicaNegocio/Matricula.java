
package capaLogicaNegocio;

import capaDatos.ControladorMatricula;
import capaDatos.DataAccessObject;

/** Clase de la capa de Negocio que gestiona todo lo referente a las matrículaciones.
 *
 * @author Confiencial
 */
public class Matricula {

    private static int cod_ultima_matricula;

    private String dni_alumno;
    private GrupoClase grupo_clase;
    private int curso;


    /** Crea una nueva matrícula */
    public Matricula () {
    } // fin del constructor

    /** Crea una nueva matrícula e inicializa los atributos dni_alumno, curso
     *  y grupo_clase.
     *
     * @param dni_alumno dni del alumno matriculado.
     * @param grupo_clase grupo de clase en el que se ha matriculado.
     * @param curso curso en el que se ha matriculado.
     */
    public Matricula (String dni_alumno, GrupoClase grupo_clase, int curso) {

        this.curso = curso;
        this.dni_alumno = dni_alumno;
        this.grupo_clase = grupo_clase;

    } // fin del constructor


    /** Método que comprueba si la matrícula está dada de alta y, si no lo está,
     * llama al método insertarEnTablaMatricula de la clase ControladorMatricula para
     * realizar el alta de la misma.
     * Si ocurre algún error, lanzará una excepción.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.   
     */
    public void altaMatricula(DataAccessObject dataAccessObject) {

        ControladorMatricula DAOMatricula = new ControladorMatricula ();
        
        try { 
            this.grupo_clase.validarGrupoClase(dataAccessObject);
        } catch (RuntimeException e) {
            throw new RuntimeException("Problema al dar de alta el grupo de clase"
                                     + " en altaMatricula");
        }
        if (DAOMatricula.estaDadaDeAlta(this, dataAccessObject)){
            throw new RuntimeException ("El alumno con DNI "+this.getDNI()+" ya está dado de alta");
        } else {
            DAOMatricula.insertarEnTablaMatricula(this, dataAccessObject);
          }
    } // fin del método altaMatricula


    /** Método que elimina de la Base de Datos todas las matrículas dadas de alta
     *  en el curso actual.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void eliminarMatriculasCursoActual(DataAccessObject dataAccessObject) {
        ControladorMatricula DAOMatricula = new ControladorMatricula();
        DAOMatricula.eliminarMatriculasCursoActual(dataAccessObject);
    } // fin del método eliminarMatriculasCursoActual


    @SuppressWarnings("static-access")

    /** Método que comprueba al arrancar el programa, cual es la clave de la
     * última matrícula dada de alta. Esto se comprueba para, si se da una nueva
     * matrícula de alta, que no se repita la clave con otras matrículas.
     *
     */
    public void recuperarUltimoCodMatricula() {

        ControladorMatricula DAOMatricula = new ControladorMatricula ();
        this.cod_ultima_matricula = DAOMatricula.recuperarUltimoCodMatricula();
    } // fin del método recuperarUltimoCodMatricula


 @SuppressWarnings("static-access")

    /** Método que devuelve un NUEVO código de matrícula.
     *  Es decir, un código que no esté usado.
     *
     * @return un NUEVO código de matrícula.
     */
    public int getNuevoCodMatricula() {
        this.cod_ultima_matricula++;
        return this.cod_ultima_matricula;
    } // fin del método getNuevoCodMatricula

    @SuppressWarnings("static-access")
    /** Método que devuelve el último código de matrícula dado de alta.
     *
     * @return el último código de matrícula dado de alta.
     */
    public int getCodUltimaMatricula() {
        return this.cod_ultima_matricula;
    } // fin del método getCodUltimaMatricula


    /** Método que devuelve el dni del alumno matriculado.
     *
     * @return dni del alumno matriculado.
     */
    public String getDNI() {
        return this.dni_alumno;
    } // fin del método getDNI



    /** Método que devuelve el grupo de clase del alumno matriculado.
     *
     * @return grupo de clase del alumno matriculado.
     */
    public String getGrupo_Clase() {
        return this.grupo_clase.getCodGrupoClase();
    } // fin del método getGrupo_Clase


    /** Método que devuelve el código del curso académico del alumno matriculado.
     *
     * @return código del curso académico del alumno matriculado.
     */
    public int getCod_Curso() {
        return this.curso;
    } // fin del método getCod_Curso

} // fin de la clase Matricula