package com.mercadopago.resources;

import com.mercadopago.net.MPResource;

/**
 * Base resource class for handling order-related operations in the MercadoPago Java SDK.
 *
 * <p>This class extends {@link MPResource} to provide the foundation for order resource
 * representations returned by the MercadoPago Orders API. It serves as the base type for
 * order-related operations such as creation, retrieval, processing, and cancellation.
 *
 * <p>Usage example:
 * <pre>{@code
 * OrderClient client = new OrderClient();
 * Order order = client.get("ORDER_ID");
 * }</pre>
 *
 * @see com.mercadopago.client.order.OrderClient
 * @see <a href="https://www.mercadopago.com/developers/en/reference/online-payments/checkout-api/create-order/post">
 *     Orders API reference</a>
 */
public class Order extends MPResource {
}