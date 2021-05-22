package Enums{
object Direction extends Enumeration {
    type Direction = Value
    val London, Madrit, Barcelona, Berlin, New_York, Dubai, Roma, Paris = Value
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
object Runaways extends Enumeration {
    type Runaways = Value
    val One, Two, Three, Four, Five, Six, Seven, Eight = Value
}
object Status extends Enumeration{
        type Status = Value
        val Leaving, Arriving, Staying, Left = Value
}
}