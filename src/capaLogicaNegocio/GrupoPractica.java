

package capaLogicaNegocio;

import capaDatos.ControladorGrupoPractica;
import capaDatos.DataAccessObject;
import capaInterfaz.listados.ListadoGrupoPractica;
import java.util.List;

/** Clase de la capa de Negocio que gestiona todo lo referente a los grupos
 *  de prácticas.
 *
 * @author Confiencial
 */
public class GrupoPractica {

    private int cod_grupo;
    private Alumno alumno1 = null;
    private Alumno alumno2 = null;
    private Profesor tutor = null;
    private float nota = 0;


    /** Crea un nuevo grupo de prácticas e inicializa los atributos grupo, alum1,
     *  alum2, tutor y nota.
     *
     * @param grupo código del grupo de prácticas.
     * @param alum1 primer integrante del grupo.
     * @param alum2 segundo integrante del grupo.
     * @param tutor profesor tutor del grupo.
     * @param nota nota obtenida.
     */
    public GrupoPractica (int grupo, Alumno alum1, Alumno alum2, Profesor tutor,
                          float nota) {
        this.cod_grupo = grupo;
        this.alumno1 = alum1;
        this.alumno2 = alum2;
        this.tutor = tutor;
        this.nota = nota;
    } // fin del constructor


    /** Crea un nuevo grupo de prácticas e inicializa el código a -1*/
    public GrupoPractica () {
        this.cod_grupo = -1;
    } // fin del constructor


    /** Crea un nuevo grupo de prácticas e inicializa el atributo grupo.
     *
     * @param cod_grupo código del grupo de prácticas.
     */
    public GrupoPractica(int cod_grupo) {
        this.cod_grupo = cod_grupo;
    } // fin del constructor


    /** Crea un nuevo grupo de prácticas e inicializa los atributos grupo y tutor.
     *
     * @param cod_grupo código del grupo de prácticas en formato String.
     * @param tutor profesor tutor del grupo.
     */
    public GrupoPractica(String cod_grupo, Profesor tutor) {
        if (this.estaVacio(cod_grupo)) {
            this.cod_grupo = -1;
        } else {
            this.cod_grupo = Integer.parseInt(cod_grupo);
        }
        this.tutor = tutor;
    } // fin del constructor


    /** Crea un nuevo grupo de prácticas e inicializa los atributos grupo, alum1,
     *  alum2 y nota.
     *
     * @param cod_grupo código del grupo de prácticas en formato String.
     * @param dni_alumno1 dni del primer integrante del grupo.
     * @param dni_alumno2 dni del segundo integrante del grupo.
     * @param nota nota obtenida en formato String.
     */
    public GrupoPractica(String cod_grupo, String dni_alumno1,
                         String dni_alumno2, String nota) {
        try {
            this.cod_grupo = Integer.parseInt(cod_grupo);
            this.nota = Float.parseFloat(nota);
            this.alumno1 = new Alumno(dni_alumno1);
            this.alumno2 = new Alumno(dni_alumno2);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Nota no válida");
        }
    } // fin del constructor



    /** Método que comprueba que los datos introducidos tienen el formato correcto
     *  y realiza el alta de un grupo de prácticas. Además, dará de alta la tutoría
     *  correspondiente y actualizará la evaluación de los alumnos.
     *  Si ocurre algún error, lanzará uan excepción.
     * 
     * @param cod_grupo contiene el código del grupo que se quiere dar de alta.
     * @param dni_alumno1 contiene el dni del 1º componente del grupo.
     * @param dni_alumno2 contiene el dni del 2º componente del grupo. Podrá ser null.
     * @param tutor contiene la información del profesor tutor del grupo.
     * @param nota contiene la nota obtenida por los componentes del grupo. Opcional.
     */
    public void altaGrupoPractica (String cod_grupo, String dni_alumno1,
                                   String dni_alumno2, Profesor tutor,
                                   String nota) {

        this.nota = 0;
        this.validarCampos(cod_grupo, dni_alumno1, dni_alumno2, tutor.getCodProfesor(),
                           nota);
        System.out.println(alumno1.getDNI());
        this.cod_grupo = Integer.parseInt(cod_grupo);
        this.tutor = tutor;
        if (this.noEstaVacio(nota)) {
            this.nota = Float.parseFloat(nota);
        } 

        ControladorGrupoPractica DAOGrupoPractica = new ControladorGrupoPractica();
        DAOGrupoPractica.darAltaGrupoPractica(this);
    } // fin del método altaGrupoPractica


    /** Método que comprueba si un grupo de prácticas está dado de alta y, si lo está,
     *  realiza la baja del mismo. Se eliminarán también los registros del grupo
     *  en las evaluaciones de los integrantes y el registro de qué profesor tutoriza
     *  ese grupo. Si ocurre algún error, lanzará una excepción.
     *
     *  @param dni1 dni del alumno 1.
     *  @param dni2 dni del alumno 2.
     */
    public void bajaGrupoPractica(String dni1, String dni2) {
        ControladorGrupoPractica DAOGrupoPractica = new ControladorGrupoPractica();
        if (DAOGrupoPractica.noEstaDadoDeAlta(this)) {
            throw new RuntimeException("El grupo no está dado de alta.");
        }
        DAOGrupoPractica.darBajaGrupoPractica(this, dni1, dni2);
    } // fin del método bajaGrupoPractica


    /** Método que comprueba que los datos introducidos tienen el formato correcto.
     *  Si ocurre algún error o algún campo no cumple el formato requerido,
     *  lanzará una excepción.
     *
     * @param cod_grupo contiene el código del grupo. Ha de ser numérico.
     * @param dni_alumno1 contiene el dni del alumno 1. No puede ser null.
     * @param cod_tutor contiene el código del tutor. No puede ser -1.
     * @param nota contiene la nota. Ha de ser numérico.
     */
    public void validarCampos (String cod_grupo, String dni_alumno1,
                               String dni_alumno2, int cod_tutor, String nota) {

        if (this.estaVacio(cod_grupo)) {
            throw new RuntimeException("Rellene el Código de grupo ");
        }

        if (this.estaVacio(dni_alumno1)) {
            throw new RuntimeException("Rellene el campo alumno1 ");
        }

        try{
            int cod = Integer.parseInt(cod_grupo);
        } catch (NumberFormatException e) {
           throw new RuntimeException("Revise que haya introducido un "
                                    + "valor numérico en Código de grupo");
        }

        if (cod_tutor == -1) {
            throw new RuntimeException("Rellene el campo Tutor");
        }

        if (this.noEstaVacio(nota)) {
            try{
                float nota_aux = Float.parseFloat(nota);
            } catch (NumberFormatException e) {
               throw new RuntimeException("Revise que haya introducido un "
                                        + "valor numérico en Nota");
            }
        }

        alumno1 = new Alumno (dni_alumno1);
        if (alumno1.noEstaDadoDeAlta()) {
            throw new RuntimeException("El alumno1 no está dado de alta");
        }

        if (this.noEstaVacio(dni_alumno2)) {
            alumno2 = new Alumno(dni_alumno2);
            if (alumno2.noEstaDadoDeAlta()) {
                throw new RuntimeException("El alumno2 no está dado de alta");
            }
        }
    } // fin del método validarCampos



    /** Método que realiza la consulta solicitada y devuelve el resultado.
     * 
     * @param dni dni del alumno por el cual se quiere filtrar. Puede ser null.
     * 
     * @return Si la ejecución ha sido correcta, devuelve el resultado de la consulta.
     *         Si ocurre algún error, lanzará una excepción.
     */
    public List<ListadoGrupoPractica> consultarGrupoPractica (String dni) {
        ControladorGrupoPractica DAOGrupoPractica = new ControladorGrupoPractica();
        return DAOGrupoPractica.realizarConsultaGrupoPractica(this, dni);
    } // fin del método consultarGrupoPractica


    /** Método que comprueba que grupos de prácticas están en "uso" en la convocatoria
     *  actual, y los activa en la Base de Datos. Es decir, pone su campo "activo" a 1.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void reactivarGruposPracticasEnUsoEnConvocatoriaActual(DataAccessObject dataAccessObject) {
        ControladorGrupoPractica DAOGrupoPractica = new ControladorGrupoPractica();
        DAOGrupoPractica.reactivarGruposPracticasEnUsoEnConvocatoriaActual(dataAccessObject);
    } // fin del método reactivarGruposPracticasEnUsoEnConvocatoriaActual


    /** Método que comprueba que grupos de prácticas están en "uso" en la convocatoria
     *  actual, y los activa en la Base de Datos. Es decir, pone su campo "activo" a 1.
     */
    public void reactivarGruposPracticasEnUsoEnConvocatoriaActual() {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            ControladorGrupoPractica DAOGrupoPractica = new ControladorGrupoPractica();
            DAOGrupoPractica.reactivarGruposPracticasEnUsoEnConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    } // fin del método reactivarGruposPracticasEnUsoEnConvocatoriaActual


    /** Método que, al haber un cambio de convocatoria, desactiva TODOS los gurpos
     *  de prácticas. Es decir, pone su campo "activo" a cero.
     *
     */
    public void desactivarGruposPracticas() {
        ControladorGrupoPractica DAOGrupoPractica = new ControladorGrupoPractica();
        DAOGrupoPractica.desactivarGruposPracticas();
    } // fin del metodo desactivarGruposPracticas

    /** Método que actualiza la Base de Datos con las modificaciones realizadas
     *  en los grupos de prácticas. 
     *  Si ocurre algún error, lanzará una excepción.
     * 
     * @param resultado_modificaciones contiene las modificaciones realizadas.
     */
    public void actualizarGruposPracticas (List<GrupoPractica> resultado_modificaciones) {
        ControladorGrupoPractica DAOGrupoPracticas = new ControladorGrupoPractica();
        DAOGrupoPracticas.actualizarGruposPracticas(resultado_modificaciones);
    } // fin del método actualizarGruposPracticas


    /** Método que devuelve el atributo alumno1 de la clase Alumno, que contiene
     *  los datos del primer integrante del grupo.
     *
     * @return atributo alumno1 de la clase Alumno, que contiene
     *         los datos del primer integrante del grupo.
     */
    public Alumno getAlumno1 () {
        return alumno1;
    } // fin del método getAlumno1



    /** Método que devuelve el atributo alumno2 de la clase Alumno, que contiene
     *  los datos del segundo integrante del grupo.
     *
     * @return atributo alumno2 de la clase Alumno, que contiene
     *         los datos del segundo integrante del grupo.
     */
    public Alumno getAlumno2 () {
        return alumno2;
    } // fin del método getAlumno2


    /** Método que devuelve la nota obtenida por los integrantes del grupo.
     *
     * @return nota obtenida por los integrantes del grupo.
     */
    public float getNota () {
        return nota;
    } // fin del método getNota


    /** Método que devuelve el atributo tutor de la clase Profesor, que contiene
     *  los datos del profesor tutor del grupo.
     *
     * @return atributo tutor de la clase Profesor, que contiene
     *         los datos del profesor tutor del grupo.
     */
    public Profesor getTutor () {
        return tutor;
    } // fin del método getTutor


    /** Método que devuelve el código del grupo.
     *
     * @return código del grupo.
     */
    public int getCodGrupoPractica() {
        return this.cod_grupo;
    } // fin del método getCodGrupoPractica



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

     /** Método que comprueba si una cadena no está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si no está vacía.
     *
     * @return TRUE si la cadena no está vacía.
      *        FALSE en caso contrario.
     */
    private boolean noEstaVacio(String cadena) {
	return cadena != null && !"".equals(cadena);
    } // fin del método noEstaVacio

} // fin de la clase GrupoPractica