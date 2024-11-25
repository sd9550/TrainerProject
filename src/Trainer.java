import java.awt.*;
import java.util.*;
import java.util.List;

public class Trainer {

    private final String name;
    private String[] party;
    private Creature[] currentParty;


    public Trainer(String name, String[] party) {
        this.name = name;
        this.party = party;
    }

    public Trainer(String name, Creature[] creatures) {
        this.name = name;
        this.currentParty = creatures;
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

    public boolean modifyPartyMember(int index, int level, int effortValue) {
        if (index >= currentParty.length || effortValue > 65535) {
            System.out.println("Invalid index or EV detected");
            return false;
        } else if (level > 99) {
            System.out.println("Invalid level detected");
            return false;
        }

        currentParty[index].generateStats(level, effortValue);
        return true;
    }

    @Override
    public String toString() {
        return "Trainer Name: %s\nParty Size: %s\n".formatted(name, party.length);
    }

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
}
