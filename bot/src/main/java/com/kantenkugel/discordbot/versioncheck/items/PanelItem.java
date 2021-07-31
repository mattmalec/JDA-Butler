package com.kantenkugel.discordbot.versioncheck.items;

import com.kantenkugel.discordbot.versioncheck.GithubVersionSupplier;
import com.kantenkugel.discordbot.versioncheck.RepoType;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.LongStream;

public class PanelItem extends VersionedItem
{
    private static final GithubVersionSupplier versionSupplier = new GithubVersionSupplier("pterodactyl", "panel");

    @Override
    public String getName()
    {
        return "Panel";
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
        return getVersion() == null ? null : "https://github.com/pterodactyl/panel/releases/tag/" + getVersion();
    }

    @Override
    public Supplier<String> getCustomVersionSupplier()
    {
        return versionSupplier;
    }

}
