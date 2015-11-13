package com.guwan.services;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.guwan.domain.Authority;
import com.guwan.domain.User;
import com.guwan.repository.AuthorityRepository;
import com.guwan.repository.UserRepository;
import com.guwan.support.BCryptEncoder;

/**
 * Jdbc user management service, based on the same table structure as its parent class, <tt>JdbcDaoImpl</tt>.
 * <p>
 * Provides CRUD operations for both users and groups. Note that if the {@link #setEnableAuthorities(boolean)
 * enableAuthorities} property is set to false, calls to createUser, updateUser and deleteUser will not store the
 * authorities from the <tt>UserDetails</tt> or delete authorities for the user. Since this class cannot differentiate
 * between authorities which were loaded for an individual or for a group of which the individual is a member,
 * it's important that you take this into account when using this implementation for managing your users.
 *
 * @author Luke Taylor
 * @since 2.0
 */
@Service
public class JpaUserDetailsManager extends JpaDaoImpl implements UserDetailsManager {
    //~ Static fields/initializers =====================================================================================

    // UserDetailsManager SQL
    public static final String DEF_CREATE_USER_SQL =
            "insert into users (username, password, enabled) values (?,?,?)";
    public static final String DEF_DELETE_USER_SQL =
            "delete from users where username = ?";
    public static final String DEF_UPDATE_USER_SQL =
            "update users set password = ?, enabled = ? where username = ?";
    public static final String DEF_INSERT_AUTHORITY_SQL =
            "insert into authorities (username, authority) values (?,?)";
    public static final String DEF_DELETE_USER_AUTHORITIES_SQL =
            "delete from authorities where username = ?";
    public static final String DEF_USER_EXISTS_SQL =
            "select username from users where username = ?";
    public static final String DEF_CHANGE_PASSWORD_SQL =
            "update users set password = ? where username = ?";

    // GroupManager SQL
    public static final String DEF_FIND_GROUPS_SQL =
            "select group_name from groups";
    public static final String DEF_FIND_USERS_IN_GROUP_SQL =
            "select username from group_members gm, groups g " +
            "where gm.group_id = g.id" +
            " and g.group_name = ?";
    public static final String DEF_INSERT_GROUP_SQL =
            "insert into groups (group_name) values (?)";
    public static final String DEF_FIND_GROUP_ID_SQL =
            "select id from groups where group_name = ?";
    public static final String DEF_INSERT_GROUP_AUTHORITY_SQL =
            "insert into group_authorities (group_id, authority) values (?,?)";
    public static final String DEF_DELETE_GROUP_SQL =
            "delete from groups where id = ?";
    public static final String DEF_DELETE_GROUP_AUTHORITIES_SQL =
            "delete from group_authorities where group_id = ?";
    public static final String DEF_DELETE_GROUP_MEMBERS_SQL =
            "delete from group_members where group_id = ?";
    public static final String DEF_RENAME_GROUP_SQL =
            "update groups set group_name = ? where group_name = ?";
    public static final String DEF_INSERT_GROUP_MEMBER_SQL =
            "insert into group_members (group_id, username) values (?,?)";
    public static final String DEF_DELETE_GROUP_MEMBER_SQL =
            "delete from group_members where group_id = ? and username = ?";
    public static final String DEF_GROUP_AUTHORITIES_QUERY_SQL =
            "select g.id, g.group_name, ga.authority " +
            "from groups g, group_authorities ga " +
            "where g.group_name = ? " +
            "and g.id = ga.group_id ";
    public static final String DEF_DELETE_GROUP_AUTHORITY_SQL =
            "delete from group_authorities where group_id = ? and authority = ?";


    //~ Instance fields ================================================================================================

    protected final Log logger = LogFactory.getLog(getClass());

    private AuthenticationManager authenticationManager;
	@Autowired UserRepository repository;
	@Autowired AuthorityRepository authorityRepository;
	@Autowired BCryptEncoder bcryptEncoder;
    private UserCache userCache = new NullUserCache();

    //~ Methods ========================================================================================================

    protected void initDao() throws ApplicationContextException {
        if (authenticationManager == null) {
            logger.info("No authentication manager set. Reauthentication of users when changing passwords will " +
                    "not be performed.");
        }
    }

    //~ UserDetailsManager implementation ==============================================================================

    public void createUser(final UserDetails user) {
    	User reference =(User) user;
        validateUserDetails(reference);
        repository.save(reference);
    }

    public void updateUser(final UserDetails user) {
        validateUserDetails(user);
        repository.save((User)user);

        userCache.removeUserFromCache(user.getUsername());
    }

    public void deleteUser(String username) {
        repository.deleteByUsername(username);
        userCache.removeUserFromCache(username);
    }
    
    public void insertAuthorities(List<Authority> Authorities) {
    	authorityRepository.save(Authorities);
    }


    private void deleteAuthorities(String username) {
    	authorityRepository.deleteByUsername(username);
    }

    public void changePassword(String oldPassword, String newPassword) throws AuthenticationException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        newPassword = bcryptEncoder.encode(newPassword);
        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException("Can't change password as no Authentication object found in context " +
                    "for current user.");
        }

        String username = currentUser.getName();

        // If an authentication manager has been set, re-authenticate the user with the supplied password.
        if (authenticationManager != null) {
            logger.debug("Reauthenticating user '"+ username + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            logger.debug("No authentication manager set. Password won't be re-checked.");
        }

        logger.debug("Changing password for user '"+ username + "'");

        User reference =repository.findByUsername(username);
        reference.setPassword(newPassword);
        repository.save(reference);
        
        //repository.setPasswordForUsername(newPassword,username);

        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser, newPassword));

        userCache.removeUserFromCache(username);
    }

    protected Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
        UserDetails user = loadUserByUsername(currentAuth.getName());

        UsernamePasswordAuthenticationToken newAuthentication =
                new UsernamePasswordAuthenticationToken(user, newPassword, user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }

    public boolean userExists(String username) {
    	User user =repository.findByUsername(username);
        return user == null;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Optionally sets the UserCache if one is in use in the application.
     * This allows the user to be removed from the cache after updates have taken place to avoid stale data.
     *
     * @param userCache the cache used by the AuthenticationManager.
     */
    public void setUserCache(UserCache userCache) {
        Assert.notNull(userCache, "userCache cannot be null");
        this.userCache = userCache;
    }

    private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
        validateAuthorities(user.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(), "getAuthority() method must return a non-empty string");
        }
    }
}
