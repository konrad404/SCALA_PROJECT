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

package Observer{
        class Observer(val engine: Engine){
        private var pastFlights:  Array[Flight] = Array()
        private var flightsNumber: Int = 0

        def flightTookPlaace(flight: Flight) : Unit = {
            pastFlights = pastFlights :+ flight
            flightsNumber += 1
        }


        def getStatisticsFromDay(day: Date) : String = {
            var todaysFlights: Array[Flight] = Array()
            for(flight <- pastFlights){
                if (day.getDate() == flight.getDate().getDate() && day.getMonth() == flight.getDate().getMonth() && day.getYear() == flight.getDate().getYear()){
                    todaysFlights = todaysFlights :+ flight
                }
            }
            var sumDelay = 0.0
            var countDelay = 0
            var count = 0
            var places = 0 
            var income = 0.0
            var fillPercentage = 0.0
            var res: String = ""
            for(flight <- todaysFlights){
                println(flight)
                res += flight.toString()
                res += "\n"
                count += 1
                places += flight.getTakenPlaces()
                income += flight.getCurrIncome()
                sumDelay += flight.getDelay()
                countDelay +=1
                fillPercentage += flight.getFillPercent()
            }
            if(countDelay == 0){
                countDelay+=1
            }
            fillPercentage = 100 * fillPercentage / countDelay
            res += "\n\nDay: "+  (day.getYear()+ 1900) + ".0" +  (day.getMonth() + 1) + "." + day.getDate()+ "  Statistics: Flights number: "+  count+ " clients number: "+ places+ " income: "+ income + "0" + " average delay in hours: " + scala.math.BigDecimal(sumDelay/countDelay).setScale(2, BigDecimal.RoundingMode.HALF_UP) + " average percentage of taken places in flights: " + scala.math.BigDecimal(fillPercentage).setScale(2, BigDecimal.RoundingMode.HALF_UP)
            println(res)
            res
        }
        
        def getEndStatisticsToDay(day: Date) : String = {
            var todaysFlights: Array[Flight] = Array()
            for(flight <- pastFlights){
                if (flight.getDate().compareTo(day) <= 0){
                    todaysFlights = todaysFlights :+ flight
                }
            }
            var sumDelay = 0.0
            var countDelay = 0
            var count = 0
            var places = 0 
            var income = 0.0
            var fillPercentage = 0.0
            for(flight <- todaysFlights){
                count += 1
                places += flight.getTakenPlaces()
                income += flight.getCurrIncome()
                sumDelay += flight.getDelay()
                countDelay +=1
                fillPercentage += flight.getFillPercent()
            }
            if(countDelay == 0){
                countDelay+=1
            }
            fillPercentage = 100 * fillPercentage / countDelay
            val res: String = ("Flights number: " +  count + "\nclients number: "+ places+ "\nincome: "+ income + "0" + "\naverage delay in hours: " 
            + scala.math.BigDecimal(sumDelay/countDelay).setScale(2, BigDecimal.RoundingMode.HALF_UP) 
            + "\naverage percentage of taken places in flights: " + scala.math.BigDecimal(fillPercentage).setScale(2, BigDecimal.RoundingMode.HALF_UP))
            res
        }

    }

    object Observer {
        def apply(engine: Engine) = new Observer(engine)
    }
}