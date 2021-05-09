object Status extends Enumeration{
    type InOut = Value
    val Leaving, Arriving, Staying, Left = Value
}

import Direction._

import Status._

class Data(private val year: Int, private val month: Int, private val day: Int){
    override def toString(): String = "Y: " + year + " M: " + month + " D: " + day
}

class Hour(private var hour: Int, private var minute: Int){
    override def toString(): String = {
        var h = {
            if(hour < 10)
            "0" + hour
            else
            "" + hour
        }
        var m = {
            if(minute < 10)
            "0" + minute
            else
            "" + minute
        }
        h + ":" + m
    }
    def increase(bonusHour: Int, bonusMinute: Int): Int = {
        var bHour = bonusHour
        minute += bonusMinute
        if(minute >= 60){
            minute %= 60
            bHour +=1
        }
        hour += bHour
        if(hour >= 24){
            hour %= 24
            1
        }
        else{
            0
        }
    }
}

class InvalidProbabilityException(s:String) extends Exception(s){}  


class Flight(private val direction: Direction, private val in_out: InOut, private var places: Int, private val data: Data,
 private val hour: Hour, private val changeTimeProb: Double, private val runway: Int, private val price: Double){
    if(places <= 0){
        throw new IllegalArgumentException
    }
    if(changeTimeProb > 1 || changeTimeProb < 0){
        throw new InvalidProbabilityException("wrong probability")
    }

    def getPrice(): Double = price
    def isArriving(): Boolean  = (in_out == Status.Arriving)
    def isLeaving(): Boolean  = (in_out == Status.Leaving)
    def getDate(): Data = data
    def getHour(): Hour = hour
    def res_places(n: Int): Unit ={
        places -= n
    }
    def getPlacesNumber(): Int = places
    def getDirection(): Direction = direction;
}


object Appl {
  def main(agrs: Array[String]) {
    val data = new Data(1997, 12, 14)
    val direction = Direction.London
    val inOut = Status.Arriving
    val hour = new Hour(23,57)
    val newFlight = new Flight(direction,inOut,20,data,hour,0.3,5,54.43)
    println(newFlight.getPrice())
    println(newFlight.isArriving())  
    println(newFlight.isLeaving())
    println(newFlight.getDate())
    println(newFlight.getPlacesNumber())
    println(newFlight.getDirection())
    println(newFlight.getHour())
    println(hour)
    val day = hour.increase(2,4)
    println(day +" godzina: " + hour)
  }
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
