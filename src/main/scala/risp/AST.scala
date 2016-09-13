package risp

/**
 * Created by Lloyd on 9/13/16.
 *
 * Copyright 2016
 */

/**
 * Basic form of a Function.
 *
 * @param init how to transform an element of A to a B so we can start folding over a list of A
 * @param foldStep how to accumulate elements
 * @tparam A type of the arguments this function takes
 * @tparam B type of the result this function produces
 */
abstract class BaseFunc[A, B](val init: A => B, val foldStep: (B, A) => B)

/**
 * Basic function that folds over arguments without modifying the element types
 *
 * @param foldStep how to accumulate elements
 * @tparam A type of the arguments this function takes
 */
abstract class Func[A](override val foldStep: (A, A) => A) extends BaseFunc[A, A](identity, foldStep)

/**
 * A term
 */
sealed abstract class Term[A]

/**
 * Just a simple wrapper of anything into a term
 */
final case class Just[A](element: A) extends Term[A] {
  override def toString: String = s"$element"
}

/**
 * An Expression
 *
 * @param func function to apply terms to
 * @param terms variable terms
 *
 * @tparam Arg Type of terms
 * @tparam Result Type of result
 */
final case class Expr[Arg, Result](func: BaseFunc[Arg, Result], terms: Term[Arg]*) extends Term[Result] {
  override def toString: String = {
    val argsStr = terms.map(_.toString).mkString(" ")
    s"""($func $argsStr)"""
  }
}
