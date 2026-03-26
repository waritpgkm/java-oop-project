import java.util.Scanner;

class Game {
    private static Scanner scanner = new Scanner(System.in);
    private static Team playerTeam;
    private static Team botTeam;
    private static Character[] livingPlayers;
    private static Character[] livingBots;

    public static void main(String[] args) {
        // Create characters
        Character warrior1 = new Warrior("Ekanan", 100, 50, 20, 0.1, 1.8);
        Character mage1 = new Mage("Sarun", 80, 60, 30, 0.1, 1.5);
        Character warrior2 = new Warrior("Somsak", 100, 50, 20, 0.1, 1.8);
        Character mage2 = new Mage("Somchai", 80, 60, 30, 0.1, 1.5);

        // Create teams
        playerTeam = new Team("Player Team", new Character[] { warrior1, mage1 });
        botTeam = new Team("Bot Team", new Character[] { warrior2, mage2 });

        livingPlayers = playerTeam.getLivingMembers();
        livingBots = botTeam.getLivingMembers();

        System.out.println("=== WELCOME TO THE BATTLE ARENA ===");
        System.out.println("You control the Player Team!");
        System.out.println("The Bot controls the Bot Team!");
        System.out.println();

        // Game loop
        int round = 1;

        while (!playerTeam.isDefeated() && !botTeam.isDefeated()) {
            System.out.printf("================================\n%12sRound: %d\n================================\n", "",
                    round);

            displayTeams(playerTeam, botTeam);

            System.out.println("YOUR TURN:");
            playerMove();

            System.out.println();

            System.out.println("BOT'S TURN:");
            botMove();

            // Regenerate 5 stamina each round for all living characters
            for (Character member : playerTeam.getLivingMembers()) {
                member.adjustStamina(5);
            }
            for (Character member : botTeam.getLivingMembers()) {
                member.adjustStamina(5);
            }

            round++;
        }

        if(isEnd()) {
            endGame();
        }
    }

    private static void playerMove() {
        // Find living characters

        if (livingPlayers.length == 0)
            return;

        // Each living player character makes a move
        for (Character playerChar : livingPlayers) {
            System.out.println("\n" + playerChar.getName() + "'s turn:");
            makePlayerCharacterMove(playerChar, livingBots, livingPlayers);
        }
    }

    private static void makePlayerCharacterMove(Character playerChar, Character[] botTargets,
            Character[] playerAllies) {

        System.out.println("Choose an action for " + playerChar.getName() + ":");
        System.out.println("HP: " + String.format("%.1f/%.1f", playerChar.getHp(), playerChar.getMax_hp()));
        System.out.println(
                "Stamina: " + String.format("%.1f/%.1f", playerChar.getStemina(), playerChar.getMax_stemina()));

        if (playerChar instanceof Warrior) {
            System.out.println("1. Attack (10 stamina)");
            System.out.println("2. Ultimate Strike (30 stamina)");
            System.out.println("3. Skip Turn");
            System.out.print("Choose (1-3): ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Character target1 = chooseTarget(botTargets);
                    if (target1 != null) {
                        ((Warrior) playerChar).attack(target1);
                        if (!target1.isAlive()) {
                            eliminate(target1);
                        }
                    }
                    break;

                case 2:
                    Character target2 = chooseTarget(botTargets);
                    if (target2 != null) {
                        ((Warrior) playerChar).ultimate(target2);
                        if (!target2.isAlive()) {
                            eliminate(target2);
                        }
                    }
                    break;

                case 3:
                    System.out.println(playerChar.getName() + " skips their turn.");
                    break;

                default:
                    break;
            }
        } else if (playerChar instanceof Mage) {
            System.out.println("1. Attack (15 stamina)");
            System.out.println("2. Heal Ally (20 stamina)");
            System.out.println("3. Heal All Allies (30 stamina)");
            System.out.println("4. Skip Turn");
            System.out.print("Choose (1-4): ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Character target = chooseTarget(botTargets);
                    if (target != null) {
                        ((Mage) playerChar).attack(target);
                        if (!target.isAlive()) {
                            eliminate(target);
                        }
                    }
                    break;
                case 2:
                    Character ally = chooseAlly(playerAllies);
                    if (ally != null) {
                        ((Mage) playerChar).healing_allie(ally);
                    }
                    break;
                case 3:
                    ((Mage) playerChar).healing_all_allies(playerAllies);
                    break;

                case 4:
                    System.out.println(playerChar.getName() + " skips their turn.");
                    break;

                default:
                    break;
            }
        } else {
            System.out.println("Invalid choice or insufficient stamina. Try again.");
        }
    }

    private static Character chooseTarget(Character[] targets) {
        if (targets.length == 0) {
            System.out.println("No targets available!");
            return null;
        }
        if (targets.length == 1) {
            System.out.println("Only one target available: " + targets[0].getName());
            return targets[0];
        }

        System.out.println("Choose a target:");
        for (int i = 0; i < targets.length; i++) {
            System.out.println((i + 1) + ". " + targets[i].getName() +
                    " (HP: " + String.format("%.1f/%.1f", targets[i].getHp(), targets[i].getMax_hp()) + ")");
        }
        System.out.print("Choose (1-" + targets.length + "): ");

        int choice = scanner.nextInt();
        if (choice >= 1 && choice <= targets.length) {
            return targets[choice - 1];
        } else {
            System.out.println("Invalid choice. No action taken.");
            return null;
        }
    }

    private static Character chooseAlly(Character[] allies) {
        System.out.println("Choose an ally to heal:");
        for (int i = 0; i < allies.length; i++) {
            System.out.println((i + 1) + ". " + allies[i].getName() +
                    " (HP: " + String.format("%.1f/%.1f", allies[i].getHp(), allies[i].getMax_hp()) + ")");
        }
        System.out.print("Choose (1-" + allies.length + "): ");

        int choice = scanner.nextInt();
        if (choice >= 1 && choice <= allies.length) {
            return allies[choice - 1];
        }
        return null;
    }

    public static void botMove() {
        // Find living bot members and player targets
        Character[] livingBotMembers = botTeam.getLivingMembers();
        Character[] livingPlayerMembers = playerTeam.getLivingMembers();

        if (livingBotMembers.length == 0 || livingPlayerMembers.length == 0) {
            return; // No moves possible
        }

        // Each living bot character makes a move
        for (Character botChar : livingBotMembers) {
            makeCharacterMove(botChar, livingPlayerMembers, livingBotMembers);
        }
    }

    private static void makeCharacterMove(Character botChar, Character[] playerTargets, Character[] botAllies) {
        if (botChar instanceof Warrior) {
            makeWarriorMove((Warrior) botChar, playerTargets);
        } else if (botChar instanceof Mage) {
            makeMageMove((Mage) botChar, playerTargets, botAllies);
        }
    }

    private static void makeWarriorMove(Warrior warrior, Character[] playerTargets) {
        // Find the weakest living target
        Character weakestTarget = findWeakestTarget(playerTargets);

        if (weakestTarget != null) {
            // 50% chance to use ultimate if enough stamina
            if (warrior.getStemina() >= 30 && Math.random() < 0.5) {
                warrior.ultimate(weakestTarget);
            } else {
                warrior.attack(weakestTarget);
                if (!weakestTarget.isAlive()) {
                    eliminate(weakestTarget);
                }
            }
        }
    }

    private static void makeMageMove(Mage mage, Character[] playerTargets, Character[] botAllies) {
        // Check if any ally needs healing (HP below 50%)
        Character allyToHeal = findAllyNeedingHealing(botAllies);

        if (allyToHeal != null && mage.getStemina() >= 20) {
            // 40% chance to heal all allies if multiple need healing
            Character[] alliesNeedingHealing = getAlliesNeedingHealing(botAllies);
            if (alliesNeedingHealing.length > 1 && mage.getStemina() >= 30 && Math.random() < 0.4) {
                mage.healing_all_allies(alliesNeedingHealing);
            } else {
                mage.healing_allie(allyToHeal);
            }
        } else {
            // Attack the weakest target
            Character weakestTarget = findWeakestTarget(playerTargets);
            if (weakestTarget != null) {
                mage.attack(weakestTarget);
                if (!weakestTarget.isAlive()) {
                    eliminate(weakestTarget);
                }
            }
        }
    }

    private static Character findWeakestTarget(Character[] targets) {
        Character weakest = null;
        double lowestHp = Double.MAX_VALUE;

        for (Character target : targets) {
            if (target.isAlive() && target.getHp() < lowestHp) {
                lowestHp = target.getHp();
                weakest = target;
            }
        }
        return weakest;
    }

    private static Character findAllyNeedingHealing(Character[] allies) {
        for (Character ally : allies) {
            if (ally.isAlive() && ally.getHp() / ally.getMax_hp() < 0.5) {
                return ally;
            }
        }
        return null;
    }

    private static Character[] getAlliesNeedingHealing(Character[] allies) {
        java.util.List<Character> needingHealing = new java.util.ArrayList<>();
        for (Character ally : allies) {
            if (ally.isAlive() && ally.getHp() / ally.getMax_hp() < 0.5) {
                needingHealing.add(ally);
            }
        }
        return needingHealing.toArray(new Character[0]);
    }

    private static void displayTeams(Team playerTeam, Team botTeam) {
        System.out.println("PLAYER TEAM:");
        System.out.println(playerTeam.toStringOnlyAlive());
        System.out.println("BOT TEAM:");
        System.out.println(botTeam.toStringOnlyAlive());
    }

    private static void eliminate(Character target) {
        System.out.println(target.getName() + " has been eliminated!");
        if (isEnd()) {
            endGame();
        }
        livingPlayers = playerTeam.getLivingMembers(); // Update living players after defeat
        livingBots = botTeam.getLivingMembers(); // Update living bots after defeat
    }

    private static boolean isEnd() {
        return playerTeam.isDefeated() || botTeam.isDefeated();
    }

    private static void endGame() {
        if (playerTeam.isDefeated()) {
            System.out.println("BOT WINS! Better luck next time!");
        } else if (botTeam.isDefeated()) {
            System.out.println("YOU WIN! Congratulations!");
        }
        System.exit(0);
    }
}