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

    def work(days: Int): Unit = {
        var date = new Date()
        for(day <- 1 to days){
            date = new Date(date.getTime() + 86400000)
            for(flight <- flights){
                if(date.getDate() == flight.getDate().getDate() && date.getMonth() == flight.getDate().getMonth() && date.getYear() == flight.getDate().getYear()){
                    flight.fly()
                }
            }
            // println(date)
            observer.getStatisticsFromDay(date)
        }
        
    }

    def reservePlaces(flightId: Int, numberOfCustomers: Int, isBusiness: Boolean) : Unit = {
        flights(flightId-1).res_places(numberOfCustomers, isBusiness)
    }

    def getDate(): Date = currentTime

    def getStatisticsFromDay(day: Date): String = {
        observer.getStatisticsFromDay(day)
    }

}
}