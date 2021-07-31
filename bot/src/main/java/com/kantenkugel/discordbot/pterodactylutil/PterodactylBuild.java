package com.kantenkugel.discordbot.pterodactylutil;

import org.json.JSONObject;

public class PterodactylBuild
{
    public final String panelVersion;
    public final String wingsVersion;
    private PterodactylBuild(String panelVersion, String wingsVersion)
    {
        this.panelVersion = panelVersion;
        this.wingsVersion = wingsVersion;
    }

    static PterodactylBuild fromJson(JSONObject json)
    {
        String panelVersion = json.getString("panel");
        String wingsVersion = json.getString("wings");

        return new PterodactylBuild(panelVersion, wingsVersion);
    }

}
