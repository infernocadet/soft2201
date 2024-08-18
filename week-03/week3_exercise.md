# week 3 exercise planning

notes:

- designing point-of-sale system for a computer shop
- shop sells variety of products
  - desktop computers
  - laptops
    - brands from Apple, Dell, HP, Lenovo
  - accessories
    - keyboard
    - monitor
    - speaker
    - mice
  - services
    - hardware repair
    - data recovery
    - system recovery
- customers can place orders for items they wish to purchase
  - orders include
    - computers/laptops
    - accessories
    - services
- orders
  - each order belongs to a single customer
  - orders can contain one or more items
  - each order has a unique order number
  - contains customer detail
  - total amount to be paid
  - payment method (cash, card, bank transfer, gift card)
  - payment date

needs to follow solid principles:
S - single responsibility principle:

- every class has a single responsibility, and responsibility should be entirely met. class should only have one reason to change
  O - open/closed principle:
- should be able to extend code without breaking it - don't alter superclasses
- polymorphism basically
  L - liskov substitution princple:
- subtypes must be substitutable for their base types
  I - interface segregation principle
- fat interfaces - group up interfaces
  D - dependency inversion principle:
-

main classes:
product:

- product_name
- retail_price
- updatePrice()

good -> product:

- good_id
- brand

service -> product:

- estimated_completion_date

customer:

- customer_id
- name
- contact_detail

payment:

- total
- payment_type: PaymentMethod
- changeRequired

PaymentMethod:

- makePayment()

cashPayment -> PaymentMethod:

- totalCash:

cardPayment -> PaymentMethod:

- cardNumber
- cardFee

bankTransfer -> PaymentMethod:

- bsb
- accountNumber

giftCard -> PaymentMethod:

- cardNumber
- checkCardBalance()
- reduceCardBalance()
