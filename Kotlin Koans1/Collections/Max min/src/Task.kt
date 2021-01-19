fun Shop.getCustomerWithMaxOrders(): Customer? =
        customers.maxBy { it.orders.size }

fun getMostExpensiveProductBy(customer: Customer): Product? =
        customer.orders
                .flatMap { it.products }
                .maxBy { it.price }