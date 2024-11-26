package kg.tasksystem.service;

import kg.tasksystem.model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;

    @Spy
    private JwtService jwtServiceSpy;

    @Test
    @Disabled("Не работает из-за несоответствия генерируемого поля Jwts.expiration и, в итоге, получаемого токена")
    public void generateToken_withData_expectSuccess() {
        int userId = 1;
        String userEmail = "test@example.com";
        User customUserDetails = User.builder().id(userId).email(userEmail).role("ROLE_USER").build();
        String initialToken = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjoxLCJlbWFpbCI6InRlc3RAZXhhbXBsZS5jb20iLCJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNzMyNjE1NjIxLCJleHAiOjE3MzI2MjE2MjF9.-ZAjRQmWddKNpu_6hz7xC9U-l8MyTDD32In4Fk6VkW0";

        doReturn(initialToken).when(jwtServiceSpy).generateToken(anyMap(), eq(customUserDetails));

        jwtService.setSecretKey("SkmsMFNubSonX13bhnR9fOaWQ3c+ObXMDJZXJqV1BuUEBXkYLJCtzsQh2HaGpEEKUjN2wlvVK3YKss2O0ElwYXgv0qAERSKNuiWOCTFQwru9muBNwGhsHU83DXiGHhKGKLhYOT2pG85lc5nN1U4mqN09Wd6ED0pkMius8HPXu39nNavIHE3II3DeKiFCtZKtifiA9ccS4Q4gLmHIviQZHiaGugPtqOkjbTlROvgurZsoIh37lFekfLlEC7C+2YA9yOfE9AY0kBcrZBmrzTN2ypx3lMB6F2lZ2JvZEh7t7gQreYnXUdXGbvq4pM4fHKgdfhgwYtZoiyR77YK3GNGw====");

        String token = jwtService.generateToken(customUserDetails);

        assertNotNull(token);
        assertEquals(initialToken, token);

        ArgumentCaptor<Map<String, Object>> claimsCaptor = ArgumentCaptor.forClass(Map.class);
        verify(jwtServiceSpy).generateToken(claimsCaptor.capture(), eq(customUserDetails));

        Map<String, Object> capturedClaims = claimsCaptor.getValue();
        assertEquals(1, capturedClaims.get("id"));
        assertEquals("test@example.com", capturedClaims.get("email"));
        assertEquals("ROLE_USER", capturedClaims.get("role"));
    }
}
