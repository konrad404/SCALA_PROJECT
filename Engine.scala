import Flight._
import Customer._
import Enums._

package Engine{

class Engine(){
    private var timetableLength = 20
    private val flightGenerator = FlightGenerator()
    private var flights : Array[Flight] = flightGenerator.generateTimetable(timetableLength) // to się wygeneruje

    def getFlights():Array[Flight]={this.flights}

    def showTimetable(): Unit = {
        for(i:Int <- 0 to timetableLength -1){
            println(flights(i))
        }
    }

    def work(): Unit = {

    }

    def reservePlaces(flightId: Int, numberOfCustomers: Int) : Unit = {
        flights(flightId-1).res_places(numberOfCustomers)
    }
}
}