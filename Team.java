public class Team {
    private String name;
    private Character[] members;

    public Team(String name, Character[] members) {
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return this.name;
    }

    public Character[] getMembers() {
        return this.members;
    }

    public Character[] getLivingMembers() {
        java.util.List<Character> living = new java.util.ArrayList<>();
        for (Character member : members) {
            if (member.isAlive()) {
                living.add(member);
            }
        }
        return living.toArray(new Character[0]);
    }

    public boolean isDefeated() {
        for (Character member : members) {
            if (member.isAlive()) {
                return false;
            }
        }
        return true;
    }

    public String toStringOnlyAlive() {
        StringBuilder sb = new StringBuilder();
        for (Character member : this.getLivingMembers()) {
            sb.append(member.prettyString()).append("\n");
        }
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Character member : members) {
            sb.append(member.prettyString()).append("\n");
        }
        return sb.toString();
    }
}