package app.pwdgen

import org.junit.Assert.*
import org.junit.Test
import java.util.regex.Pattern

class PasswordGeneratorTest {

    @Test
    fun `Password should contain no special characters`() {
        val pwdGenerator = PasswordGenerator(true, true, true, false)
        for (i in 8..100) {
            val pwd = pwdGenerator.generate(i)
            val p = Pattern.compile("(?![$SPECIAL]).*$")
            val m = p.matcher(pwd)
            val b = m.matches()
            assertTrue("Password: $pwd, Length: $i", b)
        }
    }

    @Test
    fun `Password should be lower case only`() {
        val pwdGenerator = PasswordGenerator(true, false, true, true)
        for (i in 8..100) {
            val pwd = pwdGenerator.generate(i)
            val p = Pattern.compile("(?![$UPPER]).*$")
            val m = p.matcher(pwd)
            val b = m.matches()
            assertTrue("Password: $pwd, Length: $i", b)
        }
    }

    @Test
    fun `Password should be alphabets only`() {
        val pwdGenerator = PasswordGenerator(true, true, false, false)
        for (i in 8..100) {
            val pwd = pwdGenerator.generate(i)
            val p = Pattern.compile("(?![$DIGITS|$SPECIAL]).*$")
            val m = p.matcher(pwd)
            val b = m.matches()
            assertTrue("Password: $pwd, Length: $i", b)
        }
    }
}