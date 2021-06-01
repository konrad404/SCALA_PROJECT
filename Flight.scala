import Enums.Direction
import Enums.Sizes
import java.util.Calendar
import java.util.Date
import scala.util.Random
import scala.math.BigDecimal
import Observer._

package Flight{


    class InvalidProbabilityException(s:String) extends Exception(s){}  


    class Flight(private val direction: Direction.Value, private val status: Status.Value, private var places: Int, private var date: Date,
     private val changeTimeProb: Double, private val price: Double, private val observer: Observer){
        if(places <= 0){
            throw new IllegalArgumentException
        }
        if(changeTimeProb > 1 || changeTimeProb < 0){
            throw new InvalidProbabilityException("wrong probability")
        }

        val toAdd = 200
        private val firstClassPrice : Double  = price + toAdd
        private var freeEconomicPlaces = (places * 0.7).toInt
        private var freeBusinessPlaces = places - freeEconomicPlaces 
        private var takenPlaces = 0
        private var takenBusinessPlaces = 0
        private var flightDelay: Double = 0.0
        private val id = Flight.getNewId()
        

        def getPrice(): Double = price

        def getFirstClassPrice(): Double = firstClassPrice

        def getDate(): Date = date

        def getDelay(): Double = flightDelay

        def resPlaces(n: Int, isBusiness: Boolean): Unit ={
            if(isBusiness){
                if(n > freeBusinessPlaces){
                    throw new IllegalArgumentException("There is not enough places")
                }
                else{
                    takenBusinessPlaces += n
                    freeBusinessPlaces -= n
                }
            }
            else {
                if(n > freeEconomicPlaces){
                    throw new IllegalArgumentException("There is not enough places")
                }
                else{
                    takenPlaces += n
                    freeEconomicPlaces -= n
                }
            }
        }

        def deleteRes(n: Int, isBusiness: Boolean): Unit = {
            if(isBusiness){
                takenBusinessPlaces -= n
                freeBusinessPlaces+=n
            }
            else{
                takenPlaces-=n
                freeEconomicPlaces+=n
            }
        }

        def fly(): Unit = {
            if(Random.nextDouble() < changeTimeProb){
                val delay = ((scala.math.abs(Random.nextGaussian()))/24 * 86400000).toLong
                date = new Date(date.getTime() + delay)
                flightDelay = delay.toDouble/(3600000)
            }
            observer.flightTookPlace(this)
        }

        def getFreePlacesNumber(): Int = freeEconomicPlaces + freeBusinessPlaces

        def getFreeEconomicPlacesNumber(): Int = freeEconomicPlaces

        def getFreeBusinessPlacesNumber(): Int = freeBusinessPlaces

        def getCurrIncome(): Double = takenPlaces * price + takenBusinessPlaces * firstClassPrice

        def getTakenPlaces(): Int = takenPlaces + takenBusinessPlaces

        def getDirection(): Direction.Direction = direction   

        def getProbability(): Double = changeTimeProb

        def getId(): Int = id

        def getFillPercent(): Double = ((takenBusinessPlaces + takenPlaces).toDouble / places)

        override def toString() = {
            "ID: " + id + "       \t Direction: " + direction + "\t date: " + date + "\t status: " + status + "\t places: " + places + " \t free: " + (freeEconomicPlaces + freeBusinessPlaces) + "\t price:" + price + "0\t first class price: " + firstClassPrice + "0"
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
            val probability = Random.nextDouble()/3
            val direction = Direction(Random.nextInt(Direction.maxId))
            val inOut = Status(Random.nextInt(2))
            val price = (minPrice + Random.nextInt(priceBracket)).toDouble

            new Flight(direction,inOut,places,data,probability,price, observer)
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
