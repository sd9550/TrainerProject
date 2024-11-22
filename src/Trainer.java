import java.awt.*;
import java.util.*;
import java.util.List;

public class Trainer {

    private final String name;
    private final String[] party;


    public Trainer(String name, String[] party) {
        this.name = name;
        this.party = party;
    }

    public void printTrainerDetails() {
        System.out.println("---------- TRAINER DETAILS ----------");
        System.out.printf("Trainer Name: %s\nParty Size: %s\n", name, party.length);
        for (int i = 0; i < party.length; i++) {
            if (party[i] != null) {
                System.out.print("\t(" + party[i].split(",")[1] + ")");
            }
        System.out.println();
        }
    }

    public String getTrainerDetails() {
        StringBuilder sb = new StringBuilder("\nTrainer Name: %s\nParty Size: %s\n".formatted(name, party.length));
        for (int i = 0; i < party.length; i++) {
            sb.append("(").append(party[i].split(",")[1]).append(")");
        }
        return sb.toString();
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
        String[] tempParty = new String[randomPartySize];

        for (int i = 0; i < randomPartySize; i++) {
            tempParty[i] = pokemonList.get(trainerValues.get(i));
        }

        return new Trainer(name, tempParty);
    }
}
