package com.almightyalpaca.discord.jdabutler.commands.commands;

import com.almightyalpaca.discord.jdabutler.commands.Command;
import com.almightyalpaca.discord.jdabutler.util.EmbedUtil;
import com.kantenkugel.discordbot.versioncheck.VersionCheckerRegistry;
import com.kantenkugel.discordbot.versioncheck.items.VersionedItem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class VersionsCommand extends Command
{

    private static final String[] ALIASES = { "version", "latest" };
    private static final String DEFAULT_ITEMS = "pterodactyl4j panel wings";

    @Override
    public void dispatch(final User sender, final TextChannel channel, final Message message, final String content, final GuildMessageReceivedEvent event)
    {
        final EmbedBuilder eb = new EmbedBuilder();

        EmbedUtil.setColor(eb);

        List<VersionedItem> items;
        if(content.trim().isEmpty())
            items = VersionCheckerRegistry.getItemsFromString(DEFAULT_ITEMS, false);
        else
            items = VersionCheckerRegistry.getItemsFromString(content, false);

        if(items.isEmpty())
        {
            eb.setAuthor("Latest version", null, EmbedUtil.getJDAIconUrl());
            eb.setTitle("No item(s) found for input", null);
        }
        else
        {
            if(items.size() == 1)
                eb.setAuthor("Latest version for "+items.get(0).getName(), null, EmbedUtil.getJDAIconUrl());
            else
                eb.setAuthor("Latest versions", null, EmbedUtil.getJDAIconUrl());

            for (VersionedItem versionedItem : items)
            {
                if (versionedItem.getUrl() != null)
                    eb.addField(versionedItem.getName(), String.format("[%s](%s)", versionedItem.getVersion(), versionedItem.getUrl()), true);
                else
                    eb.addField(versionedItem.getName(), versionedItem.getVersion(), true);
            }
        }

        reply(event, eb.build());
    }

    @Override
    public String[] getAliases()
    {
        return VersionsCommand.ALIASES;
    }

    @Override
    public String getHelp()
    {
        return "Prints versions of all the things that matter :D";
    }

    @Override
    public String getName()
    {
        return "versions";
    }
}
