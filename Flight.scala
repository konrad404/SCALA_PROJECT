package Flight{
    import Enums.Direction
    import Enums.Sizes
    import Enums.Runaways
    import java.util.Calendar
    import java.util.Date
    import scala.util.Random
    import scala.math.BigDecimal

    object Status extends Enumeration{
        type Status = Value
        val Leaving, Arriving, Staying, Left = Value
    }


    // import Status

    class DateFun(){
        // var c = Calendar()
        def getDate(): Date = {
            Calendar.getInstance().getTime()
        }

    }

    // class Hour(private var hour: Int, private var minute: Int){
    //     override def toString(): String = {
    //         var h = {
    //             if(hour < 10)
    //             "0" + hour
    //             else
    //             "" + hour
    //         }
    //         var m = {
    //             if(minute < 10)
    //             "0" + minute
    //             else
    //             "" + minute
    //         }
    //         h + ":" + m
    //     }
    //     def increase(bonusHour: Int, bonusMinute: Int): Int = {
    //         var bHour = bonusHour
    //         minute += bonusMinute
    //         if(minute >= 60){
    //             minute %= 60
    //             bHour +=1
    //         }
    //         hour += bHour
    //         if(hour >= 24){
    //             hour %= 24
    //             1
    //         }
    //         else{
    //             0
    //         }
    //     }
    // }

    class InvalidProbabilityException(s:String) extends Exception(s){}  


    class Flight(private val direction: Direction.Value, private val status: Status.Value, private var freePlaces: Int, private val date: Date,
     private val changeTimeProb: Double, private val runway: Runaways.Value, private val price: Double){
        if(freePlaces <= 0){
            throw new IllegalArgumentException
        }
        if(changeTimeProb > 1 || changeTimeProb < 0){
            throw new InvalidProbabilityException("wrong probability")
        }
        val toAdd = 200

        private val firstClassPrice = price + toAdd
        private val places = freePlaces

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
        def getDirection(): Direction.Direction = direction    
        def getProbability(): Double = changeTimeProb
        override def toString() = {
            "Direction: " + direction + ", date: " + date + ", status: " + status + ", places: " + places + ", free: " + freePlaces + ", price:" + price + ", first class price: " + firstClassPrice
        }
    }

    class FlightGenerator(){
        val minPrice = 50
        val priceBracket = 300
        def generateFlight(): Flight = {
            val today = new Date()
            var time = today.getTime()
            var seed: Long = scala.math.pow(10,10).toLong
            var toAdd = Random.nextLong(seed)
            println(toAdd) 
            val data = new Date(time + toAdd)
            for( i <- Sizes.values){
                println(i)
            }
            val places = Sizes(Random.nextInt(Sizes.maxId)).toString().toInt
            val probability = Random.nextDouble()
            val direction = Direction(Random.nextInt(Direction.maxId))
            val inOut = Status(Random.nextInt(2))
            val runaway = Runaways(Random.nextInt(Runaways.maxId))
            val price = (minPrice + Random.nextInt(priceBracket)).toDouble
            new Flight(direction,inOut,places,data,probability,runaway,price)
        }
        
        def generateTimetable(amount:  Int){
            
        }

    }

    object FlightGenerator {
        def apply() = new FlightGenerator
    }


    // object ApplFlight {
    //     def main(agrs: Array[String]) {
    //         val data = new Date(1997, 12, 14)
    //         // println(data.howFarAway())
    //         val direction = London
    //         val inOut = Status.Arriving
    //         val newFlight = new Flight(direction,inOut,20,data,0.3,5,54.43)
    //         println(newFlight.getPrice())
    //         println(newFlight.isArriving())  
    //         println(newFlight.isLeaving())
    //         println(newFlight.getDate())
    //         println(newFlight.getPlacesNumber())
    //         println(newFlight.getDirection())
    //     }
    // }
}



// cel
// przylot/wylot
// liczba miejsc
// termin
// prawdopodobieństwo przełożenia/odwołania
// pas
// ceny


// przylot
// odlot
// opóźnij
// odwołaj
