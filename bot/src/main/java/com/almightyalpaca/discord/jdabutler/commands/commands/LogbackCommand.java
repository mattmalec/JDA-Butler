package com.almightyalpaca.discord.jdabutler.commands.commands;

import com.almightyalpaca.discord.jdabutler.commands.Command;
import com.almightyalpaca.discord.jdabutler.util.LogbackUtil;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class LogbackCommand extends Command
{

    @Override
    public void dispatch(final User sender, final TextChannel channel, final Message message, final String content, final GuildMessageReceivedEvent event)
    {
        final MessageBuilder mb = new MessageBuilder()
                .appendCodeBlock(LogbackUtil.getLogback(), "xml");
        reply(event, mb.build());
    }

    @Override
    public String getHelp()
    {
        return "Shows an example logback.xml file";
    }

    @Override
    public String getName()
    {
        return "logback.xml";
    }

}
