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
            for(flight <- todaysFlights){
                println(flight)
                count += 1
                places += flight.getTakenPlaces()
                income += flight.getCurrIncome()
                sumDelay += flight.getDelay()
                countDelay +=1
            }
            if(countDelay == 0){
                countDelay+=1
            }
            val res: String = "\n\nDay: "+  (day.getYear()+ 1900) + ".0" +  (day.getMonth() + 1) + "." + day.getDate()+ "  Statistics: Flights number: "+  count+ " clients number: "+ places+ " income: "+ income + "0" + " average delay in hours: " + scala.math.BigDecimal(sumDelay/countDelay).setScale(3, BigDecimal.RoundingMode.HALF_UP)
            println(res)
            res
        }
        
    }

    object Observer {
        def apply(engine: Engine) = new Observer(engine)
    }
}