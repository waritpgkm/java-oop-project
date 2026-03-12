class Game {
    public static void main(String[] args) {
        Character warrior1 = new Warrior("Aragorn", 100, 50, 20);
        Character mage1 = new Mage("Gandalf", 80, 60, 25);
        Character warrior2 = new Warrior("Legolas", 90, 40, 18);
        Character mage2 = new Mage("Saruman", 70, 70, 30);

        Team teamA = new Team("Fellowship", new Character[]{warrior1, mage1});
        Team teamB = new Team("Sauron's Forces", new Character[]{warrior2, mage2});

        System.out.println(teamA);
        System.out.println(teamB);

        // Simulate a battle
        while (!teamA.isDefeated() && !teamB.isDefeated()) {
            warrior1.attack(warrior2);
            mage1.attack(mage2);
            if (warrior2.isAlive()) {
                warrior2.attack(warrior1);
            }
            if (mage2.isAlive()) {
                mage2.attack(mage1);
            }
            System.out.println();
            System.out.println(teamA);
            System.out.print(teamB);

            System.out.println("------------------------------");
        }

        System.out.println((teamA.isDefeated() ? teamB.getName() : teamA.getName()) + " wins!");
    }
}