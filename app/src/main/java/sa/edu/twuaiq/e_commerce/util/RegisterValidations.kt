package sa.edu.twuaiq.e_commerce.util

import java.util.regex.Pattern



class RegisterValidations {

    /**
     * Regular Expressions are a fundamental part of almost every programming language and Kotlin is no exception to it.
     * In Kotlin, the support for regular expression is provided through Regex class.
     * An object of this class represents a regular expression, that can be used for string matching purposes.
     * */

    private val REGEX_PASSWORD = "^(?=.*[0-9])" +  // a digit must occur at least once
            "(?=.*[a-z])" +  // a lower case letter must occur at least once
            "(?=.*[A-Z])" +  // an upper case letter must occur at least once
            "(?=.*[!@#\\$%\\^&\\*])" +  // a special character must occur at least once
            "(?=\\S+$)" +  // no whitespace allowed in the entire string
            ".{8,}$" // anything, at least eight places though

    private val REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"


    fun emailIsValid(email: String) : Boolean {

        /**
         * A compiled representation of a regular expression.

        A regular expression, specified as a string, must first be compiled into an instance of this class.
        The resulting pattern can then be used to create a Matcher object that can match arbitrary against the regular expression.
        All of the state involved in performing a match resides in the matcher,
        so many matchers can share the same pattern.
         * */
        val pattern = Pattern.compile(REGEX_EMAIL)

        val matcher = pattern.matcher(email)

        /**
         * matches() – This function returns a boolean indicating whether the input string completely matches the pattern or not.
         * */
        return matcher.matches()
    }

    fun passwordIsValid(password: String) : Boolean {
        val pattern = Pattern.compile(REGEX_PASSWORD)

        val matcher = pattern.matcher(password)

        return matcher.matches()
    }
}