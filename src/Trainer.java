import java.awt.*;
import java.util.*;
import java.util.List;

public class Trainer {

    private final String name;
    private String[] party;
    private Creature[] currentParty;
    private int dollars = 0;


    public Trainer(String name, String[] party) {
        this.name = name;
        this.party = party;
    }

    public Trainer(String name, Creature[] creatures) {
        Random random = new Random();
        this.name = name;
        this.currentParty = creatures;
        this.dollars = random.nextInt(50, 500);
    }

    public void printTrainerDetails() {
        System.out.println("---------- TRAINER DETAILS ----------");
        System.out.printf("Trainer Name: %s\nParty Size: %s\n", name, currentParty.length);
        for (int i = 0; i < currentParty.length; i++) {
            if (currentParty[i] != null) {
                System.out.println(currentParty[i]);
            }
        }
    }

    public String getTrainerDetails() {
        StringBuilder sb = new StringBuilder("\nTrainer Name: %s\nParty Size: %s\n".formatted(name, currentParty.length));
        for (String s : party) {
            sb.append("(").append(s.split(",")[1]).append(")");
        }
        return sb.toString();
    }

    public String getWriteInfo() {
        StringBuilder sb = new StringBuilder(name + "," + currentParty.length + System.lineSeparator());
        for (Creature c: currentParty) {
            sb.append(c.fileWriteInfo());
        }
        return sb.toString();
     }

     // modify the party based on index with a new level and effort value
    public void modifyPartyMember(int index, int level, int effortValue) {
        if (index < 0 || index >= currentParty.length) {
            System.out.println("Invalid index detected - current party size is " + currentParty.length);
        } else if (effortValue > 65535 || effortValue <= 0){
            System.out.println("Invalid effort value detected - value must be greater than 0 and less than 65535");
        } else if (level <= currentParty[index].getLevel() || level > 99) {
            System.out.println("Invalid level detected - level must be greater than current value and less than 100");
        }
        currentParty[index].generateStats(level, effortValue);
    }

    @Override
    public String toString() {
        return "Trainer Name: %s\nParty Size: %s\n".formatted(name, party.length);
    }

    // generate a random trainer based on trainer-data.csv. File has data for name and valid first-gen.csv values
    public static Trainer getRandomTrainer(List<String> trainerList, List<String> pokemonList) {
        Random random = new Random();
        String[] randomLine = trainerList.get(random.nextInt(0, trainerList.size())).split(",");
        List<Integer> trainerValues = new ArrayList<>();
        String name = randomLine[0];

        for (int i = 1; i < randomLine.length; i++) {
            trainerValues.add(Integer.parseInt(randomLine[i]));
        }

        Collections.shuffle(trainerValues);
        int randomPartySize = random.nextInt(1, trainerValues.size());
        Creature[] creatures = new Creature[randomPartySize];

        for (int i = 0; i < randomPartySize; i++) {
            creatures[i] = new Creature(pokemonList.get(trainerValues.get(i)));
        }

        return new Trainer(name, creatures);
    }

    public String toJSON() {
        StringJoiner sj = new StringJoiner(",", "[", "]");

        for (Creature c: currentParty) {
            sj.add(c.toJSON());
        }

        return new StringJoiner(", ", "{", "}")
                .add("\"name\":\"" + name + "\"")
                .add("\"dollars\":" + dollars)
                .add("\"party size\":" + currentParty.length)
                //.add("\"party\":[" + partyDetails + "]")
                .add("\"party\":" + sj)
                .toString();
    }
}
