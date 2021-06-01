import Enums._
import Flight._
import Engine._
import java.util.Calendar;
import java.util.Date
import scala.util.Random


object Appl1 {
        def main(agrs: Array[String]) {
            val engine: Engine = new Engine()
            engine.work(20)
        }
    }