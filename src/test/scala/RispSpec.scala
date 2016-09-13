import org.scalatest.{FunSpec, Matchers, OptionValues}, OptionValues._
import risp._, dsl._, IntOps._

/**
  * Created by Lloyd on 9/13/16.
  *
  * Copyright 2016
  */
class RispSpec extends FunSpec with Matchers {

  describe("Eval.apply") {

    it("should work") {

      case object ToInt extends BaseFunc[String, Int](_.toInt, (acc, next) => acc * 10 + next.toInt)

      val expr = Expr(
        Add, 1, 2,
        Expr(ToInt, "1", "2"),
        Expr(Times, 3,
          Expr(Minus, 4, 1, 1))
      )
      val r = Eval(expr)
      r.value shouldBe 21
    }

  }

}