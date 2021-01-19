
import org.junit.Test
import org.junit.Assert
import sun.security.krb5.KrbException.errorMessage

class TestNamedArguments {

    @Test(timeout = 1000)
    fun testJoinToString() {
        Assert.assertEquals(errorMessage("joinOptions"), "[yes, no, may be]", joinOptions(listOf("yes", "no", "may be")))
    }

    private fun errorMessage(s: String): String {
    return "lol"
    }

}