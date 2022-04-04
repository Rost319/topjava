package ru.javawebinar.topjava.service.jpa;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@RunWith(SpringRunner.class)
@ActiveProfiles({Profiles.JPA, Profiles.POSTGRES_DB})
public class JpaUserServiceTest extends UserServiceTest {
}
