package gr.athenarc.ticketingsystem.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public class User {

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    String sub;
    String firstname;
    String lastname;
    String email;

    public User() {
        // no-arg constructor
    }

    public User(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public static User of(Authentication auth) {
        logger.trace("Creating UserService from Authentication: %n{}", auth);
        User user = new User();
        if (auth == null) {
            throw new InsufficientAuthenticationException("You are not authenticated, please log in.");
        } else if (auth.getPrincipal() instanceof Jwt) {
            Jwt principal = ((Jwt) auth.getPrincipal());
            user.sub = principal.getClaimAsString("sub");
            user.email = principal.getClaimAsString("email");
            user.firstname = principal.getClaimAsString("name");
            user.lastname = principal.getClaimAsString("family_name");
        } else {
            throw new InsufficientAuthenticationException("Could not create user. Insufficient user authentication");
        }
        logger.debug("UserService from Authentication: {}", user);
        return user;
    }

    public User(User user) {
        this(user.getFirstname(), user.getLastname(), user.getEmail());
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getFirstname() != null ? !getFirstname().equals(user.getFirstname()) : user.getFirstname() != null)
            return false;
        if (getLastname() != null ? !getLastname().equals(user.getLastname()) : user.getLastname() != null)
            return false;
        return getEmail() != null ? getEmail().equals(user.getEmail()) : user.getEmail() == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstname() != null ? getFirstname().hashCode() : 0;
        result = 31 * result + (getLastname() != null ? getLastname().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        return result;
    }
}
