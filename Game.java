import java.util.Scanner;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room island1, island2, island3, island4,
             island5, origIsland, ocean1, ocean2, 
             ocean3, ocean4, ocean5, ocean6, ocean7, 
             ocean8, ocean9, ocean10, ocean11, 
             ocean12, ocean13, ocean14, ocean15, ocean16,
             ocean17, ocean18, ocean19, secretCave;
      
        // create the rooms
        origIsland = new Room("On stranded island");
        island1 = new Room("on island 1");
        island2 = new Room("on island 2");
        island3 = new Room("on island 3");
        island4 = new Room("on island 4");
        island5 = new Room("on island 5, finds a bear");
        secretCave = new Room("secret cave under the surface of the island");
        ocean1 = new Room("open ocean");
        ocean2 = new Room("open ocean");
        ocean3 = new Room("open ocean");
        ocean4 = new Room("open ocean");
        ocean5 = new Room("open ocean");
        ocean6 = new Room("open ocean");
        ocean7 = new Room("open ocean");
        ocean8 = new Room("open ocean");
        ocean9 = new Room("open ocean");
        ocean10 = new Room("open ocean");
        ocean11 = new Room("open ocean");
        ocean12 = new Room("open ocean");
        ocean13 = new Room("open ocean");
        ocean14 = new Room("open ocean");
        ocean15 = new Room("open ocean");
        ocean16 = new Room("open ocean");
        ocean17 = new Room("open ocean");
        ocean18 = new Room("open ocean");
        ocean19 = new Room("open ocean");

        
        // initialise room exits (north, east, south, west)
        origIsland.setExits(ocean6, ocean10, ocean14, ocean9, null, null);
        island1.setExits(ocean2, ocean6, ocean9, ocean5, null, null);
        island2.setExits(ocean5, ocean9, ocean12, null, null, null);
        island3.setExits(ocean13, ocean18, null, ocean17, null, null);
        island4.setExits(null, null, ocean8, ocean4, null, null);
        island5.setExits(ocean16, null, null, ocean19, null, secretCave);
        
        secretCave.setExits(null, null, null, null, island5, null);

        ocean1.setExits(null, ocean2, ocean5, null, null, null);
        ocean2.setExits(null, ocean3, island1, ocean1, null, null);
        ocean3.setExits(null, ocean4, ocean6, ocean2, null, null);
        ocean4.setExits(null, island4, ocean7, ocean3, null, null);
        ocean5.setExits(ocean1, island1, island2, null, null, null);
        ocean6.setExits(ocean3, ocean7, origIsland, island1, null, null);
        ocean7.setExits(ocean4, ocean8, ocean10, ocean6, null, null);
        ocean8.setExits(island4, null, ocean11, ocean7, null, null);
        ocean9.setExits(island1, origIsland, ocean13, island2, null, null);
        ocean10.setExits(ocean7, ocean11, ocean15, origIsland, null, null);
        ocean11.setExits(ocean8, null, ocean16, ocean10, null, null);
        ocean12.setExits(island2, ocean13, ocean17, null, null, null);
        ocean13.setExits(ocean9, ocean14, island3, ocean12, null, null);
        ocean14.setExits(origIsland, ocean15, ocean18, ocean13, null, null);
        ocean15.setExits(ocean10, ocean16, ocean19, ocean14, null, null);
        ocean16.setExits(ocean11, null, island5, ocean15, null, null);
        ocean17.setExits(ocean12, island3, null, null, null, null);
        ocean18.setExits(ocean14, ocean19, null, island3, null, null);
        ocean19.setExits(ocean15, island5, null, ocean18, null, null);

        currentRoom = origIsland;  // start game outside

        island1.addItem(new Item("sticks", "some sticks on the ground"));
        island2.addItem(new Item("rocks", "some rocks on the ground"));
        island3.addItem(new Item("plastic bottles", "some plastic bottles along the shore"));
        island4.addItem(new Item("fresh water", "a bottle of fresh water"));
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
    }

    private void printLocationInfo() {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("You can go: ");
        System.out.print(currentRoom.getExitString());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are stranded. You are alone. You wander");
        System.out.println("around at the island.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Edge of the world!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
        }

        Item designatedItem;
        if (currentRoom.getDescription().equalsIgnoreCase("on island 1")) {
            String designatedItemName = "sticks";
            designatedItem = currentRoom.getItem(designatedItemName);
        }
        if (currentRoom.getDescription().equalsIgnoreCase("on island 2")) {
            String designatedItemName = "rocks";
            designatedItem = currentRoom.getItem(designatedItemName);
        }
        if (currentRoom.getDescription().equalsIgnoreCase("on island 3")) {
            String designatedItemName = "plastic bottles";
            designatedItem = currentRoom.getItem(designatedItemName);
        }
        if (currentRoom.getDescription().equalsIgnoreCase("on island 4")) {
            String designatedItemName = "fresh water";
            designatedItem = currentRoom.getItem(designatedItemName);
        }

        System.out.println(currentRoom.getItemList());
        
        if (currentRoom.getDescription().equalsIgnoreCase("secret cave under the surface of the island")) {
            System.out.println("Congratulations! You have reached the secret cave.");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}