package com.mercadopago.client.pos;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromString;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.BaseClientTest;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.resources.pos.Pos;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class PosClientTest extends BaseClientTest {
  private final PosClient client = new PosClient();

  @Test
  void searchPosSuccess() throws Exception {
    HttpResponse httpResponse =
        generateHttpResponseFromString(
            "{\"paging\":{\"total\":1,\"limit\":10,\"offset\":0},\"results\":[{\"id\":\"1\",\"name\":\"Register 1\"}]}",
            OK);
    doReturn(httpResponse).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));
    Mockito.clearInvocations(HTTP_CLIENT);

    MPResultsResourcesPage<Pos> page = client.searchPos(null);

    ArgumentCaptor<HttpRequestBase> captor = ArgumentCaptor.forClass(HttpRequestBase.class);
    Mockito.verify(HTTP_CLIENT).execute(captor.capture(), any(HttpContext.class));
    assertEquals("GET", captor.getValue().getMethod());
    assertEquals("/pos", captor.getValue().getURI().getPath());
    assertNotNull(page.getResponse());
    assertEquals(OK, page.getResponse().getStatusCode());
    assertEquals(1, page.getPaging().getTotal());
    assertEquals("1", page.getResults().get(0).getId());
    assertEquals("Register 1", page.getResults().get(0).getName());
  }

  @Test
  void searchPosWithRequestOptionsSuccess() throws Exception {
    HttpResponse httpResponse =
        generateHttpResponseFromString(
            "{\"paging\":{\"total\":1,\"limit\":10,\"offset\":0},\"results\":[{\"id\":\"2\"}]}",
            OK);
    doReturn(httpResponse).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));
    Mockito.clearInvocations(HTTP_CLIENT);

    SearchPosRequest request =
        SearchPosRequest.builder()
            .externalId("register-1")
            .externalStoreId("external-store")
            .storeId("store-1")
            .category(123)
            .build();
    MPResultsResourcesPage<Pos> page = client.searchPos(request, buildRequestOptions());

    ArgumentCaptor<HttpRequestBase> captor = ArgumentCaptor.forClass(HttpRequestBase.class);
    Mockito.verify(HTTP_CLIENT).execute(captor.capture(), any(HttpContext.class));
    assertEquals("GET", captor.getValue().getMethod());
    assertEquals("/pos", captor.getValue().getURI().getPath());
    String query = captor.getValue().getURI().getRawQuery();
    assertTrue(query.contains("external_id=register-1"));
    assertTrue(query.contains("external_store_id=external-store"));
    assertTrue(query.contains("store_id=store-1"));
    assertTrue(query.contains("category=123"));
    assertEquals("Bearer abc", captor.getValue().getFirstHeader("authorization").getValue());
    assertEquals(OK, page.getResponse().getStatusCode());
    assertEquals(10, page.getPaging().getLimit());
    assertEquals(0, page.getPaging().getOffset());
    assertEquals("2", page.getResults().get(0).getId());
  }

  @Test
  void searchPosWithFiltersWithoutRequestOptionsSuccess() throws Exception {
    HttpResponse httpResponse =
        generateHttpResponseFromString(
            "{\"paging\":{\"total\":1,\"limit\":10,\"offset\":0},\"results\":[{\"id\":\"3\",\"name\":\"Register 3\"}]}",
            OK);
    doReturn(httpResponse).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));
    Mockito.clearInvocations(HTTP_CLIENT);

    SearchPosRequest request = SearchPosRequest.builder().externalId("register-3").build();
    MPResultsResourcesPage<Pos> page = client.searchPos(request);

    ArgumentCaptor<HttpRequestBase> captor = ArgumentCaptor.forClass(HttpRequestBase.class);
    Mockito.verify(HTTP_CLIENT).execute(captor.capture(), any(HttpContext.class));
    assertEquals("GET", captor.getValue().getMethod());
    assertEquals("/pos", captor.getValue().getURI().getPath());
    assertEquals("external_id=register-3", captor.getValue().getURI().getRawQuery());
    assertEquals(1, page.getPaging().getTotal());
    assertEquals("3", page.getResults().get(0).getId());
    assertEquals("Register 3", page.getResults().get(0).getName());
  }

  @Test
  void searchPosWithCategoryAndRequestOptionsSuccess() throws Exception {
    HttpResponse httpResponse =
        generateHttpResponseFromString(
            "{\"paging\":{\"total\":1,\"limit\":10,\"offset\":0},\"results\":[{\"id\":\"4\",\"name\":\"Register 4\"}]}",
            OK);
    doReturn(httpResponse).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));
    Mockito.clearInvocations(HTTP_CLIENT);

    SearchPosRequest request = SearchPosRequest.builder().category(42).build();
    MPResultsResourcesPage<Pos> page = client.searchPos(request, buildRequestOptions());

    ArgumentCaptor<HttpRequestBase> captor = ArgumentCaptor.forClass(HttpRequestBase.class);
    Mockito.verify(HTTP_CLIENT).execute(captor.capture(), any(HttpContext.class));
    assertEquals("GET", captor.getValue().getMethod());
    assertEquals("/pos", captor.getValue().getURI().getPath());
    assertEquals("category=42", captor.getValue().getURI().getRawQuery());
    assertEquals("Bearer abc", captor.getValue().getFirstHeader("authorization").getValue());
    assertEquals(OK, page.getResponse().getStatusCode());
    assertEquals(1, page.getPaging().getTotal());
    assertEquals("4", page.getResults().get(0).getId());
    assertEquals("Register 4", page.getResults().get(0).getName());
  }

  @Test
  void searchPosEncodesExternalIdWithRequestOptionsSuccess() throws Exception {
    HttpResponse httpResponse =
        generateHttpResponseFromString(
            "{\"paging\":{\"total\":1,\"limit\":10,\"offset\":0},\"results\":[{\"id\":\"5\",\"name\":\"Register 5\"}]}",
            OK);
    doReturn(httpResponse).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));
    Mockito.clearInvocations(HTTP_CLIENT);

    SearchPosRequest request = SearchPosRequest.builder().externalId("register&5").build();
    MPResultsResourcesPage<Pos> page = client.searchPos(request, buildRequestOptions());

    ArgumentCaptor<HttpRequestBase> captor = ArgumentCaptor.forClass(HttpRequestBase.class);
    Mockito.verify(HTTP_CLIENT).execute(captor.capture(), any(HttpContext.class));
    assertEquals("GET", captor.getValue().getMethod());
    assertEquals("/pos", captor.getValue().getURI().getPath());
    assertEquals("external_id=register%265", captor.getValue().getURI().getRawQuery());
    assertEquals("Bearer abc", captor.getValue().getFirstHeader("authorization").getValue());
    assertEquals(OK, page.getResponse().getStatusCode());
    assertEquals(1, page.getPaging().getTotal());
    assertEquals("5", page.getResults().get(0).getId());
    assertEquals("Register 5", page.getResults().get(0).getName());
  }

  @Test
  void searchPosDeserializesMultipleTypedResultsWithRequestOptions() throws Exception {
    HttpResponse httpResponse =
        generateHttpResponseFromString(
            "{\"paging\":{\"total\":2,\"limit\":2,\"offset\":0},\"results\":[{\"id\":\"6\",\"name\":\"Register 6\"},{\"id\":\"7\",\"name\":\"Register 7\"}]}",
            OK);
    doReturn(httpResponse).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));
    Mockito.clearInvocations(HTTP_CLIENT);

    MPResultsResourcesPage<Pos> page =
        client.searchPos(SearchPosRequest.builder().storeId("store-2").build(), buildRequestOptions());

    ArgumentCaptor<HttpRequestBase> captor = ArgumentCaptor.forClass(HttpRequestBase.class);
    Mockito.verify(HTTP_CLIENT).execute(captor.capture(), any(HttpContext.class));
    assertEquals("GET", captor.getValue().getMethod());
    assertEquals("/pos", captor.getValue().getURI().getPath());
    assertEquals("store_id=store-2", captor.getValue().getURI().getRawQuery());
    assertEquals("Bearer abc", captor.getValue().getFirstHeader("authorization").getValue());
    assertEquals(OK, page.getResponse().getStatusCode());
    assertEquals(2, page.getPaging().getTotal());
    assertEquals(2, page.getPaging().getLimit());
    assertEquals(0, page.getPaging().getOffset());
    assertEquals("6", page.getResults().get(0).getId());
    assertEquals("Register 6", page.getResults().get(0).getName());
    assertEquals("7", page.getResults().get(1).getId());
    assertEquals("Register 7", page.getResults().get(1).getName());
  }
}
