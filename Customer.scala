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

    private var bookedFlights : List[Int] = List()
    private val takenFlights : List[Int] = List()
    private var isBusiness = false

    def checkDaysInAdvance(flightDate: Date):Boolean = {
        val flightTime = flightDate.getTime()
        val currTime = engine.getDate().getTime()
        flightTime > currTime && ((flightTime - currTime) / 86400000 >= daysInAdvance)
    }

    def bookFlight(postponeDirection: Direction.Value, postponeDate: Date):Unit={
        var flights : Array[Flight] = this.engine.getFlights()
        
        //basic criteria
        flights = flights.filter(f => (f.getFreePlacesNumber() >= numberOfCustomers
                        && countries.contains(f.getDirection()) && checkDaysInAdvance(f.getDate())))

        if(postponeDirection != null){
            flights = flights.filter(f => (f.getDirection() == postponeDirection && f.getDate().after(postponeDate)))
        }

        // first class
        var firstClassFlights = flights.filter(f => (f.getFreeBusinessPlacesNumber() >= numberOfCustomers &&
                                priceRange(0) <= f.getFirstClassPrice() && priceRange(1) >= f.getFirstClassPrice()))
        
        if(firstClassFlights.size > 0){
            flights = firstClassFlights
            isBusiness = true
        }
        else{
            //economic class
            isBusiness = false
            flights = flights.filter(f => (f.getFreeEconomicPlacesNumber() >= numberOfCustomers &&
                                             priceRange(0) <= f.getPrice() && priceRange(1) >= f.getPrice()))
        }
                              
        //println("===============================================================================\n [FILTERED FLIGHTS]")
        //for(f <- flights)
        //    println(f.toString())
        
        if(flights.size > 0){
            val selectedFlight = flights(Random.nextInt(flights.size))
            //println("===============================================================================\n [SELECTED FLIGHT]")
            ///println(selectedFlight.toString())
            bookedFlights = selectedFlight.getId() :: bookedFlights
        
            engine.reservePlaces(selectedFlight.getId(), numberOfCustomers, isBusiness)
        }
    }

    def takeFlight(flightId: Int):Unit={
        bookedFlights.drop(flightId)
        flightId +: takenFlights
    }

    def buyAdditionalTicket(flightId: Int, amount: Int):Unit={
        var flight: Flight = this.engine.getFlights()(flightId)
        numberOfCustomers += amount

        if(flight.getDate().after(engine.getDate()) && ((isBusiness && flight.getFreeBusinessPlacesNumber() >= amount)
            || (!isBusiness && flight.getFreeEconomicPlacesNumber() >= amount))){
                println(flight.getFreeBusinessPlacesNumber() + " " + flight.getFreeEconomicPlacesNumber())
                println("IsBussines:" + isBusiness + " amount: " + amount)
                engine.reservePlaces(flightId, amount, isBusiness)
            }
    }

    def cancelFlight(flightId: Int):Unit={
        bookedFlights.drop(flightId)
        engine.deleteReservedPlaces(flightId, numberOfCustomers, isBusiness)
    }

    def flightPostponement(flightId: Int):Unit={
        var flightToChange : Flight = this.engine.getFlights()(flightId)
        cancelFlight(flightId)
        bookFlight(flightToChange.getDirection(), flightToChange.getDate())
    }

    override def toString():String={
        name + " " + countries.toString() + " " + numberOfCustomers + " " + daysInAdvance + " " + priceRange.toString() + " " + tripReason 
    }

    def randomEvent():Unit={
        //postponing a flight event
        if(bookedFlights.size > 0){
            if(Random.nextInt(100) % 10 == 0){
                flightPostponement(bookedFlights(Random.nextInt(bookedFlights.size)))
            }
            //buy additional tickets event
            if(Random.nextInt(100) % 9 == 0){
                buyAdditionalTicket(bookedFlights(Random.nextInt(bookedFlights.size)), Random.nextInt(10))
            }
            if(Random.nextInt(100) % 10 == 0){
                cancelFlight(bookedFlights(Random.nextInt(bookedFlights.size)))
            }
        }
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
            val daysInAdvance = Random.nextInt(7)
            val minPrice = (50 + Random.nextInt(200)).toDouble
            val maxPrice = (minPrice + Random.nextInt(300)).toDouble
            val priceRange = List(minPrice, maxPrice)
            val tripReason = TripReason(Random.nextInt(TripReason.maxId))
            new Customer(name, directions, numberOfCustomers, daysInAdvance, priceRange, tripReason, engine)
        }

        def generateCustomers(numberOfCustomers: Int): List[Customer] ={
            var newCustomers : List[Customer] = List()
            for( _ <- 0 to numberOfCustomers){
                var customer = generateCustomer()
                newCustomers = customer :: newCustomers
            }
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