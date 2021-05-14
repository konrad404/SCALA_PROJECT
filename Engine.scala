import Flight._
import Customer._
import Enums._

package Engine{

class Engine(){
    val flightGenerator = FlightGenerator()
    private var flights : Array[Flight] = flightGenerator.generateTimetable(20) // to się wygeneruje

    def getFlights():Array[Flight]={this.flights}
}
}