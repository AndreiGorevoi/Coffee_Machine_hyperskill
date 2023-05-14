package machine

fun main() {
    val coffeeMachine = CoffeeMachine()

    while (true) {
        coffeeMachine.processAction()
        coffeeMachine.handleInput(readln())
    }

}
