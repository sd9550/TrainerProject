import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        String firstPath = "files/first-gen.csv", secondPath = "files/trainer-data.csv";
        List<String> firstGeneration = readFileAndReturnPokemonList(firstPath);
        Map<String, List<Integer>> trainerDataMap = readFileAndReturnTrainerMap(secondPath);
        List<String> trainerList = readFileAndReturnPokemonList(secondPath);
        List<Trainer> trainers = Stream.generate(() -> {
            assert trainerList != null;
            return Trainer.getRandomTrainer(trainerList, firstGeneration);
        }).limit(5).toList();

        //trainers.forEach(Trainer::printTrainerDetails);
       // assert trainerDataMap != null;
        //printAllData(trainerDataMap);
        //writeToFile(trainers);
        assert firstGeneration != null;
        Creature[] redParty = new Creature[]{new Creature(firstGeneration.get(25)),
                                new Creature(firstGeneration.get(1)),
                                new Creature(firstGeneration.get(4)),
                                new Creature(firstGeneration.get(7))};
        Trainer redTrainer = new Trainer("Red", redParty);
        redTrainer.printTrainerDetails();
        redTrainer.modifyPartyMember(0, 10, 15000);

    }

    public static List<String> readFileAndReturnPokemonList(String filePath) {
        List<String> pokemonList = new ArrayList<>();
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    pokemonList.add(line);
                }
                return pokemonList;
            } catch (IOException e) {
                System.out.println("IO Error: " + e.getLocalizedMessage());
            } finally {
                System.out.println("Total size of list: " + pokemonList.size());
            }
        } else {
            System.out.println("Unable to parse file - it does not exist");
        }

        return null;
    }

    public static Map<String, List<Integer>> readFileAndReturnTrainerMap(String filePath) {
        Map<String, List<Integer>> map = new LinkedHashMap<>();
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] listSplit = line.split(",");
                    List<Integer> numbers = new ArrayList<>();

                    for (int i = 1; i < listSplit.length; i++) {
                        numbers.add(Integer.parseInt(listSplit[i]));
                    }

                    map.put(listSplit[0], numbers);
                }

                return map;
            } catch (IOException e) {
                System.out.println("IO Error: " + e.getLocalizedMessage());
            } finally {
                System.out.println("Total size of map: " + map.size());
            }
        }
        return null;
    }

    public static void writeToFile(List<Trainer> trainers)  {
        File file = new File("output/test.txt");

        if (!file.exists()) {
            try {
                boolean createdDir = file.getParentFile().mkdir();
                boolean createdFile = file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error: " + e.getLocalizedMessage());
            }

        }
        try (FileWriter writer = new FileWriter("output/test.txt")) {
            for (Trainer t: trainers) {
                writer.write(t.getTrainerDetails());
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        System.out.println("Data was successfully written to " + file);
    }

    public static void printAllData(Map<String, List<Integer>> map) {
        map.forEach((k, v) -> System.out.println(k + ": " + v.toString()));
    }

    public static void printAllData(List<String> list) {
        for (String s: list) {
            String[] line = s.split(",");
            Arrays.stream(line).forEach(p -> System.out.printf("%-15s", p));
            System.out.println();

        }
    }

}
