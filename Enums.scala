package Enums{
object Direction extends Enumeration {
    type Direction = Value
    val London, Madrit, Berlin, Minsk, Dubai, Roma, Paris = Value
}

object TripReason extends Enumeration {
    type TripReason = Value
    val Vacation, Work, Visit = Value
}

object Sizes extends Enumeration {
    val Small = Value("80")
    val Medium = Value("160")
    val Big = Value("220")
}
object Status extends Enumeration{
        type Status = Value
        val Leaving, Arriving, Staying, Left = Value
}
}