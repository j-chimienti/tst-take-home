package services

object PromotionService {

  def allCombinablePromotions(
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] = {
    // Generate all promotion codes
    val codes =
      allPromotions.flatMap(c =>
        combinablePromotions(
          promotionCode = c.code,
          allPromotions = allPromotions
        )
      )
    // Remove duplicates by converting to sets and back to sequences
    val uniqueCombos = codes.map(combo => combo.promotionCodes.toSet).distinct
    // Convert sets back to PromotionCombo
    uniqueCombos.map(combo => PromotionCombo(combo.toSeq))
  }

  @throws[RuntimeException]("missing promotion")
  def combinablePromotions(
      promotionCode: String,
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] = {
    // Map promotion codes to their respective promotions
    val promotionsByCode = allPromotions.map(p => p.code -> p).toMap

    val promo = promotionsByCode.getOrElse(
      promotionCode,
      throw new NoSuchElementException(
        s"promotionCode=$promotionCode not found"
      )
    )

    // Filter out promotions that are not combinable with the given promotion
    val combinablePromotions = allPromotions
      .filterNot(p =>
        p.code == promotionCode ||
          promo.notCombinableWith.contains(p.code) || p.notCombinableWith
            .contains(promotionCode)
      )

    val combos = combinablePromotions
      .map(startingPromotion => {
        combinablePromotions
          .filterNot(_.code == startingPromotion.code)
          .foldLeft(
            (
              Set(promotionCode, startingPromotion.code),
              promo.notCombinableWith.toSet ++ startingPromotion.notCombinableWith.toSet
            )
          ) { case ((combo, notCompatibleList), remaining) =>
            if (notCompatibleList.contains(remaining.code))
              (combo, notCompatibleList)
            else
              (
                combo + remaining.code,
                notCompatibleList ++ remaining.notCombinableWith.toSet
              )
          }
      })
      .map { case (c, _) => c }
    // Remove duplicate combinations and convert to PromotionCombo
    val uniqueCombos =
      combos.toSet.map((c: Set[String]) => PromotionCombo(c.toSeq))
    uniqueCombos.toSeq
  }

}
