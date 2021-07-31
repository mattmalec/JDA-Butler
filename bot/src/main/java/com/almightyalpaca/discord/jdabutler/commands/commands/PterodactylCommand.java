package com.almightyalpaca.discord.jdabutler.commands.commands;

import com.almightyalpaca.discord.jdabutler.Bot;
import com.almightyalpaca.discord.jdabutler.commands.Command;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PterodactylCommand extends Command
{

    private static final String[] ALIASES = new String[]
    { "panel" };

    @Override
    public void dispatch(final User sender, final TextChannel channel, final Message message, final String content, final GuildMessageReceivedEvent event)
    {
        reply(event, Bot.PTERODACTYL_INVITE_LINK);
    }

    @Override
    public String[] getAliases()
    {
        return PterodactylCommand.ALIASES;
    }

    @Override
    public String getHelp()
    {
        return "shows the invite link for the pterodactyl guild";
    }

    @Override
    public String getName()
    {
        return "pterodactyl";
    }
}
