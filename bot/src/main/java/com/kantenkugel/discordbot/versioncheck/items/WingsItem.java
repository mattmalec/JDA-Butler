package com.kantenkugel.discordbot.versioncheck.items;

import com.kantenkugel.discordbot.versioncheck.GithubVersionSupplier;
import com.kantenkugel.discordbot.versioncheck.RepoType;

import java.util.function.Supplier;

public class WingsItem extends VersionedItem
{
    private static final GithubVersionSupplier versionSupplier = new GithubVersionSupplier("pterodactyl", "wings");

    @Override
    public String getName()
    {
        return "Wings";
    }

    @Override
    public String getDescription() {
        return "Updates for Pterodactyl Panel";
    }

    @Override
    public RepoType getRepoType()
    {
        return null;
    }

    @Override
    public String getGroupId()
    {
        return null;
    }

    @Override
    public String getArtifactId()
    {
        return null;
    }

    @Override
    public String getUrl()
    {
        return getVersion() == null ? null : "https://github.com/pterodactyl/wings/releases/tag/" + getVersion();
    }

    @Override
    public Supplier<String> getCustomVersionSupplier()
    {
        return versionSupplier;
    }

}
