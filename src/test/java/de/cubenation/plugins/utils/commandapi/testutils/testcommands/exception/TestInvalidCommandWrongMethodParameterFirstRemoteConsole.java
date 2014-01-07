package de.cubenation.plugins.utils.commandapi.testutils.testcommands.exception;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginBase;
import org.junit.Assert;

import de.cubenation.plugins.utils.commandapi.annotation.Command;
import de.cubenation.plugins.utils.commandapi.annotation.SenderRemoteConsole;

public class TestInvalidCommandWrongMethodParameterFirstRemoteConsole {
    public TestInvalidCommandWrongMethodParameterFirstRemoteConsole(PluginBase plugin) {
    }

    @Command(main = "test")
    @SenderRemoteConsole
    public void wrongCommad(Player player, String[] args) {
        Assert.fail("command should not be called");
    }
}