package Enums{
object Direction extends Enumeration {
    type Direction = Value
    val London, Madrit, Barcelona = Value
}

object TripReason extends Enumeration {
    type TripReason = Value
    val Vacation, Work, Visit = Value
}
}