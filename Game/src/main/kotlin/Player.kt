import kotlin.math.roundToInt

class Player(maxValueHealthPlayer: Int) : Creature(maxValueHealth = maxValueHealthPlayer) {

    private var healCounter = 4

    fun heal(): Int? {
        return if (healCounter > 0) {
            val newHealthValue = health + (maxValueHealth * 0.3).roundToInt()
            health = if (newHealthValue <= maxValueHealth) newHealthValue else maxValueHealth
            --healCounter
        } else {
            null
        }
    }
}
