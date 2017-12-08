Player player = new Player(
        health: 100,
        previousHealth: 100,
        keys: 0,
)

BufferedReader bR = new BufferedReader(new InputStreamReader(System.in))

println "What is your name?"
player.name = bR.readLine()
println "Welcome " + player.name
println()

Npc lucy = new Npc(
        name:"Lucy the receptionist",
        isAggressive: false,
        description:"Good afternoon ${player.name}",
        alive: true,
        overallDamage: 0,
)

Room room1 = new Room(
        title:"the lounge",
        description:"The receptionist stands behind a wooden desk. There is also a TV showing the generic sports channel, but you are far too busy for that.",
        npc: lucy,
        locked: false,
)

Npc vendor = new Npc(
        health: 30,
        name:"Josh",
        isAggressive: true,
        multiplier: 3,
        description:"Hey! Your not supposed to be here! Where's your identification?!",
        alive: true,
        overallDamage: 0,
        attackNumber: 0,
)

Room room2 = new Room(
        title:"the arcade",
        description:"You walk through the door, or maybe you teleported... I don't know but there are video games lining the walls if you are bored. Too bad there isn't an option to play them, in fact you can't even look at them. :(",
        npc: vendor,
        locked: false,
)

Npc chef = new Npc(
        health: 40,
        name:"Chef Ticonderoga",
        isAggressive: true,
        multiplier: 3,
        description:"Please be patient Lucy, dinner is almost...well hello there, you must be new in town. ${player.name}, wasn't it?",
        alive: true,
        overallDamage: 0,
        attackNumber: 0,
)

Room room3 = new Room(
        title:"kitchen",
        description:"This is a normal kitchen, like the one at a stereotypical restaurant. Although you do smell something divine from the other room. Also there is pizza on the counter but you are allergic to yeast.",
        npc: chef,
        locked: false,
)

Npc stinkyCheese = new Npc(
        health: 150,
        name:"Stinky Cheese",
        isAggressive: true,
        multiplier: 5,
        description:"Ha! You think you can just waltz in here and take all my cheese! Never!",
        alive: true,
        overallDamage: 0,
        attackNumber: 0,
)

Room room4 = new Room(
        title:"Cheese Heaven",
        description:"You have arrived at the cheese room, filled with all sorts of cheese: Blue, Cheddar, American, Swiss, Camembert, Mozzarella, Roquefort, Manchego, Cotija, and so many more that you don't know the names to.",
        npc: stinkyCheese,
        locked: true,
)

Npc lamp = new Npc(
        health: 80,
        name:"Lamp",
        isAggressive: true,
        multiplier: 4,
        description:"Hello. You must be ${player.name}. Welcome to my humble aboad.",
        alive: true,
        overallDamage: 0,
        attackNumber: 0,
)

Room room5 = new Room(
        title:"garage",
        description:"There is a lamp, just standing there, in the middle of an empty room. Odd.",
        npc: lamp,
        locked: false,
)

Npc gremlin = new Npc(
        health: 15,
        name:"Mort the Gremlin",
        isAggressive: true,
        multiplier: 2,
        description:"Go away... go away! Leave me be!",
        alive: true,
        overallDamage: 0,
        attackNumber: 0,
)

Room room6 = new Room(
        title:"bathroom",
        description:"Welcome. Don't know why you came here, you already went earlier, remember?",
        npc: gremlin,
        locked: false,
)

List<Npc> npcs = [lucy,vendor,chef,stinkyCheese,lamp,gremlin]

Integer count = 0
npcs.each {
    if (it.isAggressive) {
        count++
    }
}

//Connections from one room to another
room1.connections = ["n":room2,"e":room3,"w":room5]
room2.connections = ["e":room6,"s":room1]
room3.connections = ["s":room4,"w":room1]
room4.connections = ["n":room3]
room5.connections = ["e":room1]
room6.connections = ["w":room2]

boolean difficulty = true
while (difficulty) {
    println "Choose your difficulty. [easy] [medium] [hard]"
    def userInput1 = bR.readLine()
    if (userInput1 == "easy") {
        player.multiplier = 8
        difficulty = false
    } else if (userInput1 == "medium") {
        player.multiplier = 5
        difficulty = false
    } else if (userInput1 == "hard") {
        player.multiplier = 2
        difficulty = false
    } else {
        println "Please choose a difficulty."
    }
}

Room currentRoom = room1 //This is the current room that the player is in
Boolean keepPlaying = true //This variable controls whether the player keeps on playing or not
Scanner scanner = new Scanner(System.in) //Creates a scanner object, prompting the player to press a button to continue
Room previousRoom = room1 //Creating a variable for the room that the player was previously in
Integer npcDeaths = 0 //The number of kills the player has accumulated

println()
println "To quit enter \'q\'"
println()
println "Welcome to " + currentRoom.title

while (keepPlaying) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
    println currentRoom.description

    if (player.health == 100) {
        println "You are at full health!"
    } else {
        println "Your health is " + player.health + " out of 100"
    }

    if (currentRoom.npc.attackNumber == 1) {
        println "${currentRoom.npc.name} has attacked ${currentRoom.npc.attackNumber} time"
    } else if (currentRoom.npc.attackNumber > 1) {
        println "${currentRoom.npc.name} has attacked ${currentRoom.npc.attackNumber} times"
    }

    if (currentRoom.npc.overallDamage > 0) {
        println "You have lost ${currentRoom.npc.overallDamage} health here"
    } else if (currentRoom.npc.overallDamage < 0) {
        println "You have gained ${currentRoom.npc.overallDamage * -1} health here"
    }

    if (player.keys == 1) {
        println "You have ${player.keys} key"
    } else if (player.keys > 1) {
        println "You have ${player.keys} keys"
    }

    //Beginning of Fighting Loop
    if (!currentRoom.npc.isAggressive) {
        println "${currentRoom.npc.name} says: ${currentRoom.npc.description}"
    } else if (currentRoom.npc.isAggressive && currentRoom.npc.alive) {
        println "${currentRoom.npc.name} says: ${currentRoom.npc.description}"
        def keepFighting = true
        while (currentRoom.npc.alive && player.health > 0 && keepFighting) { //Fighting loops till the monster is dead, the player is dead, or the player flees
            Random rand = new Random() //Creates a random object
            int max = 10 //This is the maximum number for the random number generator
            def randomIntegerList = [] //This is a list of random numbers created below
            (1..5).each {
                randomIntegerList << rand.nextInt(max) + 1 //This creates a random number between 1 and 10
            }
            if (randomIntegerList[0] % 2 == 1) {
                //If the random number is odd, then the monster attacks
                player.health = player.health - (randomIntegerList[1] * currentRoom.npc.multiplier)
                currentRoom.npc.overallDamage = currentRoom.npc.overallDamage + (randomIntegerList[1] * currentRoom.npc.multiplier)
                currentRoom.npc.attackNumber++
                if (player.health <= 0) {
                    println currentRoom.npc.name + " attacked"
                    println "You have been killed by " + currentRoom.npc.name
                    keepPlaying = false
                    keepFighting = false
                } else {
                    println currentRoom.npc.name + " attacked. Your health is now at " + player.health
                    print "Press the enter key to continue..."
                    scanner.nextLine()
                    println()
                }
                player.previousHealth = player.health
            } else if (randomIntegerList[0] % 2 == 0) {
                //If the random number is even, the player has a choice to either attack or flee
                boolean keepAnswer = true
                while (keepAnswer) {
                    print "Do you wish to attack[a] or flee[f]?: "
                    def userInput = br.readLine()
                    if (userInput == "a") {
                        //The player attacks
                        keepAnswer = false
                        currentRoom.npc.health = currentRoom.npc.health - (randomIntegerList[1] * player.multiplier)
                        println "You've inflicted ${randomIntegerList[1] * player.multiplier} on ${currentRoom.npc.name}"

                        if (currentRoom.npc.health <= 0) {
                            println "You have killed " + currentRoom.npc.name
                            currentRoom.npc.alive = false
                            npcDeaths++
                            if (npcDeaths == count - 1) {
                                println()
                                println "You have found a key!"
                                println()
                                player.keys++
                            }
                        } else {
                            println currentRoom.npc.name + "'s health is now at " + currentRoom.npc.health
                        }

                        print "Press the enter key to continue..."
                        scanner.nextLine()
                        println()
                        //The player gains health
                        if (player.health == 100) {
                            println "You have maximum health"
                        } else {
                            println "You have gained health!"
                            player.health = player.health + (randomIntegerList[2] * player.multiplier)
                            if (player.health > 100) {
                                player.health = 100
                            }
                            println "Your health is now ${player.health} out of 100"
                        }

                        currentRoom.npc.overallDamage = currentRoom.npc.overallDamage + (player.previousHealth - player.health)

                        print "Press the enter key to continue..."
                        scanner.nextLine()
                        println()
                    } else if (userInput == "f") {
                        keepAnswer = false
                        println "You decided to flee."
                        currentRoom = previousRoom
                        println currentRoom.description
                        if (!currentRoom.npc.isAggressive) {
                            println currentRoom.npc.name + " says: " + currentRoom.npc.description
                        }
                        keepFighting = false
                        print "Press the enter key to continue..."
                        scanner.nextLine()
                        println()
                    } else if (userInput == "q") {
                        keepAnswer = false
                        keepFighting = false
                        keepPlaying = false
                        println "Good-bye"
                    } else {
                        println "Choose a valid answer"
                    }
                }
            }
        }
        if (!currentRoom.npc.alive) {
            println currentRoom.npc.name + " is now dead"
        }
    } else {
        println currentRoom.npc.name + " is dead"
    }
    //End of fighting loop
    if (!keepPlaying) {
        //This with exit the game if keepPlaying is false
    } else if (npcDeaths == count) {
        println()
        println "Congratulations ${player.name}! You have beaten ${currentRoom.npc.name}!"
        println "You have been awarded: a light bulb, a knife, Roquefort, a sock, and a golden ring as trophies."
        println "Have a great day!"
        println()
        print "Press the enter key to continue..."
        scanner.nextLine()
        keepPlaying = false
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
            println "You are currently in ${currentRoom.title}"
        } else if (currentRoom.connections.get(userInput).locked && player.keys == 0) {
            println "That room is locked"
            println()
            println "You are currently in ${currentRoom.title}"
        } else {
            //If the user enters a valid number, it goes to a room
            //If the user enters an invalid number, it doesn't go to a room
            if (currentRoom.connections.get(userInput).locked && player.keys > 0) {
                println "You have used a key for the door."
                player.keys--
            }
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