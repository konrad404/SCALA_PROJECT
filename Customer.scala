import Enums.Direction
import Enums.TripReason
import Enums.Status
import Engine._
import Flight._

package Customer{
class Customer(private val name: String, private val countries: Array[Direction.Value], private var numberOfCustomers: Int,
    private var daysInAdvance: Int, private val priceRange: Array[Double], private val tripReason: TripReason.Value, private val engine: Engine){

    private val bookedFlights : Array[Flight] = Array()

    def bookFlight():Unit={
        var flights : Array[Flight] = this.engine.getFlights()
        
        flights = flights.filter(f => (priceRange(0) <= f.getPrice() && priceRange(1) >= f.getPrice() &&
                        priceRange(0) <= f.getFirstClassPrice() && priceRange(1) >= f.getFirstClassPrice() &&
                        f.getFreePlacesNumber() >= numberOfCustomers && f.getStatus() == Status.Arriving)
                        && countries.contains(f.getDirection()))
        
        println("===============================================================================\n [FILTERED FLIGHTS]")
        for(f <- flights)
            println(f.toString())
    }
     
} 
    
object ApplCust {
    def main(agrs: Array[String]):Unit={
        val e = new Engine()
        val dirs : Array[Direction.Value] = Array(Direction.London)
        val range : Array[Double] = Array(100.0, 200.0)
        val cust = new Customer("Roman", dirs, 1, 3, range, TripReason.Work, e)
        cust.bookFlight()
    }
}

}

/*
zamów lot
odwołaj lot
przełóż na następny termin
przyjedź na lot
dokupić bilet
*/