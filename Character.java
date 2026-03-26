abstract class Character {
    private String name;
    private String role;
    private double max_hp;
    private double hp;
    private double max_stemina;
    private double stemina;
    private double base_atk;

    public Character(String name, String role, double max_hp, double max_stemina, double base_atk) {
        setName(name);
        setRole(role);
        setMax_hp(max_hp);
        setHp(max_hp);
        setMax_stemina(max_stemina);
        setStemina(max_stemina);
        setBase_atk(base_atk);
    }

    // Name
    private void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    // Role
    private void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return this.role;
    }

    // Max HP
    private void setMax_hp(double max_hp) {
        this.max_hp = max_hp;
    }
    public double getMax_hp() {
        return this.max_hp;
    }

    // HP
    private void setHp(double hp) {
        this.hp = hp > this.max_hp ? this.max_hp : hp < 0 ? 0 : hp;
    }
    public double getHp() {
        return this.hp;
    }

    // Max Stamina
    private void setMax_stemina(double max_stemina) {
        this.max_stemina = max_stemina;
    }
    public double getMax_stemina() {
        return this.max_stemina;
    }

    // Stamina
    private void setStemina(double stemina) {
        this.stemina = stemina > this.max_stemina ? this.max_stemina : stemina < 0 ? 0 : stemina;
    }
    public double getStemina() {
        return this.stemina;
    }

    // Base ATK Damage
    private void setBase_atk(double base_atk) {
        this.base_atk = base_atk;
    }
    public double getBase_atk() {
        return this.base_atk;
    }

    public void adjustHp(double delta) {
        setHp(getHp() + delta);
    }

    public void adjustStamina(double delta) {
        setStemina(getStemina() + delta);
    }

    abstract public void attack(Character target);

    public boolean isAlive() {
        return this.hp > 0;
    }

    public String toString() {
        return String.format(
            "Character{name='%s', role='%s', hp=%.1f/%.1f, stamina=%.1f/%.1f, base_atk=%.1f}",
            name, role, hp, max_hp, stemina, max_stemina, base_atk
        );
    }

    public String calculateBar(double current, double max) {
        int barLength = 20;
        int filledLength = (int) ((current / max) * barLength);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < filledLength; i++) {
            bar.append('|');
        }
        for (int i = filledLength; i < barLength; i++) {
            bar.append('\s');
        }
        return bar.toString();
    }

    public String prettyString() {
        return String.format(
            "Name: %s\nRole: %s\nHP: [%s] (%.1f/%.1f)\nStamina: [%s] (%.1f/%.1f)\nBase ATK: %.1f",
            name, role, calculateBar(hp, max_hp), hp, max_hp, calculateBar(stemina, max_stemina), stemina, max_stemina, base_atk
        );
    }
}
