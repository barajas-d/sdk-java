package com.mercadopago.resources;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.advancedpayment.AdvancedPaymentClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceArray;
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
 * Resource representing an advanced payment (marketplace split payment) in the MercadoPago API.
 *
 * <p>Advanced payments enable marketplace integrations to collect a single payment and
 * distribute funds among multiple sellers (disbursements). They support two-step flows
 * (authorize → capture) and individual disbursement release-date control.
 *
 * <p>Usage example:
 * <pre>{@code
 * AdvancedPayment payment = AdvancedPayment.findById(123456L);
 * System.out.println(payment.getStatus());
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">
 *     Advanced Payments API reference</a>
 */
@Getter
public class AdvancedPayment extends MPBase {

  /** Unique identifier of the advanced payment. */
  private Long id;

  /** Current status of the advanced payment (e.g., approved, rejected, pending). */
  private String status;

  /** Detailed status information. */
  private String statusDetail;

  /** MercadoPago application identifier. */
  private String applicationId;

  /** Human-readable description of the payment. */
  private String description;

  /** Integrator-provided external reference. */
  private String externalReference;

  /** When false, the payment was authorized but not captured. */
  private Boolean capture;

  /** When true, the payment is approved or rejected immediately. */
  private Boolean binaryMode;

  /** Timestamp when the advanced payment was created. */
  private OffsetDateTime dateCreated;

  /** Timestamp when the advanced payment was last updated. */
  private OffsetDateTime dateLastUpdated;

  /** List of payment sources (card tokens, amounts). */
  private MPResourceArray<AdvancedPaymentPayment> payments;

  /** List of receivers (collector IDs and amounts). */
  private List<AdvancedPaymentDisbursement> disbursements;

  /** Buyer information. */
  private AdvancedPaymentPayer payer;

  /** Total transaction amount. */
  private BigDecimal transactionAmount;

  /** Total amount charged to the payer. */
  private BigDecimal totalAmount;

  /** Currency code (ISO 4217, e.g., "BRL", "ARS"). */
  private String currencyId;

  /** Site identifier (e.g., "MLB", "MLA"). */
  private String siteId;

  /** MercadoPago public key used. */
  private String publicKey;

  /** Marketplace identifier. */
  private String marketplaceId;

  /** Platform fee amount. */
  private BigDecimal platformFee;

  /**
   * Retrieves an advanced payment by its unique identifier using default configuration.
   *
   * @param id the unique identifier of the advanced payment
   * @return the requested {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static AdvancedPayment findById(Long id) throws MPException, MPApiException {
    return findById(id, null);
  }

  /**
   * Retrieves an advanced payment by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the advanced payment
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the requested {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static AdvancedPayment findById(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return new AdvancedPaymentClient(MercadoPagoConfig.getHttpClient()).get(id, requestOptions);
  }

  /**
   * Searches advanced payments matching the given criteria using default configuration.
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
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link AdvancedPayment} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static MPResultsResourcesPage<AdvancedPayment> search(
      MPSearchRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return new AdvancedPaymentClient(MercadoPagoConfig.getHttpClient())
        .search(request, requestOptions);
  }
}