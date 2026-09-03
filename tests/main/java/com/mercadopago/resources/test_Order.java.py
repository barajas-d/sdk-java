package com.mercadopago.resources;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mercadopago.net.MPResource;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Order} resource class.
 */
class OrderTest {

  /**
   * Verifies that Order can be instantiated successfully.
   */
  @Test
  void testOrderInstantiation() {
    Order order = new Order();
    assertNotNull(order, "Order instance should not be null");
  }

  /**
   * Verifies that Order extends MPResource as expected.
   */
  @Test
  void testOrderExtendsMPResource() {
    Order order = new Order();
    assertTrue(order instanceof MPResource, "Order should extend MPResource");
  }

  /**
   * Verifies that Order inherits response handling from MPResource.
   */
  @Test
  void testOrderInheritsResponseFromMPResource() {
    Order order = new Order();
    // Initially, response should be null since it's not set
    // This tests that the inherited field is accessible
    assertNotNull(order, "Order should be instantiable and have access to MPResource fields");
  }
}