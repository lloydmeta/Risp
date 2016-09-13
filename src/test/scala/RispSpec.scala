import org.scalatest.{ FunSpec, Matchers, OptionValues }, OptionValues._
import risp._, dsl._, IntOps._

import scala.annotation.tailrec

/**
 * Created by Lloyd on 9/13/16.
 *
 * Copyright 2016
 */
class RispSpec extends FunSpec with Matchers {

  describe("Eval.apply") {

    it("should work") {

      case object ToInt extends BaseFunc[String, Int](_.toInt, (acc, next) => acc * 10 + next.toInt)

      val expr = |(
        Add, 1, 2,
        |(ToInt, "1", "2"),
        |(Times, 3,
          |(Minus, 4, 1, 1))
      )
      val r = Eval(expr)
      r.value shouldBe 21
    }

    it("should not blow the stack") {
      val expr = stacked(levels = 500000, |(Times, 1, 1))
      val r = Eval(expr)
      r.value shouldBe 1
    }

  }

  /**
   * Returns a head-stacked version of the given expression.
   *
   * {{{
   *    stacked(levels = 3, |(Times, 1, 1))
   *    res14: Expr[Int, Int] = (Times (Times (Times 1 1) 1 1) 1 1)
   * }}}
   */
  private def stacked[Arg](levels: Int, expr: Expr[Arg, Arg]): Expr[Arg, Arg] = {
    @tailrec
    def step(current: Int, currentExpr: Expr[Arg, Arg]): Expr[Arg, Arg] = {
      if (current <= 1)
        currentExpr
      else
        step(current - 1, Expr(expr.func, currentExpr +: expr.terms: _*))
    }
    step(levels, expr)
  }

}