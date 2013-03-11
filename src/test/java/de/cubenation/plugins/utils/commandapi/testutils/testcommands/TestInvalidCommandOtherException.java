package de.cubenation.plugins.utils.commandapi.testutils.testcommands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Assert;

import de.cubenation.plugins.utils.commandapi.annotation.Command;

public class TestInvalidCommandOtherException {
    public TestInvalidCommandOtherException(JavaPlugin plugin) throws Exception {
        throw new Exception("test");
    }

    @Command(main = "test")
    public void emptyCommand(Player player, String[] args) {
        Assert.fail("command should not be called");
    }
}
