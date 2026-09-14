package com.mercadopago.resources;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.Headers;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResponse;
import com.mercadopago.serialization.Serializer;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * Resource representing a MercadoPago Order.
 *
 * <p>Provides methods to retrieve refunds associated with an order through the
 * {@code GET /v1/orders/{order_id}/refund} endpoint.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">
 *     MercadoPago API reference</a>
 */
@Getter
@Setter
public class Order extends MPResource {

  /** Unique identifier of the order. */
  private String id;

  /** HTTP client used to execute API requests. */
  private MPHttpClient httpClient;

  /** URL template for the order refunds endpoint. */
  private static final String REFUNDS_URL = "/v1/orders/%s/refund";

  /**
   * Default constructor. Initializes the HTTP client using the global configuration.
   */
  public Order() {
    this.httpClient = MercadoPagoConfig.getHttpClient();
  }

  /**
   * Constructs an {@code Order} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} used to execute HTTP requests
   */
  public Order(MPHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  /**
   * Retrieves all refunds associated with this order.
   *
   * <p>Sends a GET request to {@code /v1/orders/{order_id}/refund} endpoint.
   *
   * @return an {@link MPResourceList} containing the refund resources
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResourceList getRefunds() throws MPException, MPApiException {
    return this.getRefunds(null);
  }

  /**
   * Retrieves all refunds associated with this order with custom request options.
   *
   * <p>Sends a GET request to {@code /v1/orders/{order_id}/refund} endpoint.
   * Supports {@code X-Idempotency-Key} and {@code X-Product-ID} headers through
   * the request options.
   *
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers
   *     (including X-Idempotency-Key and X-Product-ID), or timeouts for this single request;
   *     may be {@code null}
   * @return an {@link MPResourceList} containing the refund resources
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResourceList getRefunds(MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    String uri = String.format(REFUNDS_URL, this.id);
    
    Map<String, String> headers = new HashMap<>();
    headers.put(Headers.ACCEPT, "application/json");
    headers.put(Headers.CONTENT_TYPE, "application/json; charset=UTF-8");
    headers.put(Headers.PRODUCT_ID, MercadoPagoConfig.PRODUCT_ID);
    headers.put(
        Headers.USER_AGENT,
        String.format("MercadoPago Java SDK/%s", MercadoPagoConfig.CURRENT_VERSION));
    headers.put(Headers.TRACKING_ID, MercadoPagoConfig.TRACKING_ID);

    // Add authorization header
    String accessToken = getAccessToken(requestOptions);
    if (accessToken != null) {
      headers.put(Headers.AUTHORIZATION, String.format("Bearer %s", accessToken));
    }

    // Merge custom headers from request options (including X-Idempotency-Key and X-Product-ID)
    if (requestOptions != null && requestOptions.getCustomHeaders() != null) {
      for (Map.Entry<String, String> entry : requestOptions.getCustomHeaders().entrySet()) {
        headers.put(entry.getKey().toLowerCase(), entry.getValue());
      }
    }

    MPRequest mpRequest = MPRequest.builder()
        .uri(uri)
        .method(HttpMethod.GET)
        .headers(headers)
        .accessToken(accessToken)
        .connectionTimeout(getConnectionTimeout(requestOptions))
        .connectionRequestTimeout(getConnectionRequestTimeout(requestOptions))
        .socketTimeout(getSocketTimeout(requestOptions))
        .build();

    MPResponse response = httpClient.send(mpRequest);
    MPResourceList result = Serializer.deserializeFromJson(MPResourceList.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  private String getAccessToken(MPRequestOptions requestOptions) {
    if (requestOptions != null 
        && requestOptions.getAccessToken() != null 
        && !requestOptions.getAccessToken().isEmpty()) {
      return requestOptions.getAccessToken();
    }
    return MercadoPagoConfig.getAccessToken();
  }

  private int getConnectionTimeout(MPRequestOptions requestOptions) {
    if (requestOptions != null && requestOptions.getConnectionTimeout() > 0) {
      return requestOptions.getConnectionTimeout();
    }
    return MercadoPagoConfig.getConnectionTimeout();
  }

  private int getConnectionRequestTimeout(MPRequestOptions requestOptions) {
    if (requestOptions != null && requestOptions.getConnectionRequestTimeout() > 0) {
      return requestOptions.getConnectionRequestTimeout();
    }
    return MercadoPagoConfig.getConnectionRequestTimeout();
  }

  private int getSocketTimeout(MPRequestOptions requestOptions) {
    if (requestOptions != null && requestOptions.getSocketTimeout() > 0) {
      return requestOptions.getSocketTimeout();
    }
    return MercadoPagoConfig.getSocketTimeout();
  }
}