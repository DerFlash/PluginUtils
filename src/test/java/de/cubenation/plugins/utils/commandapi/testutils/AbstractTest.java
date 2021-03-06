package de.cubenation.plugins.utils.commandapi.testutils;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Before;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.cubenation.plugins.utils.chatapi.ChatService;
import de.cubenation.plugins.utils.chatapi.ColorParser;
import de.cubenation.plugins.utils.chatapi.UTF8Control;
import de.cubenation.plugins.utils.commandapi.CommandsManager;
import de.cubenation.plugins.utils.commandapi.exception.CommandException;
import de.cubenation.plugins.utils.commandapi.exception.CommandManagerException;
import de.cubenation.plugins.utils.commandapi.exception.CommandWarmUpException;

public abstract class AbstractTest {
    protected HashMap<String, Short> testValid = new HashMap<String, Short>();
    protected ArrayList<String> chatList = new ArrayList<String>();
    protected CommandsManager commandsManager;

    @Before
    public void setUp() throws CommandWarmUpException, CommandManagerException {
        if (Bukkit.getServer() == null) {
            Server server = de.cubenation.plugins.utils.testapi.AbstractTest.getServer();

            Bukkit.setServer(server);
        }

        final TestPlugin testPlugin = new TestPlugin() {
            @Override
            public void doSomeThing(String string) {
                if (testValid.containsKey(string)) {
                    testValid.put(string, new Short((short) (testValid.get(string) + 1)));
                } else {
                    testValid.put(string, new Short((short) 1));
                }
            }
        };

        ChatService chatService = new ChatService(testPlugin, null, "messages", new Locale("de")) {
            @Override
            public void one(CommandSender sender, String resourceString, Object... parameter) {
                ResourceBundle resource = ResourceBundle.getBundle("PluginUtils_messages", new Locale("de"), testPlugin.getClass().getClassLoader(),
                        new UTF8Control());

                String outputString = resource.getString(resourceString);

                outputString = ColorParser.replaceColor(outputString);

                int i = 0;
                for (Object param : parameter) {
                    outputString = outputString.replace("{" + i + "}", param.toString());
                    i++;
                }
                sender.sendMessage(outputString);
            }
        };

        CommandsManager.setOwnChatService(chatService);
        commandsManager = new CommandsManager(testPlugin);
    }

    protected void executeComannd(String args) throws CommandException {
        Player sender = mock(Player.class);

        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                chatList.add((String) args[0]);
                return null;
            }
        }).when(sender).sendMessage(anyString());

        executeComannd(commandsManager, args, sender);
    }

    protected void executeComannd(CommandsManager commandsManager, String args) throws CommandException {
        Player sender = mock(Player.class);

        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                chatList.add((String) args[0]);
                return null;
            }
        }).when(sender).sendMessage(anyString());

        executeComannd(commandsManager, args, sender);
    }

    protected void executeComannd(String args, CommandSender sender) throws CommandException {
        executeComannd(commandsManager, args, sender);
    }

    protected void executeComannd(CommandsManager commandsManager, String args, CommandSender sender) throws CommandException {
        args = args.substring(1);

        String[] split = args.split(" ");
        String commandLabel = split[0];
        String[] paramArgs = new String[split.length - 1];
        System.arraycopy(split, 1, paramArgs, 0, split.length - 1);

        commandsManager.execute(sender, new TestCommand(), commandLabel, paramArgs);
    }

    @After
    public void tearDown() {
        testValid.clear();
        chatList.clear();
    }
}
