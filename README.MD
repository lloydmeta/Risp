# Risp [![Build Status](https://travis-ci.org/lloydmeta/Risp.svg?branch=master)](https://travis-ci.org/lloydmeta/Risp)

Reduced-Lisp in Scala Processor, Risp, is a simple language/internal DSL
written in Scala that aims to be a simplified but type-safe Lisp.

I have absolutely no need for something like this, but maybe some brighter minds
on the internet can have some fun with it.

## Why

To scratch an itch, mostly. Also, recently found out that `TailRec` implements
`map` and `flatMap`, so I wanted to write a stack-safe interpreter (see `risp.Eval`).

There are a bunch of examples everywhere on how to implement traditional 
Lisps in Scala on the internet, and they are usually either dynamically typed,
compile from non-Scala to Scala, or a combination of both. Risp is different 
in that it is both implemented and written in Scala.


## Usage

```scala
import risp._, dsl._, IntOps._

// declaring a function
case object ToInt extends BaseFunc[String, Int](_.toInt, (acc, next) => acc * 10 + next.toInt)

val expr = Expr(Add, 1, 2, Expr(ToInt, "1", "2")) // => (Add 1 2 (ToInt 1 2))
val r = Eval(expr)
assert(r == Some(15))
```