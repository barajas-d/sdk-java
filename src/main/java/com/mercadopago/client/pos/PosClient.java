package com.mercadopago.client.pos;

import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.pos.Pos;
import com.mercadopago.serialization.Serializer;
import java.lang.reflect.Type;

/** Client for searching points of sale. */
public class PosClient extends MercadoPagoClient {

  private static final String URL_POS = "/pos";

  /** Creates a POS client with the configured HTTP client. */
  public PosClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Creates a POS client with a custom HTTP client.
   *
   * @param httpClient the HTTP client used to execute requests
   */
  public PosClient(MPHttpClient httpClient) {
    super(httpClient);
  }

  /**
   * Searches points of sale using the provided query filters.
   *
   * @param request search filters (external_id, external_store_id, store_id, and category), or
   *     {@code null} for an unfiltered search
   * @return a page containing matching points of sale and paging information
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Pos> searchPos(MPSearchRequest request)
      throws MPException, MPApiException {
    return searchPos(request, null);
  }

  /**
   * Searches points of sale with custom request options.
   *
   * @param request search filters (external_id, external_store_id, store_id, and category), or
   *     {@code null} for an unfiltered search
   * @param requestOptions optional per-request access token, headers, and timeouts
   * @return a page containing matching points of sale and paging information
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Pos> searchPos(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException, MPApiException {
    MPResponse response = search(URL_POS, request, requestOptions);
    Type responseType = new TypeToken<MPResultsResourcesPage<Pos>>() {}.getType();
    MPResultsResourcesPage<Pos> result =
        Serializer.deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);
    return result;
  }
}
