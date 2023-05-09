package by.webproj.carshowroom.command;

public interface ServiceLocator {
    /**
     *
     * @param commandName
     * @return command by name
     */
    Command getCommand(String commandName);
}
