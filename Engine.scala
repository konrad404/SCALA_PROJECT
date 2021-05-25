import Flight._
import Customer._
import Enums._
import Observer._
import java.util.Date

package Engine{

class Engine(){
    private var timetableLength = 2000
    private val observer: Observer = Observer(this)
    private val flightGenerator = FlightGenerator(observer)
    private val customerGenerator = new CustomerGenerator(this)
    private var date = new Date()
    
    private var flights : Array[Flight] = flightGenerator.generateTimetable(timetableLength)
    private var customers : List[Customer] = List()


    def getFlights():Array[Flight]={this.flights}

    def showTimetable(): Unit = {
        for(i:Int <- 0 to timetableLength -1){
            println(flights(i))
        }
    }

    def work(days: Int): Unit = {
        customers = customerGenerator.generateCustomers(20000) ++ customers
        for(customer <- customers)
            customer.bookFlight(null, null)

        for(day <- 1 to days){
            date = new Date(date.getTime() + 86400000)
            println()
            for(flight <- flights){
                if(date.getDate() == flight.getDate().getDate() && date.getMonth() == flight.getDate().getMonth() && date.getYear() == flight.getDate().getYear()){
                    flight.fly()
                }
            }
            for(customer <- customers)
                customer.randomEvent()

            observer.getStatisticsFromDay(date)
            println("\n")
            Thread.sleep(1000)
        }
    }

    def reservePlaces(flightId: Int, numberOfCustomers: Int, isBusiness: Boolean) : Unit = {
        flights(flightId-1).resPlaces(numberOfCustomers, isBusiness)
    }

    def deleteReservedPlaces(flightId: Int, numberOfCustomers: Int, isBusiness: Boolean): Unit = {
        flights(flightId-1).deleteRes(numberOfCustomers, isBusiness)
    }

    def getDate(): Date = date

    def getStatisticsFromDay(day: Date): String = {
        observer.getStatisticsFromDay(day)
    }

}
}