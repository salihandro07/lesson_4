import java.util.Random;

public class HW {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {290, 270, 250, 400};
    public static int[] heroesDamage = {20, 15, 10, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic"};
    public static final int MEDIC_HEAL_MIN = 50;
    public static final int MEDIC_HEAL_MAX = 300;
    public static final int MEDIC_INDEX = 3;

    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        medicHeals();
        printStatistics();
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i != MEDIC_INDEX && heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (i != MEDIC_INDEX && heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i].equals(bossDefence)) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
            }
        }
    }

    public static void medicHeals() {
        if (heroesHealth[MEDIC_INDEX] > 0) {
            int minHealthIndex = -1;
            int minHealthValue = 100;

            for (int i = 0; i < heroesHealth.length; i++) {
                if (i != MEDIC_INDEX && heroesHealth[i] > 0 && heroesHealth[i] < minHealthValue) {
                    minHealthValue = heroesHealth[i];
                    minHealthIndex = i;
                }
            }

            if (minHealthIndex != -1) {
                Random random = new Random();
                int healAmount = random.nextInt(MEDIC_HEAL_MAX - MEDIC_HEAL_MIN + 1)
                        + MEDIC_HEAL_MIN;
                heroesHealth[minHealthIndex] += healAmount;
                if (heroesHealth[minHealthIndex] > 100) {
                    heroesHealth[minHealthIndex] = 100;
                }
                System.out.println("Medic heals " + heroesAttackType[minHealthIndex]
                        + " for " + healAmount + " health.");
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " -----------------");
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}