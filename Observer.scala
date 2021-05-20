package Observer{

    import Engine._
    import Flight._
    import Enums.Direction
    import Enums.Sizes
    import Enums.Runaways
    import Enums.Status
    import java.util.Calendar
    import java.util.Date
    import scala.util.Random
    import scala.math.BigDecimal

    class Observer(val engine: Engine){
        private var pastFlights:  Array[Flight] = Array()
        private var flightsNumber: Int = 0

        // val today = new Date()
        // var time = today.getTime()
        // var seed: Long = scala.math.pow(10,10).toLong
        // var toAdd = Random.nextLong(seed)
        // // val data = new Date(time + toAdd)
        // val places = Sizes(Random.nextInt(Sizes.maxId)).toString().toInt
        // val probability = Random.nextDouble()
        // val direction = Direction(Random.nextInt(Direction.maxId))
        // val inOut = Status(Random.nextInt(2))
        // val runaway = Runaways(Random.nextInt(Runaways.maxId))
        // val price = (20 + Random.nextInt(20)).toDouble
        // val flight1 =  new Flight(direction,inOut,places,today,probability,runaway,price, this)

        // flightTookPlaace(flight1)

        def flightTookPlaace(flight: Flight) : Unit = {
            pastFlights = pastFlights :+ flight
            flightsNumber += 1
        }

        def getStatisticsFromDay(day: Date): Unit = {
            var todaysFlights: Array[Flight] = Array()
            for(flight <- pastFlights){
                if (day.getDate() == flight.getDate().getDate() && day.getMonth() == flight.getDate().getMonth() && day.getYear() == flight.getDate().getYear()){
                    todaysFlights = todaysFlights :+ flight
                }
            }
            var count = 0
            var places = 0 
            var income = 0.0
            for(flight <- todaysFlights){
                println(flight)
                count += 1
                places += flight.getTakenPlaces()
                income += flight.getCurrIncome()
            }
            println("Flights on day: "+  day.getYear()+ day.getMonth()+ day.getDate()+ " : "+  count+ " clients number: "+ places+ " income: "+ income, "0")
        }
        
    }

    object Observer {
        def apply(engine: Engine) = new Observer(engine)
    }
}