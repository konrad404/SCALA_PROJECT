import Flight._
import Customer._
import Enums._

package Engine{

class Engine(){
    private var flights : Array[Flight] = Array() // to się wygeneruje

    def getFlights():Array[Flight]={this.flights}
}
}