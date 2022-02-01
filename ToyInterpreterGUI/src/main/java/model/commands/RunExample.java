package model.commands;

import controller.Controller;

public class RunExample extends Command {
    private final Controller ctr;

    public RunExample(String key, String description, Controller ctr) {
        super(key, description);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.allStep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
