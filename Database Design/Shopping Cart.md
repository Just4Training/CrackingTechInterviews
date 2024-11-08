# Shopping Cart Design

## Core Entities
- Product
- Customer / User
- Cart
- Order

### Product Design
- ProductId(PK) : UUID
- Name : String
- Description : String
- Price : Numeric
- Quantity : Numeric

### User Design
- UserId(PK) : UUID
- Name : String

### Cart Design
- CartId(PK) : UUID
- UserId(FK) : UUID

### Order Design
- OrderId(PK) : UUID
- UserId(FK) : UUID
- OrderDate: TIMESTAMP
- TotalAmount: Numeric

### OrderItem Design
- Id(PK) : UUID
- OrderId(FK) : UUID
- ProductId(FK) : UUID
- Quanity: INT
- Price: Numeric

## Relationship

> (Reference)[https://www.geeksforgeeks.org/how-to-design-a-database-for-shopping-cart/]