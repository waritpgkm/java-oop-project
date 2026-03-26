public class Warrior extends Character {
    public Warrior(String name, double max_hp, double max_stamina, double base_atk, double crit_rate,
            double crit_multiplier) {
        super(name, "Warrior", max_hp, max_stamina, base_atk, crit_rate, crit_multiplier);
    }

    public boolean attack(Character target) {
        if (getStemina() >= 10) {
            if (Math.random() < getCrit_rate()) {
                System.out.printf("%s swings a sword at %s for %.1f CRITICAL damage!\n", getName(), target.getName(),
                        getBase_atk() * getCrit_multiplier());
                target.adjustHp(-getBase_atk() * getCrit_multiplier());
            } else {
                System.out.printf("%s swings a sword at %s for %.1f damage!\n", getName(), target.getName(),
                        getBase_atk());
                target.adjustHp(-getBase_atk());
            }
            adjustStamina(-10);
        } else {
            System.out.printf("%s is too tired to swing a sword!\n", getName());
        }
        return target.isAlive();
    }

    public void ultimate(Character target) {
        if (getStemina() >= 30) {
            System.out.printf("%s performs a powerful strike on %s for %.1f damage!\n", getName(), target.getName(),
                    getBase_atk() * 2);
            target.adjustHp(-getBase_atk() * 2);
            adjustStamina(-30);
        } else {
            System.out.printf("%s is too tired to perform a powerful strike!\n", getName());
        }
    }
}
