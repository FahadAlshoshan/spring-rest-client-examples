package guru.springframework.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {
  private final String API_ROOT = "https://api.predic8.de:443/shop/";
  RestTemplate restTemplate;

  @Before
  public void setUp() throws Exception {
    restTemplate = new RestTemplate();
  }

  @Test
  public void testGetCategories() {
    JsonNode jsonNode = restTemplate.getForObject(API_ROOT + "categories/", JsonNode.class);
    System.out.println(jsonNode.toString());
  }

  @Test
  public void testGetCategory() {
    JsonNode jsonNode = restTemplate.getForObject(API_ROOT + "categories/1", JsonNode.class);
    System.out.println(jsonNode.toString());
  }

  @Test
  public void testPostCustomer() {
    Map<String, Object> map = new HashMap<>();
    map.put("firstname", "Test");
    map.put("lastname", "TestLast");
    JsonNode jsonNode = restTemplate.postForObject(API_ROOT + "customers/", map, JsonNode.class);
    System.out.println(jsonNode.toString());
  }

  @Test
  public void testUpdateCustomer() {
    String api = API_ROOT + "customers/";
    Map<String, Object> map = new HashMap<>();
    map.put("firstname", "Test1");
    map.put("lastname", "TestLast1");
    JsonNode jsonNode = restTemplate.postForObject(api, map, JsonNode.class);

    System.out.println("Response");
    System.out.println(jsonNode.toString());

    String customerId = jsonNode.get("customer_url").textValue().split("/")[3];
    System.out.println("Created Customer id: " + customerId);

    map.put("firstname", "Test2");
    map.put("lastname", "TestLast2");
    restTemplate.put(api + customerId, map);
    JsonNode updateNode = restTemplate.getForObject(api + customerId, JsonNode.class);
    System.out.println(updateNode.toString());
  }

  @Test(expected = HttpClientErrorException.class)
  public void testDeleteCustomer() {
    String api = API_ROOT + "customers/";
    Map<String, Object> map = new HashMap<>();
    map.put("firstname", "Test1");
    map.put("lastname", "TestLast1");
    JsonNode jsonNode = restTemplate.postForObject(api, map, JsonNode.class);

    System.out.println("Response");
    System.out.println(jsonNode.toString());

    String customerId = jsonNode.get("customer_url").textValue().split("/")[3];
    System.out.println("Created Customer id: " + customerId);

    restTemplate.delete(api + customerId, map);
    restTemplate.getForObject(api + customerId, JsonNode.class);
  }
}
