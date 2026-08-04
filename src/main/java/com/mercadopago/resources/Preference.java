package com.mercadopago.resources;

import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * Resource representing a MercadoPago Preference (Checkout Pro).
 *
 * <p>A Preference defines the product or service details, prices, and configurations for
 * a checkout experience. Customers are redirected to a MercadoPago-hosted checkout page
 * where they can complete the payment.
 *
 * <p>This class extends {@link MPBase} to inherit standard API interaction methods such as
 * {@code _put()}, which is used by the {@link #expire(Long)} method to set the preference
 * to an expired state.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">Preferences API reference</a>
 */
@Getter
@Setter
public class Preference extends MPBase {

  /**
   * Expires a preference by its unique identifier using default configuration.
   *
   * <p>Sends a PUT request to {@code /checkout/preferences/{id}/expire} to mark the
   * preference as expired, preventing further payments from being processed through it.
   *
   * @param id the unique identifier of the preference to expire
   * @return the expired {@link Preference}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static Preference expire(Long id) throws MPException, MPApiException {
    return expire(id, null);
  }

  /**
   * Expires a preference by its unique identifier with custom request options.
   *
   * <p>Sends a PUT request to {@code /checkout/preferences/{id}/expire} to mark the
   * preference as expired. This overload accepts {@link MPRequestOptions} for
   * per-request configuration overrides such as custom headers or timeouts.
   *
   * @param id the unique identifier of the preference to expire
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the expired {@link Preference}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static Preference expire(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    String path = String.format("/checkout/preferences/%s/expire", id);
    MPResponse response = _put(path, HttpMethod.PUT, null, null, requestOptions);
    
    Preference preference = new Preference();
    preference.setResponse(response);
    return preference;
  }
}