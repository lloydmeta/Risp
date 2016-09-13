package risp

import scala.util.control.TailCalls._

/**
  * Created by Lloyd on 9/13/16.
  *
  * Copyright 2016
  */

object Eval {

  /**
    * Evaluates the given term
    *
    * Returns the evaluated result a given term. The implementation should be stack-safe because
    * trampolining is used.
    */
  def apply[A](l: Term[A]): Option[A] = tailRecApply(l).result

  private[this] def tailRecApply[A](l: Term[A]): TailRec[Option[A]] = l match {
    case Just(a) => done(Some(a))
    case Expr(func, elems @ _*) => {
      val init: TailRec[Option[A]] = (for {
        a <- elems.headOption
      } yield {
        tailcall(tailRecApply(a)).map { tc =>
          tc.map(func.init)
        }
      }).getOrElse(done(None))
      elems.drop(1).foldLeft(init) { (tcOptAcc, next) =>
        for {
          optAcc <- tcOptAcc
          optNext <- tailcall(tailRecApply(next))
        } yield {
          for {
            acc <- optAcc
            next <- optNext
          } yield func.foldStep(acc, next)
        }
      }
    }
  }

}