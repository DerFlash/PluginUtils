package de.cubenation.plugins.utils.commandapi.testutils.testcommands;

import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Assert;

import de.cubenation.plugins.utils.commandapi.annotation.Command;

public class TestInvalidCommandWrongMethodParameterFirst {
    public TestInvalidCommandWrongMethodParameterFirst(JavaPlugin plugin) {
    }

    @Command(main = "test")
    public void wrongCommad(Integer player, String[] args) {
        Assert.fail("command should not be called");
    }
}