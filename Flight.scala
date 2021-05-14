package Flight{
    import Enums.Direction
    import Enums.Sizes
    import Enums.Runaways
    import Enums.Status
    import java.util.Calendar
    import java.util.Date
    import scala.util.Random
    import scala.math.BigDecimal
    import Observer._

    class InvalidProbabilityException(s:String) extends Exception(s){}  


    class Flight(private val direction: Direction.Value, private val status: Status.Value, private var freePlaces: Int, private val date: Date,
     private val changeTimeProb: Double, private val runway: Runaways.Value, private val price: Double, private val observer: Observer){
        if(freePlaces <= 0){
            throw new IllegalArgumentException
        }
        if(changeTimeProb > 1 || changeTimeProb < 0){
            throw new InvalidProbabilityException("wrong probability")
        }
        val toAdd = 200

        private val firstClassPrice : Double  = price + toAdd
        private val places = freePlaces
        private val id = Flight.getNewId()

        def getPrice(): Double = price
        def getFirstClassPrice(): Double = firstClassPrice
        def isArriving(): Boolean  = (status == Status.Arriving)
        def isLeaving(): Boolean  = (status == Status.Leaving)
        def getDate(): Date = date
        def res_places(n: Int): Unit ={
            if(n > freePlaces){
                throw new IllegalArgumentException("There is not enough places")
            }
            freePlaces -= n
        }
        def getFreePlacesNumber(): Int = freePlaces
        def getTakenPlaces(): Int = places - freePlaces
        def getDirection(): Direction.Direction = direction    
        def getProbability(): Double = changeTimeProb
        def getStatus(): Status.Status = status
        def getId(): Int = id
        override def toString() = {
            "ID: " + id + "\t Direction: " + direction + "\t date: " + date + "\t status: " + status + "\t places: " + places + "\t free: " + freePlaces + "\t price:" + price + "0\t first class price: " + firstClassPrice + "0"
        }
    }

    object Flight{
        var highestId = 0
        def getNewId(): Int = {
            highestId += 1
            highestId
        }
    }

    class FlightGenerator(observer: Observer){
        val minPrice = 50
        val priceBracket = 300
        def generateFlight(): Flight = {
            val today = new Date()
            var time = today.getTime()
            var seed: Long = scala.math.pow(10,10).toLong
            var toAdd = Random.nextLong(seed)
            val data = new Date(time + toAdd)
            val places = Sizes(Random.nextInt(Sizes.maxId)).toString().toInt
            val probability = Random.nextDouble()
            val direction = Direction(Random.nextInt(Direction.maxId))
            val inOut = Status(Random.nextInt(2))
            val runaway = Runaways(Random.nextInt(Runaways.maxId))
            val price = (minPrice + Random.nextInt(priceBracket)).toDouble
            new Flight(direction,inOut,places,data,probability,runaway,price, observer)
        }
        
        def generateTimetable(amount:  Int): Array[Flight] = {
            var timetable : Array[Flight] = Array()
            for(i <- 1 to amount){
                timetable = timetable :+ generateFlight()
            }
            timetable
        }
    }

    object FlightGenerator {
        def apply(observer: Observer) = new FlightGenerator(observer)
    }
}
