import Enums.Direction
import Enums.TripReason
import Enums.Status
import Engine._
import Flight._
import Observer._
import scala.util.Random
import java.util.Date

package Customer{
class Customer(private val name: String, private val countries: scala.collection.mutable.Set[Direction.Value], private var numberOfCustomers: Int,
    private var daysInAdvance: Int, private val priceRange: List[Double], private val tripReason: TripReason.Value, private val engine: Engine){

    private val bookedFlights : List[Int] = List()
    private val takenFlights : List[Int] = List()

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
        
        val selectedFlight = flights(Random.nextInt(flights.size))
        println("===============================================================================\n [SELECTED FLIGHT]")
        println(selectedFlight.toString())
        selectedFlight.getId() +: bookedFlights

        engine.reservePlaces(selectedFlight.getId(), numberOfCustomers)
    }

    def cancelFlight(flightId: Int):Unit={
        bookedFlights.drop(flightId)
    }

    def takeFlight(flightId: Int):Unit={
        bookedFlights.drop(flightId)
        flightId +: takenFlights
    }

    def buyAdditionalTicket(flightId: Int, amount: Int):Unit={
        var flight: Flight = this.engine.getFlights()(flightId)

        if(flight.getDate().after(engine.getDate()) && flight.getFreePlacesNumber() >= amount)
            engine.reservePlaces(flightId, amount)
    }

    override def toString():String={
        name + " " + countries.toString() + " " + numberOfCustomers + " " + daysInAdvance + " " + priceRange.toString() + " " + tripReason 
    }
}

    class CustomerGenerator(engine: Engine){
        val nameGenerator = new NameGenerator()

        def generateCustomer(): Customer={
            var name = nameGenerator.generateName()
            val directions = scala.collection.mutable.Set[Direction.Value]()
            val numberOfDirections = Random.nextInt(Direction.maxId)+1
            while(directions.size < numberOfDirections){
                directions.add(Direction(Random.nextInt(Direction.maxId)))
            }
            val numberOfCustomers = Random.nextInt(9) + 1
            val daysInAdvance = Random.nextInt(150)
            val minPrice = (50 + Random.nextInt(200)).toDouble
            val maxPrice = (minPrice + Random.nextInt(300)).toDouble
            val priceRange = List(minPrice, maxPrice)
            val tripReason = TripReason(Random.nextInt(TripReason.maxId))
            new Customer(name, directions, numberOfCustomers, daysInAdvance, priceRange, tripReason, engine)
        }

        def generateCustomers(numberOfCustomers: Int): List[Customer] ={
            var newCustomers : List[Customer] = List()
            for( _ <- 0 to numberOfCustomers)
                generateCustomer() +: newCustomers
            newCustomers 
        }
    }

    class NameGenerator(){
        val names = List("Anna", "Maria", "Katarzyna", "Barbara", "Ewa", "Teresa", "Malgorzata", "Elzbieta", "Magdalena", "Agnieszka", "Zofia",
        "Krystyna", "Jadwiga", "Joanna", "Natalia", "Monika", "Marta", "Helena", "Dorota", "Danuta", "Piotr", "Krzysztof", "Tomasz", "Pawel",
        "Jan", "Michal", "Marcin", "Jakub", "Adam", "Stanislaw", "Marek", "Lukasz", "Grzegorz", "Mateusz", "Wojciech", "Mariusz", "Dariusz", "Konrad", "Maciej")

        val surnames = List("Nowak", "Wojcik", "Kowalczyk", "Mazur", "Maj", "Krawczyk", "Zajac", "Krol", "Wrobel", "Stepien", "Adamczyk", "Pawlak",
        "Dudek", "Walczak", "Sikora", "Baran", "Pietrzak")

        def generateName():String={
            names(Random.nextInt(names.size)) + " " + surnames(Random.nextInt(surnames.size))
        }

    }

}

/*
zamów lot ok
odwołaj lot ok
przełóż na następny termin - y jak
przyjedź na lot ok
dokupić bilet ok
*/