package com.mercadopago.client.chargeback;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/** JSON request body containing documentation files for a chargeback update. */
@Getter
@Builder
public class UpdateChargebackRequest {
  /** Documentation files to submit with the chargeback. */
  private final List<File> files;

  /** Documentation file supplied by URL. */
  @Getter
  @Builder
  public static class File {
    /** Description of the documentation file. */
    private final String description;

    /** Name of the documentation file. */
    private final String name;

    /** URL of the documentation file. */
    private final String url;
  }
}
