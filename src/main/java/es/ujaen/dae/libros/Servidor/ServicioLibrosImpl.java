package es.ujaen.dae.libros.Servidor;

import java.util.ArrayList;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import es.ujaen.dae.libros.excepciones.ErrorUsuarioRegistrado;
import es.ujaen.dae.libros.excepciones.ErrorLogin;
import es.ujaen.dae.libros.excepciones.ErrorNoAutorizado;
import es.ujaen.dae.libros.excepciones.ErrorLibroExistente;
import es.ujaen.dae.libros.excepciones.ErrorLibroNoExiste;
import java.util.Calendar;
import java.util.List;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alvarogonzalezgonzalez
 */
@Component
public class ServicioLibrosImpl implements ServicioLibros {

    String cif;
    String url;
    String nombre;
    //@Autowired
    //private ApplicationContext appContext;
    @Autowired
    AutorDAO autDAO;
    @Autowired
    RevisorDAO revDAO;
    @Autowired
    EditorDAO editDAO;
    @Autowired
    LibroDAO libDAO;

    public ServicioLibrosImpl() {
        cif = "234239472893R";
        url = "www.elibros.com";
        nombre = "E-Libros";

        // generarToken(tokensNicksRevisor, "Revisor");
        // editores.put(ed1.getNick(), ed1);
    }

    @Override
    public List<Revisor> consultarRevisores() {

        List<Revisor> r = new ArrayList<>();
        r = revDAO.getRevisores();
        return r;

    }

    @Override
    public void holaBuscaToken(String tok) {
        // AutorDAO autDAO = appContext.getBean(AutorDAO.class);
        Revisor a = revDAO.buscarPorToken(tok);
        if (a != null) {
            System.out.println("nombre" + a.getNombre());
        } else {
            System.out.println("no encontrado");
        }
        if (a == null) {
            System.out.println("no encontrado2");

        }
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String c) {
        this.cif = c;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String u) {
        this.url = u;
    }

    /**
     * @param l
     * @param librosEnRevision the librosEnRevision to set
     */
    @Override
    public boolean publicarLibro(Libro l, String token) {
        //Comprobar validez del token y si tiene autorizacion para su rol
        //Solo lo realiza un AUTOR->A
        try {
            if ("A".equals(checkAutorizacion(token))) {
                //comprobacion de libro unico
                if (libDAO.buscar(l.getIsbn()) == null) {
                    //Asignar estado para que el libro se quede a la espera del visto bueno del editor
                    l.setEstado("revisionContenido");
                    libDAO.insertar(l);
                    return true;
                } else {
                    throw new ErrorLibroExistente();
                }
            }
        } catch (RuntimeException e) {

        }
        return false;
    }

    private void asignarRevisor() {
        /*
        En este método cogemos todos los libros que estén con el estado pendienteRevisores y le vamos
        asignando un revisor escogido por fecha de antigüedad si este no ha superado su carga aún
         */

        int revisoresAsignados = 0;

        List<Libro> librosParaRevision = new ArrayList<>();

        librosParaRevision = libDAO.getLibrosRevisores();

        for (Libro librosParaRevision1 : librosParaRevision) {
            while (revisoresAsignados != 3) {
                Revisor revMasAntiguo = revDAO.getRevAsignacionMasAntigua();
                if (revMasAntiguo.getLibrosPendientes().size() < revMasAntiguo.getCarga()) {
                    //librosParaRevision1.agregarRevisor(revMasAntiguo);
                    revMasAntiguo.asignarLibro(librosParaRevision1);
                    revMasAntiguo.setFecha(Calendar.getInstance());
                    revMasAntiguo.setCarga(revMasAntiguo.getCarga() + 1);
                    librosParaRevision1.setEstado("enRevision");
                    //libDAO.eliminar(librosParaRevision1);
                    //libDAO.insertar(librosParaRevision1);
                    libDAO.actualizar(librosParaRevision1);
                    revDAO.actualizar(revMasAntiguo);
                    revisoresAsignados++;
                } else {
                    revMasAntiguo.setFecha(Calendar.getInstance());
                    revDAO.actualizar(revMasAntiguo);
                }

            }
        }

    }

    @Override
    public Libro consultarLibroTitulo(String titulo
    ) {
        try {
            Libro l = libDAO.buscarPorTitulo(titulo);
            if (l != null) {
                return l;
            } else {
                throw new ErrorLibroNoExiste();
            }
        } catch (RuntimeException e) {

        }
        return null;
    }

    @Override
    public List<Libro> consultarLibroTematica(String tem) {
        List<Libro> l = libDAO.buscarPorTematica(tem);
        try {
            if (l != null) {
                return l;
            } else {
                throw new ErrorLibroNoExiste();
            }
        } catch (RuntimeException e) {

        }
        return null;
    }

    private void desasignarRevisores(Libro l) {
//        for (int i = 0; i <= l.getRevisoresAsignados().size(); i++) {
//            Revisor r = l.getRevisoresAsignados().get(i);
//            r.getLibrosPendientes().remove(l);
//            revDAO.actualizar(r);
//        }
    }

    @Override
    public void aceptarLibro(Libro l, int estado, String token) {

        if ("E".equals(checkAutorizacion(token))) {
            if (estado == 1) {
                l.setEstado("pendienteRevisores");
                libDAO.actualizar(l);
                asignarRevisor();
            }
        }
    }

    @Override
    public void rechazarLibro(Libro l, int estado, String token) {

        if ("E".equals(checkAutorizacion(token))) {
            if (estado == 0) {
                l.setEstado("noPublicable");
                libDAO.actualizar(l);
            }
        }
    }

    @Override
    public Integer asignarNota(Libro l, int n, String token) {
        //Comprobar validez del token y si tiene autorizacion para su rol
        //Solo lo realiza un REVISOR->R
        if ("R".equals(checkAutorizacion(token))) {

            //ASIGNAR NOTA a la nota que sea distinto de -1
            //l.setNota1=n;
            //libDAO.actualizar(l);
            //obtenemos el nick mediante el token
            //desde el token accedemos al revisor desde el mapa
            //eliminamos de la lista el libro
            Revisor r = revDAO.buscarPorToken(token);
            //r.eliminaLibroPendiente(l)
            //revDAO.actualizar(revDAO.buscarPorToken(token))

            //cuando asignamos una nota, llamaremos a chekNuevaNota para comprobar si la nota
            //introducida es menor que 2 para indicar el libro como no publicable
            if (!l.checkNuevaNota(n)) {

                //Desasignamos el libros a los revisores que tenia
                desasignarRevisores(l);
                //Borramos los revisores del libro
                //l.getRevisoresAsignados().clear();
                //Actualiza en BD
                libDAO.actualizar(l);
                return -2;
            }
            //también llamaremos a checkNotaFinal para comprobar si todas las notas(3) están
            //ya asignadas y si su suma total es buena para ser publicado

            int result = l.checkNotaFinal();

            if (result == -1) {
                return -1; //Aun no tiene 3 notas asignadas, sigue enRevision...
            } else if (result == -2) {
                //Desasignamos el libros a los revisores que tenia
                desasignarRevisores(l);
                //Borramos los revisores del libro
//                l.getRevisoresAsignados().clear();
                //Actualiza en BD
                libDAO.actualizar(l);
                return -2;
            }
            //Actualizamos la carga del revisor, 
            r.setCarga(r.getCarga() - 1);
            revDAO.actualizar(r);
            //Se ha evaluado positivamente el libro y se guarda en libros
            //Desasignamos el libros a los revisores que tenia
            desasignarRevisores(l);
            //Borramos los revisores del libro
//            l.getRevisoresAsignados().clear();
            //Actualiza en BD
            l.setEstado("publicado");
            libDAO.actualizar(l);
            return 1;

        }
        return -9;//No autorizado
    }

    /*
     * Examinar contenido del libro.
     * En este método accederemos a la cola inicial de los libros subidos al sistema donde un administrador
     * hará una revisión previa del contenido de dicho libro antes de asignar los 3 revisores para que lo 
     * puntúen. Este administrador podrá poner el libro en revisión o como no publicable, según lo estime.
     * 
     * Este método deberá ser llamado por el método RevisarLibro, que deberá obtener el texto del libro
     */
//    @Override
//    public void examinarContLibro(Libro l, String token) {
//        //Comprobar validez del token y si tiene autorizacion para su rol
//        //Solo lo realiza un EDITOR->E
//        if ("E".equals(checkAutorizacion(token))) {
//
//            if ("pendienteRevisores".equals(l.getEstado())) {
//                //intenta asignar a 3 revisores
//                asignarRevisor();
//            }
//        }
//    }

    /*
     * Método para modificar el estado del libro
     */
//    @Override
//    public void modificarEstado(Libro l, String estado) {
//
//        l.setEstado(estado);
//        libDAO.actualizar(l);
//    }

    /*
    * Podemos hacer la captura de excepciones en este método con las excepciones que he incluido
    * en el paquete de cliente. No se si se deben de implementar ahí o en el paquete del servidor.
    * si no lo hacemos así, se puede hacer como en el método de asignarNotaLibro() que usamos los enteros
    * para capturar el resultado de las notas, lo que aquí lo haríamos con el nombre, nick y pass
     */
    @Override
    public String registroUsuario(String nombre, String nick, String pass, String rol) {
        try {
            if (null != rol) {
                switch (rol) {
                    case "Autor":

                        //comprobacion de autor único
                        // if (!autores.containsKey(nick) && !revisores.containsKey(nick) && !editores.containsKey(nick)) {
                        //appContext.getBean(AutorDAO.class).buscarPorToken("jh3g12jk3gj12kkk-A") 
                        if (autDAO.buscarPorNick(nick) == null
                                && revDAO.buscarPorNick(nick) == null
                                && editDAO.buscarPorNick(nick) == null) {
                            Autor nuevoAutor = new Autor(nombre, nick, pass);
                            String token = generarToken("Autor");
                            nuevoAutor.setToken(token);
                            //tokensNicksAutor.put(token, nick);//para busqueda al autor desde el token eficiente.
                            //autores.put(nick, nuevoAutor);
                            autDAO.insertar(nuevoAutor);
                            return token; //devuelve token para que el usuario pueda hacer sus operaciones.

                        } else {
                            throw new ErrorUsuarioRegistrado();
                        }

                    case "Editor":
                        //comprobacion de editor único
                        if (autDAO.buscarPorNick(nick) == null
                                && revDAO.buscarPorNick(nick) == null
                                && editDAO.buscarPorNick(nick) == null) {
                            Editor nuevoEditor = new Editor(nombre, nick, pass);
                            String token = generarToken("Editor");
                            nuevoEditor.setToken(token);
                            //tokensNicksEditor.put(token, nick);//para busqueda al editor desde el token eficiente.
                            //editores.put(nick, nuevoEditor);
                            editDAO.insertar(nuevoEditor);
                            return token; //devuelve token para que el usuario pueda hacer sus operaciones.

                        } else {
                            throw new ErrorUsuarioRegistrado();
                        }
                    default:

                        break;
                }

                /*  //EXCEPCION, ERROR DE USUARIO, ROL NO ASIGNADO
            System.out.println("ERROR, rol no asignado al usuario");
            return "";*/
            }
        } catch (RuntimeException e) {

        }
        return "";

    }

    @Override
    public String registroUsuarioRevisor(String nombre, String nick, String pass, String rol, int carga) {

        //comprobacion de revisor único
        if (autDAO.buscarPorNick(nick) == null
                && revDAO.buscarPorNick(nick) == null
                && editDAO.buscarPorNick(nick) == null) {

            Revisor nuevoRevisor = new Revisor(nombre, nick, pass, carga);
            String token = generarToken("Revisor");
            nuevoRevisor.setToken(token);
            // tokensNicksRevisor.put(token, nick);//para busqueda al revisor desde el token eficiente.
            //getRevisores().put(nick, nuevoRevisor);
            revDAO.insertar(nuevoRevisor);
            return token; //devuelve token para que el usuario pueda hacer sus operaciones.
        } else {
            throw new ErrorUsuarioRegistrado();

        }
    }

    /*
    Genera un string aleatorio de caracteres predefinidos en chars[] de size 10
    se vuelve a generar si ya existe en el Set de tokens 
     */
    private String generarToken(String rol) {
        String token;
        char[] chars = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        int charsLength = chars.length;
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        //while (tokensNick.containsKey(buffer.toString()) || buffer.length() == 0) {
        // appContext.getBean(AutorDAO.class).buscarToken("jh3g12jk3gj12-A")

        while (autDAO.buscarPorToken(buffer.toString()) != null
                || revDAO.buscarPorToken(buffer.toString()) != null
                || editDAO.buscarPorToken(buffer.toString()) != null
                || buffer.length() == 0) {
            //comprobacion de token único
            for (int i = 0; i < 10; i++) {
                //generacion del token
                buffer.append(chars[random.nextInt(charsLength)]);
            }
        }
        token = buffer.toString();
        if (null != rol) {
            switch (rol) {
                case "Autor":
                    token += "A";
                    break;
                case "Editor":
                    token += "E";
                    break;
                case "Revisor":
                    token += "R";
                    break;
                default:
                    break;
            }
        }
        return token;
    }

    @Override
    public String login(String nick, String pass) {
        String token = "";
        //buscar si es un autor,editor o revisor
        //if (autores.containsKey(nick)) {
        try {
            if (autDAO.buscarPorNick(nick) != null) {
                //comprueba que la pass sea correcta
                //if (autores.get(nick).getPass().equals(pass)) {
                if (autDAO.buscarPorNick(nick).getPass().equals(pass)) {
                    //****!!!!!si no tiene token se genera->> PARA PRUEBAS DE AUTORES CREADOS DESDE CODIGO
                    /* if (autores.get(nick).getToken().equals("")) {
                    String tok = generarToken("Autor");
                   // tokensNicksAutor.put(tok, nick);
                    autores.get(nick).setToken(tok);

                }*/
                    //obtenemos el token para devolverlo
                    //token = autores.get(nick).getToken();
                    token = autDAO.buscarPorNick(nick).getToken();
                    return token;
                } else {
                    throw new ErrorLogin();
                }
            } else if (revDAO.buscarPorNick(nick) != null) {
                //if (getRevisores().get(nick).getPass().equals(pass)) {
                if (revDAO.buscarPorNick(nick).getPass().equals(pass)) {
                    //****!!!!!si no tiene token se genera->> PARA PRUEBAS DE REVISORES CREADOS DESDE CODIGO
                    /* if (revisores.get(nick).getToken().equals("")) {
                    String tok = generarToken(tokensNicksRevisor, "Revisor");
                    tokensNicksRevisor.put(tok, nick);
                    revisores.get(nick).setToken(tok);
                }*/

                    //obtenemos el token para devolverlo
                    // token = revisores.get(nick).getToken();
                    token = revDAO.buscarPorNick(nick).getToken();
                    return token;
                } else {
                    throw new ErrorLogin();
                }
            } else if (editDAO.buscarPorNick(nick) != null) {
                // if (editores.get(nick).getPass().equals(pass)) {
                if (editDAO.buscarPorNick(nick).getPass().equals(pass)) {
                    //****!!!!!si no tiene token se genera->> PARA PRUEBAS DE EDITORES CREADOS DESDE CODIGO
                    /* if (editores.get(nick).getToken().equals("")) {
                    String tok = generarToken(tokensNicksEditor, "Editor");
                    tokensNicksEditor.put(tok, nick);
                    editores.get(nick).setToken(tok);
                }*/
                    //obtenemos el token para devolverlo
                    //token = editores.get(nick).getToken();
                    token = editDAO.buscarPorNick(nick).getToken();
                    return token;
                } else {
                    throw new ErrorLogin();
                }
            }

        } catch (RuntimeException e) {

        }

        return token;

    }

    //Comprueba que el token existe en el sistema que esta asociado a un  Autor,Editor o Revisor 
    //Y comprueba que el token pasado es el mismo a la persona asociada
    //se devuelve A,E o R segun su rol para saber si tiene autorizacion a la operacion que quiere realizar
    // y devuelve vacio si no es correcto.
    private String checkAutorizacion(String token) {
        try {
            //localizar que tipo de usuario es, se mira en el ultimo caracter del token
            if (token.indexOf("A", token.length() - 1) > 0) {
                //comprueba tokens pertenece al autor
                //if (tokensNicksAutor.containsKey(token)) {
                //appContext.getBean(AutorDAO.class).buscarPorToken("jh3g12jk3gj12kkk-A")
                if (autDAO.buscarPorToken(token) != null) {
                    return "A";
                }
            } else if (token.indexOf("E", token.length() - 1) > 0) {
                //if (tokensNicksEditor.containsKey(token)) {
                if (editDAO.buscarPorToken(token) != null) {
                    return "E";
                }
            } else if (token.indexOf("R", token.length() - 1) > 0) {
                // if (tokensNicksRevisor.containsKey(token)) {
                if (revDAO.buscarPorToken(token) != null) {
                    return "R";
                }
            } else {
                throw new ErrorNoAutorizado();
            }
        } catch (RuntimeException e) {

        }
        return "";
    }

    @Override
    public List<Libro> librosParaRevisar(String token, String estado) {
        List<Libro> resul = new ArrayList<>();
        if (token.indexOf("E", token.length() - 1) > 0) {
            resul = libDAO.getLibrosParaRevision(estado);
//Buscar en la BD todos los libros que tengan el estado revisionContenido y devolverlos

        }
        return resul;
    }

    @Override
    public List<Libro> librosParaPuntuar(String token) {
        List<Libro> resul = new ArrayList<>();

        if (revDAO.buscarPorToken(token) != null) {
            //devolver la lista de libros que tiene que puntuar el REVISOR
            //se localiza mediante el token
            /*String nick = tokensNicksRevisor.get(token);
            Revisor r = revisores.get(nick);*/
            Revisor r = revDAO.buscarPorToken(token);
            return r.getLibrosPendientes();
            //return revisores.get(tokensNicksRevisor.get(token)).getLibrosPendientes();
        }
        return resul;
    }

    public void rellenarColaRevisores() {
        //crear 5 revisores de ejemplo
        Revisor r1 = new Revisor("luis revisor", "luisr", "1234", 2);

        r1.setToken("skdjfasdf3q4-R");
        revDAO.insertar(r1);
        Revisor r2 = new Revisor("jesus revisor", "jesusr", "1234", 4);
        //String token1 = generarToken("Revisor");
        r2.setToken("345234652345jt6n245-R");
        revDAO.insertar(r2);

        Revisor r5 = new Revisor("pepito revisor", "pepito", "1234", 4);
        r5.setToken("asd98a7sd9a-R");
        revDAO.insertar(r5);

        Revisor r6 = new Revisor("tony revisor", "tony", "1234", 4);
        r6.setToken("ds8dx25s-R");
        revDAO.insertar(r6);

        Revisor r7 = new Revisor("laura revisor", "laura", "1234", 4);
        r7.setToken("d8fr2c8esw56s-R");
        revDAO.insertar(r7);
//        Revisor r3 = new Revisor("Antonio revisor", "antonior", "1234",3);
//        String token2 = generarToken("Revisor");
//        r3.setToken(token2);
//        revDAO.insertar(r3);
//        Revisor r4 = new Revisor("miguel revisor", "miguelr", "1234",5);
//        String token3 = generarToken("Revisor");
//        r4.setToken(token3);
//        revDAO.insertar(r4);
//        Revisor r5 = new Revisor("fran revisor", "franr", "1234",2);
//        String token4 = generarToken("Revisor");
//        r5.setToken(token4);
//        revDAO.insertar(r5);
//        

        Libro l1 = new Libro("978-6754", "Aventuras", "Libro1",
                "23/2/2013", "revisonContenido", "Texto vacío");
        Libro l2 = new Libro("978-121123", "Aventuras", "Libro2",
                "23/2/2013", "revisionContenido", "Texto vacío");
        Libro l3 = new Libro("978-43", "Aventuras", "Libro3",
                "23/2/2013", "revisionContenido", "Texto vacío");

        Libro l5 = new Libro("978-22", "Aventuras", "Libro5",
                "23/2/2013", "revisionContenido", "Texto vacío");
        Libro l6 = new Libro("978-84998423299619", "Aventuras", "Libro6",
                "23/2/2013", "revisionContenido", "Texto vacío");
        Libro l7 = new Libro("978-42342", "Clasico", "Libro6",
                "23/2/2013", "revisionContenido", "Texto vacío");

        libDAO.insertar(l1);
        libDAO.insertar(l2);
        libDAO.insertar(l3);

        libDAO.insertar(l5);
        libDAO.insertar(l6);
        libDAO.insertar(l7);

        /*r1.asignarLibro(l1);
        r1.setFecha(Calendar.getInstance());
        revDAO.actualizar(r1);
        //r2.asignarLibro(l2);
        r2.asignarLibro(l3);
        
        r2.setFecha(Calendar.getInstance());
        revDAO.actualizar(r2);*/
//        r3.asignarLibro(l5);
//        r3.asignarLibro(l7);
//        r4.asignarLibro(l6);
//        Libro l8 = new Libro("43243241", "Terror", "Dracula",
//                "23/2/1967", "revisionContenido", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
//                + "sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
//                + "quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. "
//                + "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
//                + "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia");
//        Libro l9 = new Libro("43243243", "Historia", "Roma",
//                "23/2/1987", "revisionContenido", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
//                + "sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
//                + "quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. "
//                + "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
//                + "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia");
//        Libro l10 = new Libro("43243242", "Aventuras", "El hobbit",
//                "23/2/1967", "publicado", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
//                + "sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
//                + "quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. "
//                + "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
//                + "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia");
//        libDAO.insertar(l8);
//        libDAO.insertar(l9);
//        libDAO.insertar(l10);
        Editor ed1 = new Editor("marcelo editor", "marceloe", "1234");
        //String token7 = generarToken("Editor");
        ed1.setToken("1134f434tvrvsgfdt34-E");
        editDAO.insertar(ed1);

    }

    @Override
    public Autor loginAutor(String nick) {

            Autor aut = null;
            aut = autDAO.buscarPorNick(nick);
           return aut;
    }
    @Override
    public Editor loginEditor(String nick) {

            Editor edit = null;
            edit = editDAO.buscarPorNick(nick);
           return edit;
    }
    @Override
    public Revisor loginRevisor(String nick) {

            Revisor rev = null;
            rev = revDAO.buscarPorNick(nick);
           return rev;
    }
}
