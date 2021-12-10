package Model

class User {
    var name: String? = null
    var number: String? = null

    constructor(name: String?, number: String?) {
        this.name = name
        this.number = number
    }

    constructor()

    override fun toString(): String {
        return "User(name=$name, number=$number)"
    }

}