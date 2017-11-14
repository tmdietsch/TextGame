import java.util.Random

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

def difficulty = true
while (difficulty) {
    println "Choose your difficulty. [Easy] [Medium] [Hard]"
    def userInput1 = bR.readLine()
    if (userInput1 == "Easy") {
        def playerMultiplier = 10
        difficulty = false
    } else if (userInput1 == "Medium") {
        def playerMultiplier = 5
        difficulty = false
    } else if (userInput1 == "Hard") {
        def playerMultiplier = 2
        difficulty = false
    } else {
        println "Please choose a difficulty."
    }
}
println "To quit enter \'q\'"
Integer health = 100
Room currentRoom = room1
Boolean keepPlaying = true
Scanner scanner = new Scanner(System.in)
def previousRoom
println()
println "Welcome to " + currentRoom.title

while (keepPlaying) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

    Random rand = new Random() //Creates a random object
    int max = 10 //This is the maximum number for the random number generator
    def randomIntegerList = [] //This is a list of random numbers created below

    println currentRoom.description

    //Fighting loops till the monster or the player is dead
    if (!currentRoom.npc.isAggressive) {
        println currentRoom.npc.description
    } else if (currentRoom.npc.isAggressive) {
        (1..10).each {
            randomIntegerList << rand.nextInt(max) + 1 //This creates a random number between 1 and 10
        }
        println currentRoom.npc.description
        def keepFighting = true
        while (currentRoom.npc.alive && health > 0 && keepFighting) {
            if (randomIntegerList % [0] == 1) {
                health = health - (randomIntegerList[1]*currentRoom.npc.multiplier)
                println currentRoom.npc.name + " attacked. Your health is now " + health + "."
                scanner.nextLine()
            } else if (randomIntegerList % [0] == 0) {
                println "Do you wish to attack or flee? [a] [f]"
                def userInput = br.readLine()
                if (userInput == "a") {
                    currentRoom.npc.health = currentRoom.npc.health - (randomIntegerList[1] * playerMultiplier)
                } else if (userInput == "f") {
                    println "You decided to flee."
                    currentRoom = previousRoom
                    println currentRoom.description
                    keepFighting = false
                    scanner.nextLine()
                }
            }

        }
        println currentRoom.npc.name + " is dead."
    }

    if (health > 100) {
        health = 100
    } else if (health <= 0) {
        keepPlaying = false
    }

    println "Exits: " + currentRoom.connections.keySet()

    if (!keepPlaying) {
        //This with exit the game if keepPlaying is false
    } else {
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