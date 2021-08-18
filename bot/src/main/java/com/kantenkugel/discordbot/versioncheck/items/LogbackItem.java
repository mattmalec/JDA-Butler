package com.kantenkugel.discordbot.versioncheck.items;

import com.kantenkugel.discordbot.versioncheck.RepoType;

public class LogbackItem extends VersionedItem
{

    public LogbackItem() {
        setVersion("1.2.5");
    }

    @Override
    public String getName()
    {
        return "Logback";
    }

    @Override
    public String getDescription() {
        return "Updates for Logback Classic";
    }

    @Override
    public RepoType getRepoType()
    {
        return RepoType.MAVENCENTRAL;
    }

    @Override
    public String getGroupId()
    {
        return "ch.qos.logback";
    }

    @Override
    public String getArtifactId()
    {
        return "logback-classic";
    }

}
