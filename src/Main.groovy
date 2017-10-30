Room room1 = new Room (
        roomid:1,
        title:"room 1",
        description:"This is the lounge, when you come in, a receptionist waves at you, \"Good morning.\" There is also a TV showing the generic sports channel, but you are far too busy for that.",
)

Room room2 = new Room (
        roomid:2,
        title:"room 2",
        description:"You walk through the door, or maybe you teleported... I don't know but there are video games lining the walls if you are bored. Too bad there isn't an option to play them, in fact you can't even look at them. :(",
)

Room room3 = new Room(
        roomid:3,
        title:"room 3",
        description:"Welcome to the kitchen, a normal kitchen, like the one at a stereotypical restaurant. Although you do smell something cheesy from Room 4. Also there is pizza on the counter but you are allergic to yeast.",
)

Room room4 = new Room(
        roomid:4,
        title:"room 4",
        description:"You have arrived at the cheese room, filled with all sorts of cheese: Blue, Cheddar, American, Swiss, Camembert, Mozzarella, Roquefort, Manchego, Cotija, and so many more that you don't know the names to.",
)

Room room5 = new Room(
        roomid:5,
        title:"room 5",
        description:"There is a lamp, just standing there, in the middle of an empty room. Odd.",
)

room1.connections = ["n":room2, "w":room5, "e":room3]
room2.connections = ["s":room1]
room3.connections = ["w":room1,"s":room4]
room4.connections = ["n":room3]
room5.connections = ["e":room1]

println "To quit enter \'q\'"
Room currentRoom = room1
Boolean keepPlaying = true
println()

while (keepPlaying) {
    //Defining user input
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

    println currentRoom.description
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
            currentRoom = room
            println "Welcome to " + currentRoom.title
        } else {
            println "Sorry, that is not a direction. Try again."
            println()
            println "You are currently in " + currentRoom.title
        }
    }
}