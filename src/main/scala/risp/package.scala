/**
  * Created by Lloyd on 9/13/16.
  *
  * Copyright 2016
  */

package object risp {

  /**
    * Brings an implicit conversion in scope to make it easier to write expressions without manually wrapping
    * non-Expressions in Just
    */
  object dsl {
    import scala.language.implicitConversions
    implicit def anyToJust[A](a: A): Just[A] = Just(a)
    val | = Expr
  }

  object IntOps {
    case object Add extends Func[Int](_ + _)
    case object Minus extends Func[Int](_ - _)
    case object Times extends Func[Int](_ * _)
    case object Divide extends BaseFunc[Int, Double](_.toDouble, _ / _.toDouble)
  }
}
