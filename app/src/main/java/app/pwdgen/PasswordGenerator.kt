package app.pwdgen

import java.util.*

const val LOWER = "abcdefghijklmnopqrstuvwxyz"
const val UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
const val DIGITS = "0123456789"
const val SPECIAL = "!@#$%&*()_+\\-=|,./?><"

class PasswordGenerator(private val lower: Boolean = true,
                        private val upper: Boolean = true,
                        private val digits: Boolean = true,
                        private val special: Boolean = true) {

    /**
     * This method will generate a password depending the use* properties you
     * define. It will use the categories with a probability. It is not sure
     * that all of the defined categories will be used.
     *
     * @param length the length of the password you would like to generate.
     * @return a password that uses the categories you define when constructing
     * the object with a probability.
     */
    fun generate(length: Int = 8): String {
        // Argument Validation.
        if (length <= 0) {
            return ""
        } else if (!lower && !upper && !digits && !special) {
            return "¯\\_(ツ)_/¯"
        }

        // Variables.
        val password = StringBuilder(length)
        val random = Random(System.nanoTime())

        // Collect the categories to use.
        val charCategories = ArrayList<String>(4)
        if (lower) {
            charCategories.add(LOWER)
        }
        if (upper) {
            charCategories.add(UPPER)
        }
        if (digits) {
            charCategories.add(DIGITS)
        }
        if (special) {
            charCategories.add(SPECIAL)
        }

        // Build the password.
        for (i in 0 until length) {
            val charCategory = charCategories[random.nextInt(charCategories.size)]
            val position = random.nextInt(charCategory.length)
            password.append(charCategory[position])
        }
        return String(password)
    }
}