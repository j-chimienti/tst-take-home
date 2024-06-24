package services

/** @param cabinCode
  *   The price for a specific cabin on a specific cruise.
  * @param rateCode
  *   A rate is a way to group related prices together. A rate is defined by its
  *   Rate Code and which Rate Group it belongs to
  * @param price
  * @param rateGroup
  *   Specific rates are grouped into a related rate group. There is a
  *   one-to-many relationship between rate groups and rates
  */
case class BestGroupPrice(
    cabinCode: String,
    rateCode: String,
    price: BigDecimal,
    rateGroup: String
)
