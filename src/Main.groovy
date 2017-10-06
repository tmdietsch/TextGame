Room room1 = new Room (
        roomid:1,
        title:"room 1",
        description:"This is the lounge, when you come in, a receptionist waves at you, \"Good morning.\" There is also a TV showing the generic sports play, but you are far too busy for that."
)

Room room2 = new Room (
        roomid:2,
        title:"room 2",
        description:"You walk through the door, or maybe you teleported... I don't know but there are video games lining the walls if you are bored. Too bad there isn't an option to play them, in fact you can't even look at them. :("
)

Room room3 = new Room(
        roomid:3,
        title:"room 3",
        description:"Welcome to the kitchen, a normal kitchen, like the one at a stereotypical restaurant. Although you do smell something cheesy from Room 4. Also there is pizza on the counter but you are allergic to yeast."
)

Room room4 = new Room(
        roomid:4,
        title:"room 4",
        description:"You have arrived at the cheese room, filled with all sorts of cheese: Blue, Cheddar, American, Swiss, Camembert, Mozzarella, Roquefort, Manchego, Cotija, and so many more that you don't know the names to."
)

Room room5 = new Room(
        roomid:5,
        title:"room 5",
        description:"There is a lamp, just standing there, in the middle of an empty room. Odd."
)

Map <Integer,Room> rooms = [
        1:room1,
        2:room2,
        3:room3,
        4:room4,
        5:room5,
]

Boolean keepPlaying = true
while (keepPlaying) {
    //Defining user input
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
    print "Enter room number: "
    def userInput = br.readLine()
    if (userInput == 'q') {
        //If the user enters 'q' then the game ends
        println "Good-bye"
        keepPlaying = false
    } else if (!userInput) {
        //If the user enters nothing then it asks for an integer
        println "You didn't type anything, the keyboard is right there. Assuming you are not on a phone."
    } else if (!userInput.isInteger()){
        //If the user enters a non-integer, then it asks for a real number
        println "Enter a \'room number\' please."
    } else {
        //Show user room infromation
        Room room = rooms.get userInput as Integer
        //If the user enters a valid number, it goes to a room
        //If the user enters an invalid number, it doesn't go to a room
        if (room) {
            println "Welcome to " + room.title
            println room.description
        } else {
            println "Sorry, that room doesn't exist. Maybe it will be created in a future update."
        }
    }

}