package ru.pivovarov.t1.LoggingOperations;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.pivovarov.t1.LoggingOperations.model.Order;
import ru.pivovarov.t1.LoggingOperations.model.User;
import ru.pivovarov.t1.LoggingOperations.service.OrderService;
import ru.pivovarov.t1.LoggingOperations.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserOrderIT {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:latest"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
    }

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Test
    @org.junit.jupiter.api.Order(1)
    void saveAndGetUserOrder() {
        User user = User.builder()
                .id(1L)
                .email("testEmail.ru")
                .name("User")
                .build();
        Order orderSand = Order.builder()
                .id(1L)
                .user(user)
                .description("sand")
                .status("inactive")
                .build();
        Order orderRock = Order.builder()
                .user(user)
                .description("rock")
                .status("active")
                .build();
        user.setOrders(List.of(orderSand, orderRock));
        userService.save(user);
        List<User> users = userService.getUsers();
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(2, orderService.getOrders().size());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void shouldGetAllCustomers() {
        RestAssured.baseURI = "http://localhost:" + port;
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users/0")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getUserOrderById_returnError() {
        Assertions.assertThrows(NoSuchElementException.class, () -> userService.getUserById(0L));
        Assertions.assertThrows(NoSuchElementException.class, () -> orderService.getOrderById(0L));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @Transactional
    void getUserOrderById_return() {
        User user = userService.getUserById(1L);
        Assertions.assertEquals("testEmail.ru", user.getEmail());
        Assertions.assertNotNull(orderService.getOrderById(1L));
    }
    @Test
    @org.junit.jupiter.api.Order(4)
    void deleteUserOrder() {
        List<Order> orders = orderService.getOrders();
        Assertions.assertEquals(2, orders.size());
        orderService.deleteOrderById(1L);
        Assertions.assertEquals(1, orderService.getOrders().size());
        userService.deleteUserById(1L);
        Assertions.assertEquals(0, userService.getUsers().size());
        Assertions.assertEquals(0, orderService.getOrders().size());
    }
}
