package net.huskycraft.huskyarena.Commands;

import net.huskycraft.huskyarena.HuskyArena;
import net.huskycraft.huskyarena.Session;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class QuitCmd implements CommandExecutor{

    HuskyArena plugin;

    public QuitCmd(HuskyArena plugin) {
        this.plugin = plugin;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = (Player) src;
        Session session = plugin.getSessionManager().playerSession.get(player);
        session.remove(player);

        return CommandResult.success();
    }
}