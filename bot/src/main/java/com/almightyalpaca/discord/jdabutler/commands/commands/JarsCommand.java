package com.almightyalpaca.discord.jdabutler.commands.commands;

import com.almightyalpaca.discord.jdabutler.Bot;
import com.almightyalpaca.discord.jdabutler.commands.Command;
import com.almightyalpaca.discord.jdabutler.util.EmbedUtil;
import com.kantenkugel.discordbot.jenkinsutil.JenkinsApi;
import com.kantenkugel.discordbot.jenkinsutil.JenkinsBuild;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;

public class JarsCommand extends Command
{
    private static final String[] ALIASES = new String[]
    { "jar" };

    @Override
    public void dispatch(final User sender, final TextChannel channel, final Message message, final String content, final GuildMessageReceivedEvent event)
    {

        final EmbedBuilder eb = new EmbedBuilder();
        EmbedUtil.setColor(eb);
        eb.setAuthor("Latest Pterodactyl4J jars", null, EmbedUtil.getJDAIconUrl());
        eb.setTitle(EmbedBuilder.ZERO_WIDTH_SPACE, null);

        try
        {
            JenkinsBuild lastBuild = JenkinsApi.P4J_JENKINS.getLastSuccessfulBuild();
            if(lastBuild == null)
            {
                reply(event, "Could not get Artifact-data from CI!");
                return;
            }

            eb.addField("jar", "[download](" + lastBuild.artifacts.get("Pterodactyl4J").getLink() + ")", true);
            eb.addField("javadoc", "[download](" + lastBuild.artifacts.get("Pterodactyl4J-javadoc").getLink() + ")", true);
            eb.addField("sources", "[download](" + lastBuild.artifacts.get("Pterodactyl4J-sources").getLink() + ")", true);
//            eb.addField("withDependencies", "[(normal)](" + lastBuild.artifacts.get("JDA-withDependencies").getLink() + ") " +
//                    "[(no-opus)](" + lastBuild.artifacts.get("JDA-withDependencies-no-opus").getLink() + ")", true);

            reply(event, eb.build());
        }
        catch(IOException ex)
        {
            Bot.LOG.warn("Failed fetching latest build from Jenkins for Jars command", ex);
            reply(event, "CI was unreachable!");
        }
    }

    @Override
    public String[] getAliases()
    {
        return JarsCommand.ALIASES;
    }

    @Override
    public String getHelp()
    {
        return "Displays links to all JAR files";
    }

    @Override
    public String getName()
    {
        return "jars";
    }
}
