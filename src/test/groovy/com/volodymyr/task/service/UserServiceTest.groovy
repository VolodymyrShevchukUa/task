import com.volodymyr.task.dto.UserRegistration
import com.volodymyr.task.entity.Role
import com.volodymyr.task.entity.User
import com.volodymyr.task.repository.UserRepository
import com.volodymyr.task.service.RoleService
import com.volodymyr.task.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Shared
import spock.lang.Specification

class UserServiceTest extends Specification {

    @Shared
    UserService userService
    @Shared
    UserRepository userRepository
    @Shared
    PasswordEncoder passwordEncoder
    @Shared
    RoleService roleService

    def setup() {
        userRepository = Mock(UserRepository)
        passwordEncoder = Mock(PasswordEncoder)
        roleService = Mock(RoleService)
        userService = new UserService(userRepository, passwordEncoder, roleService)

    }

    def "userRegistration should create a new user"() {
        given:
        def userRegistration = new UserRegistration("newUser","password")
        def existingUser = Optional.empty()

        userRepository.findByUsername(userRegistration.userName) >> existingUser
        passwordEncoder.encode(_) >> "encodedPassword"
        roleService.getAllRoles() >> [new Role()]

        when:
        def result = userService.userRegistration(userRegistration)

        then:
        result.username == userRegistration.userName
    }

    def "userRegistration should throw exception if user already exists"() {
        given:
        def userRegistration = new UserRegistration("existingUser", "password")
        def existingUser = Optional.of(new User())

        userRepository.findByUsername(userRegistration.userName) >> existingUser
        roleService.getAllRoles() >> null

        when:
        def exception = null
        try {
            userService.userRegistration(userRegistration)
        } catch (RuntimeException e) {
            exception = e
        }

        then:
        exception != null
        exception.message == "User already exist"
    }

}
