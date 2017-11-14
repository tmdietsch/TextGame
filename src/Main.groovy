Npc lucy = new Npc(
        name:"Lucy the receptionist",
        isAggressive: false,
        description:"",
        alive: true,
)

Room room1 = new Room(
        roomid:1,
        title:"room 1",
        description:"This is the lounge. There is a TV showing the generic sports channel, but you are far too busy for that.",
        npc: lucy,
)

Npc vendor = new Npc(
        name:"Josh",
        isAggressive: false,
        description:"",
        alive: true,
)

Room room2 = new Room(
        roomid:2,
        title:"room 2",
        description:"You walk through the door, or maybe you teleported... I don't know but there are video games lining the walls if you are bored. Too bad there isn't an option to play them, in fact you can't even look at them. :(",
        npc: vendor,
)

Npc chef = new Npc(
        health: 40,
        name:"Chef Ticonderoga",
        isAggressive: true,
        multiplier: 3,
        description:"",
        alive: true,
)

Room room3 = new Room(
        roomid:3,
        title:"room 3",
        description:"Welcome to the kitchen, a normal kitchen, like the one at a stereotypical restaurant. Although you do smell something cheesy from Room 4. Also there is pizza on the counter but you are allergic to yeast.",
        npc: chef,
)

Npc stinkyCheese = new Npc(
        health: 70,
        name:"Stinky Cheese",
        isAggressive: true,
        multiplier: 5,
        description:"",
        alive: true,
)

Room room4 = new Room(
        roomid:4,
        title:"room 4",
        description:"You have arrived at the cheese room, filled with all sorts of cheese: Blue, Cheddar, American, Swiss, Camembert, Mozzarella, Roquefort, Manchego, Cotija, and so many more that you don't know the names to.",
        npc: stinkyCheese,
)

Npc lamp = new Npc(
        health: 30,
        name:"Lamp",
        isAggressive: true,
        multiplier: 4,
        description:"",
        alive: true,
)

Room room5 = new Room(
        roomid:5,
        title:"room 5",
        description:"There is a lamp, just standing there, in the middle of an empty room. Odd.",
        npc: lamp,
)

room1.connections = ["n":room2, "w":room5, "e":room3]
room2.connections = ["s":room1]
room3.connections = ["w":room1,"s":room4]
room4.connections = ["n":room3]
room5.connections = ["e":room1]

//Defining user input
BufferedReader bR = new BufferedReader(new InputStreamReader(System.in))

boolean difficulty = true
Integer number = 0
while (difficulty) {
    println "Choose your difficulty. [easy] [medium] [hard]"
    def userInput1 = bR.readLine()
    if (userInput1 == "easy") {
        number = 8
        difficulty = false
    } else if (userInput1 == "medium") {
        number = 5
        difficulty = false
    } else if (userInput1 == "hard") {
        number = 2
        difficulty = false
    } else {
        println "Please choose a difficulty."
    }
}

Integer health = 100 //This is the player's health
Room currentRoom = room1 //This is the current room that the player is in
Boolean keepPlaying = true //This variable controls whether the player keeps on playing or not
Scanner scanner = new Scanner(System.in) //Creates a scanner object, prompting the player to press a button to continue
Room previousRoom = room1 //Creating a variable for the room that the player was previously in

println()
println "To quit enter \'q\'"
println()
println "Welcome to " + currentRoom.title

while (keepPlaying) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
    println currentRoom.description
    if (health == 100) {
        println "You are at full health!"
    } else {
        println "Your health is " + health + " out of 100"
    }
        //Fighting loops till the monster or the player is dead
    if (!currentRoom.npc.isAggressive) {
        println currentRoom.npc.name + " says: " + currentRoom.npc.description
    } else if (currentRoom.npc.isAggressive && currentRoom.npc.alive) {
        println currentRoom.npc.name + " says: " + currentRoom.npc.description
        def keepFighting = true
        while (currentRoom.npc.alive && health > 0 && keepFighting) {
            Random rand = new Random() //Creates a random object
            int max = 10 //This is the maximum number for the random number generator
            def randomIntegerList = [] //This is a list of random numbers created below
            (1..10).each {
                randomIntegerList << rand.nextInt(max) + 1 //This creates a random number between 1 and 10
            }
            if (randomIntegerList[0] % 2 == 1) {
                //If the random number is odd, then the monster attacks
                health = health - (randomIntegerList[1]*currentRoom.npc.multiplier)
                if (health <= 0) {
                    println currentRoom.npc.name + " attacked"
                    println "You have been killed by " + currentRoom.npc.name
                    keepPlaying = false
                    keepFighting = false
                } else {
                    println currentRoom.npc.name + " attacked. Your health is now at " + health
                    print "Press the enter key to continue..."
                    scanner.nextLine()
                }
            } else if (randomIntegerList[0] % 2 == 0) {
                //If the random number is even, the player has a choice to either attack or flee
                print "Do you wish to attack[a] or flee[f]?: "
                def userInput = br.readLine()
                if (userInput == "a") {
                    //The player attacks
                    currentRoom.npc.health = currentRoom.npc.health - (randomIntegerList[1] * number)
                    println "You've inflicted ${randomIntegerList[1] * number} on ${currentRoom.npc.name}"

                    if (currentRoom.npc.health <= 0) {
                        println "You have killed " + currentRoom.npc.name
                        currentRoom.npc.alive = false
                    } else {
                        println currentRoom.npc.name + "'s health is now at " + currentRoom.npc.health
                    }

                    print "Press the enter key to continue..."
                    scanner.nextLine()
                    //The player gains health
                    health = health + (randomIntegerList[2] * number)

                    if (health >= 100) {
                        println "You have maximum health"
                        health = 100
                    } else {
                        println "You have gained health!"
                        println "Your health is at " + health
                    }

                    print "Press the enter key to continue..."
                    scanner.nextLine()
                } else if (userInput == "f") {
                    println "You decided to flee."
                    currentRoom = previousRoom
                    println currentRoom.description
                    keepFighting = false
                    print "Press the enter key to continue..."
                    scanner.nextLine()
                } else if (userInput == "q") {
                    keepFighting = false
                    keepPlaying = false
                    println "Good-bye"
                }
            }

        }
        if (!currentRoom.npc.alive) {
            println currentRoom.npc.name + " is dead"
        }
    } else {
        println currentRoom.npc.name + " is dead"
    }

    if (!keepPlaying) {
        //This with exit the game if keepPlaying is false
    } else {
        println "Exits: " + currentRoom.connections.keySet()
        print "Enter a direction: "
        def userInput = br.readLine()
        println()
        if (userInput == "q") {
            //If the user enters 'q' then the game ends
            println "Good-bye"
            keepPlaying = false
        } else if (userInput == "sv_cheats 1") {
            println "I'm sorry, but your attempt to access cheats has been denied."
            println()
            println "You are currently in " + currentRoom.title
        } else if (userInput.isInteger()) {
            println "Please don't use a number."
            println()
            println "You are currently in " + currentRoom.title
        } else if (!userInput) {
            //If the user enters nothing then it asks for an integer
            println "You didn't type anything, try again."
            println()
            println "You are currently in " + currentRoom.title
        } else {
            //If the user enters a valid number, it goes to a room
            //If the user enters an invalid number, it doesn't go to a room
            Room room = currentRoom.connections.get(userInput)
            if (room) {
                previousRoom = currentRoom
                currentRoom = room
                println "Welcome to " + currentRoom.title
            } else {
                println "Sorry, that is not a direction. Try again."
                println()
                println "You are currently in " + currentRoom.title
            }
        }
    }
}