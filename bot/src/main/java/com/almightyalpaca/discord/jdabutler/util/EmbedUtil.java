package com.almightyalpaca.discord.jdabutler.util;

import com.almightyalpaca.discord.jdabutler.Bot;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.Color;

public class EmbedUtil
{

    public static final Color COLOR_P4J_BLUE = Color.decode("#134C8E");

    public static String getJDAIconUrl()
    {
        return Bot.getGuildP4J().getIconUrl();
    }

    public static void setColor(final EmbedBuilder builder)
    {
        builder.setColor(EmbedUtil.COLOR_P4J_BLUE);
    }

}
