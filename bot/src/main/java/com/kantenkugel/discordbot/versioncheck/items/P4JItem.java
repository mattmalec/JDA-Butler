package com.kantenkugel.discordbot.versioncheck.items;

import com.almightyalpaca.discord.jdabutler.Bot;
import com.almightyalpaca.discord.jdabutler.util.EmbedUtil;
import com.almightyalpaca.discord.jdabutler.util.FormattingUtil;
import com.almightyalpaca.discord.jdabutler.util.MiscUtils;
import com.almightyalpaca.discord.jdabutler.util.gradle.GradleProjectDropboxUtil;
import com.kantenkugel.discordbot.jdocparser.JDoc;
import com.kantenkugel.discordbot.jenkinsutil.JenkinsApi;
import com.kantenkugel.discordbot.jenkinsutil.JenkinsBuild;
import com.kantenkugel.discordbot.versioncheck.JenkinsVersionSupplier;
import com.kantenkugel.discordbot.versioncheck.RepoType;
import com.kantenkugel.discordbot.versioncheck.UpdateHandler;
import com.kantenkugel.discordbot.versioncheck.changelog.ChangelogProvider;
import com.kantenkugel.discordbot.versioncheck.changelog.JenkinsChangelogProvider;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

public class P4JItem extends VersionedItem implements UpdateHandler
{
    private final ChangelogProvider changelogProvider;
    private final Supplier<String> versionSupplier;
    private final long roleId;
    private final long channelId;
    private final String job;
    private final JenkinsApi jenkins;

    private static final List<String> aliases = Arrays.asList("p4j");

    public P4JItem()
    {
        this(JenkinsApi.P4J_JENKINS, 781255837183639573L, 780230961035608067L, "Pterodactyl4J");
    }

    public P4JItem(JenkinsApi api, long roleId, long channelId, String job)
    {
        this.roleId = roleId;
        this.channelId = channelId;
        this.job = job;
        this.changelogProvider = new JenkinsChangelogProvider(api, "https://github.com/mattmalec/Pterodactyl4J/");
        this.versionSupplier = new JenkinsVersionSupplier(api);
        this.jenkins = api;
    }

    @Override
    public Supplier<String> getCustomVersionSupplier() {
        return versionSupplier;
    }

    @Override
    public String getName()
    {
        return job;
    }

    @Override
    public String getDescription() {
        return "Updates for every " + getName() + " development build";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public RepoType getRepoType()
    {
        return RepoType.REPO_MATTMALEC;
    }

    @Override
    public Set<RepoType> getAdditionalRepositories() {
        return EnumSet.of(RepoType.MAVENCENTRAL);
    }

    @Override
    public String getGroupId()
    {
        return "com.mattmalec";
    }

    @Override
    public String getArtifactId()
    {
        return "Pterodactyl4J";
    }

    @Override
    public String getUrl()
    {
        return jenkins.getLastSuccessfulBuildUrl();
    }

    @Override
    public long getAnnouncementRoleId()
    {
        return roleId;
    }

    @Override
    public long getAnnouncementChannelId()
    {
        return channelId;
    }

    @Override
    public UpdateHandler getUpdateHandler()
    {
        return this;
    }

    @Override
    public ChangelogProvider getChangelogProvider()
    {
        return changelogProvider;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onUpdate(VersionedItem item, String previousVersion, boolean shouldAnnounce)
    {
        boolean isActualJDA = getName().equalsIgnoreCase("pterodactyl4j");
        String version = item.getVersion();
        int buildNumber = Integer.parseInt(version.substring(version.indexOf("_") + 1));
        if (!isActualJDA || buildNumber != Bot.config.getInt("p4j.version.build", -1))
        {
            Bot.LOG.debug("Update found!");

            if(isActualJDA)
            {
                Bot.config.put("p4j.version.build", buildNumber);
                Bot.config.put("p4j.version.name", version);

                Bot.config.save();
            }

            JenkinsBuild jenkinsBuild;

            try
            {
                jenkinsBuild = jenkins.fetchLastSuccessfulBuild();
            }
            catch(IOException ex)
            {
                Bot.LOG.warn("Could not fetch latest Jenkins build in P4JItem#onUpdate()", ex);
                return;
            }

            if(jenkinsBuild == null)
            {
                Bot.LOG.warn("Could not fetch Jenkins-build for new version (triggered by maven update)");
                return;
            }

            if(isActualJDA)
            {
                //                    GradleProjectDropboxUtil.uploadProject();
                Bot.EXECUTOR.submit(JDoc::reFetch);
            }

            if(!shouldAnnounce)
                return;

            final EmbedBuilder eb = new EmbedBuilder();

            final MessageBuilder mb = new MessageBuilder();

            FormattingUtil.setFooter(eb, jenkinsBuild.culprits, jenkinsBuild.buildTime);

            Role announcementRole = getAnnouncementRole();

            mb.append(announcementRole.getAsMention());

            eb.setAuthor("P4J version " + version + " has been released\n", jenkins.jenkinsBase + buildNumber, EmbedUtil.getJDAIconUrl());

            EmbedUtil.setColor(eb);

            if (jenkinsBuild.changes.size() > 0)
            {

                eb.setTitle(EmbedBuilder.ZERO_WIDTH_SPACE, null);
                ChangelogProvider.Changelog changelog = getChangelogProvider().getChangelog(Integer.toString(jenkinsBuild.buildNum));
                List<String> changeset = changelog.getChangeset();

                int fields;

                if (changeset.size() > 25)
                    fields = 24;
                else
                    fields = Math.min(changeset.size(), 25);

                for (int j = 0; j < fields; j++)
                {
                    final String field = changeset.get(j);
                    eb.addField(j == 0 ? "Commits:" : "", field, false);
                }

                if (changeset.size() > 25)
                    eb.addField("", "max embed length reached", false);

            }

            final MessageEmbed embed = eb.build();

            mb.setEmbeds(embed);

            final TextChannel channel = getAnnouncementChannel();

            MiscUtils.announce(channel, announcementRole, mb.build(), false);
        }
    }
}
