package es.ujaen.dae.libros.Cliente;

import es.ujaen.dae.libros.Servidor.Autor;
import es.ujaen.dae.libros.Servidor.AutorDAO;
import es.ujaen.dae.libros.Servidor.EditorDAO;
import es.ujaen.dae.libros.Servidor.LibroDAO;
import es.ujaen.dae.libros.Servidor.RevisorDAO;
import es.ujaen.dae.libros.Servidor.Libro;
import es.ujaen.dae.libros.Servidor.Revisor;
import es.ujaen.dae.libros.Servidor.ServicioLibros;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alvarogonzalezgonzalez
 */
public class ClienteLibros {

    ApplicationContext context;
    
    public ClienteLibros(ApplicationContext context) {
        this.context = context;
    }

    private String menuAutor(String token, ServicioLibros servicios) {
        //Opciones de autor: publicarLibro y consultar libro
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        String opcionElegida;
        while (!salir) {
            System.out.println("***Bienvenido al menú de AUTOR *** ");
            System.out.println("a. Publicar Libro");
            System.out.println("b. Consultar Libro");
            System.out.println("q. Salir");

            try {

                System.out.println("Escribe el número correspondiente de la acción a realizar: ");
                opcionElegida = sn.next();

                switch (opcionElegida) {
                    case "a":
                        System.out.println("Has seleccionado la opcion 3. Publicar Libro");
                        Libro nuevoLibro2 = new Libro("978-8499899619", "Aventuras", "El temor de un hombre sabio",
                                "23/2/2013", "En revision", "Texto vacío");
                        if (servicios.publicarLibro(nuevoLibro2, token)) {
                            //publicación con éxito, no estaba repetido (isbn)
                            System.out.println("Libro publicado con éxito: " + nuevoLibro2.getTitulo());
                        } else {
                            System.out.println("El libro: " + nuevoLibro2.getTitulo() + " ya existe.");
                        }

                        break;
                    case "b":

                        System.out.println("Opción b: Consultar Libro");

                        boolean correcto = false;
                        int opcionSelec = -1;
                        while (!correcto) {
                            System.out.println("Escribe el número de la opción de búsqueda: ");
                            System.out.println("0. Por título");
                            System.out.println("1. Por temática");
                            opcionSelec = sn.nextInt();
                            if (opcionSelec == 0 || opcionSelec == 1) {
                                correcto = true;
                            }

                        }
                        String text = "";
                        if (opcionSelec == 0) {
                            correcto = false;
                            System.out.println("Escribe el título del libro a buscar: ");
                            while (!correcto) {
                                text = sn.nextLine();
                                if (!text.isEmpty()) {
                                    correcto = true;
                                }
                            }
                            Libro lib = servicios.consultarLibroTitulo(text);
                            if (lib != null) {
                                System.out.println("El contenido del libro " + text + " es :" + lib.getTexto());
                            } else {
                                System.out.println("Libro no encontrado");
                            }
                        } else if (opcionSelec == 1) {
                            correcto = false;
                            System.out.println("Escribe la temática del libro a buscar: ");
                            while (!correcto) {
                                text = sn.next();
                                if (!text.isEmpty()) {
                                    correcto = true;
                                }
                            }
                            List<Libro> libros = new ArrayList<>();
                            libros = servicios.consultarLibroTematica(text);
                            if (libros.size() > 0) {
                                for (Libro libro : libros) {
                                    System.out.println("El contenido del libro " + text + " es :" + libro.getTexto());
                                    System.out.println(libro);
                                }
                            } else {
                                System.out.println("Libro no encontrado");

                            }

                        }
                        break;

                    case "q":

                        salir = true;
                        break;

                }
            } catch (InputMismatchException e) {
                System.out.println("Alerta, debe insertar una letra válida");
                sn.next();
            }
        }
        return "q";
    }

    private String menuEditor(String token, ServicioLibros servicios) {
        //examinarContenidoLIbro y consultar libro

        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        boolean correcto = false;
        String opcionElegida;
        int libroSelec = 0;
        int opcionSelec = 0;
        while (!salir) {
            System.out.println("***Bienvenido al menú de EDITOR *** ");
            System.out.println("a. Examinar contenido libro");
            System.out.println("b. Consultar Libro");
            System.out.println("c. Consultar Revisores");
            System.out.println("q. Salir");

            try {

                System.out.println("Escribe el número correspondiente de la acción a realizar: ");
                opcionElegida = sn.next();

                switch (opcionElegida) {
                    case "a":
                        System.out.println("Opción a: Examinar contenido Libro");
                        List<Libro> lista = new ArrayList<>();
                        lista = servicios.librosParaRevisar(token,"revisionContenido");
                        if (lista.isEmpty()) {
                            System.out.println("No hay ningún libro para revisar su contenido.");
                        } else {
                            correcto = false;
                            while (!correcto) {
                                System.out.println("Escribe el número del libro a revisar: ");
                                for (int i = 0; i < lista.size(); i++) {
                                    System.out.println(i + ". " + lista.get(i).getTitulo());
                                }
                                libroSelec = sn.nextInt();
                                if (libroSelec >= 0 && libroSelec <= lista.size()) {
                                    correcto = true;
                                }

                            }

                            //Ya tenemos el libro a revisar libroSelec
                            System.out.println("El contenido del libro " + lista.get(libroSelec).getTitulo() + " es:"
                                    + lista.get(libroSelec).getTexto());
                            correcto = false;
                            while (!correcto) {
                                System.out.println("Seleccione la opción para calificar el contenido del libro: ");
                                System.out.println("0. No publicable");
                                System.out.println("1. Publicable, asignarle revisores");
                                opcionSelec = sn.nextInt();
                                if (opcionSelec == 0 || opcionSelec == 1) {
                                    correcto = true;
                                }

                            }
                            //Ya tenemos la calificacion del libro opcionSelec
                            //Actualizamos estado al libro
                            Libro l = lista.get(libroSelec);
                            if (opcionSelec == 0) {
                                int puntuacion=0;
                                servicios.rechazarLibro(l,puntuacion,token);
                                //servicios.modificarEstado(lista.get(libroSelec), "noPublicable");
                            } else if (opcionSelec == 1) {
                                int puntuacion=1;
                                servicios.aceptarLibro(l,puntuacion,token);
                                
                                
                            }
                            //si el estado a pasado a ser pendienteRevisores, llamamos a examinarContLibro y 
                            //asignamos revisores
                            //servicios.examinarContLibro(l, token);
                        }

                        break;

                    case "b":

                        System.out.println("Opción b: Consultar Libro");
                        boolean c = false;
                        int opSelec = -1;
                        while (!c) {
                            System.out.println("Escribe el número de la opción de búsqueda: ");
                            System.out.println("0. Por título");
                            System.out.println("1. Por temática");
                            opcionSelec = sn.nextInt();
                            if (opSelec == 0 || opSelec == 1) {
                                c = true;
                            }

                        }
                        String text = "";
                        if (opSelec == 0) {
                            c = false;
                            System.out.println("Escribe el título del libro a buscar: ");
                            while (!c) {
                                text = sn.nextLine();
                                if (!text.isEmpty()) {
                                    c = true;
                                }
                            }
                            Libro lib = servicios.consultarLibroTitulo(text);
                            if (lib != null) {
                                System.out.println("El contenido del libro " + text + " es :" + lib.getTexto());
                            } else {
                                System.out.println("Libro no encontrado");
                            }
                        } else if (opcionSelec == 1) {
                            c = false;
                            System.out.println("Escribe la temática del libro a buscar: ");
                            while (!c) {
                                text = sn.next();
                                if (!text.isEmpty()) {
                                    c = true;
                                }
                            }
                            List<Libro> libros = new ArrayList<>();
                            libros = servicios.consultarLibroTematica(text);
                            if (libros.size() > 0) {
                                for (Libro libro : libros) {
                                    System.out.println("El contenido del libro " + text + " es :" + libro.getTexto());
                                    System.out.println(libro);
                                }
                            } else {
                                System.out.println("Libro no encontrado");

                            }

                        }
                        break;
                    case "c":
                        System.err.println("Revisores registrados en el sistema, Nick:");

                        List<Revisor> r = servicios.consultarRevisores();
                        for (int i = 0; i < r.size(); i++) {
                            System.out.println(r.get(i));
                        }

                        break;
                    case "q":

                        salir = true;
                        break;

                }
            } catch (InputMismatchException e) {
                System.out.println("Alerta, debe insertar una letra válida");
                sn.next();
            }
        }
        return "q";
    }

    private String menuRevisor(String token, ServicioLibros servicios) {
        //asignarNota y consultar libro

        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        String opcionElegida;
        while (!salir) {
            System.out.println("***Bienvenido al menú de REVISOR *** ");
            System.out.println("a. Asignar nota");
            System.out.println("b. Consultar Libro");
            System.out.println("q. Salir");

            try {

                System.out.println("Escribe el número correspondiente de la acción a realizar: ");
                opcionElegida = sn.next();

                switch (opcionElegida) {

                    case "a":
                        System.out.println("Has seleccionado la opcion Asignar nota");
                        System.out.println("Tus libros pendientes de revisión son: ");
                        List<Libro> resul = null;
                        int cont = 0;
                        int libroElegido = 0;
                        int notaAsignada = 0;
                        //EN ESTE METODO HAY QUE MOSTRAR LOS LIBROS PENDIENTES DE REVISION DE CADA REVISOR
                        resul = servicios.librosParaPuntuar(token);
                        if (resul.isEmpty()) {
                            System.err.println("No tiene libros asignados para revisar.");
                            break;
                        } else {
                            for (Libro l : resul) {
                                System.out.println(cont + " " + l.getTitulo());
                                cont++;
                            }
                            System.out.println("¿Qué libro desea puntuar? ");
                            libroElegido = sn.nextInt();
                            System.out.println("¿Qué nota desea darle? ");
                            notaAsignada = sn.nextInt();
                            servicios.asignarNota(resul.get(libroElegido), notaAsignada, token);
                            System.err.println("Libro puntuado correctamente con la calificación de " + notaAsignada);
                        }

                        break;

                    case "b":
                        System.out.println("Opción b: Consultar Libro");

                        boolean correcto = false;
                        int opcionSelec = -1;
                        while (!correcto) {
                            System.out.println("Escribe el número de la opción de búsqueda: ");
                            System.out.println("0. Por título");
                            System.out.println("1. Por temática");
                            opcionSelec = sn.nextInt();
                            if (opcionSelec == 0 || opcionSelec == 1) {
                                correcto = true;
                            }

                        }
                        String text = "";
                        if (opcionSelec == 0) {
                            correcto = false;
                            System.out.println("Escribe el título del libro a buscar: ");
                            while (!correcto) {
                                text = sn.nextLine();
                                if (!text.isEmpty()) {
                                    correcto = true;
                                }
                            }
                            Libro lib = servicios.consultarLibroTitulo(text);
                            if (lib != null) {
                                System.out.println("El contenido del libro " + text + " es :" + lib.getTexto());
                            } else {
                                System.out.println("Libro no encontrado");
                            }
                        } else if (opcionSelec == 1) {
                            correcto = false;
                            System.out.println("Escribe la temática del libro a buscar: ");
                            while (!correcto) {
                                text = sn.next();
                                if (!text.isEmpty()) {
                                    correcto = true;
                                }
                            }
                            List<Libro> libros = new ArrayList<>();
                            libros = servicios.consultarLibroTematica(text);
                            if (libros.size() > 0) {
                                for (Libro libro : libros) {
                                    System.out.println("El contenido del libro " + text + " es :" + libro.getTexto());
                                    System.out.println(libro);
                                }
                            } else {
                                System.out.println("Libro no encontrado");

                            }

                        }

                        break;

                    case "q":
                        salir = true;
                        break;

                }
            } catch (InputMismatchException e) {
                System.out.println("Alerta, debe insertar una letra válida");
                sn.next();
            }
        }
        return "q";
    }

    public void run() {
       
        
        ServicioLibros servicios = context.getBean(ServicioLibros.class);
        servicios.rellenarColaRevisores();

        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        String opcionElegida;
        String nick = "";
        String pass = "";
        String pass1 = "";
        String rol = "";
        String nombre = "";
        String rolElegido = "";
        int carga = 0;
        System.out.println("*** Bienvenido a la plataforma E-libros ***");
        while (!salir) {

            System.out.println("a. Login");
            System.out.println("b. Registro");
            System.out.println("c. Consultar libro");

            System.out.println("q. Salir");
            String token = "";

            try {

                System.out.println("Escribe el número correspondiente de la acción a realizar: ");
                opcionElegida = sn.next();

                switch (opcionElegida) {
                    case "a":
                        nick = "";
                        pass = "";
                        while (nick.length() == 0) {
                            System.out.println("Introduce nick: ");
                            nick = sn.next();
                        }
                        while (pass.length() == 0) {
                            System.out.println("Introduce contraseña: ");
                            pass = sn.next();
                        }

                        //***COMPROBAR EXISTENCIA USUARIO Y NICK-PASS CORRECTO
                        //***SI DEVUELVE un token ...->seguimos
                        //***Si devuelce ""->fallo en el login
                        token = servicios.login(nick, pass);
                        if (token.length() == 0) {
                            System.out.println("Error en el login. Nick o pass inválidos ");
                            break;
                        } else {
                            if (token.indexOf("A", token.length() - 1) > 0) {
                                menuAutor(token, servicios);
                            } else if (token.indexOf("E", token.length() - 1) > 0) {
                                menuEditor(token, servicios);
                            } else if (token.indexOf("R", token.length() - 1) > 0) {
                                menuRevisor(token, servicios);
                            }
                        }
                        break;
                    case "b":

                        System.out.println("Registro");

                        System.out.println("Introduzca el rol deseado");
                        System.err.println("a. Autor");
                        System.err.println("b. Revisor");
                        System.err.println("c. Editor");
                        //recoger el rol
                        rolElegido = sn.next();
                        switch (rolElegido) {

                            case "a":
                                rol = "Autor";
                                break;
                            case "b":
                                rol = "Revisor";
                                break;
                            case "c":
                                rol = "Editor";
                                break;

                        }

                        //guardar usuario
                        System.out.println("Introduzca el nombre de usuario");
                        //recoger nombre de usuario
                        nombre = sn.next();
                        //comprobar que no existe
                        System.out.println("Introduzca el nick");
                        //recoger el nick de usuario
                        nick = sn.next();
                        if ("Revisor".equals(rol)) {
                            System.out.println("Introduzca la carga máxima de libros que desea revisar");
                            //recoger contraseña
                            carga = sn.nextInt();
                        }
                        System.out.println("Introduzca la contraseña");
                        //recoger contraseña
                        pass = sn.next();
                        System.out.println("Repita la contraseña");
                        //recoger contraseña
                        pass1 = sn.next();
                        //if contraseña==contraseña1
                        if (pass == null ? pass1 == null : pass.equals(pass1)) {
                            if ("Revisor".equals(rol)) {
                                token = servicios.registroUsuarioRevisor(nombre, nick, pass, rol, carga);
                                if (token != null) {
                                    System.out.println("Registrado con éxito como " + rol);
                                } else {
                                    System.out.println("Fallo en el registro");
                                }
                            }
                            token = servicios.registroUsuario(nombre, nick, pass, rol);
                            if (token != null) {
                                System.out.println("Registrado con éxito como" + rol);
                            } else {
                                System.out.println("Fallo en el registro");
                            }

                            break;
                        } else {
                            System.out.println("Contraseña incorrecta!");
                        }

                    case "c":
                        System.out.println("Opción c: Consultar Libro");

                        boolean correcto = false;
                        int opcionSelec = -1;
                        while (!correcto) {
                            System.out.println("Escribe el número de la opción de búsqueda: ");
                            System.out.println("0. Por título");
                            System.out.println("1. Por temática");
                            opcionSelec = sn.nextInt();
                            if (opcionSelec == 0 || opcionSelec == 1) {
                                correcto = true;
                            }

                        }
                        String text = "";
                        if (opcionSelec == 0) {
                            correcto = false;
                            System.out.println("Escribe el título del libro a buscar: ");
                            while (!correcto) {
                                text = sn.nextLine();
                                if (!text.isEmpty()) {
                                    correcto = true;
                                }
                            }
                            Libro lib = servicios.consultarLibroTitulo(text);
                            if (lib != null) {
                                System.out.println("El contenido del libro " + text + " es :" + lib.getTexto());
                            } else {
                                System.out.println("Libro no encontrado");
                            }
                        } else if (opcionSelec == 1) {
                            correcto = false;
                            System.out.println("Escribe la temática del libro a buscar: ");
                            while (!correcto) {
                                text = sn.next();
                                if (!text.isEmpty()) {
                                    correcto = true;
                                }
                            }
                            List<Libro> libros = null;
                            libros = servicios.consultarLibroTematica(text);
                            if (libros != null) {
                                for (Libro libro : libros) {
                                    System.out.println("El contenido del libro " + text + " es :" + libro.getTexto());
                                    System.out.println(libro);
                                }
                            } else {
                                System.out.println("Libro no encontrado");

                            }

                        }
                        break;

                    case "q":

                        salir = true;
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Alerta, debe insertar un número");
                sn.next();
            }
        }
    }
}
