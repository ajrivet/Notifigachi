package vet.alecri.notifigachi.foreground

// Game.kt is the implementation of the actual game state at any given point in time

class Game {

    // Game state variables
    var name = "guy"
    var food:Int        = 10
    var sleepiness:Int  = 10
    var cleanliness:Int = 10
    var activity:Int    = 10

    fun isAlive():Boolean       { return (this.food > 0) }
    fun isTired():Boolean       { return (this.sleepiness < 5) }
    fun isHungry():Boolean      { return (this.food in 1..5)}
    fun isClean():Boolean       { return (this.cleanliness > 5)}
    fun wantsToPlay():Boolean   { return (this.activity > 5)}

    fun isHappy():Int {
        var x = this.food + this.sleepiness + this.activity + this.cleanliness
        var happiness:Int
        when (x) {
            in 30..21 -> {
                happiness = HAPPINESS_HIGH
            }
            in 20..11 -> {
                happiness = HAPPINESS_MED
            }
            else -> {
                happiness = HAPPINESS_LOW
            }
        }
        return happiness
    }

    fun feed()  { this.food += 3 }
    fun sleep() { this.sleepiness += 3 }
    fun clean() { this.cleanliness += 3 }
    fun play()  { this.activity += 3 }

    // Add a bit of randomness to the stat changes
    // Tick can sometimes reduce only some stat and not others
    fun tick() {

        val rnd = (0..15).random()

        when(rnd) {
            0 ->            { this.food-- }
            1 ->            { this.sleepiness-- }
            2 ->            { this.activity-- }
            in 3..5 ->      { this.food--; this.sleepiness--}
            in 6..8 ->      { this.food--; this.activity--}
            in 9..11 ->     { this.sleepiness--; this.activity--}
            in 12..15 ->    { this.food--; this.sleepiness--; this.activity--}
        }

        // Poop here maybe
        // Neat, no need for ternary
//        if((0..15).random() % 2 == 0)

    }



    companion object {
        val HAPPINESS_HIGH = 2
        val HAPPINESS_MED = 1
        val HAPPINESS_LOW = 0
    }
}