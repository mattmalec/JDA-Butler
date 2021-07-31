package com.kantenkugel.discordbot.pterodactylutil;

import com.almightyalpaca.discord.jdabutler.Bot;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class PterodactylApi
{
    public static final String PTERODACTYL_API_BASE = "https://cdn.pterodactyl.io/releases/latest.json";

    public PterodactylBuild fetchLatestBuild() throws IOException {
        Request req = new Request.Builder().url(PTERODACTYL_API_BASE).get().build();

        try(Response res = Bot.httpClient.newCall(req).execute())
        {
            if(!res.isSuccessful())
                return null;
            return PterodactylBuild.fromJson(new JSONObject(new JSONTokener(res.body().charStream())));
        }
    }

    public static PterodactylApi getInstance() {
        return new PterodactylApi();
    }
}
