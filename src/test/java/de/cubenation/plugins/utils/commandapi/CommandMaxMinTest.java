package de.cubenation.plugins.utils.commandapi;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.junit.Assert;
import org.junit.Test;

import de.cubenation.plugins.utils.commandapi.exception.CommandException;
import de.cubenation.plugins.utils.commandapi.testutils.AbstractTest;
import de.cubenation.plugins.utils.commandapi.testutils.TestPlayer;
import de.cubenation.plugins.utils.commandapi.testutils.testcommands.minmax.TestValidCommandMax;
import de.cubenation.plugins.utils.commandapi.testutils.testcommands.minmax.TestValidCommandMin;
import de.cubenation.plugins.utils.commandapi.testutils.testcommands.minmax.TestValidCommandMinMaxExact;
import de.cubenation.plugins.utils.commandapi.testutils.testcommands.minmax.TestValidCommandMinMaxRange;
import de.cubenation.plugins.utils.commandapi.testutils.testcommands.minmax.TestValidCommandNoMax;

public class CommandMaxMinTest extends AbstractTest {
    @Test
    public void testMaxCommand() throws CommandException {
        commandsManager.add(TestValidCommandMax.class);

        final ArrayList<String> chatList = new ArrayList<String>();
        TestPlayer player = new TestPlayer() {
            @Override
            public void sendMessage(String message) {
                chatList.add(message);
            }
        };

        executeComannd("/test", player);
        Assert.assertEquals(0, chatList.size());
        executeComannd("/test 5", player);
        Assert.assertEquals(0, chatList.size());
        executeComannd("/test 5 1", player);
        Assert.assertEquals(1, chatList.size());

        Assert.assertEquals(1, testValid.size());
        Assert.assertTrue(testValid.containsKey("testMaxCommand"));
        Assert.assertEquals(new Short((short) 2), testValid.get("testMaxCommand"));

        Assert.assertEquals(1, chatList.size());
        Assert.assertEquals(ChatColor.RED + "Zu viel Parameter angegeben", chatList.get(0));
    }

    @Test
    public void testNoMaxCommand() throws CommandException {
        commandsManager.add(TestValidCommandNoMax.class);

        final ArrayList<String> chatList = new ArrayList<String>();
        TestPlayer player = new TestPlayer() {
            @Override
            public void sendMessage(String message) {
                chatList.add(message);
            }
        };

        executeComannd("/test", player);
        Assert.assertEquals(0, chatList.size());
        executeComannd("/test 5", player);
        Assert.assertEquals(1, chatList.size());
        executeComannd("/test 5 1", player);
        Assert.assertEquals(2, chatList.size());

        Assert.assertEquals(1, testValid.size());
        Assert.assertTrue(testValid.containsKey("testNoMaxCommand"));
        Assert.assertEquals(new Short((short) 1), testValid.get("testNoMaxCommand"));

        Assert.assertEquals(2, chatList.size());
        Assert.assertEquals(ChatColor.RED + "Befehl unterstützt keine Parameter", chatList.get(0));
        Assert.assertEquals(ChatColor.RED + "Befehl unterstützt keine Parameter", chatList.get(1));
    }

    @Test
    public void testMinCommand() throws CommandException {
        commandsManager.add(TestValidCommandMin.class);

        final ArrayList<String> chatList = new ArrayList<String>();
        TestPlayer player = new TestPlayer() {
            @Override
            public void sendMessage(String message) {
                chatList.add(message);
            }
        };

        executeComannd("/test", player);
        Assert.assertEquals(1, chatList.size());
        executeComannd("/test 5", player);
        Assert.assertEquals(1, chatList.size());
        executeComannd("/test 5 1", player);
        Assert.assertEquals(1, chatList.size());

        Assert.assertEquals(1, testValid.size());
        Assert.assertTrue(testValid.containsKey("testMinCommand"));
        Assert.assertEquals(new Short((short) 2), testValid.get("testMinCommand"));

        Assert.assertEquals(1, chatList.size());
        Assert.assertEquals(ChatColor.RED + "Mindest Anzahl an Parameter nicht angegeben", chatList.get(0));
    }

    @Test
    public void testMinMaxExactCommand() throws CommandException {
        commandsManager.add(TestValidCommandMinMaxExact.class);

        final ArrayList<String> chatList = new ArrayList<String>();
        TestPlayer player = new TestPlayer() {
            @Override
            public void sendMessage(String message) {
                chatList.add(message);
            }
        };

        executeComannd("/test", player);
        Assert.assertEquals(1, chatList.size());
        executeComannd("/test 5", player);
        Assert.assertEquals(1, chatList.size());
        executeComannd("/test 5 1", player);
        Assert.assertEquals(2, chatList.size());

        Assert.assertEquals(1, testValid.size());
        Assert.assertTrue(testValid.containsKey("testMinMaxExactCommand"));
        Assert.assertEquals(new Short((short) 1), testValid.get("testMinMaxExactCommand"));

        Assert.assertEquals(2, chatList.size());
        Assert.assertEquals(ChatColor.RED + "Mindest Anzahl an Parameter nicht angegeben", chatList.get(0));
        Assert.assertEquals(ChatColor.RED + "Zu viel Parameter angegeben", chatList.get(1));
    }

    @Test
    public void testMinMaxRangeCommand() throws CommandException {
        commandsManager.add(TestValidCommandMinMaxRange.class);

        final ArrayList<String> chatList = new ArrayList<String>();
        TestPlayer player = new TestPlayer() {
            @Override
            public void sendMessage(String message) {
                chatList.add(message);
            }
        };

        executeComannd("/test", player);
        Assert.assertEquals(1, chatList.size());
        executeComannd("/test 5", player);
        Assert.assertEquals(1, chatList.size());
        executeComannd("/test 5 1", player);
        Assert.assertEquals(1, chatList.size());
        executeComannd("/test 5 1 4", player);
        Assert.assertEquals(2, chatList.size());

        Assert.assertEquals(1, testValid.size());
        Assert.assertTrue(testValid.containsKey("testMinMaxRangeCommand"));
        Assert.assertEquals(new Short((short) 2), testValid.get("testMinMaxRangeCommand"));

        Assert.assertEquals(2, chatList.size());
        Assert.assertEquals(ChatColor.RED + "Mindest Anzahl an Parameter nicht angegeben", chatList.get(0));
        Assert.assertEquals(ChatColor.RED + "Zu viel Parameter angegeben", chatList.get(1));
    }
}
