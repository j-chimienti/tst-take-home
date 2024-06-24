import services.PromotionService.{allCombinablePromotions, combinablePromotions}
import services.{Promotion, PromotionCombo}

class PromotionServiceTest extends ServiceSpec {

  import PromotionServiceTest._

  test("allPromotions") {
    val allCombos = allCombinablePromotions(promotions)
    assert(
      allCombos === expectedAllCombos
    )
  }

  test("p1") {
    val p1Combos =
      combinablePromotions(promotionCode = "P1", allPromotions = promotions)
    p1Combos shouldBe expectedP1Combos

  }
  test("p3") {
    val p3Combos =
      combinablePromotions(promotionCode = "P3", allPromotions = promotions)
    assert(p3Combos === expectedP3Combos)
  }
}

object PromotionServiceTest {
  val promotions = Seq(
    Promotion(code = "P1", notCombinableWith = Seq("P3")),
    Promotion(code = "P2", notCombinableWith = Seq("P4", "P5")),
    Promotion(code = "P3", notCombinableWith = Seq("P1")),
    Promotion(code = "P4", notCombinableWith = Seq("P2")),
    Promotion(code = "P5", notCombinableWith = Seq("P2"))
  )
  val expectedAllCombos = Seq(
    PromotionCombo(promotionCodes = Seq("P1", "P2")),
    PromotionCombo(promotionCodes = Seq("P1", "P4", "P5")),
    PromotionCombo(promotionCodes = Seq("P2", "P3")),
    PromotionCombo(promotionCodes = Seq("P3", "P4", "P5"))
  )

  val expectedP1Combos = Seq(
    PromotionCombo(promotionCodes = Seq("P1", "P2")),
    PromotionCombo(promotionCodes = Seq("P1", "P4", "P5"))
  )

  val expectedP3Combos = Seq(
    PromotionCombo(Seq("P3", "P2")),
    PromotionCombo(Seq("P3", "P4", "P5"))
  )
}
