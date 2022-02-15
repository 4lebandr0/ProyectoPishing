import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.*;

public class CuentaPalabras {

    private static final String CLASS_NAME = CuentaPalabras.class.getSimpleName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

    public static void main(String[] args) throws IOException {

        FileHandler logFile = new FileHandler("Logging.txt");
        SimpleFormatter plainText = new SimpleFormatter();
        logFile.setFormatter(plainText);
        LOG.addHandler(logFile);
        LOG.setLevel(Level.ALL);

        int txt=1;
        //String target_dir = "Nombre_Directorio";
        File dir = new File(args[0]);
        File[] files = dir.listFiles();
        //COnjunto de palabras vacias
        String[] palabrasvacias = {"a","aca","al","algo","algunos","algunas","alla"
                ,"ambos","ante","ante","antes","aquel","aquella","aquello","aquellas"
                ,"aquellos","aqui","arriba","asi","aun","aunque","bastante","bien"
                ,"cabe","cada","casi","cierto","cierta","ciertos","ciertas","como"
                ,"con","conmigo","contigo","conseguimos","conseguiste","consegui"
                ,"consigo","consigues","consigue","contigo","contra","cual","cuales"
                ,"cualquier","cualquiera","cuan","cuando","cuanto","cuanta","cuantos"
                ,"cuantas","de","dejar","del","demas","demasiada","demasiado","demasiadas"
                ,"demasiados","dentro","desde","donde","el","ella","ellas","ellos","emplea"
                ,"emplean","empleo","en","encima","entonces","entre","era","eras","eras","eramos"
                ,"eran","eres","es","esa","ese","eso","esas","esos","esta","estas","estaba"
                ,"estado","estamos","estan","estar","este","esto","estos","estoy","etc","fin"
                ,"fue","fueron","fui","fuimos","ha","hace","haces","hacen","hacer","hacia"
                ,"hago","hasta","incluso","intenta","intento","intentas","intentamos","intentan"
                ,"intentar","intento","ir","junto","juntos","la","los","las","largo","me","menos"
                ,"mi","mis","mia","mias","mientras","mio","mios","misma","mismo","mismas","mismos"
                ,"modo","mucha","muchas","mucho","muchos","muchisimas","muchisimos","muchisima"
                ,"muchisimo","muy","nada","ni","ninguno","ninguna","ningunos","ningunas","no"
                ,"nos","nosotras","nosotros","nuestra","nuestro","nuestras","nuestros","nunca"
                ,"otra","otro","otras","otros","para","parecer","pero","poca","poco","pocas"
                ,"pocos","podemos","poder","podria","podriamos","podrias","por","porque","primero"
                ,"puede","pueden","puedo","pues","que","querer","quienes","sabe","sabes","saben"
                ,"sabemos","saber","se","ser","si","siempre","siendo","sin","sino","sobre","solamente"
                ,"solo","somos","soy","sra","sr","sres","sta","su","sus","suya","suyo","suyas","suyos"
                ,"tal","tales","tampoco","tan","tanta","tanto","tantas","tantos","te","tenemos","tener"
                ,"tengo","ti","tiene","tienen","toda","todo","todas","todos","tomar","trabaja","trabajo"
                ,"trabajamos","trabajan","trabajar","trabajas","tras","tu","tus","tuya","tuyo","tuyas"
                ,"tuyos","ultimo","un","una","unos","unas","usa","usas","usamos","usan","usar","uso"
                ,"usted","ustedes","va","van","vamos","varias","varios","vaya","verdadero","voy","y"
                ,"ya","yo"};

        for (File f : files) {
            if (args.length == 0) {
                System.out.println("Falta el nombre de archivo");
                System.exit(0);
            }
            String name = f.getName();
            FileReader fi = null;
            try {
                fi = new FileReader(f);
            } catch (FileNotFoundException ex) {
                System.out.println( ex.getMessage());
                System.exit(-1);
            }

            //Usar para leer linea x linea el archivo
            BufferedReader inputFile = new BufferedReader(fi);

            String textLine = null;

            int lineCount = 0;
            int wordCount = 0;
            int numberCount = 0;
            int contUtiles = 0;

            String delimiters = "\\s+|,\\s*|\\.\\s*|\\;\\s*|\\:\\s*|\\!\\s*|\\¡\\s*|\\¿\\s*|\\?\\s*|\\-\\s*"
                    + "|\\[\\s*|\\]\\s*|\\(\\s*|\\)\\s*|\\\"\\s*|\\_\\s*|\\%\\s*|\\+\\s*|\\/\\s*|\\#\\s*|\\$\\s*";


            // Lista con todas las palabras diferentes
            HashSet<String> wordsSet = new HashSet<String>();
            // Tiempo inicial
            long startTime = System.currentTimeMillis();
            try {
                String[] words = new String[0];
                while ((textLine = inputFile.readLine()) != null) {
                    lineCount++;

                    if (textLine.trim().length() == 0) {
                        continue; // la linea esta vacia, continuar
                    }

                    // separar las palabras en cada linea
                    words = textLine.split(delimiters);
                    wordCount += words.length;

                    for (String theWord : words) {

                        theWord = theWord.toLowerCase().trim();

                        boolean isNumeric = true;

                        // verificar si el token es un numero
                        try {
                            Double num = Double.parseDouble(theWord);
                        } catch (NumberFormatException e) {
                            isNumeric = false;
                        }

                        // Si el token es un numero, pasar al siguiente
                        if (isNumeric) {
                            numberCount++;
                            continue;
                        }

                        wordsSet.add(theWord);

                        // si la palabra no esta en la lista, agregar a la lista
                        //if ( !wordsSet.contains(theWord) ) {
                        // wordsSet.add( theWord );
                        //}
                    }
                }
                //Contador de palabras vacias
                // Obtener tiempo de ejecución
                long tiempoEjecucion = System.currentTimeMillis() - startTime;
                inputFile.close();
                fi.close();

                System.out.printf("%2.3f  segundos, %2d lineas y %3d palabras\n",
                        tiempoEjecucion / 1000.00, lineCount, wordCount - numberCount);

                // Mostrar total de palabras diferentes
                System.out.printf("%5d palabras diferentes\n", wordsSet.size());
                for (String word : wordsSet) {
                    System.out.println(word);

                }
                //Crear archivos de texto
                try {
                    File myObj = new File("textos/PalabrasCompletas"+txt+".txt");
                    if (myObj.createNewFile()) {
                        System.out.println("File created: " + myObj.getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {
                    File myObj = new File("textos/PalabrasVacias"+txt+".txt");
                    if (myObj.createNewFile()) {
                        System.out.println("File created: " + myObj.getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                //Filtrar palabras
                HashSet<String> wordWithStopWord = new HashSet<String>(wordsSet);
                HashSet<String> wordsSetComplete = new HashSet<String>(wordsSet);
                HashSet<String> StopWordsSet = new HashSet<>(Arrays.asList(palabrasvacias));
                wordWithStopWord.removeAll(StopWordsSet);
                wordsSetComplete.removeAll(wordWithStopWord);
                int palabrasCompletas = 0, palabrasVacias = 0;
                try {
                    FileWriter myWriter = new FileWriter("textos/PalabrasCompletas"+txt+".txt");
                    for(String StopWord : wordWithStopWord){
                        palabrasCompletas += 1;
                        // palabras completas
                        myWriter.write(StopWord+"\n");

                    }
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                    System.out.println("Palabras completas archivo: "+palabrasCompletas);
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {
                    FileWriter myWriter = new FileWriter("textos/PalabrasVacias"+txt+".txt");
                    for(String Word : wordsSetComplete){
                        //palabras vacias
                        palabrasVacias += 1;
                        myWriter.write(Word+"\n");

                    }
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                    System.out.println("Palabras vacias archivo: "+ palabrasVacias);
                    double porcent = palabrasCompletas*100/(wordCount - numberCount);
                    System.out.println("Porcentaje palabras utiles%palabras totales: " + porcent + "%");
                    LOG.info( name+", "+wordCount+", "+palabrasCompletas+", "+wordsSet+"\n");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            } catch (IOException ex) {
                System.out.println( ex.getMessage() );
            }
            txt++;
        }
    }
}
