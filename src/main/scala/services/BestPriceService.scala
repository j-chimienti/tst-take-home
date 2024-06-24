package services

object BestPriceService {

  @throws[NoSuchElementException]("if a cabinPrice.rateCode missing in rates")
  def getBestGroupPrices(
      rates: Seq[Rate],
      prices: Seq[CabinPrice]
  ): Seq[BestGroupPrice] = {
    // Map rateCode to rateGroup for quick lookup eg M1 -> Military, M2 -> Military
    val rateCodeToRateGroups =
      rates.map(rate => rate.rateCode -> rate.rateGroup).toMap

    // group prices by cabin code and rate group eg (CA,Military),(CB,Military)
    val pricesByCabinCodeAndRateGroup =
      prices.groupBy(cp =>
        (
          cp.cabinCode,
          rateCodeToRateGroups.getOrElse(
            cp.rateCode,
            throw new NoSuchElementException(
              s"cabinCode=${cp.cabinCode} missing from rates"
            )
          )
        )
      )

    // Find the best price for each group
    val bestPrices = pricesByCabinCodeAndRateGroup.map {
      case ((cabinCode, rateGroup), cabinPrices) =>
        val bestCabinPrice = cabinPrices.minBy(_.price)
        BestGroupPrice(
          cabinCode = cabinCode,
          rateCode = bestCabinPrice.rateCode,
          price = bestCabinPrice.price,
          rateGroup = rateGroup
        )
    }

    // Convert to Seq and sort by price
    bestPrices.toSeq.sortBy(_.price)
  }

}
