package neesh.gameoflife.GameOfLife;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Embeds {
	
	/*
	 * What is an embed?
	 * An embed is a message that only Discord bots can send, that looks nice and organized: https://gblobscdn.gitbook.com/assets%2F-LAEeOAJ8-CJPfZkGKqI%2F-Lh-d6Qc42Rq3BmspE9l%2F-LAEmPBF47FJgnfBD21P%2Fembedexample2.png?alt=media
	 * 
	 * Fields of an embed:
	 * A title: This is the largest text, and represents what the embed is for
	 * A description: This is smaller text that appears below the title, and is what I use to actually display the grid.
	 * A field: A field has a title and a value, which I use to say who asked for the embed, and also describe the commands in the bot in the help embed.
	 * A color: All embeds have a strip of color along the left side, which I mostly set to pink.
	 */
    public static void sendGameEmbed(MessageChannel channel, Game game, User user) { //The Embed that sends the actual game of a user
    	String grid = Game.getGridString();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Game of Life");	
        embed.setDescription(grid);
        embed.addField("Type r to refresh the grid, s to stop the game, or n to go to the next generation.	", "", false);  //The boolean at the end of the addField methods represent whether or not they will be inline.
        embed.addField("You can also use :arrow_forward: for the next generation, :repeat: to refresh the grid, and :stop_button: to end the game.", "", false);
        GameUtil.getGame(user);
		embed.addField("Generation", String.valueOf(Game.getGen()), false);
        embed.addField("Player", user.getAsMention(), false);  //This getAsMention thing just pings the person that sent the command for the embed
        
        embed.setColor(Color.magenta);
        channel.sendMessage(embed.build()).queue(message ->{  //I use a method inside of the queue method, which allows me to do things directly after the message sends. I can't do this normally because it's an async command.
        	message.addReaction("U+25B6").queue();  //Play button
        	message.addReaction("U+23F9").queue();  //Refresh button
        	message.addReaction("U+1F501").queue(); //Stop button
        	Game.setLast(message);  //Sets the last message in a game, which lets me do things like edit them.
        });
    }
    public static void sendInfoEmbed(MessageChannel channel, User user) { //An embed that sends during the help command
    	String prefix = GameOfLife.prefix;
    	EmbedBuilder embed = new EmbedBuilder();
    	embed.setTitle("Game of Life");
    	embed.setDescription("[] = required, <> = optional");
    	embed.addField("`" + prefix + "start`", "Starts a game if you don't already have one.", false);
    	embed.addField("`" + prefix + "stop`", "Stops an active game if you have one.", false);
    	embed.addField("`" + prefix + "ping`", "Pong!", false);
    	embed.addField("`" + prefix + "size [size]`", "Changes the size of the grid.", false);
    	embed.addField("`" + prefix + "setprefix [prefix]`", "Changes the prefix of the bot.", false);
    	embed.addField("My prefix: ", GameOfLife.prefix, false);
    	embed.addField("Player", user.getAsMention(), false);
    	embed.setColor(Color.magenta);
    	channel.sendMessage(embed.build()).queue();
    }
    public static void sendEndEmbed(MessageChannel channel, User user) {  //An embed that signals the end of a game
		EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Game of Life");
        embed.setDescription("The game has ended.");
        embed.addField("Player", user.getAsMention(), false);
        embed.setColor(Color.red);
        channel.sendMessage(embed.build()).queue();
    }
    public static void sendGameRepeatEmbed(MessageChannel channel, User user) {  //An embed that is sent when a player with a game tries to make another
		EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Game of Life");
        embed.setDescription("You already have a game!");
        embed.addField("Player", user.getAsMention(), false);
        embed.setColor(Color.magenta);
        channel.sendMessage(embed.build()).queue();
    }
    public static void sendNoGameEmbed(MessageChannel channel, User user) {  //An embed that is sent when a player without a game tries to stop a game
		EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Game of Life");
        embed.setDescription("You don't have a game!");
        embed.addField("Player", user.getAsMention(), false);
        embed.setColor(Color.magenta);
        channel.sendMessage(embed.build()).queue();
    }
    public static void sendDeadGameEmbed(MessageChannel channel, User user) {  //An embed that is sent when a game is completely dead.
    	EmbedBuilder embed = new EmbedBuilder();
    	embed.setTitle("Your game died!");
    	embed.setDescription("All of the cells in your grid died :(");
    	embed.addField("Player", user.getAsMention(), false);
    	embed.setColor(Color.magenta);
    	channel.sendMessage(embed.build()).queue();
    }
    public static void sendErrorEmbed(MessageChannel channel, User user) {  //An embed that is sent when there is an error and a command doesn't have the correct arguments
    	EmbedBuilder embed = new EmbedBuilder();
    	embed.setTitle("There was an error");
    	embed.setDescription("Please make sure you're using the right arguments");
    	embed.addField("Player", user.getAsMention(), false);
    	embed.setColor(Color.magenta);
    	channel.sendMessage(embed.build()).queue();
    }
    public static void sendNoPermissionsEmbed(MessageChannel channel, User user) {  //An embed that is sent when a user doesn't have the permission to set a new prefix or size.
    	EmbedBuilder embed = new EmbedBuilder();
    	embed.setTitle("There was an error");
    	embed.setDescription("You don't have the permissions for that.");
    	embed.addField("Player", user.getAsMention(), false);
    	embed.setColor(Color.magenta);
    	channel.sendMessage(embed.build()).queue();
    }
    public static void sendSuccessSizeEmbed(MessageChannel channel, User user, int size) { //An embed that is sent when a size is successfully changed
    	EmbedBuilder embed = new EmbedBuilder();
    	embed.setTitle("Success!");
    	embed.setDescription("I successfully set the new size to " + size);
    	embed.addField("Player", user.getAsMention(), false);
    	embed.setColor(Color.magenta);
    	channel.sendMessage(embed.build()).queue();
    }
}
