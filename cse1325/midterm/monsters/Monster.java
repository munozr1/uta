public class Monster {
    String Name;
    String HealthPoints;
    String Level;
    String Speed;
    String Type;

    Monster(String name, String healthPoints, String level, String speed, String type) {
        this.Name = name;
        this.HealthPoints = healthPoints;
        this.Level = level;
        this.Speed = speed;
        this.Type = type;
    }

    @Override
    public String toString() {
        return Name + ',' +
                HealthPoints + ',' +
                Level + ',' +
                Speed + ',' +
                Type + ',';
    }
}
