import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Creature {

    private String name;
    private int level = 1;
    private String[] creatureTypes = new String[2];
    private Map<String, Integer> creatureStats = new LinkedHashMap<>();
    private final String[] BASE_STATS = {"HP", "ATTACK", "DEFENSE", "SP ATTACK", "SP DEFENSE", "SPEED"};
    private Map<String, Integer> individualValues = new LinkedHashMap<>();

    public Creature(String name, String[] creatureTypes, int[] stats) {
        this.name = name;
        this.creatureTypes = creatureTypes;

        for (int i = 0; i < BASE_STATS.length; i++) {
            creatureStats.put(BASE_STATS[i], stats[i]);
        }

        generateIndividualValues();
    }

    public Creature(String firstGen) {
        try {
            String[] split = firstGen.split(",");
            this.name = split[1];
            this.creatureTypes[0] = split[2];
            this.creatureTypes[1] = split[3];
            int[] stats = Arrays.stream(Arrays.copyOfRange(split, 4, 10 )).mapToInt(Integer::parseInt).toArray();

            for (int i = 0; i < BASE_STATS.length; i++) {
                creatureStats.put(BASE_STATS[i], stats[i]);
            }

            generateIndividualValues();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }

    }

    private void generateIndividualValues() {
        Random random = new Random();
        int hp = 0;
        individualValues.put("HP", 0);

        for (int i = 1; i < BASE_STATS.length; i++) {
            individualValues.put(BASE_STATS[i], random.nextInt(0, 16));
        }

        if (individualValues.get("ATTACK") % 2 == 1) hp += 8;
        if (individualValues.get("DEFENSE") % 2 == 1) hp += 4;
        if (individualValues.get("SPEED") % 2 == 1) hp += 2;
        if (individualValues.get("SP ATTACK") % 2 == 1) hp += 1;

        individualValues.put("HP", hp);
    }

    public void generateStats(int level, int effortValue) {
        this.level = level;
        var copy = Map.copyOf(creatureStats);

        for (String s: BASE_STATS) {
            int base = creatureStats.get(s);
            int indiv = individualValues.get(s);
            int updatedStat = (int) (Math.floor((((base + indiv) * 2 + Math.floor(Math.ceil(Math.sqrt(effortValue)) / 4)) * level) / 100) + 5);
            if (s.equals("HP")) {
                updatedStat += 5 + level;
            }
            creatureStats.put(s, updatedStat);
        }

        System.out.println("Updated stats for " + name + " - Level " + level);
        for (String s: BASE_STATS) {
            System.out.print(s + "-" + copy.get(s) + "->" + creatureStats.get(s) + "\n");
        }

    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "\nName: %s - %s\nBase Stats: %s".formatted(name, Arrays.toString(creatureTypes), creatureStats.toString());
    }
}
