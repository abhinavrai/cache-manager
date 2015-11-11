/*
 * Copyright (c) 2014-2015. Author or original authors. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.itrixlabs.cache.util;

/**
 * <p>
 * <b><i>Internal class for making application logic related assertions.</i></b>
 * </p>
 * <p>
 * Utility methods provided by this class may be used in the application (however, extending this
 * class itself is not allowed) for making clean assertions and avoiding condition blocks. Designed
 * for making application logic look clean and work fast.
 * </p>
 * <p>
 * However, it is undesirable to instantiate (and not required too) this class anywhere in the
 * application.
 * </p>
 * <p>
 * Further, extension is also not allowed as it may have side effects (not to mention, methods of
 * this class use reflective code to perform some difficult operations).
 * </p>
 * 
 * @author Abhinav Rai
 * @since November 11<sup>th</sup>, 2015
 * 
 */
public final class Assert {

    /**
     * <p>
     * Tests the given argument for <code>null</code> identity and throws an
     * {@link IllegalArgumentException} with the provided message if the argument is
     * <code>null</code>.
     * </p>
     * 
     * @param arg
     *            the object for <code>null</code> assertion
     * @param message
     *            the message to be returned with exception if assertion fails
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertNotNull(Object arg, String message) throws IllegalArgumentException {
	if (arg == null)
	    throw new IllegalArgumentException(message);
    }

    /**
     * <p>
     * Tests the given argument for <code>null</code> identity and throws an
     * {@link IllegalArgumentException} with a default message if the argument is <code>null</code>.
     * </p>
     * 
     * @param arg
     *            the object for <code>null</code> assertion
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertNotNull(Object arg) throws IllegalArgumentException {
	assertNotNull(arg, "[Assertion Error]: Argument is null");
    }

    /**
     * <p>
     * Tests the given arguments for <code>null</code> identity and throws an
     * {@link IllegalArgumentException} with a default message if any argument is <code>null</code>.
     * </p>
     * 
     * @param args
     *            the object for <code>null</code> assertion
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertNotNull(Object... args) {
	for (int i = 0; i < args.length; i++)
	    assertNotNull(args[i], "[Assertion Error]: Atleast one argument is null");
    }

    /**
     * <p>
     * Tests the given arguments for equality based upon the {@link #equals(Object)} method of the
     * class in focus and throws an {@link IllegalArgumentException} with the provided message if
     * the arguments are not equal. An {@link IllegalArgumentException} exception is also thrown
     * when any of the arguments are null or the objects' classes are not assignable from each
     * other.
     * </p>
     * <p>
     * <i>It is highly recommended to override the {@link #equals(Object)} and {@link #hashCode()}
     * methods in the class for which object comparison is desired as {@link Object} class'
     * {@link #equals(Object)} method returns equality on the basis of equal identity only; which
     * may not be always desirable.</i>
     * </p>
     * 
     * @param arg1
     *            the first object argument for equality assertion
     * @param arg2
     *            the second object argument for equality assertion
     * @param message
     *            the message to be returned with the exception if assertion fails
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertEquals(Object arg1, Object arg2, String message) {
	assertNotNull(arg1, arg2);
	if (arg1.getClass().isAssignableFrom(arg2.getClass()))
	    if (!(arg1.equals(arg2)))
		throw new IllegalArgumentException(message);
    }

    /**
     * <p>
     * Tests the given arguments for equality based upon the {@link #equals(Object)} method of the
     * class in focus and throws an {@link IllegalArgumentException} with a default message if the
     * arguments are not equal. An {@link IllegalArgumentException} exception is also thrown when
     * any of the arguments are null or the objects' classes are not assignable from each other.
     * </p>
     * <p>
     * <i>It is highly recommended to override the {@link #equals(Object)} and {@link #hashCode()}
     * methods in the class for which object comparison is desired as {@link Object} class'
     * {@link #equals(Object)} method returns equality on the basis of equal identity only; which
     * may not be always desirable.</i>
     * </p>
     * 
     * @param arg1
     *            the first object argument for equality assertion
     * @param arg2
     *            the second object argument for equality assertion
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertEquals(Object arg1, Object arg2) {
	assertEquals(arg1, arg2, "[Assertion Error]: Arguments aren't equal");
    }

    /**
     * <p>
     * Tests the given arguments for equality based upon the {@link #equals(Object)} method of the
     * class in focus and throws an {@link IllegalArgumentException} with a default message if the
     * arguments are not equal. An {@link IllegalArgumentException} exception is also thrown when
     * any of the arguments are null or the objects' classes are not assignable from each other.
     * </p>
     * <p>
     * <i>It is highly recommended to override the {@link #equals(Object)} and {@link #hashCode()}
     * methods in the class for which object comparison is desired as {@link Object} class'
     * {@link #equals(Object)} method returns equality on the basis of equal identity only; which
     * may not be always desirable.</i>
     * </p>
     * 
     * @param args
     *            the object arguments for equality assertion
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertEquals(Object... args) {
	for (int i = 0; i < (args.length - 1); i++)
	    assertEquals(args[i], args[i + 1],
		    "[Assertion Error]: Atleast one argument is different");
    }

    /**
     * <p>
     * Tests the given argument of type {@link String}, {@link StringBuilder} or
     * {@link StringBuffer} for empty (but not <code>null</code>) value and throws an
     * {@link IllegalArgumentException} with the provided message if the argument is an empty string
     * literal or representation. An {@link IllegalArgumentException} exception is also thrown if
     * the argument is <code>null</code> or the object is not an instance of any one of the three
     * popular string representations in Java.
     * </p>
     * 
     * @param arg
     *            the object arguments for not empty assertion
     * @param message
     *            the message to be returned with the exception if assertion fails
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertNotEmpty(Object arg, String message) {
	assertNotNull(arg, message);
	if (arg instanceof String && ((String) arg).trim() == "")
	    throw new IllegalArgumentException(message);
	else if ((arg instanceof StringBuilder)
		&& (((StringBuilder) arg).toString().trim().equals("")))
	    throw new IllegalArgumentException(message);
	else if ((arg instanceof StringBuffer)
		&& (((StringBuffer) arg).toString().trim().equals("")))
	    throw new IllegalArgumentException(message);
    }

    /**
     * <p>
     * Tests the given argument of type {@link String}, {@link StringBuilder} or
     * {@link StringBuffer} for empty (but not <code>null</code>) value and throws an
     * {@link IllegalArgumentException} with a default message if the argument is an empty string
     * literal or representation. An {@link IllegalArgumentException} exception is also thrown if
     * the argument is <code>null</code> or the object is not an instance of any one of the three
     * popular string representations in Java.
     * </p>
     * 
     * @param arg
     *            the object argument for not empty assertion
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertNotEmpty(Object arg) {
	assertNotEmpty(arg, "[Assertion Error]: Either not a string type, null or empty");
    }

    /**
     * <p>
     * Tests the given arguments of type {@link String}, {@link StringBuilder} or
     * {@link StringBuffer} for empty (but not <code>null</code>) value and throws an
     * {@link IllegalArgumentException} with a default message if any argument is an empty string
     * literal or representation. An {@link IllegalArgumentException} exception is also thrown if
     * any argument is <code>null</code> or the object is not an instance of any one of the three
     * popular string representations in Java.
     * </p>
     * 
     * @param args
     *            the object arguments for not empty assertion
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertNotEmpty(Object... args) {
	for (int i = 0; i < args.length; i++)
	    assertNotEmpty(args[i],
		    "[Assertion Error]: Atleast one argument is either not a string type, null or empty");
    }

    /**
     * Tests the given condition for truth and throws an {@link IllegalArgumentException} with the
     * provided message if the condition is false.
     * 
     * @param condition
     *            the condition for truth assertion
     * @param message
     *            the message to be returned with the exception if assertion fails
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertTrue(boolean condition, String message) {
	if (!condition)
	    throw new IllegalArgumentException(message);
    }

    /**
     * Tests the given condition for truth and throws an {@link IllegalArgumentException} with a
     * default message if the condition is false.
     * 
     * @param condition
     *            the condition for truth assertion
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertTrue(boolean condition) {
	assertTrue(condition, "[Assertion Error]: Condition is false");
    }

    /**
     * Tests the given conditions for truth and throws an {@link IllegalArgumentException} with a
     * default message if any condition is false.
     * 
     * @param conditions
     *            the conditions for truth assertion
     * @throws IllegalArgumentException
     *             exception thrown if assertion fails
     */
    public static void assertTrue(boolean... conditions) {
	for (int i = 0; i < conditions.length; i++) {
	    assertTrue(conditions[i], "[Assertion Error]: Atleast one condition is false");
	}
    }
}