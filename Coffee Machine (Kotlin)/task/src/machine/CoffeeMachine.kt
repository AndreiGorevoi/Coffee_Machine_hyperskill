package machine

import kotlin.system.exitProcess

const val ESPRESSO_WATER = 250
const val ESPRESSO_MILK = 0
const val ESPRESSO_BEANS = 16
const val ESPRESSO_PRICE = 4

const val LATTE_WATER = 350
const val LATTE_MILK = 75
const val LATTE_BEANS = 20
const val LATTE_PRICE = 7

const val CAPPUCCINO_WATER = 200
const val CAPPUCCINO_MILK = 100
const val CAPPUCCINO_BEANS = 12
const val CAPPUCCINO_PRICE = 6

const val CHOSE = "Chose"
const val BUY = "Buy"
const val FILL = "Fill"
const val FILLING_WATER = "Fill water"
const val FILLING_MILK = "Fill milk"
const val FILLING_COFFEE = "Fill coffee"
const val FILLING_CUPS = "Fill cups"
const val TAKE = "Take"
const val REMAINING = "Remaining"
const val CHOOSING_COFFEE_TYPE = "Choosing coffee type"

class CoffeeMachine(
    private var water: Int = 400,
    private var milk: Int = 540,
    private var beans: Int = 120,
    private var cash: Int = 550,
    private var disposableCups: Int = 9,
    private var state: String = CHOSE
) {
    private fun printState() =
        println(
            """
        The coffee machine has:
        $water ml of water
        $milk ml of milk
        $beans g of coffee beans
        $disposableCups disposable cups
        $${cash} of money
    """.trimIndent()
        )

    private fun makeACoffee(typeStr: String) {
        val type = when (typeStr) {
            "1" -> TypeOfCoffee.ESPRESSO
            "2" -> TypeOfCoffee.LATTE
            "3" -> TypeOfCoffee.CAPPUCCINO
            else -> TypeOfCoffee.ESPRESSO
        }
        if (checkResourcesForMakingCoffee(type)) println("I have enough resources, making you a coffee!") else return
        when (type) {
            TypeOfCoffee.CAPPUCCINO -> {
                water -= CAPPUCCINO_WATER
                milk -= CAPPUCCINO_MILK
                beans -= CAPPUCCINO_BEANS
                cash += CAPPUCCINO_PRICE
                disposableCups -= 1
            }

            TypeOfCoffee.ESPRESSO -> {
                water -= ESPRESSO_WATER
                milk -= ESPRESSO_MILK
                beans -= ESPRESSO_BEANS
                cash += ESPRESSO_PRICE
                disposableCups -= 1
            }

            TypeOfCoffee.LATTE -> {
                water -= LATTE_WATER
                milk -= LATTE_MILK
                beans -= LATTE_BEANS
                cash += LATTE_PRICE
                disposableCups -= 1
            }
        }

    }

    private fun take() {
        println("I gave you $${cash}")
        cash = 0
    }

    private fun checkResourcesForMakingCoffee(type: TypeOfCoffee): Boolean {
        when (type) {
            TypeOfCoffee.CAPPUCCINO -> {
                if (water < CAPPUCCINO_WATER) {
                    println("Sorry, not enough water!")
                    return false
                } else if (milk < CAPPUCCINO_MILK) {
                    println("Sorry, not enough milk!")
                    return false
                } else if (beans < CAPPUCCINO_BEANS) {
                    println("Sorry, not enough beans!")
                    return false
                } else if (disposableCups < 1) {
                    println("Sorry, not enough cups!")
                    return false
                }
            }

            TypeOfCoffee.LATTE -> {
                if (water < LATTE_WATER) {
                    println("Sorry, not enough water!")
                    return false
                } else if (milk < LATTE_MILK) {
                    println("Sorry, not enough milk!")
                    return false
                } else if (beans < LATTE_BEANS) {
                    println("Sorry, not enough beans!")
                    return false
                } else if (disposableCups < 1) {
                    println("Sorry, not enough cups!")
                    return false
                }
            }

            TypeOfCoffee.ESPRESSO -> {
                if (water < ESPRESSO_WATER) {
                    println("Sorry, not enough water!")
                    return false
                } else if (beans < ESPRESSO_BEANS) {
                    println("Sorry, not enough beans!")
                    return false
                } else if (disposableCups < 1) {
                    println("Sorry, not enough cups!")
                    return false
                }
            }
        }
        return true
    }

    fun handleInput(input: String) {
        when(state) {
            CHOSE -> {
                when(input) {
                    "buy" -> state = BUY;
                    "fill" -> state = FILL;
                    "take" -> state = TAKE;
                    "remaining"-> state = REMAINING
                    "exit"-> exitProcess(200)
                }
            }
            CHOOSING_COFFEE_TYPE -> {
                when(input) {
                    "back" -> state = CHOSE
                    else -> {
                        makeACoffee(input)
                        state = CHOSE
                    }
                }
            }
            FILLING_WATER -> {
                water += input.toInt()
                state = FILLING_MILK
            }
            FILLING_MILK -> {
                milk += input.toInt()
                state = FILLING_COFFEE
            }
            FILLING_COFFEE -> {
                beans += input.toInt()
                state = FILLING_CUPS
            }
            FILLING_CUPS -> {
                disposableCups += input.toInt()
                state = CHOSE
            }
        }
    }

    private fun printStartingText() = println("Write action (buy, fill, take, remaining, exit):")

    fun processAction() {
        when(state) {
            CHOSE -> {
                printStartingText()
            }
            REMAINING -> {
                printState()
                state = CHOSE
                printStartingText()
            }
            BUY -> {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:")
                state = CHOOSING_COFFEE_TYPE
            }
            TAKE -> {
                take()
                state = CHOSE
                printStartingText()
            }
            FILL -> {
                println("Write how many ml of water you want to add:")
                state = FILLING_WATER
            }
            FILLING_MILK -> {
                println("Write how many ml of milk you want to add:")
            }
            FILLING_COFFEE -> {
                println("Write how many grams of coffee beans you want to add:")
            }
            FILLING_CUPS -> {
                println("Write how many disposable cups you want to add:")
            }
        }
    }
}