package de.cubenation.plugins.utils.commandapi;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import de.cubenation.plugins.utils.commandapi.exception.CommandException;
import de.cubenation.plugins.utils.commandapi.testutils.AbstractTest;
import de.cubenation.plugins.utils.commandapi.testutils.TestConsole;
import de.cubenation.plugins.utils.commandapi.testutils.TestPlayer;
import de.cubenation.plugins.utils.commandapi.testutils.testcommands.TestValidCommandConsole;
import de.cubenation.plugins.utils.commandapi.testutils.testcommands.TestValidCommandMain;

public class CommandSenderTest extends AbstractTest {
    @Test
    public void testConsoleCommand() throws CommandException {
        commandsManager.add(TestValidCommandConsole.class);

        executeComannd("/test", new TestConsole());

        Assert.assertEquals(1, testValid.size());
        Assert.assertTrue(testValid.containsKey("testConsoleCommand"));
        Assert.assertEquals(new Short((short) 1), testValid.get("testConsoleCommand"));
    }

    @Test
    public void testPlayerCommand() throws CommandException {
        commandsManager.add(TestValidCommandMain.class);

        executeComannd("/test", new TestPlayer());

        Assert.assertEquals(1, testValid.size());
        Assert.assertTrue(testValid.containsKey("testOneMainCommand"));
        Assert.assertEquals(new Short((short) 1), testValid.get("testOneMainCommand"));
    }

    @Test
    public void testNotConsoleCommand() throws CommandException {
        commandsManager.add(TestValidCommandMain.class);

        final ArrayList<String> chatList = new ArrayList<String>();
        executeComannd("/test", new TestConsole() {
            @Override
            public void sendMessage(String message) {
                chatList.add(message);
            }
        });

        Assert.assertEquals(0, testValid.size());
        Assert.assertEquals(1, chatList.size());
        Assert.assertEquals("Befehl nicht gefunden. Versuche /test help", chatList.get(0));
    }

    @Test
    public void testNotPlayerCommand() throws CommandException {
        commandsManager.add(TestValidCommandConsole.class);

        final ArrayList<String> chatList = new ArrayList<String>();
        executeComannd("/test", new TestPlayer() {
            @Override
            public void sendMessage(String message) {
                chatList.add(message);
            }
        });

        Assert.assertEquals(0, testValid.size());
        Assert.assertEquals(1, chatList.size());
        Assert.assertEquals("�cBefehl nicht gefunden. Versuche /test help", chatList.get(0));
    }
}
