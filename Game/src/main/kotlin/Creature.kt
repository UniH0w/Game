import kotlin.random.Random

private const val MAX_ATTACK = 30
private const val MAX_DEFENSE = 30

abstract class Creature(val maxValueHealth:Int) {

     var health =  maxValueHealth
        set(value) {
            field = if (value < 0) 0 else value
        }

    var attack = Random.nextInt(1, MAX_ATTACK)
    var defense = Random.nextInt(1, MAX_DEFENSE)
    private var damage = Random.nextInt(1, maxValueHealth)

    fun attack(creature: Creature): Int {
        val currentDamage = Random.nextInt(1, damage)
        creature.health -= currentDamage
        return currentDamage
    }
}
