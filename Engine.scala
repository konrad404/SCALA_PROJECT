import Flight._
import Customer._
import Enums._
import Observer._
import java.util.Date

package Engine{

class Engine(){
    private var timetableLength = 20
    private val observer: Observer = Observer(this)
    private val flightGenerator = FlightGenerator(observer)
    private val customerGenerator = new CustomerGenerator(this)
    private val currentTime = new Date()
    
    //ja to bym dał jako osobne metody zapisujące do tych varów, a nie że się wywołują przy tworzeniu engina
    private var flights : Array[Flight] = flightGenerator.generateTimetable(timetableLength)
    private var customers : List[Customer] = customerGenerator.generateCustomers(timetableLength)


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

    def getDate(): Date = currentTime

    def getStatisticsFromDay(day: Date){
        observer.getStatisticsFromDay(day)
    }

}
}