package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.advancedpayment.AdvancedPaymentDisbursement;
import com.mercadopago.resources.advancedpayment.AdvancedPaymentPayer;
import com.mercadopago.resources.advancedpayment.AdvancedPaymentPayment;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;

/**
 * Resource representing an Advanced Payment (Marketplace Split Payment) in MercadoPago.
 *
 * <p>Advanced Payments enable marketplace integrations to collect a single payment from a buyer
 * and automatically split funds among multiple sellers (disbursements). They support two-step
 * flows (authorize → capture) and individual disbursement release-date control.
 *
 * <p>Usage example:
 * <pre>{@code
 * AdvancedPayment payment = AdvancedPayment.get(12345L);
 * AdvancedPaymentPayer payer = payment.getPayer();
 * List<AdvancedPaymentDisbursement> disbursements = payment.getDisbursements();
 * }</pre>
 *
 * @see com.mercadopago.client.advancedpayment.AdvancedPaymentClient
 * @see <a href="https://www.mercadopago.com/developers/en/reference">Advanced Payments API reference</a>
 */
@Getter
public class AdvancedPayment extends MPBase {

  /** Unique identifier of the advanced payment. */
  private Long id;

  /** Current status of the advanced payment (e.g., approved, pending, cancelled). */
  private String status;

  /** Detailed status information for the payment. */
  private String statusDetail;

  /** MercadoPago application identifier. */
  private String applicationId;

  /** Human-readable description of the payment. */
  private String description;

  /** Integrator-provided external reference. */
  private String externalReference;

  /** Indicates whether the payment was captured (true) or only authorized (false). */
  private Boolean capture;

  /** When true, the payment is approved or rejected immediately. */
  private Boolean binaryMode;

  /** List of payment sources (card tokens, amounts). */
  private List<AdvancedPaymentPayment> payments;

  /** List of disbursements (receivers and amounts). */
  private List<AdvancedPaymentDisbursement> disbursements;

  /** Information about the buyer. */
  private AdvancedPaymentPayer payer;

  /** ISO 8601 date when the advanced payment was created. */
  private OffsetDateTime dateCreated;

  /** ISO 8601 date when the advanced payment was last updated. */
  private OffsetDateTime dateLastUpdated;

  /** Total transaction amount. */
  private BigDecimal transactionAmount;

  /** Indicates whether the payment is live (true) or in test mode (false). */
  private Boolean liveMode;

  /**
   * Retrieves an advanced payment by its unique identifier.
   *
   * @param id the unique identifier of the advanced payment
   * @return the requested {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static AdvancedPayment get(Long id) throws MPException, MPApiException {
    return get(id, null);
  }

  /**
   * Retrieves an advanced payment by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the advanced payment
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static AdvancedPayment get(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return _get(String.format("/v1/advanced_payments/%s", id), requestOptions, AdvancedPayment.class);
  }

  /**
   * Searches advanced payments matching the given criteria.
   *
   * @param request the {@link MPSearchRequest} with search filters and pagination parameters
   * @return an {@link MPResultsResourcesPage} of {@link AdvancedPayment} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static MPResultsResourcesPage<AdvancedPayment> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return search(request, null);
  }

  /**
   * Searches advanced payments matching the given criteria with custom request options.
   *
   * @param request the {@link MPSearchRequest} with search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link AdvancedPayment} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static MPResultsResourcesPage<AdvancedPayment> search(
      MPSearchRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return _search("/v1/advanced_payments/search", request, requestOptions, AdvancedPayment.class);
  }
}