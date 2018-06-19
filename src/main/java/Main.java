import java.io.File;
import java.util.*;

public class Main {

    public static final String GERMAN_FILE_PATH = "trackimo_android_app_strings_german_de.xlsx";
    public static final String ITALIAN_FILE_PATH = "trackimo_android_app_strings_italian_it.xlsx";
    public static final String PORTUGAL_FILE_PATH = "trackimo_android_app_strings_portuguese_pt.xlsx";
    public static final String SPAINISH_FILE_PATH = "trackimo_android_app_strings_spanish_es.xlsx";


    public static final String ANDROID_KEYS_FILE_PATH = "full.xlsx";

    public static void main(String[] args) {
        String spainFilePath, portugalFilePath, italianFilePath, germanFilePath, androidFileKeys;
        germanFilePath = ClassLoader.getSystemClassLoader().getResource(GERMAN_FILE_PATH).getFile();
        portugalFilePath = ClassLoader.getSystemClassLoader().getResource(PORTUGAL_FILE_PATH).getFile();
        spainFilePath = ClassLoader.getSystemClassLoader().getResource(SPAINISH_FILE_PATH).getFile();
        italianFilePath = ClassLoader.getSystemClassLoader().getResource(ITALIAN_FILE_PATH).getFile();
        androidFileKeys = ClassLoader.getSystemClassLoader().getResource(ANDROID_KEYS_FILE_PATH).getFile();


        //Map<Integer, Map<String, String>> engToGer, engToPort, engToSpain, engToItal;
        Map<String, String> engToGer, engToPort, engToSpain, engToItal;
        System.out.println(spainFilePath);

        engToPort = FileHelper.excelConverterToPairMap(portugalFilePath);
        /*engToGer = FileHelper.excelConverterToMap(germanFilePath);
        engToSpain = FileHelper.excelConverterToMap(spainFilePath);
        engToItal = FileHelper.excelConverterToMap(italianFilePath);*/

        List<String> androidKeys = loadAndroidWordKeys(androidFileKeys);


        Map<String, String> rightProjection = new HashMap<>();


        /*for (int i = 0; i < androidKeys.size(); i++) {
            String projKey, projValue;
            assert engToPort != null;
            for (Map.Entry<String, String> pair : engToPort.entrySet()
                    ) {
                String andKey = androidKeys.get(i);
                if (pair.getKey() != null){
                    if (andKey.contains(pair.getKey())) {
                        rightProjection.put(androidKeys.get(i), pair.getValue());

                    } else {
                        continue;
                    }
                }
            }
        }
        if (rightProjection != null && !rightProjection.isEmpty()) {
            printPairMap(rightProjection);
        }*/
        printPairMap(engToPort);


    }

    public static void printMap(Map<Integer, Map<String, String>> storage) {
        if (storage != null && !storage.isEmpty()) {

            for (Map.Entry<Integer, Map<String, String>> dictionary : storage.entrySet()
                    ) {
                String eng = null,
                        ger = null;
                String number = String.valueOf(dictionary.getKey());
                for (Map.Entry<String, String> wordPair : dictionary.getValue().entrySet()) {
                    eng = wordPair.getKey();
                    ger = wordPair.getValue();
                }
                System.out.printf("%s %s %s\n", number, eng, ger);
            }

        }
    }

    public String getFilePathByName(String name) {
        return ClassLoader.getSystemClassLoader().getResource(name).getFile();

    }

    public static List<String> loadAndroidWordKeys(String fullFilePath) {
        return FileHelper.excelConverterToList(fullFilePath);

    }

    public static void printList(List<?> list) {
        int counter = 0;
        for (Object item : list
                ) {
            counter++;
            System.out.println(counter + " " + item.toString());
        }
    }


    public static void printPairMap(Map<String, String> map) {
        map.forEach((key, val) -> System.out.println(key + " " + val));
    }
}
