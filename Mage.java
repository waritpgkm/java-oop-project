public class Mage extends Character {
    public Mage(String name, double max_hp, double max_stamina, double base_atk) {
        super(name, "Mage", max_hp, max_stamina, base_atk);
    }

    public void attack(Character target) {
        if (getStemina() >= 15) {
            System.out.printf("%s casts a spell on %s for %.1f damage!\n", getName(), target.getName(), getBase_atk());
            target.adjustHp(-getBase_atk());
            adjustStamina(-15);
        } else {
            System.out.printf("%s is too tired to cast a spell!\n", getName());
        }
    }

    public void healing_allie(Character target) {
        if (getStemina() >= 20) {
            System.out.printf("%s casts a healing spell on %s for %.1f HP!\n", getName(), target.getName(), getBase_atk() * 0.5);
            target.adjustHp(getBase_atk() * 0.8);
            adjustStamina(-20);
        } else {
            System.out.printf("%s is too tired to cast a healing spell!\n", getName());
        }
    }

    public void healing_all_allies(Character[] allies) {
        if (getStemina() >= 30) {
            System.out.printf("%s casts a group healing spell on all allies for %.1f HP!\n", getName(), getBase_atk() * 0.8);
            for (Character ally : allies) {
                ally.adjustHp(getBase_atk() * 0.5);
            }
            adjustStamina(-30);
        } else {
            System.out.printf("%s is too tired to cast a group healing spell!\n", getName());
        }
    }
}
