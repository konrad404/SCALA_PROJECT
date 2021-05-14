import Enums.Direction
import Enums.TripReason
import Enums.Status
import Engine._
import Flight._
import scala.util.Random

package Customer{
class Customer(private val name: String, private val countries: Array[Direction.Value], private var numberOfCustomers: Int,
    private var daysInAdvance: Int, private val priceRange: Array[Double], private val tripReason: TripReason.Value, private val engine: Engine){

    private val bookedFlights : List[Int] = List()

    val rand = new scala.util.Random

    def bookFlight():Unit={
        var flights : Array[Flight] = this.engine.getFlights()
        
        //days in advance do ogarnięcia
        flights = flights.filter(f => (priceRange(0) <= f.getPrice() && priceRange(1) >= f.getPrice() &&
                        priceRange(0) <= f.getFirstClassPrice() && priceRange(1) >= f.getFirstClassPrice() &&
                        f.getFreePlacesNumber() >= numberOfCustomers && f.getStatus() == Status.Arriving)
                        && countries.contains(f.getDirection()))
        
        println("===============================================================================\n [FILTERED FLIGHTS]")
        for(f <- flights)
            println(f.toString())
        
        val selectedFlight = flights(rand.nextInt(flights.size))
        println("===============================================================================\n [SELECTED FLIGHT]")
        println(selectedFlight.toString())
        selectedFlight.getId() +: bookedFlights

        engine.reservePlaces(selectedFlight.getId(), numberOfCustomers)
    }

    def cancelFlight(flightId: Int):Unit={
        bookedFlights.drop(flightId)
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