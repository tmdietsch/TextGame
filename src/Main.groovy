Room room1 = new Room (
        roomid:1,
        title:"This is the title",
        description:"Decribing for you"
)

Room room2 = new Room (
        roomid:2,
        title:"This is the title",
        description:"Decribing for you"
)

Room room3 = new Room(
        roomid:3,
        title:"This is the title",
        description:"Decribing for you"
)

Map <Integer,Room> rooms = [
        1:room1,
        2:room2,
        3:room3,
]

Boolean keepPlaying = true
while (keepPlaying) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
    print "Enter room number: "
    def userInput = br.readLine()
    if (userInput == 'q') {
        println "Quitting"
        keepPlaying = false
    } else if (!userInput) {
        println "Choice can't be empty. Please try again"
    } else if (!userInput.isInteger()){
        println "Please enter a number"
    } else {
        //Show user room infromation
        Room room = rooms.get userInput as Integer
        if (room) {
            println "Welcome to " + room.title
        } else {
            println "Sorry, the room doesn't exist"
        }
    }

}