import java.util.*
import kotlin.random.Random
import kotlin.system.exitProcess

private const val MIN_DICE_VALUE = 1
private const val MIN_SUCCESS_DICE_VALUE = 5
private const val MAX_DICE_VALUE = 6

fun main() {
    println("Начать игру: 1\nВыход: 2")
    inputInt(range = 1..2, errorMessage = "Введите коректное значение") { value ->
        when (value) {
            1 -> startGame()
            2 -> exitGame()
        }
    }
}

fun startGame() {
    print("Введите максимальное допустипое значение здоровья существ (от 30 до 100): ")
    inputInt(range = 30..100, errorMessage = "Введите коректное значение") { maxHealth ->
        val player = Player(maxHealth)
        val monster = Monster(maxHealth)
        printHealth(player, monster)
        do {
            gameTurn(player, monster)
        } while (player.health > 0 && monster.health > 0)
        if (monster.health <= 0) {
            println("Вы победили!")
        } else {
            println("Вы проиграли!")
        }
        exitGame()
    }
}

fun printHealth(player: Player, monster: Monster) {
    println("Очки здоровья игрока: ${player.health}")
    println("Очки здоровья монстра: ${monster.health}")
}

fun gameTurn(player: Player, monster: Monster) {
    println("------------------------------------------")
    println("Ударить монстра: 1")
    println("Использовать исцеление: 2")
    inputInt(range = 1..2, errorMessage = "Введите корректное значение") { value ->
        when (value) {
            1 -> {
                if (throwDice(player, monster)) {
                    println("Вы нанесли ${player.attack(monster)} урона.")
                } else {
                    println("Вы промахнулись.")
                }
                if (throwDice(monster, player)) {
                    println("Монстр нанес вам ${monster.attack(player)} урона.")
                } else {
                    println("Монстр промахнулся.")
                }
                printHealth(player, monster)
            }
            2 -> {
                player.heal()?.let { healCounter ->
                    println("Вы восстановили здоровье. Новое значение - ${player.health}. Осталось $healCounter зелий.")
                } ?: run {
                    println("Вы исчерпали зелья восстановления.")
                }
            }
        }
    }
}

fun throwDice(attackingCreature: Creature, defencingCreature: Creature): Boolean {
    val attackModifier = attackingCreature.attack - defencingCreature.defense + 1
    return List(
        if (attackModifier >= 1) attackModifier else 1
    ) {
        Random.nextInt(MIN_DICE_VALUE, MAX_DICE_VALUE)
    }.any { it in MIN_SUCCESS_DICE_VALUE..MAX_DICE_VALUE }
}

fun inputInt(range: IntRange, errorMessage: String, onSuccess: (Int) -> Unit) {
    var input: Int?
    do {
        input = Scanner(System.`in`).next().toIntOrNull()
        if (input !in range) println(errorMessage)
    } while (input !in range)
    if (input != null) {
        onSuccess(input)
    }
}

fun exitGame(): Unit = exitProcess(1)
