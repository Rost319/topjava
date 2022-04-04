package ru.javawebinar.topjava.service.jdbc;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@RunWith(SpringRunner.class)
@ActiveProfiles({Profiles.JDBC, Profiles.POSTGRES_DB})
public class JdbcUserServiceTest extends UserServiceTest {
}
