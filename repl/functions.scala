def add1(value: Int) = value + 1

val add1: Int => Int = _ + 1

val mul2: Int => Int = _ * 2

(add1 compose mul2)(2)
(add1 andThen mul2)(2)



val small: Int => Option[Int] = Some(_).filter(_ <= 10)

val even: Int => Option[Int] = Some(_).filter(_ % 2 == 0)

small andThen even

val smallEven: Int => Option[Int] = small(_) match {
  case Some(v) => even(v)
  case None => None
}

val smallEven: Int => Option[Int] = small(_) flatMap even

import cats._
import cats.data.Kleisli
import cats.implicits._

val smallEven = Kleisli(small) andThen Kleisli(even)

smallEven.run(2)
smallEven.run(11)




