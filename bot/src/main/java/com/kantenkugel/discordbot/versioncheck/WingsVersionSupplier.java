package com.kantenkugel.discordbot.versioncheck;

import com.kantenkugel.discordbot.pterodactylutil.PterodactylApi;
import com.kantenkugel.discordbot.versioncheck.items.VersionedItem;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Supplier;

/**
 * Simple implementation aimed to be served to {@link VersionedItem#getCustomVersionSupplier()}
 * This uses a given Pterodactyl CDN to get the version.
 */
public class WingsVersionSupplier implements Supplier<String>
{
    private final PterodactylApi pterodactylApi;

    public WingsVersionSupplier(PterodactylApi pterodactylApi)
    {
        this.pterodactylApi = pterodactylApi;
    }


    @Override
    public String get()
    {
        try
        {
            return pterodactylApi.fetchLatestBuild().wingsVersion;
        }
        catch(IOException ex)
        {
            throw new UncheckedIOException(ex);
        }
    }
}
