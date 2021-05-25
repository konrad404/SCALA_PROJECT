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

    // def showTimetable(day: Date){
    //     for(flight <- flights){
    //         if(day.getDate() == flight.getDate().getDate() && day.getMonth() == flight.getDate().getMonth() && day.getYear() == flight.getDate().getYear()){
    //             println(flight)
    //         }
    //     }
    // }

    def work(days: Int): Unit = {
        // var date = new Date()

        //generate customers, each of them books a flight
        customers = customerGenerator.generateCustomers(2000) ++ customers
        for(customer <- customers)
            customer.bookFlight(null, null)

        for(day <- 1 to days){
            date = new Date(date.getTime() + 86400000)
            // showTimetable(date)
            println()
            for(flight <- flights){
                if(date.getDate() == flight.getDate().getDate() && date.getMonth() == flight.getDate().getMonth() && date.getYear() == flight.getDate().getYear()){
                    flight.fly()
                }
            }
            // println(date)
            for(customer <- customers)
                customer.randomEvent()

            observer.getStatisticsFromDay(date)
            println("\n")
            Thread.sleep(1000)
        }
    }

    def reservePlaces(flightId: Int, numberOfCustomers: Int, isBusiness: Boolean) : Unit = {
        flights(flightId-1).res_places(numberOfCustomers, isBusiness)
    }

    def getDate(): Date = date

    def getStatisticsFromDay(day: Date): String = {
        observer.getStatisticsFromDay(day)
    }

}
}