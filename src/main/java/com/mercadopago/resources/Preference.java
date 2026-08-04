package com.mercadopago.resources;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.common.BackUrl;
import com.mercadopago.resources.common.DifferentialPricing;
import com.mercadopago.resources.common.Item;
import com.mercadopago.resources.common.PaymentMethod;
import com.mercadopago.resources.common.ReceiverAddress;
import com.mercadopago.resources.common.Shipment;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;

/**
 * MercadoPago Preference resource.
 *
 * <p>Represents a checkout preference containing all the information needed to display a payment
 * checkout. Preferences define items, payer information, payment methods, redirect URLs, and
 * other settings for the checkout experience.
 *
 * <p>Usage example:
 * <pre>{@code
 * Preference preference = Preference.expire("123456789");
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/preferences">
 *     Preferences API reference</a>
 */
@Getter
public class Preference extends MPBase {

  /** Unique identifier of the preference. */
  private String id;

  /** List of items to be paid. */
  private List<Item> items;

  /** Payer information. */
  private PreferencePayer payer;

  /** Payment methods configuration. */
  private PaymentMethod paymentMethods;

  /** Shipping information. */
  private Shipment shipments;

  /** URLs to redirect the buyer after payment. */
  private BackUrl backUrls;

  /** URL to redirect automatically after payment approval. */
  private String autoReturn;

  /** External reference for integrator tracking. */
  private String externalReference;

  /** Whether to use binary mode (immediate approval/rejection). */
  private Boolean binaryMode;

  /** Date when the preference expires. */
  private OffsetDateTime expirationDateFrom;

  /** Date when the preference expires. */
  private OffsetDateTime expirationDateTo;

  /** Total amount to be collected. */
  private BigDecimal collectorId;

  /** Application identifier. */
  private String applicationId;

  /** Marketplace identifier. */
  private String marketplace;

  /** Marketplace fee. */
  private BigDecimal marketplaceFee;

  /** Differential pricing configuration. */
  private DifferentialPricing differentialPricing;

  /** Date of creation. */
  private OffsetDateTime dateCreated;

  /** Date of last update. */
  private OffsetDateTime lastUpdated;

  /** Notification URL for payment updates. */
  private String notificationUrl;

  /** Additional information. */
  private String additionalInfo;

  /** Tracks information. */
  private MPResourceList tracks;

  /** Preference expiration absolute date. */
  private OffsetDateTime expires;

  /** Whether the preference has expired. */
  private Boolean expired;

  /** Total amount. */
  private BigDecimal totalAmount;

  /** Client identifier. */
  private String clientId;

  /** Collector address. */
  private ReceiverAddress address;

  /** Statement descriptor. */
  private String statementDescriptor;

  /** Purpose. */
  private String purpose;

  /** Site identifier. */
  private String siteId;

  /** Processing modes. */
  private List<String> processingModes;

  /** Metadata. */
  private Object metadata;

  /**
   * Expires a preference by its unique identifier.
   *
   * <p>This method calls the PUT /checkout/preferences/{id}/expire endpoint to mark the
   * preference as expired, preventing it from being used for new payment checkouts.
   *
   * @param id the unique identifier of the preference to expire
   * @return the expired {@link Preference}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  @PUT(path = "/checkout/preferences/:id/expire")
  public static Preference expire(String id) throws MPException, MPApiException {
    return expire(id, null);
  }

  /**
   * Expires a preference by its unique identifier with custom request options.
   *
   * <p>This method calls the PUT /checkout/preferences/{id}/expire endpoint to mark the
   * preference as expired, preventing it from being used for new payment checkouts.
   *
   * @param id the unique identifier of the preference to expire
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the expired {@link Preference}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  @PUT(path = "/checkout/preferences/:id/expire")
  public static Preference expire(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return _put(
        String.format("/checkout/preferences/%s/expire", id),
        null,
        Preference.class,
        requestOptions,
        MercadoPagoConfig.getHttpClient());
  }
}