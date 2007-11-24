package org.apache.wicket.teatime.logbot;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.text.SimpleDateFormat;
import org.jibble.pircbot.*;

public class LogBot extends PircBot {

    private static final Pattern urlPattern = Pattern.compile("(?i:\\b((http|https|ftp|irc)://[^\\s]+))");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("H:mm");

    public static final String GREEN = "irc-green";
    public static final String BLACK = "irc-black";
    public static final String BROWN = "irc-brown";
    public static final String NAVY = "irc-navy";
    public static final String BRICK = "irc-brick";
    public static final String RED = "irc-red";

    public LogBot(String name, File outDir, String joinMessage) {
        setName(name);
        setVerbose(false);
        this.outDir = outDir;
        this.joinMessage = joinMessage;
    }

    public void append(String color, String line) {
        line = Colors.removeFormattingAndColors(line);

        line = line.replaceAll("&", "&amp;");
        line = line.replaceAll("<", "&lt;");
        line = line.replaceAll(">", "&gt;");

        Matcher matcher = urlPattern.matcher(line);
        line = matcher.replaceAll("<a href=\"$1\">$1</a>");


        try {
            Date now = new Date();
            String date = DATE_FORMAT.format(now);
            String time = TIME_FORMAT.format(now);
            File file = new File(outDir, date + ".log");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            String entry = "<span class=\"irc-date\">[" + time + "]</span> <span class=\"" + color + "\">" + line + "</span><br />";
            writer.write(entry);
            writer.newLine();
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Could not write to log: " + e);
        }
    }

    public void onAction(String sender, String login, String hostname, String target, String action) {
        append(BRICK, "* " + sender + " " + action);
    }

    public void onJoin(String channel, String sender, String login, String hostname) {
        append(GREEN, "* " + sender + " (" + login + "@" + hostname + ") has joined " + channel);
        if (sender.equals(getNick())) {
            sendNotice(channel, joinMessage);
        }
        else {
            sendNotice(sender, joinMessage);
        }
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        append(BLACK, "<" + sender + "> " + message);

        message = message.toLowerCase();
        if (message.startsWith(getNick().toLowerCase()) && message.indexOf("help") > 0) {
            sendMessage(channel, joinMessage);
        }
    }

    public void onMode(String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
        append(GREEN, "* " + sourceNick + " sets mode " + mode);
    }

    public void onNickChange(String oldNick, String login, String hostname, String newNick) {
        append(GREEN, "* " + oldNick + " is now known as " + newNick);
    }

    public void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
        append(BROWN, "-" + sourceNick + "- " + notice);
    }

    public void onPart(String channel, String sender, String login, String hostname) {
        append(GREEN, "* " + sender + " (" + login + "@" + hostname + ") has left " + channel);
    }

    public void onPing(String sourceNick, String sourceLogin, String sourceHostname, String target, String pingValue) {
        append(RED, "[" + sourceNick + " PING]");
    }

    public void onPrivateMessage(String sender, String login, String hostname, String message) {
         append(BLACK, "<- *" + sender + "* " + message);
    }

    public void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) {
        append(NAVY, "* " + sourceNick + " (" + sourceLogin + "@" + sourceHostname + ") Quit (" + reason + ")");
    }

    public void onTime(String sourceNick, String sourceLogin, String sourceHostname, String target) {
        append(RED, "[" + sourceNick + " TIME]");
    }

    public void onTopic(String channel, String topic, String setBy, long date, boolean changed) {
        if (changed) {
            append(GREEN, "* " + setBy + " changes topic to '" + topic + "'");
        }
        else {
            append(GREEN, "* Topic is '" + topic + "'");
            append(GREEN, "* Set by " + setBy + " on " + new Date(date));
        }
    }

    public void onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target) {
        append(RED, "[" + sourceNick + " VERSION]");
    }

    public void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
        append(GREEN, "* " + recipientNick + " was kicked from " + channel + " by " + kickerNick);
        if (recipientNick.equalsIgnoreCase(getNick())) {
            joinChannel(channel);
        }
    }

    public void onDisconnect() {
        append(NAVY, "* Disconnected.");
        while (!isConnected()) {
            try {
                reconnect();
            }
            catch (Exception e) {
                try {
                    Thread.sleep(10000);
                }
                catch (Exception anye) {
                    // Do nothing.
                }
            }
        }
    }

    public static void copy(File source, File target) throws IOException {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(target));
        int bytesRead = 0;
        byte[] buffer = new byte[1024];
        while ((bytesRead = input.read(buffer, 0, buffer.length)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        output.flush();
        output.close();
        input.close();
    }

    private File outDir;
    private String joinMessage;
}