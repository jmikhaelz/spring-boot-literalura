package mx.aluras.literatura.gutendex.cli;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import mx.aluras.literatura.gutendex.client.GutendexApiClient;
import mx.aluras.literatura.gutendex.model.Author;
import mx.aluras.literatura.gutendex.model.Book;
import mx.aluras.literatura.gutendex.model.BookResponse;
import mx.aluras.literatura.gutendex.model.GeminiApiProperties;
import mx.aluras.literatura.gutendex.repository.AuthorRepository;
import mx.aluras.literatura.gutendex.repository.BookRepository;
import mx.aluras.literatura.gutendex.service.GutendexApiService;

@Component
public class MenuInteractivo implements CommandLineRunner {
    // VARIABLE LOCALES
    private Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(MenuInteractivo.class.getName());
    // VARIABLE DE PROPIEDADES
    @Autowired
    private GeminiApiProperties geminiApiProperties = new GeminiApiProperties();
    // VARIABLE DE DB
    @Autowired
    private BookRepository repository;
    @Autowired
    private AuthorRepository repositoryA;

    @Override
    public void run(String... args) throws Exception {
        if (Arrays.asList(args).contains("--cli")) {
            BarraCargaUtil.mostrar(25, 8);
            mostrarMenu();
            System.out.println("¡Finalizado exitosamente el programa!");
        }
    }

    public void mostrarMenu() {
        int opcion;
        do {
            opcion = -1;
            System.out.println("\n|\t\t BOOK PLANET \t\t|");
            System.out.println("\t0. Salir");
            System.out.println("\t1. Buscar libro por Titulo en Gutendex");
            System.out.println("\t2. Buscar autor registrado");
            System.out.println("\t3. Buscar libro registrado");
            System.out.println("\t4. Buscar Idioma registrado");
            System.out.println("\t5. Buscar autores por fecha de nacimiento/fallecimiento registrados");
            System.out.print("Seleccione una opción: ");
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        buscarPorGutendex();
                        break;
                    case 2:
                        buscarPorAutor();
                        break;
                    case 3:
                        buscarPorLibro();
                        break;
                    case 4:
                        buscarPorIdioma();
                        break;
                    case 5:
                        buscarPorFechaAutor();
                        break;
                    case 0:
                        System.out.print("¿Seguro que quieres salir? [Ingrese Y/ Si no de ENTER]: ");
                        String respuesta = scanner.nextLine().trim().toLowerCase();
                        if (!(respuesta.equals("y")))
                            opcion = -1;
                        break;
                    default:
                        logger.warning("No existe la opcion ingresada");
                        break;
                }
            } else {
                logger.warning("No existe la opcion ingresada");
                scanner.nextLine();
            }

        } while (opcion != 0);
    }

    private void buscarPorLibro() {
        System.out.println("Ingrese el nombre del libro que quieres buscar: ");
        var abreviatura = scanner.nextLine();
        Optional<Book> bookBuscada = repository.buscarPorTitulo(abreviatura);
        if (bookBuscada.isPresent())
            System.out.println("Libro Encontrado : \n\t" + bookBuscada.get());
        else
            System.out.println("EL libro " + abreviatura + " no existente en DB");
    }

    private void buscarPorAutor() {
        System.out.println("Ingrese el nombre del autor@ que quieres buscar: ");
        var autor = scanner.nextLine();
        List<Author> autorBuscada = repositoryA.buscarPorAutor(autor);
        if (autorBuscada != null && !autorBuscada.isEmpty()) {
            autorBuscada.forEach(a -> {
                System.out.println(a + "\n\tLibros escritos :");
                a.getLibros().forEach(l -> {
                    System.out.println("\t" + l.getTitulo());
                });
            });

        } else {
            logger.warning(" Ninguna coincidencia con el nombre de " + autor);
        }
    }

    private void buscarPorFechaAutor() {
        System.out.println("Ingrese el año de fallecimiento/nacimiento del autor@ que quieres buscar: ");
        var autor = scanner.nextInt();
        List<Author> autorBuscada = repositoryA.buscarPorYear(autor);
        if (autorBuscada != null && !autorBuscada.isEmpty()) {
            autorBuscada.forEach(a -> {
                System.out.println(a + "\n\tLibros escritos :");
                a.getLibros().forEach(l -> {
                    System.out.println("\t" + l.getTitulo());
                });
            });

        } else {
            logger.warning(" Ninguna coincidencia con el nombre de " + autor);
        }
    }

    private void buscarPorIdioma() {
        System.out.println("\n\tIDOMA - ABREVIATURA");
        System.out.println("\n\tInglés - en" +
                "\n\t" +
                "Español - es" +
                "\n\t" +
                "Francés - fr" +
                "\n\t" +
                "Alemán - de" +
                "\n\t" +
                "Italiano - it" +
                "\n\t" +
                "Portugués - pt" +
                "\n\t" +
                "Latín - la" +
                "\n\t" +
                "Holandés - nl" +
                "\n\t" +
                "Chino - zh" +
                "\n\t" +
                "Sueco - sv");
        System.out.println("Ingrese la abreviatura del idioma:");
        var abreviatura = scanner.nextLine();
        if (IDIOMAS_ABREV.contains(abreviatura)) {
            List<Book> books = repository.findByIdioma(abreviatura);
            System.out.println("\n\t Libros en " + abreviatura);
            books.forEach(s -> System.out.println("*" + s));
        } else {
            logger.warning(" No existe la abreviatura: " + abreviatura);
        }
    }

    private void buscarPorGutendex() {
        while (true) {
            System.out.print("\n\t🔎 Ingrese el nombre del libro: ");
            String busqueda = scanner.nextLine().toLowerCase();
            if (obtenerDatoLibro(busqueda)) {
                System.out.println("✅ Datos Obtenidos del Libro: " + busqueda.toUpperCase());
                break;
            } else {
                BarraCargaUtil.mostrar(25, 8);

                logger.warning("❌ Sin datos obtenidos del Libro: " + busqueda.toUpperCase());
                System.out.print("¿Quieres seguir con otra búsqueda? [Ingrese Y/ Si no de ENTER]: ");
                var respuesta = scanner.nextLine().trim().toLowerCase();
                if (!(respuesta.equals("y")))
                    break;
            }
        }
    }

    private boolean obtenerDatoLibro(String busqueda) {
        try {
            // Solicitamos peticion a la API
            URI uri = URI.create("https://gutendex.com/books/?search="
                    + URLEncoder.encode(busqueda.toLowerCase(), StandardCharsets.UTF_8));
            //System.out.println(uri.toString());
            var resquest = new GutendexApiClient().getJSONToString(uri);
            // Procesamiento de JSON a Object
            BookResponse bookCache = new GutendexApiService().getAbout(resquest, BookResponse.class);
            /*
             * Mostrar todas las repuestas
             *
             * bookCache.resultados().stream().forEach(
             * b -> System.out.println(b.toString()));
             * 
             * Mostrar la primera respuesta
             */
            BarraCargaUtil.mostrar(25, 8);
            if (bookCache.resultados().get(0).titulo() != null) {
                Book consulta = new Book(bookCache.resultados().get(0), geminiApiProperties);
                System.out.println(consulta);
                repository.save(consulta);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return false;
    }

    public static final Set<String> IDIOMAS_ABREV = Set.of(
            "en", "es", "fr", "de", "it", "pt", "la", "nl", "zh", "sv");
}