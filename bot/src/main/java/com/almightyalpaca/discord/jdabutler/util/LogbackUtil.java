package com.almightyalpaca.discord.jdabutler.util;

public class LogbackUtil {

    public static String getLogback() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<configuration>\n" +
                "    <appender name=\"STDOUT\" class=\"ch.qos.logback.core.ConsoleAppender\">\n" +
                "        <encoder>\n" +
                "            <pattern>%d{HH:mm:ss.SSS} %boldCyan(%-21.-25thread) %boldGreen(%-12.-12logger{0}) %highlight(%-6level) %msg%n</pattern>\n" +
                "        </encoder>\n" +
                "    </appender>\n" +
                "\n" +
                "    <root level=\"trace\">\n" +
                "        <appender-ref ref=\"STDOUT\" />\n" +
                "    </root>\n" +
                "</configuration>";
    }
}
