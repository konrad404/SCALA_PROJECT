package Observer{

    import Engine._
    import Flight._

    class Observer(val engine: Engine){
        private var flights:  Array[Flight] = Array()
        private var flightsNumber: Int = 0

        
    }

    object Observer {
        def apply(engine: Engine) = new Observer(engine)
    }
}