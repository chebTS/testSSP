import ua.cats.SecRole
import ua.cats.SecUser
import ua.cats.SecUserSecRole
import ua.cats.User

class BootStrap {
    def springSecurityService

    def init = { servletContext ->
        /*if (!User.list()){
            User user = new User(email: "chebTS1@gmail.com").save(failOnError: true);
            user = new User(email: "chebTS2@gmail.com").save(failOnError: true);
            user = new User(email: "chebTS3@gmail.com").save(failOnError: true);
            user = new User(email: "chebTS4@gmail.com").save(failOnError: true);
            user = new User(email: "chebTS4@gmail.com").save(failOnError: true);
        }*/
        def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)

        def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
                username: 'admin',
                password: springSecurityService.encodePassword('admin'),
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            SecUserSecRole.create adminUser, adminRole
        }
    }
    def destroy = {
    }
}
