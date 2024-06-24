import services.{BestGroupPrice, BestPriceService, CabinPrice, Rate}

class BestPriceServiceTest extends ServiceSpec {
  import BestPriceServiceTest._
  test("getBestGroupPrices") {
    BestPriceService.getBestGroupPrices(
      rates = rates,
      prices = prices
    ) shouldBe expected
  }
}

object BestPriceServiceTest {
  val rates = Seq(
    Rate(rateCode = "M1", rateGroup = "Military"),
    Rate(rateCode = "M2", rateGroup = "Military"),
    Rate(rateCode = "S1", rateGroup = "Senior"),
    Rate(rateCode = "S2", rateGroup = "Senior")
  )

  val prices = Seq(
    CabinPrice(cabinCode = "CA", rateCode = "M1", price = 200.00),
    CabinPrice(cabinCode = "CA", rateCode = "M2", price = 250.00),
    CabinPrice(cabinCode = "CA", rateCode = "S1", price = 225.00),
    CabinPrice(cabinCode = "CA", rateCode = "S2", price = 260.00),
    CabinPrice(cabinCode = "CB", rateCode = "M1", price = 230.00),
    CabinPrice(cabinCode = "CB", rateCode = "M2", price = 260.00),
    CabinPrice(cabinCode = "CB", rateCode = "S1", price = 245.00),
    CabinPrice(cabinCode = "CB", rateCode = "S2", price = 270.00)
  )
  val expected = Seq(
    BestGroupPrice(
      cabinCode = "CA",
      rateCode = "M1",
      price = 200.0,
      rateGroup = "Military"
    ),
    BestGroupPrice(
      cabinCode = "CA",
      rateCode = "S1",
      price = 225.00,
      rateGroup = "Senior"
    ),
    BestGroupPrice(
      cabinCode = "CB",
      rateCode = "M1",
      price = 230.00,
      rateGroup = "Military"
    ),
    BestGroupPrice(
      cabinCode = "CB",
      rateCode = "S1",
      price = 245.00,
      rateGroup = "Senior"
    )
  )

}
