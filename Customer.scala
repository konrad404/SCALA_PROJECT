import Enums.Direction._
import Enums.TripReason._
import Engine.Engine._
import Flight.Flight._

package Customer{
class Customer(private val name: String, private val countries: Array[Direction], private var numberOfCustomers: Int,
    private var daysInAdvance: Int, private val priceRange: Array[Int], private val tripReason: TripReason, private val engine: Engine){

    private val bookedFlights : Array[Flight] = Array()

    def bookFlight():Unit={
        val flights : Array[Flight] = this.engine.getFlights

        //flights.filter(f => ())
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