package com.mercadopago.resources.pos;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import lombok.Getter;

/** A point of sale returned by the POS API. */
@Getter
public class Pos extends MPResource {
  /** Unique identifier of the point of sale. */
  private String id;

  /** Display name of the point of sale. */
  private String name;

  /** Identifier of the parent store. */
  private String storeId;

  /** Merchant's identifier for the point of sale. */
  private String externalId;

  /** Merchant's identifier for the parent store. */
  private String externalStoreId;

  /** Business category code. */
  private Integer category;

  /** Whether the amount at this point of sale is fixed. */
  private Boolean fixedAmount;

  /** Webhook URL associated with this point of sale. */
  private String url;

  /** Data encoded in the QR code. */
  private String qrCode;

  /** URL of the QR code image. */
  private String qrCodeImage;

  /** Creation timestamp. */
  private OffsetDateTime dateCreated;

  /** Last modification timestamp. */
  private OffsetDateTime dateLastUpdated;
}
