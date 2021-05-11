package Flight{
    import Enums.Direction._

    import java.util.Calendar;
    import java.util.Date

    object Status extends Enumeration{
        type InOut = Value
        val Leaving, Arriving, Staying, Left = Value
    }


    import Status._


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


    class Flight(private val direction: Direction, private val in_out: InOut, private var places: Int, private val date: Date,
     private val changeTimeProb: Double, private val runway: Int, private val price: Double){
        if(places <= 0){
            throw new IllegalArgumentException
        }
        if(changeTimeProb > 1 || changeTimeProb < 0){
            throw new InvalidProbabilityException("wrong probability")
        }

        def getPrice(): Double = price
        def isArriving(): Boolean  = (in_out == Status.Arriving)
        def isLeaving(): Boolean  = (in_out == Status.Leaving)
        def getDate(): Date = date
        def res_places(n: Int): Unit ={
            if(n > places){
                throw new IllegalArgumentException("There is not enough places")
            }
            places -= n
        }
        def getPlacesNumber(): Int = places
        def getDirection(): Direction = direction;
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
