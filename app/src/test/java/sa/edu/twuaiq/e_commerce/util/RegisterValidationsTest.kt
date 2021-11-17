package sa.edu.twuaiq.e_commerce.util

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegisterValidationsTest {

    private lateinit var validator: RegisterValidations

    @Before
    fun setup() {
        validator = RegisterValidations()
    }

    @Test
    fun emailIsValidWithInvalidEmailThenReturnFalseValue() {
        val validation = validator.emailIsValid("test-dd.com")

        assertEquals(false ,validation)
    }

    @Test
    fun emailIsValidWithValidEmailThenReturnTrueValue() {
        val validation = validator.emailIsValid("test@test.com")

        assertEquals(true , validation)
    }


    @Test
    fun passwordIsValidWithInvalidPasswordThenReturnFalseValue() {
        val validation = validator.passwordIsValid("73")

        assertEquals(false, validation)
    }


    @Test
    fun passwordIsValidWithValidPasswordThenReturnTrueValue() {
        val validation = validator.passwordIsValid("Tu@2185697")

        assertEquals(true, validation)
    }

}