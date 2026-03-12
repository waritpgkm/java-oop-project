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

    public boolean isDefeated() {
        for (Character member : members) {
            if (member.isAlive()) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team ").append(name).append(":\n");
        for (Character member : members) {
            sb.append(member.prettyString()).append("\n");
        }
        return sb.toString();
    }
}