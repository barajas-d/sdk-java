package com.mercadopago.client.pos;

import com.mercadopago.net.MPSearchRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/** Filters for searching points of sale through GET /pos. */
@Getter
@Builder
public class SearchPosRequest extends MPSearchRequest {

  /** External identifier of the point of sale. */
  private final String externalId;

  /** External identifier of the store containing the point of sale. */
  private final String externalStoreId;

  /** Identifier of the store containing the point of sale. */
  private final String storeId;

  /** Category of the point of sale. */
  private final Integer category;

  @Override
  public Map<String, Object> getParameters() {
    Map<String, Object> parameters = new HashMap<>();
    if (externalId != null) {
      parameters.put("external_id", externalId);
    }
    if (externalStoreId != null) {
      parameters.put("external_store_id", externalStoreId);
    }
    if (storeId != null) {
      parameters.put("store_id", storeId);
    }
    if (category != null) {
      parameters.put("category", category);
    }
    return parameters;
  }
}
