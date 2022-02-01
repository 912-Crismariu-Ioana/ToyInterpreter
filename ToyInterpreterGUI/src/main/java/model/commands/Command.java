package model.commands;

public abstract class Command {
    private final String key, description;

    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public abstract void execute();

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }
}
